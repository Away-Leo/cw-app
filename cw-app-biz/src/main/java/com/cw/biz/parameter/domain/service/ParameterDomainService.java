package com.cw.biz.parameter.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.parameter.app.ParameterEnum;
import com.cw.biz.parameter.app.dto.IndexParameterDto;
import com.cw.biz.parameter.app.dto.ParameterDto;
import com.cw.biz.parameter.domain.entity.Parameter;
import com.cw.biz.parameter.domain.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class ParameterDomainService {

    @Autowired
    private ParameterRepository repository;
    /**
     * 新增参数
     * @param parameterDto
     * @return
     */
    public Parameter create(ParameterDto parameterDto)
    {
        Parameter parameter = new Parameter();
        parameter.from(parameterDto);
        return repository.save(parameter);
    }

    /**
     * 修改参数
     * @param indexParameterDto
     * @return
     */
    public Parameter update(IndexParameterDto indexParameterDto){
        //借款起始金额
        Parameter parameter = repository.findByParameterCode(ParameterEnum.startAmount.getKey());
        if(parameter == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.startAmount.getKey());
            parameterDto.setParameterName(ParameterEnum.startAmount.getValue());
            parameterDto.setParameterName(ParameterEnum.startAmount.getValue());
            parameterDto.setParameterValue(indexParameterDto.getStartAmount());
            create(parameterDto);
        }else{
            parameter.setParameterValue(indexParameterDto.getStartAmount());
        }
        //借款截止金额
        Parameter parameter1 = repository.findByParameterCode(ParameterEnum.endAmount.getKey());
        if(parameter1 == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.endAmount.getKey());
            parameterDto.setParameterName(ParameterEnum.endAmount.getValue());
            parameterDto.setParameterName(ParameterEnum.endAmount.getValue());
            parameterDto.setParameterValue(indexParameterDto.getEndAmount());
            create(parameterDto);
        }else{
            parameter1.setParameterValue(indexParameterDto.getEndAmount());
        }
        //开始期限日
        Parameter parameter2 = repository.findByParameterCode(ParameterEnum.startPeriodDay.getKey());
        if(parameter2 == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.startPeriodDay.getKey());
            parameterDto.setParameterName(ParameterEnum.startPeriodDay.getValue());
            parameterDto.setParameterName(ParameterEnum.startPeriodDay.getValue());
            parameterDto.setParameterValue(new BigDecimal(indexParameterDto.getStartPeriodDay()));
            create(parameterDto);
        }else{
            parameter2.setParameterValue(new BigDecimal(indexParameterDto.getStartPeriodDay()));
        }
        //结束期限日
        Parameter parameter3 = repository.findByParameterCode(ParameterEnum.endPeriodDay.getKey());
        if(parameter3 == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.endPeriodDay.getKey());
            parameterDto.setParameterName(ParameterEnum.endPeriodDay.getValue());
            parameterDto.setParameterName(ParameterEnum.endPeriodDay.getValue());
            parameterDto.setParameterValue(new BigDecimal(indexParameterDto.getEndPeriodDay()));
            create(parameterDto);
        }else{
            parameter3.setParameterValue(new BigDecimal(indexParameterDto.getEndPeriodDay()));
        }
        //起始期限月
        Parameter parameter4 = repository.findByParameterCode(ParameterEnum.startPeriodMonth.getKey());
        if(parameter4 == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.startPeriodMonth.getKey());
            parameterDto.setParameterName(ParameterEnum.startPeriodMonth.getValue());
            parameterDto.setParameterName(ParameterEnum.startPeriodMonth.getValue());
            parameterDto.setParameterValue(new BigDecimal(indexParameterDto.getStartPeriodMonth()));
            create(parameterDto);
        }else{
            parameter4.setParameterValue(new BigDecimal(indexParameterDto.getStartPeriodMonth()));
        }
        //结束期限月
        Parameter parameter5 = repository.findByParameterCode(ParameterEnum.endPeriodMonth.getKey());
        if(parameter5 == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.endPeriodMonth.getKey());
            parameterDto.setParameterName(ParameterEnum.endPeriodMonth.getValue());
            parameterDto.setParameterName(ParameterEnum.endPeriodMonth.getValue());
            parameterDto.setParameterValue(new BigDecimal(indexParameterDto.getEndPeriodMonth()));
            create(parameterDto);
        }else{
            parameter5.setParameterValue(new BigDecimal(indexParameterDto.getEndPeriodMonth()));
        }
        //贷款利率
        Parameter parameter6 = repository.findByParameterCode(ParameterEnum.loanRate.getKey());
        if(parameter6 == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.loanRate.getKey());
            parameterDto.setParameterName(ParameterEnum.loanRate.getValue());
            parameterDto.setParameterName(ParameterEnum.loanRate.getValue());
            parameterDto.setParameterValue(indexParameterDto.getLoanRate());
            create(parameterDto);
        }else{
            parameter6.setParameterValue(indexParameterDto.getLoanRate());
        }

        //运营成本
        Parameter parameter7 = repository.findByParameterCode(ParameterEnum.operateCost.getKey());
        if(parameter7 == null)
        {
            ParameterDto parameterDto = new ParameterDto();
            parameterDto.setParameterCode(ParameterEnum.operateCost.getKey());
            parameterDto.setParameterName(ParameterEnum.operateCost.getValue());
            parameterDto.setParameterName(ParameterEnum.operateCost.getValue());
            parameterDto.setParameterValue(indexParameterDto.getOperateCost());
            create(parameterDto);
        }else{
            parameter7.setParameterValue(indexParameterDto.getOperateCost());
        }
        return parameter;
    }

    /**
     * 产品停用、启用
     * @param productDto
     * @return
     */
    public Parameter enable(ParameterDto productDto){
        Parameter parameter = repository.findOne(productDto.getId());
        if(parameter == null)
        {
            CwException.throwIt("参数不存在");
        }
        if(parameter.getIsValid()) {
            parameter.setIsValid(Boolean.FALSE);
        }else{
            parameter.setIsValid(Boolean.TRUE);
        }
        return parameter;
    }

    /**
     * 查询单个参数
     * @param id
     * @return
     */
    public Parameter findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 根据参数编码查询审核配置
     * @param code
     * @return
     */
    public Parameter findByCode(String code)
    {
        return repository.findByParameterCode(code);
    }

    /**
     * 查询所有参数
     * @return
     */
    public List<Parameter> findAll() {
        return repository.findAll();
    }

}
