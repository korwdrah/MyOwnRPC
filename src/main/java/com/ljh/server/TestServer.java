package com.ljh.server;

import com.ljh.service.BlogService;
import com.ljh.service.UserService;
import com.ljh.service.impl.BlogServiceImpl;
import com.ljh.service.impl.UserServiceImpl;

import java.util.HashMap;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
        HashMap<String, Object> serviceMap = new HashMap<>();
        serviceMap.put("com.ljh.service.UserService",userService);
        serviceMap.put("com.ljh.service.BlogService",blogService);

    }
}
