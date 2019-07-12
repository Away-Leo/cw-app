package com.cw.biz.product.domain.repository;

import com.cw.biz.product.domain.entity.ProductCooperativePrice;
import com.cw.biz.product.domain.entity.ProductVersion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 产品合作价格
 * Created by dujy on 2017-05-20.
 */
public interface ProductCooperativePriceRepository extends JpaRepository<ProductCooperativePrice,Long>{

    List<ProductCooperativePrice> findByProductId(Long productId);
}
