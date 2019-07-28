package com.cw.biz.discount.domain.repository;

import com.cw.biz.discount.domain.entity.WholeDisCount;
import com.cw.core.common.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WholeDiscountRepository extends BaseRepository<WholeDisCount,Long> {

    public List<WholeDisCount> findAll();

}
