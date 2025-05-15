package com.ljh.utils.myserializer.encodedecode;

import com.ljh.utils.RPCObj.RPCRequest;
import com.ljh.utils.myserializer.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyEncoder extends MessageToByteEncoder {
    //自定义编码器-----序列化到字节数组
    private Serializer serializer;
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        //消息类型
        System.out.println(msg.getClass());
        //写入消息类型
        if(msg instanceof RPCRequest){
            out.writeShort(MessageType.REQUEST.getCode());
        }
        else{
            out.writeShort(MessageType.RESPONSE.getCode());
        }
        //写入序列化方式
        out.writeShort(serializer.getType());
        byte[] bytes = serializer.serialize(msg);
        //写入长度
        out.writeInt(bytes.length);
        //写入字节数组
        out.writeBytes(bytes);
    }
}
