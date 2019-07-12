package com.cw.biz.user.domain.service;

import com.cw.biz.user.app.dto.CwUserInfoDto;
import com.cw.biz.user.domain.entity.CwUserInfo;
import com.cw.biz.user.domain.repository.SeUserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class CwUserInfoDomainService {

    @Autowired
    private SeUserInfoRepository repository;
    /**
     * 新增客户信息
     * @param cwUserInfoDto
     * @return
     */
    private CwUserInfo create(CwUserInfoDto cwUserInfoDto)
    {
        CwUserInfo cwUserInfo = new CwUserInfo();
        cwUserInfo.from(cwUserInfoDto);
        return repository.save(cwUserInfo);
    }

    /**
     * 修改客户信息
     * @param cwUserInfoDto
     * @return
     */
    public CwUserInfo update(CwUserInfoDto cwUserInfoDto)
    {
        //借款起始金额
        CwUserInfo cwUserInfo = repository.findByUserId(cwUserInfoDto.getUserId());
        if(cwUserInfo == null)
        {
            cwUserInfo = create(cwUserInfoDto);
        }else{
            cwUserInfo.from(cwUserInfoDto);
        }
        return cwUserInfo;
    }

    /**
     * 查询客户信息
     * @param id
     * @return
     */
    public CwUserInfo findById(Long id)
    {
        return repository.findByUserId(id);
    }

}
