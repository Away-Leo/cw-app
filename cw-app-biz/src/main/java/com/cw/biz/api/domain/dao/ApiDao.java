package com.cw.biz.api.domain.dao;

import com.cw.biz.CwException;
import com.cw.biz.api.app.dto.ApiDto;
import com.cw.biz.home.app.dto.AppDevDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * API接口服务
 */
@Repository
public class ApiDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 借款申请
     * @param apiDto
     * @return
     */
    public Boolean applyLoan(ApiDto apiDto){
        String sql = "select id from pf_se_user where phone='"+apiDto.getPhone()+"'";
        //查询系统是否存在该用户
        List<AppDevDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));
        //检查用户是否存在
        if(homeDto.size()==0){
            CwException.throwIt("用户不存在");
        }
        //用户存在则验证数据有效性


        return Boolean.TRUE;
    }

}
