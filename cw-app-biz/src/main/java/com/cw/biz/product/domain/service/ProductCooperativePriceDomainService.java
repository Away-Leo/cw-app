package com.cw.biz.product.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.product.app.dto.ProductCooperativePriceDto;
import com.cw.biz.product.domain.entity.ProductCooperativePrice;
import com.cw.biz.product.domain.repository.ProductCooperativePriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产品合作价格
 * Created by dujy on 2017-05-20.
 */
@Service
public class ProductCooperativePriceDomainService {

    @Autowired
    private ProductCooperativePriceRepository repository;
    /**
     * 新增产品
     * @param productDto
     * @return
     */
    public ProductCooperativePrice create(ProductCooperativePriceDto productDto)
    {
        ProductCooperativePrice product = new ProductCooperativePrice();
        product.from(productDto);
        return repository.save(product);
    }

    /**
     * 修改产品
     * @param productSubTypeDto
     * @return
     */
    public ProductCooperativePrice update(ProductCooperativePriceDto productSubTypeDto)
    {
        ProductCooperativePrice product = repository.findOne(productSubTypeDto.getId());
        if(product == null)
        {
            CwException.throwIt("产品不存在");
        }
        product.from(productSubTypeDto);
        repository.save(product);
        return product;
    }

    /**
     * 删除数据
     * @param id
     */
    public void delete(Long id){
        repository.delete(id);
    }
    /**
     * 查询产品详情
     * @param id
     * @return
     */
    public ProductCooperativePrice findById(Long id)
    {
        ProductCooperativePrice product = repository.findOne(id);
        return product;
    }

    /**
     * 查询产品价格
     * @param productId
     * @return
     */
    public List<ProductCooperativePrice> findAllByProductId(Long productId)
    {
        return repository.findByProductId(productId);
    }

}
