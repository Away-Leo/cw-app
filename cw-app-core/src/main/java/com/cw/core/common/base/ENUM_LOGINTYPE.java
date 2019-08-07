package com.cw.core.common.base;

import com.cw.core.common.util.ObjectHelper;

/**
 * @Title: ENUM_EXCEPTION.java
 * @Description: 异常信息定义枚举类
 * @Author: Away
 * @Date: 2018/4/12 12:01
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
public enum ENUM_LOGINTYPE {

    PHONELOGIN("phoneLogin","手机号登陆"),
    PASSWORDLOGIN("passwordLogin","密码登陆");

    public final String msg;
    public final String code;

    private ENUM_LOGINTYPE(String code, String msg){
        this.msg=msg;
        this.code=code;
    }

    public static String getType(String type){
        if(ObjectHelper.isNotEmpty(type)){
            for(ENUM_LOGINTYPE temp:ENUM_LOGINTYPE.values()){
                if(temp.code.equalsIgnoreCase(type)){
                    return temp.code;
                }
            }
        }
        return PASSWORDLOGIN.code;
    }

}
