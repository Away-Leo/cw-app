package com.cw.biz.creditcard.domain.repository;

import com.cw.biz.creditcard.domain.entity.Bank;
import com.cw.biz.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 产品数据
 * Created by dujy on 2017-05-20.
 */
public interface BankRepository extends JpaRepository<Bank,Long>,JpaSpecificationExecutor<Bank> {

    List<Bank> findByIsRecommend(Boolean isRecommend);

    @Query(value = "select * from cw_bank_service order by show_order top 10 ",nativeQuery = true)
    List<Bank> findByBankTop();
}
