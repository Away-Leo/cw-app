package com.cw.biz.apply.domain.entity;

import com.cw.biz.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品申请消息
 * Created by Administrator on 2017/7/24.
 */
@Entity
@Table(name="cw_product_apply_message")
@Setter
@Getter
public class ApplyMessage extends BaseEntity{

    @Column(name="product_id",columnDefinition="int(11)  comment '产品ID'")
    private Long productId;

    @Column(name="product_name",columnDefinition="varchar(200)  comment '产品名称'")
    private String name;

    @Column(name="user_id",columnDefinition="int(11)  comment '用户ID'")
    private Long userId;

    @Column(name="phone",columnDefinition="varchar(20)  comment '手机号码'")
    private String phone;

    @Column(name="apply_date",columnDefinition="datetime  comment '申请时间'")
    private Date applyDate;

    @Column(name="apply_amount",columnDefinition="decimal(10,2)  comment '申请金额'")
    private BigDecimal applyAmount = BigDecimal.ZERO;
}
