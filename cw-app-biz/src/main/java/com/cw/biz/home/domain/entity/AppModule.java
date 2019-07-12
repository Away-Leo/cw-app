package com.cw.biz.home.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * APP所属模块
 * Created by Administrator on 2017/7/28.
 */
@Entity
@Table(name="cw_module_app")
@Getter
@Setter
public class AppModule extends AggEntity {

    @Column(name="app_name",columnDefinition="varchar(100) not null comment '应用名称'")
    private String appName;

    @Column(name="module_ids",columnDefinition="varchar(200)  comment 'app对应的模块组合'")
    private String moduleIds;

    @Column(name="is_valid",columnDefinition="tinyint(1) not null comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}