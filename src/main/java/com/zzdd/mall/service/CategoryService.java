package com.zzdd.mall.service;/*
 * description：  com.zzdd.mall.service 商品分类目录Service
 * author:       zz
 * creattime:    2023/4/10 - 13:48
 * */

import com.zzdd.mall.model.request.AddCategoryReq;

public interface CategoryService {
    void add(AddCategoryReq addCategoryReq);
}
