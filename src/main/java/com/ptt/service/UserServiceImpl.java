package com.ptt.service;

import com.ptt.dao.UserMapper;
import com.ptt.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User logincheck(String name) {

        User u = userMapper.logincheck(name);
        return u;
    }

    @Override
    public void register(User user) {
        userMapper.register(user);
    }

    @Override
    public Integer updatebyId(User user) {
        userMapper.updatebyId(user);
        return user.getId();
    }

    //    //public void reg(User user){
//
//// - 生成随机盐
//        String salt = UUID.randomUUID().toString().toUpperCase();
//// - 基于原密码和盐值执行加密
//        String md5Password = getMd5Password(user.getPassword(), salt);
//// - 将盐和加密结果封装到user对象中
//        user.setSalt(salt);
//        user.setPassword(md5Password);
//
//// 执行注册
//        userMapper.register(user);
//
//    }
//
//    private String getMd5Password(String password, String salt) {
//// 拼接原密码与盐值
//        String str = salt + password + salt;
//// 循环加密5次
//        for (int i = 0; i < 5; i++) {
//
//// DigestUtils：springboot提供的工具类
//            str = DigestUtils.md5DigestAsHex(
//                    str.getBytes()).toUpperCase();
//        }
//// 返回结果
//        return str;
//    }
}
