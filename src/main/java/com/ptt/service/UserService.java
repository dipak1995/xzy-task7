package com.ptt.service;

import com.ptt.pojo.User;


public interface UserService {

    //登录
    User logincheck(String name);

    //注册用户
    void register(User user);

    Integer updatebyId(User user);
}
