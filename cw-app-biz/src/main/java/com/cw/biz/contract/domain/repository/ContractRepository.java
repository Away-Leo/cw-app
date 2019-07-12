package com.cw.biz.contract.domain.repository;

import com.cw.biz.contract.domain.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 合同管理
 * Created by dujy on 2017-05-20.
 */
public interface ContractRepository extends JpaRepository<Contract,Long>,JpaSpecificationExecutor<Contract> {

    Contract findByProductId(Long productId);
}
