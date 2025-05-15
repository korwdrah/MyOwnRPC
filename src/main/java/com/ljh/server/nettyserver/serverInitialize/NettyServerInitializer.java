package com.ljh.server.nettyserver.serverInitialize;

import com.ljh.utils.provider.ServiceProvider;
import com.ljh.utils.myserializer.JsonSerializer;
import com.ljh.utils.myserializer.encodedecode.MyDecoder;
import com.ljh.utils.myserializer.encodedecode.MyEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

//初始化channel的pipeline----即怎么接受请求 初始化TCP链接的SocketChannel
//pipeline本质上是一个ChannelHandler的链表 处理链中流动的数据
@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;
    //inbound和outbound公用一个链表
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MyDecoder());
        pipeline.addLast(new MyEncoder(new JsonSerializer()));

        //具体的调用方法 会触发outBound
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));

    }
}
