package com.cw.biz.message.app.service;

import com.cw.biz.common.dto.Pages;
import com.cw.biz.creditcard.app.dto.CreditCardDto;
import com.cw.biz.message.app.dto.MessageDto;
import com.cw.biz.message.domain.service.MessageDomainService;
import com.cw.biz.parameter.app.ParameterEnum;
import com.cw.biz.parameter.domain.entity.Parameter;
import com.cw.biz.parameter.domain.service.ParameterDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 消息列表服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class MessageAppService {

    @Autowired
    private MessageDomainService messageDomainService;

    /**
     * 新增消息
     * @param messageDto
     * @return
     */
    public Long create(MessageDto messageDto)
    {
        return messageDomainService.create(messageDto).getId();
    }
    /**
     * 查询消息详情
     * @param id
     * @return
     */
    public MessageDto findById(Long id)
    {
        return messageDomainService.findById(id).to(MessageDto.class);
    }

    /**
     * 查询消息列表
     * @return
     */
    public Page<MessageDto> findByCondition(MessageDto dto)
    {
        return Pages.map(messageDomainService.findByCondition(dto),MessageDto.class);
    }

}
