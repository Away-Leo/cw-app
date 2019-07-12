package com.cw.biz.home.app.service;

import com.cw.biz.home.app.dto.ExportDataLogDto;
import com.cw.biz.home.domain.entity.ExportDataLog;
import com.cw.biz.home.domain.service.ExportDataLogDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台模块开关服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ExportDataLogAppService {

    @Autowired
    private ExportDataLogDomainService domainService;

    /**
     * 新增导入日志记录
     * @return
     */
    public Long create(ExportDataLogDto exportDataLogDto)
    {

        return domainService.create(exportDataLogDto).getId();

    }

    /**
     * 查询数据导出记录日志
     * @return
     */
    public List<ExportDataLogDto> findAll(String batchNo)
    {
        List<ExportDataLog> exportDataLogList = domainService.findAll(batchNo);
        List<ExportDataLogDto> exportDataLogDtoList = new ArrayList<>();
        for(ExportDataLog exportDataLog:exportDataLogList){
            exportDataLogDtoList.add(exportDataLog.to(ExportDataLogDto.class));
        }
        return exportDataLogDtoList;
    }

}
