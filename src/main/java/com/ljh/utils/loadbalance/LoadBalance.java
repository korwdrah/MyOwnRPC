package com.ljh.utils.loadbalance;

import java.util.List;

public interface LoadBalance {
    //根据负载结果返回一个服务地址
    String balance(List<String> addressList);
}
