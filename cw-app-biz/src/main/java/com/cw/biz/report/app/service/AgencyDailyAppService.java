package com.cw.biz.report.app.service;

import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.report.app.dto.AgencyDailyDto;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.biz.report.app.dto.SettleDto;
import com.cw.biz.report.domain.service.AgencyDailyDomainService;
import com.cw.biz.report.domain.service.MarketDailyDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 代理人日报
 */
@Transactional
@Service
public class AgencyDailyAppService {

    @Autowired
    private AgencyDailyDomainService agencyDailyDomainService;

    /**
     * 查询日报数据
     * @return
     */
    public List<AgencyDailyDto> findAll(ReportSearchDto dto)
    {
        List<AgencyDailyDto> marketDailyList = agencyDailyDomainService.findAll(dto);
        return marketDailyList;
    }

    /**
     * 修改日报数据
     * @param dto
     * @return
     */
    public Boolean agencyUpdate(AgencyDailyDto dto) {
        return agencyDailyDomainService.agencyUpdate(dto);
    }
}
