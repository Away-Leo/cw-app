package com.cw.biz.home.app.dto;

import com.cw.biz.common.dto.BaseDto;
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
@Getter
@Setter
public class ExportDataLogDto extends BaseDto {

    private String batchNo;

    private Date exportDate;

    private String exportResult;

    private Long exportUserId;

    private Integer exportNum;

    /**
     * 保存数据验证
     */
    public void prepareSave()
    {

    }
}