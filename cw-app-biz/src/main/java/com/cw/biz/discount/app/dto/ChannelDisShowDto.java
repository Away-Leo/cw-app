package com.cw.biz.discount.app.dto;

import com.cw.core.common.base.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
* @Title: WholeDisCount.java
* @Description: 扣量全局设置
* @author Away
* @date 2019/7/27 18:29
* @copyright 重庆平讯数据
* @version V1.0
*/
@Getter
@Setter
public class ChannelDisShowDto extends BaseDto {

    /**起始扣量数**/
    private Integer startNum;

    /**扣量比例**/
    private Double percent;

    /**渠道推广链接**/
    private String channelUrl;

    /**渠道推广后台链接**/
    private String channelBackUrl;

    /**渠道名称**/
    private String channelName;

    /**渠道电话**/
    private String channelPhone;

    /**渠道价格**/
    private String channelPrice;

    /**渠道编号**/
    private String channelCode;

    /**操作员**/
    private String operator;

    /**注册日期**/
    private String regisDate;

    /**真是注册数**/
    private String trueNum;

    /**显示数**/
    private String showNum;


    private String uniId;






}
