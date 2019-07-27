package com.cw.biz.banner.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.banner.app.dto.BannerDto;
import com.cw.biz.banner.domain.entity.Banner;
import com.cw.biz.banner.domain.repository.BannerRepository;
import com.cw.biz.home.app.service.AppInfoAppService;
import com.cw.biz.product.domain.entity.Product;
import com.cw.biz.product.domain.entity.ProductAuditVersion;
import com.cw.biz.product.domain.repository.ProductAuditRepository;
import com.cw.biz.product.domain.service.ProductDomainService;
import com.cw.core.common.util.ObjectHelper;
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
 * banner查询服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class BannerDomainService {

    @Autowired
    private BannerRepository repository;
    @Autowired
    private ProductAuditRepository productAuditRepository;
    @Autowired
    private AppInfoAppService appInfoAppService;
    @Autowired
    private ProductDomainService productDomainService;
    /**
     * 新增banner图标
     * @param bannerDto
     * @return
     */
    public Banner create(BannerDto bannerDto)
    {
        Banner banner = new Banner();
        banner.from(bannerDto);
        banner=bindProduct(banner);
        return repository.save(banner);
    }

    private Banner bindProduct(Banner banner){
        Product product=this.productDomainService.findByUrl(banner.getJumpUrl().trim());
        if(ObjectHelper.isNotEmpty(product)){
            banner.setProductId(product.getId());
            banner.setProductName(product.getName());
        }
        return banner;
    }

    /**
     * 修改banner图标
     * @param bannerDto
     * @return
     */
    public Banner update(BannerDto bannerDto)
    {
        Banner banner = repository.findOne(bannerDto.getId());
        if(banner == null){
            CwException.throwIt("banner不存在");
        }
        banner.from(bannerDto);
        banner=bindProduct(banner);
        repository.save(banner);
        return banner;
    }


    /**
     * 银行停用、启用
     * @param bannerDto
     * @return
     */
    public Banner enable(BannerDto bannerDto)
    {
        Banner banner = repository.findOne(bannerDto.getId());
        if(banner == null)
        {
            CwException.throwIt("银行不存在");
        }
        if(banner.getIsValid()) {
            banner.setIsValid(Boolean.FALSE);
        }else{
            banner.setIsValid(Boolean.TRUE);
        }
        return banner;
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public Banner findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 按条件查询产品列表
     * @param bannerDto
     * @return
     */
    public Page<Banner> findByCondition(BannerDto bannerDto)
    {
        String[] fields = {"showOrder"};
        bannerDto.setSortFields(fields);
        Specification<Banner> supplierSpecification = (Root<Banner> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = Lists.newArrayListWithCapacity(20);
            predicates.add(cb.equal(root.get("isValid"),Boolean.TRUE));
            if(!Objects.isNull(bannerDto.getName())&&!"".equals(bannerDto.getName())){
                predicates.add(cb.like(root.get("name"),"%"+bannerDto.getName()+"%"));
            }
            //根据位置查询banner图
            if(!Objects.isNull(bannerDto.getBannerPosition())&&!"".equals(bannerDto.getBannerPosition()))
            {
                predicates.add(cb.equal(root.get("bannerPosition"),bannerDto.getBannerPosition()));
            }

            query.where(cb.and(predicates.toArray(new Predicate[0])));
            return query.getRestriction();
        };
        return repository.findAll(supplierSpecification, bannerDto.toPage());
    }


    private Boolean checkVersionShowData(String versionNo){
        ProductAuditVersion productAuditVersion = productAuditRepository.findByDataVersion(versionNo);
        if(Objects.isNull(productAuditVersion))
        {
            return Boolean.FALSE;
        }
        return productAuditVersion.getIsAudit();
    }

    /**
     * 根据位置查询banner图片
     * @return
     */
    public List<Banner> findByBannerPosition(String position,String versionNo) {
        if(position.contains("wx")){
            ProductAuditVersion productAuditVersion =productAuditRepository.findByDataVersion(versionNo);
            String appName;
            String wx="ls";
            if(productAuditVersion!=null) {
                appName = productAuditVersion.getAppName();
                wx = appInfoAppService.findByCode(appName).getOrgName();
            }
            return repository.findWxBanner(wx+"_"+position);
        }else{
            if(versionNo!=null){
                if(!"1".equals(versionNo)){
                    Boolean flag = checkVersionShowData(versionNo);
                    if(flag) {
                        return repository.findByBannerPosition(position);
                    }else{
                        return repository.findByAuditBannerPosition();
                    }
                }else{
                    return repository.findWxBanner(position);
                }
            }else{
                return repository.findByBannerPosition(position);
            }
        }
    }
}
