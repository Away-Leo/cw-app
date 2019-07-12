package com.cw.biz.parameter.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 积分墙用户点击记录表
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class IntegralWallLogDto extends BaseDto{

    private String deviceNo;

    private String code;

    private String encryptStr;

    private String appName;

}
