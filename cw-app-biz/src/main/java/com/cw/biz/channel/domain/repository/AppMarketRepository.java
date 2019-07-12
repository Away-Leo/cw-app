package com.cw.biz.channel.domain.repository;

import com.cw.biz.channel.domain.entity.AppMarket;
import com.cw.biz.channel.domain.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 应用市场
 * Created by Administrator on 2017/7/13.
 */
public interface AppMarketRepository extends JpaRepository<AppMarket,Long>,JpaSpecificationExecutor<AppMarket> {

    AppMarket findByCode(String code);
}
