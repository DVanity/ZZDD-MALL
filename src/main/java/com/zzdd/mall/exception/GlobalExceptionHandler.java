package com.zzdd.mall.exception;/*
 * description：  com.zzdd.mall.exception 处理统一异常的handler
 * author:       zz
 * creattime:    2023/4/6 - 15:47
 * */

import com.zzdd.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice //拦截异常
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //一般系统异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public  Object handleException(Exception ex){
        log.error("Default Exception",ex);
        return ApiRestResponse.error(ZzddMallExceptionEnum.SYSTEM_ERROR);
    }

    //操作异常提示
    @ExceptionHandler(ZzddMallException.class)
    @ResponseBody
    public  Object handleZzddMallException(ZzddMallException ex){
        log.error("ZzddMallException",ex);
        return ApiRestResponse.error(ex.getCode(),ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        log.error("MethodArgumentNotValidException",ex);
        return handleBindingResult(ex.getBindingResult());
    }

    private ApiRestResponse handleBindingResult(BindingResult result){
        //把异常对外提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (int i = 0; i < allErrors.size(); i++) {
                ObjectError objectError =  allErrors.get(i);
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0) {
            return ApiRestResponse.error(ZzddMallExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse.error(ZzddMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }
}
