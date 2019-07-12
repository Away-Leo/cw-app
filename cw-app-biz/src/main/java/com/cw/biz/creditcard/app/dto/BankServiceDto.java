package com.cw.biz.creditcard.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 银行卡服务信息
 * Created by dujiangyu on 2017/6/21.
 */
@Getter
@Setter
public class BankServiceDto extends BaseDto {

    private Long id;

    private String name;

    private Long bankId;

    private String serviceMemo;

    private String serviceType;

    private Boolean isValid=Boolean.TRUE;

}
