package com.cw.biz.message.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 消息内容
 * Created by Administrator on 2017/6/1.
 */
@Entity
@Table(name="cw_message")
@Getter
@Setter
public class Message extends AggEntity{

    @Column(name="user_id",columnDefinition="int(11) not null comment '所属用户'")
    private Long userId;

    @Column(name="title",columnDefinition="varchar(100) not null comment '消息标题'")
    private String title;

    @Column(name="content",columnDefinition="varchar(500) not null comment '消息内容'")
    private String content;

    @Column(name="send_date",columnDefinition="datetime not null comment '发送时间'")
    private Date sendDate;

    @Column(name="app_name",columnDefinition="varchar(50) comment 'app名称'")
    private String appName;

    @Column(name="is_read",columnDefinition="tinyint(1) not null comment '是否已读'")
    private Boolean isRead=Boolean.FALSE;

    /**
     *  保存数据验证
     */
    public void prepareSave()
    {

    }
}
