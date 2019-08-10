package com.cw.biz.discount.domain.repository;

import com.cw.biz.discount.domain.entity.ChannelUserIp;
import com.cw.core.common.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChannelUserIpRepository extends BaseRepository<ChannelUserIp, Long> {


    ChannelUserIp findByChannelCodeAndUserIp(String channelCode,String userIp);

    @Query(" select count(userIp) from  ChannelUserIp where channelCode=? and flowTime=? ")
    Long findByChannelCodeAndFlowTime(String channelCode,String flowTime);

}
