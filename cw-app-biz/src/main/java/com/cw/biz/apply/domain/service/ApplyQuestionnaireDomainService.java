package com.cw.biz.apply.domain.service;

import com.cw.biz.apply.app.dto.ApplyQuestionnaireDto;
import com.cw.biz.apply.domain.entity.ApplyQuestionnaire;
import com.cw.biz.apply.domain.repository.ApplyQuestionnaireRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 产品调查服务
 * Created by Administrator on 2017/6/13.
 */
@Service
public class ApplyQuestionnaireDomainService {

    @Autowired
    private ApplyQuestionnaireRepository repository;

    /**
     * 新增借款调查
     * @param applyDto
     * @return
     */
    public ApplyQuestionnaire create(ApplyQuestionnaireDto applyDto){
        applyDto.setId(null);
        ApplyQuestionnaire apply = new ApplyQuestionnaire();
        apply.from(applyDto);
        return repository.save(apply);
    }

    /**
     * 查询调查申请
     * @param applyDto
     * @return
     */
    public Page<ApplyQuestionnaire> findByCondition(ApplyQuestionnaireDto applyDto)
    {
        String[] fields = {"applyDate"};
        applyDto.setSortFields(fields);
        applyDto.setSortDirection(Sort.Direction.DESC);
        Specification<ApplyQuestionnaire> supplierSpecification = (Root<ApplyQuestionnaire> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(4);

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, applyDto.toPage());
    }
}
