package com.cw.biz.agency.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.agency.app.dto.AgencyChannelDto;
import com.cw.biz.agency.domain.dao.AgencyChannelDao;
import com.cw.biz.agency.domain.entity.AgencyChannelInfo;
import com.cw.biz.agency.domain.repository.AgencyChannelRepository;
import com.cw.biz.common.dto.Pages;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AgencyChannelService {

    @Autowired
    private AgencyChannelRepository repository;

    @Autowired
    private AgencyChannelDao channelDao;

    /**
     * 保存数据
     * @param channelDto
     */
    @Transactional
    public AgencyChannelInfo createChannel(AgencyChannelDto channelDto) {
        //名称是否存在
        Specification<AgencyChannelInfo> supplierSpecification = (Root<AgencyChannelInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            if(channelDto.getChannelId() == null) {
                //渠道名称
                if(!Objects.isNull(channelDto.getName())&&!"".equals(channelDto.getName()))
                {
                    predicates.add(cb.equal(root.get("name"),channelDto.getName()));
                }
            } else {
                //是否存在对应产品
                predicates.add(cb.equal(root.get("channelId"),channelDto.getChannelId()));
                predicates.add(cb.equal(root.get("productId"),channelDto.getProductId()));
            }
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        List<AgencyChannelInfo> existInfoList = repository.findAll(supplierSpecification);
        if(existInfoList != null && existInfoList.size() > 0) {
            CwException.throwIt("渠道名称或该渠道代理产品已存在");
        }

        AgencyChannelInfo info = new AgencyChannelInfo();
        if(channelDto.getChannelId() != null) {
            info.setChannelId(channelDto.getChannelId());
            info.setProductId(channelDto.getProductId());
            info.setAccountName(channelDto.getAccountName());
            info.setAccountPwd(channelDto.getAccountPwd());
            info.setName(channelDto.getName());
            info.setUrl(channelDto.getUrl());
            info.setAdminUrl(channelDto.getUrl());
            info.setManagerId(channelDto.getManagerId());
            info.setSettleCycle(channelDto.getSettleCycle());
        } else {
            info.from(channelDto);
        }
        //默认添加即生效
        info.setIsValid(true);
        info = repository.save(info);
        if(info.getChannelId() == null) {
            info.setChannelId(info.getId());
        }
        return info;
    }

    /**
     * 查找指定记录
     * @param id
     * @return
     */
    public AgencyChannelInfo findChannelById(Long id) {
        return repository.findOne(id);
    }

    /**
     * 修改渠道
     * @param channelDto
     */
    public void updateById(AgencyChannelDto channelDto) {
        AgencyChannelInfo info = repository.findOne(channelDto.getId());
        if(info == null)
        {
            CwException.throwIt("渠道不存在");
        }
        Specification<AgencyChannelInfo> supplierSpecification = (Root<AgencyChannelInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            predicates.add(cb.notEqual(root.get("id"), channelDto.getId()));
            if(channelDto.getChannelId() == null) {
                //渠道名称
                if(!Objects.isNull(channelDto.getName())&&!"".equals(channelDto.getName()))
                {
                    predicates.add(cb.equal(root.get("name"),channelDto.getName()));
                    predicates.add(cb.isNull(root.get("channelId")));
                }
            } else {
                //是否存在对应产品
                predicates.add(cb.equal(root.get("channelId"),channelDto.getChannelId()));
                predicates.add(cb.equal(root.get("productId"),channelDto.getProductId()));
            }
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        List<AgencyChannelInfo> existInfoList = repository.findAll(supplierSpecification);
        if(existInfoList != null && existInfoList.size() > 0) {
            CwException.throwIt("渠道名称或该渠道代理产品已存在");
        }
        Boolean isValid = info.getIsValid();
        if(channelDto.getSettleCycle() != 4) {
            channelDto.setPrepareAmount(null);
            info.setPrepareAmount(null);
        }
        info.from(channelDto);
        info.setIsValid(isValid);
        repository.save(info);
        //修改主资料，更新其他附资料名称
        if(info.getChannelId() == info.getId()) {
            channelDao.updateNameByChannleId(info.getChannelId(), info.getName());
        }
    }

    /**
     * 启用禁用
     * @param channelDto
     */
    @Transactional
    public AgencyChannelInfo enable(AgencyChannelDto channelDto) {
        AgencyChannelInfo info = repository.findOne(channelDto.getId());
        if(info == null)
        {
            CwException.throwIt("渠道不存在");
        }
        if(info.getIsValid()) {
            info.setIsValid(Boolean.FALSE);
            info.setOnlineDate(new Date());
        }else{
            info.setIsValid(Boolean.TRUE);
            info.setOnlineDate(new Date());
        }
        return info;
    }

    /**
     * 查找列表
     * @param channelDto
     * @return
     */
    public Page<AgencyChannelDto> findByCondition(AgencyChannelDto channelDto) {
        String[] fields = {"channelId", "id"};
        channelDto.setSortFields(fields);
        channelDto.setSortDirection(Sort.Direction.DESC);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfmat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Specification<AgencyChannelInfo> supplierSpecification = (Root<AgencyChannelInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            //渠道名称
            if(!Objects.isNull(channelDto.getName())&&!"".equals(channelDto.getName()))
            {
                predicates.add(cb.like(root.get("name"),"%"+channelDto.getName()+"%"));
            }
            if(!Objects.isNull(channelDto.getIsValid()) && channelDto.getIsValid() == 1) {
                predicates.add(cb.equal(root.get("isValid"), Boolean.TRUE));
            } else if(!Objects.isNull(channelDto.getIsValid()) && channelDto.getIsValid() == 0) {
                predicates.add(cb.equal(root.get("isValid"), Boolean.FALSE));
            }
            if(channelDto.getShowOnly() != null && channelDto.getShowOnly() == 1) {
                predicates.add(cb.equal(root.get("channelId"), root.get("id")));
            }
            try {
                if(!StringUtils.isBlank(channelDto.getCreateTime())) {
                    predicates.add(cb.between(root.get("rawAddTime").as(Timestamp.class), sdfmat.parse(sdfmat.format(sdf.parse(channelDto.getCreateTime()).getTime())),
                            sdfmat.parse(sdfmat.format(sdf.parse(channelDto.getCreateTime()).getTime() + 86400000))));
                }
                if(!StringUtils.isBlank(channelDto.getOnlineDate())) {
                    predicates.add(cb.between(root.get("rawAddTime").as(Timestamp.class), sdfmat.parse(sdfmat.format(sdf.parse(channelDto.getOnlineDate()).getTime())),
                            sdfmat.parse(sdfmat.format(sdf.parse(channelDto.getOnlineDate()).getTime() + 86400000))));
                }
            }catch (Exception e) {
            }
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return Pages.map(repository.findAll(supplierSpecification, channelDto.toPage()),AgencyChannelDto.class);
    }
}
