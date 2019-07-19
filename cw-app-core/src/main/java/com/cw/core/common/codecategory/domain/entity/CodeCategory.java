package com.cw.core.common.codecategory.domain.entity;

import com.cw.core.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * @Title: CodeCategory.java
 * @Description: 下拉框树父级大类
 * @Author: Away
 * @Date: 2018/4/23 16:48
 * @Copyright: 重庆平迅数据服务有限公司
 * @Version: V1.0
 */
@Entity
@Table(name="ess_codecategory")
@org.hibernate.annotations.Table(appliesTo = "ess_codecategory",comment = "系统基本组件-下拉框值数据字典树")
@Getter
@Setter
public class CodeCategory extends BaseEntity{

    @Column(name="categoryCd",columnDefinition="varchar(100) unique comment '所属大类编号'")
    private String categoryCd;

    @Column(name="categoryNm",columnDefinition="varchar(100)  comment '所属大类名称'")
    private String categoryNm;

    @Column(name="description",columnDefinition="varchar(100)  comment '大类描述'")
    private String description;

    @Column(columnDefinition = " tinyint default 0  COMMENT '是否为公共的0是,1否' ")
    private boolean isCommon = true;

    @Column(columnDefinition = " tinyint default 0  COMMENT '是否可见 0可见,1不可见' ")
    private boolean isEnabled = true;

    @Column(columnDefinition = " tinyint default 1  COMMENT '是否为树形结构 0是,1不是' ")
    private boolean isTree = false;

}
