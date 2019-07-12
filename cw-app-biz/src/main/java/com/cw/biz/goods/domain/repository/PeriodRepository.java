package com.cw.biz.goods.domain.repository;


import com.cw.biz.goods.domain.entity.Period;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品分期
 * Created by dujy on 2017-05-20.
 */
public interface PeriodRepository extends JpaRepository<Period,Long> {

    List<Period> findByGoodsId(Long goodsId);
}
