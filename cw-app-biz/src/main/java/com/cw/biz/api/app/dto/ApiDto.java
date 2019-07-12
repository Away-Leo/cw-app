package com.cw.biz.api.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

/**
 * 借款申请数据传输对象
 */
@Getter
@Setter
public class ApiDto extends BaseDto{

    private String phone;

    private String certNo;

    private String name;
}
