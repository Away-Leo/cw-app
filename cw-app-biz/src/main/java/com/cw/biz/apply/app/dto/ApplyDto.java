package com.cw.biz.apply.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 借款申请
 * Created by Administrator on 2017/6/13.
 */
@Setter
@Getter
public class ApplyDto extends PageDto{

    private Long id;

    private Long productId;

    private String productName;

    private String productFlag;
    private String img;
    private BigDecimal serviceRate;
    private Integer startPeriod;

    private Integer endPeriod;

    private String periodType;

    private String channelNo;

    public String getPeriodType() {
        if("month".equals(periodType))
        {
            periodType="月";
        }else{
            periodType="日";
        }
        return periodType;
    }

    private BigDecimal startAmount;

    private BigDecimal endAmount;

    private String loanDay;

    private Long userId;

    private BigDecimal loanAmount;

    private String applyArea;

    private Date applyDate;

    private String appName="MBD";

    private String productLabel;

    private String subTitle;

    private Integer clickNum;
}
