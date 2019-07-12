package com.cw.biz.integral.domain.repository;

import com.cw.biz.integral.domain.entity.IntegralLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 积分兑换日志
 * Created by dujy on 2017-11-20.
 */
public interface IntegralLogRepository extends JpaRepository<IntegralLog,Long>,JpaSpecificationExecutor<IntegralLog> {

}
