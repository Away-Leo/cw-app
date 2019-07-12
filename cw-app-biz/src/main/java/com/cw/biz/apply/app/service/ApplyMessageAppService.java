package com.cw.biz.apply.app.service;

import com.cw.biz.CPContext;
import com.cw.biz.apply.app.dto.ApplyMessageDto;
import com.cw.biz.apply.domain.entity.ApplyMessage;
import com.cw.biz.apply.domain.service.ApplyMessageDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 借款申请调查
 * Created by Administrator on 2017/6/13.
 */
@Transactional
@Service
public class ApplyMessageAppService {

    @Autowired
    private ApplyMessageDomainService domainService;
    /**
     * 新增借款调查
     * @param applyDto
     * @return
     */
    public Long create(ApplyMessageDto applyDto)
    {
        applyDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        applyDto.setApplyDate(new Date());
        return domainService.create(applyDto).getId();
    }

    /**
     * 查询申请结果
     * @return
     */
    public List<ApplyMessageDto> findAll()
    {
        List<ApplyMessageDto> applyMessageDto = new ArrayList<ApplyMessageDto>();
        List<ApplyMessage> applyMessages = domainService.findALl();
        for(ApplyMessage applyMessage:applyMessages){
            applyMessageDto.add(applyMessage.to(ApplyMessageDto.class));
        }
        return applyMessageDto;
    }
}
