package com.cw.biz.channel.domain.service;

import com.cw.biz.channel.domain.entity.AppMarket;
import com.cw.biz.channel.domain.repository.AppMarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 应用市场领域服务
 * Created by dujy on 2017-08-30.
 */
@Service
public class AppMarketDomainService {

    @Autowired
    private AppMarketRepository repository;

    /**
     * 按条件查询渠道列表
     * @return
     */
    public List<AppMarket> findAll()
    {
        return repository.findAll();
    }

    /**
     * 按条件查询渠道列表
     * @return
     */
    public AppMarket findByChannelNo(String channelNo){
        return repository.findByCode(channelNo);
    }

}
