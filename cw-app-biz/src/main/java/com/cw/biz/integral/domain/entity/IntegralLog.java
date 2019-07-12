package com.cw.biz.integral.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户积分记录
 * Created by Administrator on 2017/11/20.
 */
@Entity
@Table(name="cw_user_integral_log")
@Setter
@Getter
public class IntegralLog extends AggEntity {

    @Column(name="user_id",columnDefinition="int(11) not null comment '用户编码'")
    private Long userId;

    @Column(name="phone",columnDefinition="varchar(11) not null comment '用户手机号'")
    private String phone;

    @Column(name="integral",columnDefinition="decimal(20,2) default 0 comment '用户总积分'")
    private BigDecimal integral=BigDecimal.ZERO;

    @Column(name="integral_type",columnDefinition="int(11) not null comment '积分类型 1：获得积分，2积分兑换礼品'")
    private Long integralType;

    @Column(name="gift_type",columnDefinition="int(11) not null comment '积分兑换类型'")
    private Long giftType;

    @Column(name="gift_id",columnDefinition="int(11) not null comment '礼品ID'")
    private Long giftId;

    @Column(name="active_id",columnDefinition="int(11) not null comment '活动ID'")
    private String activeId;

    @Column(name="active_name",columnDefinition="varchar(200) not null comment '活动名称'")
    private String activeName;

    @Column(name="exchange_date",columnDefinition="datetime comment '兑换时间'")
    private Date exchangeDate;

    @Column(name="apply_date",columnDefinition="datetime comment '申请兑换时间'")
    private Date applyDate;

    @Column(name="memo",columnDefinition="varchar(200) not null comment '备注'")
    private String memo;

    @Column(name="register_no_apply",columnDefinition="decimal(20,2) default 0 comment '注册未申请'")
    private BigDecimal registerNoApply=BigDecimal.ZERO;

    @Column(name="register_apply",columnDefinition="decimal(20,2) default 0 comment '注册已申请'")
    private BigDecimal registerApply=BigDecimal.ZERO;

    @Column(name="loan_user",columnDefinition="decimal(20,2) default 0 comment '成功借款用户'")
    private BigDecimal loanUser=BigDecimal.ZERO;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
