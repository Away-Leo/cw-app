package com.cw.biz.push.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 推送消息
 * Created by dujiangyu on 2017/11/9.
 */
@Entity
@Table(name="cw_push_message")
@Getter
@Setter
public class PushMessage extends AggEntity {

    @Column(name="app_name",columnDefinition="varchar(10)  comment '所属APP'")
    private String appName;

    @Column(name="title",columnDefinition="varchar(100) not null comment '消息标题'")
    private String title;

    @Column(name="content",columnDefinition="varchar(500)  comment '消息内容'")
    private String content;

    @Column(name="url",columnDefinition="varchar(100) comment '跳转链接'")
    private String url;

    @Column(name="send_date",columnDefinition="datetime comment '发送时间'")
    private Date sendDate;

    @Column(name="is_send",columnDefinition="tinyint(1) comment '是否发送'")
    private Boolean isSend=Boolean.FALSE;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}
