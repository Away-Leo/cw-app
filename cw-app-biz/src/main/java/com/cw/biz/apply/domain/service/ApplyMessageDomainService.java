package com.cw.biz.apply.domain.service;

import com.cw.biz.apply.app.dto.ApplyMessageDto;
import com.cw.biz.apply.app.dto.ApplyQuestionnaireDto;
import com.cw.biz.apply.domain.entity.ApplyMessage;
import com.cw.biz.apply.domain.entity.ApplyQuestionnaire;
import com.cw.biz.apply.domain.repository.ApplyMessageRepository;
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
 * 产品申请结果服务
 * Created by Administrator on 2017/6/13.
 */
@Service
public class ApplyMessageDomainService {

    @Autowired
    private ApplyMessageRepository repository;

    /**
     * 新增借款调查
     * @param applyDto
     * @return
     */
    public ApplyMessage create(ApplyMessageDto applyDto){
        ApplyMessage apply = new ApplyMessage();
        apply.from(applyDto);
        return repository.save(apply);
    }

    /**
     * 查询调查申请
     * @return
     */
    public List<ApplyMessage> findALl()
    {
        return repository.findAll();
    }
}
