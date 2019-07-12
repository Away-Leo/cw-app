package com.cw.biz.home.app.service;

import com.cw.biz.home.app.dto.LoanServiceDto;
import com.cw.biz.home.domain.entity.LoanService;
import com.cw.biz.home.domain.service.LoanServiceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 后台模块开关服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class LoanServiceAppService {

    @Autowired
    private LoanServiceDomainService domainService;

    /**
     * 查询贷款统计数据
     * @return
     */
    public LoanServiceDto getLoanService()
    {
        LoanServiceDto loanServiceDto = new LoanServiceDto();
        LoanService loanService = domainService.findAll();
        if(loanService!=null)
        {
            loanServiceDto = loanService.to(LoanServiceDto.class);
        }
        return loanServiceDto;

    }

}
