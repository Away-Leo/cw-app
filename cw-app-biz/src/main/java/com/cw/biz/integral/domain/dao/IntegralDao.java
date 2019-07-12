package com.cw.biz.integral.domain.dao;

import com.cw.biz.integral.app.dto.IntegralLogDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IntegralDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 积分兑换
     * @param integralLogDto
     * @return
     */
    public List<IntegralLogDto> integralExchange(IntegralLogDto integralLogDto){
        String sql = " SELECT\n" +
                "   a.id,\n" +
                "   a.phone,\n" +
                "   a.integral,\n" +
                "   a.apply_date as applyDate,\n" +
                "   a.exchange_date as exchangeDate,\n" +
                "   a.memo,\n" +
                "   b.name,  \n" +
                "   b.start_value as exchangeIntegral\n" +
                " FROM\n" +
                "    cw_user_integral_log a\n" +
                " INNER JOIN ( " +
                "    SELECT " +
                "    * " +
                "  FROM\n" +
                "   cw_parameter_spinner " +
                " WHERE " +
                "  type IN ('cash', 'stream', 'videovip') " +
                " ) b ON a.gift_id = b.id " +
                " where 1=1 and a.integral_type=2 " ;
        if(integralLogDto.getName()!=null){
            sql+=" and b.name like '%"+integralLogDto.getName()+"%'";
        }

        List<IntegralLogDto> integralLogDtos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(IntegralLogDto.class));
        return integralLogDtos;
    }

    /**
     * 查询积分兑换申请记录详情
     * @param id
     * @return
     */
    public IntegralLogDto findById(Long id){
        String sql = " SELECT\n" +
                "   a.id,\n" +
                "   a.phone,\n" +
                "   a.integral,\n" +
                "   a.apply_date as applyDate,\n" +
                "   a.exchange_date as exchangeDate,\n" +
                "   a.memo,\n" +
                "   b.name,  \n" +
                "   b.start_value as exchangeIntegral\n" +
                " FROM\n" +
                "    cw_user_integral_log a\n" +
                " LEFT JOIN ( " +
                "    SELECT " +
                "    * " +
                "  FROM\n" +
                "   cw_parameter_spinner " +
                " WHERE " +
                "  type IN ('cash', 'stream', 'videovip') " +
                " ) b ON a.gift_id = b.id" +
                " where 1=1 and a.id= "+id ;

        List<IntegralLogDto> integralLogDtos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(IntegralLogDto.class));
        if(integralLogDtos.size()>0){
            return integralLogDtos.get(0);
        }
        return null;
    }


    /**
     * 积分统计
     * @param integralLogDto
     * @return
     */
    public List<IntegralLogDto> findAll(IntegralLogDto integralLogDto){


        String queryDate=" and date_format(apply_date,'%y-%m-%d')=date_format(now(),'%y-%m-%d') ";
        if(integralLogDto.getApplyStartDate()!=null&&!"".equals(integralLogDto.getApplyStartDate())){
            queryDate = " and apply_date between str_to_date('"+integralLogDto.getApplyStartDate()
                    +"','%y-%m-%d') and str_to_date('"+integralLogDto.getApplyEndDate()+"','%y-%m-%d')";
        }

        String sql = "  select a.id,              \n" +
                "    a.phone,              \n" +
                "    b.name as activeName,                         \n" +
                "    a.integral,              \n" +
                "    c.integral as totalIntegral,              \n" +
                "    CONCAT(date_format(b.start_date,'%Y-%m-%d'),'至',date_format(b.end_date,'%Y-%m-%d')) as activeDate,\n" +
                "    (select count(DISTINCT aa.user_id) \n" +
                "       from (select user_id,invite_code,apply_date from cw_log where type=0 "+queryDate+") aa \n" +
                "      left JOIN (select user_id from cw_log where type=2 "+queryDate+") bb on aa.user_id = bb.user_id\n" +
                "        where aa.invite_code=a.user_id " +
                "          and bb.user_id is null) as registerNoApply,      " +
                "    (select count(DISTINCT bb.user_id)  " +
                "       from (select user_id,invite_code,apply_date from cw_log where type=0 "+queryDate+") aa  " +
                "       left JOIN (select user_id from cw_log where type=2 "+queryDate+") bb " +
                "         on aa.user_id = bb.user_id " +
                "      where aa.invite_code=a.user_id  " +
                "        and bb.user_id is not null) as registerApply,  " +
                "    sum(loan_user) as loanUser  " +
                "  from cw_user_integral_log a inner join cw_activity b on a.active_id = b.id  " +
                "  left join cw_user_integral c on a.user_id = c.user_id  \n" +
                " where 1=1  \n" +
                "   and a.integral_type=1 \n";
            if(integralLogDto.getId()!=null){
                sql += " and a.id = "+integralLogDto.getId();
            }
            //用户号码
            if(integralLogDto.getName()!=null&&!"".equals(integralLogDto.getName())){
                sql+=" and a.phone like '%"+integralLogDto.getName()+"%'";
            }
            //活动时间
            if(integralLogDto.getApplyStartDate()!=null&&!"".equals(integralLogDto.getApplyStartDate())){
                sql+= " and date_format(a.apply_date,'%Y-%m-%d') between '"+integralLogDto.getApplyStartDate()+"' and '"+integralLogDto.getApplyEndDate()+"'";
            }
            sql+=   " group by a.id,         \n" +
                    "          a.phone,      \n" +
                    "          b.name,       \n" +
                    "          a.integral,   \n" +
                    "          c.integral,   \n" +
                    "          CONCAT(date_format(b.start_date,'%Y-%m-%d'),'至',date_format(b.end_date,'%Y-%m-%d')) " ;


        List<IntegralLogDto> integralLogDtos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(IntegralLogDto.class));
        return integralLogDtos;
    }

}
