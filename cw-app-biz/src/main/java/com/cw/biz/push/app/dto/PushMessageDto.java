package com.cw.biz.push.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 推送消息
 * Created by dujiangyu on 2017/11/9.
 */
@Getter
@Setter
public class PushMessageDto extends PageDto {

    private Long id;

    private String appName;

    private String title;

    private String content;

    private String url;

    private Date sendDate;

    private Boolean isSend;
}
