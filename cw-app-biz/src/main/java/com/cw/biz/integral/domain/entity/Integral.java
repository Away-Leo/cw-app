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
 * 用户积分管理
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_user_integral")
@Setter
@Getter
public class Integral extends AggEntity {

    @Column(name="user_id",columnDefinition="int(11) not null comment '用户编码'")
    private Long userId;

    @Column(name="integral",columnDefinition="decimal(20,2) default 0 comment '用户积分'")
    private BigDecimal integral=BigDecimal.ZERO;

    @Column(name="last_exchange_date",columnDefinition="datetime comment '最后兑换时间'")
    private Date lastExchangeDate;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
