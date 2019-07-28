package com.cw.biz.user.app.dto;

import com.cw.core.common.base.BaseDto;
import com.cw.core.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
public class UserDto extends BaseDto{


    private Long merchantId;

    private String userName;

    private String displayName;

    private String password;

    private String salt;

    private String roleIds;

    private Boolean locked;

    private String type;


    private Long rid;

    private String wechatId;

    private String phone;

    private Date registerDate;

    private String sourceCode;
    private String startTime;
    private String endTime;



}
