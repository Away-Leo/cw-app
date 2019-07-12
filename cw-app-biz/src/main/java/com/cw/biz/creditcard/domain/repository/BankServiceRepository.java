package com.cw.biz.creditcard.domain.repository;

import com.cw.biz.creditcard.domain.entity.BankService;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 银行卡服务数据
 * Created by dujy on 2017-05-20.
 */
public interface BankServiceRepository extends JpaRepository<BankService,Long>{

}
