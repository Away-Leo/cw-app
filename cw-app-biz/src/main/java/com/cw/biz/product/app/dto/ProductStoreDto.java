package com.cw.biz.product.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 产品门店
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class ProductStoreDto extends BaseDto{

    private Long id;

    private String storeName;

    private String storeAddress;

    private String storeImg;

    private String memo;

    private String tel;

    private String longitude;

    private String latitude;

    private String appName;
}
