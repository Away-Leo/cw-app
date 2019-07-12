package com.cw.biz.integral.domain.service;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.integral.app.dto.IntegralDto;
import com.cw.biz.integral.domain.entity.Integral;
import com.cw.biz.integral.domain.repository.IntegralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 积分服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class IntegralDomainService {

    @Autowired
    private IntegralRepository repository;
    /**
     * 新增用户积分
     * @param integralDto
     * @return
     */
    public Integral create(IntegralDto integralDto)
    {
        Integral integral = new Integral();
        integral.from(integralDto);
        return repository.save(integral);
    }

    /**
     * 修改用户积分
     * @param integralDto
     * @return
     */
    public Integral update(IntegralDto integralDto){
        integralDto.setUserId(CPContext.getContext().getSeUserInfo().getId());
        Integral integral = repository.findByUserId(integralDto.getUserId());
        if(integral == null){
            create(integralDto);
        }else{
            //增加或者减少积分 1：获得积分，2：积分兑换礼品
            if(integralDto.getIntegralType()==2){
                if(integral.getIntegral().subtract(integralDto.getIntegral()).compareTo(BigDecimal.ZERO)==-1){
                    CwException.throwIt("积分不足，兑换失败");
                }
                integral.setIntegral(integral.getIntegral().subtract(integralDto.getIntegral()));
            }else {
                integral.setIntegral(integral.getIntegral().add(integralDto.getIntegral()));
            }
        }
        return integral;
    }

    /**
     * 查询用户积分
     * @param id
     * @return
     */
    public Integral findById(Long id){
        return repository.findByUserId(id);
    }
}
