package com.zzdd.mall.model.request;/*
 * description：  com.zzdd.mall.model.request 添加目录请求类
 * author:       zz
 * creattime:    2023/4/10 - 13:15
 * */

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddCategoryReq {

    @Size(min = 2,max = 5)
    @NotNull(message = "商品名称不能为空")
    private String name;

    @NotNull(message = "分类级别1不能为空")
    @Max(3)
    private Integer type;

    @NotNull(message = "分类级别2不能为空")
    private Integer parentId;

    @NotNull(message = "排序数值不能为空")
    private Integer orderNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
