package com.cw.biz.creditcard.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.creditcard.app.dto.CreditCardDto;
import com.cw.biz.creditcard.domain.dao.CreditCardDao;
import com.cw.biz.creditcard.domain.entity.CreditCard;
import com.cw.biz.creditcard.domain.repository.CreditCardRepository;
import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.log.app.dto.LogDto;
import com.cw.biz.log.app.service.LogAppService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

/**
 * 信用卡服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class CreditCardDomainService {

    @Autowired
    private CreditCardRepository repository;
    @Autowired
    private LogAppService logAppService;
    @Autowired
    private CreditCardDao creditCardDao;
    /**
     * 新增信用卡产品
     * @param creditCardDto
     * @return
     */
    public CreditCard create(CreditCardDto creditCardDto)
    {
        CreditCard creditCard = new CreditCard();
        creditCardDto.setBankNumber("10000");
        creditCard.from(creditCardDto);
        creditCard.setOperateNo(CPContext.getContext().getSeUserInfo().getId());
        return repository.save(creditCard);
    }

    /**
     * 修改信用卡产品
     * @param creditCardDto
     * @return
     */
    public CreditCard update(CreditCardDto creditCardDto)
    {
        CreditCard creditCard = repository.findOne(creditCardDto.getId());
        if(creditCard == null){
            CwException.throwIt("信用卡不存在");
        }
        creditCard.from(creditCardDto);
        repository.save(creditCard);

        return creditCard;
    }

    /**
     * 产品停用、启用
     * @param creditCardDto
     * @return
     */
    public CreditCard enable(CreditCardDto creditCardDto)
    {
        CreditCard creditCard = repository.findOne(creditCardDto.getId());
        if(creditCard == null)
        {
            CwException.throwIt("产品不存在");
        }
        if(creditCard.getIsValid()) {
            creditCard.setIsValid(Boolean.FALSE);
        }else{
            creditCard.setIsValid(Boolean.TRUE);
        }
        return creditCard;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public CreditCard findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 按条件查询产品列表
     * @param searchDto
     * @return
     */
    public Page<CreditCard> findByCondition(CreditCardDto searchDto)
    {


        String[] fields = {"showOrder"};
        searchDto.setSortFields(fields);
        Specification<CreditCard> supplierSpecification = (Root<CreditCard> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));
            if(!Objects.isNull(searchDto.getName()))
            {
                predicates.add(cb.like(root.get("name"),"%"+searchDto.getName()+"%"));
            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, searchDto.toPage());
    }

    /**
     * 查询推荐信用卡
     * @return
     */
    public List<CreditCard> findByRecommend(){
        return repository.findRecommendCard();
    }


    /**
     * 信用卡申请
     * @param logDto
     */
    public void applyCreditCard(LogDto logDto){
        //修改申请次数
        CreditCard creditCard= repository.findOne(logDto.getProductId());
        if(creditCard!=null) {
            creditCard.setClickNum(creditCard.getClickNum() + 1);
        }
        //记录数据日志
        logAppService.create(logDto);
    }

    /**
     * 信用卡统计分析
     * @param reportSearchDto
     * @return
     */
    public List<CreditCardDto> creditCardApply(ReportSearchDto reportSearchDto){
        return creditCardDao.creditCardApply(reportSearchDto);
    }

}
