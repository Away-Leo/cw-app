package com.cw.biz.home.domain.repository;


import com.cw.biz.home.domain.entity.ExportDataLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 导入数据日志
 * Created by dujy on 2017-05-20.
 */
public interface ExportDataLogRepository extends JpaRepository<ExportDataLog,Long> {

    List<ExportDataLog> findByBatchNo(String batchNo);
}
