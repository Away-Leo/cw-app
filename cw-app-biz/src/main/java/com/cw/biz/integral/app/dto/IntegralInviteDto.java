package com.cw.biz.integral.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 成功邀请人员明细
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class IntegralInviteDto extends BaseDto {

    private Long id;

    private Long userId;

    private String phone;

    private BigDecimal loanAmount=BigDecimal.ZERO;

    private Date loanDate;

}
