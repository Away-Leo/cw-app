package com.cw.biz.discount.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WholeDisCountDto extends com.cw.core.common.base.BaseDto {

    /**起始扣量数**/
    private Integer startNum;

    /**扣量比例**/
    private Double percent;

}
