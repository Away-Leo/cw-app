package com.cw.biz.parameter.app.service;

import com.cw.biz.parameter.app.ParameterEnum;
import com.cw.biz.parameter.app.dto.IndexParameterDto;
import com.cw.biz.parameter.app.dto.ParameterDto;
import com.cw.biz.parameter.domain.entity.Parameter;
import com.cw.biz.parameter.domain.service.ParameterDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 系统参数服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class ParameterAppService {

    @Autowired
    private ParameterDomainService parameterDomainService;

    /**
     * 修改参数
     * @param parameterDto
     * @return
     */
    public Long update(IndexParameterDto parameterDto)
    {
        return parameterDomainService.update(parameterDto).getId();
    }

    /**
     * 根据编码查询配置
     * @param code
     * @return
     */
    public ParameterDto findByCode(String code){
        return parameterDomainService.findByCode(code).to(ParameterDto.class);
    }

    /**
     * 查询全部参数
     * @return
     */
    public IndexParameterDto findAll()
    {
        IndexParameterDto indexParameterDto = new IndexParameterDto();
        List<Parameter> productList = parameterDomainService.findAll();
        for(Parameter parameter : productList){
            if(ParameterEnum.startAmount.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setStartAmount(parameter.getParameterValue());
            }
            if(ParameterEnum.endAmount.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setEndAmount(parameter.getParameterValue());
            }
            if(ParameterEnum.startPeriodDay.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setStartPeriodDay(parameter.getParameterValue().intValue());
            }
            if(ParameterEnum.endPeriodDay.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setEndPeriodDay(parameter.getParameterValue().intValue());
            }
            if(ParameterEnum.startPeriodMonth.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setStartPeriodMonth(parameter.getParameterValue().intValue());
            }
            if(ParameterEnum.endPeriodMonth.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setEndPeriodMonth(parameter.getParameterValue().intValue());
            }
            if(ParameterEnum.loanRate.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setLoanRate(parameter.getParameterValue());
            }
            if(ParameterEnum.operateCost.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setOperateCost(parameter.getParameterValue());
            }
            if(ParameterEnum.suspendFlag.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setSuspendFlag(parameter.getParameterValue()+"");
            }
            if(ParameterEnum.messageTip.getKey().equals(parameter.getParameterCode())) {
                indexParameterDto.setMessageTip(parameter.getParameterName());
            }

        }
        return indexParameterDto;
    }

}
