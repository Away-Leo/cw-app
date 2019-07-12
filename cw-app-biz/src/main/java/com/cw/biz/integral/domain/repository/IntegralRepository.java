package com.cw.biz.integral.domain.repository;

import com.cw.biz.integral.domain.entity.Integral;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 积分
 * Created by dujy on 2017-05-20.
 */
public interface IntegralRepository extends JpaRepository<Integral,Long>{

    Integral findByUserId(Long id);
}
