package com.cw.biz.goods.app.service;

import com.cw.biz.common.dto.Pages;
import com.cw.biz.goods.app.dto.OrderDto;
import com.cw.biz.goods.domain.entity.Order;
import com.cw.biz.goods.domain.service.OrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 订单服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class OrderAppService {

    @Autowired
    private OrderDomainService orderDomainService;

    /**
     * 新增订单
     * @param orderDto
     * @return
     */
    public Long create(OrderDto orderDto){
        return orderDomainService.create(orderDto).getId();
    }

    /**
     * 修改订单
     * @param orderDto
     * @return
     */
    public Long update(OrderDto orderDto)
    {
        return orderDomainService.update(orderDto).getId();
    }

    /**
     * 查询订单详情
     * @param id
     * @return
     */
    public OrderDto findById(Long id){
        OrderDto orderDto = new OrderDto();
        Order order = orderDomainService.findById(id);
        if(order!=null){
            orderDto = order.to(OrderDto.class);
        }
        return orderDto;
    }

    /**
     * 按条件查询订单
     * @param dto
     * @return
     */
    public Page<OrderDto> findByCondition(OrderDto dto){
        return Pages.map(orderDomainService.findByCondition(dto),OrderDto.class);
    }

}
