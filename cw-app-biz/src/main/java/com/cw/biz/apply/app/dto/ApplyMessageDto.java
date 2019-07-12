package com.cw.biz.apply.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品申请消息
 * Created by Administrator on 2017/7/24.
 */
@Setter
@Getter
public class ApplyMessageDto extends BaseDto{

    private Long id;

    private Long productId;

    private String name;

    private Long userId;

    private String phone;

    private Date applyDate;

    private BigDecimal applyAmount = BigDecimal.ZERO;
}
