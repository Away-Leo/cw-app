package com.cw.biz.operatecost.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.operatecost.app.dto.OperateCostDto;
import com.cw.biz.operatecost.domain.entity.OperateCost;
import com.cw.biz.operatecost.domain.repository.OperateCostRepository;
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
 * 运营成本领域服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class OperateCostDomainService {

    @Autowired
    private OperateCostRepository repository;

    /**
     * 新增运营成本
     * @param operateCostDto
     * @return
     */
    public OperateCost create(OperateCostDto operateCostDto){
        OperateCost operateCost = new OperateCost();
        operateCost.from(operateCostDto);
        return repository.save(operateCost);
    }

    /**
     * 修改运营成本
     * @param operateCostDto
     * @return
     */
    public OperateCost update(OperateCostDto operateCostDto){
        OperateCost operateCost = repository.findOne(operateCostDto.getId());
        if(operateCost == null){
            CwException.throwIt("banner不存在");
        }
        operateCost.from(operateCostDto);
        repository.save(operateCost);

        return operateCost;
    }

    /**
     * 查询运营成本详情
     * @param id
     * @return
     */
    public OperateCost findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 按条件查询运营消耗列表
     * @param operateCostDto
     * @return
     */
    public Page<OperateCost> findByCondition(OperateCostDto operateCostDto){
        String[] fields = {"dayId"};
        operateCostDto.setSortFields(fields);
        operateCostDto.setSortDirection(Sort.Direction.DESC);
        Specification<OperateCost> supplierSpecification = (Root<OperateCost> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));

            if(!Objects.isNull(operateCostDto.getFeeItem())&&!"all".equals(operateCostDto.getFeeItem())){
                predicates.add(cb.equal(root.get("feeItem"),operateCostDto.getFeeItem()));
            }

            if(!Objects.isNull(operateCostDto.getChannel())&&!"all".equals(operateCostDto.getChannel())){
                predicates.add(cb.equal(root.get("channel"),operateCostDto.getChannel()));
            }

            if(!Objects.isNull(operateCostDto.getAppName())&&!"all".equals(operateCostDto.getAppName())){
                predicates.add(cb.equal(root.get("appName"),operateCostDto.getAppName()));
            }

            if(!Objects.isNull(operateCostDto.getDayId())&&!"".equals(operateCostDto.getDayId())){
                predicates.add(cb.like(root.get("dayId"),operateCostDto.getDayId().substring(0,7)+"%"));
            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, operateCostDto.toPage());
    }
}
