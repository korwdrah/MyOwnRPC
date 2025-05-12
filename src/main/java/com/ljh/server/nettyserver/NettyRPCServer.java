package com.ljh.server.nettyserver;

import com.ljh.server.RPCServer;
import com.ljh.server.provider.ServiceProvider;
import com.ljh.server.nettyserver.serverInitialize.NettyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyRPCServer implements RPCServer {
    private ServiceProvider serviceProvider;
    @Override
    public void start(int port) {
        //netty 服务线程组boss负责建立连接， work负责具体的请求 本质上是线程池
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        System.out.println("netty服务端启动");
        try{
            //启动服务器
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //初始化(指定处理请求的channel是服务器channel，同时指定Handler，也就是如何处理请求)
            serverBootstrap.group(boss,work).channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(serviceProvider));

            //绑定端口 同步阻塞（sync等待bind完成后才继续进行）
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //死循环监听(中断不出现就一直阻塞主线程)
            channelFuture.channel().closeFuture().sync();
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }

    }

    @Override
    public void stop() {

    }
}
