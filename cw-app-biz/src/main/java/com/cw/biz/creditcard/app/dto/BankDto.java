package com.cw.biz.creditcard.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 银行卡信息
 * Created by dujiangyu on 2017/6/21.
 */
@Getter
@Setter
public class BankDto extends BaseDto {

    private Long id;

    private String name;

    private String icon;

    private String url;

    private Boolean isValid=Boolean.TRUE;

    private Boolean isRecommend=Boolean.FALSE;

    private Integer showOrder;

    private Integer clickNum;

    private String memo;

}
