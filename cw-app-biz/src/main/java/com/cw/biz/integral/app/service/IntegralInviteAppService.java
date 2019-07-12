package com.cw.biz.integral.app.service;

import com.cw.biz.CPContext;
import com.cw.biz.integral.app.dto.IntegralDto;
import com.cw.biz.integral.app.dto.IntegralInviteDto;
import com.cw.biz.integral.domain.entity.IntegralInvite;
import com.cw.biz.integral.domain.service.IntegralDomainService;
import com.cw.biz.integral.domain.service.IntegralInviteDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 成功邀请借款人员服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class IntegralInviteAppService {

    @Autowired
    private IntegralInviteDomainService domainService;

    /**
     * 新增邀请用户信息
     * @return
     */
    public Long create(IntegralInviteDto integralInviteDto){
        return domainService.create(integralInviteDto).getId();
    }

    /**
     * 修改邀请用户信息
     * @return
     */
    public IntegralInviteDto update(IntegralInviteDto integralInviteDto){
        return domainService.update(integralInviteDto).to(IntegralInviteDto.class);
    }

    /**
     * 查询用户信息
     * @return
     */
    public IntegralDto findById(){
        return domainService.findById(CPContext.getContext().getSeUserInfo().getId()).to(IntegralDto.class);
    }

    /**
     * 查询已借款人员明细
     * @param integralInviteDto
     * @return
     */
    public List<IntegralInviteDto> findAll(IntegralInviteDto integralInviteDto){
        List<IntegralInviteDto> integralInviteDtoList = new ArrayList<IntegralInviteDto>();
        List<IntegralInvite> integralInvites = domainService.findAll(integralInviteDto.getId());
        for(IntegralInvite integralInvite:integralInvites){
            integralInviteDtoList.add(integralInvite.to(IntegralInviteDto.class));
        }
        return integralInviteDtoList;
    }
}
