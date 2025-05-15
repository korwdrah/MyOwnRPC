package com.ljh.server;

import com.ljh.server.nettyserver.NettyRPCServer;
import com.ljh.utils.provider.ServiceProvider;
import com.ljh.service.BlogService;
import com.ljh.service.UserService;
import com.ljh.service.impl.BlogServiceImpl;
import com.ljh.service.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
//        HashMap<String, Object> serviceMap = new HashMap<>();
//        serviceMap.put("com.ljh.service.UserService",userService);
//        serviceMap.put("com.ljh.service.BlogService",blogService);
        ServiceProvider provider = new ServiceProvider("127.0.0.1",8899);
        provider.provideServiceInterface(userService);
        provider.provideServiceInterface(blogService);

//        ThreadPoolRPCServer server = new ThreadPoolRPCServer(provider);
//        server.start(8899);
        NettyRPCServer server = new NettyRPCServer(provider);
        server.start(8899);
    }
}
