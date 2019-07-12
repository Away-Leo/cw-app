package com.cw.biz.integral.app.service;

import com.cw.biz.integral.app.dto.IntegralLogDto;
import com.cw.biz.integral.domain.dao.IntegralDao;
import com.cw.biz.integral.domain.service.IntegralLogDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 积分服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class IntegralLogAppService {

    @Autowired
    private IntegralLogDomainService domainService;

    @Autowired
    private IntegralDao integralDao;
    /**
     * 新增积分兑换记录
     * @return
     */
    public Long create(IntegralLogDto integralLogDto){
        return domainService.create(integralLogDto).getId();
    }

    /**
     * 修改用户积分
     * @return
     */
    public IntegralLogDto update(IntegralLogDto integralLogDto){
        integralLogDto.setIntegralType(2L);
        return domainService.update(integralLogDto).to(IntegralLogDto.class);
    }

    /**
     * 查询用户积分
     * @return
     */
    public IntegralLogDto findById(Long id){
        return integralDao.findById(id);
    }

    /**
     * 查询兑换申请记录
     * @param dto
     * @return
     */
    public List<IntegralLogDto> findByCondition(IntegralLogDto dto){
        return integralDao.integralExchange(dto);
    }

    /**
     * 查询用户积分明细
     * @param dto
     * @return
     */
    public List<IntegralLogDto> findAll(IntegralLogDto dto){
        return integralDao.findAll(dto);
    }
}
