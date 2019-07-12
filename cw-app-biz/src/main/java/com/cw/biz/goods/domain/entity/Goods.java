package com.cw.biz.goods.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import com.cw.biz.log.app.LogEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品贷
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_goods")
@Getter
@Setter
public class Goods extends AggEntity {

    @Column(name="name",columnDefinition="varchar(200)  comment '产品ID'")
    private String name;

    @Column(name="img",columnDefinition="varchar(200)  comment '产品图片'")
    private String img;

    @Column(name="price",columnDefinition="decimal(11,2) comment '产品价格'")
    private BigDecimal price;

    @Column(name="goods_description",columnDefinition="varchar(2000)  comment '商品介绍'")
    private String goodsDescription;

    @Column(name="goods_code",columnDefinition="varchar(2000)  comment '商品编码'")
    private String goodsCode;

    @Column(name="brand",columnDefinition="varchar(100)  comment '品牌'")
    private String brand;

    @Column(name="model",columnDefinition="varchar(100)  comment '型号'")
    private String model;

    @Column(name="innet_model",columnDefinition="varchar(100)  comment '入网型号'")
    private String innetModel;

    @Column(name="os_type",columnDefinition="varchar(200)  comment '操作系统'")
    private String osType;

    @Column(name="card_type",columnDefinition="varchar(200)  comment '双卡机类型'")
    private String cardType;

    @Column(name="camera_num",columnDefinition="int(11)  comment '摄像头数量'")
    private Integer cameraNum;

    @Column(name="battery_capacity",columnDefinition="varchar(200)  comment '电池容量'")
    private String batteryCapacity;

    @Column(name="is_gps",columnDefinition="varchar(2000)  comment '是否支持GPS'")
    private Boolean isGps;

    @Column(name="common_function",columnDefinition="varchar(200)  comment '通用功能'")
    private String commonFunction;

    @Column(name="customer_service",columnDefinition="varchar(2000)  comment '售后服务'")
    private String customerService;

    @Column(name="apply_num",columnDefinition="int(11)  comment '申请人数'")
    private Integer applyNum;

    @Column(name="status",columnDefinition="int(1)  comment '订单状态(0：上架单，1：下架)'")
    private Integer status;

    @Column(name="show_order",columnDefinition="int(11)  comment '显示顺序'")
    private Integer showOrder;

    @Column(name="is_hot",columnDefinition="tinyint(1)  comment '是否热销'")
    private Integer isHot;

    @Column(name="is_new",columnDefinition="tinyint(1)  comment '是否新品'")
    private Integer isNew;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
