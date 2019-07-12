package com.cw.biz.creditcard.domain.repository;

import com.cw.biz.creditcard.domain.entity.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 信用卡
 * Created by dujy on 2017-05-20.
 */
public interface CreditCardRepository extends JpaRepository<CreditCard,Long>,JpaSpecificationExecutor<CreditCard> {
    /**
     * 查询推荐信用卡服务
     * @return
     */
    @Query(value = "select * from cw_credit_card where is_recommend = 1 and is_valid=1 limit 4",nativeQuery = true)
    List<CreditCard> findRecommendCard();
}
