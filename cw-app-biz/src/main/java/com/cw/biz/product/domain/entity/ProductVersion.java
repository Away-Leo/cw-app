package com.cw.biz.product.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 金融产品
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_product_version")
@Getter
@Setter
public class ProductVersion extends AggEntity{

    @Column(name="data_version",columnDefinition="int(11) not null comment '产品数据更新版本'")
    private Integer dataVersion;

    @Column(name="channel_no",columnDefinition="varchar(50) not null comment '渠道编号'")
    private String channelNo;

    @Column(name="apk_url",columnDefinition="varchar(50) not null comment '更新apk下载url'")
    private String apkUrl;

    @Column(name="memo",columnDefinition="varchar(200) not null comment '更新日志'")
    private String memo;

    @Column(name="is_audit",columnDefinition="tinyint(1) comment '是否审核'")
    private Boolean isAudit= Boolean.FALSE;

}
