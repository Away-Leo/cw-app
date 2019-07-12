package com.cw.biz.product.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品合作价格
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_product_cooperative_price")
@Getter
@Setter
public class ProductCooperativePrice extends AggEntity{

    @Column(name="product_id",columnDefinition="int(11) not null comment '产品id'")
    private Long productId;

    @Column(name="cooperation_model",columnDefinition="varchar(20) comment '合作模式'")
    private String cooperationModel;

    @Column(name="start_num",columnDefinition="int(11) comment '区间起始值'")
    private Integer startNum;

    @Column(name="end_num",columnDefinition="int(11) comment '区间结束值'")
    private Integer endNum;

    @Column(name="price_type",columnDefinition="varchar(20) comment '价格类型：fix:固定值，percent:放款百分比'")
    private String priceType;

    @Column(name="price",columnDefinition="decimal(11,2) comment '价格'")
    private BigDecimal price=BigDecimal.ZERO;

    @Column(name="start_cycle",columnDefinition="datetime comment '计费开始时间'")
    private Date startCycle;

    @Column(name="end_cycle",columnDefinition="datetime comment '计费结束时间'")
    private Date endCycle;

}
