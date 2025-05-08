package com.ljh.client;

import com.ljh.RPCObj.RPCRequest;
import com.ljh.RPCObj.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class IOClient {
    //负责与服务端的通信 发送Request 接受Response Request的封装通过另一个单独的类来反射封装
    public static RPCResponse sendRequest(String host, int port, RPCRequest request){
        try{
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            return (RPCResponse)objectInputStream.readObject();

        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
