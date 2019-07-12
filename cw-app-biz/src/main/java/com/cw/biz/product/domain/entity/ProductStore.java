package com.cw.biz.product.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 产品门店
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_product_store")
@Getter
@Setter
public class ProductStore extends AggEntity{

    @Column(name="store_name",columnDefinition="varchar(100) not null comment '门店名称'")
    private String storeName;

    @Column(name="store_address",columnDefinition="varchar(500) not null comment '门店地址'")
    private String storeAddress;

    @Column(name="store_img",columnDefinition="varchar(100) not null comment '线下门店图片'")
    private String storeImg;

    @Column(name="tel",columnDefinition="varchar(100) not null comment '联系电话'")
    private String tel;

    @Column(name="memo",columnDefinition="varchar(200) not null comment '备注'")
    private String memo;

    @Column(name="longitude",columnDefinition="varchar(20) not null comment '经度'")
    private String longitude;

    @Column(name="latitude",columnDefinition="varchar(20) not null comment '纬度'")
    private String latitude;

    @Column(name="app_name",columnDefinition="varchar(20) comment 'app标识'")
    private String appName;

}
