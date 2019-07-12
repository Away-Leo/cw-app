package com.cw.biz.home.domain.repository;


import com.cw.biz.home.domain.entity.LoanService;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 贷款服务统计
 * Created by dujy on 2017-05-20.
 */
public interface LoanServiceRepository extends JpaRepository<LoanService,Long> {

}
