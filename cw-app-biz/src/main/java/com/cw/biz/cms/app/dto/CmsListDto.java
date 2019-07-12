package com.cw.biz.cms.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 贷款攻略标题列表
 * Created by Administrator on 2017/7/10.
 */
@Setter
@Getter
public class CmsListDto extends BaseDto{

    private Long id;

    private String type;

    private String cmsImg;

    private String title;

    private String subTitle;

    private String content;

    private Date publishDate;

    private Integer clickNum=0;

    private String memo;
    private Boolean isValid=Boolean.TRUE;
}
