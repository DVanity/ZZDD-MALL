package com.zzdd.mall.exception;/*
 * description：  com.zzdd.mall.exception 统一异常
 * author:       zz
 * creattime:    2023/4/6 - 15:03
 * */

public class ZzddMallException extends Exception{
    private final Integer code;
    private final String message;

    public ZzddMallException(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public ZzddMallException(ZzddMallExceptionEnum exceptionEnum){
        this(exceptionEnum.getCode(),exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
