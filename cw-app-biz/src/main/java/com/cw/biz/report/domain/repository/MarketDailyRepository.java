package com.cw.biz.report.domain.repository;

import com.cw.biz.report.domain.entity.MarketDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 市场日报
 * Created by dujy on 2017-05-20.
 */
public interface MarketDailyRepository extends JpaRepository<MarketDaily,Long>,JpaSpecificationExecutor<MarketDaily> {
}
