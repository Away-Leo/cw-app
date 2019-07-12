package com.cw.biz.apply.domain.repository;

import com.cw.biz.apply.domain.entity.ApplyMessage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 产品申请结果
 * Created by Administrator on 2017/6/13.
 */
public interface ApplyMessageRepository extends JpaRepository<ApplyMessage,Long> {
}
