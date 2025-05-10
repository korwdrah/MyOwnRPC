package com.ljh.server;

import com.ljh.RPCObj.RPCRequest;
import com.ljh.RPCObj.RPCResponse;
import com.ljh.pojo.User;
import com.ljh.service.UserService;
import com.ljh.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/*
* 需要进行对象的封装*/
public interface RPCServer {
    void start(int port);
    void stop();
}
