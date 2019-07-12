package com.cw.biz.report.domain.dao;

import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.product.app.dto.ProductCooperativePriceDto;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.biz.report.app.dto.SettleDto;
import com.cw.core.common.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/9/5.
 */
@Repository
public class MarketDailyDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询产品当天申请人数
     * @param reportSearchDto
     * @return
     */
    public List<MarketDailyDto> findMarketDailyByDayId(ReportSearchDto reportSearchDto){
        if("".equals(reportSearchDto.getSortDesc())){
            reportSearchDto.setSortColumn("show_order");
            reportSearchDto.setSortDesc("asc");
        }else{
            if ("asc".equals(reportSearchDto.getSortDesc())){
                reportSearchDto.setSortDesc("desc");
            }else{
                reportSearchDto.setSortDesc("asc");
            }
        }

        String sql = " select '合计' as enterpriseName,'' as id,'' as productName,'' as cooperationModel,'' as express, '' as postNo,'' as postDate," +
                "              now() as importDate,status,0 show_order,0 as isBilling, 0 settleCycle," +
                "              max(case when apply_numa>0 or apply_nums>0 or loan_amount>0 then day_id else 0 end) as dayId," +
                "              sum(apply_numa) as applyNuma,sum(apply_nums) as applyNums,sum(loan_amount) as loanAmount,  " +
                "              sum(apply_time) as applyTime,sum(apply_num) as applyNum, " +
                "              sum(income_a) as incomeA, " +
                "              sum(income_s) as incomeS, " +
                "              sum(total_income) as totalIncome, " +
                "              ROUND((select parameter_value from cw_parameter where parameter_code='operateCost')/"+getTotalApply(reportSearchDto)+",2) as thousandCost, " +
                "              round(sum(price_a)/sum(case when apply_numa >0 then 1 else 0 end),2) as priceA,  " +
                "              round(sum(price_s)/sum(case when apply_nums >0 then 1 else 0 end),2) as priceS,  " +
                "              IFNULL(round(sum(total_income)/sum(case when apply_numa > 0 or apply_nums>0 then apply_num else 0 end),2),0)  as thousandIncome, " +
                "              sum(apply_numa)/sum(apply_num) as convertCpa," +
                "              sum(apply_nums)/sum(apply_num) as convertCps," +
                "              '"+reportSearchDto.getSortDesc()+"' as sortDesc " +
                "        from cw_report_market_daily a where 1=1 " ;
        if(reportSearchDto.getApplyStartDate()==null || "".equals(reportSearchDto.getApplyStartDate())) {
            sql += "  and day_id = DATE_FORMAT(date_sub(curdate(),interval 1 day),'%Y-%m-%d') \n" ;
        }else{
            sql += "  and day_id between DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m-%d') " +
                    " and DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyEndDate()+"','%Y-%m-%d'),'%Y-%m-%d')  \n" ;
        }
        if(reportSearchDto.getStatus()!=9){
            if(reportSearchDto.getStatus()==5) {
                sql += " and status in (5,8) ";
            }else{
                sql += " and status = " + reportSearchDto.getStatus();
            }
        }

        if(reportSearchDto.getProductName()!=null){
            sql+=" and product_id in (select id from cw_product where name like '%"+reportSearchDto.getProductName()+"%')";
        }
        /**
         * 根据登录用户权限查询相应的数据
         */
        if(!CPContext.getContext().getSeUserInfo().getUsername().equals("admin")&&
                !CPContext.getContext().getSeUserInfo().getUsername().equals("zhouyue")&&
                !CPContext.getContext().getSeUserInfo().getUsername().equals("yangxin")) {
            //sql += " and operate_no = "+CPContext.getContext().getSeUserInfo().getId();
        }

        if(reportSearchDto.getIsValid()!=null&&reportSearchDto.getIsValid()){
            sql+=" and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司' and is_valid=1)";
        }
        if(reportSearchDto.getIsValid()!=null&&!reportSearchDto.getIsValid()){
            sql+=" and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司' and is_valid=0)";
        }
        if(reportSearchDto.getCooperationModel()!=null&&!"".equals(reportSearchDto.getCooperationModel())){
            sql += " and product_id in (select id from cw_product where cooperation_model='"+reportSearchDto.getCooperationModel()+"') ";
        }
        //公司名称
        if(!"".equals(reportSearchDto.getEnterpriseName())&&reportSearchDto.getEnterpriseName()!=null){
            sql += " and product_id in (select id from cw_product where account_name like '%"+reportSearchDto.getEnterpriseName()+"%') ";
        }

        sql += " union all ";
        sql += " select aa.*,'"+reportSearchDto.getSortDesc()+"' as sortDesc " +
                "    from ( select  a.*,(select price_a from cw_report_market_daily where day_id = a.dayId and product_id = a.id ) as priceA,  " +
                "     (select price_s from cw_report_market_daily where day_id = a.dayId and product_id = a.id ) as priceS,  " +
                "              IFNULL(ROUND(((sum(applyNuma)/sum(applyNum))*(select price_a from cw_report_market_daily where day_id = a.dayId and product_id = a.id )),2),0)+" +
                "              IFNULL((case when (select price_type from cw_product_cooperative_price WHERE product_id=a.id and cooperation_model='CPS' " +
                "                              and (a.dayId BETWEEN IFNULL(start_cycle,'2016-12-31') AND IFNULL(end_cycle,'2099-12-31')) ) = 'FIX' then " +
                "                     ROUND((sum(applyNums)/sum(applyNum))*(select price_s from cw_report_market_daily where day_id = a.dayId and product_id = a.id ),2)" +
                "                   ELSE " +
                "                     ROUND((sum(applyNums)/sum(applyNum))*(sum(loanAmount)/sum(applyNums))*(select price_s from cw_report_market_daily where day_id = a.dayId and product_id = a.id )/100,2)" +
                "              end),0)  as thousandIncome," +
                "             sum(applyNuma)/sum(applyNum) as convertCpa,sum(applyNums)/sum(applyNum) as convertCps " +
                " from ( " +
                "      select account_name as enterpriseName,product_id as id,b.name as productName,b.cooperation_model as cooperationModel," +
                "              express,post_no as postNo,date_format(post_date,'%Y-%m-%d') as postDate," +
                "              a.import_date as importDate,status,b.show_order,a.is_billing as isBilling,b.settle_cycle as settleCycle," +
                "              max(case when apply_numa>0 or apply_nums>0 or a.loan_amount>0 then day_id else 0 end) as dayId," +
                "              sum(apply_numa) as applyNuma,sum(apply_nums) as applyNums,sum(a.loan_amount) as loanAmount,  " +
                "              sum(apply_time) as applyTime,sum(apply_num) as applyNum," +
                "              sum(income_a) as incomeA, " +
                "              sum(income_s) as incomeS, " +
                "              sum(total_income) as totalIncome, " +
                "              ROUND((select parameter_value from cw_parameter where parameter_code='operateCost')/"+getTotalApply(reportSearchDto)+",2) as  thousandCost" +
                "        from cw_report_market_daily a inner join cw_product b on a.product_id = b.id  where 1=1 " ;
            if(reportSearchDto.getApplyStartDate()==null || "".equals(reportSearchDto.getApplyStartDate())) {
                sql += "\t\t and day_id = DATE_FORMAT(date_sub(curdate(),interval 1 day),'%Y-%m-%d') \n" ;
            }else{
                sql += " and day_id between DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m-%d') " +
                       " and DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyEndDate()+"','%Y-%m-%d'),'%Y-%m-%d')  \n" ;
            }

            if(reportSearchDto.getStatus()!=9){
                if(reportSearchDto.getStatus()==5) {
                    sql += " and status in (5,8) ";
                }else{
                    sql += " and status = " + reportSearchDto.getStatus();
                }
            }

            if(reportSearchDto.getProductName()!=null){
                sql+=" and product_id in (select id from cw_product where name like '%"+reportSearchDto.getProductName()+"%')";
            }
            //公司名称
            if(!"".equals(reportSearchDto.getEnterpriseName())&&reportSearchDto.getEnterpriseName()!=null){
                sql += " and b.account_name like '%"+reportSearchDto.getEnterpriseName()+"%'";
            }
            /**
             * 根据登录用户权限查询相应的数据
             */
            if(!CPContext.getContext().getSeUserInfo().getUsername().equals("admin")&&
                    !CPContext.getContext().getSeUserInfo().getUsername().equals("zhouyue")&&
                    !CPContext.getContext().getSeUserInfo().getUsername().equals("yangxin")) {
                //sql += " and a.operate_no = "+CPContext.getContext().getSeUserInfo().getId();
            }

            if(reportSearchDto.getIsValid()!=null&&reportSearchDto.getIsValid()){
                sql+=" and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司' and is_valid=1)";
            }
            if(reportSearchDto.getIsValid()!=null&&!reportSearchDto.getIsValid()){
                sql+=" and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司' and is_valid=0)";
            }

            if(reportSearchDto.getCooperationModel()!=null&&!"".equals(reportSearchDto.getCooperationModel())){
                sql += " and b.cooperation_model='"+reportSearchDto.getCooperationModel()+"'";
            }
            sql +=   " group by account_name ,product_id,b.name,b.cooperation_model,express,post_no,date_format(post_date,'%Y-%m-%d'),a.import_date,status,b.show_order,a.is_billing,b.settle_cycle ";
            sql+=" ) a group by enterpriseName ,id,productName,cooperationModel,express,postNo,postDate,importDate,status,show_order,isBilling,settleCycle " ;
            sql+=  " order by "+reportSearchDto.getSortColumn()+" "+ reportSearchDto.getSortDesc();
            sql+=" ) aa ";

        List<MarketDailyDto> marketDailyDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MarketDailyDto.class));

        return marketDailyDtoList;
    }

    /**
     * 当月申请用户数
     * @param reportSearchDto
     * @return
     */
    private Integer getTotalApply(ReportSearchDto reportSearchDto){
        String sql = " select sum(num) as applyNum   " +
                "        from (select product_id,count(1) as applyTime,count(distinct user_id) as num\n" +
                "                 from cw_log a        \n" +
                "                where 1=1 \n" +
                "                 and type=2         \n" +
                "                 and channel_no in (select code from cw_channel union all select code from cw_app_market)       \n" +
                "                 and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司' )         \n" +
                "                and user_id in (select id from pf_se_user where type='user' and register_date is not null) \n" ;
        if(reportSearchDto.getApplyStartDate()==null || "".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m') = DATE_FORMAT(now(),'%Y-%m') \n" ;
        }else{
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m-%d') " +
                   " and DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyEndDate()+"','%Y-%m-%d'),'%Y-%m-%d')  \n" ;
//            sql += " and DATE_FORMAT(apply_date,'%Y-%m') = DATE_FORMAT(now(),'%Y-%m') \n" ;

        }
        sql+=  "   group by product_id) a " ;

        List<MarketDailyDto> marketDailyDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MarketDailyDto.class));
        if(marketDailyDtoList.size()>0)
        {
            return marketDailyDtoList.get(0).getApplyNum();
        }
        return 1;
    }
    /**
     * 查询当月结算数据
     * @param reportSearchDto
     * @return
     */
    public List<MarketDailyDto> getMarketMonth(ReportSearchDto reportSearchDto){

        if(reportSearchDto.getApplyStartDate()==null||"".equals(reportSearchDto.getApplyStartDate())){
            reportSearchDto.setApplyStartDate(Utils.convertNowBeforeDate(new Date()).substring(0,7));
        }

        if(reportSearchDto.getSortColumn()==null){
            reportSearchDto.setSortColumn("show_order");
        }
        if("asc".equals(reportSearchDto.getSortDesc())){
            reportSearchDto.setSortDesc("desc");
        }else{
            reportSearchDto.setSortDesc("asc");
        }

        String statusSql="";
        if(reportSearchDto.getStatus()!=9){
            if(reportSearchDto.getStatus()==99) {
                statusSql += " and status in (2,3,4,5) ";
            }else if(reportSearchDto.getStatus()==5) {
                statusSql += " and status in (5,8) ";
            }else{
                statusSql += " and status = " + reportSearchDto.getStatus();
            }
        }

        String sql = " select '合计' as enterpriseName,'' as id," +
                "       '' as productName," +
                "       '' as cooperationModel," +
                "       '' as operateName,"+
                "       now() as importDate,status,0 show_order,0 as isBilling," +
                "      ''  as settleCycle," +
                "      sum(applyTime) as applyTime," +
                "      sum(applyNum) as applyNum," +
                "      sum(applyNuma) as applyNuma," +
                "      sum(applyNums) as applyNums," +
                "      max(dayId) AS dayId, " +
                "      (case when sum(applyNum)=0 then 0 else round(sum(applyNuma)/sum(applyNum)*100,2) end) as applyNumaRate, " +
                "      (case when sum(applyNum)=0 then 0 else round(sum(applyNums)/sum(applyNum)*100,2) end) as applyNumsRate, " +
                "      sum(incomeA) as incomeA," +
                "      sum(incomeS) as incomeS," +
                "      sum(loanAmount) as loanAmount," +
                "      sum(invoiceFee) as invoiceFee," +
                "      sum(totalIncome) as totalIncome," +
                "      sum(incomeFee) as incomeFee, " +
                "      max(incomeMemo) as incomeMemo, " +
                "      sum(confirmSettleFee) as confirmSettleFee, " +
                "      max(priceA) as priceA," +
                "      max(priceS) as priceS," +
                "      max(sortDesc) as sortDesc " +
                " from (select product_id,status,sum(apply_time) as applyTime, " +
                "       sum(apply_num) as applyNum, " +
                "       sum(apply_numa) as applyNuma, " +
                "       sum(apply_nums) as applyNums," +
                "       '' AS dayId, " +
                "       sum(income_a) as incomeA, " +
                "       sum(income_s) as incomeS, " +
                "       sum(a.loan_amount) as loanAmount, " +
                "       (case when a.is_billing=3 then ROUND(sum(total_income)/1.03*0.03,2) else 0 end) as invoiceFee, " +
                "       sum(total_income) as totalIncome, " +
                "       (case when b.settle_cycle=3 then sum(distinct income_fee) else sum(income_fee) end) as incomeFee, " +
                "       sum(income_memo) as incomeMemo, " +
                "       (case when b.settle_cycle=3 then sum(distinct confirm_settle_fee) else sum(confirm_settle_fee) end) as confirmSettleFee ,"+
                "       round(sum(price_a)/sum(case when apply_Numa>0 then 1 else 0 end),2) as priceA, " +
                "       round(sum(price_s)/sum(case when apply_Nums>0 and price_s<10 then 1 else 0 end),2) as priceS, " +
                "       '"+reportSearchDto.getSortDesc()+"' as sortDesc " +
                "  from cw_report_market_daily a  INNER join cw_product b on a.product_id = b.id " +
                " where 1=1  " ;
           sql += " and substr(day_id,1,7) = '"+reportSearchDto.getApplyStartDate()+"' ";
        sql += statusSql;
        //发票类型
        if(!"9".equals(reportSearchDto.getInvoiceType())&&reportSearchDto.getInvoiceType()!=null){
            sql += " and b.invoice_type = "+reportSearchDto.getInvoiceType();
        }
        //产品名称
        if(!"".equals(reportSearchDto.getProductName())&&reportSearchDto.getProductName()!=null){
            sql += " and a.product_name like '%"+reportSearchDto.getProductName()+"%'";
        }
        //公司名称
        if(!"".equals(reportSearchDto.getEnterpriseName())&&reportSearchDto.getEnterpriseName()!=null){
            sql += " and b.account_name like '%"+reportSearchDto.getEnterpriseName()+"%'";
        }
        if(9!=reportSearchDto.getIsBilling()){
            sql += " and a.is_billing = '"+reportSearchDto.getIsBilling()+"'";
        }
        //回款时间
        if(!"".equals(reportSearchDto.getBackDate())&&reportSearchDto.getBackDate()!=null){
            sql += " and date_format(a.back_fee_date,'%Y-%m') ='"+reportSearchDto.getBackDate()+"'";
        }
        //月结方式
        if(!"".equals(reportSearchDto.getSettleCycle())&&reportSearchDto.getSettleCycle()!=null){
            sql += " and IFNULL(b.settle_cycle,3) ='"+reportSearchDto.getSettleCycle()+"'";
        }
        //客户联系人
        if(!"all".equals(reportSearchDto.getOperateNo())&&!"".equals(reportSearchDto.getOperateNo())&&reportSearchDto.getOperateNo()!=null){
            sql += " and a.operate_no ='"+reportSearchDto.getOperateNo()+"'";
        }

        sql+=" group by product_id,status ) a " ;
        sql += " union all ";
        sql +=  "  select aa.*,'"+reportSearchDto.getSortDesc()+"' as sortDesc " +
                " from ( select aa.*,(select price_a from cw_report_market_daily where day_id = aa.dayId and product_id = aa.id ) as priceA," +
                "  (select price_s from cw_report_market_daily where day_id = aa.dayId and product_id = aa.id ) as priceS " +
                "       from (select b.account_name as enterpriseName,a.product_id as id,b.name as productName," +
                "               b.cooperation_model as cooperationModel, " +
                "               c.display_name as operateName,"+
                "               a.import_date as importDate,a.status,b.show_order,a.is_billing as isBilling,ifnull(b.settle_cycle,'3') as settleCycle," +
                "                   sum(apply_time) as applyTime, " +
                "                   sum(apply_num) as applyNum, " +
                "                   sum(apply_numa) as applyNuma, " +
                "                   sum(apply_nums) as applyNums, " +
                "                   max(case when apply_numa>0 or apply_nums>0 or a.loan_amount>0 then day_id else 0 end) as dayId, " +
                "                   (case when sum(apply_num)=0 then 0 else round(sum(apply_numa)/sum(apply_num)*100,2) end) as applyNumaRate, " +
                "                   (case when sum(apply_num)=0 then 0 else round(sum(apply_nums)/sum(apply_num)*100,2) end) as applyNumsRate, " +
                "                   sum(income_a) as incomeA, " +
                "                   sum(income_s) as incomeS, " +
                "                   sum(a.loan_amount) as loanAmount, " +
                "                   (case when a.is_billing=3 then ROUND(sum(total_income)/1.03*0.03,2) else 0 end) as invoiceFee, " +
                "                   sum(total_income) as totalIncome, " +
                "                   (case when b.settle_cycle=3 then sum(distinct income_fee) else sum(income_fee) end) as incomeFee, " +
                "                   max(income_memo) as incomeMemo, " +
                "                   (case when b.settle_cycle=3 then sum(distinct confirm_settle_fee) else sum(confirm_settle_fee) end) as confirmSettleFee " +
                "            from cw_report_market_daily a INNER join cw_product b on a.product_id = b.id " +
                "            left join pf_se_user c on b.operate_no=c.id " +
                "           where 1=1 " ;
        sql += " and substr(day_id,1,7) = '"+reportSearchDto.getApplyStartDate()+"' ";
        sql += statusSql;

        //发票类型
        if(!"9".equals(reportSearchDto.getInvoiceType())&&reportSearchDto.getInvoiceType()!=null)
        {
            sql += " and b.invoice_type = "+reportSearchDto.getInvoiceType();
        }
        //产品名称
        if(!"".equals(reportSearchDto.getProductName())&&reportSearchDto.getProductName()!=null){
            sql += " and a.product_name like '%"+reportSearchDto.getProductName()+"%'";
        }
        //公司名称
        if(!"".equals(reportSearchDto.getEnterpriseName())&&reportSearchDto.getEnterpriseName()!=null){
            sql += " and b.account_name like '%"+reportSearchDto.getEnterpriseName()+"%'";
        }
        if(9!=reportSearchDto.getIsBilling()){
            sql += " and a.is_billing = '"+reportSearchDto.getIsBilling()+"'";
        }
        //回款时间
        if(!"".equals(reportSearchDto.getBackDate())&&reportSearchDto.getBackDate()!=null){
            sql += " and date_format(a.back_fee_date,'%Y-%m') ='"+reportSearchDto.getBackDate()+"'";
        }
        //月结方式
        if(!"".equals(reportSearchDto.getSettleCycle())&&reportSearchDto.getSettleCycle()!=null){
            sql += " and IFNULL(b.settle_cycle,3) ='"+reportSearchDto.getSettleCycle()+"'";
        }

        //客户联系人
        if(!"all".equals(reportSearchDto.getOperateNo())&&!"".equals(reportSearchDto.getOperateNo())&&reportSearchDto.getOperateNo()!=null){
            sql += " and a.operate_no ='"+reportSearchDto.getOperateNo()+"'";
        }

        sql +=   " group by b.account_name,a.product_id,b.name,b.cooperation_model," +
                "           c.display_name,a.import_date,a.status,b.show_order,a.is_billing,ifnull(b.settle_cycle,'3') ";
        sql += " order by "+reportSearchDto.getSortColumn()+" "+reportSearchDto.getSortDesc();
        sql+=" ) aa  ) aa " ;

        List<MarketDailyDto> marketDailyDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MarketDailyDto.class));

        return marketDailyDtoList;
    }

    /**
     * 根据开票信息统计查询当月结算数据
     * @param reportSearchDto
     * @return
     */
    public List<MarketDailyDto> getMarketMonthSettle(ReportSearchDto reportSearchDto){

        if(reportSearchDto.getApplyStartDate()==null||"".equals(reportSearchDto.getApplyStartDate())){
            reportSearchDto.setApplyStartDate(Utils.convertNowBeforeDate(new Date()).substring(0,7));
        }

        if(reportSearchDto.getSortColumn()==null){
            reportSearchDto.setSortColumn("show_order");
        }
        if("asc".equals(reportSearchDto.getSortDesc())){
            reportSearchDto.setSortDesc("desc");
        }else{
            reportSearchDto.setSortDesc("asc");
        }

        String sql = " select '' as settleNo,'合计' as enterpriseName," +
                "       '' as cooperationModel," +
                " null backFeeDate,"+
                "      status,0 as isBilling," +
                "      sum(applyTime) as applyTime," +
                "      sum(applyNum) as applyNum," +
                "      sum(applyNuma) as applyNuma," +
                "      sum(applyNums) as applyNums," +
                "      (case when sum(applyNum)=0 then 0 else round(sum(applyNuma)/sum(applyNum)*100,2) end) as applyNumaRate, " +
                "      (case when sum(applyNum)=0 then 0 else round(sum(applyNums)/sum(applyNum)*100,2) end) as applyNumsRate, " +
                "      sum(incomeA) as incomeA," +
                "      sum(incomeS) as incomeS," +
                "      sum(loanAmount) as loanAmount," +
                "      sum(invoiceFee) as invoiceFee," +
                "      sum(totalIncome) as totalIncome," +
                "      sum(incomeFee) as incomeFee, " +
                "      max(incomeMemo) as incomeMemo, " +
                "      max(priceA) as priceA," +
                "      max(priceS) as priceS," +
                "      max(sortDesc) as sortDesc " +
                " from (select settle_no as settleNo," +
                "                status," +
                "                sum(apply_time) as applyTime, " +
                "                sum(apply_num) as applyNum, " +
                "                sum(apply_numa) as applyNuma, " +
                "                sum(apply_nums) as applyNums," +
                "                sum(income_a) as incomeA, " +
                "                sum(income_s) as incomeS, " +
                "                sum(a.loan_amount) as loanAmount, " +
                "                (case when a.is_billing=3 then ROUND(sum(total_income)/1.03*0.03,2) else 0 end) as invoiceFee, " +
                "                sum(total_income) as totalIncome, " +
                "                max(income_fee) as incomeFee, " +
                "                max(income_memo) as incomeMemo, " +
                "                round(sum(price_a)/sum(case when apply_Numa>0 then 1 else 0 end),2) as priceA, " +
                "                round(sum(price_s)/sum(case when apply_Nums>0 and price_s<10 then 1 else 0 end),2) as priceS, " +
                "                '"+reportSearchDto.getSortDesc()+"' as sortDesc " +
                "         from cw_report_market_daily a  INNER join cw_product b on a.product_id = b.id" +
                "        where 1=1  " ;
         sql += " and substr(day_id,1,7) = '"+reportSearchDto.getApplyStartDate()+"' and status in (2,3,4,5) ";
         if(reportSearchDto.getStatus()!=9){
            if(reportSearchDto.getStatus()==8) {
                sql += " and status in (2,3,4,5) ";
            }else{
                sql += " and status = " + reportSearchDto.getStatus();
            }
         }
        //发票类型
        if(!"9".equals(reportSearchDto.getInvoiceType())&&reportSearchDto.getInvoiceType()!=null){
            sql += " and b.invoice_type = "+reportSearchDto.getInvoiceType();
        }
        //产品名称
        if(!"".equals(reportSearchDto.getProductName())&&reportSearchDto.getProductName()!=null){
            sql += " and a.product_name like '%"+reportSearchDto.getProductName()+"%'";
        }
        //公司名称
        if(!"".equals(reportSearchDto.getEnterpriseName())&&reportSearchDto.getEnterpriseName()!=null){
            sql += " and b.account_name like '%"+reportSearchDto.getEnterpriseName()+"%'";
        }
        if(9!=reportSearchDto.getIsBilling()){
            sql += " and a.is_billing = '"+reportSearchDto.getIsBilling()+"'";
        }
        sql+=" group by product_id,status ) a " ;
        sql += " union all ";
        sql +=  "  select aa.*,'"+reportSearchDto.getSortDesc()+"' as sortDesc " +
                " from ( select aa.*" +
                "       from (select a.settle_no as settleNo,b.account_name as enterpriseName," +
                "               b.cooperation_model as cooperationModel,date_format(a.back_fee_date,'%Y-%m-%d') as backFeeDate, " +
                "               a.status,a.is_billing as isBilling," +
                "                   sum(apply_time) as applyTime, " +
                "                   sum(apply_num) as applyNum, " +
                "                   sum(apply_numa) as applyNuma, " +
                "                   sum(apply_nums) as applyNums, " +
                "                   (case when sum(apply_num)=0 then 0 else round(sum(apply_numa)/sum(apply_num)*100,2) end) as applyNumaRate, " +
                "                   (case when sum(apply_num)=0 then 0 else round(sum(apply_nums)/sum(apply_num)*100,2) end) as applyNumsRate, " +
                "                   sum(income_a) as incomeA, " +
                "                   sum(income_s) as incomeS, " +
                "                   sum(a.loan_amount) as loanAmount, " +
                "                   (case when a.is_billing=1 then ROUND(sum(total_income)/1.03*0.03,2) else 0 end) as invoiceFee, " +
                "                   sum(total_income) as totalIncome, " +
                "                   max(income_fee) as incomeFee, " +
                "                   max(income_memo) as incomeMemo, " +
                "                   max(price_a) as priceA,"+
                "                   max(price_s) as priceS "+
                "            from cw_report_market_daily a INNER join cw_product b on a.product_id = b.id" +
                "           where 1=1 " ;
        sql += " and substr(day_id,1,7) = '"+reportSearchDto.getApplyStartDate()+"' and status in (2,3,4,5)  ";
        if(reportSearchDto.getStatus()!=9){
            if(reportSearchDto.getStatus()==8) {
                sql += " and status in (2,3,4,5) ";
            }else{
                sql += " and status = " + reportSearchDto.getStatus();
            }
        }
        //发票类型
        if(!"9".equals(reportSearchDto.getInvoiceType())&&reportSearchDto.getInvoiceType()!=null)
        {
            sql += " and b.invoice_type = "+reportSearchDto.getInvoiceType();
        }
        //产品名称
        if(!"".equals(reportSearchDto.getProductName())&&reportSearchDto.getProductName()!=null){
            sql += " and a.product_name like '%"+reportSearchDto.getProductName()+"%'";
        }
        //公司名称
        if(!"".equals(reportSearchDto.getEnterpriseName())&&reportSearchDto.getEnterpriseName()!=null){
            sql += " and b.account_name like '%"+reportSearchDto.getEnterpriseName()+"%'";
        }
        if(9!=reportSearchDto.getIsBilling()){
            sql += " and a.is_billing = '"+reportSearchDto.getIsBilling()+"'";
        }
        sql +=   " group by a.settle_no,b.account_name,b.cooperation_model,date_format(a.back_fee_date,'%Y-%m-%d'),a.status,a.is_billing ";
        sql += " order by "+reportSearchDto.getSortColumn()+" "+reportSearchDto.getSortDesc();
        sql+=" ) aa  ) aa " ;

        List<MarketDailyDto> marketDailyDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MarketDailyDto.class));

        return marketDailyDtoList;
    }
    /**
     * 邮递快递信息
     * @param reportSearchDto
     * @return
     */
    public List<MarketDailyDto> marketMonthList(ReportSearchDto reportSearchDto){
        String sql=  "        select b.account_name as enterpriseName," +
                "                a.product_id as productId," +
                "                a.STATUS,b.name as productName," +
                "                b.cooperation_model as cooperationModel, " +
                "                b.invoice_link_address as invoiceLinkAddress," +
                "                b.invoice_link_phone as invoiceLinkPhone, "+
                "                a.express as express," +
                "                a.post_no as postNo," +
                "                a.post_date as postDate," +
                "                b.settle_cycle as  settleCycle"+
                "          from cw_report_market_daily a " +
                "    INNER join cw_product b on a.product_id = b.id" +
                "         where 1=1 and a.status in (3,4,7,8) " ;//月结产品3，4，日、周结产品7，8
        if(reportSearchDto.getApplyStartDate()==null || "".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and substr(day_id,1,7) = DATE_FORMAT(date_sub(curdate(),interval 1 MONTH" +
                    "),'%Y-%m') \n" ;
        }else{
            sql += " and substr(day_id,1,7) = DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m') ";
        }
        if(!Objects.isNull(reportSearchDto.getProductName())&&!"".equals(reportSearchDto.getProductName())){
            sql+= " and a.product_name like '%"+reportSearchDto.getProductName()+"%'";
        }
        if(!Objects.isNull(reportSearchDto.getPostNo())&&!"".equals(reportSearchDto.getPostNo())){
            sql+= " and a.post_no like '%"+reportSearchDto.getPostNo()+"%'";
        }
        //发票类型
        if(!"9".equals(reportSearchDto.getInvoiceType())&&reportSearchDto.getInvoiceType()!=null){
            sql += " and b.invoice_type = "+reportSearchDto.getInvoiceType();
        }
        sql +=   " group by b.account_name," +
                "                a.product_id," +
                "                a.STATUS,b.name ," +
                "                b.cooperation_model, " +
                "                b.invoice_link_address," +
                "                b.invoice_link_phone, "+
                "                a.express,a.post_no," +
                "                a.post_date,b.settle_cycle" +
                " order by b.show_order asc ";

        List<MarketDailyDto> marketDailyDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MarketDailyDto.class));

        return marketDailyDtoList;
    }

    /**
     * 查询市场每月明细
     * @param reportSearchDto
     * @return
     */
    public List<MarketDailyDto> marketDailyDetail(ReportSearchDto reportSearchDto) {

        String sql = " select a.id,day_id as dayId,a.product_id as productId,b.account_name as enterpriseName," +
                "              b.name as productName,apply_time as applyTime,apply_num as applyNum," +
                "              a.cooperation_model as cooperationModel,status,b.settle_cycle as settleCycle," +
                "              a.settle_channel as settleChannel,a.settle_memo as settlememo,a.income_memo as incomeMemo," +
                "              apply_numa as applyNuma," +
                "              apply_nums as applyNums," +
                "              apply_num as applyNum,a.loan_amount as loanAmount,a.income_fee as incomeFee," +
                "              price_a as priceA,price_s as priceS,income_a as incomeA,income_s as incomeS," +
                "              total_income as totalIncome," +
                "              import_date as importDate,  " +
                "              IFNULL(ROUND(((apply_numa/apply_num)*(price_a/(case when apply_numa >0 then 1 else 0 end))),2),0)+" +
                "              IFNULL((case when (select price_type from cw_product_cooperative_price WHERE product_id=a.id and cooperation_model='CPS' " +
                "                              and (now() BETWEEN IFNULL(start_cycle,'2016-12-31') AND IFNULL(end_cycle,'2099-12-31'))) = 'FIX' then " +
                "                     ROUND((apply_nums/apply_num)*(price_s/(case when apply_nums >0 then 1 else 0 end)),2)" +
                "                   ELSE " +
                "                     ROUND((apply_nums/apply_num)*(a.loan_amount/apply_nums)*(price_s/(case when apply_nums>0 then 1 else 0 end))/100,2)" +
                "              end),0)  as thousandIncome " +
                "        FROM cw_report_market_daily a " +
                "   inner join cw_product b on a.product_id = b.id \n" +
                "       where 1=1 ";
        if(reportSearchDto.getApplyStartDate()==null || "".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and substr(day_id,1,7) = DATE_FORMAT(date_sub(curdate(),interval 1 day),'%Y-%m') \n" ;
        }else{
            sql += " and substr(day_id,1,7) = DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m') ";
        }
        //产品名称
        sql += " and (product_id = "+("".equals(reportSearchDto.getProductId())?"0":reportSearchDto.getProductId());
        if(reportSearchDto.getProductName()!=null && !"".equals(reportSearchDto.getProductName())) {
            sql += " or a.product_name like '%"+reportSearchDto.getProductName()+"%' \n" ;
        }
        //公司名称
        if(!"".equals(reportSearchDto.getEnterpriseName())&&reportSearchDto.getEnterpriseName()!=null){
            sql += " and b.account_name like '%"+reportSearchDto.getEnterpriseName()+"%'";
        }
        sql +=")";
        sql += " union all " ;
        sql += " select 0 as id,'' as dayId,0 as productId,'合计' as enterpriseName,'' as productName,sum(apply_time) as applyTime," +
                "      sum(apply_num) as applyNum," +
               "        '' as cooperationModel,status,0 as settleCycle,'' as settleChannel,'' as settleMemo, '' as incomeMemo," +
               "      sum(apply_numa) as applyNuma,sum(apply_nums) as applyNums,sum(apply_num) as applyNum," +
               "      sum(loan_amount) as loanAmount," +
               "      sum(income_fee) as incomeFee," +
               "      round(sum(price_a)/sum(case when apply_numa>0 then 1 else 0 end),2) as priceA," +
               "      round(sum(price_s)/sum(case when apply_nums>0 then 1 else 0 end),2) as priceS," +
               "      sum(income_a) as incomeA," +
               "      sum(income_s) as incomeS," +
               "      sum(total_income) as totalIncome," +
               "      now() as importDate,  " +
               "      IFNULL(ROUND(((sum(apply_numa)/sum(apply_num))*(sum(price_a)/sum(case when apply_numa >0 then 1 else 0 end))),2),0)+" +
               "      IFNULL((case when (select price_type from cw_product_cooperative_price WHERE product_id=a.id and cooperation_model='CPS' " +
               "           and (now() BETWEEN IFNULL(start_cycle,'2016-12-31') AND IFNULL(end_cycle,'2099-12-31'))) = 'FIX' then " +
               "               ROUND((sum(apply_nums)/sum(apply_num))*(sum(price_s)/sum(case when apply_nums >0 then 1 else 0 end)),2)" +
               "           ELSE " +
               "               ROUND((sum(apply_nums)/sum(apply_num))*(sum(loan_amount)/sum(apply_nums))*(sum(price_s)/sum(case when apply_nums>0 then 1 else 0 end))/100,2)" +
               "       end),0)  as thousandIncome " +
               "    FROM cw_report_market_daily a \n" +
               "   where 1=1 ";
        if(reportSearchDto.getApplyStartDate()==null || "".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and substr(day_id,1,7) = DATE_FORMAT(date_sub(curdate(),interval 1 day),'%Y-%m') \n" ;
        }else{
            sql += " and substr(day_id,1,7) = DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m') ";
        }
        //产品名称
        sql += " and (product_id = "+("".equals(reportSearchDto.getProductId())?"0":reportSearchDto.getProductId());
        if(reportSearchDto.getProductName()!=null && !"".equals(reportSearchDto.getProductName())) {
            sql += " or a.product_name like '%"+reportSearchDto.getProductName()+"%' \n" ;
        }
        sql +=")";
        sql +=" order by dayId desc";

        List<MarketDailyDto> marketDailyDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MarketDailyDto.class));
        return marketDailyDtoList;
    }


    /**
     * 收入明细
     * @param reportSearchDto
     * @return
     */
    public List<MarketDailyDto> incomeBackList(ReportSearchDto reportSearchDto) {

        String sql = " select substr(day_id,1,7) as dayId,a.product_id as productId," +
                "             b.name as productName," +
                "             substr(a.back_fee_date,1,10) as backFeeDate, " +
                "             (case when b.settle_cycle=3 then sum(DISTINCT a.income_fee)else sum(a.income_fee) end) as incomeFee," +
                "             max(a.income_memo) as incomeMemo " +
                "        FROM cw_report_market_daily a " +
                "   inner join cw_product b on a.product_id = b.id \n" +
                "       where 1=1 ";
        if(reportSearchDto.getApplyStartDate()==null || "".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and substr(day_id,1,7) >= DATE_FORMAT(date_sub(curdate(),interval 1 month),'%Y-%m') \n" ;
        }else{
            sql += " and substr(day_id,1,7) >= DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m') ";
        }
        //产品名称
        sql += " and status=5 and product_id = "+("".equals(reportSearchDto.getProductId())?"0":reportSearchDto.getProductId());
        sql += " group by substr(day_id,1,7),a.product_id,b.name,substr(a.back_fee_date,1,7) ";

        sql += " union all " ;

        sql += " select '' as dayId,0 as productId," +
                "             '' as productName," +
                "             null as backFeeDate, " +
                "             sum(distinct a.income_fee) as incomeFee," +
                "             max(a.income_memo) as incomeMemo " +
                "        FROM cw_report_market_daily a " +
                "   inner join cw_product b on a.product_id = b.id \n" +
                "       where 1=1 ";
        if(reportSearchDto.getApplyStartDate()==null || "".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and substr(day_id,1,7) >= DATE_FORMAT(date_sub(curdate(),interval 1 month),'%Y-%m') \n" ;
        }else{
            sql += " and substr(day_id,1,7) >= DATE_FORMAT(STR_TO_DATE('"+reportSearchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m') ";
        }
        //产品名称
        sql += " and status=5 and product_id = "+("".equals(reportSearchDto.getProductId())?"0":reportSearchDto.getProductId());
        sql +=" order by dayId desc";

        List<MarketDailyDto> marketDailyDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MarketDailyDto.class));
        return marketDailyDtoList;
    }

    /**
     * 更新收入数据
     * @param reportSearchDto
     * @return
     */
    public List<MarketDailyDto> marketDailyDetailTest(ReportSearchDto reportSearchDto) {
        String sql ="select id,day_id as dayId,enterprise_name as enterpriseName,product_id as productId,product_name as productName,apply_time as applyTime,apply_num as applyNum," +
                "             cooperation_model as cooperationModel,status,ifnull(apply_numa,0) as applyNuma,ifnull(apply_nums,0) as applyNums,ifnull(apply_num,0) as applyNum,ifnull(loan_amount,0) as loanAmount," +
                "             price_a as priceA,price_s as priceS,income_a as incomeA,income_s as incomeS,total_income as totalIncome,import_date as importDate  " +
                "        FROM cw_report_market_daily a where product_id=207 \n" ;
        List<MarketDailyDto> marketDailyDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MarketDailyDto.class));
        return marketDailyDtoList;
    }

    /**
     * 日结确认回款
     * @param dailyDto
     * @return
     */
    public int updateDailySettleIncome(MarketDailyDto dailyDto){
        //修改日结数据的回款信息
        String sql=" update cw_report_market_daily a INNER JOIN (select product_id from cw_report_market_daily " +
                "     where id="+dailyDto.getId()+" )  b on a.product_id = b.product_id " +
                "       set status=5,back_fee_date=now() "+
                "     where day_id between '"+dailyDto.getStartDate()+"' and '"+dailyDto.getEndDate()+"' ";
        jdbcTemplate.update(sql);

        //选择最大的时间来输入回款金额
        sql=" update cw_report_market_daily a INNER JOIN (select product_id from cw_report_market_daily where id="+dailyDto.getId()+" )  b " +
                "  on a.product_id = b.product_id " +
                "       set income_fee = "+dailyDto.getIncomeFee()+","+
                "           income_memo ='"+dailyDto.getIncomeMemo()+"'"+
                "     where day_id = '"+dailyDto.getEndDate()+"' ";

        return jdbcTemplate.update(sql);
    }
        /**
         * 修改回盘数据
         * @param dailyDto
         * @return
         */
    public int updateCounteroffer(MarketDailyDto dailyDto){

        Boolean flag=Boolean.FALSE;

        BigDecimal priceA = BigDecimal.ZERO;
        BigDecimal priceS = BigDecimal.ZERO;
        BigDecimal incomeA = BigDecimal.ZERO;
        BigDecimal incomeS = BigDecimal.ZERO;
        int isStage=0;
        if("null".equals(dailyDto.getPriceDate())){
            dailyDto.setPriceDate(Utils.beforeDay());
        }

        /*****************************************CPA价格**************************************************/
        //查询cpa价格 查询价格在这个时间段的价格，如此时间段无价格则采用默认价格
        String cpasql = "select * from cw_product_cooperative_price where product_id="+dailyDto.getProductId()+" " +
                "        and "+dailyDto.getApplyNuma()+" BETWEEN start_num and end_num and cooperation_model='CPA'  " +
                "        and STR_TO_DATE('" + dailyDto.getPriceDate() + "','%Y-%m-%d') BETWEEN start_cycle and IFNUll(end_cycle,'2099-12-31')";
        List<ProductCooperativePriceDto> marketDailyDtoList = jdbcTemplate.query(cpasql, new BeanPropertyRowMapper(ProductCooperativePriceDto.class));
        if(marketDailyDtoList.size()>0){
            priceA = marketDailyDtoList.get(0).getPrice();
            incomeA = new BigDecimal(dailyDto.getApplyNuma()).multiply(priceA);
            flag = Boolean.TRUE;
            isStage = 1;
        }
        //查询最新价格
        String cpasqls = "select * from cw_product_cooperative_price where product_id="+dailyDto.getProductId()+" " +
                "        and "+dailyDto.getApplyNuma()+" BETWEEN start_num and end_num and cooperation_model='CPA' " +
                "        and start_cycle is null and end_cycle is null ";
        List<ProductCooperativePriceDto> marketDailyDtoLists = jdbcTemplate.query(cpasqls, new BeanPropertyRowMapper(ProductCooperativePriceDto.class));
        if(marketDailyDtoLists.size()>0&&isStage==0)
        {
            priceA = marketDailyDtoLists.get(0).getPrice();
            incomeA = new BigDecimal(dailyDto.getApplyNuma()).multiply(priceA);
            flag = Boolean.TRUE;
        }

        /*************************************cps价格结算************************************/
        isStage=0;
        //查询cps价格
        String cpssql = "select * from cw_product_cooperative_price where product_id="+dailyDto.getProductId()+" " +
                "        and "+dailyDto.getApplyNums()+" BETWEEN start_num and end_num and cooperation_model='CPS'  " +
                "        and STR_TO_DATE('" + dailyDto.getPriceDate() + "','%Y-%m-%d') BETWEEN start_cycle and IFNUll(end_cycle,'2099-12-31') ";
        List<ProductCooperativePriceDto> cooperativePriceDtoList = jdbcTemplate.query(cpssql, new BeanPropertyRowMapper(ProductCooperativePriceDto.class));
        if(cooperativePriceDtoList.size()>0)
        {
            priceS = cooperativePriceDtoList.get(0).getPrice();
            String priceType = cooperativePriceDtoList.get(0).getPriceType();
            //cps按照固定价格算收入
            if("fix".equals(priceType)){
                incomeS = new BigDecimal(dailyDto.getApplyNums()).multiply(priceS);
            }else{
                incomeS = dailyDto.getLoanAmount().multiply(priceS).divide(new BigDecimal(100));
            }
            flag = Boolean.TRUE;
            isStage=1;
        }

        //查询cps价格 默认配置的价格执行
        String cpssqls = "select * from cw_product_cooperative_price where product_id="+dailyDto.getProductId()+" " +
                "        and "+dailyDto.getApplyNums()+" BETWEEN start_num and end_num and cooperation_model='CPS' " +
                "        and start_cycle is null and end_cycle is null  ";
        List<ProductCooperativePriceDto> cooperativePriceDtoLists = jdbcTemplate.query(cpssqls, new BeanPropertyRowMapper(ProductCooperativePriceDto.class));
        if(cooperativePriceDtoLists.size()>0&&isStage==0)
        {
            priceS = cooperativePriceDtoLists.get(0).getPrice();
            String priceType = cooperativePriceDtoLists.get(0).getPriceType();
            //cps按照固定价格算收入
            if("fix".equals(priceType)){
                incomeS = new BigDecimal(dailyDto.getApplyNums()).multiply(priceS);
            }else{
                incomeS = dailyDto.getLoanAmount().multiply(priceS).divide(new BigDecimal(100));
            }
            flag = Boolean.TRUE;
        }

        if(!flag) {
            CwException.throwIt("请配置产品价格");
        }
        String sql = " update cw_report_market_daily" +
                "    set " ;
        if(dailyDto.getApplyNuma()!=null&&dailyDto.getApplyNuma()>0) {
            sql += "  apply_numa=" + dailyDto.getApplyNuma() + "," ;
        }
        if(dailyDto.getApplyNums()!=null&&dailyDto.getApplyNums()>0) {
            sql += "  apply_nums=" + dailyDto.getApplyNums() + ",";
        }
        if(dailyDto.getLoanAmount()!=null&&dailyDto.getLoanAmount().compareTo(BigDecimal.ZERO)>0) {
            sql += " loan_amount=" + dailyDto.getLoanAmount() + ",";
        }
         sql+=  "   price_a =" + priceA + "," +
                "        income_a =" + incomeA + "," +
                "        price_s =" + priceS + "," +
                "        income_s =" + incomeS + "," +
                "        total_income =" + incomeS.add(incomeA);

        sql += " where 1=1 and product_id=" + dailyDto.getId();
        if (dailyDto.getApplyStartDate() == null) {
            sql += "  and day_id=date_format(date_sub(curdate(),interval 1 day),'%Y-%m-%d')";
        } else {
            sql += "  and day_id=date_format(STR_TO_DATE('" + dailyDto.getApplyStartDate() + "','%Y-%m-%d'),'%Y-%m-%d')";
        }
        return jdbcTemplate.update(sql);
    }

    /**
     * 明细回盘数据
     * @param dailyDto
     * @return
     */
    public int updateMerchantIncome(MarketDailyDto dailyDto){
        Boolean flag=Boolean.FALSE;

        BigDecimal priceA = BigDecimal.ZERO;
        BigDecimal priceS = BigDecimal.ZERO;
        BigDecimal incomeA = BigDecimal.ZERO;
        BigDecimal incomeS = BigDecimal.ZERO;
        int isStage=0;
        if("null".equals(dailyDto.getPriceDate())){
            dailyDto.setPriceDate(Utils.beforeDay());
        }
        Integer applyNuma=0;
        Integer applyNums=0;
        //数据初始化处理
        if(dailyDto.getApplyNuma()!=null){
            applyNuma = dailyDto.getApplyNuma();
        }
        if(dailyDto.getApplyNums()!=null){
            applyNums = dailyDto.getApplyNums();
        }

        //测试
        //dailyDto.setPriceDate(dailyDto.getDayId());
        /*****************************************CPA价格**************************************************/
        if(dailyDto.getApplyNuma()!=null) {
            //查询cpa价格 查询价格在这个时间段的价格，如此时间段无价格则采用默认价格
            String cpaSql = "select * from cw_product_cooperative_price where product_id=" + dailyDto.getProductId() + " " +
                    "        and " + applyNuma + " BETWEEN start_num and end_num and cooperation_model='CPA' " +
                    "        and '" + dailyDto.getPriceDate() + "' BETWEEN start_cycle and IFNUll(end_cycle,'2099-12-31') ";
            List<ProductCooperativePriceDto> marketDailyDtoList = jdbcTemplate.query(cpaSql, new BeanPropertyRowMapper(ProductCooperativePriceDto.class));
            if (marketDailyDtoList.size() > 0) {
                priceA = marketDailyDtoList.get(0).getPrice();
                incomeA = new BigDecimal(dailyDto.getApplyNuma()).multiply(priceA);
                flag = Boolean.TRUE;
                isStage = 1;
            }
            //查询最新价格
            String cpaSqls = "select * from cw_product_cooperative_price where product_id=" + dailyDto.getProductId() + " " +
                    "        and " + applyNuma + " BETWEEN start_num and end_num and cooperation_model='CPA'" +
                    "        and start_cycle is null and end_cycle is null ";
            List<ProductCooperativePriceDto> marketDailyDtoLists = jdbcTemplate.query(cpaSqls, new BeanPropertyRowMapper(ProductCooperativePriceDto.class));
            if (marketDailyDtoLists.size() > 0 && isStage == 0) {
                priceA = marketDailyDtoLists.get(0).getPrice();
                incomeA = new BigDecimal(dailyDto.getApplyNuma()).multiply(priceA);
                flag = Boolean.TRUE;
                if (incomeA.compareTo(BigDecimal.ZERO) == 0) {
                    priceA = BigDecimal.ZERO;
                }
            }
            //录入错误修正数据处理
            if(dailyDto.getApplyNuma()==0){
                flag = Boolean.TRUE;
            }
        }
        /*************************************cps价格结算************************************/
        isStage=0;
        //查询cps价格

        if(dailyDto.getApplyNums()!=null) {
            String cpsSql = "select * from cw_product_cooperative_price where product_id="+dailyDto.getProductId()+" " +
                    "        and "+applyNums+" BETWEEN start_num and end_num and cooperation_model='CPS' " +
                    "        and  '"+dailyDto.getPriceDate()+"' BETWEEN start_cycle and IFNUll(end_cycle,'2099-12-31') ";
            List<ProductCooperativePriceDto> cooperativePriceDtoList = jdbcTemplate.query(cpsSql, new BeanPropertyRowMapper(ProductCooperativePriceDto.class));
            if(cooperativePriceDtoList.size()>0)
            {
                priceS = cooperativePriceDtoList.get(0).getPrice();
                String priceType = cooperativePriceDtoList.get(0).getPriceType();
                if("fix".equals(priceType)){
                    incomeS = new BigDecimal(dailyDto.getApplyNums()).multiply(priceS);
                }else{
                    incomeS = dailyDto.getLoanAmount().multiply(priceS).divide(new BigDecimal(100));
                }
                isStage=1;
                flag = Boolean.TRUE;
            }

        //查询cps价格 默认配置的价格执行
            String cpsSqls = "select * from cw_product_cooperative_price where product_id=" + dailyDto.getProductId() + " " +
                    "        and " + applyNums + " BETWEEN start_num and end_num and cooperation_model='CPS' " +
                    "        and start_cycle is null and end_cycle is null  ";
            List<ProductCooperativePriceDto> cooperativePriceDtoLists = jdbcTemplate.query(cpsSqls, new BeanPropertyRowMapper(ProductCooperativePriceDto.class));
            if (cooperativePriceDtoLists.size() > 0 && isStage == 0) {
                priceS = cooperativePriceDtoLists.get(0).getPrice();
                String priceType = cooperativePriceDtoLists.get(0).getPriceType();
                //cps按照固定价格算收入
                if ("fix".equals(priceType)) {
                    incomeS = new BigDecimal(dailyDto.getApplyNums()).multiply(priceS);
                } else {
                    incomeS = dailyDto.getLoanAmount().multiply(priceS).divide(new BigDecimal(100));
                }
                if (incomeS.compareTo(BigDecimal.ZERO) == 0) {
                    priceS = BigDecimal.ZERO;
                }
                flag = Boolean.TRUE;
            }
            //录入错误修正数据处理
            if(dailyDto.getApplyNums()==0||dailyDto.getLoanAmount().compareTo(BigDecimal.ZERO)==0){
                flag = Boolean.TRUE;
            }
        }

        if(!flag) {
            CwException.throwIt("请配置产品价格");
        }
        String sql=" update cw_report_market_daily " +
                "    set  " ;
            Boolean isIncomeA=Boolean.FALSE;
            if(dailyDto.getApplyNuma()!=null) {
                sql += "  apply_numa=" + dailyDto.getApplyNuma() + ", " +
                       "  price_a ="+priceA+"," +
                       "  income_a ="+incomeA+","+
                       "  settle_channel ='"+dailyDto.getSettleChannel()+"',"+
                       "  settle_memo ='"+dailyDto.getSettleMemo()+"',"+
                       "  total_income=IFNULL(income_s,0)+"+incomeA;
                isIncomeA = Boolean.TRUE;
            }
            if(dailyDto.getLoanAmount()!=null&&dailyDto.getApplyNums()!=null) {
                if(isIncomeA){
                    sql+=",";
                }
                sql += " apply_nums=" + dailyDto.getApplyNums() + ",";
                sql += " loan_amount=" + dailyDto.getLoanAmount() + ","+
                       " price_s ="+priceS + ","+
                       " income_s ="+incomeS +"," +
                       " settle_channel = '"+dailyDto.getSettleChannel()+"',"+
                       " settle_memo = '"+dailyDto.getSettleMemo()+"'";
                       if(isIncomeA){
                           sql += " ,total_income="+(incomeA.add(incomeS));
                       }else{
                           sql += " ,total_income =IFNULL(income_a,0)+"+incomeS;
                       }
            }
            sql +=" where 1=1 and id="+dailyDto.getId();
        return jdbcTemplate.update(sql);
    }

    /**
     * 月结修改状态
     * @param dailyDto
     * @return
     */
    public int updateStatus(MarketDailyDto dailyDto){
        if((dailyDto.getSettleDate()==null||"".equals(dailyDto.getSettleDate()))&&dailyDto.getStatus()==1){
            CwException.throwIt("账期不能为空！");
        }
        //复核默认是上一月，如果选择了月份则默认是选择的月份
        if((dailyDto.getSettleDate()==null||"".equals(dailyDto.getSettleDate()))&&dailyDto.getStatus()>1){
            dailyDto.setSettleDate(Utils.convertNowBeforeDate(new Date()).substring(0,7));
        }
        String settleNo = Utils.convertDate(new Date()).replaceAll("-","")+Utils.randomStr();
//        //检查同一开票单位下是否有已复核的产品如有则用原来的结算单号
//        if(2==dailyDto.getStatus()){
//            String settleSql="select max(settle_no) as settleNo from cw_report_market_daily where day_id like '"+dailyDto.getSettleDate()+"%' \n" +
//                    "and product_id in (select id from cw_product where account_name in (select account_name from cw_product a where id="+dailyDto.getProductId()+"))";
//            List<MarketDailyDto> marketDailyDtoList = jdbcTemplate.query(settleSql, new BeanPropertyRowMapper(MarketDailyDto.class));
//            if(marketDailyDtoList.size()>0){
//               String settleNoOld = marketDailyDtoList.get(0).getSettleNo();
//               if(settleNoOld!=null&&!"".equals(settleNoOld)){
//                   settleNo = settleNoOld;
//               }
//            }
//        }

        String condition="";
        //根据操作状态来修改处于当前阶段的任务
        if(dailyDto.getStatus()==1){//确认回盘
            condition +="  and status=0 " ;
        }else if(dailyDto.getStatus()==2){//复核
            condition +="  and status=1 " ; //复核后生产结算单号
        }else if(dailyDto.getStatus()==3){//开票
            condition +="  and status=2 " ;
        }else if(dailyDto.getStatus()==4){//邮寄
            condition +="  and status=3 " ;
        }else if(dailyDto.getStatus()==5){//确认回款(已邮寄，不开票,日结产品)
            condition +="  and (status =4 or (status=2 and is_billing=6)) " ;
        }
        //修改回盘数据
        String sql=" update cw_report_market_daily " ;
           sql+=    " set raw_version=1 ";
           if(dailyDto.getStatus()!=6){
             sql+="  ,status="+dailyDto.getStatus();
           }
           if(dailyDto.getStatus()==1){
               sql+=",is_billing="+dailyDto.getIsBilling();
               sql+=",confirm_settle_fee="+dailyDto.getConfirmSettleFee();
           }
            //记录开票状态
           if(dailyDto.getStatus()==3||dailyDto.getStatus()==6){
               sql+=",is_billing="+dailyDto.getStatus();
           }
           if(dailyDto.getStatus()==2){//复核过后生成结算单号
               sql+=",settle_no = (case when settle_no is null then '"+settleNo+"' else settle_no end)";
           }
           sql +=" where 1=1 "+condition ;
           sql+= " and substr(day_id,1,7) = '"+dailyDto.getSettleDate()+"' " +
                 " and ( product_id = "+dailyDto.getProductId()+" or settle_no ='"+dailyDto.getProductId()+"') ";
           int result=jdbcTemplate.update(sql);

           //修改回款数据
            if(dailyDto.getStatus()==5){
                sql="    update cw_report_market_daily a " +
                    " inner join (select product_id,max(day_id) as day_id from cw_report_market_daily " +
                    "             where 1=1 " +
                    "               and substr(day_id,1,7) = '"+dailyDto.getSettleDate()+"' " +
                    "               and ( product_id = "+dailyDto.getProductId()+" or settle_no ='"+dailyDto.getProductId()+"') "+
                    "           ) b on a.day_id = b.day_id ";
                sql+=" set income_fee="+dailyDto.getIncomeFee();
                sql+="    ,income_memo='"+dailyDto.getIncomeMemo()+"'";
                sql+="    ,back_fee_date = now() ";
                sql+= " where 1=1 and substr(a.day_id,1,7) = '"+dailyDto.getSettleDate()+"' " +
                        " and ( a.product_id = "+dailyDto.getProductId()+" or a.settle_no ='"+dailyDto.getProductId()+"') ";
                jdbcTemplate.update(sql);
            }


       return result;
    }

    /**
     * 修改快递信息
     * @param dailyDto
     * @return
     */
    public int postInfoUpdate(MarketDailyDto dailyDto){

        if(dailyDto.getStatus()==3){
            dailyDto.setStatus(4);
        }
        if(dailyDto.getStatus()==7){
            dailyDto.setStatus(8);
        }
        String sql=" update cw_report_market_daily " +
                "       set express ='"+dailyDto.getExpress()+"',"+
                "            post_no='"+dailyDto.getPostNo()+"',"+
                "            post_date='"+dailyDto.getPostDate()+"'," +
                "            status = "+dailyDto.getStatus()+
                "   where 1=1 " +
                "     and substr(day_id,1,7) = '"+dailyDto.getSettleDate()+"'" +
                "     and ( product_id = "+dailyDto.getProductId()+" or settle_no ='"+dailyDto.getProductId()+"') ";
        return jdbcTemplate.update(sql);
    }
    /**
     * 查询结算单信息
     * @param dto
     * @return
     */
    public SettleDto getSettleInfo(ReportSearchDto dto){

        if("".equals(dto.getApplyStartDate())){
            dto.setApplyStartDate(Utils.convertNowBeforeDate(new Date()).substring(0,7));
        }
        String sql = "select aaa.*,bbb.price_type as priceTypeA,ccc.price_type as priceTypeS\n" +
                "  from (select aa.*,\n" +
                "       b.account_name as accountName,\n" +
                "       b.bank_account as bankAccount,\n" +
                "       b.open_bank_name as openBankName,\n" +
                "       b.link_address as linkAddress,\n" +
                "       b.invoice_item as invoiceItem,\n" +
                "       b.invoice_type as invoiceType,\n" +
                "       b.taxes_object as taxesObject,\n" +
                "       b.taxpayer_no as taxpayerNo,\n" +
                "       b.invoice_link_address as invoiceLinkAddress,\n" +
                "       b.invoice_link_phone as invoiceLinkPhone,\n" +
                "       b.link_phone as linkPhone," +
                "       b.invoice_memo as invoiceMemo\n" +
                " from (\n" +
                " select a.product_id,a.cooperation_model,a.enterprise_name,a.product_name,min(a.day_id) as settleStartDate,max(a.day_id) as settleEndDate,\n" +
                "      max(a.price_a) priceA,max(a.price_s) priceS,sum(total_income) as totalIncome,sum(apply_numa) as applyNuma,\n" +
                "      sum(loan_amount) as loanAmount,sum(apply_nums) as applyNums\n" +
                " from cw_report_market_daily  a \n" +
                " where SUBSTR(day_id,1,7)='"+dto.getApplyStartDate()+"'\n" +
                "   and product_id="+dto.getProductId()+"\n" +
                " ) aa INNER JOIN cw_product b on aa.product_id = b.id\n" +
                " ) aaa \n" +
                " LEFT JOIN (select * from cw_product_cooperative_price where product_id="+dto.getProductId()+" and cooperation_model='CPA') bbb on aaa.pricea = bbb.price  \n" +
                " LEFT JOIN (select * from cw_product_cooperative_price where product_id="+dto.getProductId()+" and cooperation_model='CPS') ccc on aaa.prices = ccc.price";

        List<SettleDto> settleDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SettleDto.class));
        if(settleDtoList.size()>0){
            return settleDtoList.get(0);
        }
        return new SettleDto();
    }

    /**
     * 产品关联查询统计
     * @param dto
     * @return
     */
    public List<SettleDto> getSettleRelation(ReportSearchDto dto){

        String sql = "select b.id as productId,a.settle_no as settleNo,b.account_name as accountName,b.name as productName,SUBSTR(day_id,1,7) as acctMonth," +
                "             sum(a.total_income) as totalIncome \n" +
                "  from cw_report_market_daily a \n" +
                " INNER JOIN cw_product b on a.product_id=b.id \n" +
                " where 1=1 and status=1 \n" +
                "   and b.account_name in (select account_name from cw_product where id="+dto.getProductId()+"" ;
                sql+=" )  \n ";
        if(!"income".equals(dto.getSysType())) {
            sql += "   and a.product_id !=" + dto.getProductId() + " \n ";
        }
        if(dto.getProductName()!=null&&!"".equals(dto.getProductName())){
                sql+=" and name like '%"+dto.getProductName()+"%'";
            }
            if(dto.getEnterpriseName()!=null&&!"".equals(dto.getEnterpriseName())){
                sql+=" and account_name like '%"+dto.getEnterpriseName()+"%'";
            }
               sql+= "group by b.id,a.settle_no,b.account_name,b.name,SUBSTR(day_id,1,7)";

        List<SettleDto> settleDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SettleDto.class));

        return settleDtoList;
    }

    /**
     * 收入结算详情
     * @param dto
     * @return
     */
    public List<SettleDto> getSettleIncomeDetail(ReportSearchDto dto){

        String sql = "select b.id as productId,a.settle_no as settleNo,b.account_name as accountName,b.name as productName," +
                "             SUBSTR(day_id,1,7) as acctMonth," +
                "                      b.invoice_item as invoiceItem, " +
                "                      b.invoice_type as invoiceType, " +
                "             sum(a.total_income) as totalIncome \n" +
                "       from cw_report_market_daily a \n" +
                " INNER JOIN cw_product b on a.product_id=b.id \n" +
                "      where 1=1 \n" +
                "       and a.settle_no = '"+dto.getProductId()+"' " ;
        sql+= "group by b.id,a.settle_no,b.account_name,b.name,SUBSTR(day_id,1,7)";

        List<SettleDto> settleDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SettleDto.class));

        return settleDtoList;
    }

    /**
     * 根据关联操作联系结算单据
     */
    public int updateIncomeSettle(SettleDto settleDto){
        String relationAcctMonth = settleDto.getSettleAcctMonth();
        if(settleDto.getAcctMonth()==null){
            settleDto.setAcctMonth(Utils.convertNowBeforeDate(new Date()).substring(0,7));
        }
        String dyncSql="";
        if(!StringUtils.isEmpty(relationAcctMonth)){
            String[] relatinoAry = relationAcctMonth.split(",");
            int index=0;
            for(String relation:relatinoAry){
                String[] keyValue = relation.split("\\|");
                if(keyValue.length==2){
                    if(index<(relatinoAry.length)){
                        dyncSql += " or ";
                    }
                    dyncSql += " ( product_id = "+keyValue[0];
                    dyncSql += " and day_id like '"+keyValue[1]+"%' ) ";
                }
                index++;
            }
        }
        //结算单号
        String settleNo = Utils.convertDate(new Date()).replaceAll("-","")+Utils.randomStr();
        String sql="update cw_report_market_daily set settle_no='"+settleNo+"'" +
                "    where status=1 and ( ( product_id ="+settleDto.getProductId()+" and day_id like '"+settleDto.getAcctMonth()+"%' ) \n" +
                "   " +dyncSql+
                " ) ";
        return jdbcTemplate.update(sql);
    }

    /**
     * 取消产品关联
     * @return
     */
    public int cancelIncomeSettle(SettleDto settleDto){
        String relationAcctMonth = settleDto.getSettleAcctMonth();
        if(settleDto.getAcctMonth()==null){
            settleDto.setAcctMonth(Utils.convertNowBeforeDate(new Date()).substring(0,7));
        }
        String condition ="";
        if(!StringUtils.isEmpty(relationAcctMonth)){
            String[] relatinoAry = relationAcctMonth.split(",");
            int index=0;
            for(String relation:relatinoAry){
                String[] keyValue = relation.split("\\|");
                if(keyValue.length==2){
                    condition += " ( product_id = "+keyValue[0];
                    condition += " and day_id like '"+keyValue[1]+"%' ) ";
                    if(index<(relatinoAry.length-1)){
                        condition += " or ";
                    }
                }
                index++;
            }
        }
        //取消结算关联
        String sql=" update cw_report_market_daily set settle_no=null " +
                "     where 1=1 and ( \n" +
                "   " +condition+
                " ) ";
        return jdbcTemplate.update(sql);
    }

    /**
     * 查询商户上线以来收入回款情况
     * @param dto
     * @return
     */
    public List<MarketDailyDto> getAccountIncomeStatus(ReportSearchDto dto){
        String sql = "select b.name as productName,substr(a.day_id,1,7) as dayId,status," +
                "             IFNULL(income_fee,0) as incomeFee,\n" +
                "             IFNULL(sum(total_income),0) as totalIncome " +
                "       from cw_report_market_daily a INNER JOIN cw_product b on a.product_id = b.id\n" +
                "      where b.account_name in ( select account_name from cw_product where id in (" +
                "        select max(product_id) from cw_report_market_daily where settle_no ='" +dto.getSettleNo()+"'))"+
                "   group by b.name,substr(day_id,1,7),a.status,IFNULL(income_fee,0) ";

        List<MarketDailyDto> settleDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(MarketDailyDto.class));
        return settleDtoList;
    }
}

