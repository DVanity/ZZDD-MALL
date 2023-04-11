package com.zzdd.mall.controller;/*
 * description：  com.zzdd.mall.controller 商品目录
 * author:       zz
 * creattime:    2023/4/10 - 13:11
 * */

import com.github.pagehelper.PageInfo;
import com.zzdd.mall.common.ApiRestResponse;
import com.zzdd.mall.common.Constant;
import com.zzdd.mall.exception.ZzddMallExceptionEnum;
import com.zzdd.mall.model.pojo.Category;
import com.zzdd.mall.model.pojo.User;
import com.zzdd.mall.model.request.AddCategoryReq;
import com.zzdd.mall.model.request.UpdateCategoryReq;
import com.zzdd.mall.model.vo.CategoryVO;
import com.zzdd.mall.service.CategoryService;
import com.zzdd.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @ApiOperation("后台添加目录")
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

    @ApiOperation("后台更新目录")
    @PostMapping("admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(@Valid @RequestBody UpdateCategoryReq updateCategoryReq,HttpSession session){
        //获取当前用户
        User currentUser = (User) session.getAttribute(Constant.ZZDD_MALL_USER);

        if (currentUser == null) {
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole){
            //是管理员 添加商品
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq,category);
            categoryService.update(category);
            return ApiRestResponse.success();
        }
        else {
            return ApiRestResponse.error(ZzddMallExceptionEnum.NEED_ADMIN);
        }
    }

    @ApiOperation("后台删除目录")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id){
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台目录列表")
    @PostMapping("admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum,@RequestParam Integer pageSize){
        PageInfo pageInfo = categoryService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("前台目录列表")
    @PostMapping("category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer(){
        List<CategoryVO> categoryVOS = categoryService.listCategoryForCustomer();
        return ApiRestResponse.success(categoryVOS);
    }
}
