package com.cw.biz.goods.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.goods.app.dto.OrderDto;
import com.cw.biz.goods.domain.entity.Order;
import com.cw.biz.goods.domain.repository.OrderRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 订单服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class OrderDomainService {

    @Autowired
    private OrderRepository repository;
    /**
     * 新增订单
     * @param orderDto
     * @return
     */
    public Order create(OrderDto orderDto){
        Order order = new Order();
        order.from(orderDto);
        order.setUserId(CPContext.getContext().getSeUserInfo().getId());
        return repository.save(order);
    }

    /**
     * 修改订单
     * @param orderDto
     * @return
     */
    public Order update(OrderDto orderDto)
    {
        Order order = repository.findOne(orderDto.getId());
        if(order == null){
            CwException.throwIt("商品不存在");
        }
        order.from(orderDto);
        repository.save(order);

        return order;
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    public Order findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 按条件查询订单列表
     * @param orderDto
     * @return
     */
    public Page<Order> findByCondition(OrderDto orderDto)
    {
        String[] fields = {"orderDate"};
        orderDto.setSortFields(fields);
        Specification<Order> supplierSpecification = (Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            //查询当前登陆用户的订单
            predicates.add(cb.equal(root.get("userId"), CPContext.getContext().getSeUserInfo().getId()));
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, orderDto.toPage());
    }

}
