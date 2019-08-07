package com.cw.biz.discount.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.apply.domain.entity.Apply;
import com.cw.biz.apply.domain.service.ApplyDomainService;
import com.cw.biz.discount.app.dto.ChannelDisCountDto;
import com.cw.biz.discount.app.dto.ChannelDisShowDto;
import com.cw.biz.discount.app.dto.WholeDisCountDto;
import com.cw.biz.discount.domain.entity.ChannelDisCountSetting;
import com.cw.biz.discount.domain.entity.WholeDisCount;
import com.cw.biz.discount.domain.repository.ChannelDisCountSettingRepository;
import com.cw.biz.discount.domain.repository.WholeDiscountRepository;
import com.cw.biz.jdbc.JdbcPage;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.user.domain.entity.UserEntity;
import com.cw.biz.user.domain.service.UserDomainService;
import com.cw.core.common.base.BaseDomainService;
import com.cw.core.common.base.ENUM_EXCEPTION;
import com.cw.core.common.util.ObjectHelper;
import com.zds.common.lang.exception.BusinessException;
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

    private final ApplyDomainService applyDomainService;

    private UserDomainService userDomainService;

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DiscountSettingDomainService(ChannelDisCountSettingRepository settingRepository, DiscountDomainService discountDomainService, ApplyDomainService applyDomainService, JdbcTemplate jdbcTemplate) {
        this.settingRepository = settingRepository;
        wholeDisCount= discountDomainService.findDisCount();
        this.applyDomainService = applyDomainService;
        this.jdbcTemplate = jdbcTemplate;
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
                UserEntity userEntity=userDomainService.findByPhone(phone);
                switch (type){
                    case 1:
                        if(ObjectHelper.isEmpty(userEntity)){
                            old.setChannelRegister(old.getChannelRegister()+1);
                        }
                    break;
                    case 2:
                        if(ObjectHelper.isNotEmpty(userEntity)&&!userEntity.getLocked()&&!userEntity.getActived()){
                            old.setChannelLogin(old.getChannelLogin()+1);
                        }
                    break;
                }
                return settingRepository.updateEntity(old).to(ChannelDisCountDto.class);
            }else{
                return null;
            }
        }else{
            throw new BusinessException(ENUM_EXCEPTION.E10001.code,ENUM_EXCEPTION.E10001.msg);
        }
    }


    public ChannelDisCountDto incrementUvAndPv(Long productId){
        if(ObjectHelper.isNotEmpty(productId)){
            ChannelDisCountSetting old=settingRepository.findByChannelCode(CPContext.getContext().getSeUserInfo().getSourceCode());
            if(ObjectHelper.isNotEmpty(old)){
                List<Apply> apply=this.applyDomainService.findByUserAndProduct(CPContext.getContext().getSeUserInfo().getId(),productId);
                if(ObjectHelper.isNotEmpty(apply)&&apply.size()>0){
                    old.setChannelPv(old.getChannelPv()+1);
                }else{
                    old.setChannelPv(old.getChannelPv()+1);
                    old.setChannelUv(old.getChannelUv()+1);
                }
                return settingRepository.updateEntity(old).to(ChannelDisCountDto.class);
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

}
