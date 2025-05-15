package com.ljh.utils.provider;

import com.ljh.utils.zkp.ServiceRegister;
import com.ljh.utils.zkp.ZKServiceRegister;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
    /*
    封装提供商
     */
    private Map<String,Object> interfaceProvider;
    //每一个服务需要向zkp服务器注册
    private ServiceRegister serviceRegister;
    private String host;
    private int port;

    public ServiceProvider(String host,int port){
        interfaceProvider = new HashMap<>();
        serviceRegister = new ZKServiceRegister();
        this.host = host;
        this.port = port;
    }

    public void provideServiceInterface(Object service){
//        String name = service.getClass().getName();
        //可能实现了多个接口
        Class<?>[] interfaces = service.getClass().getInterfaces();
        //添加对应的service
        for(Class clazz:interfaces){
            interfaceProvider.put(clazz.getName(),service);
            //映射
            serviceRegister.register(clazz.getName(),new InetSocketAddress(host,port));
        }
    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
