package com.cw.biz.log.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

/**
 * 短信发送次数
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class SendSmsTimeDto extends BaseDto {

    private String phone;

    private Date sendDate;

    private Integer sendTime;

    private String appName;

}
