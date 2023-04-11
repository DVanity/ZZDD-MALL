package com.zzdd.mall.service;/*
 * description：  com.zzdd.mall.service 商品分类目录Service
 * author:       zz
 * creattime:    2023/4/10 - 13:48
 * */

import com.github.pagehelper.PageInfo;
import com.zzdd.mall.model.pojo.Category;
import com.zzdd.mall.model.request.AddCategoryReq;
import com.zzdd.mall.model.vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer();
}
