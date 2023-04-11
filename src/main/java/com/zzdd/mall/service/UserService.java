package com.zzdd.mall.service;

import com.zzdd.mall.exception.ZzddMallException;
import com.zzdd.mall.model.pojo.User;

/*
* 描述： UserService
* */
public interface UserService {

    User getUser();

    //注册
    void register(String userName,String password) throws ZzddMallException;

    //登录
    User login(String userName, String password) throws ZzddMallException;

    //更新人员信息
    void updateInformation(User user) throws ZzddMallException;

    //检查是否是管理员
    boolean checkAdminRole(User user);
}
