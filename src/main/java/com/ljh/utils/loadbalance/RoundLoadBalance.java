package com.ljh.utils.loadbalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundLoadBalance implements LoadBalance{
    private int choose = -1;
    @Override
    public String balance(List<String> addressList) {
        //轮询负载均衡
        choose++;
        choose = choose % addressList.size();
        return addressList.get(choose);
    }
}
