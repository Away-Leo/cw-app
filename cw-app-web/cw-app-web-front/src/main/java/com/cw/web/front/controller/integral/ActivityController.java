package com.cw.web.front.controller.integral;

import com.cw.biz.integral.app.dto.ActivityDto;
import com.cw.biz.integral.app.service.ActivityAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
* @Title: ActivityController.java
* @Description: 活动controller
* @author Away
* @date 2017/12/6 16:52
* @copyright 重庆平讯数据
* @version V1.0
*/
@RestController
public class ActivityController extends AbstractFrontController {

    @Autowired
    private ActivityAppService activityAppService;

    @GetMapping("/activity/findRecentActivity.json")
    public CPViewResultInfo getActivity(){
        CPViewResultInfo info=new CPViewResultInfo();
        ActivityDto dto=activityAppService.findLastActivity();
        info.setData(dto);
        info.setSuccess(true);
        info.setMessage("成功");
        return info;
    }

}
