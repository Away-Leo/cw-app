package com.cw.biz.agency.domain.repository;

import com.cw.biz.agency.domain.entity.AgencyChannelInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AgencyChannelRepository extends JpaRepository<AgencyChannelInfo, Long>,JpaSpecificationExecutor<AgencyChannelInfo> {
}
