package com.ljh.RPCObj;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/*
* 对于网络传输肯定会有状态码 所以需要状态信息和对应的数据*/

@Data
@Builder
public class RPCResponse implements Serializable {
    private int code;
    private String message;
    //具体数据
    private Object data;

    public static RPCResponse success(Object data){
        return RPCResponse.builder().code(200).data(data).build();
    }

    public static RPCResponse fail(Object data){
        return RPCResponse.builder().code(500).message("服务器发生错误").build();
    }

}
