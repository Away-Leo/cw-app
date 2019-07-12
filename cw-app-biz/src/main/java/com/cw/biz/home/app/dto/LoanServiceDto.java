package com.cw.biz.home.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 贷款统计数据
 * Created by Administrator on 2017/7/28.
 */
@Getter
@Setter
public class LoanServiceDto extends BaseDto {

    private Long id;

    private Integer serviceUserNum;

    private BigDecimal loanTotalAmount;

    private BigDecimal successRate=BigDecimal.ZERO;

}