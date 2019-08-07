package com.cw.biz.discount.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.apply.domain.entity.Apply;
import com.cw.biz.apply.domain.service.ApplyDomainService;
import com.cw.biz.discount.app.dto.ChannelDisCountDto;
import com.cw.biz.discount.app.dto.ChannelDisShowDto;
import com.cw.biz.discount.app.dto.ChannelQualityDto;
import com.cw.biz.discount.app.dto.WholeDisCountDto;
import com.cw.biz.discount.domain.entity.ChannelDisCountSetting;
import com.cw.biz.discount.domain.entity.ChannelQuality;
import com.cw.biz.discount.domain.entity.WholeDisCount;
import com.cw.biz.discount.domain.repository.ChannelDisCountSettingRepository;
import com.cw.biz.discount.domain.repository.ChannelQualityRepository;
import com.cw.biz.discount.domain.repository.WholeDiscountRepository;
import com.cw.biz.jdbc.JdbcPage;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.user.domain.entity.UserEntity;
import com.cw.biz.user.domain.service.UserDomainService;
import com.cw.core.common.base.BaseDomainService;
import com.cw.core.common.base.ENUM_EXCEPTION;
import com.cw.core.common.util.DateHelper;
import com.cw.core.common.util.ObjectHelper;
import com.cw.core.common.util.ObjectProperUtil;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class DiscountSettingDomainService extends BaseDomainService<ChannelDisCountSettingRepository,ChannelDisCountSetting, ChannelDisShowDto> {

    private final ChannelDisCountSettingRepository settingRepository;

    private WholeDisCountDto wholeDisCount;

    private final ApplyDomainService applyDomainService;

    private final ChannelQualityRepository channelQualityRepository;

    private final UserDomainService userDomainService;

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DiscountSettingDomainService(ChannelDisCountSettingRepository settingRepository, DiscountDomainService discountDomainService, ApplyDomainService applyDomainService, JdbcTemplate jdbcTemplate, ChannelQualityRepository channelQualityRepository, UserDomainService userDomainService) {
        this.settingRepository = settingRepository;
        wholeDisCount= discountDomainService.findDisCount();
        this.applyDomainService = applyDomainService;
        this.jdbcTemplate = jdbcTemplate;
        this.channelQualityRepository = channelQualityRepository;
        this.userDomainService = userDomainService;
    }

    private JdbcPage<ChannelDisShowDto> findChannelDisShowDtoByCondition(ChannelDisShowDto showDto){
        StringBuffer sql=new StringBuffer("select * from channel_flow_view v where 1=1");
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getRegisDate())){
            sql.append(" and v.regis_date = '"+showDto.getRegisDate().trim()+"'");
        }
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getChannelName())){
            sql.append(" and v.channel_name like '%"+showDto.getChannelName().trim()+"%' ");
        }
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getChannelCode())){
            sql.append(" and v.channel_code = '"+showDto.getChannelCode().trim()+"' ");
        }
        if(ObjectHelper.isNotEmpty(showDto)&&ObjectHelper.isNotEmpty(showDto.getChannelPhone())){
            sql.append(" and v.channel_phone = '"+showDto.getChannelPhone().trim()+"' ");
        }
        sql.append(" order by v.regis_date desc ");

        return new JdbcPage(sql.toString(),showDto.getPage()+1,showDto.getSize(),jdbcTemplate,ChannelDisShowDto.class);
    }


    public Page<ChannelDisShowDto> findChannelByCondition(Pageable pageable,ChannelDisShowDto showDto) throws Exception {
        JdbcPage<ChannelDisShowDto> sourceData=this.findChannelDisShowDtoByCondition(showDto);
        List<ChannelDisShowDto> sourceList=sourceData.getResult();
        for(ChannelDisShowDto temp:sourceList){
            temp=countShow(temp);
            temp.setUniId(temp.getChannelCode()+temp.getRegisDate());
            temp.setPercent(null);
            temp.setStartNum(null);
            temp.setTrueNum(null);
        }
        return new PageImpl<ChannelDisShowDto>(sourceList,pageable,sourceData.getTotalCount());
    }

    private ChannelDisShowDto countShow(ChannelDisShowDto source){
        if(ObjectHelper.isNotEmpty(source.getStartNum())&&ObjectHelper.isNotEmpty(source.getPercent())){
            if(ObjectHelper.isEmpty(source.getTrueNum())){
                source.setTrueNum("0");source.setShowNum("0");
            }else {
                if(Integer.valueOf(source.getTrueNum())>source.getStartNum()){
                    String tempCount=source.getStartNum()+(Integer.valueOf(source.getTrueNum())-source.getStartNum())*(1-source.getPercent()/100)+"";
                    source.setShowNum(tempCount.substring(0,tempCount.indexOf(".")));
                }else{
                    source.setShowNum(source.getTrueNum());
                }}
        }else{
            if(ObjectHelper.isEmpty(source.getTrueNum())){
                source.setShowNum("0");source.setTrueNum("0");
            }else{
                if(Integer.valueOf(source.getTrueNum())>wholeDisCount.getStartNum()){
                    String tempCount=wholeDisCount.getStartNum()+(Integer.valueOf(source.getTrueNum())-wholeDisCount.getStartNum())*(1-wholeDisCount.getPercent()/100)+"";
                    source.setShowNum(tempCount.substring(0,tempCount.indexOf(".")));
                }else {
                    source.setShowNum(source.getTrueNum());
                }
            }
        }
        return source;
    }

    public Page<ChannelDisShowDto> findByCondition(Pageable pageable,ChannelDisShowDto showDto) throws Exception {
        JdbcPage<ChannelDisShowDto> sourceData=this.findChannelDisShowDtoByCondition(showDto);
        List<ChannelDisShowDto> sourceList=sourceData.getResult();
        for(ChannelDisShowDto temp:sourceList){
            temp=countShow(temp);
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

    /**
     * @Author: Away
     * @Title: increateRegisNum
     * @Description: 增加注册数
     * @Param channelCode
     * @Return: com.cw.biz.discount.app.dto.ChannelDisCountDto
     * @Date: 2019/8/1 4:03
     * @Version: 1
     */
    public ChannelDisCountDto incrementRegisNum(String phone,String channelCode,int type){
        if(ObjectHelper.isNotEmpty(channelCode)){
            ChannelDisCountSetting old=settingRepository.findByChannelCode(channelCode);
            if(ObjectHelper.isNotEmpty(old)){
                //查找每日渠道质量数据
                ChannelQuality quality=this.channelQualityRepository.findByChannelCodeAndFlowTime(channelCode, DateHelper.getDateString(new Date(),DateHelper.getFormat(DateHelper.dtShort)));
                //为空则新建
                if(ObjectHelper.isEmpty(quality)){
                    quality=new ChannelQuality();
                    quality.setChannelCode(channelCode);
                    quality.setChannelName(old.getChannelName());
                    quality.setChannelPhone(old.getChannelPhone());
                    quality.setChannelPrice(old.getChannelPrice());
                    quality.setFlowTime(DateHelper.getDateString(new Date(),DateHelper.getFormat(DateHelper.dtShort)));
                }
                UserEntity userEntity=userDomainService.findByPhone(phone);
                switch (type){
                    case 1:
                        if(ObjectHelper.isEmpty(userEntity)){
                            old.setChannelRegister(old.getChannelRegister()+1);
                            quality.setChannelRegister(quality.getChannelRegister()+1);
                        }
                    break;
                    case 2:
                        if(ObjectHelper.isNotEmpty(userEntity)&&!userEntity.getLocked()&&(ObjectHelper.isEmpty(userEntity.getActived())||!userEntity.getActived())){
                            old.setChannelLogin(old.getChannelLogin()+1);
                            quality.setChannelLogin(quality.getChannelLogin()+1);
                        }
                    break;
                }
                channelQualityRepository.updateEntity(quality);
                return settingRepository.updateEntity(old).to(ChannelDisCountDto.class);
            }else{
                return null;
            }
        }else{
            throw new BusinessException(ENUM_EXCEPTION.E10001.code,ENUM_EXCEPTION.E10001.msg);
        }
    }


    public ChannelDisCountDto incrementUvAndPv(Long productId) throws Exception {
        if(ObjectHelper.isNotEmpty(productId)){
            ChannelDisCountSetting old=settingRepository.findByChannelCode(CPContext.getContext().getSeUserInfo().getSourceCode());
            //查找每日渠道质量数据
            ChannelQuality quality=this.channelQualityRepository.findByChannelCodeAndFlowTime(old.getChannelCode(), DateHelper.getDateString(new Date(),DateHelper.getFormat(DateHelper.dtShort)));
            //为空则新建
            if(ObjectHelper.isEmpty(quality)){
                quality=new ChannelQuality();
                quality.setChannelCode(old.getChannelCode());
                quality.setChannelName(old.getChannelName());
                quality.setChannelPhone(old.getChannelPhone());
                quality.setChannelPrice(old.getChannelPrice());
                quality.setFlowTime(DateHelper.getDateString(new Date(),DateHelper.getFormat(DateHelper.dtShort)));
            }
            if(ObjectHelper.isNotEmpty(old)){
                List<Apply> apply=this.applyDomainService.findByUserAndProduct(CPContext.getContext().getSeUserInfo().getId(),productId);
                if(ObjectHelper.isNotEmpty(apply)&&apply.size()>0){
                    quality.setChannelPv(quality.getChannelPv()+1);
                    old.setChannelPv(old.getChannelPv()+1);
                }else{
                    quality.setChannelPv(quality.getChannelPv()+1);
                    old.setChannelPv(ObjectHelper.isNotEmpty(old.getChannelPv())?old.getChannelPv()+1:1);
                    quality.setChannelUv(quality.getChannelUv()+1);
                    old.setChannelUv(ObjectHelper.isNotEmpty(old.getChannelUv())?old.getChannelUv()+1:1);
                }
                old=settingRepository.updateEntity(old);
                this.channelQualityRepository.updateEntity(quality);
                return ObjectProperUtil.compareAndValue(old,new ChannelDisCountDto(),true,new String[]{"id"});
            }else {
                return null;
            }
        }else{
            throw new BusinessException(ENUM_EXCEPTION.E10001.code,ENUM_EXCEPTION.E10001.msg);
        }
    }


    public Page<ChannelDisCountSetting> findByCon(Pageable pageable,ChannelDisCountDto disCountDto){
        return this.CT.findByCon(pageable, disCountDto);
    }

    public Page<ChannelQualityDto> findChannelQualityByCon(Pageable pageable, ChannelQualityDto channelQualityDto) throws Exception {
        Page<ChannelQuality> sourceData=this.channelQualityRepository.findByCon(pageable, channelQualityDto);
        if(ObjectHelper.isNotEmpty(sourceData)&&ObjectHelper.isNotEmpty(sourceData.getContent())&&sourceData.getContent().size()>0){
            List<ChannelQualityDto> returnList=new ArrayList<>(sourceData.getContent().size());
            for(ChannelQuality temp:sourceData.getContent()){
                returnList.add(ObjectProperUtil.compareAndValue(temp,new ChannelQualityDto(),true,new String[]{"id"}));
            }
            return new PageImpl<>(returnList,pageable,sourceData.getTotalElements());
        }else{
            return new PageImpl<>(new ArrayList<ChannelQualityDto>(0),pageable,0);
        }
    }

}
