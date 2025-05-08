package com.ljh.RPCObj;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/*请求的接口名称 方法名 参数列表 参数类型 都要抽象
这样服务端就能根据这些信息根据反射调用相应的方法*/
@Data
@Builder
public class RPCRequest implements Serializable {
    // 服务类名，客户端只知道接口名，在服务端中用接口名指向实现类
    private String interfaceName;
    // 方法名
    private String methodName;
    // 参数列表
    private Object[] params;
    // 参数类型
    private Class<?>[] paramsTypes;
}
