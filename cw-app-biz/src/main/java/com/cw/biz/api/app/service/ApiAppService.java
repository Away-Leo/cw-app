package com.cw.biz.api.app.service;

import com.cw.biz.api.app.dto.ApiDto;
import com.cw.biz.api.domain.service.ApiDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class ApiAppService {

    @Autowired
    private ApiDomainService apiDomainService;
    /**
     * api贷款申请接口
     * @param apiDto
     * @return
     */
    public Boolean applyLoan(ApiDto apiDto){
        return apiDomainService.applyLoan(apiDto);
    }
}
