package com.cw.biz.product.app.service;

import com.cw.biz.CPContext;
import com.cw.biz.common.dto.Pages;
import com.cw.biz.log.app.LogEnum;
import com.cw.biz.log.app.dto.LogDto;
import com.cw.biz.product.app.dto.*;
import com.cw.biz.product.domain.entity.Product;
import com.cw.biz.product.domain.entity.ProductAuditVersion;
import com.cw.biz.product.domain.entity.ProductStore;
import com.cw.biz.product.domain.entity.ProductVersion;
import com.cw.biz.product.domain.service.ProductDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ProductAppService {

    @Autowired
    private ProductDomainService productDomainService;

    /**
     * 新增产品
     * @param productDto
     * @return
     */
    public Long create(ProductDto productDto)
    {
        return productDomainService.create(productDto).getId();
    }

    /**
     * 新增产品
     * @param productDto
     * @return
     */
    public Long update(ProductDto productDto)
    {
        return productDomainService.update(productDto).getId();
    }

    /**
     * 停用启用产品
     * @param productDto
     * @return
     */
    public void enable(ProductVersionDto productDto){
        productDomainService.enable(productDto);
    }


    /**
     * 后台产品管理是查询数据
     * @param id
     * @return
     */
    public ProductDto findProductById(Long id){
        Product product = productDomainService.findById(id);
        return product.to(ProductDto.class);
    }

    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public ProductDto findById(Long id){
        ProductDto productDto = new ProductDto();
        Product product = productDomainService.findById(id);
        if(product!=null){
            productDto =  product.to(ProductDto.class);
            if(productDto.getIsHidden()==2&&productDto.getLimitUserTop()<productDto.getTodayApplyUser()){
                //设置流量控制需要跳转的Url 20171117屏蔽跳转功能
//                if(productDto.getJumpUrl()!=null&&!"0".equals(productDto.getJumpUrl())&&!"".equals(productDto.getJumpUrl())) {
//                    ProductDto jumpProductDto = productDomainService.findById(Long.parseLong(productDto.getJumpUrl())).to(ProductDto.class);
//                    if(jumpProductDto!=null) {
//                        productDto.setUrl(jumpProductDto.getUrl());
//                        productDto.setId(jumpProductDto.getId());
//                    }
//                }
            }
        }

        return productDto;
    }

    /**
     * 按条件搜索产品列表v1.0秒必贷
     * @param dto
     * @return
     */
    public Page<ProductListDto> findByCondition(ProductSearchDto dto){
        return Pages.map(productDomainService.findByCondition(dto),ProductListDto.class);
    }

    /**
     * 按条件搜索产品V1.1和借多少
     * @param dto
     * @return
     */
    public Page<ProductListDto> findByConditionVersion(ProductSearchDto dto)
    {
        return Pages.map(productDomainService.findByConditionVersion(dto),ProductListDto.class);
    }


    /**
     * 查询全部产品
     * @return
     */
    public List<ProductDto> findAll(ProductSearchDto searchDto){
        List<ProductDto> productDtoList = new ArrayList<ProductDto>();
        List<Product> productList = productDomainService.findAll(searchDto).getContent();
        for(Product product:productList){
            productDtoList.add(product.to(ProductDto.class));
        }
        return productDtoList;
    }


    /**
     * 借款申请
     * @param dto
     */
    public void applyLoan(LogDto dto){
        if(dto.getUserId()==null) {
            dto.setUserId(CPContext.getContext().getSeUserInfo().getrId());
        }
        dto.setType(LogEnum.APPLY_LOAN);
        productDomainService.applyLoan(dto);
    }

    /**
     * 查询产品数据是否有更新
     * @return
     */
    public ProductVersionDto findProductVersion(String channelNo)
    {
        ProductVersionDto productVersionDto = new ProductVersionDto();
        ProductVersion productVersion = productDomainService.findProductVersion(channelNo);
        if(!Objects.isNull(productVersion))
        {
            productVersionDto = productVersion.to(ProductVersionDto.class);
        }

        return productVersionDto;
    }

    /**
     * 查询产品线下门店
     * @return
     */
    public List<ProductStoreDto> findStoreByName(String name,String appName){
        List<ProductStoreDto> productDtoList = new ArrayList<ProductStoreDto>();
        List<ProductStore> productList = productDomainService.findStoreByName(name,appName);
        for(ProductStore product:productList){
            productDtoList.add(product.to(ProductStoreDto.class));
        }
        return productDtoList;
    }

    /**
     * 查找推荐产品
     * @return
     */
    public List<ProductListDto> findRecommendProduct(ProductSearchDto productSearchDto)
    {
        List<ProductListDto> productDtoList = new ArrayList<ProductListDto>();
        List<Product> productList = productDomainService.findRecommendProduct(productSearchDto).getContent();
        for(Product product:productList){
            productDtoList.add(product.to(ProductListDto.class));
        }
        return productDtoList;
    }


    /**
     * 保存产品排序
     * @return
     */
    public Boolean saveProductSort(ProductSortDto productSortDto){
        productDomainService.saveProductSort(productSortDto);
        return Boolean.TRUE;
    }

    /**
     * 保存产品排序
     * @return
     */
    public Boolean saveProductConfig(ProductSortDto productSortDto){
        productDomainService.saveProductConfig(productSortDto);
        return Boolean.TRUE;
    }

    /**
     * 定时任务执行上下架功能
     */
    public void executeJob(){
        productDomainService.executeJob();
    }

    /**
     * 产品申请推荐功能
     * @param productId
     * @return
     */
    public List<ProductRecommendListDto> findRecommendProductByProductId(Long productId,String versionNo,String channelNo){
        List<ProductRecommendListDto> productList = productDomainService.findRecommendProductByProductId(productId,versionNo,channelNo);
        return productList;
    }

    /**
     * 产品排序列表
     * @param dto
     * @return
     */
    public List<ProductListDto> findByProductSortList(ProductSearchDto dto){
        return productDomainService.findByProductSortList(dto);
    }

    /**
     * 产品申请推荐功能
     * @param productId
     * @param productName
     * @return
     */
    public List<ProductRecommendListDto> findNoRecommendByProductId(Long productId,String productName){
        List<ProductRecommendListDto> productList = productDomainService.findNoRecommendByProductId(productId,productName);
        return productList;
    }

    /**
     * 产品推荐设置
     * @param recommendListDto
     * @return
     */
    public Boolean productSetRecommend(ProductRecommendListDto recommendListDto){
        productDomainService.productSetRecommend(recommendListDto);
        return Boolean.TRUE;
    }

    /**
     * 产品推荐功能开关
     * @param recommendListDto
     * @return
     */
    public Boolean updateProductRecommendFlag(ProductDto recommendListDto){
        productDomainService.updateProductRecommendFlag(recommendListDto);
        return Boolean.TRUE;
    }

    /**
     * 查询审核版本
     * @param versionNo
     * @return
     */
    public Boolean findVersionData(String versionNo){
        return productDomainService.checkVersionShowData(versionNo);
    }

    /**
     * 查询
     * @param versionNo
     * @return
     */
    public ProductAuditVersionDto getAuditVersion(String versionNo){
        ProductAuditVersionDto productAuditVersionDto = new ProductAuditVersionDto();
        ProductAuditVersion productAuditVersion = productDomainService.getAuditVersion(versionNo);
        if(productAuditVersion!=null){
            productAuditVersionDto = productAuditVersion.to(ProductAuditVersionDto.class);
        }
        return productAuditVersionDto;
    }

}
