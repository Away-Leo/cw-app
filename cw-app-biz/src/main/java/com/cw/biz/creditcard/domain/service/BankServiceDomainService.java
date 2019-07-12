package com.cw.biz.creditcard.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.creditcard.app.dto.BankServiceDto;
import com.cw.biz.creditcard.domain.entity.BankService;
import com.cw.biz.creditcard.domain.repository.BankServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 银行服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class BankServiceDomainService {

    @Autowired
    private BankServiceRepository repository;
    /**
     * 新增银行服务
     * @param bankDto
     * @return
     */
    public BankService create(BankServiceDto bankDto)
    {
        BankService bank = new BankService();
        bank.from(bankDto);
        return repository.save(bank);
    }

    /**
     * 修改银行服务
     * @param bankDto
     * @return
     */
    public BankService update(BankServiceDto bankDto)
    {
        BankService bank = repository.findOne(bankDto.getId());
        if(bank == null)
        {
            CwException.throwIt("银行服务不存在");
        }
        bank.from(bankDto);
        repository.save(bank);

        return bank;
    }


    /**
     * 银行服务停用、启用
     * @param bankDto
     * @return
     */
    public BankService enable(BankServiceDto bankDto)
    {
        BankService bank = repository.findOne(bankDto.getId());
        if(bank == null)
        {
            CwException.throwIt("银行服务不存在");
        }
        if(bank.getIsValid()) {
            bank.setIsValid(Boolean.FALSE);
        }else{
            bank.setIsValid(Boolean.TRUE);
        }
        return bank;
    }

    /**
     * 查询银行服务详情
     * @param id
     * @return
     */
    public BankService findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 查询所有银行服务
     * @return
     */
    public List<BankService> findAll() {
        return repository.findAll();
    }
}
