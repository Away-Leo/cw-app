package com.cw.biz.channel.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.banner.app.dto.BannerDto;
import com.cw.biz.banner.domain.entity.Banner;
import com.cw.biz.banner.domain.repository.BannerRepository;
import com.cw.biz.channel.app.dto.ChannelDto;
import com.cw.biz.channel.domain.entity.Channel;
import com.cw.biz.channel.domain.repository.ChannelRepository;
import com.cw.biz.product.app.ChannelEnum;
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
import java.util.Objects;

/**
 * 渠道服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ChannelDomainService {

    @Autowired
    private ChannelRepository repository;
    /**
     * 新增渠道
     * @param channelDto
     * @return
     */
    public Channel create(ChannelDto channelDto)
    {
        Channel channel = new Channel();
        channel.from(channelDto);
        channel.prepareSave();
        return repository.save(channel);
    }

    /**
     * 修改渠道
     * @param channelDto
     * @return
     */
    public Channel update(ChannelDto channelDto)
    {
        Channel channel = repository.findOne(channelDto.getId());
        if(channel == null)
        {
            CwException.throwIt("渠道不存在");
        }
        channel.from(channelDto);
        repository.save(channel);

        return channel;
    }


    /**
     * 渠道停用
     * @param channelDto
     * @return
     */
    public Channel enable(ChannelDto channelDto)
    {
        Channel channel = repository.findOne(channelDto.getId());
        if(channel == null)
        {
            CwException.throwIt("渠道不存在");
        }
        if(channel.getIsValid()) {
            channel.setIsValid(Boolean.FALSE);
        }else{
            channel.setIsValid(Boolean.TRUE);
        }
        return channel;
    }

    /**
     * 查询渠道详情
     * @param id
     * @return
     */
    public Channel findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 按条件查询渠道列表
     * @param channelDto
     * @return
     */
    public Page<Channel> findByCondition(ChannelDto channelDto)
    {
        String[] fields = {"id"};
        channelDto.setSortFields(fields);
        channelDto.setSortDirection(Sort.Direction.DESC);
        Specification<Channel> supplierSpecification = (Root<Channel> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            //渠道编码
            if(!Objects.isNull(channelDto.getCode())&&!"".equals(channelDto.getCode()))
            {
                predicates.add(cb.like(root.get("code"),"%"+channelDto.getCode()+"%"));
            }
            //渠道名称
            if(!Objects.isNull(channelDto.getName())&&!"".equals(channelDto.getName()))
            {
                predicates.add(cb.like(root.get("name"),"%"+channelDto.getName()+"%"));
            }

            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, channelDto.toPage());
    }

    /**
     * 查询所有渠道
     * @return
     */
    public List<Channel> findAll(){
        return repository.findAll();
    }

}
