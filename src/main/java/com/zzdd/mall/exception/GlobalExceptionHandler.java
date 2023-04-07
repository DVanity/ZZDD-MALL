package com.zzdd.mall.exception;/*
 * description：  com.zzdd.mall.exception 处理统一异常的handler
 * author:       zz
 * creattime:    2023/4/6 - 15:47
 * */

import com.zzdd.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice //拦截异常
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public  Object handleException(Exception ex){
        log.error("Default Exception",ex);
        return ApiRestResponse.error(ZzddMallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(ZzddMallException.class)
    @ResponseBody
    public  Object handleZzddMallException(ZzddMallException ex){
        log.error("ZzddMallException",ex);
        return ApiRestResponse.error(ex.getCode(),ex.getMessage());
    }
}
