package com.cw.biz.parameter.domain.repository;

import com.cw.biz.parameter.domain.entity.IntegralWallLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 积分墙日志接口
 * Created by dujy on 2017-05-20.
 */
public interface IntegralWallLogRepository extends JpaRepository<IntegralWallLog,Long>{
}
