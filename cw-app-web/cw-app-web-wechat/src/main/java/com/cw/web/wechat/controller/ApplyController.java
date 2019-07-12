package com.cw.web.wechat.controller;

import com.cw.biz.log.app.LogEnum;
import com.cw.biz.log.app.dto.LogDto;
import com.cw.biz.log.app.service.LogAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 产品申请接口
 */
@RestController
public class ApplyController extends AbstractWechatController {

    @Autowired
    private LogAppService logAppService;
    /**
     * 立即申请
     * @param logDto
     * @return
     */
    @PostMapping("/product/applyLoan.json")
    public CPViewResultInfo applyLoan(@RequestBody LogDto logDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        logDto.setUserId(999L);//测试账号
        //记录数据日志
        logDto.setApplyDate(new Date());
        logDto.setType(LogEnum.APPLY_LOAN);
        logAppService.create(logDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("产品申请成功");
        return cpViewResultInfo;
    }

}
