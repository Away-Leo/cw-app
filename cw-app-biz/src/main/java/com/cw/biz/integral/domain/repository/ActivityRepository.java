package com.cw.biz.integral.domain.repository;

import com.cw.biz.integral.domain.entity.Activity;
import com.cw.biz.integral.domain.entity.IntegralInvite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @Title: ActivityRepository.java
* @Description: 活动操作库
* @author Away
* @date 2017/12/6 16:33
* @copyright 重庆平讯数据
* @version V1.0
*/
public interface ActivityRepository extends JpaRepository<Activity,Long>{

    /**
     * @Author: Away
     * @Description: 按照是否有效和创建时间查找第一条
     * @Param: isValid
     * @Return com.cw.biz.integral.domain.entity.Activity
     * @Date 2017/12/6 17:44
     * @Copyright 重庆平讯数据
     */
    public Activity findFirst1ByIsValidOrderByRawAddTimeDesc(boolean isValid);

}
