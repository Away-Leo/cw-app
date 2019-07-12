package com.cw.biz.cms.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.banner.app.dto.BannerDto;
import com.cw.biz.banner.domain.entity.Banner;
import com.cw.biz.banner.domain.repository.BannerRepository;
import com.cw.biz.cms.app.dto.CmsDto;
import com.cw.biz.cms.domain.entity.Cms;
import com.cw.biz.cms.domain.repository.CmsRepository;
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
import java.util.Objects;

/**
 * 贷款攻略服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class CmsDomainService {

    @Autowired
    private CmsRepository repository;
    /**
     * 新增贷款攻略文章
     * @param cmsDto
     * @return
     */
    public Cms create(CmsDto cmsDto)
    {
        Cms cms = new Cms();
        cms.from(cmsDto);
        return repository.save(cms);
    }

    /**
     * 修改贷款攻略文章
     * @param cmsDto
     * @return
     */
    public Cms update(CmsDto cmsDto)
    {
        Cms cms = repository.findOne(cmsDto.getId());
        if(cms == null)
        {
            CwException.throwIt("banner不存在");
        }
        cms.from(cmsDto);
        repository.save(cms);

        return cms;
    }


    /**
     * 贷款攻略停用启用
     * @param cmsDto
     * @return
     */
    public Cms enable(CmsDto cmsDto)
    {
        Cms banner = repository.findOne(cmsDto.getId());
        if(banner == null)
        {
            CwException.throwIt("贷款攻略不存在");
        }

        return banner;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public Cms findById(Long id)
    {
        Cms cms = repository.findOne(id);
        cms.setClickNum(cms.getClickNum()+1);
        return cms;
    }

    /**
     * 按条件查询产品列表
     * @param cmsDto
     * @return
     */
    public Page<Cms> findByCondition(CmsDto cmsDto)
    {
        String[] fields = {"publishDate"};
        cmsDto.setSortFields(fields);
        Specification<Cms> supplierSpecification = (Root<Cms> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));
            if(cmsDto.getNewsType()!=null){
                predicates.add(cb.equal(root.get("memo"),cmsDto.getNewsType()));
            }
            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, cmsDto.toPage());
    }

    /**
     * 查询列表数据
     * @return
     */
    public List<Cms> findAll()
    {
        return repository.findByCmsArticle();
    }

}
