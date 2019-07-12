package com.cw.biz.integral.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.creditcard.app.dto.CreditCardDto;
import com.cw.biz.creditcard.domain.entity.CreditCard;
import com.cw.biz.integral.app.dto.IntegralDto;
import com.cw.biz.integral.app.dto.IntegralLogDto;
import com.cw.biz.integral.domain.entity.IntegralLog;
import com.cw.biz.integral.domain.repository.IntegralLogRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 积分兑换服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class IntegralLogDomainService {

    @Autowired
    private IntegralLogRepository repository;

    @Autowired
    private IntegralDomainService domainService;
    /**
     * 新增用户消费记录
     * @param integralLogDto
     * @return
     */
    public IntegralLog create(IntegralLogDto integralLogDto)
    {
        IntegralLog integralLog = new IntegralLog();
        integralLog.from(integralLogDto);
        return repository.save(integralLog);
    }

    /**
     * 修改用户积分
     * @param integralLogDto
     * @return
     */
    public IntegralLog update(IntegralLogDto integralLogDto){
        IntegralLog integralLog = repository.findOne(integralLogDto.getId());
        if(integralLog==null){
            CwException.throwIt("积分不存在");
        }
        //操作用户剩余积分
        IntegralDto integralDto = new IntegralDto();
        integralDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        integralDto.setIntegralType(integralLogDto.getIntegralType());
        integralDto.setIntegral(integralLogDto.getIntegral());
        domainService.update(integralDto);
        integralLog.setExchangeDate(new Date());
        integralLog.setMemo(integralLogDto.getMemo());
        return integralLog;
    }

    /**
     * 查询用户兑换记录
     * @param id
     * @return
     */
    public IntegralLog findById(Long id){
        return repository.findOne(id);
    }

}
