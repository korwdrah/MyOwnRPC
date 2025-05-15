package com.ljh.client;

import com.ljh.utils.RPCObj.RPCRequest;
import com.ljh.utils.RPCObj.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/*
通过动态代理对象 封装Request
 */
@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    //通过接口来直接发送请求 可能有netty的实现，也可能是普通实现
    private RPClient rpClient;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass()
                        .getName())
                .methodName(method.getName())
                .params(args)
                .paramsTypes(method.getParameterTypes()).build();
        //通过客户端接口来发送请求
        RPCResponse response = rpClient.sendRequest(request);
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
