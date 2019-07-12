package com.cw.biz.goods.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.goods.app.dto.GoodsDto;
import com.cw.biz.goods.domain.entity.Goods;
import com.cw.biz.goods.domain.repository.GoodsRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 商品服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class GoodsDomainService {

    @Autowired
    private GoodsRepository repository;
    /**
     * 新增商品
     * @param goodsDto
     * @return
     */
    public Goods create(GoodsDto goodsDto)
    {
        Goods goods = new Goods();
        goods.from(goodsDto);
        return repository.save(goods);
    }

    /**
     * 修改商品
     * @param goodsDto
     * @return
     */
    public Goods update(GoodsDto goodsDto)
    {
        Goods goods = repository.findOne(goodsDto.getId());
        if(goods == null){
            CwException.throwIt("商品不存在");
        }
        goods.from(goodsDto);
        repository.save(goods);

        return goods;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public Goods findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 按条件查询产品列表
     * @param goodsDto
     * @return
     */
    public Page<Goods> findByCondition(GoodsDto goodsDto)
    {
        String[] fields = {"showOrder"};
        goodsDto.setSortFields(fields);
        Specification<Goods> supplierSpecification = (Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, goodsDto.toPage());
    }

}
