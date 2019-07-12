package com.cw.biz.home.app.dto;

import com.cw.biz.common.dto.BaseDto;
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
@Getter
@Setter
public class AppModuleDto extends BaseDto {

    private String appName;

    private String moduleIds;

    private Boolean isValid=Boolean.TRUE;

}