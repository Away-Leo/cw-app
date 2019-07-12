package com.cw.biz.apply.app.service;

import com.cw.biz.CPContext;
import com.cw.biz.apply.app.dto.ApplyQuestionnaireDto;
import com.cw.biz.apply.domain.service.ApplyQuestionnaireDomainService;
import com.cw.biz.common.dto.Pages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * 借款申请调查
 * Created by Administrator on 2017/6/13.
 */
@Transactional
@Service
public class ApplyQuestionnaireAppService {

    @Autowired
    private ApplyQuestionnaireDomainService domainService;
    /**
     * 新增借款调查
     * @param applyDto
     * @return
     */
    public Long create(ApplyQuestionnaireDto applyDto)
    {
        applyDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        applyDto.setApplyDate(new Date());
        applyDto.setProductId(applyDto.getId());
        return domainService.create(applyDto).getId();
    }

    /**
     * 按条件搜索申请信息
     * @return
     */
    public Page<ApplyQuestionnaireDto> findByCondition(ApplyQuestionnaireDto applyDto)
    {
        return Pages.map(domainService.findByCondition(applyDto),ApplyQuestionnaireDto.class);
    }
}
