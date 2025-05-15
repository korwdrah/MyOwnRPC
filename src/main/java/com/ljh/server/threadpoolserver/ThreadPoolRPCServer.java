package com.ljh.server.threadpoolserver;

import com.ljh.server.RPCServer;
import com.ljh.utils.provider.ServiceProvider;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRPCServer implements RPCServer {
    private ServiceProvider serviceProvider;
    private final ThreadPoolExecutor threadPool;

    public ThreadPoolRPCServer(ServiceProvider provider){
        threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                1000,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
        serviceProvider = provider;
    }

    public ThreadPoolRPCServer(ServiceProvider provider,
                               int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit unit,
                               BlockingQueue<Runnable> blockingQueue){
        threadPool = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,blockingQueue);
        serviceProvider = provider;
    }


    @Override
    public void start(int port) {
        System.out.println("服务端启动");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true){
                Socket socket = serverSocket.accept();
                threadPool.execute(new WorkThread(socket,serviceProvider));
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void stop() {

    }
}
