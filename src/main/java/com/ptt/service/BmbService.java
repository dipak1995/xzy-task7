package com.ptt.service;

import com.ptt.pojo.Bmb;

import java.util.List;

public interface BmbService {

    /**
     * 添加用户
     */
    int saveBmb(Bmb bmb);

    /**
     * 根据id删除用户
     */
    int deleteBmbById(Integer id);

    /**
     * 修改用户
     */
    int updateBmb(Bmb bmb);

    /**
     * 根据id查询单个用户
     */
    Bmb getBmbById(Integer id);

    /**
     * 查询所有操作
     */
    List<Bmb> queryAllBmb();
}
