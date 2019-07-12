package com.cw.biz.integral.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户积分管理
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class IntegralDto extends BaseDto {
    private Long id;

    private Long userId;

    private Long  integralType;

    private BigDecimal integral=BigDecimal.ZERO;

    private Date lastExchangeDate;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
