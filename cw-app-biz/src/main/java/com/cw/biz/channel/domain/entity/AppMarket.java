package com.cw.biz.channel.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 应用市场
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_app_market")
@Getter
@Setter
public class AppMarket extends AggEntity {

    @Column(name="code",columnDefinition="varchar(100) not null comment '应用市场编码'")
    private String code;

    @Column(name="name",columnDefinition="varchar(500) not null comment '应用市场名称'")
    private String name;

    @Column(name="download_num",columnDefinition="int(11) not null comment '下载数'")
    private Integer downloadNum;

    @Column(name="download_num_yesterday",columnDefinition="int(11) not null comment '昨日下载数'")
    private Integer yesterdayDownloadNum;

    @Column(name="comment_num",columnDefinition="int(11) not null comment '评论数量'")
    private Integer commentNum;

    @Column(name="memo",columnDefinition="varchar(200) not null comment '备注'")
    private String memo;
}
