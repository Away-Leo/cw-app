package com.cw.biz.discount.domain.entity;

import com.cw.core.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
* @Title: WholeDisCount.java
* @Description: 扣量全局设置
* @author Away
* @date 2019/7/27 18:29
* @copyright 重庆平讯数据
* @version V1.0
*/
@Entity
@Table(name="cw_channel_discount")
@org.hibernate.annotations.Table(appliesTo = "cw_channel_discount",comment = "渠道扣量设置")
@Getter
@Setter
public class ChannelDisCountSetting extends BaseEntity {

    @Column(name="start_num",columnDefinition="int(11)  comment '起始扣量数'")
    private Integer startNum;

    @Column(name="percent",columnDefinition="int(11)  comment '扣量比例'")
    private Double percent;

    @Column(name="channel_url",columnDefinition="varchar(255)  comment '渠道推广链接'")
    private String channelUrl;

    @Column(name="channel_back_url",columnDefinition="varchar(255)  comment '渠道推广后台链接'")
    private String channelBackUrl;

    @Column(name="channel_name",columnDefinition="varchar(255)  comment '渠道名称'")
    private String channelName;

    @Column(name="channel_phone",columnDefinition="varchar(255)  comment '渠道电话'")
    private String channelPhone;

    @Column(name="channel_price",columnDefinition="varchar(10)  comment '渠道价格'")
    private String channelPrice;

    @Column(name="channel_code",columnDefinition="varchar(50) unique comment '渠道编号'")
    private String channelCode;

    @Column(name="operator",columnDefinition="varchar(100)  comment '操作员'")
    private String operator;

    private String trueNum;

    private String showNum;

    private String uniId;

    private String regisDate;

    private transient int page;
    private transient int size;



}
