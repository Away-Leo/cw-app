package com.cw.biz.creditcard.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 信用卡
 * Created by dujiangyu on 2017/6/21.
 */
@Entity
@Table(name="cw_credit_card")
@Getter
@Setter
public class CreditCard extends AggEntity{

    @Column(name="name",columnDefinition="varchar(100) not null comment '名称'")
    private String name;

    @Column(name="bank_number",columnDefinition="varchar(100) not null comment '银行编号'")
    private String bankNumber;

    @Column(name="bank_name",columnDefinition="varchar(100) not null comment '名称'")
    private String bankName;

    @Column(name="img",columnDefinition="varchar(100) not null comment '信用卡图片'")
    private String img;

    @Column(name="recommend_img",columnDefinition="varchar(100) not null comment '信用卡图片'")
    private String recommendImg;

    @Column(name="is_recommend",columnDefinition="tinyint(1) not null comment '是否推荐'")
    private Boolean isRecommend=Boolean.FALSE;

    @Column(name="card_desc",columnDefinition="varchar(100) not null comment '信用卡描述'")
    private String cardDesc;

    @Column(name="url",columnDefinition="varchar(500) comment '客户app跳转地址'")
    private String url;

    @Column(name="show_order",columnDefinition="int(11) comment '排序'")
    private Integer showOrder;

    @Column(name="click_num",columnDefinition="int(11)  default 0 comment '点击率'")
    private Integer clickNum=0;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    @Column(name="is_android",columnDefinition="tinyint(1) not null comment '安卓是否上架'")
    private Boolean androidFlag=Boolean.TRUE;

    @Column(name="is_apple",columnDefinition="tinyint(1) not null comment '苹果是否上架'")
    private Boolean appleFlag=Boolean.TRUE;

    @Column(name="is_wechat",columnDefinition="tinyint(1) not null comment '微信是否上架'")
    private Boolean wechatFlag=Boolean.TRUE;

    @Column(name="account_name",columnDefinition="varchar(200) comment '开户名称'")
    private String accountName;

    @Column(name="open_bank_name",columnDefinition="varchar(200) comment '开户银行'")
    private String openBankName;

    @Column(name="bank_account",columnDefinition="varchar(50) comment '银行账号'")
    private String bankAccount;

    @Column(name="link_address",columnDefinition="varchar(500) comment '联系地址'")
    private String linkAddress;

    @Column(name="link_phone",columnDefinition="varchar(50) comment '联系电话'")
    private String linkPhone;

    @Column(name="invoice_type",columnDefinition="int(11) comment '发票类型'")
    private Integer invoiceType;

    @Column(name="invoice_item",columnDefinition="varchar(50) comment '发票名目'")
    private String invoiceItem;

    @Column(name="taxes_object",columnDefinition="varchar(50) comment '税金支付方'")
    private String taxesObject;

    @Column(name="taxpayer_no",columnDefinition="varchar(50) comment '纳税人识别号'")
    private String taxpayerNo;

    @Column(name="invoice_link_address",columnDefinition="varchar(200) comment '发票邮寄地址'")
    private String invoiceLinkAddress;

    @Column(name="invoice_link_phone",columnDefinition="varchar(20) comment '发票收件人联系电话'")
    private String invoiceLinkPhone;

    @Column(name="invoice_memo",columnDefinition="varchar(200) comment '开票备注信息'")
    private String invoiceMemo;

    @Column(name="operate_no",columnDefinition="int(11) comment '操作人员'")
    private Long operateNo;
    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
