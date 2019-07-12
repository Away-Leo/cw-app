package com.cw.biz.cms.domain.repository;

import com.cw.biz.cms.domain.entity.Cms;
import com.cw.biz.creditcard.domain.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 贷款攻略
 * Created by dujy on 2017-05-20.
 */
public interface CmsRepository extends JpaRepository<Cms,Long>,JpaSpecificationExecutor<Cms> {

    @Query(value = "select * from cw_cms_article where is_top='1' order by publish_date desc limit 4",nativeQuery = true)
    List<Cms> findByCmsArticle();
}
