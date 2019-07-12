package com.cw.biz.report.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 市场日报
 * Created by Administrator on 2017/9/26.
 */
@Getter
@Setter
public class AgencyDailyDto extends BaseDto {

    private Long id;    //日报ID

    private String dayId;   //日报日期

    private Long productId; //产品ID

    private String productName; //产品名称

    private Long channelId; //渠道ID

    private String channelName; //渠道名称

    private String productUrl;  //产品链接

    private String productAdminUrl; //产品后台地址

    private String productUserName; //产品登录用户名

    private String productPwd;  //产品登录密码

    private Integer settleCycle;        //结算周期

    private BigDecimal prepareAmount;   //预付金额

    private BigDecimal inPrice; //接入单价

    private BigDecimal outPrice;    //接出单价

    private Integer applyNum;   //注册量

    private BigDecimal cost;        //产生费用

    private BigDecimal profit;  //单条利润

    private BigDecimal profitCount; //总利润

    private String moneyInfo;   //打款信息

    private Integer isSettle;   //是否结算

    private BigDecimal prepareAmountBalance; //预付结余
}
