package com.cw.biz.goods.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 商品详情
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class GoodsDto extends PageDto {

    private Long id;

    private String name;

    private String img;

    private BigDecimal price;

    private String goodsDescription;

    private String goodsCode;

    private String brand;

    private String model;

    private String innetModel;

    private String osType;

    private String cardType;

    private Integer cameraNum;

    private String batteryCapacity;

    private Boolean isGps;

    private String commonFunction;

    private String customerService;

    private Integer applyNum;

    private Integer showOrder;

    private Integer status;

    private Integer isHot;

    private Integer isNew;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
