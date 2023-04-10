package com.zzdd.mall.controller;/*
 * description：  com.zzdd.mall.controller 商品目录
 * author:       zz
 * creattime:    2023/4/10 - 13:11
 * */

import com.zzdd.mall.common.ApiRestResponse;
import com.zzdd.mall.common.Constant;
import com.zzdd.mall.exception.ZzddMallExceptionEnum;
import com.zzdd.mall.model.pojo.User;
import com.zzdd.mall.model.request.AddCategoryReq;
import com.zzdd.mall.service.CategoryService;
import com.zzdd.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class CategoryController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session,@Valid @RequestBody AddCategoryReq addCategoryReq){
//        if (addCategoryReq.getName() == null || addCategoryReq.getOrderNum() == null ||
//                addCategoryReq.getParentId() == null || addCategoryReq.getType() == null){
//            return ApiRestResponse.error(ZzddMallExceptionEnum.NAME_NOT_NULL);
//        }
        //获取当前用户
        User currentUser = (User) session.getAttribute(Constant.ZZDD_MALL_USER);

        if (currentUser == null) {
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole){
            //是管理员 添加商品
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }
        else {
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_ADMIN);
        }
    }
}
