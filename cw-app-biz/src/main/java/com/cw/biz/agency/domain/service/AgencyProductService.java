package com.cw.biz.agency.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.agency.app.dto.AgencyProductDto;
import com.cw.biz.agency.domain.dao.AgencyProductDao;
import com.cw.biz.agency.domain.entity.AgencyProductInfo;
import com.cw.biz.agency.domain.repository.AgencyProductRepository;
import com.cw.biz.common.dto.Pages;
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
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AgencyProductService {

    @Autowired
    private AgencyProductDao agencyProductDao;

    @Autowired
    private AgencyProductRepository repository;

    /**
     * 保存数据
     * @param productDto
     */
    public void createProduct(AgencyProductDto productDto) {
        AgencyProductInfo info = new AgencyProductInfo();
        info.from(productDto);
        //默认添加即上架
        info.setIsValid(true);
        repository.save(info);
    }

    /**
     * 查找指定记录
     * @param id
     * @return
     */
    public AgencyProductInfo findProductById(Long id) {
        return repository.findOne(id);
    }

    /**
     * 修改产品
     * @param productDto
     */
    public void updateById(AgencyProductDto productDto) {
        AgencyProductInfo info = repository.findOne(productDto.getId());
        if(info == null)
        {
            CwException.throwIt("产品不存在");
        }
        Boolean isValid = info.getIsValid();
        info.from(productDto);
        info.setIsValid(isValid);
        repository.save(info);
    }

    /**
     * 启用禁用
     * @param productDto
     */
    @Transactional
    public AgencyProductInfo enable(AgencyProductDto productDto) {
        AgencyProductInfo info = repository.findOne(productDto.getId());
        if(info == null)
        {
            CwException.throwIt("产品不存在");
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
     * @param productDto
     * @return
     */
    public Page<AgencyProductDto> findByCondition(AgencyProductDto productDto) {
        String[] fields = {"id"};
        productDto.setSortFields(fields);
        productDto.setSortDirection(Sort.Direction.DESC);
        Specification<AgencyProductInfo> supplierSpecification = (Root<AgencyProductInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            //产品名称
            if(!Objects.isNull(productDto.getName())&&!"".equals(productDto.getName()))
            {
                predicates.add(cb.like(root.get("name"),"%"+productDto.getName()+"%"));
            }
            if(!Objects.isNull(productDto.getIsValid()) && productDto.getIsValid() == 1) {
                predicates.add(cb.equal(root.get("isValid"), Boolean.TRUE));
            } else if(!Objects.isNull(productDto.getIsValid()) && productDto.getIsValid() == 0) {
                predicates.add(cb.equal(root.get("isValid"), Boolean.FALSE));
            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return Pages.map(repository.findAll(supplierSpecification, productDto.toPage()),AgencyProductDto.class);
    }
}
