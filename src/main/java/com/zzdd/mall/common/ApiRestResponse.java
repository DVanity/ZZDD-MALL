package com.zzdd.mall.common;/*
 * description：  com.zzdd.mall.common 通用返回对象
 * author:       zz
 * creattime:    2023/4/6 - 13:50
 * */

import com.zzdd.mall.exception.ZzddMallExceptionEnum;

public class ApiRestResponse<T> {
    private Integer status;

    private String mas;

    private T data;

    private static final int OK_CODE = 10000;

    private static final String OK_MSG = "SUCCESS";

    public ApiRestResponse(Integer status, String mas, T data) {
        this.status = status;
        this.mas = mas;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String mas) {
        this.status = status;
        this.mas = mas;
    }

    public ApiRestResponse() {
        this(OK_CODE,OK_MSG);
    }

    public static <T> ApiRestResponse<T> success(){
        return new ApiRestResponse<>();
    }

    public static <T> ApiRestResponse<T> success(T result){
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setData(result);
        return response;
    }

    public static <T> ApiRestResponse<T> error(Integer code,String msg){
        return new ApiRestResponse<>(code,msg);
    }

    public static <T> ApiRestResponse<T> error(ZzddMallExceptionEnum ex){
        return new ApiRestResponse<>(ex.getCode(),ex.getMsg());
    }

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", mas='" + mas + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMas() {
        return mas;
    }

    public void setMas(String mas) {
        this.mas = mas;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static int getOkCode() {
        return OK_CODE;
    }

    public static String getOkMsg() {
        return OK_MSG;
    }
}
