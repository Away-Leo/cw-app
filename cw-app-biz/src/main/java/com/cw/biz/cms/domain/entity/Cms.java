package com.cw.biz.cms.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 文章-贷款攻略
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_cms_article")
@Getter
@Setter
public class Cms extends AggEntity {

    @Column(name="type",columnDefinition="varchar(100) not null comment '贷款类型'")
    private String type;

    @Column(name="title",columnDefinition="varchar(500) not null comment '文章标题'")
    private String title;

    @Column(name="sub_title",columnDefinition="varchar(500) not null comment '文章副标题'")
    private String subTitle;

    @Column(name="cms_img",columnDefinition="varchar(100) not null comment '文章标题图片'")
    private String cmsImg;

    @Column(name="is_top",columnDefinition="tinyint(1)  comment '是否推荐文章'")
    private Boolean isTop = Boolean.FALSE;

    @Column(name="content",columnDefinition="varchar(2000)  comment '文章内容'")
    private String content;

    @Column(name="publish_date",columnDefinition="datetime comment '发布时间'")
    private Date publishDate;

    @Column(name="click_num",columnDefinition="int(12)  comment '点击量'")
    private Integer clickNum=0;

    @Column(name="is_valid",columnDefinition="int(1) comment '是否有效'")
    private Boolean isValid=Boolean.TRUE;

    @Column(name="memo",columnDefinition="varchar(200) l comment '备注'")
    private String memo;
}
