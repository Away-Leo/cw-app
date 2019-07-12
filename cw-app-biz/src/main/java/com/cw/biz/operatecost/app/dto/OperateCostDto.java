package com.cw.biz.operatecost.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 运营成本
 * Created by dujiangyu on 2017/6/21.
 */
@Getter
@Setter
public class OperateCostDto extends PageDto {

    private Long id;

    private String dayId;

    private String costType;

    private String feeItem;

    private String channel;

    private String appName;

    private BigDecimal costFee=BigDecimal.ZERO;

    private Long operateNo;

    private Boolean isValid=Boolean.TRUE;

}
