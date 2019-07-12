package com.cw.biz.home.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.core.common.util.AppUtils;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 各App用户数
 * Created by dujiangyu on 2017/6/21.
 */
@Getter
@Setter
public class AppDevDto extends BaseDto {
    private Long id;

    private String appName;

    private Integer devUser=0;

    private Integer loginTime=0;//登录次数

    private Integer loginNum=0;//二次登陆

    private Integer applyTime=0;//申请

    private Integer applyNum=0;//申请

    private String name;

}
