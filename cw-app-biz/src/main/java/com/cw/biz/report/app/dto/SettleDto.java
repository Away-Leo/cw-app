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
public class SettleDto extends BaseDto {
    private String enterpriseName;
    private Long productId;
    private String productName;
    private String cooperationModel;
    private BigDecimal priceA=BigDecimal.ZERO;
    private BigDecimal priceS=BigDecimal.ZERO;
    private Integer applyNuma=0;
    private Integer applyNums=0;
    private BigDecimal loanAmount=BigDecimal.ZERO;
    private String invoiceType;
    private String taxesObject;
    private String invoiceItem;
    private BigDecimal totalIncome;
    private String priceTypeA;
    private String priceTypeS;
    private String accountName;
    private String bankAccount;
    private String openBankName;
    private String linkAddress;
    private String linkPhone;
    private String invoiceLinkAddress;
    private String invoiceLinkPhone;
    private String taxpayerNo;
    private String settleStartDate;
    private String settleNo;
    private String settleEndDate;
    private String invoiceMemo;
    private String acctMonth;
    //结算标识
    private String settleAcctMonth;//保存规则：产品ID+账期
}
