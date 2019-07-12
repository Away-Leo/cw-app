package com.cw.biz.agency.domain.repository;

import com.cw.biz.agency.domain.entity.AgencyProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgencyProductRepository extends JpaRepository<AgencyProductInfo, Long>,JpaSpecificationExecutor<AgencyProductInfo> {
}
