package com.ljh.utils.myserializer;

import java.io.*;

//Java自带的序列化方式
public class ObjectSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = null;
        //序列化对象
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{
            //创建一个bos的oos 通过自带的序列化方式来将对象写到bos中
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        //反序列化
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return obj;
    }
    //0是原生的java序列化器
    @Override
    public int getType() {
        return 0;
    }
}
