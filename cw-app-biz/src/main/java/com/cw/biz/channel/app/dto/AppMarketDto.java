package com.cw.biz.channel.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 应用市场
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class AppMarketDto extends BaseDto {

    private Long id;

    private String code;

    private String name;

    private Integer downloadNum;

    private Integer yesterdayDownloadNum;

    private Integer commentNum;

    private String memo;
}
