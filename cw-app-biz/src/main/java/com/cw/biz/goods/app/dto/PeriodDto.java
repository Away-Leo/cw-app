package com.cw.biz.goods.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 商品分期表
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class PeriodDto extends BaseDto {

    private Long id;

    private Long goodsId;

    private Integer period;

    private BigDecimal price;

    private BigDecimal rate;

    private String periodMemo;

    private Integer showOrder;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
