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
 * 市场日报
 * Created by Administrator on 2017/9/26.
 */
@Entity
@Table(name="cw_report_market_daily")
@Getter
@Setter
public class MarketDaily extends AggEntity {

    @Column(name="day_id",columnDefinition="varchar(20) not null comment '日期'")
    private String dayId;

    @Column(name="enterprise_name",columnDefinition="varchar(100) comment '公司名称'")
    private String enterpriseName;

    @Column(name="product_id",columnDefinition="int(11) comment '产品id'")
    private Long productId;

    @Column(name="product_name",columnDefinition="varchar(500) comment '产品名称'")
    private String productName;

    @Column(name="cooperation_model",columnDefinition="varchar(20) comment '合作模式'")
    private String cooperationModel;

    @Column(name="apply_time",columnDefinition="int(11) comment '申请次数'")
    private Integer applyTime;

    @Column(name="apply_num",columnDefinition="int(11) comment '申请用户数'")
    private Integer applyNum;

    @Column(name="price_a",columnDefinition="decimal(11,2) comment 'cpa合作单价'")
    private BigDecimal priceA;

    @Column(name="price_s",columnDefinition="decimal(11,2) comment 'cps合作单价'")
    private BigDecimal priceS;

    @Column(name="apply_numa",columnDefinition="int(11) comment 'A申请用户数'")
    private Integer applyNuma;

    @Column(name="apply_nums",columnDefinition="int(11) comment 'S申请用户数'")
    private Integer applyNums;

    @Column(name="loan_amount",columnDefinition="decimal(11,2) comment 'S申请用户数'")
    private BigDecimal loanAmount;

    @Column(name="income_a",columnDefinition="decimal(11,2) comment 'CPA收入'")
    private BigDecimal incomeA;

    @Column(name="income_s",columnDefinition="decimal(11,2) comment 'CPS收入'")
    private BigDecimal incomeS;

    @Column(name="total_income",columnDefinition="decimal(11,2) comment '总收入'")
    private BigDecimal totalIncome;

    @Column(name="import_date",columnDefinition="datetime comment '入库时间'")
    private Date importDate;

    @Column(name="operate_no",columnDefinition="int(11) comment '操作人员'")
    private Long operateNo;

    @Column(name="status",columnDefinition="int(11) comment '操作状态：0:录入回盘,1:对账确认,2复核，3财务开票,4已邮寄，5：已回款'")
    private Integer status=0;

    @Column(name="is_billing",columnDefinition="int(11) comment '操作状态：0:开票,1:不开票'")
    private Integer isBilling=0;

    @Column(name="cost_fee",columnDefinition="decimal(11,2) comment '当月成本'")
    private BigDecimal cost=BigDecimal.ZERO;

    @Column(name="express",columnDefinition="varchar(20) comment '快递公司'")
    private String express;

    @Column(name="post_no",columnDefinition="varchar(20) comment '运单号'")
    private String postNo;

    @Column(name="post_date",columnDefinition="datetime comment '发送时间'")
    private Date postDate;

    @Column(name="confirm_settle_fee",columnDefinition="decimal(11,2) comment '确认结算金额'")
    private BigDecimal confirmSettleFee=BigDecimal.ZERO;

    @Column(name="income_fee",columnDefinition="decimal(11,2) comment '回款金额'")
    private BigDecimal incomeFee=BigDecimal.ZERO;

    @Column(name="income_memo",columnDefinition="varchar(200) comment '回款说明'")
    private String incomeMemo;

    @Column(name="settle_memo",columnDefinition="varchar(200) comment '结算说明备注'")
    private String settleMemo;

    @Column(name="settle_channel",columnDefinition="varchar(200) comment '结算渠道'")
    private String settleChannel;

    @Column(name="back_fee_date",columnDefinition="datetime comment '回款时间'")
    private Date backFeeDate;

    @Column(name="settle_no",columnDefinition="varchar(20) comment '结算单号'")
    private String settleNo;
    /**
     *  保存数据验证
     */
    public void prepareSave()
    {

    }
}
