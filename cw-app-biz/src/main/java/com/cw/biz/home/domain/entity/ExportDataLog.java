package com.cw.biz.home.domain.entity;

import com.cw.biz.common.entity.AggEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 数据导出日志表
 * Created by Administrator on 2017/7/28.
 */
@Entity
@Table(name="cw_export_data_log")
@Getter
@Setter
public class ExportDataLog extends AggEntity {

    @Column(name="batch_no",columnDefinition="varchar(100) not null comment '批次编码'")
    private String batchNo;

    @Column(name="export_date",columnDefinition="datetime  comment '导入时间'")
    private Date exportDate;

    @Column(name="export_result",columnDefinition="varchar(200)  comment '导入结果'")
    private String exportResult;

    @Column(name="export_user_id",columnDefinition="int(11)  comment '导入用户'")
    private Long exportUserId;

    @Column(name="export_num",columnDefinition="int(11) comment '导入记录数据'")
    private Integer exportNum;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}