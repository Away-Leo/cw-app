package com.cw.biz.report.domain.dao;

import com.cw.biz.CwException;
import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.report.app.dto.AgencyDailyDto;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 */
@Repository
public class AgencyDailyDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询代理人报表
     * @param reportSearchDto
     * @return
     */
    public List<AgencyDailyDto> findAgencyDailyByDayId(ReportSearchDto reportSearchDto){

        String sql = "select ad.id id, ad.day_id dayId, ad.product_id productId, ap.name productName, ad.channel_id channelId,\n" +
                "ac.name channelName, ac.url productUrl, ac.admin_url productAdminUrl, ac.account_name productUserName, ac.account_pwd productPwd,\n" +
                "(select settle_cycle from cw_agency_channel where id=ac.channel_id) settleCycle, ad.in_price inPrice, ad.apply_num applyNum, ad.out_price outPrice, (ad.apply_num * ad.out_price) cost,\n" +
                "(ad.in_price - ad.out_price) profit, (ad.apply_num * (ad.in_price - ad.out_price)) profitCount, ad.money_info moneyInfo, ad.is_settle isSettle,\n" +
                "ad.prepare_amount_balance prepareAmountBalance\n" +
                "from cw_report_agency_daily ad\n" +
                "left join cw_agency_product ap on ap.id=ad.product_id\n" +
                "left join cw_agency_channel ac on ac.id=ad.channel_id\n" +
                "where 1 = 1 \n";

        if(StringUtils.isBlank(reportSearchDto.getApplyStartDate()) && StringUtils.isBlank(reportSearchDto.getApplyEndDate())) {
            sql += "  and ad.day_id = DATE_FORMAT(date_sub(curdate(),interval 1 day),'%Y-%m-%d') \n" ;
        }
        if(!StringUtils.isBlank(reportSearchDto.getApplyStartDate()) && StringUtils.isBlank(reportSearchDto.getApplyEndDate())) {
            sql += "  and ad.day_id >= DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m-%d') ";
        }
        if(StringUtils.isBlank(reportSearchDto.getApplyStartDate()) && !StringUtils.isBlank(reportSearchDto.getApplyEndDate())) {
            sql += " and ad.day_id <= DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyEndDate()+"','%Y-%m-%d'),'%Y-%m-%d')  \n" ;
        }
        if(!StringUtils.isBlank(reportSearchDto.getApplyStartDate()) && !StringUtils.isBlank(reportSearchDto.getApplyEndDate())) {
            sql += " and ad.day_id >= DATE_FORMAT(STR_TO_DATE('" + reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m-%d') ";
            sql += " and ad.day_id <= DATE_FORMAT(STR_TO_DATE('" + reportSearchDto.getApplyEndDate() + "','%Y-%m-%d'),'%Y-%m-%d')  \n";
        }

        if(reportSearchDto.getProductName()!=null){
            sql+=" and ad.product_id in (select id from cw_agency_product where name like '%"+reportSearchDto.getProductName()+"%')";
        }

        //APP名称
        if(!"".equals(reportSearchDto.getAppName())&&reportSearchDto.getAppName()!=null){
            sql += " and ad.channel_id in (select id from cw_agency_channel where name like '%"+reportSearchDto.getAppName()+"%') ";
        }

        if(reportSearchDto.getManagerId() != null) {
            sql += " and ac.manager_id=" + reportSearchDto.getManagerId();
        }

        sql += " order by ad.product_id desc";

        List<AgencyDailyDto> agencyDailyDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AgencyDailyDto.class));

        return agencyDailyDtoList;
    }

    @Transactional
    public Boolean agencyUpdate(AgencyDailyDto dto) {
        if(dto.getInPrice() == null || dto.getOutPrice() == null || dto.getApplyNum() == null){
            CwException.throwIt("接入单价或接入量或接出单价不能为空");
        }
        //修改日报数据
        String sql=" update cw_report_agency_daily " ;
        BigDecimal cost = BigDecimal.ZERO;
        if(dto.getInPrice() != null) {
            //接入单价
            sql+=    " set in_price=" + dto.getInPrice();
            //接出单价
            sql+=    " ,out_price=" + dto.getOutPrice();
            //接入量
            sql+=    " ,apply_num=" + dto.getApplyNum();
            //产生费用
            cost = dto.getOutPrice().multiply(new BigDecimal(dto.getApplyNum()));
            sql += " , cost=" + cost;
            //单条利润
            BigDecimal profit = dto.getInPrice().subtract(dto.getOutPrice());
            sql += " , profit=" + profit;
            //总利润
            BigDecimal profitCount = profit.multiply(new BigDecimal(dto.getApplyNum()));
            sql += " , profit_count=" + profitCount;
            //是否结算
            if(dto.getIsSettle() != null) {
                sql += " , is_settle='" + dto.getIsSettle() + "'";
            }
        }

        //打款信息
        if(!StringUtils.isBlank(dto.getMoneyInfo())) {
            sql += " , money_info='" + dto.getMoneyInfo() + "'";
        }
        sql+=    " where id=" + dto.getId();
        int result=jdbcTemplate.update(sql);

        //如果此次更新是已结算，并且是预付款，需要扣减预付
//        if(dto.getIsSettle() != null && dto.getIsSettle() == 1) {
//            sql = "select prepare_amount from cw_agency_channel where id=" + dto.getChannelId();
//            try {
//                BigDecimal prepareAmountBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class);
//                if(prepareAmountBalance != null) {
//                    if(prepareAmountBalance.compareTo(cost) < 0) {
//                        CwException.throwIt("预付款不够支付，请先给渠道充值预付款");
//                    }
//                    sql = "update cw_agency_channel set prepare_amount=prepare_amount-" + cost + " where id=" + dto.getChannelId();
//                    jdbcTemplate.update(sql);
//                    sql = "update cw_report_agency_daily set prepare_amount=" + prepareAmountBalance + ", prepare_amount_balance=" + prepareAmountBalance.subtract(cost) + " where id=" + dto.getId();
//                    jdbcTemplate.update(sql);
//                }
//            } catch (Exception e) {
//                //查询没有值，不做处理
//            }
//        }

        if(result == 1) {
            return true;
        } else {
            return false;
        }
    }
}

