package com.cw.biz.report.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.report.app.dto.AgencyDailyDto;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.biz.report.app.dto.SettleDto;
import com.cw.biz.report.domain.dao.AgencyDailyDao;
import com.cw.biz.report.domain.dao.MarketDailyDao;
import com.cw.biz.report.domain.entity.MarketDaily;
import com.cw.biz.report.domain.repository.AgencyDailyRepository;
import com.cw.biz.report.domain.repository.MarketDailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 代理人日报
 */
@Service
public class AgencyDailyDomainService {

    @Autowired
    private AgencyDailyRepository repository;

    @Autowired
    private AgencyDailyDao agencyDailyDao;
    /**
     * 查询当月日报信息
     * @return
     */
    public List<AgencyDailyDto> findAll(ReportSearchDto dto) {

        return agencyDailyDao.findAgencyDailyByDayId(dto);
    }


    public Boolean agencyUpdate(AgencyDailyDto dto) {
        return agencyDailyDao.agencyUpdate(dto);
    }
}
