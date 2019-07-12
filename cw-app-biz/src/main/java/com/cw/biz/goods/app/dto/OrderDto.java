package com.cw.biz.goods.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单表
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class OrderDto extends PageDto {

    private Long id;

    private Long userId;

    private Long goodsId;

    private Date orderDate;

    private BigDecimal price;

    private Integer period;

    private Integer emailAddress;

    private Integer status;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
