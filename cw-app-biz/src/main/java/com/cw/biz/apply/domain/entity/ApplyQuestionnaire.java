package com.cw.biz.apply.domain.entity;

import com.cw.biz.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 产品申请调查问卷
 * Created by Administrator on 2017/7/24.
 */
@Entity
@Table(name="cw_product_apply_questionnaire")
@Setter
@Getter
public class ApplyQuestionnaire extends BaseEntity{

    @Column(name="product_id",columnDefinition="int(11)  comment '产品ID'")
    private Long productId;

    @Column(name="user_id",columnDefinition="int(11)  comment '用户ID'")
    private Long userId;

    @Column(name="apply_date",columnDefinition="datetime  comment '申请时间'")
    private Date applyDate;

    @Column(name="is_apply",columnDefinition="tinyint(1)  comment '是否成功申请'")
    private Boolean isApply = Boolean.FALSE;
}
