package com.cw.biz.discount.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.discount.app.dto.ChannelDisShowDto;
import com.cw.biz.discount.app.dto.WholeDisCountDto;
import com.cw.biz.discount.domain.entity.ChannelDisCountSetting;
import com.cw.biz.discount.domain.entity.WholeDisCount;
import com.cw.biz.discount.domain.repository.ChannelDiscountRepository;
import com.cw.biz.discount.domain.repository.WholeDiscountRepository;
import com.cw.biz.parameter.domain.entity.Parameter;
import com.cw.biz.parameter.domain.service.ParameterDomainService;
import com.cw.core.common.base.BaseDomainService;
import com.cw.core.common.base.ENUM_EXCEPTION;
import com.cw.core.common.util.DateHelper;
import com.cw.core.common.util.ObjectHelper;
import com.cw.core.common.util.ObjectProperUtil;
import com.cw.core.common.util.UUIDUtil;
import com.zds.common.lang.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DiscountDomainService extends BaseDomainService<WholeDiscountRepository, WholeDisCount, WholeDisCountDto> {

    private final ChannelDiscountRepository channelDiscountRepository;

    private final ParameterDomainService parameterDomainService;

    @Autowired
    public DiscountDomainService(ChannelDiscountRepository channelDiscountRepository, ParameterDomainService parameterDomainService) {
        this.channelDiscountRepository = channelDiscountRepository;
        this.parameterDomainService = parameterDomainService;
    }

    /**
     * @Author: Away
     * @Description: 修改或者新增全局扣量设置
     * @Param: dto
     * @Return com.cw.biz.discount.app.dto.WholeDisCountDto
     * @Date 2019/7/27 21:59
     * @Copyright 重庆平讯数据
     */
    public WholeDisCountDto saveOrCreate(WholeDisCountDto dto) throws Exception {
        WholeDisCountDto old = this.findDisCount();
        old.setStartNum(dto.getStartNum());
        old.setPercent(dto.getPercent());
        return saveOrUpdateData(old, WholeDisCount.class);
    }

    public WholeDisCountDto findDisCount() {
        List<WholeDisCount> all = this.CT.findAll();
        return toDto(ObjectHelper.isNotEmpty(all) ? all.get(0) : new WholeDisCount(), WholeDisCountDto.class);
    }


    /**
     * @Author: Away
     * @Description: 更新或者保存渠道扣量设置
     * @Param: channelDisCountDto
     * @Return com.cw.biz.discount.app.dto.ChannelDisCountDto
     * @Date 2019/7/27 21:59
     * @Copyright 重庆平讯数据
     */
    public ChannelDisShowDto saveOrUpdateChannel(ChannelDisShowDto channelDisCountDto) throws Exception {
        if(ObjectHelper.isNotEmpty(channelDisCountDto)){
            if(ObjectHelper.isNotEmpty(channelDisCountDto.getId())){
                ChannelDisCountSetting channelDisCountSetting=this.channelDiscountRepository.findOne(channelDisCountDto.getId());
                channelDisCountSetting= ObjectProperUtil.compareAndValue(channelDisCountDto,channelDisCountSetting,false,new String[]{"id"});
                return this.channelDiscountRepository.updateEntity(channelDisCountSetting).to(ChannelDisShowDto.class);
            }else{
                ChannelDisCountSetting channelDisCountSetting=new ChannelDisCountSetting();
                channelDisCountSetting=ObjectProperUtil.compareAndValue(channelDisCountDto,channelDisCountSetting,false,new String[]{"id"});
                channelDisCountSetting.setChannelCode(UUIDUtil.generateShortUuid()+ DateHelper.getDateString(new Date(),DateHelper.getFormat(DateHelper.dtLong)));
                channelDisCountSetting.setOperator(CPContext.getContext().getSeUserInfo().getUsername());
                Parameter parameter=this.parameterDomainService.findByCode("channelBaseUrl");
                Parameter parameter2=this.parameterDomainService.findByCode("channelBackBaseUrl");
                channelDisCountSetting.setChannelUrl(parameter.getParameterValStr()+"?channel="+channelDisCountSetting.getChannelCode());
                channelDisCountSetting.setChannelBackUrl(parameter2.getParameterValStr()+"?channel="+channelDisCountSetting.getChannelCode());
                return this.channelDiscountRepository.saveEntity(channelDisCountSetting).to(ChannelDisShowDto.class);
            }
        }else {
            throw new BusinessException(ENUM_EXCEPTION.E10001.code,ENUM_EXCEPTION.E10001.msg);
        }
    }

}
