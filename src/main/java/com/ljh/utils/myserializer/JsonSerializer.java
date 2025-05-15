package com.ljh.utils.myserializer;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljh.utils.RPCObj.RPCRequest;
import com.ljh.utils.RPCObj.RPCResponse;

public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object object) {
        //通过json来序列化
        return JSONObject.toJSONBytes(object);
    }

    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        switch (messageType){
            case 0:
                //解码请求
                RPCRequest rpcRequest = JSON.parseObject(bytes, RPCRequest.class);
                //防止反序列化的时候参数类型丢失
                Object[] objects = new Object[rpcRequest.getParams().length];
                for (int i = 0; i < objects.length; i++) {
                    Class<?> paramsType = rpcRequest.getParamsTypes()[i];
                    if(!paramsType.isAssignableFrom(rpcRequest.getParams()[i].getClass())){
                        objects[i] = JSONObject.toJavaObject((JSONObject) rpcRequest.getParams()[i],rpcRequest.getParamsTypes()[i]);
                    }
                    else{
                        objects[i] = rpcRequest.getParams()[i];
                    }
                }
                rpcRequest.setParams(objects);
                obj = rpcRequest;
                break;
            case 1:
                //解码响应
                RPCResponse rpcResponse = JSON.parseObject(bytes, RPCResponse.class);
                Class<?> dataType = rpcResponse.getDataType();
                //防止反序列化的时候数据类型丢失
                if(!dataType.isAssignableFrom(rpcResponse.getData().getClass())){
                    rpcResponse.setData(JSONObject.toJavaObject((JSONObject)rpcResponse.getData(),dataType));
                }
                obj = rpcResponse;
                break;
            default:
                System.out.println("不支持此类型的消息");
                throw new RuntimeException();
        }
        return obj;
    }

    @Override
    public int getType() {
        return 1;
    }
}
