package com.cw.biz.integral.domain.repository;

import com.cw.biz.integral.domain.entity.IntegralInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 邀请成功借款人员明细
 * Created by dujy on 2017-05-20.
 */
public interface IntegralInviteRepository extends JpaRepository<IntegralInvite,Long>{

    List<IntegralInvite> findByUserId(Long id);
}
