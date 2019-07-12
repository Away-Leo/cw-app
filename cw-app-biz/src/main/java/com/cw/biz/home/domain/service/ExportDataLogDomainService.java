package com.cw.biz.home.domain.service;


import com.cw.biz.home.app.dto.ExportDataLogDto;
import com.cw.biz.home.domain.entity.ExportDataLog;
import com.cw.biz.home.domain.repository.ExportDataLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 博达数据导入日志
 * Created by dujy on 2017-05-20.
 */
@Service
public class ExportDataLogDomainService {

    @Autowired
    private ExportDataLogRepository repository;

    /**
     * 保存记录日志
     * @return
     */
    public ExportDataLog create(ExportDataLogDto exportDataLogDto)
    {
        ExportDataLog exportDataLog = new ExportDataLog();
        exportDataLog.from(exportDataLogDto);
        return repository.save(exportDataLog);
    }

    /**
     * 查询导入记录
     * @return
     */
    public List<ExportDataLog> findAll(String batchNo){
        if(batchNo==null||"".equals(batchNo)){
            return repository.findAll();
        }else {
            return repository.findByBatchNo(batchNo);
        }
    }

}
