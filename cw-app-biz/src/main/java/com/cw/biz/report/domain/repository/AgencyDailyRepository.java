package com.cw.biz.report.domain.repository;

import com.cw.biz.report.domain.entity.AgencyDaily;
import com.cw.biz.report.domain.entity.MarketDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 代理人日报
 */
public interface AgencyDailyRepository extends JpaRepository<AgencyDaily,Long>,JpaSpecificationExecutor<AgencyDaily> {
}
