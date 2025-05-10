package com.ljh.server.provider;

import java.util.HashMap;
import java.util.Map;

public class ServiceProvider {
    /*
    封装提供商
     */
    private Map<String,Object> interfaceProvider;

    public ServiceProvider(){
        interfaceProvider = new HashMap<>();
    }

    public void provideServiceInterface(Object service){
//        String name = service.getClass().getName();
        //可能实现了多个接口
        Class<?>[] interfaces = service.getClass().getInterfaces();
        //添加对应的service
        for(Class clazz:interfaces){
            interfaceProvider.put(clazz.getName(),service);
        }
    }

    public Object getService(String interfaceName){
        return interfaceProvider.get(interfaceName);
    }
}
