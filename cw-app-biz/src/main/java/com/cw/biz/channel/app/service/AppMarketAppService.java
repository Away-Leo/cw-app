package com.cw.biz.channel.app.service;

import com.cw.biz.channel.app.dto.AppMarketDto;
import com.cw.biz.channel.app.dto.ChannelDto;
import com.cw.biz.channel.domain.entity.AppMarket;
import com.cw.biz.channel.domain.service.AppMarketDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 渠道服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class AppMarketAppService {

    @Autowired
    private AppMarketDomainService appMarketDomainService;
    @Autowired
    private ChannelAppService channelAppService;
    /**
     * 按条件查询渠道
     * @return
     */
    public List<AppMarketDto> findAll()
    {
        List<AppMarketDto> appMarketDtoList = new ArrayList<AppMarketDto>();
        List<ChannelDto> channelDtoList = channelAppService.findAll();
        List<AppMarket> appMarketList = appMarketDomainService.findAll();
        //应用市场
        for(AppMarket appMarket : appMarketList){
            appMarketDtoList.add(appMarket.to(AppMarketDto.class));
        }
        //渠道数据
        for(ChannelDto channelDto:channelDtoList){
            AppMarketDto appMarketDto = new AppMarketDto();
            appMarketDto.setCode(channelDto.getCode());
            appMarketDto.setName(channelDto.getName());
            appMarketDtoList.add(appMarketDto);
        }
        return appMarketDtoList;
    }

    /**
     * 查询渠道
     * @param channelNo
     * @return
     */
    public AppMarketDto findByChannelNo(String channelNo){

        return appMarketDomainService.findByChannelNo(channelNo).to(AppMarketDto.class);
    }

}
