package com.ljh.utils.loadbalance;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance implements LoadBalance{
    @Override
    public String balance(List<String> addressList) {
        //随机返回地址
        Random random = new Random();
        int randIdx = random.nextInt(addressList.size());
        System.out.println("选择"+randIdx+"号服务器");
        return addressList.get(randIdx);
    }
}
