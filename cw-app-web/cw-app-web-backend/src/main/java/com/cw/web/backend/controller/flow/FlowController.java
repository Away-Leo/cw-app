package com.cw.web.backend.controller.flow;

import com.cw.biz.discount.app.dto.ChannelDisShowDto;
import com.cw.biz.discount.app.dto.WholeDisCountDto;
import com.cw.biz.discount.domain.entity.ChannelDisCountSetting;
import com.cw.biz.discount.domain.service.DiscountDomainService;
import com.cw.biz.discount.domain.service.DiscountSettingDomainService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Title: FlowController.java
* @Description:  引流控制器
* @author Away
* @date 2019/7/27 22:53
* @copyright 重庆平讯数据
* @version V1.0
*/
@RestController
public class FlowController extends AbstractBackendController {

    private final DiscountDomainService discountDomainService;

    private final DiscountSettingDomainService discountSettingDomainService;


    @Autowired
    public FlowController(DiscountSettingDomainService discountSettingDomainService, DiscountDomainService discountDomainService) {
        this.discountSettingDomainService = discountSettingDomainService;
        this.discountDomainService = discountDomainService;
    }

    /**
     * @Author: Away
     * @Description 查找渠道跑量分页数据
     * @Param: info
     * @Param: pageRequest
     * @Param: disShowDto
     * @Return com.cw.web.common.dto.CPViewResultInfo
     * @Date 2019/7/27 23:03
     * @Copyright 重庆平讯数据
     */
    @GetMapping("/flow/findChannelFlowPage.json")
    public CPViewResultInfo findChannelFlowPage(CPViewResultInfo info, ChannelDisShowDto disShowDto){
        try {
            info.newSuccess(this.discountSettingDomainService.findByCondition(new PageRequest(disShowDto.getPage(),disShowDto.getSize()),disShowDto));
        }catch (Exception e){
            info.newFalse(e);
        }
        return info;
    }

    /**
     * @Author: Away
     * @Description: 更新或保存渠道设置
     * @Param: info
     * @Param: channelDisShowDto
     * @Return com.cw.web.common.dto.CPViewResultInfo
     * @Date 2019/7/27 23:06
     * @Copyright 重庆平讯数据
     */
    @PostMapping("/flow/saveOrUpdateFlow.json")
    public CPViewResultInfo saveOrUpdateFlow(CPViewResultInfo info, ChannelDisShowDto channelDisShowDto){
        try {
            info.newSuccess(this.discountDomainService.saveOrUpdateChannel(channelDisShowDto));
        }catch (Exception e){
            info.newFalse(e);
        }
        return info;
    }

    /**
     * @Author: Away
     * @Description: 更新或者保存全局扣量设置
     * @Param: info
     * @Param: wholeDisCountDto
     * @Return com.cw.web.common.dto.CPViewResultInfo
     * @Date 2019/7/27 23:07
     * @Copyright 重庆平讯数据
     */
    @PostMapping("/flow/saveOrUpdateWhole.json")
    public CPViewResultInfo saveOrUpdateWhole(CPViewResultInfo info, WholeDisCountDto wholeDisCountDto){
        try {
            info.newSuccess(this.discountDomainService.saveOrCreate(wholeDisCountDto));
        }catch (Exception e){
            info.newFalse(e);
        }
        return info;
    }

    /**
     * @Author: Away
     * @Description: 更新或者保存全局扣量设置
     * @Param: info
     * @Param: wholeDisCountDto
     * @Return com.cw.web.common.dto.CPViewResultInfo
     * @Date 2019/7/27 23:07
     * @Copyright 重庆平讯数据
     */
    @GetMapping("/flow/findWhole.json")
    public CPViewResultInfo findWhole(CPViewResultInfo info){
        try {
            info.newSuccess(this.discountDomainService.findDisCount());
        }catch (Exception e){
            info.newFalse(e);
        }
        return info;
    }



}
