package com.cw.biz.product.domain.repository;

import com.cw.biz.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 产品数据
 * Created by dujy on 2017-05-20.
 */
public interface ProductRepository extends JpaRepository<Product,Long>,JpaSpecificationExecutor<Product> {

    Product findByUrl(String url);
}
