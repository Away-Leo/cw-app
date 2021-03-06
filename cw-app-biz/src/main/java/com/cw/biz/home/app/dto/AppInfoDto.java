package com.cw.biz.home.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * APP管理
 * Created by Administrator on 2017/7/28.
 */
@Getter
@Setter
public class AppInfoDto extends BaseDto {

    private String code;

    private String name;

    private String smsContent;

    private String registerMessage;

    private Boolean isValid=Boolean.TRUE;

    private String orgName;

    private String masterSecret;

    private String appKey;

    private String templateid;
}