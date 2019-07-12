package com.cw.biz.parameter.app;

/**
 *
 * Created by Administrator on 2017/6/4.
 */
public enum ParameterEnum {

    startAmount("startAmount","起始金额"),
    endAmount("endAmount","截止金额"),
    startPeriodDay("startPeriodDay","开始天数"),
    endPeriodDay("endPeriodDay","截止天数"),
    startPeriodMonth("startPeriodMonth","开始月份"),
    endPeriodMonth("endPeriodMonth","结束月份"),
    loanRate("loanRate","贷款年利率"),
    operateCost("operateCost","运营成本"),
    suspendFlag("suspendFlag","APP悬浮开关"),
    messageTip("messageTip","产品列表提示"),
    copyright("copyright","版权信息"),
    aboutUs("aboutUs","关于我们");

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    ParameterEnum(String key,String value){
        this.key = key;
        this.value = value;
    }
}
