package com.ljh.server;

import com.ljh.pojo.User;
import com.ljh.service.UserService;
import com.ljh.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        try{
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务端启动");
            while (true){
                //监听端口
                Socket socket = serverSocket.accept();
                //开个线程处理对应的请求
                new Thread(()->{
                    try{
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        Integer id = objectInputStream.readInt();
                        User user = userService.getUserByUserId(id);
                        objectOutputStream.writeObject(user);
                        objectOutputStream.flush();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                        System.out.println("IO读取错误失败");
                    }
                }).start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }
}
