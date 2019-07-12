package com.cw.biz.product.app.dto;

import com.cw.biz.common.dto.BaseDto;
import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 金融产品审核版本号
 * Created by Administrator on 2017/6/1.
 */
@Getter
@Setter
public class ProductAuditVersionDto extends BaseDto{

    private String dataVersion;

    private Boolean isAudit;

    private String appName;

    private String appCopyRight;

    private String appAboutUs;

    private String appleId;

}
