package com.cw.biz.home.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 报表查询条件DTO
 * Created by dujiangyu on 2017/6/21.
 */
@Getter
@Setter
public class ReportSearchDto extends BaseDto {

    private String applyStartDate;

    private String applyEndDate;

    private String applyMonth;

    private String merchantName;

    private String enterpriseName;

    private String productName;

    private String appName;

    private String channelName;

    private String belongApp;

    private String channelId;

    private String channelNo;

    private String productId;

    private String quota;

    private String sortColumn;

    private String sortDesc="asc";

    private Boolean isValid;

    private String cooperationModel;

    private Integer status;

    private String sysType;
    private String settleNo;
    private String invoiceType;
    private Integer isBilling;
    private String backDate;
    private String operateNo;
    private String postNo;
    private String settleCycle;

    private Long managerId;
}
