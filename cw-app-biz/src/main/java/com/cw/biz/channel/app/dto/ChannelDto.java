package com.cw.biz.channel.app.dto;

import com.cw.biz.common.dto.PageDto;
import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 渠道DTO
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class ChannelDto extends PageDto {

    private Long id;

    private String code;

    private String name;

    private String address;

    private String contactPerson;

    private String tel;

    private String memo;

    private BigDecimal channelPrice;

    private String cooperationType;//cpa cps

    private String cooperationMode;

    private Boolean isValid=Boolean.TRUE;

}
