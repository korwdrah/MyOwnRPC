package com.ljh.client.nettyclinet;

import com.ljh.server.nettyserver.serverInitialize.NettyRPCServerHandler;
import com.ljh.utils.myserializer.JsonSerializer;
import com.ljh.utils.myserializer.encodedecode.MyDecoder;
import com.ljh.utils.myserializer.encodedecode.MyEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //decoder会根据消息头中的内容自动采用对应的Serializer
        pipeline.addLast(new MyDecoder());
        pipeline.addLast(new MyEncoder(new JsonSerializer()));

        //具体的调用方法
        pipeline.addLast(new NettyRPClientHandler());
    }
}
