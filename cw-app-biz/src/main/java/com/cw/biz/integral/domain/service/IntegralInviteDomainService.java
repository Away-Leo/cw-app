package com.cw.biz.integral.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.integral.app.dto.IntegralInviteDto;
import com.cw.biz.integral.domain.entity.IntegralInvite;
import com.cw.biz.integral.domain.repository.IntegralInviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 邀请借款人员积分
 * Created by dujy on 2017-05-20.
 */
@Service
public class IntegralInviteDomainService {

    @Autowired
    private IntegralInviteRepository repository;
    /**
     * 新增成功邀请人员
     * @param integralInviteDto
     * @return
     */
    public IntegralInvite create(IntegralInviteDto integralInviteDto)
    {
        IntegralInvite integralInvite = new IntegralInvite();
        integralInvite.from(integralInviteDto);
        return repository.save(integralInvite);
    }

    /**
     * 修改邀请人员明细
     * @param integralInviteDto
     * @return
     */
    public IntegralInvite update(IntegralInviteDto integralInviteDto){
        IntegralInvite integralInvite = repository.findOne(integralInviteDto.getId());
        if(integralInvite != null){
            CwException.throwIt("邀请人员不存在");
        }
        integralInvite.from(integralInviteDto);
        return integralInvite;
    }

    /**
     * 查询邀请人员明细
     * @param id
     * @return
     */
    public IntegralInvite findById(Long id){
        return repository.findOne(id);
    }

    /**
     * 查询成功借款人员
     * @return
     */
    public List<IntegralInvite> findAll(Long id){
        return repository.findByUserId(id);
    }
}
