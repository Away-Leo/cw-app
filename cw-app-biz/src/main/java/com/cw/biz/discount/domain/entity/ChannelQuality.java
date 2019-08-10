package com.cw.biz.discount.domain.entity;

import com.cw.core.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;

/**
* @Title: WholeDisCount.java
* @Description: 扣量全局设置
* @author Away
* @date 2019/7/27 18:29
* @copyright 重庆平讯数据
* @version V1.0
*/
@Entity
@Table(name="cw_channel_quality",indexes = {@Index(name = "quality_channel", columnList = "channel_code,flow_time")})
@org.hibernate.annotations.Table(appliesTo = "cw_channel_quality",comment = "渠道质量")
@Getter
@Setter
public class ChannelQuality extends BaseEntity {

    @Column(name="channel_pv",columnDefinition="int(11) default 0 comment '产品PV统计数'")
    private Integer channelPv=0;

    @Column(name="channel_uv",columnDefinition="int(11) default 0 comment '产品UV统计数'")
    private Integer channelUv=0;

    @Column(name="flow_uv",columnDefinition="int(11) default 0 comment '渠道引流页UV数'")
    private Integer flowUv=0;

    @Column(name="channel_register",columnDefinition="int(11) default 0  comment '渠道注册数'")
    private Integer channelRegister=0;

    @Column(name="channel_login",columnDefinition="int(11) default 0  comment '渠道激活数'")
    private Integer channelLogin=0;

    @Column(name="channel_name",columnDefinition="varchar(255)  comment '渠道名称'")
    private String channelName;

    @Column(name="channel_phone",columnDefinition="varchar(255)  comment '渠道电话'")
    private String channelPhone;

    @Column(name="channel_price",columnDefinition="varchar(10)  comment '渠道价格'")
    private String channelPrice;

    @Column(name="channel_code",columnDefinition="varchar(200) comment '渠道编号'")
    private String channelCode;

    @Column(name="flow_time",columnDefinition="varchar(10) not null comment '引流日期'")
    private String flowTime;


    private transient int page;
    private transient int size;



}
