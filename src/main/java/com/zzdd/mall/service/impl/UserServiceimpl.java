package com.zzdd.mall.service.impl;
import com.zzdd.mall.exception.ZzddMallException;
import com.zzdd.mall.exception.ZzddMallExceptionEnum;
import com.zzdd.mall.model.dao.UserMapper;
import com.zzdd.mall.model.pojo.User;
import com.zzdd.mall.service.UserService;
import com.zzdd.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.security.NoSuchAlgorithmException;
/*
* 描述：UserService 实现类
* */

@Service
public class UserServiceimpl implements UserService {
    @Autowired()
    UserMapper userMapper;

    @Override
    public User getUser() {
        return userMapper.selectByPrimaryKey(1);
    }

    //注册用户
    @Override
    public void register(String userName, String password) throws ZzddMallException {
        //查询用户名是否存在，不允许重名
        User result = userMapper.selectByName(userName);
        if(result != null){
            throw new ZzddMallException(ZzddMallExceptionEnum.NAME_EXISTED);
        }

        //注册到数据库
        User user = new User();
        user.setUsername(userName);
        try {
            user.setPassword(MD5Utils.getMD5Str(password));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        int count = userMapper.insertSelective(user);
        if (count == 0){
            throw new ZzddMallException(ZzddMallExceptionEnum.INSERT_FAILED);
        }
    }

    //登录
    @Override
    public User login(String userName, String password) throws ZzddMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName,md5Password);
        if(user == null){
            throw new ZzddMallException(ZzddMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;

    }

    //更新个性签名
    @Override
    public void updateInformation(User user) throws ZzddMallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1){
            throw new ZzddMallException(ZzddMallExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public boolean checkAdminRole(User user){
        //2是管理员
        return user.getRole().equals(2);
    }
}
