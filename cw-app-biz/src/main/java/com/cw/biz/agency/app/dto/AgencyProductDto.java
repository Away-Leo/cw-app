package com.cw.biz.agency.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AgencyProductDto extends PageDto {

    private Long id;

    private String name;

    private Integer isValid;


}
