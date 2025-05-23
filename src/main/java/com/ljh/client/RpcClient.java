package com.ljh.client;

import com.ljh.client.nettyclinet.NettyRPCClient;
import com.ljh.pojo.Blog;
import com.ljh.pojo.User;
import com.ljh.server.nettyserver.NettyRPCServer;
import com.ljh.service.BlogService;
import com.ljh.service.UserService;
import com.ljh.service.impl.UserServiceImpl;

public class RpcClient {
    //客户端的行为
    public static void main(String[] args) {
//        UserServiceImpl userService = new UserServiceImpl();
        //只需要通过客户端去发送消息
//        SimpleRpcClient simpleRpcClient = new SimpleRpcClient("127.0.0.1", 8899);
        //通过zkp获取服务端的信息
        NettyRPCClient nettyRPCClient = new NettyRPCClient();
        ClientProxy clientProxy = new ClientProxy(nettyRPCClient);

        UserService proxy = clientProxy.getProxy(UserService.class);

        //调用方法1
        User user = proxy.getUserByUserId(1);
        System.out.println(user);
//        Integer user1 = proxy.insertUser(user);
//        System.out.println("user1 = " + user1);
        //尝试负载均衡---一个客户端的多个请求去负载均衡
        BlogService proxy_1 = clientProxy.getProxy(BlogService.class);
        Blog blog = proxy_1.getBlogById(11);
        System.out.println("blog = " + blog);


    }
}
