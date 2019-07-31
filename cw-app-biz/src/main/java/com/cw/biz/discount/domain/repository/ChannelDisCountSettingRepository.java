package com.cw.biz.discount.domain.repository;

import com.cw.biz.discount.app.dto.ChannelDisCountDto;
import com.cw.biz.discount.app.dto.ChannelDisShowDto;
import com.cw.biz.discount.domain.entity.ChannelDisCountSetting;
import com.cw.core.common.base.BaseRepository;
import com.cw.core.common.util.ObjectHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public interface ChannelDisCountSettingRepository extends BaseRepository<ChannelDisCountSetting,Long> {

    default Page<ChannelDisCountSetting> findShowByPage(Pageable pageable, ChannelDisShowDto showDto) throws Exception {
        StringBuffer sql=new StringBuffer("select * from channel_flow_view v where 1=1");
        Map<String,Object> con=new HashMap<>();
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getRegisDate())){
            sql.append(" and v.regis_date =:regisDate ");
            con.put("regisDate",showDto.getRegisDate().trim());
        }
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getChannelName())){
            sql.append(" and v.channel_name like :channelName ");
            con.put("channelName","%"+showDto.getChannelName().trim()+"%");
        }
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getChannelPhone())){
            sql.append(" and v.channel_phone = :channelPhone ");
            con.put("channelPhone",showDto.getChannelPhone().trim());
        }
        sql.append(" order by v.regis_date desc ");
        return findBySqlEntityPage(pageable,sql.toString(),con,ChannelDisCountSetting.class);
    }

    ChannelDisCountSetting findByChannelCode(String channelCode);

    default Page<ChannelDisCountSetting> findByCon(Pageable pageable, ChannelDisCountDto channelDisCountDto){
        StringBuilder hql=new StringBuilder(" from ChannelDisCountSetting where 1=1 ");
        Map<String,Object> conditions=new HashMap<>();
        if(ObjectHelper.isNotEmpty(channelDisCountDto)&&ObjectHelper.isNotEmpty(pageable)){
            if(ObjectHelper.isNotEmpty(channelDisCountDto.getChannelName())){
                hql.append(" and channelName like :channelName ");
                conditions.put("channelName","%"+channelDisCountDto.getChannelName().trim()+"%");
            }
            if(ObjectHelper.isNotEmpty(channelDisCountDto.getChannelPhone())){
                hql.append(" and  channelPhone =:channelPhone ");
                conditions.put("channelPhone",channelDisCountDto.getChannelPhone().trim());
            }
            return findByHqlPage(pageable,hql.toString(),conditions);
        }else{
            return null;
        }
    }
}
