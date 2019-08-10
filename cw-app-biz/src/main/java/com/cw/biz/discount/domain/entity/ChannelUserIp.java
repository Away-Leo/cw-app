package com.cw.biz.discount.domain.entity;

import com.cw.core.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
* @Title: WholeDisCount.java
* @Description: 渠道用户IP统计
* @author Away
* @date 2019/7/27 18:29
* @copyright 重庆平讯数据
* @version V1.0
*/
@Entity
@Table(name="cw_channel_userip",indexes = {@Index(name = "quality_channel", columnList = "channel_code,user_ip")})
@org.hibernate.annotations.Table(appliesTo = "cw_channel_userip",comment = "渠道用户IP")
@Getter
@Setter
public class ChannelUserIp extends BaseEntity {

    @Column(name="channel_code",columnDefinition="varchar(200) not null comment '渠道编号'")
    private String channelCode;

    @Column(name="user_ip",columnDefinition="varchar(30) not null comment '渠道用户IP'")
    private String userIp;

    @Column(name="flow_time",columnDefinition="varchar(10) not null comment '引流日期'")
    private String flowTime;

}
