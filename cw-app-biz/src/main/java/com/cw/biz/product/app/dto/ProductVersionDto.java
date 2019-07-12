package com.cw.biz.product.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 金融产品
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class ProductVersionDto extends BaseDto{
    @NotNull
    private Long id;

    private Integer dataVersion;

    private String channelNo;

    private String apkUrl;

    private String memo;

    private Boolean isAudit;

    private String appName;

}
