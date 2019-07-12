package com.cw.biz.report.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 代理日报
 * Created by Administrator on 2017/9/26.
 */
@Entity
@Table(name="cw_report_agency_daily")
@Getter
@Setter
public class AgencyDaily extends AggEntity {

    @Column(name="day_id",columnDefinition="varchar(20) not null comment '日期'")
    private String dayId;

    @Column(name="product_id",columnDefinition="int(11) comment '产品id'")
    private Long productId;

    @Column(name="product_name",columnDefinition="varchar(500) comment '产品名称'")
    private String productName;

    @Column(name="channel_id",columnDefinition="int(11) comment '渠道id'")
    private Long channelId;

    @Column(name="channel_name",columnDefinition="varchar(100) comment '渠道名称'")
    private String channelName;

    @Column(name="in_price",columnDefinition="decimal(11,2) comment '接入单价'")
    private BigDecimal inPrice;

    @Column(name="out_price",columnDefinition="decimal(11,2) comment '接出单价'")
    private BigDecimal outPrice;

    @Column(name="apply_num",columnDefinition="int(11) comment '注册量'")
    private BigDecimal applyNum;

    @Column(name="cost",columnDefinition="decimal(11,2) comment '产生费用'")
    private BigDecimal cost;

    @Column(name="profit",columnDefinition="decimal(11,2) comment '单条利润'")
    private BigDecimal profit;

    @Column(name="profit_count",columnDefinition="decimal(11,2) comment '总利润'")
    private BigDecimal profitCount;

    @Column(name="money_info",columnDefinition="varchar(50) comment '打款信息'")
    private BigDecimal moneyInfo;

    @Column(name="prepare_amount",columnDefinition="decimal(9,2) comment '预付金额'")
    private BigDecimal prepareAmount;

    @Column(name="prepare_amount_balance",columnDefinition="decimal(9,2) comment '结余预付'")
    private BigDecimal prepareAmountBalance;

    @Column(name="is_settle",columnDefinition="int(1) comment '是否结算'")
    private Boolean isSettle=Boolean.FALSE;

    /**
     *  保存数据验证
     */
    public void prepareSave()
    {

    }
}
