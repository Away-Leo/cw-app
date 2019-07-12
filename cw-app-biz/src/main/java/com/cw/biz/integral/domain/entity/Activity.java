package com.cw.biz.integral.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 活动
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_activity")
@Setter
@Getter
public class Activity extends AggEntity {

    @Column(name="name",columnDefinition="varchar(200) not null comment '活动名称'")
    private String name;

    @Column(name="code",columnDefinition="varchar(20) default 0 comment '活动编码'")
    private String code;

    @Column(name="start_date",columnDefinition="datetime comment '开始时间'")
    private Date startDate;

    @Column(name="end_date",columnDefinition="datetime comment '结束时间'")
    private Date endDate;

    @Column(name="is_valid",columnDefinition="tinyint(1) comment '是否有效'")
    private Boolean isValid;

    @Column(name="activity_url",columnDefinition="varchar(200) comment '活动地址'")
    private String activityUrl;

    @Column(name="activity_img_url",columnDefinition="varchar(200) comment '活动图标地址'")
    private String activityImgUrl;

    @Column(name="activity_intro",columnDefinition="varchar(500) comment '活动介绍'")//用于分享后显示的介绍
    private String activityIntro;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
