package com.cw.biz.report.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 市场日报
 * Created by Administrator on 2017/9/26.
 */
@Getter
@Setter
public class MarketDailyDto extends BaseDto {

    private Long id;

    private String dayId;

    private String enterpriseName;

    private Long productId;
    private String productName;

    private String cooperationModel;

    private Integer applyTime=0;

    private Integer applyNum=0;

    private BigDecimal priceA=BigDecimal.ZERO;

    private BigDecimal priceS=BigDecimal.ZERO;

    private Integer applyNuma=0;

    private Integer applyNums=0;

    private BigDecimal loanAmount=BigDecimal.ZERO;

    private BigDecimal incomeA=BigDecimal.ZERO;

    private BigDecimal incomeS=BigDecimal.ZERO;

    private BigDecimal totalIncome;

    private Date importDate;

    private String applyStartDate;

    private String startDate;

    private String endDate;

    private Integer status;

    private BigDecimal thousandAIncome=BigDecimal.ZERO;

    private BigDecimal thousandSIncome=BigDecimal.ZERO;

    private BigDecimal thousandIncome=BigDecimal.ZERO;

    private BigDecimal thousandCost=BigDecimal.ZERO;

    private BigDecimal applyNumaRate;

    private BigDecimal applyNumsRate;

    private BigDecimal invoiceFee;

    private String priceDate;//填写回盘价格数据日期

    private String sortDesc;

    private String invoiceLinkAddress;

    private String invoiceLinkPhone;

    private String express;

    private String postNo;

    private String postDate;

    private Integer isBilling;

    private BigDecimal confirmSettleFee=BigDecimal.ZERO;

    private BigDecimal incomeFee=BigDecimal.ZERO;

    private String incomeMemo;

    private Date backFeeDate;

    private String settleDate;
    //结算单号
    private String settleNo;
    //结算周期
    private String settleCycle;
    //客服经理
    private String operateName;

    private String settleMemo;

    private String settleChannel;
    //CPA转化率
    private BigDecimal convertCpa;
    //CPS转化率
    private BigDecimal convertCps;


}
