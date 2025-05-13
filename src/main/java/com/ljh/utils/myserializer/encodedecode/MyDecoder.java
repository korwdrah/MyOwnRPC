package com.ljh.utils.myserializer.encodedecode;

import com.ljh.utils.myserializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //解码过程
        short messageType = in.readShort();
        if(messageType != MessageType.REQUEST.getCode() && messageType != MessageType.RESPONSE.getCode()){
            //不对劲
            System.out.println("不支持对应消息类型");
            return;
        }
        //读取序列化类型
        short serializeType = in.readShort();
        Serializer serializer = Serializer.getSerializerByCode(serializeType);
        if(serializer == null) {
            throw new RuntimeException("没有对应的序列化器");
        }
        //读取序列化后的数组
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        //解码数组成对象并写入out列表
        Object deserialize = serializer.deserialize(bytes, messageType);
        out.add(deserialize);
    }
}
