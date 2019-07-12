package com.cw.biz.channel.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import com.cw.core.common.util.RadomUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 渠道
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_channel",indexes = {@Index(unique = true, name = "cw_channel_unique_idx", columnList = "code")})
@Getter
@Setter
public class Channel extends AggEntity {

    @Column(name="code",columnDefinition="varchar(100) not null comment '渠道编码'")
    private String code;

    @Column(name="name",columnDefinition="varchar(100) not null comment '渠道名称'")
    private String name;

    @Column(name="address",columnDefinition="varchar(100) not null comment '渠道地址'")
    private String address;

    @Column(name="contact_person",columnDefinition="varchar(100) not null comment '联系人'")
    private String contactPerson;

    @Column(name="tel",columnDefinition="varchar(100) not null comment '联系电话'")
    private String tel;

    @Column(name="memo",columnDefinition="varchar(300) not null comment '备注'")
    private String memo;

    @Column(name="channel_price",columnDefinition="decimal(11,2) not null comment '渠道价格'")
    private BigDecimal channelPrice;

    @Column(name="cooperation_type",columnDefinition="varchar(20) not null comment '合作类型'")
    private String cooperationType;//cpa cps

    @Column(name="cooperation_mode",columnDefinition="varchar(20) not null comment '合作模式'")
    private String cooperationMode;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    public void prepareSave()
    {
        this.setCode(RadomUtils.generateShortUuid());
    }
}
