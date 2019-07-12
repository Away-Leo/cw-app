package com.cw.biz.product.app.service;

import com.cw.biz.product.app.dto.ProductCooperativePriceDto;
import com.cw.biz.product.app.dto.ProductSubTypeDto;
import com.cw.biz.product.domain.entity.ProductCooperativePrice;
import com.cw.biz.product.domain.entity.ProductSubType;
import com.cw.biz.product.domain.service.ProductCooperativePriceDomainService;
import com.cw.biz.product.domain.service.ProductSubTypeDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品分类
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ProductPriceAppService {

    @Autowired
    private ProductCooperativePriceDomainService domainService;

    /**
     * 新增产品价格
     * @param productCooperativePriceDto
     * @return
     */
    public Long create(ProductCooperativePriceDto productCooperativePriceDto)
    {
        return domainService.create(productCooperativePriceDto).getId();
    }

    /**
     * 修改产品价格
     * @param productCooperativePriceDto
     * @return
     */
    public Long update(ProductCooperativePriceDto productCooperativePriceDto)
    {
        return domainService.update(productCooperativePriceDto).getId();
    }

    /**
     * 删除产品价格
     * @param priceDto
     * @return
     */
    public void delete(ProductCooperativePriceDto priceDto)
    {
        domainService.delete(priceDto.getId());
    }
    /**
     * 查询产品价格详情
     * @param id
     * @return
     */
    public ProductCooperativePriceDto findById(Long id)
    {
        return domainService.findById(id).to(ProductCooperativePriceDto.class);
    }

    /**
     * 查询产品类型
     * @return
     */
    public List<ProductCooperativePriceDto> findAll(Long productId)
    {
        List<ProductCooperativePriceDto> productCooperativePriceDtos = new ArrayList<ProductCooperativePriceDto>();
        List<ProductCooperativePrice> productSubTypeList = domainService.findAllByProductId(productId);
        for(ProductCooperativePrice productCooperativePrice:productSubTypeList){
            productCooperativePriceDtos.add(productCooperativePrice.to(ProductCooperativePriceDto.class));
        }
        return productCooperativePriceDtos;
    }

}
