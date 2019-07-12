package com.cw.biz.push.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.home.app.dto.AppInfoDto;
import com.cw.biz.home.app.service.AppInfoAppService;
import com.cw.biz.push.app.dto.PushMessageDto;
import com.cw.biz.push.domain.entity.PushMessage;
import com.cw.biz.push.domain.repository.PushMessageRepository;
import com.cw.core.common.push.PxPushCommpent;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * APP消息推送服务
 * Created by dujy on 2017-11-09.
 */
@Service
public class PushMessageDomainService {

    @Autowired
    private PushMessageRepository repository;

    @Autowired
    private PxPushCommpent pushCommpent;

    @Autowired
    private AppInfoAppService appInfoAppService;

    /**
     * 新增推送信息
     * @param pushMessageDto
     * @return
     */
    public PushMessage create(PushMessageDto pushMessageDto){
        PushMessage pushMessage = new PushMessage();
        pushMessage.from(pushMessageDto);
        return repository.save(pushMessage);
    }

    /**
     * 修改推送信息
     * @param pushMessageDto
     * @return
     */
    public PushMessage update(PushMessageDto pushMessageDto){
        PushMessage pushMessage = repository.findOne(pushMessageDto.getId());
        if(pushMessage == null){
            CwException.throwIt("推送信息不存在");
        }
        pushMessage.from(pushMessageDto);
        repository.save(pushMessage);

        return pushMessage;
    }
    /**
     * 查询推送消息
     * @param id
     * @return
     */
    public PushMessage findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 查询消息
     * @return
     */
    public PushMessage findByContent(String title)
    {
        return repository.findByContent(title);
    }

    /**
     * 查询消息推送列表
     * @param messageDto
     * @return
     */
    public Page<PushMessage> findByCondition(PushMessageDto messageDto){
        //设置排序
        messageDto.setSortFields(new String[]{"sendDate"});
        messageDto.setSortDirection(Sort.Direction.DESC);
        Specification<PushMessage> supplierSpecification = (Root<PushMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            //产品名称
            if(!Objects.isNull(messageDto.getTitle())){
                predicates.add(cb.like(root.get("title"),"%"+messageDto.getTitle()+"%"));
            }
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, messageDto.toPage());
    }

    /**
     * 发送消息推送
     * @return
     */
    public Boolean sendPushMessage(Long id){
        PushMessage pushMessage = repository.findOne(id);
        if(pushMessage==null){
            CwException.throwIt("推送信息不存在");
        }
        if(pushMessage.getIsSend()){
            CwException.throwIt("此信息已推送!");
        }
        pushMessage.setSendDate(new Date());
        pushMessage.setIsSend(Boolean.TRUE);
        //查询appkey  支持单个APP推送和所有APP都推送
        if("all".equals(pushMessage.getAppName())){
            List<AppInfoDto> appList = appInfoAppService.getAppList();
            for(AppInfoDto appInfoDto:appList) {
                if (appInfoDto.getMasterSecret() != null && appInfoDto.getAppKey() != null) {
                    pushCommpent.pushMessage(pushMessage.getTitle(), pushMessage.getContent(), pushMessage.getTitle(),
                            pushMessage.getId(), pushMessage.getUrl(), appInfoDto.getMasterSecret(), appInfoDto.getAppKey());
                }
            }
            return Boolean.TRUE;
        }else{
            AppInfoDto appInfoDto = appInfoAppService.findByCode(pushMessage.getAppName());
            return pushCommpent.pushMessage(pushMessage.getTitle(),pushMessage.getContent(),pushMessage.getTitle(),
                    pushMessage.getId(),pushMessage.getUrl(),appInfoDto.getMasterSecret(),appInfoDto.getAppKey());
        }

    }

}
