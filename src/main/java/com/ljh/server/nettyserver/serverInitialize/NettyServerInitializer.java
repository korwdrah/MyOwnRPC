package com.ljh.server.nettyserver.serverInitialize;

import com.ljh.server.provider.ServiceProvider;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;

//初始化channel的pipeline----即怎么接受请求 初始化TCP链接的SocketChannel
//pipeline本质上是一个ChannelHandler的链表 处理链中流动的数据
@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;
    //inbound和outbound保持相对顺序，内部的handler保持代码的顺序
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //Inbound 流程
        // 消息格式 [长度][消息体], 解决粘包问题 整个包的最大长度 长度字段的偏移量 长度字段大小（4字节---int) 需要调整的偏移量（不包含消息头的长度就要调整，0就代表就是消息体的长度） 从解码后的帧跳的字节数（跳过4个字节的长度字段）
        // 解码消息
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
        //解码成Java类对象
        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
            @Override
            public Class<?> resolve(String className) throws ClassNotFoundException {
                return Class.forName(className);
            }
        }));
        //具体的调用方法
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));

        //outBound流程---编码 写入长度
        // 编码器，长度字段，写入到前4个字节中 int类型
        pipeline.addLast(new LengthFieldPrepender(4));

        // 这里使用的还是java 序列化方式， netty的自带的解码编码支持传输这种结构
        pipeline.addLast(new ObjectEncoder());

    }
}
