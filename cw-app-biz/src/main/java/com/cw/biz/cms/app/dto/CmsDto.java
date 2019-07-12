package com.cw.biz.cms.app.dto;

import com.cw.biz.common.dto.PageDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 贷款攻略
 * Created by Administrator on 2017/7/10.
 */
@Setter
@Getter
public class CmsDto extends PageDto{

    private Long id;

    private String type;

    private String cmsImg;

    private String title;

    private String subTitle;

    private Boolean isTop = Boolean.FALSE;

    private String content;

    private Date publishDate;

    private Integer clickNum=0;

    private String memo;

    private Boolean isValid=Boolean.TRUE;

    private String newsType;
}
