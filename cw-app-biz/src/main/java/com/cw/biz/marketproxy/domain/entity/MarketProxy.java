package com.cw.biz.marketproxy.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 市场人员提成价格表
 * Created by dujiangyu on 2017/6/21.
 */
@Entity
@Table(name="cw_market_proxy_price")
@Getter
@Setter
public class MarketProxy extends AggEntity {

    @Column(name="register_num",columnDefinition="int(11) not null comment '注册量'")
    private Integer registerNum;

    @Column(name="price",columnDefinition="decimal(11,2) not null comment '产品价格'")
    private BigDecimal price=BigDecimal.ZERO;

    @Column(name="percent",columnDefinition="decimal(11,2) comment '提成比例'")
    private BigDecimal percent=BigDecimal.ZERO;

    @Column(name="amount",columnDefinition="decimal(11,2) not null comment '提成金额'")
    private BigDecimal amount=BigDecimal.ZERO;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
