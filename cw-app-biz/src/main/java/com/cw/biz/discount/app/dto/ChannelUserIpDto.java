package com.cw.biz.discount.app.dto;

import com.cw.core.common.base.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
* @Title: WholeDisCount.java
* @Description: 渠道用户IP统计
* @author Away
* @date 2019/7/27 18:29
* @copyright 重庆平讯数据
* @version V1.0
*/
@Getter
@Setter
public class ChannelUserIpDto extends BaseDto {

    /**渠道编号**/
    private String channelCode;

    /**渠道用户IP**/
    private String userIp;

    private String flowTime;



}
