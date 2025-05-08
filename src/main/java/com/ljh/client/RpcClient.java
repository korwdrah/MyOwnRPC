package com.ljh.client;

import com.ljh.pojo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class RpcClient {
    //客户端的行为
    public static void main(String[] args) {
        try{
            Socket socket = new Socket("127.0.0.1", 8899);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //传输给服务端id
            objectOutputStream.writeInt(new Random().nextInt());
            //缓冲区写入
            objectOutputStream.flush();
            //获取服务端返回的用户对象
            User user = (User) objectInputStream.readObject();
            System.out.println("服务端返回的对象"+user);
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("客户端启动失败");
        }
    }
}
