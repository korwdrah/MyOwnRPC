package com.ljh.utils.zkp;

import com.ljh.utils.loadbalance.LoadBalance;
import com.ljh.utils.loadbalance.RoundLoadBalance;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.nio.FloatBuffer;
import java.util.List;
//znode格式：/rootpath/servicename/host:port
public class ZKServiceRegister implements ServiceRegister{
    //zk客户端
    private CuratorFramework client;
    // zookeeper根路径节点
    private static final String ROOT_PATH = "MyRPC";
    //添加负载均衡 这个应该是可见的
    private LoadBalance loadBalance = new RoundLoadBalance();

    //初始化客户端
    public ZKServiceRegister(){
        // 指数时间重试
        RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
        //使用心跳监听 并建立于zkp的链接
        client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181").sessionTimeoutMs(40000).retryPolicy(policy).namespace(ROOT_PATH).build();
        client.start();
        System.out.println("zkp 链接成功");
    }
    //进行服务注册
    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try{
            if(client.checkExists().forPath("/"+serviceName) == null){
                //将服务创建成永久节点，提供者下线的时候只删除地址，不删除服务名
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/"+serviceName);
            }
            //服务路径地址
            String path = "/"+serviceName+"/"+getServiceAddress(serverAddress);
            //创建临时节点
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
        }
        catch (Exception e){
            System.out.println("服务已存在");
        }
    }
    //根据服务名称返回地址
    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            List<String> paths = client.getChildren().forPath("/" + serviceName);
            //通过负载均衡选择服务器
            String addStr = loadBalance.balance(paths);
            return parseAddress(addStr);
        }
        catch (Exception e){
            System.out.println("没有该服务");
        }
        return null;
    }
    //通过字符串解析服务地址
    private InetSocketAddress parseAddress(String addStr) {
        String[] split = addStr.split(":");
        return new InetSocketAddress(split[0],Integer.parseInt(split[1]));
    }

    public String getServiceAddress(InetSocketAddress serverAddress){
        //获取服务提供者的地址
        return serverAddress.getHostName()+":"+serverAddress.getPort();
    }
}
