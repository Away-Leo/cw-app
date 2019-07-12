package com.cw.biz.integral.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户积分记录
 * Created by Administrator on 2017/11/20.
 */
@Setter
@Getter
public class IntegralLogDto extends BaseDto {
    private Long id;

    private Long userId;

    private String applyStartDate;

    private String applyEndDate;

    private String phone;

    private BigDecimal integral=BigDecimal.ZERO;

    private Long integralType;

    private Long giftType;

    private Long giftId;

    private String activeId;

    private String activeName;

    private String name;

    private String exchangeDate;

    private Date applyDate;

    private BigDecimal exchangeIntegral=BigDecimal.ZERO;

    private String memo;

    private BigDecimal registerNoApply=BigDecimal.ZERO;

    private BigDecimal registerApply=BigDecimal.ZERO;

    private BigDecimal loanUser=BigDecimal.ZERO;

    private BigDecimal totalIntegral=BigDecimal.ZERO;

    private String activeDate;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
