package com.cw.web.backend.controller.integral;

import com.cw.biz.integral.app.dto.IntegralInviteDto;
import com.cw.biz.integral.app.dto.IntegralLogDto;
import com.cw.biz.integral.app.service.IntegralInviteAppService;
import com.cw.biz.integral.app.service.IntegralLogAppService;
import com.cw.biz.integral.domain.entity.IntegralInvite;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 邀请成功借款人员控制器
 * Created by dujy on 2017-11-1.
 */
@RestController
public class IntegralInviteController extends AbstractBackendController {

    @Autowired
    private IntegralInviteAppService integralInviteAppService;

    /**
     * 修改成功借款用户信息
     * @param integralInviteDto
     * @return
     */
    @PostMapping("/integral/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody IntegralInviteDto integralInviteDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        IntegralInviteDto inviteDto = integralInviteAppService.update(integralInviteDto);
        cpViewResultInfo.setData(inviteDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("修改成功");
        return cpViewResultInfo;
    }


    /**
     * 搜索积分对象申请记录
     * @param integralInviteDto
     * @return
     */
    @PostMapping("/integral/findAll.json")
    @ResponseBody
    public CPViewResultInfo findAll(@RequestBody IntegralInviteDto integralInviteDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<IntegralInviteDto> integralLogDtos = integralInviteAppService.findAll(integralInviteDto);
        cpViewResultInfo.setData(integralLogDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }
}
