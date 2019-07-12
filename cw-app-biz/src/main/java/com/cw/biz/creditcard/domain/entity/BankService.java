package com.cw.biz.creditcard.domain.entity;

import com.cw.biz.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 银行卡服务信息
 * Created by dujiangyu on 2017/6/21.
 */
@Entity
@Table(name="cw_bank_service")
@Getter
@Setter
public class BankService extends BaseEntity {

    @Column(name="name",columnDefinition="varchar(100) not null comment '服务描述'")
    private String name;

    @Column(name="bank_id",columnDefinition="varchar(100) not null comment '银行编号'")
    private Long bankId;

    @Column(name="service_memo",columnDefinition="varchar(100) not null comment '服务号码或描述'")
    private String serviceMemo;

    @Column(name="service_type",columnDefinition="varchar(100) not null comment '服务类型(邮箱/电话)'")
    private String serviceType;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
