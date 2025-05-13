package com.ljh.client;

import com.ljh.client.nettyclinet.NettyRPCClient;
import com.ljh.pojo.Blog;
import com.ljh.server.nettyserver.NettyRPCServer;
import com.ljh.service.BlogService;
import com.ljh.service.impl.UserServiceImpl;

public class RpcClient {
    //客户端的行为
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        //只需要通过客户端去发送消息
//        SimpleRpcClient simpleRpcClient = new SimpleRpcClient("127.0.0.1", 8899);
        NettyRPCClient nettyRPCClient = new NettyRPCClient("127.0.0.1", 8899);
        ClientProxy clientProxy = new ClientProxy(nettyRPCClient);

//        UserService proxy = clientProxy.getProxy(UserService.class);
//
//        //调用方法1
//        User user = proxy.getUserByUserId(1);
//        System.out.println(user);
//        Integer user1 = proxy.insertUser(user);
//        System.out.println("user1 = " + user1);
        BlogService proxy = clientProxy.getProxy(BlogService.class);
        Blog blog = proxy.getBlogById(11);
        System.out.println("blog = " + blog);


    }
}
