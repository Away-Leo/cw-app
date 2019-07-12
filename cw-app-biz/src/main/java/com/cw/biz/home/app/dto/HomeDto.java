package com.cw.biz.home.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 系统banner
 * Created by dujiangyu on 2017/6/21.
 */
@Getter
@Setter
public class HomeDto extends BaseDto {
    private Long id;
    private String code;
    private String appName;
    private String applyDate;
    private Integer dayUserCount=0;

    private Integer monthUserCount=0;

    private Integer monthApplyCount=0;

    private Integer dayApplyCount=0;

    private Integer dayLogin=0;

    private Integer monthLogin=0;

    private String channelNo;

    private String channelCode;

    private String merchantName;

    private Integer registerNum=0;//注册
    private Integer todayRegisterNum=0;//注册

    private Integer loginTime=0;//登录次数

    private Integer loginNum=0;//二次登陆

    private Integer applyTime=0;//申请

    private Integer applyNum=0;//申请
    private Integer todayApplyNum=0;//申请

    private Integer num=0;

    private BigDecimal merchantIncome=BigDecimal.ZERO;

    private BigDecimal channelOutFee=BigDecimal.ZERO;

    private BigDecimal channelInFee=BigDecimal.ZERO;

    private BigDecimal cpm=BigDecimal.ZERO;

    private BigDecimal roi=BigDecimal.ZERO;

    private BigDecimal distributeRatio=BigDecimal.ZERO;
    private BigDecimal dayRatio=BigDecimal.ZERO;
    private BigDecimal monthRatio=BigDecimal.ZERO;

    private Integer downloadNum=0;//今日下载数

    private Integer yesterdayDownloadNum=0;//昨日下载数

    private Integer commentNum=0;//评论数

    private Integer jumpAppStoreNum=0;//各渠道跳转应用市场量

    private String sortColumn;

    private String sortDesc;

    private Integer monthApplyNum;

    private Integer appMarketDayNum;
    private Integer channelDayNum;

    private Integer appMarketMonthNum;
    private Integer channelMonthNum;

    private Integer appMarketDayApplyTime;
    private Integer channelDayApplyNum;

    private Integer appMarketApplyNum;
    private Integer channelApplyNum;

    private Integer appMarketMonthApplyTime;
    private Integer channelApplyTime;

    private Integer appMarketMonthApplyNum;
    private Integer channelMonthApplyNum;


}
