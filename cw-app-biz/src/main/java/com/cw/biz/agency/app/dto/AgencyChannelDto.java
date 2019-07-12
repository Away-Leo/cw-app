package com.cw.biz.agency.app.dto;

import com.cw.biz.agency.domain.entity.AgencyProductInfo;
import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AgencyChannelDto extends PageDto {

    private Long id;

    private Long channelId;

    private String name;

    private Long managerId;

    private String cardNo;

    private String phone;

    private Long productId;

    private String productName;

    private String url;

    private String adminUrl;

    private String accountName;

    private String accountPwd;

    private Integer isValid;

    private Integer settleCycle;

    private BigDecimal prepareAmount;

    private AgencyProductInfo productInfo;

    private Integer showOnly;   //1.distinct显示，不传值显示所有

    private String createTime;

    private String onlineDate;

    private String rawAddTime;
}
