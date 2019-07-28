package com.cw.biz.user.domain.service;

import com.cw.biz.user.app.dto.UserDto;
import com.cw.biz.user.domain.entity.UserEntity;
import com.cw.biz.user.domain.repository.UserRepository;
import com.cw.core.common.base.BaseDomainService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class UserDomainService extends BaseDomainService<UserRepository,UserEntity,UserDto>{

    public Page<UserDto> findByCondition(Pageable pageable,UserDto userDto) throws ParseException {
        return toDtoPage(this.CT.findByCondition(pageable, userDto),UserDto.class,pageable);
    }
}
