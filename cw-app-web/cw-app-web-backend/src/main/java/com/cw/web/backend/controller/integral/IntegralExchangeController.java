package com.cw.web.backend.controller.integral;

import com.cw.biz.integral.app.dto.IntegralLogDto;
import com.cw.biz.integral.app.service.IntegralLogAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 积分兑换操作
 * Created by dujy on 2017-11-1.
 */
@RestController
public class IntegralExchangeController extends AbstractBackendController {

    @Autowired
    private IntegralLogAppService integralLogAppService;

    /**
     * 修改积分兑换
     * @param integralLogDto
     * @return
     */
    @PostMapping("/integralInvite/update.json")
    @ResponseBody
    public CPViewResultInfo update(@RequestBody IntegralLogDto integralLogDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        IntegralLogDto integralLogDto1 = integralLogAppService.update(integralLogDto);
        cpViewResultInfo.setData(integralLogDto1);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 查询积分详情
     * @param integralLogDto
     * @return
     */
    @PostMapping("/integralInvite/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(@RequestBody IntegralLogDto integralLogDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        IntegralLogDto integralLogDto1 = integralLogAppService.findById(integralLogDto.getId());
        cpViewResultInfo.setData(integralLogDto1);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 搜索积分对象申请记录
     * @param integralLogDto
     * @return
     */
    @PostMapping("/integralInvite/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody IntegralLogDto integralLogDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<IntegralLogDto> integralLogDtos = integralLogAppService.findByCondition(integralLogDto);
        cpViewResultInfo.setData(integralLogDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 积分统计
     * @param integralLogDto
     * @return
     */
    @PostMapping("/integralLog/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findAll(@RequestBody IntegralLogDto integralLogDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<IntegralLogDto> integralLogDtos = integralLogAppService.findAll(integralLogDto);
        cpViewResultInfo.setData(integralLogDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("积分统计查询成功");
        return cpViewResultInfo;
    }
}
