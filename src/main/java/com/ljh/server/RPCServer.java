package com.ljh.server;

/*
* 需要进行对象的封装*/
public interface RPCServer {
    void start(int port);
    void stop();
}
