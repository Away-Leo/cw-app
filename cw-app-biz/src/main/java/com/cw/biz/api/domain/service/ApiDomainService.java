package com.cw.biz.api.domain.service;

import com.cw.biz.api.app.dto.ApiDto;
import com.cw.biz.api.domain.dao.ApiDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * API应用服务
 */
@Service
public class ApiDomainService {
    @Autowired
    private ApiDao apiDao;

    /**
     * api贷款申请接口
     * @param apiDto
     * @return
     */
    public Boolean applyLoan(ApiDto apiDto){
        return apiDao.applyLoan(apiDto);
    }
}
