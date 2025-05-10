package com.ljh.client;

import com.ljh.RPCObj.RPCRequest;
import com.ljh.RPCObj.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
通过动态代理对象 封装Request
 */
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    private String host;
    private int port;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass()
                        .getName())
                .methodName(method.getName())
                .params(args)
                .paramsTypes(method.getParameterTypes()).build();
        RPCResponse response = IOClient.sendRequest(host, port, request);
        System.out.println(response);
        return response.getData();
    }
    //创建代理对象
    //clazz标识要代理的接口
    //
    <T> T getProxy(Class<T> clazz){
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T)o;
    }
}
