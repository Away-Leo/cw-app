package com.cw.web.front.controller.integral;

import com.cw.biz.CPContext;
import com.cw.biz.integral.app.dto.IntegralDto;
import com.cw.biz.integral.app.dto.IntegralLogDto;
import com.cw.biz.integral.app.service.IntegralAppService;
import com.cw.biz.integral.app.service.IntegralLogAppService;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.front.controller.AbstractFrontController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 积分查询接口
 */
@RestController
public class IntegralController extends AbstractFrontController {

    @Autowired
    private IntegralAppService integralAppService;

    @Autowired
    private IntegralLogAppService integralLogAppService;
    /**
     * 查询用户积分接口
     * @return
     */
    @GetMapping("/integral/findById.json")
    public CPViewResultInfo findById(){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        IntegralDto integralDto = integralAppService.findById();
        cpViewResultInfo.setData(integralDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 积分兑换申请
     * @param integralLogDto
     * @return
     */
    @PostMapping("/integral/integralExchange.json")
    public CPViewResultInfo findByCondition(@RequestBody IntegralLogDto integralLogDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        integralLogDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        integralLogDto.setIntegralType(2L);
        integralLogDto.setPhone(CPContext.getContext().getSeUserInfo().getUsername());
        Long id = integralLogAppService.create(integralLogDto);
        cpViewResultInfo.setData(id);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("兑换申请成功");
        return cpViewResultInfo;
    }


    /**
     * 分享活动链接
     * @return
     */
    @GetMapping("/integral/shareActive.json")
    public CPViewResultInfo shareActive(){
        String shareUrl="https://www.pingxundata.com/active.html?inviteCode=";
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        cpViewResultInfo.setData(shareUrl+""+CPContext.getContext().getSeUserInfo().getId());
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}
