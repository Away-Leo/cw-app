package com.cw.biz.push.app.service;

import com.cw.biz.common.dto.Pages;
import com.cw.biz.product.app.dto.ProductListDto;
import com.cw.biz.push.app.dto.PushMessageDto;
import com.cw.biz.push.domain.service.PushMessageDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 信用卡服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class PushMessageAppService {

    @Autowired
    private PushMessageDomainService pushMessageDomainService;

    /**
     * 新增产品
     * @param pushMessageDto
     * @return
     */
    public Long create(PushMessageDto pushMessageDto){
        return pushMessageDomainService.create(pushMessageDto).getId();
    }

    /**
     * 修改推送消息
     * @param pushMessageDto
     * @return
     */
    public Long update(PushMessageDto pushMessageDto){
        return pushMessageDomainService.update(pushMessageDto).getId();
    }

    /**
     * 查询推送消息
     * @param id
     * @return
     */
    public PushMessageDto findById(Long id){
        return pushMessageDomainService.findById(id).to(PushMessageDto.class);
    }
    /**
     * 查询产品详情
     * @param title
     * @return
     */
    public PushMessageDto findByContent(String title)   {
        return pushMessageDomainService.findByContent(title).to(PushMessageDto.class);
    }

    /**
     * 查询推送消息
     * @param pushMessageDto
     * @return
     */
    public Page<PushMessageDto> findByCondition(PushMessageDto pushMessageDto)   {
        return Pages.map(pushMessageDomainService.findByCondition(pushMessageDto),PushMessageDto.class);
    }
    /**
     * APP消息推送
     * @param id
     * @return
     */
    public boolean sendPushMessage(Long id){
        return pushMessageDomainService.sendPushMessage(id);
    }

}
