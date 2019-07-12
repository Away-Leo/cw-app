package com.cw.biz.product.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 金融产品审核版本号
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_audit_version",indexes = {@Index(unique = true, name = "cw_audit_version_unique_idx", columnList = "data_version")})
@Getter
@Setter
public class ProductAuditVersion extends AggEntity{

    @Column(name="data_version",columnDefinition="varchar(20) not null comment 'app版本号'")
    private String dataVersion;

    @Column(name="is_audit",columnDefinition="tinyint(1) not null comment '是否审核通过'")
    private Boolean isAudit;

    @Column(name="app_name",columnDefinition="varchar(20) comment '所属APP'")
    private String appName;

    @Column(name="app_copy_right",columnDefinition="varchar(100)  comment '版权'")
    private String appCopyRight;

    @Column(name="app_about_us",columnDefinition="varchar(100)  comment '关于我们'")
    private String appAboutUs;

    @Column(name="apple_id",columnDefinition="varchar(100)  comment '苹果ID'")
    private String appleId;

}
