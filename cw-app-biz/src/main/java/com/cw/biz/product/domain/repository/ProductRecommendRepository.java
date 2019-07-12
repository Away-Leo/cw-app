package com.cw.biz.product.domain.repository;

import com.cw.biz.product.domain.entity.ProductRecommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 产品推荐数据
 * Created by dujy on 2017-10-19.
 */
public interface ProductRecommendRepository extends JpaRepository<ProductRecommend,Long>,JpaSpecificationExecutor<ProductRecommend> {
}
