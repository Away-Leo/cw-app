package com.cw.biz.home.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统模块
 * Created by Administrator on 2017/7/28.
 */
@Entity
@Table(name="cw_module")
@Getter
@Setter
public class Module extends AggEntity {

    @Column(name="name",columnDefinition="varchar(100) not null comment '模块名称'")
    private String name;

    @Column(name="remark",columnDefinition="varchar(100)  comment '备注'")
    private String remark;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}