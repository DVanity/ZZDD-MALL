package com.zzdd.mall.controller;

import com.mysql.cj.util.StringUtils;
import com.zzdd.mall.common.ApiRestResponse;
import com.zzdd.mall.common.Constant;
import com.zzdd.mall.exception.ZzddMallException;
import com.zzdd.mall.exception.ZzddMallExceptionEnum;
import com.zzdd.mall.model.pojo.User;
import com.zzdd.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/*
* 描述：用户控制器
* */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public User personalPage(){
        return userService.getUser();
    }

    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("username") String userName, @RequestParam("password")String password) throws ZzddMallException {
        if (StringUtils.isNullOrEmpty(userName)){
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)){
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_PASSWORD);
        }
        //密码长度校验 长度不小于8位
        if (password.length()<8){
            return ApiRestResponse.error(ZzddMallExceptionEnum.PASSWORD_TOO_SHORT);
        }

        userService.register(userName,password);
        return ApiRestResponse.success();
    }

    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("username") String userName, @RequestParam("password")String password, HttpSession session) throws ZzddMallException {
        if (StringUtils.isNullOrEmpty(userName)){
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)){
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(userName,password);
        //保存用户信息时不保存密码
        user.setPassword(null);
        session.setAttribute(Constant.ZZDD_MALL_USER,user);
        return ApiRestResponse.success(user);
    }

    //更新个性签名
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session,@RequestParam String signature) throws ZzddMallException {
        User currentUser = (User)session.getAttribute(Constant.ZZDD_MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    //登出
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session){
        session.removeAttribute(Constant.ZZDD_MALL_USER);
        return ApiRestResponse.success();
    }

    //管理员登录
    @PostMapping("/adminlogin")
    @ResponseBody
    public ApiRestResponse adminlogin(@RequestParam("username") String userName, @RequestParam("password")String password, HttpSession session) throws ZzddMallException {
        if (StringUtils.isNullOrEmpty(userName)){
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isNullOrEmpty(password)){
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_PASSWORD);
        }

        User user = userService.login(userName,password);
        //校验是否为管理员
        if (userService.checkAdminRole(user)) {
            //是
            //保存用户信息时不保存密码
            user.setPassword(null);
            session.setAttribute(Constant.ZZDD_MALL_USER,user);
            return ApiRestResponse.success(user);
        }else {
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_ADMIN);
        }
    }

}

