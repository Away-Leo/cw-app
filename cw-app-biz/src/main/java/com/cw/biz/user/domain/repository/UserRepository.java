package com.cw.biz.user.domain.repository;

import com.cw.biz.user.app.dto.UserDto;
import com.cw.biz.user.domain.entity.UserEntity;
import com.cw.core.common.base.BaseRepository;
import com.cw.core.common.util.DateHelper;
import com.cw.core.common.util.ObjectHelper;
import com.zds.common.lang.util.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户基本信息
 * Created by dujy on 2017-05-20.
 */
public interface UserRepository extends BaseRepository<UserEntity,Long> {

    default Page<UserEntity> findByCondition(Pageable pageable,UserDto userDto) throws ParseException {
        StringBuffer hql=new StringBuffer("from UserEntity where 1=1");
        Map<String,Object> con=new HashMap<>();
        if(ObjectHelper.isEmpty(userDto))return null;
        if(ObjectHelper.isNotEmpty(userDto.getPhone())){
            hql.append(" and phone = :phone ");
            con.put("phone",userDto.getPhone().trim());
        }
        if(ObjectHelper.isNotEmpty(userDto.getSourceCode())){
            hql.append(" and sourceCode =:sourceCode ");
            con.put("sourceCode",userDto.getSourceCode().trim());
        }
        if(ObjectHelper.isNotEmpty(userDto.getStartTime())){
            hql.append(" and registerDate >:startTime ");
            con.put("startTime", new SimpleDateFormat(DateHelper.dtSimple).parse(userDto.getStartTime()));
        }
        if(ObjectHelper.isNotEmpty(userDto.getEndTime())){
            hql.append(" and registerDate <:endTime ");
            con.put("endTime",new SimpleDateFormat(DateHelper.dtSimple).parse(userDto.getEndTime()));
        }

        return findByHqlPage(pageable,hql.toString(),con);
    }

    public UserEntity findByPhone(String phone);

}
