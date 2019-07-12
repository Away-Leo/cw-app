package com.cw.biz.creditcard.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.creditcard.app.dto.BankDto;
import com.cw.biz.creditcard.domain.entity.Bank;
import com.cw.biz.creditcard.domain.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 银行服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class BankDomainService {

    @Autowired
    private BankRepository repository;
    /**
     * 新增银行
     * @param bankDto
     * @return
     */
    public Bank create(BankDto bankDto)
    {
        Bank bank = new Bank();
        bank.from(bankDto);
        return repository.save(bank);
    }

    /**
     * 修改银行
     * @param bankDto
     * @return
     */
    public Bank update(BankDto bankDto)
    {
        Bank bank = repository.findOne(bankDto.getId());
        if(bank == null)
        {
            CwException.throwIt("银行不存在");
        }
        bank.from(bankDto);
        repository.save(bank);

        return bank;
    }


    /**
     * 银行停用、启用
     * @param bankDto
     * @return
     */
    public Bank enable(BankDto bankDto)
    {
        Bank bank = repository.findOne(bankDto.getId());
        if(bank == null)
        {
            CwException.throwIt("银行不存在");
        }
        if(bank.getIsValid()) {
            bank.setIsValid(Boolean.FALSE);
        }else{
            bank.setIsValid(Boolean.TRUE);
        }
        return bank;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public Bank findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 查询所有产品列表
     * @return
     */
    public List<Bank> findAll() {
        Specification<Bank> spec = new Specification<Bank>() {
            @Override
            public Predicate toPredicate(Root<Bank> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                list.add(cb.equal(root.get("isValid"),Boolean.TRUE));
                Predicate[] p = new Predicate[list.size()];
                return cb.and(list.toArray(p));
            }
        };
        return repository.findAll(spec);
    }

    /**
     * 查询推荐银行
     * @return
     */
    public List<Bank> findByRecommend()
    {
        return repository.findByIsRecommend(Boolean.TRUE);
    }

    /**
     * 查询前10位银行
     * @return
     */
    public List<Bank> findByBankTop()
    {
        return repository.findByBankTop();
    }

}
