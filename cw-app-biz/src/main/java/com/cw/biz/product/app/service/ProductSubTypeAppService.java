package com.cw.biz.product.app.service;

import com.cw.biz.product.app.dto.ProductSubTypeDto;
import com.cw.biz.product.domain.entity.ProductSubType;
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
public class ProductSubTypeAppService {

    @Autowired
    private ProductSubTypeDomainService productSubTypeDomainService;

    /**
     * 新增产品类型
     * @param productSubTypeDto
     * @return
     */
    public Long create(ProductSubTypeDto productSubTypeDto)
    {
        return productSubTypeDomainService.create(productSubTypeDto).getId();
    }

    /**
     * 修改产品类型
     * @param productSubTypeDto
     * @return
     */
    public Long update(ProductSubTypeDto productSubTypeDto)
    {
        return productSubTypeDomainService.update(productSubTypeDto).getId();
    }

    /**
     * 查询产品类型详情
     * @param id
     * @return
     */
    public ProductSubTypeDto findById(Long id)
    {
        return productSubTypeDomainService.findById(id).to(ProductSubTypeDto.class);
    }

    /**
     * 查询产品类型
     * @return
     */
    public List<ProductSubTypeDto> findAll(String appName)
    {
        List<ProductSubTypeDto> productSubTypeDtoList = new ArrayList<ProductSubTypeDto>();
        List<ProductSubType> productSubTypeList = productSubTypeDomainService.findAll(appName);
        for(ProductSubType productSubType:productSubTypeList){
            productSubTypeDtoList.add(productSubType.to(ProductSubTypeDto.class));
        }
        return productSubTypeDtoList;
    }

}
