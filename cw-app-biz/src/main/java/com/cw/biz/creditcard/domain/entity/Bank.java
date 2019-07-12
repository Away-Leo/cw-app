package com.cw.biz.creditcard.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 银行卡信息
 * Created by dujiangyu on 2017/6/21.
 */
@Entity
@Table(name="cw_bank_service")
@Getter
@Setter
public class Bank extends AggEntity {

    @Column(name="name",columnDefinition="varchar(100) not null comment '银行名称'")
    private String name;

    @Column(name="icon",columnDefinition="varchar(100)  comment '银行图标'")
    private String icon;

    @Column(name="url",columnDefinition="varchar(100) comment '银行跳转链接'")
    private String url;

    @Column(name="is_valid",columnDefinition="tinyint(1)  comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    @Column(name="is_recommend",columnDefinition="tinyint(1)  comment '是否有效'")
    private Boolean isRecommend=Boolean.FALSE;

    @Column(name="show_order",columnDefinition="int(11)  comment '排序'")
    private Integer showOrder;

    @Column(name="click_num",columnDefinition="int(11)  comment '申请人数'")
    private Integer clickNum;

    @Column(name="memo",columnDefinition="varchar(100)  comment '备注'")
    private String memo;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
