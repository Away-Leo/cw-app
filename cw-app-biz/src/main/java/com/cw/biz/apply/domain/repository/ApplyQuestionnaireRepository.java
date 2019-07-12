package com.cw.biz.apply.domain.repository;

import com.cw.biz.apply.domain.entity.ApplyQuestionnaire;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 借款申请优化调查
 * Created by Administrator on 2017/6/13.
 */
public interface ApplyQuestionnaireRepository extends PagingAndSortingRepository<ApplyQuestionnaire,Long>,JpaSpecificationExecutor<ApplyQuestionnaire> {
}
