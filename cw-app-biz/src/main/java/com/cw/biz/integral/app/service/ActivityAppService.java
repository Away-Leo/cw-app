package com.cw.biz.integral.app.service;

import com.cw.biz.CPContext;
import com.cw.biz.integral.app.dto.ActivityDto;
import com.cw.biz.integral.app.dto.IntegralDto;
import com.cw.biz.integral.domain.entity.Activity;
import com.cw.biz.integral.domain.service.ActivityDomainService;
import com.cw.biz.integral.domain.service.IntegralDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
* @Title: ActivityAppService.java
* @Description: 活动服务
* @author Away
* @date 2017/12/6 16:49
* @copyright 重庆平讯数据
* @version V1.0
*/
@Transactional
@Service
public class ActivityAppService {

    @Autowired
    private ActivityDomainService activityDomainService;

    /**
     * @Author: Away
     * @Description: 查找最新一次活动
     * @Param:
     * @Return com.cw.biz.integral.domain.entity.Activity
     * @Date 2017/12/6 16:50
     * @Copyright 重庆平讯数据
     */
    public ActivityDto findLastActivity(){
        ActivityDto returnData=activityDomainService.findLastActivity().to(ActivityDto.class);
        if(returnData!=null&&returnData.getActivityUrl()!=null&&!returnData.getActivityUrl().equalsIgnoreCase("")){
            returnData.setActivityUrl(returnData.getActivityUrl()+"?refereeUser="+CPContext.getContext().getSeUserInfo().getId());
        }
        return returnData;
    }

}
