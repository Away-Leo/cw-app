package com.cw.biz.integral.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.integral.app.dto.IntegralDto;
import com.cw.biz.integral.domain.entity.Activity;
import com.cw.biz.integral.domain.entity.Integral;
import com.cw.biz.integral.domain.repository.ActivityRepository;
import com.cw.biz.integral.domain.repository.IntegralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
* @Title: ActivityDomainService.java
* @Description: 活动数据服务service
* @author Away
* @date 2017/12/6 16:46
* @copyright 重庆平讯数据
* @version V1.0
*/
@Service
public class ActivityDomainService {

    @Autowired
    private ActivityRepository activityRepository;

    /**
     * @Author: Away
     * @Description: 查找最新一条活动
     * @Param:
     * @Return com.cw.biz.integral.domain.entity.Activity
     * @Date 2017/12/6 16:48
     * @Copyright 重庆平讯数据
     */
    public Activity findLastActivity(){
        return activityRepository.findFirst1ByIsValidOrderByRawAddTimeDesc(true);
    }
}
