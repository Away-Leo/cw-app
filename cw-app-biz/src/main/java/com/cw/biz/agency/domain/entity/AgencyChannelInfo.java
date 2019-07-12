package com.cw.biz.agency.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="cw_agency_channel")
@Getter
@Setter
public class AgencyChannelInfo extends AggEntity {

    @Column(name="channel_id",columnDefinition="int(11) comment '渠道ID，一个渠道可代理多个产品'")
    private Long channelId;

    @Column(name="manager_id",columnDefinition="int(11) comment '业务负责人ID'")
    private Long managerId;

    @Column(name="name",columnDefinition="varchar(50) comment '渠道名称'")
    private String name;

    @Column(name="phone",columnDefinition="varchar(11) comment '手机号码'")
    private String phone;

    @Column(name="card_no",columnDefinition="varchar(18) comment '身份证号码'")
    private String cardNo;

    @Column(name="product_id",columnDefinition="int(11) not null comment '产品ID'")
    private Long productId;

    @Column(name="url",columnDefinition="varchar(200) comment '产品链接'")
    private String url;

    @Column(name="admin_url",columnDefinition="varchar(200) comment '产品后台'")
    private String adminUrl;

    @Column(name="account_name",columnDefinition="varchar(18) comment '对应产品后台账号'")
    private String accountName;

    @Column(name="account_pwd",columnDefinition="varchar(18) comment '对应产品后台密码'")
    private String accountPwd;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    @Column(name="online_date",columnDefinition="datetime comment '上下线时间'")
    private Date onlineDate;

    @Column(name="settle_cycle",columnDefinition="tinyint(1) comment '结算方式：1：日结，2：周结，3：月结，4：预付'")
    private Integer settleCycle;

    @Column(name="prepare_amount",columnDefinition="decimal(7,2) comment '预付金额'")
    private BigDecimal prepareAmount;

    //cascade:表的级联操作
    @OneToOne //JPA注释： 一对一 关系
    //referencedColumnName：参考列名,默认的情况下是列表的主键
    //nullable=是否可以为空，
    //insertable：是否可以插入，
    //updatable：是否可以更新
    // columnDefinition=列定义，
    //foreignKey=外键
    @JoinColumn(name="product_id", referencedColumnName = "id", insertable = false, updatable = false, foreignKey=@ForeignKey(name = "none",value = ConstraintMode.NO_CONSTRAINT))
    private AgencyProductInfo productInfo;
}
