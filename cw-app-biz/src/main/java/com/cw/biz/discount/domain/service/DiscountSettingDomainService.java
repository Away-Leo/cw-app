package com.cw.biz.discount.domain.service;

import com.cw.biz.discount.app.dto.ChannelDisShowDto;
import com.cw.biz.discount.app.dto.WholeDisCountDto;
import com.cw.biz.discount.domain.entity.ChannelDisCountSetting;
import com.cw.biz.discount.domain.entity.WholeDisCount;
import com.cw.biz.discount.domain.repository.ChannelDisCountSettingRepository;
import com.cw.biz.discount.domain.repository.WholeDiscountRepository;
import com.cw.biz.jdbc.JdbcPage;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.core.common.base.BaseDomainService;
import com.cw.core.common.util.ObjectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DiscountSettingDomainService extends BaseDomainService<ChannelDisCountSettingRepository,ChannelDisCountSetting, ChannelDisShowDto> {

    private final ChannelDisCountSettingRepository settingRepository;

    private WholeDisCountDto wholeDisCount;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DiscountSettingDomainService(ChannelDisCountSettingRepository settingRepository,DiscountDomainService discountDomainService) {
        this.settingRepository = settingRepository;
        wholeDisCount= discountDomainService.findDisCount();
    }

    private JdbcPage<ChannelDisShowDto> findChannelDisShowDtoByCondition(ChannelDisShowDto showDto){
        StringBuffer sql=new StringBuffer("select * from channel_flow_view v where 1=1");
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getRegisDate())){
            sql.append(" and v.regis_date = '"+showDto.getRegisDate().trim()+"'");
        }
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getChannelName())){
            sql.append(" and v.channel_name like '%"+showDto.getChannelName().trim()+"%' ");
        }
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getChannelPhone())){
            sql.append(" and v.channel_phone = '"+showDto.getChannelPhone().trim()+"' ");
        }
        sql.append(" order by v.regis_date desc ");

        return new JdbcPage(sql.toString(),showDto.getPage()+1,showDto.getSize(),jdbcTemplate,ChannelDisShowDto.class);
    }


    public Page<ChannelDisShowDto> findByCondition(Pageable pageable,ChannelDisShowDto showDto) throws Exception {
        JdbcPage<ChannelDisShowDto> sourceData=this.findChannelDisShowDtoByCondition(showDto);
        List<ChannelDisShowDto> sourceList=sourceData.getResult();
        for(ChannelDisShowDto temp:sourceList){
            if(ObjectHelper.isNotEmpty(temp.getStartNum())&&ObjectHelper.isNotEmpty(temp.getPercent())){
                if(ObjectHelper.isEmpty(temp.getTrueNum())){
                    temp.setTrueNum("0");temp.setShowNum("0");
                }else {
                if(Integer.valueOf(temp.getTrueNum())>temp.getStartNum()){
                    String tempCount=temp.getStartNum()+(Integer.valueOf(temp.getTrueNum())-temp.getStartNum())*(1-temp.getPercent()/100)+"";
                    temp.setShowNum(tempCount.substring(0,tempCount.indexOf(".")));
                }else{
                    temp.setShowNum(temp.getTrueNum());
                }}
            }else{
                if(ObjectHelper.isEmpty(temp.getTrueNum())){
                    temp.setShowNum("0");temp.setTrueNum("0");
                }else{
                    if(Integer.valueOf(temp.getTrueNum())>wholeDisCount.getStartNum()){
                        String tempCount=wholeDisCount.getStartNum()+(Integer.valueOf(temp.getTrueNum())-wholeDisCount.getStartNum())*(1-wholeDisCount.getPercent()/100)+"";
                        temp.setShowNum(tempCount.substring(0,tempCount.indexOf(".")));
                    }else {
                        temp.setShowNum(temp.getTrueNum());
                    }
                }
            }
            temp.setUniId(temp.getChannelCode()+temp.getRegisDate());
        }
        return new PageImpl<ChannelDisShowDto>(sourceList,pageable,sourceData.getTotalCount());
    }

    public ChannelDisShowDto saveOrUpdata(ChannelDisShowDto disShowDto) throws Exception {
        if(ObjectHelper.isNotEmpty(disShowDto.getId())){
            ChannelDisCountSetting old= settingRepository.findOne(disShowDto.getId());
            old.setStartNum(disShowDto.getStartNum());
            old.setPercent(disShowDto.getPercent());
            settingRepository.updateEntity(old);
            return disShowDto;
        }else{
            return saveOrUpdateData(disShowDto,ChannelDisCountSetting.class);
        }
    }

}
