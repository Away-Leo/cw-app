package com.cw.biz.goods.domain.repository;


import com.cw.biz.goods.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 订单
 * Created by dujy on 2017-05-20.
 */
public interface OrderRepository extends JpaRepository<Order,Long> ,JpaSpecificationExecutor<Order> {

}
