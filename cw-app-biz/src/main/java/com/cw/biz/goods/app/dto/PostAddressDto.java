package com.cw.biz.goods.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商品邮寄地址
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class PostAddressDto extends BaseDto {

    private Long id;

    private Long userId;

    private String name;

    private String phone;

    private String address;

    private Boolean isDefault;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
