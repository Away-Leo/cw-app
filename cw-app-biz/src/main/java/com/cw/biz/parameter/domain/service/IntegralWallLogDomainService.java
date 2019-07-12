package com.cw.biz.parameter.domain.service;

import com.cw.biz.parameter.app.dto.IntegralWallLogDto;
import com.cw.biz.parameter.app.dto.ParameterDto;
import com.cw.biz.parameter.domain.entity.IntegralWallLog;
import com.cw.biz.parameter.domain.entity.SpinnerParameter;
import com.cw.biz.parameter.domain.repository.IntegralWallLogRepository;
import com.cw.biz.parameter.domain.repository.SpinnerParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分墙接口保存
 * Created by dujy on 2017-05-20.
 */
@Service
public class IntegralWallLogDomainService {

    @Autowired
    private IntegralWallLogRepository repository;
    /**
     * 新增积分墙用户点击
     * @param integralWallLogDto
     * @return
     */
    public IntegralWallLog create(IntegralWallLogDto integralWallLogDto)
    {
        IntegralWallLog integralWallLog = new IntegralWallLog();
        integralWallLog.from(integralWallLogDto);
        return repository.save(integralWallLog);
    }

}
