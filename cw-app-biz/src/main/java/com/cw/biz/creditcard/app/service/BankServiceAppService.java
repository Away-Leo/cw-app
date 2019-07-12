package com.cw.biz.creditcard.app.service;

import com.cw.biz.creditcard.app.dto.BankServiceDto;
import com.cw.biz.creditcard.domain.service.BankServiceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 银行服务描述
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class BankServiceAppService {

    @Autowired
    private BankServiceDomainService bankServiceDomainService;

    /**
     * 新增银行服务
     * @param bankServiceDto
     * @return
     */
    public Long create(BankServiceDto bankServiceDto)
    {
        return bankServiceDomainService.create(bankServiceDto).getId();
    }

    /**
     * 新增银行服务
     * @param bankServiceDto
     * @return
     */
    public Long update(BankServiceDto bankServiceDto)
    {
        return bankServiceDomainService.update(bankServiceDto).getId();
    }

    /**
     * 停用启用银行服务
     * @param bankServiceDto
     * @return
     */
    public void enable(BankServiceDto bankServiceDto)
    {
        bankServiceDomainService.enable(bankServiceDto);
    }


}
