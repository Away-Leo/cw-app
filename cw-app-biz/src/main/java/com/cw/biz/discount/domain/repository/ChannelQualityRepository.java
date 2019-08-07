package com.cw.biz.discount.domain.repository;

import com.cw.biz.discount.app.dto.ChannelQualityDto;
import com.cw.biz.discount.domain.entity.ChannelQuality;
import com.cw.core.common.base.BaseRepository;
import com.cw.core.common.util.ObjectHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public interface ChannelQualityRepository extends BaseRepository<ChannelQuality, Long> {


    ChannelQuality findByChannelCodeAndFlowTime(String channelCode,String flowTime);

    default Page<ChannelQuality> findByCon(Pageable pageable, ChannelQualityDto channelQualityDto) {
        StringBuilder hql = new StringBuilder(" from ChannelQuality where 1=1 ");
        Map<String, Object> conditions = new HashMap<>();
        if (ObjectHelper.isNotEmpty(channelQualityDto) && ObjectHelper.isNotEmpty(pageable)) {
            if (ObjectHelper.isNotEmpty(channelQualityDto.getChannelName())) {
                hql.append(" and channelName like :channelName ");
                conditions.put("channelName", "%" + channelQualityDto.getChannelName().trim() + "%");
            }
            if (ObjectHelper.isNotEmpty(channelQualityDto.getChannelPhone())) {
                hql.append(" and  channelPhone =:channelPhone ");
                conditions.put("channelPhone", channelQualityDto.getChannelPhone().trim());
            }
            if (ObjectHelper.isNotEmpty(channelQualityDto.getChannelCode())) {
                hql.append(" and  channelCode =:channelCode ");
                conditions.put("channelCode", channelQualityDto.getChannelCode().trim());
            }
            if(ObjectHelper.isNotEmpty(channelQualityDto.getFlowTimeStart())){
                hql.append(" and  flowTime >= :startTime");
                conditions.put("startTime", channelQualityDto.getFlowTimeStart().replaceAll("-",""));
            }
            if(ObjectHelper.isNotEmpty(channelQualityDto.getFlowTimeEnd())){
                hql.append(" and  flowTime <= :endTime");
                conditions.put("endTime", channelQualityDto.getFlowTimeEnd().replaceAll("-",""));
            }
            return findByHqlPage(pageable, hql.toString(), conditions);
        } else {
            return null;
        }
    }
}
