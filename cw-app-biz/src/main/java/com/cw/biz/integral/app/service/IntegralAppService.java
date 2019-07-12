package com.cw.biz.integral.app.service;

import com.cw.biz.CPContext;
import com.cw.biz.integral.app.dto.IntegralDto;
import com.cw.biz.integral.domain.service.IntegralDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 积分服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class IntegralAppService {

    @Autowired
    private IntegralDomainService domainService;

    /**
     * 修改用户积分
     * @return
     */
    public IntegralDto update(IntegralDto integralDto){

        return domainService.update(integralDto).to(IntegralDto.class);
    }

    /**
     * 查询用户积分
     * @return
     */
    public IntegralDto findById(){
        return domainService.findById(CPContext.getContext().getSeUserInfo().getId()).to(IntegralDto.class);
    }

}
