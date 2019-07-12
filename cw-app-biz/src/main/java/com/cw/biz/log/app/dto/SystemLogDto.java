package com.cw.biz.log.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统操作日志
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class SystemLogDto extends BaseDto {

    private Long userId;

    private Date logDate;

    private String url;

    private String operateArg;

    private String action;

    private String name;

    private String remoteAddress;
}
