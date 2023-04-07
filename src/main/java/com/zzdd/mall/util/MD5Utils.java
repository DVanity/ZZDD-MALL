package com.zzdd.mall.util;/*
 * description：  com.zzdd.mall.util MD5工具
 * author:       zz
 * creattime:    2023/4/6 - 16:43
 * */

import com.zzdd.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String getMD5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        return Base64.encodeBase64String(md5.digest((strValue+ Constant.SALT).getBytes()));
    }
}
