package com.cw.biz.creditcard.app.service;

import com.cw.biz.creditcard.app.dto.BankDto;
import com.cw.biz.creditcard.domain.entity.Bank;
import com.cw.biz.creditcard.domain.service.BankDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 信用卡服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class BankAppService {

    @Autowired
    private BankDomainService bankDomainService;

    /**
     * 新增产品
     * @param bankDto
     * @return
     */
    public Long create(BankDto bankDto)
    {
        return bankDomainService.create(bankDto).getId();
    }

    /**
     * 新增产品
     * @param bankDto
     * @return
     */
    public Long update(BankDto bankDto)
    {
        return bankDomainService.update(bankDto).getId();
    }

    /**
     * 停用启用产品
     * @param bankDto
     * @return
     */
    public void enable(BankDto bankDto)
    {
        bankDomainService.enable(bankDto);
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public BankDto findById(Long id)
    {
        return bankDomainService.findById(id).to(BankDto.class);
    }

    /**
     * 查询所有银行
     * @return
     */
    public List<BankDto> findAll()
    {
        List<BankDto> bankDtoList = new ArrayList<>();
        List<Bank> bankList = bankDomainService.findAll();
        for(Bank bank:bankList)
        {
            bankDtoList.add(bank.to(BankDto.class));
        }
        return bankDtoList;
    }

    /**
     * 查询银行卡信息
     * @param flag
     * @return
     */
    public List<BankDto> findBankByPosition(String flag)
    {
        List<BankDto> bankDtoList = new ArrayList<BankDto>();
        List<Bank> bankList;
        if("1".equals(flag))
        {
            bankList = bankDomainService.findAll();
        }else if("2".equals(flag)){
            bankList = bankDomainService.findByRecommend();
        }else{
            bankList = bankDomainService.findByBankTop();
        }

        for(Bank bank:bankList)
        {
            bankDtoList.add(bank.to(BankDto.class));
        }

        return bankDtoList;
    }


}
