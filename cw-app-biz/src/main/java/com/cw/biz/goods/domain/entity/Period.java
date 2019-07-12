package com.cw.biz.goods.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 分期表
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_period")
@Getter
@Setter
public class Period extends AggEntity {

    @Column(name="goods_id",columnDefinition="int(11)  comment '商品ID'")
    private Long goodsId;

    @Column(name="period",columnDefinition="int(11)  comment '产品分期期数'")
    private Integer period;

    @Column(name="price",columnDefinition="decimal(11,2) comment '分期价格'")
    private BigDecimal price;

    @Column(name="rate",columnDefinition="decimal(11,2) comment '分期利率'")
    private BigDecimal rate;

    @Column(name="period_memo",columnDefinition="varchar(200)  comment '分期描述'")
    private String periodMemo;

    @Column(name="show_order",columnDefinition="int(11)  comment '显示顺序'")
    private Integer showOrder;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
