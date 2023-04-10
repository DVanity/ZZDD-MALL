package com.zzdd.mall.service.impl;
import com.zzdd.mall.exception.ZzddMallException;
import com.zzdd.mall.exception.ZzddMallExceptionEnum;
import com.zzdd.mall.model.dao.CategoryMapper;
import com.zzdd.mall.model.pojo.Category;
import com.zzdd.mall.model.request.AddCategoryReq;
import com.zzdd.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
 * description：  com.zzdd.mall.service.impl
 * author:       zz
 * creattime:    2023/4/10 - 13:49
 * */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public void add(AddCategoryReq addCategoryReq){
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq,category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            throw new ZzddMallException(ZzddMallExceptionEnum.NAME_GORY_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if (count == 0){
            throw new ZzddMallException(ZzddMallExceptionEnum.CREATE_FAILED);
        }
    }
}
