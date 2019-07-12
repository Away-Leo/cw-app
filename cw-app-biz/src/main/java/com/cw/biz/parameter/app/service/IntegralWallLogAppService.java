package com.cw.biz.parameter.app.service;

import com.alibaba.druid.util.Utils;
import com.cw.biz.CwException;
import com.cw.biz.parameter.app.dto.IntegralWallLogDto;
import com.cw.biz.parameter.domain.service.IntegralWallLogDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 积分墙接口数据
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class IntegralWallLogAppService {

    @Autowired
    private IntegralWallLogDomainService domainService;
    public static String encryptStr="pingxundata1234567890";
    /**
     * 查询列表参数
     * @return
     */
    public Long create(IntegralWallLogDto integralWallLogDto)
    {
        String md5Str = Utils.md5(integralWallLogDto.getDeviceNo()+integralWallLogDto.getCode()+encryptStr);
        if(md5Str.toUpperCase().equals(integralWallLogDto.getEncryptStr().toUpperCase())){
            Long id = domainService.create(integralWallLogDto).getId();
            return  id;
        }else{
            CwException.throwIt("非法签名");
        }
        return null;
    }
}


