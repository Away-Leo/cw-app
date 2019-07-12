package com.cw.biz.integral.app.dto;

import com.cw.biz.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 活动
 * Created by Administrator on 2017/6/1.
 */
@Setter
@Getter
public class ActivityDto extends BaseDto {

    private Long id;

    private String name;

    private String code;

    private Date startDate;

    private Date endDate;

    private Boolean isValid;

    private String activityUrl;

    private String activityImgUrl;

    private String activityIntro;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
