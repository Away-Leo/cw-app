package com.cw.biz.apply.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 产品申请调查问卷
 * Created by Administrator on 2017/7/24.
 */
@Setter
@Getter
public class ApplyQuestionnaireDto extends PageDto{

    private Long id;

    private Long productId;

    private Long userId;

    private Date applyDate;

    private Boolean isApply = Boolean.FALSE;
}
