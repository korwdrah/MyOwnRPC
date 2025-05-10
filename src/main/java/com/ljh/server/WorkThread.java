package com.ljh.server;

import com.ljh.RPCObj.RPCRequest;
import com.ljh.RPCObj.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/*
工作线程 将通信过程分离出来，便于处理多线程情况
 */
@AllArgsConstructor
public class WorkThread implements Runnable{
    private Socket socket;
    private Map<String,Object> serviceProvider;
    @Override
    public void run() {
        try{
            //具体就是接受请求，同时
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            RPCRequest request = (RPCRequest) ois.readObject();
            RPCResponse response = getResponse(request);
            oos.writeObject(response);
            oos.flush();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("IO读取数据有误");
        }
    }

    private RPCResponse getResponse(RPCRequest request) {
        //通过服务映射来获取对应的服务
        Object service = serviceProvider.get(request.getInterfaceName());
        Method method = null;
        try{
            //反射获取对应的方法
            method = service.getClass().getMethod(request.getMethodName(),request.getParamsTypes());
            //进行调用 一定记得调用的参数有服务类和对应的参数
            Object returnVal = method.invoke(service, request.getParams());
            return RPCResponse.success(returnVal);
        }
        catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
            System.out.println("e = " + e);
            return RPCResponse.fail("方法执行错误");
        }
    }
}
