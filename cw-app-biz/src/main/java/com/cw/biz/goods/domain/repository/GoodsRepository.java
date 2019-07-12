package com.cw.biz.goods.domain.repository;


import com.cw.biz.goods.domain.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 商品
 * Created by dujy on 2017-05-20.
 */
public interface GoodsRepository extends JpaRepository<Goods,Long>,JpaSpecificationExecutor<Goods> {

}
