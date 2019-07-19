package com.cw.core.common.codecategory.domain.entity;

import com.cw.core.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @Title: SimpleCode.java
 * @Description: 系统基本组件-下拉框数据字典
 * @Author: Away
 * @Date: 2018/4/23 17:44
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
@Entity
@Table(name="ess_simplecode")
@org.hibernate.annotations.Table(appliesTo = "ess_simplecode",comment = "系统基本组件-下拉框值数据字典")
@Getter
@Setter
public class SimpleCode extends BaseEntity{

    @Column(columnDefinition = " tinyint default 1  COMMENT '是否为树形结构 0是,1不是' ")
    private boolean isTree = false;

    @Column(name="code",columnDefinition="varchar(100) unique  comment '选项编号'")
    private String code;

    @Column(name="name",columnDefinition="varchar(100) unique  comment '选项名称'")
    private String name;

    @Column(name="fullCode",columnDefinition="varchar(100) unique  comment '选项全编号'")
    private String fullCode;

    @Column(name="description",columnDefinition="varchar(100) unique  comment '选项描述'")
    private String description;

    @Column(name="dispOrder",columnDefinition="int(10)  comment '显示顺序'")
    private Integer dispOrder;

    @Column(columnDefinition = " tinyint default 1  COMMENT '是否为默认0是,1否' ")
    private boolean isDefault = false;

    @Column(columnDefinition = " tinyint default 1  COMMENT '是否为固定的 0是,1否' ")
    private boolean isFixed = false;

    @Column(name = "categoryCd", columnDefinition = "varchar(100) COMMENT '所属大类编号'")
    protected String categoryCd;





}
