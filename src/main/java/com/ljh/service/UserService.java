package com.ljh.service;

import com.ljh.pojo.User;

public interface UserService {
    // 客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);
    //增加一个功能 插入数据
    Integer insertUser(User user);
}
