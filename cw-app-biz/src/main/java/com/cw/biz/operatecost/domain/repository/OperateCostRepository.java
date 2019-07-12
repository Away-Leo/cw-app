package com.cw.biz.operatecost.domain.repository;

import com.cw.biz.operatecost.domain.entity.OperateCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 运营成本
 * Created by dujy on 2017-05-20.
 */
public interface OperateCostRepository extends JpaRepository<OperateCost,Long>,JpaSpecificationExecutor<OperateCost> {

}
