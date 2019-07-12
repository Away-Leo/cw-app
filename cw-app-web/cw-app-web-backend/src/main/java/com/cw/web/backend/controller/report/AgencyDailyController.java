package com.cw.web.backend.controller.report;

import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.report.app.dto.AgencyDailyDto;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.biz.report.app.dto.SettleDto;
import com.cw.biz.report.app.service.AgencyDailyAppService;
import com.cw.biz.report.app.service.MarketDailyAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 代理人日报
 */
@RestController
public class AgencyDailyController extends AbstractBackendController {

    @Autowired
    private AgencyDailyAppService appService;

    /**
     * 查询日报数据
     * @return
     */
    @PostMapping("/report/agencyDailyReport.json")
    public CPViewResultInfo findAll(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AgencyDailyDto> productDtos = appService.findAll(dto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    @PostMapping("/report/agencyUpdateStatus.json")
    public CPViewResultInfo agencyUpdateStatus(@RequestBody AgencyDailyDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Boolean result = appService.agencyUpdate(dto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("修改成功");
        return cpViewResultInfo;
    }

}
