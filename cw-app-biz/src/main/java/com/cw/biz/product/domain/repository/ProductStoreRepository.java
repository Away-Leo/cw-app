package com.cw.biz.product.domain.repository;

import com.cw.biz.product.domain.entity.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 产品门店
 * Created by dujy on 2017-05-20.
 */
public interface ProductStoreRepository extends JpaRepository<ProductStore,Long>{
    @Query(value = "select * from cw_product_store where store_address like ?1 and app_name=?2",nativeQuery = true)
    List<ProductStore> findByStoreName(String name,String appName);
}
