package com.cw.biz.operatecost.domain.dao;

import com.cw.biz.operatecost.app.dto.OperateCostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class OperateCostDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 统计合计
     * @return
     */
    public OperateCostDto findCountCost(OperateCostDto operateCostDto){

        StringBuffer sql=new StringBuffer();
        sql.append("select ifnull(sum(cost_fee),0) as costFee from cw_operate_cost where 1=1 and is_valid=1 ");
        if(!Objects.isNull(operateCostDto.getFeeItem())&&!"all".equals(operateCostDto.getFeeItem())){
            sql.append(" and fee_item='"+operateCostDto.getFeeItem()+"'");
        }

        if(!Objects.isNull(operateCostDto.getChannel())&&!"all".equals(operateCostDto.getChannel())){
            sql.append(" and channel='"+operateCostDto.getChannel()+"'");
        }

        if(!Objects.isNull(operateCostDto.getAppName())&&!"all".equals(operateCostDto.getAppName())){
            sql.append(" and app_name='"+operateCostDto.getAppName()+"'");
        }

        if(!Objects.isNull(operateCostDto.getDayId())&&!"".equals(operateCostDto.getDayId())){
            sql.append(" and day_id like '"+operateCostDto.getDayId().substring(0,7)+"%'");
        }

        List<OperateCostDto> homeDtoList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(OperateCostDto.class));

        return homeDtoList.get(0);
    }

}
