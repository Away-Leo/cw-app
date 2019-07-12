package com.cw.biz.parameter.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 积分墙用户点击记录表
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_integral_wall_log")
@Setter
@Getter
public class IntegralWallLog extends AggEntity{

    @Column(name="device_no",columnDefinition="varchar(50) not null comment '设备唯一编码'")
    private String deviceNo;

    @Column(name="code",columnDefinition="varchar(50) not null comment '随机码'")
    private String code;

    @Column(name="app_name",columnDefinition="varchar(50)  comment '所属APP'")
    private String appName;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
