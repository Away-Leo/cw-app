package com.cw.biz.discount.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import com.cw.core.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
* @Title: WholeDisCount.java
* @Description: 扣量全局设置
* @author Away
* @date 2019/7/27 18:29
* @copyright 重庆平讯数据
* @version V1.0
*/
@Entity
@Table(name="cw_discount_whole")
@org.hibernate.annotations.Table(appliesTo = "cw_discount_whole",comment = "扣量全局设置")
@Getter
@Setter
public class WholeDisCount extends BaseEntity {

    @Column(name="start_num",columnDefinition="int(11)  comment '起始扣量数'")
    private Integer startNum;

    @Column(name="percent",columnDefinition="int(11)  comment '扣量比例'")
    private Integer percent;
}
