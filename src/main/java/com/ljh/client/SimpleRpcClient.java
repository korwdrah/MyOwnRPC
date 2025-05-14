package com.ljh.client;

import com.ljh.RPCObj.RPCRequest;
import com.ljh.RPCObj.RPCResponse;
import com.ljh.utils.zkp.ServiceRegister;
import com.ljh.utils.zkp.ZKServiceRegister;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@AllArgsConstructor
public class SimpleRpcClient implements RPClient{
    private String host;
    private int port;
    private ServiceRegister serviceRegister;
    public SimpleRpcClient(){
        //建立zkp服务器的连接
        serviceRegister = new ZKServiceRegister();
    }

    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        //通过zkp服务器来获取服务的地址
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        host = address.getHostName();
        port = address.getPort();
        try {
            // 发起一次Socket连接请求
            Socket socket = new Socket(host, port);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(request);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            RPCResponse response = (RPCResponse) objectInputStream.readObject();

            //System.out.println(response.getData());
            return response;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println();
            return null;
        }
    }
}
