package com.cw.biz.goods.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_order")
@Getter
@Setter
public class Order extends AggEntity {

    @Column(name="user_id",columnDefinition="int(11)  comment '用户ID'")
    private Long userId;

    @Column(name="goods_id",columnDefinition="int(11)  comment '商品id'")
    private Long goodsId;

    @Column(name="order_date",columnDefinition="datetime  comment '订单时间'")
    private Date orderDate;

    @Column(name="price",columnDefinition="decimal(11,2) comment '订单价格'")
    private BigDecimal price;

    @Column(name="period",columnDefinition="int(11)  comment '期数'")
    private Integer period;

    @Column(name="email_address",columnDefinition="int(11)  comment '邮寄地址'")
    private Integer emailAddress;

    @Column(name="status",columnDefinition="int(1)  comment '订单状态(0：下单，1：支付，发货)'")
    private Integer status;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
