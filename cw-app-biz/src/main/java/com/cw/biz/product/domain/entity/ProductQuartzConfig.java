package com.cw.biz.product.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 产品定时任务配置
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_product_quartz_config")
@Getter
@Setter
public class ProductQuartzConfig extends AggEntity{

    @Column(name="online_date",columnDefinition="datetime comment '上架时间'")
    private Date onlineDate;

    @Column(name="offline_date",columnDefinition="datetime comment '下架时间'")
    private Date offlineDate;

    @Column(name="product_id",columnDefinition="int(11) not null comment '产品id'")
    private Long productId;

    @Column(name="memo",columnDefinition="varchar(200) not null comment '更新日志'")
    private String memo;

}
