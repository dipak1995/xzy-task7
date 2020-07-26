package com.ptt.dao;

import com.ptt.pojo.User;

public interface UserMapper {

    //登录
    User logincheck(String name);

    //注册用户
    void register(User user);

    //更新头像
    Integer updatebyId(User user);

//    //查询所有
//    List<User> queryAllUser();
}
