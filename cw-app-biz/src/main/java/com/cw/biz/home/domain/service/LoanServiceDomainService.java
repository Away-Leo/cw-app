package com.cw.biz.home.domain.service;


import com.cw.biz.home.domain.entity.LoanService;
import com.cw.biz.home.domain.repository.LoanServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * banner查询服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class LoanServiceDomainService {

    @Autowired
    private LoanServiceRepository loanServiceDataRepository;

    /**
     * 统计新增用户数
     * @return
     */
    public LoanService findAll()
    {
        List<LoanService> loanServiceDataList = loanServiceDataRepository.findAll();
        if(loanServiceDataList!=null)
        {
            return loanServiceDataList.get(0);
        }
        return null;
    }

}
