package com.ljh.server;

import com.ljh.RPCObj.RPCRequest;
import com.ljh.RPCObj.RPCResponse;
import com.ljh.pojo.User;
import com.ljh.service.UserService;
import com.ljh.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/*
* 需要进行对象的封装*/
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
                        RPCRequest request = (RPCRequest) objectInputStream.readObject();
                        //处理逻辑
                        //通过反射机制获取方法对象
                        Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
                        //通过invoke调用方法
                        Object invoke = method.invoke(userService, request.getParams());
                        objectOutputStream.writeObject(RPCResponse.success(invoke));
                        objectOutputStream.flush();
                    }
                    catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
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
