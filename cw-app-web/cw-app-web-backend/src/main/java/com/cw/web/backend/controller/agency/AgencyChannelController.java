package com.cw.web.backend.controller.agency;

import com.cw.biz.agency.app.dto.AgencyChannelDto;
import com.cw.biz.agency.app.dto.AgencyProductDto;
import com.cw.biz.agency.domain.entity.AgencyChannelInfo;
import com.cw.biz.agency.domain.entity.AgencyProductInfo;
import com.cw.biz.agency.domain.service.AgencyChannelService;
import com.cw.biz.agency.domain.service.AgencyProductService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 代理渠道维护
 */
@RestController
public class AgencyChannelController extends AbstractBackendController {

    @Autowired
    private AgencyChannelService agencyChannelService;

    @PostMapping("/agency/channel/agencyChannelCreate.json")
    @ResponseBody
    public CPViewResultInfo add(@RequestBody AgencyChannelDto channelDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        agencyChannelService.createChannel(channelDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    @GetMapping("/agency/channel/findById.json")
    @ResponseBody
    public CPViewResultInfo findById(Long id) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        AgencyChannelInfo info = agencyChannelService.findChannelById(id);
        cpViewResultInfo.setData(info);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查找成功");
        return cpViewResultInfo;
    }

    @PostMapping("/agency/channel/updateById.json")
    @ResponseBody
    public CPViewResultInfo updateById(@RequestBody AgencyChannelDto channelDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        agencyChannelService.updateById(channelDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("修改成功");
        return cpViewResultInfo;
    }

    /**
     * 查询渠道列表
     * @param channelDto
     * @return
     */
    @PostMapping("/agency/channel/findByCondition.json")
    @ResponseBody
    public CPViewResultInfo findByCondition(@RequestBody AgencyChannelDto channelDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Page<AgencyChannelDto> agencyChannelDtos = agencyChannelService.findByCondition(channelDto);
        cpViewResultInfo.setData(agencyChannelDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 启用停用渠道
     * @param channelDto
     * @return
     */
    @PostMapping("/agency/channel/enable.json")
    @ResponseBody
    public CPViewResultInfo enable(@RequestBody AgencyChannelDto channelDto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        agencyChannelService.enable(channelDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}
