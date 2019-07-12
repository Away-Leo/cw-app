package com.cw.biz.creditcard.app.dto;

import com.cw.biz.common.dto.PageDto;
import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 信用卡
 * Created by dujiangyu on 2017/6/21.
 */
@Getter
@Setter
public class CreditCardDto extends PageDto{
    private Long id;

    private String name;

    private String bankNumber;

    private String bankName;

    private String img;

    private String recommendImg;

    private Boolean isRecommend=Boolean.FALSE;

    private String cardDesc;

    private String url;

    private Integer showOrder;

    private Integer clickNum=0;

    private Boolean isValid=Boolean.TRUE;

    private Boolean androidFlag=Boolean.TRUE;

    private Boolean appleFlag=Boolean.TRUE;

    private Boolean wechatFlag=Boolean.TRUE;

    private String accountName;

    private String openBankName;

    private String bankAccount;

    private String linkAddress;

    private String linkPhone;

    private Integer invoiceType;

    private String invoiceItem;

    private String taxesObject;

    private String taxpayerNo;

    private String invoiceLinkAddress;

    private String invoiceLinkPhone;

    private String invoiceMemo;

    private Long operateNo;

    private Integer applyTime;//申请次数

    private Integer applyNum;//申请用户

}
