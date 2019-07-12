package com.cw.biz.parameter.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 首页配置信息
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class IndexParameterDto extends BaseDto{

    //开始金额
    private BigDecimal startAmount;
    //结束金额
    private BigDecimal endAmount;

    private Integer startPeriodDay;

    private Integer endPeriodDay;

    private Integer startPeriodMonth;

    private Integer endPeriodMonth;

    private BigDecimal loanRate;

    private String suspendFlag;

    private String messageTip;

    private BigDecimal operateCost=BigDecimal.ZERO;
}
