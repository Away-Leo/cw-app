package com.cw.biz.user.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import com.cw.biz.user.app.enums.EducationEnum;
import com.cw.biz.user.app.enums.IdentityEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信息
 * Created by dujy on 2017/7/31.
 */
@Entity
@Table(name="cw_user_info")
@Getter
@Setter
public class CwUserInfo extends AggEntity{

    @Column(name="user_id",columnDefinition="varchar(100) not null comment '用户ID'")
    private Long userId;

    @Column(name="name",columnDefinition="varchar(100)  comment '姓名'")
    private String name;

    @Column(name="cert_no",columnDefinition="varchar(20)  comment '证件号码'")
    private String certNo;

    @Column(name="batch_no",columnDefinition="varchar(32)  comment '导出批次数据'")
    private String batchNo;

    @Column(name="city",columnDefinition="varchar(50)  comment '现居地'")
    private String city;

    @Column(name="address",columnDefinition="varchar(100)  comment '联系地址'")
    private String address;

    @Column(name="sex",columnDefinition="varchar(100)  comment '性别'")
    private String sex;

    @Column(name="birthday",columnDefinition="datetime  comment '出生年月'")
    private Date birthday;

    @Column(name="age",columnDefinition="int(11) comment '年龄'")
    private Integer age;

    @Column(name="domicile_place",columnDefinition="varchar(100)  comment '户籍'")
    private String domicilePlace;

    @Column(name="education",columnDefinition="varchar(100)  comment '教育程度'")
    private String education;

    @Column(name="social_identity",columnDefinition="varchar(100)  comment '社会身份'")
    private String socialIdentity;

    @Column(name="income",columnDefinition="decimal(12,2)  comment '收入'")
    private BigDecimal income=BigDecimal.ZERO;

    @Column(name="is_social",columnDefinition="int(1)  comment '有无社保'")
    private Boolean isSocial=Boolean.FALSE;

    @Column(name="is_fund",columnDefinition="tinyint(1)  comment '有无公积金'")
    private Boolean isFund=Boolean.FALSE;

    @Column(name="is_creditCard",columnDefinition="tinyint(1)  comment '有无信用卡'")
    private Boolean isCreditCard=Boolean.FALSE;

    @Column(name="is_house_or_car_loan",columnDefinition="tinyint(1)  comment '有无房贷车贷'")
    private Boolean isHouseOrCarLoan=Boolean.FALSE;

    @Column(name="is_car_loan",columnDefinition="tinyint(1)  comment '有无车贷'")
    private Boolean isCarLoan=Boolean.FALSE;

    @Column(name="wechat_amount",columnDefinition="decimal(12,2)  comment '微粒贷额度'")
    private BigDecimal wechatAmount=BigDecimal.ZERO;

    @Column(name="sesame_credit",columnDefinition="decimal(12,2)  comment '芝麻信用额度'")
    private BigDecimal sesameCredit=BigDecimal.ZERO;


    @Column(name="id_card_face",columnDefinition="varchar(200)  comment '身份证正面照'")
    private String idCardFace;

    @Column(name="id_card_back",columnDefinition="varchar(200)  comment '身份证反面照'")
    private String idCardBack;
}
