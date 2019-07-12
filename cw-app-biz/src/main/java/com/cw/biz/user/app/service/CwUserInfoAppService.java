package com.cw.biz.user.app.service;

import com.cw.biz.user.app.dto.CwUserInfoDto;
import com.cw.biz.user.domain.entity.CwUserInfo;
import com.cw.biz.user.domain.service.CwUserInfoDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class CwUserInfoAppService {

    @Autowired
    private CwUserInfoDomainService cwUserInfoDomainService;

    /**
     * 修改客户信息
     * @param cwUserInfoDto
     * @return
     */
    public Long update(CwUserInfoDto cwUserInfoDto)
    {
        return cwUserInfoDomainService.update(cwUserInfoDto).getId();
    }

    /**
     * 查询用户详情
     * @param id
     * @return
     */
    public CwUserInfoDto findById(Long id)
    {
        CwUserInfoDto cwUserInfoDto = new CwUserInfoDto();
        CwUserInfo cwUserInfo = cwUserInfoDomainService.findById(id);
        if(cwUserInfo != null)
        {
            cwUserInfoDto = cwUserInfo.to(CwUserInfoDto.class);
        }
        return cwUserInfoDto;
    }


}
