package com.ljh.client.nettyclinet;

import com.ljh.RPCObj.RPCRequest;
import com.ljh.RPCObj.RPCResponse;
import com.ljh.client.RPClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.AllArgsConstructor;

import java.util.concurrent.CountDownLatch;

@AllArgsConstructor
public class NettyRPCClient implements RPClient {
    private static final Bootstrap bootStrap;
    private static final EventLoopGroup eventLoopGroup;
    private String host;
    private int port;
    static {
        //初始化
        eventLoopGroup = new NioEventLoopGroup();
        bootStrap = new Bootstrap();
        //创建通道
        bootStrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new NettyClientInitializer());
    }


    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try{
            //具体的发送请求
            ChannelFuture channelFuture = bootStrap.connect(host, port).sync();
            //建立链接
            Channel channel = channelFuture.channel();
            //写入请求
            channel.writeAndFlush(request);
            //不选择阻塞线程 异步等待响应到达handler
            channel.closeFuture().sync();
            //线程隔离的Attributekey拿到结果
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();
            System.out.println(response);
            return response;
        }
        catch (InterruptedException | NullPointerException e){
            System.out.println("e = " + e);
        }
        return null;
    }
}
