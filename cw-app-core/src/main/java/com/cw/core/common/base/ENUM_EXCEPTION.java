package com.cw.core.common.base;

/**
 * @Title: ENUM_EXCEPTION.java
 * @Description: 异常信息定义枚举类
 * @Author: Away
 * @Date: 2018/4/12 12:01
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
public enum ENUM_EXCEPTION {

    E10001("E10001","参数为空"),
    E10002("E10002","根据参数未找到相应数据"),
    E10003("E10003","传入参数有误"),
    E10004("E10004","复制出错"),
    E10005("E10005","获取当前登录用户失败"),
    E10006("E10006","未找到相应配置"),
    E10007("E10007","逻辑删除相应数据出错"),
    E10008("E10008","保存数据出错"),
    E10009("E10009","更新数据出错"),
    E10010("E10010","已经存在相同账号用户"),
    E10011("E10011","不存在此用户"),
    E10012("E10012","Token已经过期"),
    E10013("E10013","Token格式错误"),
    E10014("E10014","Token没有被正确构造"),
    E10015("E10015","签名失败"),
    E10016("E10016","非法参数异常"),
    E10017("E10017","未知错误"),
    E10018("E10018","Token生成失败"),
    E10019("E10019","Token为空"),
    E10020("E10020","密码错误"),
    E10021("E10021","MAP转实体出错"),
    E10022("E10022","无访问权限"),
    E10023("E10023","ID为空"),
    E10024("E10024","平台编号为空"),
    E10025("E10025","验证码错误"),
    E10026("E10026","会话过期"),
    E10027("E10027","图片验证码过期"),
    E10028("E10028","分页参数为空"),
    E10029("E10029","已经存在相同邮箱账户"),
    E10030("E10030","不存在相应模板"),
    E10031("E10031","已经存在相同编号属性"),
    E10032("E10032","根据token查找新颜手机号出错，返回为空"),
    E10033("E10033","根据token未查到相应手机号码");

    public final String msg;
    public final String code;

    private ENUM_EXCEPTION(String code, String msg){
        this.msg=msg;
        this.code=code;
    }


}
