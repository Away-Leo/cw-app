package com.cw.biz.parameter.app.service;

import com.cw.biz.common.entity.BaseEntity;
import com.cw.biz.parameter.app.dto.SpinnerParameterDto;
import com.cw.biz.parameter.domain.entity.SpinnerParameter;
import com.cw.biz.parameter.domain.service.SpinnerParameterDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * 查询列表参数
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class SpinnerParameterAppService {

    @Autowired
    private SpinnerParameterDomainService domainService;

    /**
     * 查询列表参数
     * @return
     */
    public List<SpinnerParameterDto> findByType(String type)
    {
        List<SpinnerParameter> spinnerParameters =domainService.findByType(type);
        return  BaseEntity.map(spinnerParameters,SpinnerParameterDto.class);
    }

    /**
     * 查询描述
     * @return
     */
    public SpinnerParameterDto findByCode(String code)
    {
        SpinnerParameterDto spinnerParameterDto = null;
        SpinnerParameter spinnerParameter = domainService.findByCode(code);
        if (!Objects.isNull(spinnerParameter)) {
            spinnerParameterDto = spinnerParameter.to(SpinnerParameterDto.class);
        }
        return spinnerParameterDto;
    }

}
