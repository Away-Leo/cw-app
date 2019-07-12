package com.cw.biz.creditcard.domain.dao;

import com.cw.biz.creditcard.app.dto.CreditCardDto;
import com.cw.biz.home.app.dto.ReportSearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 信用卡申请统计
 */
@Repository
public class CreditCardDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 信用卡申请统计分析
     *
     * @return
     */
    public List<CreditCardDto> creditCardApply(ReportSearchDto reportSearchDto) {

        String sql = "select a.product_id  as id ,b.account_name as accountName,b.name,count(1) as applyTime," +
                "            count(distinct user_id) as applyNum \n" +
                "       from cw_log a left join cw_credit_card b on a.product_id = b.id\n" +
                "      where type=7 \n";
                if(reportSearchDto.getApplyStartDate()!=null&&!"".equals(reportSearchDto.getApplyStartDate())){
                    sql+=" and DATE_FORMAT(a.apply_date,'%Y-%m-%d') between  '"+reportSearchDto.getApplyStartDate()+"' " +
                         " and '"+reportSearchDto.getApplyEndDate()+"' \n" ;
                }else{
                    sql+="        and DATE_FORMAT(a.apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d')\n" ;
                }
                if(reportSearchDto.getProductName()!=null&&!"".equals(reportSearchDto.getProductName())){
                    sql+="        and b.name like   '%"+reportSearchDto.getProductName()+"%' " ;
                }
                sql += "   group by a.product_id,b.account_name,b.name order by apply_date desc \n" +
                " ";

        List<CreditCardDto> applyNumList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(CreditCardDto.class));
        return applyNumList;
    }
}