package com.cw.web.front.controller.goods;

import com.cw.biz.goods.app.dto.OrderDto;
import com.cw.biz.goods.app.service.OrderAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 订单服务
 * Created by dujy on 2017-06-26.
 */
@RestController
public class OrderController extends AbstractFrontController {

    @Autowired
    private OrderAppService orderAppService;

    /**
     * 订单记录
     * @param orderDto
     * @return
     */
    @PostMapping("/order/create.json")
    @ResponseBody
    public CPViewResultInfo create(@RequestBody OrderDto orderDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long logId = orderAppService.create(orderDto);
        cpViewResultInfo.setData(logId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("创建订单成功");

        return cpViewResultInfo;
    }

    /**
     * 订单状态修改
     * @param orderDto
     * @return
     */
    @PostMapping("/order/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody OrderDto orderDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long logId = orderAppService.update(orderDto);
        cpViewResultInfo.setData(logId);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("订单状态修改成功");

        return cpViewResultInfo;
    }
    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @GetMapping("/order/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        OrderDto orderDto = orderAppService.findById(id);
        cpViewResultInfo.setData(orderDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询商品列表
     * @param goodsDto
     * @return
     */
    @PostMapping("/order/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody OrderDto goodsDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<OrderDto> goodsDtoPage = orderAppService.findByCondition(goodsDto);
        cpViewResultInfo.setData(goodsDtoPage);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

}
