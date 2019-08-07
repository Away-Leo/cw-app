package com.cw.biz.xinyanlogin.utils;

import java.security.MessageDigest;

/**
 * MD5 工具类
 *
 * @author yingmuhuadao
 * @date 2019/4/28
 */
public class Md5Util {
    public static String encrypt32(String encryptStr) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5.digest(encryptStr.getBytes());
            StringBuffer hexValue = new StringBuffer();

            for(int i = 0; i < md5Bytes.length; ++i) {
                int val = md5Bytes[i] & 255;
                if (val < 16) {
                    hexValue.append("0");
                }

                hexValue.append(Integer.toHexString(val));
            }

            encryptStr = hexValue.toString();
            return encryptStr;
        } catch (Exception var6) {
            throw new SecurityException("MD5加密失败", var6);
        }
    }
}
