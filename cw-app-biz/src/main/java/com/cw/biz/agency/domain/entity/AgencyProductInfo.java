package com.cw.biz.agency.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="cw_agency_product")
@Getter
@Setter
public class AgencyProductInfo extends AggEntity {

    @Column(name="name",columnDefinition="varchar(50) not null comment '产品名称'")
    private String name;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    @Column(name="online_date",columnDefinition="datetime comment '上下线时间'")
    private Date onlineDate;
}
