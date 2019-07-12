package com.cw.biz.operatecost.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 每日运营成本
 * Created by dujiangyu on 2017/6/21.
 */
@Entity
@Table(name="cw_operate_cost")
@Getter
@Setter
public class OperateCost extends AggEntity {

    @Column(name="day_id",columnDefinition="varchar(10) comment '产生时间'")
    private String dayId;

    @Column(name="cost_type",columnDefinition="varchar(200) comment '成本类型:充值，消耗'")
    private String costType;

    @Column(name="fee_item",columnDefinition="varchar(200) comment '消耗费用类型'")
    private String feeItem;

    @Column(name="channel",columnDefinition="varchar(200) comment '渠道'")
    private String channel;

    @Column(name="app_name",columnDefinition="varchar(200) comment '产品名称'")
    private String appName;

    @Column(name="cost_fee",columnDefinition="decimal(11,2) comment '消耗成本'")
    private BigDecimal costFee=BigDecimal.ZERO;

    @Column(name="operate_no",columnDefinition="int(11)  comment '录入人员'")
    private Long operateNo;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
