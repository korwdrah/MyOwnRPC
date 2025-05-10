package com.ljh.client;

import com.ljh.RPCObj.RPCRequest;
import com.ljh.RPCObj.RPCResponse;
import com.ljh.pojo.Blog;
import com.ljh.pojo.User;
import com.ljh.service.BlogService;
import com.ljh.service.UserService;
import com.ljh.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class RpcClient {
    //客户端的行为
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
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
