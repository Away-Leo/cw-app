package com.cw.biz.home.domain.dao;

import com.cw.biz.CPContext;
import com.cw.biz.home.app.dto.*;
import com.cw.biz.product.app.dto.ProductListDto;
import com.cw.biz.product.app.dto.ProductSearchDto;
import com.cw.biz.user.app.dto.CwUserInfoDto;
import com.cw.core.common.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Administrator on 2017/6/26.
 */
@Repository
public class HomeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 用户发展统计数据
     *
     * @return
     */
    public HomeDto countUser(String applyDate) {

        if (applyDate == null || "".equals(applyDate)) {
            applyDate = Utils.convertDate(new Date());
        }

        String applyNumSql = "select IFNULL(count(1),0) as applyTime " +
                " from (select user_id,channel_no " +
                "  FROM cw_log a " +
                " where type=2 " +
                "   and DATE_FORMAT(apply_date,'%Y-%m-%d')=DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') " +
                "   and channel_no in (select code from cw_channel union all select code from cw_app_market) " +
                "                      and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                "   and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') " +
                " group by user_id,channel_no " +
                " ) aa  ";
        List<HomeDto> applyNumList = jdbcTemplate.query(applyNumSql, new BeanPropertyRowMapper(HomeDto.class));
        int applyNum = applyNumList.get(0).getApplyTime();

        String marketSql = "(select IFNULL(count(1),0) as applyTime " +
                " from (select user_id,channel_no " +
                "  FROM cw_log a " +
                " where type=2 " +
                "   and DATE_FORMAT(apply_date,'%Y-%m-%d')=DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') " +
                "   and channel_no in (select code from cw_app_market) " +
                "   and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') " +
                "   and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                " group by user_id,channel_no " +
                " ) aa ) ";
        List<HomeDto> homeDto = jdbcTemplate.query(marketSql, new BeanPropertyRowMapper(HomeDto.class));
        int marketApplyTime = homeDto.get(0).getApplyTime();

        String channelSql = "(select IFNULL(count(1),0) as applyTime " +
                " from (select user_id,channel_no " +
                "  FROM cw_log a " +
                " where type=2 " +
                "   and DATE_FORMAT(apply_date,'%Y-%m-%d')=DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') " +
                "   and channel_no in (select code from cw_channel) " +
                "   and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') " +
                "   and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                " group by user_id,channel_no " +
                " ) aa ) ";
        homeDto = jdbcTemplate.query(channelSql, new BeanPropertyRowMapper(HomeDto.class));
        int channelApplyTime = homeDto.get(0).getApplyTime();

        String applyMonthSql = "  (select IFNULL(count(1),0) as applyTime  " +
                "  from (select user_id,channel_no " +
                "  FROM cw_log a " +
                " where type=2  " +
                "   and DATE_FORMAT(apply_date,'%Y-%m')=DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m') " +
                "   and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') " +
                "   and channel_no in (select code from cw_channel union all select code from cw_app_market) " +
                "                      and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                " group by user_id,channel_no " +
                " ) aa ) ";
        homeDto = jdbcTemplate.query(applyMonthSql, new BeanPropertyRowMapper(HomeDto.class));
        int applyTimes = homeDto.get(0).getApplyTime();

        String marketMonthSql = "  (select IFNULL(count(1),0) as applyTime  " +
                "  from (select user_id,channel_no " +
                "  FROM cw_log a " +
                " where type=2  " +
                "   and DATE_FORMAT(apply_date,'%Y-%m')=DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m') " +
                "   and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') " +
                "   and channel_no in (select code from cw_app_market) " +
                "                      and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                " group by user_id,channel_no " +
                " ) aa ) ";
        homeDto = jdbcTemplate.query(marketMonthSql, new BeanPropertyRowMapper(HomeDto.class));
        int monthApplyTime = homeDto.get(0).getApplyTime();

        String channelMonthSql = "  (select IFNULL(count(1),0) as applyTime  " +
                "  from (select user_id,channel_no " +
                "  FROM cw_log a " +
                " where type=2  " +
                "   and DATE_FORMAT(apply_date,'%Y-%m')=DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m') " +
                "   and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') " +
                "   and channel_no in (select code from cw_channel) " +
                "   and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                " group by user_id,channel_no " +
                " ) aa ) ";
        homeDto = jdbcTemplate.query(channelMonthSql, new BeanPropertyRowMapper(HomeDto.class));
        int channelMonthTime = homeDto.get(0).getApplyTime();

        String sql = " select IFNULL(sum(case when apply_date = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') and type=0 then total else 0 end),0) as dayUserCount, " +
                "        IFNULL(sum(case when channel_no in (select code from cw_app_market) and apply_date = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') " +
                "           and type=0 then total else 0 end),0) as appMarketDayNum, " +
                "        IFNULL(sum(case when channel_no in (select code from cw_channel) and apply_date = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') " +
                "           and type=0 then total else 0 end),0) as channelDayNum, " +

                "        IFNULL(sum(case when type=0 then total else 0 end),0) as monthUserCount, " +
                "        IFNULL(sum(case when channel_no in (select code from cw_app_market) and type=0 then total else 0 end),0) as appMarketMonthNum, " +
                "        IFNULL(sum(case when channel_no in (select code from cw_channel) and type=0 then total else 0 end),0) as channelMonthNum, " +

                "        IFNULL(sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and apply_date = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') " +
                "           and type=2 then total else 0 end),0) as dayApplyCount," +
                "        IFNULL(sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and channel_no in (select code from cw_app_market) " +
                "           and apply_date = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') and type=2 then total else 0 end),0) as appMarketDayApplyTime," +
                "        IFNULL(sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and channel_no in (select code from cw_channel) " +
                "           and apply_date = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') and type=2 then total else 0 end),0) as channelDayApplyNum," +
                "        " + applyNum + " as applyNum," +
                "        " + marketApplyTime + " as appMarketApplyNum," +
                "        " + channelApplyTime + " as channelApplyNum," +

                "        " + applyTimes + " as monthApplyNum," +
                "        " + monthApplyTime + " as appMarketMonthApplyNum," +
                "        " + channelMonthTime + " as channelMonthApplyNum," +

                "        IFNULL(sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and type=2 then total else 0 end),0) as monthApplyCount, " +
                "        IFNULL(sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and channel_no in (select code from cw_app_market) and type=2 then total else 0 end),0) as appMarketMonthApplyTime, " +
                "        IFNULL(sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and channel_no in (select code from cw_channel) and type=2 then total else 0 end),0) as channelApplyTime " +
                " from (select DATE_FORMAT(a.apply_date,'%Y-%m-%d') as apply_date,type,channel_no,product_id,count(1) as total" +
                "         from cw_log a \n" +
                "        where channel_no in (select code from cw_channel union all select code from cw_app_market) \n" +
                "          and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                "          and DATE_FORMAT(a.apply_date,'%Y-%m') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m'),'%Y-%m')" +
                "     group by DATE_FORMAT(a.apply_date,'%Y-%m-%d'),type,channel_no,product_id" +
                " ) a ";

        List<HomeDto> homeDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));
        if (homeDtoList.size() == 0) {
            return null;
        }
        HomeDto result = homeDtoList.get(0);
        //统计分发系数
        HomeDto day = countUserRatio(applyDate,0);
        if(day.getDayUserCount()==0){
            result.setDayRatio(BigDecimal.ZERO);
        }else {
            BigDecimal ratio = new BigDecimal(day.getApplyNum()).divide(new BigDecimal(day.getDayUserCount()), 2,BigDecimal.ROUND_HALF_UP);
            result.setDayRatio(ratio);
        }
        HomeDto month = countUserRatio(applyDate,1);
        if(month.getDayUserCount()==0){
            result.setMonthRatio(BigDecimal.ZERO);
        }else {
            result.setMonthRatio(new BigDecimal(month.getApplyNum()).divide(new BigDecimal(month.getDayUserCount()), 2,BigDecimal.ROUND_HALF_UP));
        }
        return result;
    }

    private HomeDto countUserRatio(String applyDate,Integer type){
        String midSql="";
        if(applyDate!=null&&!"".equals(applyDate)){
            if(0==type) {
                midSql += " and  DATE_FORMAT(apply_date,'%Y-%m-%d')='"+applyDate+"'\n";
            }else {
                midSql += " and  DATE_FORMAT(apply_date,'%y-%m')=DATE_FORMAT(date_format('"+applyDate+"','%Y-%m-%d'),'%y-%m') " +
                        "   and  DATE_FORMAT(apply_date,'%y-%m-%d') <= date_format('"+applyDate+"','%Y-%m-%d') \n";
            }
        }else{
            if(0==type) {
                midSql += " and DATE_FORMAT(apply_date,'%y-%m-%d')= date_format(now(),'%y-%m-%d') \n";
            }else {
                midSql += " and DATE_FORMAT(apply_date,'%y-%m')= DATE_FORMAT(now(),'%y-%m') " +
                          " and DATE_FORMAT(apply_date,'%y-%m-%d') < DATE_FORMAT(now(),'%y-%m-%d')  \n";
            }
        }

        String sql="select sum(dayDev) as dayUserCount, \n" +
                "       sum(applyNum) as applyNum   \n" +
                " from (\n" +
                " select sum(case when type=0 then 1 else 0 end) as dayDev,\n" +
                "       0 applyNum\n" +
                "  from cw_log " +
                " where 1=1 " +
                "   and channel_no in (select code from cw_channel union all select code from cw_app_market) \n" +
                "   and user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null)\n"+midSql ;
        sql +=   "   union ALL\n" +
                "select 0 dayDev,\n" +
                "       sum(num) as applyNum\n" +
                " from (\n" +
                "\tselect product_id,DATE_FORMAT(apply_date,'%y-%m-%d'),\n" +
                "\t\t\t\t count(distinct user_id) as num\n" +
                "\tfrom cw_log a INNER JOIN cw_app_market b on a.channel_no = b.code     \n" +
                "\t\t\tINNER JOIN (select id from cw_product where channel!='重庆平讯数据服务有限公司' ) c on a.product_id = c.id \n" +
                "\t\t\tINNER JOIN (select id from pf_se_user where type='user' and register_date is not null) d on a.user_id = d.id    \n" +
                "\twhere 1=1 \n" +
                "\t and type=2       \n" + midSql ;
        sql += "\tgroup by product_id,DATE_FORMAT(apply_date,'%y-%m-%d')) a\n" +
                ")aa \n" +
                "\n";
        List<HomeDto> homeDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));
        if (homeDtoList.size() == 0) {
            return null;
        }

        return homeDtoList.get(0);
    }

    /**
     * 各APP用户发展情况分布
     *
     * @return
     */
    public List<HomeDto> appDev(String applyDate) {
        String sql = "select * from (";
        if (applyDate != null && !"".equals(applyDate)) {
            sql += "select dd.code ," +
                    "      dd.name as appName,\n" +
                    "      sum(dayUserCount) dayUserCount,\n" +
                    "      sum(monthUserCount) monthUserCount,\n" +
                    "      sum(dayApplyCount) dayApplyCount,\n" +
                    "      sum(monthApplyCount) monthApplyCount " +
                    " from " +
                    " (select (case when app_name in ('DKB','XSD') then 'XSD' else app_name end) app_name,\n" +
                    "       sum(case when DATE_FORMAT(a.apply_date,'%Y-%m-%d') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') and type=0 then 1 else 0 end) as dayUserCount," +
                    "       sum(case when DATE_FORMAT(a.apply_date,'%Y-%m') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m'),'%Y-%m') and type=0 then 1 else 0 end) as monthUserCount, " +
                    "       sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and DATE_FORMAT(a.apply_date,'%Y-%m-%d') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%Y-%m-%d') and type=2 then 1 else 0 end) as dayApplyCount," +
                    "       sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and DATE_FORMAT(a.apply_date,'%Y-%m') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m'),'%Y-%m') and type=2 then 1 else 0 end) as monthApplyCount " +
                    "  from cw_log a \n" +
                    " where channel_no in (select code from cw_channel union all select code from cw_app_market) \n" +
                    "   and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null)\n" +
                    "   group by (case when app_name in ('DKB','XSD') then 'XSD' else app_name end)\n" +
                    ") cc inner join cw_app dd on cc.app_name = dd.code " +
                    "group by dd.code,dd.name ";
        } else {
            sql += "select dd.code ," +
                    "             dd.name as appName,\n" +
                    "       sum(dayUserCount) dayUserCount,\n" +
                    "       sum(monthUserCount) monthUserCount,\n" +
                    "       sum(dayApplyCount) dayApplyCount,\n" +
                    "       sum(monthApplyCount) monthApplyCount " +
                    " from " +
                    " (select (case when app_name in ('DKB','XSD') then 'XSD' else app_name end) app_name,\n" +
                    "       sum(case when DATE_FORMAT(a.apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') and type=0 then 1 else 0 end) as dayUserCount," +
                    "       sum(case when DATE_FORMAT(a.apply_date,'%Y-%m') = DATE_FORMAT(now(),'%Y-%m') and type=0 then 1 else 0 end) as monthUserCount, " +
                    "       sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and DATE_FORMAT(a.apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') and type=2 then 1 else 0 end) as dayApplyCount," +
                    "       sum(case when product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') and DATE_FORMAT(a.apply_date,'%Y-%m') = DATE_FORMAT(now(),'%Y-%m') and type=2 then 1 else 0 end) as monthApplyCount " +
                    "  from cw_log a \n" +
                    " where channel_no in (select code from cw_channel union all select code from cw_app_market) \n" +
                    "   and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null)\n" +
                    "   group by (case when app_name in ('DKB','XSD') then 'XSD' else app_name end)\n" +
                    " ) cc inner join cw_app dd on cc.app_name = dd.code " +
                    " group by dd.code,dd.name ";
        }
        sql+=" ) aa order by dayUserCount desc ";

        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));

        return homeDto;
    }

    /**
     * 统计应用市场新增、申请
     *
     * @return
     */
    public List<HomeDto> dayDevUser(ReportSearchDto reportSearchDto) {
        String sortFlag = "";
        if ("ASC".equals(reportSearchDto.getSortDesc().toUpperCase())) {
            sortFlag = "desc";
        } else {
            sortFlag = "asc";
        }
        String sql = " select aa.*,'" + sortFlag + "' as sortDesc from ( " +
                "  select a.id,\n" +
                "       a.name as channelNo,\n" +
                "       IFNULL(sum(b.registerNum),0) as registerNum, " +
                "       IFNULL(sum(ab.applyTime),0) as applyTime,  " +
                "       IFNULL(sum(ab.applyNum),0) as applyNum " +
                "  from cw_app_market a  " +
                //注册用户数
                " left join ( " +
                "   select channel_no,\n" +
                "          count(1) as registerNum \n" +
                "     from cw_log \n" +
                "    where type = 0 and channel_no in (select code from cw_app_market)  " +
                "      and  user_id in (select id from pf_se_user where type='user' and register_date is not null) ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and  DATE_FORMAT(apply_date,'%Y-%m-%d') =  DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        if (!"all".equals(reportSearchDto.getBelongApp()) && reportSearchDto.getBelongApp() != null && !"".equals(reportSearchDto.getBelongApp())) {
            if ("XSD".equals(reportSearchDto.getBelongApp())) {
                sql += " and app_name in ('XSD','DKB')";
            } else {
                sql += " and app_name = '" + reportSearchDto.getBelongApp() + "'";
            }
        }
        sql += "   group by channel_no\n" +
                ") b on a.code = b.channel_no \n" +
                //申请次数和用户数
                " left join ( " +
                "   select channel_no,\n" +
                "          count(1) as applyTime, \n" +
                "          COUNT(DISTINCT user_id) as applyNum  \n" +
                "     from cw_log \n" +
                "    where type = 2 and channel_no in (select code from cw_app_market)  " +
                "      and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司')   " +
                "      and  user_id in (select id from pf_se_user where type='user' and register_date is not null) ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and  DATE_FORMAT(apply_date,'%Y-%m-%d') =  DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        if (!"all".equals(reportSearchDto.getBelongApp()) && reportSearchDto.getBelongApp() != null && !"".equals(reportSearchDto.getBelongApp())) {
            if ("XSD".equals(reportSearchDto.getBelongApp())) {
                sql += " and app_name in ('XSD','DKB')";
            } else {
                sql += " and app_name = '" + reportSearchDto.getBelongApp() + "'";
            }
        }
        sql += "   group by channel_no\n" +
                ") ab on a.code = ab.channel_no \n" +

                " group by a.id,a.name\n";

        sql += " union all \n" +
                " select '999' id,\n" +
                "       '合计' as channelNo,\n" +
                "       IFNULL(sum(b.registerNum),0) as registerNum,\n" +
                "       IFNULL(sum(ab.applyTime),0) as applyTime, \n" +
                "       IFNULL(sum(ab.applyNum),0) as applyNum \n" +
                "  from cw_app_market a" +
                //注册用户数
                " LEFT JOIN (  " +
                "   select channel_no,\n" +
                "          count(1) as registerNum \n" +
                "     from cw_log \n" +
                "    where type=0 and channel_no in (select code from cw_app_market)  " +
                "      and  user_id in (select id from pf_se_user where type='user' and register_date is not null) ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and  DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and  DATE_FORMAT(apply_date,'%Y-%m-%d') =  DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        if (!"all".equals(reportSearchDto.getBelongApp()) && reportSearchDto.getBelongApp() != null && !"".equals(reportSearchDto.getBelongApp())) {
            if ("XSD".equals(reportSearchDto.getBelongApp())) {
                sql += " and app_name in ('XSD','DKB')";
            } else {
                sql += " and app_name = '" + reportSearchDto.getBelongApp() + "'";
            }
        }
        sql += "   group by channel_no " +
                ") b  on a.code = b.channel_no ";

        sql += " LEFT JOIN (  " +
                "   select channel_no,\n" +
                "          count(1) as applyTime, \n" +
                "          COUNT(DISTINCT user_id) as applyNum  \n" +
                "     from cw_log \n" +
                "    where type=2 and channel_no in (select code from cw_app_market)  " +
                "      and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司')   " +
                "      and user_id in (select id from pf_se_user where type='user' and register_date is not null) ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and  DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and  DATE_FORMAT(apply_date,'%Y-%m-%d') =  DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        if (!"all".equals(reportSearchDto.getBelongApp()) && reportSearchDto.getBelongApp() != null && !"".equals(reportSearchDto.getBelongApp())) {
            if ("XSD".equals(reportSearchDto.getBelongApp())) {
                sql += " and app_name in ('XSD','DKB')";
            } else {
                sql += " and app_name = '" + reportSearchDto.getBelongApp() + "'";
            }
        }
        sql += "   group by channel_no " +
                ") ab  on a.code = ab.channel_no ";

        sql += " ) aa ";

        if (!"".equals(reportSearchDto.getSortColumn())) {
            if ("ASC".equals(sortFlag.toUpperCase())) {
                sql += " order by " + reportSearchDto.getSortColumn() + " desc ";
            } else {
                sql += " order by " + reportSearchDto.getSortColumn() + " asc ";
            }
        }

        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));

        return homeDto;
    }

    /**
     * 统计应用市场新增，申请
     *
     * @return
     */
    public List<HomeDto> countChannelUser(String appName, String cycle, String applyDate) {
        String sql = "select a.name as channelNo,\n" +
                "       sum(case when b.type=0 then applyTime else 0 end) as registerNum,\n" +
                "       sum(case when b.type=2 then num else 0 end) as applyNum\n" +
                " from cw_app_market a left join " +
                " (select channel_no,type,count(1) as applyTime,count(distinct user_id) as num  from cw_log where 1=1 ";
        if (appName != null) {
            if ("XSD".equals(appName)) {
                sql += " and app_name in ('XSD','DKB')";
            } else {
                sql += " and app_name = '" + appName + "'";
            }
        }
        if ("M".equals(cycle)) {
            if (applyDate != null && !"".equals(applyDate)) {
                sql += " and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m'),'%y-%m') ";
            } else {
                sql += " and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(NOW(),'%y-%m') ";
            }
        } else {
            if (applyDate != null && !"".equals(applyDate)) {
                sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%y-%m-%d') ";
            } else {
                sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(NOW(),'%y-%m-%d') ";
            }
        }
        sql += " and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                " group by channel_no,type " +
                ") b on a.code = b.channel_no \n";
        sql += " group by a.name " +
                " order by a.name ";
        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));

        return homeDto;
    }

    /**
     * 构造查询条件日
     * @param applyDate
     * @return
     */
    private String createChannelSqlByDay(String applyDate){
        String sql="";
        if (applyDate != null && !"".equals(applyDate)) {
            sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%y-%m-%d') ";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(NOW(),'%y-%m-%d') ";
        }
        return sql;
    }

    /**
     * 构造查询条件月
     * @param applyDate
     * @return
     */
    private String createChannelSqlByMonth(String applyDate){
        String sql="";
        if (applyDate != null && !"".equals(applyDate)) {
            sql += " and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m'),'%y-%m') ";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(NOW(),'%y-%m') ";
        }
        return sql;
    }
    /**
     * 统计应用市场新增，申请
     *
     * @return
     */
    public List<HomeDto> countChannelList(Integer type, String appName, String cycle, String applyDate) {
        String sql = "";
        if (type == 0) {
            sql = "  select a.name as channelNo,\n" +
                    "        b.num as num\n" +
                    "  from cw_app_market a LEFT JOIN (" +
                    " select channel_no,count(device_number) as num from cw_log " +
                    "  where 1=1 ";
            if (appName != null) {
               sql += " and app_name = '" + appName + "'";
            }
            if ("M".equals(cycle)) {
                sql += createChannelSqlByMonth(applyDate);
            } else {
                sql += createChannelSqlByDay(applyDate);
            }
            sql += "   and type=" + type +
                    "  and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                    "  and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                    "  group by channel_no) b \n" +
                    "      on a.code = b.channel_no \n" +
                    " where a.id in (3,4,17,18,19,14,11,7,8,16,21) " +
                    "  group by a.name " +
                    " union ALL " +
                    "select '其它' as channelNo,\n" +
                    "        sum(num) as num\n" +
                    "   from cw_app_market a LEFT JOIN (" +
                    "   select channel_no,count(device_number) as num " +
                    "    from cw_log" +
                    "   where 1=1 ";
            if (appName != null) {
                sql += " and app_name = '" + appName + "'";
            }
            if ("M".equals(cycle)) {
                sql += createChannelSqlByMonth(applyDate);
            } else {
                sql += createChannelSqlByDay(applyDate);
            }
            sql += " and type= " + type +
                    "  and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                    "  and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                    "  group by channel_no) b \n" +
                    "     on a.code = b.channel_no " +
                    " where a.id not in (3,4,17,18,19,14,11,7,8,16,21) ";
        } else if (type == 1) {
            sql = "  select a.name as channelNo,\n" +
                    "        b.num as num\n" +
                    "  from cw_app_market a LEFT JOIN (" +
                    "  select channel_no,count(distinct device_number) as num " +
                    "    from cw_log " +
                    " where 1=1 ";
            if (appName != null) {
                sql += " and app_name = '" + appName + "'";
            }
            if ("M".equals(cycle)) {
                sql += createChannelSqlByMonth(applyDate);
            } else {
                sql += createChannelSqlByDay(applyDate);
            }
            sql += "   and type=" + type +
                    "  and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                    "  and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                    "  group by channel_no ) b \n" +
                    "   on a.code = b.channel_no" +
                    " where a.id in (3,4,17,18,19,14,11,7,8,16,21) " +
                    " group by a.name " +
                    " union ALL " +
                    "select '其它' as channelNo,\n" +
                    "       sum(b.num) as num\n" +
                    "   from cw_app_market a LEFT JOIN (" +
                    "    select channel_no,count(distinct device_number) as num from cw_log " +
                    " where 1=1 ";
            if (appName != null) {
                sql += " and app_name = '" + appName + "'";
            }
            if ("M".equals(cycle)) {
                sql += createChannelSqlByMonth(applyDate);
            } else {
                sql += createChannelSqlByDay(applyDate);
            }
            sql += " and type= " + type +
                    " and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                    " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                    "  group by channel_no ) b \n" +
                    "     on a.code = b.channel_no " +
                    " where a.id not in (3,4,17,18,19,14,11,7,8,16,21) ";
        } else {
            sql = "  select a.name as channelNo,\n" +
                    "        b.num as num\n" +
                    "  from cw_app_market a " +
                    " LEFT JOIN (select channel_no,count(product_id) as num " +
                    "               from cw_log " +
                    "               where 1=1 ";
            if (appName != null) {
                sql += " and app_name = '" + appName + "'";
            }
            if ("M".equals(cycle)) {
                sql += createChannelSqlByMonth(applyDate);
            } else {
                sql += createChannelSqlByDay(applyDate);
            }
            sql += "  and type= 2 " +
                    " and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                    " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                    " group by channel_no ) b \n" +
                    "      on a.code = b.channel_no " +
                    " where a.id in (3,4,17,18,19,14,11,7,8,16,21) " +
                    " group by a.name " +
                    " union ALL " +
                    "select '其它' as channelNo,\n" +
                    "        sum(b.num) as num\n" +
                    "   from cw_app_market a LEFT JOIN (select channel_no,count(device_number) as num " +
                    "   from cw_log " +
                    " where  1=1 ";
            if (appName != null) {
                sql += " and app_name = '" + appName + "'";
            }
            if ("M".equals(cycle)) {
                sql += createChannelSqlByMonth(applyDate);
            } else {
                sql += createChannelSqlByDay(applyDate);
            }
            sql += "  and type= " + type +
                   " and channel_no in (select code from cw_channel union all select code from cw_app_market) " +
                   " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                   " group by channel_no) b \n" +
                   "     on a.code = b.channel_no " +
                   " where a.id not in (3,4,17,18,19,14,11,7,8,16,21) ";
        }
        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));

        return homeDto;
    }

    /**
     * 来量统计
     *
     * @param appName
     * @param cycle
     * @return
     */
    public List<AppDevDto> devUserLineChart(String appName, String cycle, String applyDate) {
        String sql = "";
        if ("M".equals(cycle)) {
            sql = "SELECT " +
                    " DATE_FORMAT(apply_date, '%d') as name," +
                    " count(user_id) as devUser " +
                    "FROM\n" +
                    " cw_log t\n" +
                    "WHERE\n" +
                    " t.type = 0  and t.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null)" +
                    " and channel_no in (select code from cw_channel union all select code from cw_app_market)  ";
            if (!"".equals(appName)) {
                if ("XSD".equals(appName)) {
                    sql += "and t.app_name in ('XSD','DKB')";
                } else {
                    sql += "and t.app_name='" + appName + "'";
                }
            }
            if (applyDate != null && !"".equals(applyDate)) {
                sql += " and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m'),'%y-%m') ";
            } else {
                sql += " and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(NOW(),'%y-%m') ";
            }
            sql += " GROUP BY\n" +
                    " DATE_FORMAT(apply_date, '%d') " +
                    "order by DATE_FORMAT(apply_date, '%d') asc ";
        } else {
            sql = " SELECT " +
                    " DATE_FORMAT(apply_date, '%H') as name," +
                    " count(user_id) as devUser " +
                    " FROM cw_log t " +
                    " WHERE t.type = 0 and t.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                    " and channel_no in (select code from cw_channel union all select code from cw_app_market) ";
            if (!"".equals(appName)) {
                if ("XSD".equals(appName)) {
                    sql += "and t.app_name in ('XSD','DKB')";
                } else {
                    sql += "and t.app_name='" + appName + "'";
                }
            }
            if (applyDate != null && !"".equals(applyDate)) {
                sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(STR_TO_DATE('" + applyDate + "','%Y-%m-%d'),'%y-%m-%d') ";
            } else {
                sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(NOW(),'%y-%m-%d') ";
            }
            sql += " GROUP BY " +
                    " DATE_FORMAT(apply_date, '%H') " +
                    " order by DATE_FORMAT(apply_date, '%H') asc ";
        }
        List<AppDevDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));

        return homeDto;
    }

    /**
     * 单渠道转化率统计表
     *
     * @return
     */
    public List<HomeDto> channelInOrOutcomeTotal(ReportSearchDto reportSearchDto) {
        String sortFlag = "";
        if ("ASC".equals(reportSearchDto.getSortDesc().toUpperCase())) {
            sortFlag = "desc";
        } else {
            sortFlag = "asc";
        }
        String sql = "select aa.*,'" + sortFlag + "' as sortDesc from ( " +
                " select c.id,c.name as channelNo, c.code as channelCode,\n" +
                "       IFNULL(sum(a.registerNum),0) as registerNum,\n" +
                "       IFNULL(sum(ab.applyTime),0) as applyTime,\n" +
                "       IFNULL(sum(ab.num),0) as applyNum " +
                "  from cw_channel c " +
                //注册用户数
                "  left join (select channel_no,count(1) as registerNum \n" +
                "              from cw_log  \n" +
                "      where type=0 and channel_no in (select code from cw_channel) ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "   and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "' ";
        } else {
            sql += "   and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        //查询产品名称
        if (!"".equals(reportSearchDto.getProductName()) && reportSearchDto.getProductName() != null) {
            sql += "   and product_id in (select id from cw_product where name like '%" + reportSearchDto.getProductName() + "%') ";
        }

        sql += " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                " group by channel_no) a on a.channel_no = c.code \n" +
                //申请次数及用户数
                "  left join (select channel_no,count(1) as applyTime ,count(distinct user_id) as num \n" +
                "              from cw_log \n" +
                "      where type=2 and channel_no in (select code from cw_channel)\n" +
                "    and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "   and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "' ";
        } else {
            sql += "   and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        //查询产品名称
        if (!"".equals(reportSearchDto.getProductName()) && reportSearchDto.getProductName() != null) {
            sql += "   and product_id in (select id from cw_product where name like '%" + reportSearchDto.getProductName() + "%') ";
        }
        sql += " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                " group by channel_no) ab on ab.channel_no = c.code \n";

        //查询渠道
        if (!"".equals(reportSearchDto.getChannelName()) && reportSearchDto.getChannelName() != null) {
            sql += "   where 1=1 and name like '%" + reportSearchDto.getChannelName() + "%' ";
        }

        sql += "group by c.id,c.name,c.code ";

        sql += " union all " +
                " select '999' id,'合计' as channelNo, '99999999' as channelCode, " +
                "       IFNULL(sum(a.registerNum),0) as registerNum, " +
                "       IFNULL(sum(ab.applyTime),0) as applyTime, " +
                "       IFNULL(sum(ab.num),0) as applyNum " +
                "  from cw_channel c " +
                //注册用户数
                "   left join (select channel_no,count(1) as registerNum \n" +
                "      from cw_log \n" +
                " where type = 0 and channel_no in (select code from cw_channel)\n";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "   and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "' ";
        } else {
            sql += "   and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        //查询产品名称
        if (!"".equals(reportSearchDto.getProductName()) && reportSearchDto.getProductName() != null) {
            sql += "   and product_id in (select id from cw_product where name like '%" + reportSearchDto.getProductName() + "%') ";
        }
        sql += " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                " group by channel_no) a on a.channel_no = c.code \n";
        //申请次数和用户数
        sql += "   left join (select channel_no,count(1) as applyTime ,count(distinct user_id) as num  \n" +
                "      from cw_log \n" +
                " where type=2 and channel_no in (select code from cw_channel)\n" +
                "    and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "   and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "' ";
        } else {
            sql += "   and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        //查询产品名称
        if (!"".equals(reportSearchDto.getProductName()) && reportSearchDto.getProductName() != null) {
            sql += "   and product_id in (select id from cw_product where name like '%" + reportSearchDto.getProductName() + "%') ";
        }
        sql += " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                " group by channel_no ) ab on ab.channel_no = c.code \n";

        //查询渠道
        if (!"".equals(reportSearchDto.getChannelName()) && reportSearchDto.getChannelName() != null) {
            sql += "   where 1=1 and name like '%" + reportSearchDto.getChannelName() + "%' ";
        }
        sql += " ) aa ";
        if (!"".equals(reportSearchDto.getSortColumn())) {
            if ("ASC".equals(sortFlag.toUpperCase())) {
                sql += " order by " + reportSearchDto.getSortColumn() + " desc ";
            } else {
                sql += " order by " + reportSearchDto.getSortColumn() + " asc ";
            }
        }

        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));

        return homeDto;
    }


    /**
     * 统计应用市场新增、申请
     *
     * @return
     */
    public List<HomeDto> monthDevUser(ReportSearchDto reportSearchDto) {
        String sortFlag = "";
        if ("ASC".equals(reportSearchDto.getSortDesc().toUpperCase())) {
            sortFlag = "desc";
        } else {
            sortFlag = "asc";
        }
        String sql = "select aa.*,'" + sortFlag + "' as sortDesc from (" +
                "    select a.id,a.channel as channelNo," +
                "             a.name as merchantName,\n" +
                "             IFNull(sum(applyTime),0) as applyTime,\n" +
                "             IFNull(sum(num),0) as applyNum\n" +
                "       from cw_product a " +
                "  left join  (select product_id,count(1) as applyTime,count(distinct user_id) as num " +
                "           from cw_log a \n" +
                "      where 1=1 and type=2 " +
                "        and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                "        and user_id in (select id from pf_se_user where type='user' and register_date is not null) " +
                "        and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司')  ";

        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and  DATE_FORMAT(apply_date,'%Y-%m-%d') between  '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and  DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d')";
        }
        //区分安卓和苹果
        if(!"all".equals(reportSearchDto.getSysType())){
            if("ios".equals(reportSearchDto.getSysType())){
                sql += " and channel_no ='IOS' ";
            }else{
                sql += " and channel_no !='IOS' ";
            }
        }
        if (!Objects.isNull(reportSearchDto.getAppName()) && !"all".equals(reportSearchDto.getAppName())) {
            sql += " and a.app_name = '" + reportSearchDto.getAppName() + "'";
        }
        sql += "  group by product_id )  b   on  a.id = b.product_id   \n" +
                "      where 1=1  and channel!='重庆平讯数据服务有限公司'   \n";

        if (reportSearchDto.getProductName() != null && !"".equals(reportSearchDto.getProductName())) {
            sql += " and name like '%" + reportSearchDto.getProductName() + "%'";
        }
        if (reportSearchDto.getMerchantName() != null && !"".equals(reportSearchDto.getMerchantName())) {
            sql += " and id in (select id from cw_product where channel like '%" + reportSearchDto.getMerchantName() + "%')";
        }

        sql += "   group by a.id,a.channel,a.name ";
        sql += "    union all ";
        sql += "    select '999' as  id,'合计' as channelNo," +
                "             '-' as merchantName,\n" +
                "             IFNull(sum(applyTime),0) as applyTime,\n" +
                "             IFNull(sum(num),0) as applyNum\n" +
                "       from cw_product a " +
                "  left join ( select product_id,count(1) as applyTime,count(distinct user_id) as num\n" +
                "           from cw_log a  " +
                "      where 1=1 and type=2 " +
                "        and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                "        and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司' ) " +
                "        and user_id in (select id from pf_se_user where type='user' and register_date is not null)";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and  DATE_FORMAT(apply_date,'%Y-%m-%d') between  '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and  DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d')";
        }
        //区分安卓和苹果
        if(!"all".equals(reportSearchDto.getSysType())){
            if("ios".equals(reportSearchDto.getSysType())){
                sql += " and channel_no ='IOS' ";
            }else{
                sql += " and channel_no !='IOS' ";
            }
        }

        if (!Objects.isNull(reportSearchDto.getAppName()) && !"all".equals(reportSearchDto.getAppName())) {
            sql += " and a.app_name = '" + reportSearchDto.getAppName() + "'";
        }

        sql += "  group by product_id) b on a.id = b.product_id   \n" +
                "  where 1=1 and channel!='重庆平讯数据服务有限公司'   \n";
        if (reportSearchDto.getProductName() != null && !"".equals(reportSearchDto.getProductName())) {
            sql += " and name like '%" + reportSearchDto.getProductName() + "%'";
        }
        if (reportSearchDto.getMerchantName() != null && !"".equals(reportSearchDto.getMerchantName())) {
            sql += " and id in (select id from cw_product where channel like '%" + reportSearchDto.getMerchantName() + "%')";
        }
        sql += " ) aa ";

        if (!"".equals(reportSearchDto.getSortColumn())) {
            if ("ASC".equals(sortFlag.toUpperCase())) {
                sql += " order by " + reportSearchDto.getSortColumn() + " desc ";
            } else {
                sql += " order by " + reportSearchDto.getSortColumn() + " asc ";
            }
        }
        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));

        return homeDto;
    }

    /**
     * 商户收入
     *
     * @return
     */
    public List<HomeDto> merchantIncome(ReportSearchDto reportSearchDto) {
        String sortFlag = "";
        if ("ASC".equals(reportSearchDto.getSortDesc().toUpperCase())) {
            sortFlag = "desc";
        } else {
            sortFlag = "asc";
        }
        String sql = "select aa.*,'" + sortFlag + "' sortDesc from ( " +
                "   SELECT " +
                "           a.id," +
                "           a.channel merchantName,\n" +
                "           IFNull(a.name,'') as channelNo,\n" +
                "           IFNull(c.name,'') as channelCode,\n" +
                "           IFNull(d.name,'') as appName,\n" +
                "           IFNull(sum(applyTime),0) applyTime,\n" +
                "           IFNull(sum(applyNum),0) as applyNum \n" +
                "      FROM cw_product a " +
                "  left JOIN (select channel_no,product_id,app_name,sum(1) as applyTime,count(DISTINCT user_id) as applyNum" +
                "  from cw_log  where 1=1 and type=2 ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "' ";
        } else {
            sql += "  and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        if (reportSearchDto.getChannelNo() != null && !"".equals(reportSearchDto.getChannelNo()) && !"all".equals(reportSearchDto.getChannelNo())) {
            sql += " and channel_no = '" + reportSearchDto.getChannelNo() + "' ";
        }
        sql += "    and user_id in (select id from pf_se_user where type='user' and register_date is not null)  " +
                "  and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') " +
                "  and channel_no in (select code from cw_channel union all select code from cw_app_market) " +
                " group by channel_no,product_id,app_name) b ON a.id = b.product_id " +
                " left join (select code,name from cw_channel\n" +
                "union all \n" +
                "select code ,name from cw_app_market) c on b.channel_no = c.code " +
                " left join cw_app d on b.app_name = d.code"+
                " where 1=1 and channel!='重庆平讯数据服务有限公司' ";

        if (reportSearchDto.getProductName() != null && !"".equals(reportSearchDto.getProductName())) {
            sql += " and a.name like '%" + reportSearchDto.getProductName() + "%'";
        }
        if (reportSearchDto.getMerchantName() != null && !"".equals(reportSearchDto.getMerchantName())) {
            sql += " and a.channel like '%" + reportSearchDto.getMerchantName() + "%' ";
        }
        if (reportSearchDto.getAppName() != null && !"".equals(reportSearchDto.getAppName())&& !"all".equals(reportSearchDto.getAppName())) {
            sql += " and b.app_name = '" + reportSearchDto.getAppName() + "' ";
        }
        sql += "  GROUP BY a.id," +
                "           a.channel,\n" +
                "           a.name,\n" +
                "           c.name,\n" +
                "           d.name \n";

        sql += " union all " +
                "   SELECT " +
                "           '999' as id," +
                "           '合计' merchantName,\n" +
                "           '-' as channelNo,\n" +
                "           '-' as channelCode,\n" +
                "           '-' as appName,\n" +
                "           IFNull(sum(applyTime),0) applyTime,\n" +
                "           IFNull(sum(applyNum),0) as applyNum \n" +
                "      FROM cw_product a " +
                "  left JOIN (select product_id,sum(1) as applyTime,count(DISTINCT user_id) as applyNum" +
                "  from cw_log where 1=1 and type=2 " +
                "   and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "' ";
        } else {
            sql += "  and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        if (reportSearchDto.getChannelNo() != null && !"".equals(reportSearchDto.getChannelNo())&& !"all".equals(reportSearchDto.getChannelNo())) {
            sql += " and channel_no = '" + reportSearchDto.getChannelNo() + "' ";
        }
        if (reportSearchDto.getAppName() != null && !"".equals(reportSearchDto.getAppName())&& !"all".equals(reportSearchDto.getAppName())) {
            sql += " and app_name = '" + reportSearchDto.getAppName() + "' ";
        }
        sql += "    and user_id in (select id from pf_se_user where type='user' and register_date is not null)  " +
                "  and channel_no in (select code from cw_channel union all select code from cw_app_market) " +
                " group by product_id ) b ON a.id = b.product_id " +
                " where 1=1 and channel!='重庆平讯数据服务有限公司' ";

        if (reportSearchDto.getProductName() != null && !"".equals(reportSearchDto.getProductName())) {
            sql += " and a.name like '%" + reportSearchDto.getProductName() + "%'";
        }
        if (reportSearchDto.getMerchantName() != null && !"".equals(reportSearchDto.getMerchantName())) {
            sql += " and a.channel like '%" + reportSearchDto.getMerchantName() + "%' ";
        }
        sql += " ) aa ";
        if (!"".equals(reportSearchDto.getSortColumn())) {
            if ("ASC".equals(sortFlag.toUpperCase())) {
                sql += " order by " + reportSearchDto.getSortColumn() + " desc ";
            } else {
                sql += " order by " + reportSearchDto.getSortColumn() + " asc ";
            }
        }

        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));

        return homeDto;
    }

    /**
     * 各APP新增用户数
     *
     * @return
     */
    public List<AppDevDto> appDevUser(ReportSearchDto reportSearchDto) {
        String sql = "  select * from ( SELECT a.name as appName, " +
                "                       0 merchantIncome, " +
                "                       IFNULL(sum(case when d.type=0 then d.applyTime else 0 end),0) as devUser , " +
                "                       IFNULL(sum(case when c.type=2 then c.applyTime else 0 end),0) as applyTime, " +
                "                       IFNULL(sum(case when c.type=2 then c.applyNum  else 0 end),0) as applyNum " +
                "                FROM cw_app a  " +
                "             left join (\n" +
                "               select app_name,type,count(1) as applyTime ,count(distinct user_id)  as applyNum " +
                "                 from cw_log b  " +
                "                where 1=1 ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += "  and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        if (reportSearchDto.getChannelNo() != null && !"".equals(reportSearchDto.getChannelNo())&& !"all".equals(reportSearchDto.getChannelNo())) {
            sql += " and channel_no = '" + reportSearchDto.getChannelNo() + "'";
        }
        sql += "     and type=2 and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司') " +
                "    and b.user_id in (select id from pf_se_user where type='user' and register_date is not null)         " +
                " group by app_name,type " +
                " ) c  on a.code = c.app_name       \n" +
                "   left join (\n" +
    "               select app_name,type,count(1) as applyTime " +
    "                 from cw_log b  " +
    "                where 1=1 ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += "  and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        if (reportSearchDto.getChannelNo() != null && !"".equals(reportSearchDto.getChannelNo())&& !"all".equals(reportSearchDto.getChannelNo())) {
            sql += " and channel_no = '" + reportSearchDto.getChannelNo() + "'";
        }
        sql += "     and type=0 " +
                "    and b.user_id in (select id from pf_se_user where type='user' and register_date is not null)         " +
                "   group by app_name,type " +
                "  ) d  on a.code = d.app_name       \n" +
                " GROUP BY a.name ";
        sql += " union all " +
                " SELECT '合计' as appName,\n" +
                "                 0 merchantIncome,\n" +
                "                 IFNULL(sum(case when c.type=0 then applyTime else 0 end),0) as devUser ,\n" +
                "                 IFNULL(sum(case when c.type=2 then applyTime else 0 end),0) as applyTime,\n" +
                "                 IFNULL(sum(case when c.type=2 then applyNum  else 0 end),0) as applyNum\n" +
                "           FROM (\n" +
                "              select app_name,type,count(1) as applyTime ,count(distinct user_id)  as applyNum\n" +
                "                from cw_log b \n" +
                "               where 1=1\n";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += "  and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        if (reportSearchDto.getChannelNo() != null && !"".equals(reportSearchDto.getChannelNo()) && !"all".equals(reportSearchDto.getChannelNo())) {
            sql += " and channel_no = '" + reportSearchDto.getChannelNo() + "'";
        }
        sql +=  "  and type=2 and product_id in (select id from cw_product where channel!='重庆平讯数据服务有限公司')  " +
                "  and b.user_id in (select id from pf_se_user where type='user' and register_date is not null)                \n" +
                " group by app_name,type\n" +
                "      union all        " +
                " select app_name,type,count(1) as applyTime ,0 as applyNum\n" +
                "                from cw_log b \n" +
                "               where 1=1\n";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += "  and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        if (reportSearchDto.getChannelNo() != null && !"".equals(reportSearchDto.getChannelNo()) && !"all".equals(reportSearchDto.getChannelNo())) {
            sql += " and channel_no = '" + reportSearchDto.getChannelNo() + "'";
        }
        sql +=  "  and type=0  " +
                "  and b.user_id in (select id from pf_se_user where type='user' and register_date is not null)                \n" +
                " group by app_name,type\n" +
                " ) c  ) aa order by  devUser desc ";

        List<AppDevDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));

        return homeDto;
    }

    /**
     * 每天各时段新增用户数分布
     *
     * @return
     */
    public List<AppDevDto> devUserHour(ReportSearchDto reportSearchDto) {
        String sql;
        if (reportSearchDto.getChannelId().length() < 8) {
            if (!"month".equals(reportSearchDto.getQuota())) {
                sql = "select IFNULL(apply_date,'00') as name,\n" +
                        "     sum(case when b.type=0 then applyTime else 0 end) as devUser, \n" +
                        "     sum(case when b.type=2 then applyTime else 0 end) as applyTime, \n" +
                        "     sum(case when b.type=2 then num else 0 end) as applyNum \n" +
                        " from cw_app_market a left join " +
                        "      (select IFNULL(DATE_FORMAT(apply_date,'%H'),'00') as apply_date,channel_no,type," +
                        "              count(1) as applyTime," +
                        "              count(distinct user_id) as num" +
                        "  from cw_log where user_id in (select id from pf_se_user where type='user' " +
                        "        and register_date is not null)";
                if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
                    sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
                } else {
                    if ("month".equals(reportSearchDto.getQuota())) {
                        sql += "  and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(now(),'%y-%m') ";
                    } else {
                        sql += "  and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
                    }
                }
                if (reportSearchDto.getAppName() != null && !"".equals(reportSearchDto.getAppName())
                        && !"all".equals(reportSearchDto.getAppName()) && !"0".equals(reportSearchDto.getAppName())) {
                    if ("XSD".equals(reportSearchDto.getAppName())) {
                        sql += " and app_name in ('XSD','DKB')";
                    } else {
                        sql += " and app_name = '" + reportSearchDto.getAppName() + "'";
                    }
                }
                sql += "   group by IFNULL(DATE_FORMAT(apply_date,'%H'),'00'),channel_no,type   ) b " +
                        "    on a.code = b.channel_no  " +
                        "  where 1=1 ";
                if (reportSearchDto.getChannelId() != null && !"999".equals(reportSearchDto.getChannelId())) {
                    sql += "  and a.id = '" + reportSearchDto.getChannelId() + "'";
                }
                sql += " group by IFNULL(apply_date,'00') \n" +
                        " order by IFNULL(apply_date,'00') ";
            } else {
                sql = "select IFNULL(apply_date,'1') as name,\n" +
                        "     sum(case when b.type=0 then applyTime else 0 end) as devUser, \n" +
                        "     sum(case when b.type=2 then applyTime else 0 end) as applyTime, \n" +
                        "     sum(case when b.type=2 then num else 0 end) as applyNum \n" +
                        " from cw_app_market a left join " +
                        "      (select IFNULL(DATE_FORMAT(apply_date,'%d'),'00') as apply_date,channel_no,type," +
                        "              count(1) as applyTime," +
                        "              count(distinct user_id) as num" +
                        "  from cw_log where user_id in (select id from pf_se_user where type='user' " +
                        "        and register_date is not null)";
                if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
                    sql += "  and DATE_FORMAT(apply_date,'%Y-%m') between '" + reportSearchDto.getApplyStartDate().substring(0, 7) + "' and  '" + reportSearchDto.getApplyEndDate().substring(0, 7) + "'";
                } else {
                    sql += "  and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(now(),'%y-%m') ";

                }
                if (reportSearchDto.getAppName() != null && !"".equals(reportSearchDto.getAppName())
                        && !"all".equals(reportSearchDto.getAppName()) && !"0".equals(reportSearchDto.getAppName())) {
                    if ("XSD".equals(reportSearchDto.getAppName())) {
                        sql += " and app_name in ('XSD','DKB')";
                    } else {
                        sql += " and app_name = '" + reportSearchDto.getAppName() + "'";
                    }
                }
                sql += "   group by IFNULL(DATE_FORMAT(apply_date,'%d'),'00'),channel_no,type   ) b " +
                        "    on a.code = b.channel_no  " +
                        "  where 1=1 ";
                if (reportSearchDto.getChannelId() != null && !"999".equals(reportSearchDto.getChannelId())) {
                    sql += "  and a.id = '" + reportSearchDto.getChannelId() + "'";
                }
                sql += " group by IFNULL(apply_date,'1') \n" +
                        " order by IFNULL(apply_date,'1') ";
            }
        } else {
            sql = "select IFNULL(apply_date,'00') as name,\n" +
                    "       sum(case when b.type=0 then applyTime else 0 end) as devUser, \n" +
                    "      sum(case when b.type=2 then applyTime else 0 end) as applyTime, \n" +
                    "      sum(case when b.type=2 then num else 0 end) as applyNum \n" +
                    " from cw_channel a left join " +
                    "      (select IFNULL(DATE_FORMAT(apply_date,'%H'),'00') as apply_date,channel_no,type," +
                    "              count(1) as applyTime," +
                    "              count(distinct user_id) as num" +
                    "        from cw_log " +
                    "       where user_id in (select id from pf_se_user where type='user' " +
                    "          and register_date is not null) " +
                    "";
            if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
                sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
            } else {
                if (!"month".equals(reportSearchDto.getQuota())) {
                    sql += "  and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
                } else {
                    sql += "  and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(now(),'%y-%m') ";
                }
            }

            if (reportSearchDto.getAppName() != null && !"".equals(reportSearchDto.getAppName())
                    && !"all".equals(reportSearchDto.getAppName()) && !"0".equals(reportSearchDto.getAppName())) {
                if ("XSD".equals(reportSearchDto.getAppName())) {
                    sql += " and app_name in ('XSD','DKB')";
                } else {
                    sql += " and app_name = '" + reportSearchDto.getAppName() + "'";
                }
            }

            sql += "  group by IFNULL(DATE_FORMAT(apply_date,'%H'),'00'),channel_no,type     ) b " +
                    "    on a.code = b.channel_no  " +
                    "  where 1=1 ";
            if (reportSearchDto.getChannelId() != null && !"999".equals(reportSearchDto.getChannelId())) {
                sql += "  and a.code = '" + reportSearchDto.getChannelId() + "'";
            }

            sql += " group by IFNULL(apply_date,'00') \n" +
                    " order by IFNULL(apply_date,'00') ";
        }
        List<AppDevDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));

        return homeDto;
    }


    public List<HomeDto> getChannelData(ReportSearchDto reportSearchDto) {
        String sql = "select applyDate as applyDate," +
                "       sum(registerNum) as registerNum,\n" +
                "       sum(applyTime) as applyTime,\n" +
                "       sum(applyNum) as applyNum\n" +
                "  from (\n" +
                "select DATE_FORMAT(apply_date,'%Y-%m-%d') as applyDate," +
                "        count(1) as registerNum,\n" +
                "        0 as applyTime,\n" +
                "        0 as applyNum\n" +
                "  from cw_log " +
                " where channel_no='" + CPContext.getContext().getSeUserInfo().getUsername() + "' and type = 0\n";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        sql += " group by applyDate";

        sql += " union ALL \n" +
                " select DATE_FORMAT(apply_date,'%Y-%m-%d') as applyDate," +
                " 0 as registerNum,\n" +
                "       count(1) as applyTime,\n" +
                "       count(DISTINCT user_id) as applyNum\n" +
                "  from cw_product_apply \n" +
                " where channel_no='" + CPContext.getContext().getSeUserInfo().getUsername() + "' \n";
        if (reportSearchDto.getApplyStartDate() != null) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        sql += " group by DATE_FORMAT(apply_date,'%Y-%m-%d')" +
                ") a  " +
                " group by applyDate " +
                " order by applyDate DESC ";

        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));
        return homeDto;
    }

    /**
     * 运营报表数据
     *
     * @return
     */
    public List<OperateDto> operateReport(ReportSearchDto reportSearchDto) {
        String sqlTotal = "(select totalDay,IFNULL(count(1),0) as applyNum," +
                "       sum(case when app_name ='MBD' then  1 else 0 end) as mbdApplyNum,  \n" +
                "       sum(case when app_name in ('XSD','DKB') then 1 else 0 end) as xsdApplyNum,  \n" +
                "       sum(case when app_name ='JQW' then 1 else 0 end) as jqwApplyNum,  \n" +
                "       sum(case when app_name ='JDS' then 1 else 0 end) as jdsApplyNum,  \n" +
                "       sum(case when app_name ='DSQB' then 1 else 0 end) as dsqbApplyNum,  \n" +
                "       sum(case when app_name ='DKD' then 1 else 0 end) as dkdApplyNum,  \n" +
                "       sum(case when app_name ='DKBL' then 1 else 0 end) as dkblApplyNum,  \n" +
                "       sum(case when app_name ='PXQB' then 1 else 0 end) as pxqbApplyNum,  \n" +
                "       sum(case when app_name ='LSQD' then 1 else 0 end) as lsqdApplyNum,  \n" +
                "       sum(case when app_name ='DKQB' then 1 else 0 end) as dkqbApplyNum,  \n" +

                "       sum(case when app_name ='SYD' then 1 else 0 end) as sydApplyNum,  \n" +
                "       sum(case when app_name ='SRD' then 1 else 0 end) as srdApplyNum,  \n" +
                "       sum(case when app_name ='DKZG' then 1 else 0 end) as dkzgApplyNum,  \n" +
                "       sum(case when app_name ='JJK' then 1 else 0 end) as jjkApplyNum,  \n" +
                "       sum(case when app_name ='DQL' then 1 else 0 end) as dqlApplyNum,  \n" +
                "       sum(case when app_name ='JDB' then 1 else 0 end) as jdbApplyNum,  \n" +

                "       sum(case when app_name ='JQHH' then 1 else 0 end) as jqhhApplyNum,  \n" +
                "       sum(case when app_name ='DKH' then 1 else 0 end) as dkhApplyNum,  \n" +
                "       sum(case when app_name ='MDB' then 1 else 0 end) as mdbApplyNum,  \n" +
                "       sum(case when app_name ='XED' then 1 else 0 end) as xedApplyNum,  \n" +
                "       sum(case when app_name ='JQS' then 1 else 0 end) as jqsApplyNum,  \n" +
                "       sum(case when app_name ='JDJQ' then 1 else 0 end) as jdjqApplyNum,  \n" +
                //中鼎国泰APP
                "       sum(case when app_name ='BSYD' then 1 else 0 end) as kjwApplyNum,  \n" +
                "       sum(case when app_name ='BJQS' then 1 else 0 end) as dkssApplyNum,  \n" +
                "       sum(case when app_name ='BYDDK' then 1 else 0 end) as jqfApplyNum,  \n" +
                "       sum(case when app_name ='BDKSS' then 1 else 0 end) as dktApplyNum,  \n" +
                "       sum(case when app_name ='BHDB' then 1 else 0 end) as hdbApplyNum,  \n" +
                "       sum(case when app_name ='BDKZG' then 1 else 0 end) as yjxApplyNum,  \n" +
                "       sum(case when app_name ='BJJK' then 1 else 0 end) as bjjkApplyNum, \n" +
                "       sum(case when app_name ='BJQW' then 1 else 0 end) as bjqwApplyNum  \n" +

                "  from (select DATE_FORMAT(apply_date,'%Y-%m-%d') as totalDay,app_name,channel_no,user_id " +
                "          FROM cw_log a " +
                "         where type=2 ";

            if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
                sqlTotal += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
            } else {
                sqlTotal += " and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
            }
        sqlTotal+=  " and channel_no in (select code from cw_channel union all select code from cw_app_market) " +
                "     and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                "     group by DATE_FORMAT(apply_date,'%Y-%m-%d'),app_name,channel_no,user_id " +
                " ) cc group by totalDay) ";

        String sqlHeji = "(select '-' as day,IFNULL(count(1),0) as applyNum," +
                "       sum(case when app_name ='MBD' then 1 else 0 end) as mbdApplyNum,  \n" +
                "       sum(case when app_name in ('XSD','DKB') then 1 else 0 end) as xsdApplyNum,  \n" +
                "       sum(case when app_name ='JQW' then 1 else 0 end) as jqwApplyNum,  \n" +
                "       sum(case when app_name ='JDS' then 1 else 0 end) as jdsApplyNum,  \n" +
                "       sum(case when app_name ='DSQB' then 1 else 0 end) as dsqbApplyNum,  \n" +
                "       sum(case when app_name ='DKD' then 1 else 0 end) as dkdApplyNum,  \n" +
                "       sum(case when app_name ='DKBL' then 1 else 0 end) as dkblApplyNum,  \n" +
                "       sum(case when app_name ='PXQB' then 1 else 0 end) as pxqbApplyNum,  \n" +
                "       sum(case when app_name ='LSQD' then 1 else 0 end) as lsqdApplyNum,  \n" +
                "       sum(case when app_name ='DKQB' then 1 else 0 end) as dkqbApplyNum,  \n" +
                "       sum(case when app_name ='SYD' then 1 else 0 end) as sydApplyNum,  \n" +
                "       sum(case when app_name ='SRD' then 1 else 0 end) as srdApplyNum,  \n" +
                "       sum(case when app_name ='DKZG' then 1 else 0 end) as dkzgApplyNum,  \n" +
                "       sum(case when app_name ='JJK' then 1 else 0 end) as jjkApplyNum,  \n" +
                "       sum(case when app_name ='DQL' then 1 else 0 end) as dqlApplyNum,  \n" +
                "       sum(case when app_name ='JDB' then 1 else 0 end) as jdbApplyNum,  \n" +

                "       sum(case when app_name ='JQHH' then 1 else 0 end) as jqhhApplyNum,  \n" +
                "       sum(case when app_name ='DKH' then 1 else 0 end) as dkhApplyNum,  \n" +
                "       sum(case when app_name ='MDB' then 1 else 0 end) as mdbApplyNum,  \n" +
                "       sum(case when app_name ='XED' then 1 else 0 end) as xedApplyNum,  \n" +
                "       sum(case when app_name ='JQS' then 1 else 0 end) as jqsApplyNum,  \n" +
                "       sum(case when app_name ='JDJQ' then 1 else 0 end) as jdjqApplyNum,  \n" +

                "       sum(case when app_name ='BSYD' then 1 else 0 end) as kjwApplyNum,  \n" +
                "       sum(case when app_name ='BJQS' then 1 else 0 end) as dkssApplyNum,  \n" +
                "       sum(case when app_name ='BYDDK' then 1 else 0 end) as jqfApplyNum,  \n" +
                "       sum(case when app_name ='BDKSS' then 1 else 0 end) as dktApplyNum,  \n" +
                "       sum(case when app_name ='BHDB' then 1 else 0 end) as hdbApplyNum,  \n" +
                "       sum(case when app_name ='BDKZG' then 1 else 0 end) as yjxApplyNum,  \n" +
                "       sum(case when app_name ='BJJK' then 1 else 0 end) as bjjkApplyNum, \n" +
                "       sum(case when app_name ='BJQW' then 1 else 0 end) as bjqwApplyNum  \n" +

                "  from (select DATE_FORMAT(apply_date,'%Y-%m-%d') as totalDay,app_name,channel_no,user_id " +
                "          FROM cw_log a " +
                "         where type=2   ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sqlHeji += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sqlHeji += " and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        sqlHeji += "     and channel_no in (select code from cw_channel union all select code from cw_app_market) " +
                "     and a.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                "     group by DATE_FORMAT(apply_date,'%Y-%m-%d'),app_name,channel_no,user_id " +
                " ) cc ) ";

        String sql = "select aa.*," +
                "           IFNULL(b.applyNum,0) as applyNum," +
                "           IFNULL(b.mbdApplyNum,0) as mbdApplyNum, " +
                "           IFNULL(b.xsdApplyNum,0) as xsdApplyNum," +
                "           IFNULL(b.jqwApplyNum,0)  as jqwApplyNum," +
                "           IFNULL(b.jdsApplyNum,0)  as jdsApplyNum," +
                "           IFNULL(b.dsqbApplyNum,0)  as dsqbApplyNum," +
                "           IFNULL(b.dkqbApplyNum,0)  as dkqbApplyNum, " +
                "           IFNULL(b.dkdApplyNum,0)  as dkdApplyNum," +
                "           IFNULL(b.pxqbApplyNum,0)  as pxqbApplyNum," +
                "           IFNULL(b.dkblApplyNum,0)  as dkblApplyNum, " +
                "           IFNULL(b.lsqdApplyNum,0)  as lsqdApplyNum, " +
                "           IFNULL(b.sydApplyNum,0)  as sydApplyNum, " +
                "           IFNULL(b.srdApplyNum,0)  as srdApplyNum, " +
                "           IFNULL(b.dqlApplyNum,0)  as dqlApplyNum, " +
                "           IFNULL(b.jdbApplyNum,0)  as jdbApplyNum, " +
                "           IFNULL(b.jjkApplyNum,0)  as jjkApplyNum, " +
                "           IFNULL(b.dkzgApplyNum,0)  as dkzgApplyNum, " +

                "           IFNULL(b.jqhhApplyNum,0)  as jqhhApplyNum, " +
                "           IFNULL(b.dkhApplyNum,0)  as dkhApplyNum, " +
                "           IFNULL(b.mdbApplyNum,0)  as mdbApplyNum, " +
                "           IFNULL(b.xedApplyNum,0)  as xedApplyNum, " +
                "           IFNULL(b.jqsApplyNum,0)  as jqsApplyNum, " +
                "           IFNULL(b.jdjqApplyNum,0)  as jdjqApplyNum, " +

                "           IFNULL(b.kjwApplyNum,0)  as kjwApplyNum, " +
                "           IFNULL(b.dkssApplyNum,0)  as dkssApplyNum, " +
                "           IFNULL(b.jqfApplyNum,0)  as jqfApplyNum, " +
                "           IFNULL(b.dktApplyNum,0)  as dktApplyNum, " +
                "           IFNULL(b.hdbApplyNum,0)  as hdbApplyNum, " +
                "           IFNULL(b.yjxApplyNum,0)  as yjxApplyNum, " +
                "           IFNULL(b.bjjkApplyNum,0)  as bjjkApplyNum, " +
                "           IFNULL(b.bjqwApplyNum,0)  as bjqwApplyNum " +

                " from (select DATE_FORMAT(apply_date,'%Y-%m-%d') as day,\n" +
                createOperateSqlHead()+
                "  from cw_log t  \n" +
                " where 1=1 ";

        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        sql += "   and t.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null) " +
                "  group by DATE_FORMAT(apply_date,'%Y-%m-%d') ) aa  " +
                "  left join " + sqlTotal + " b on aa.day = b.totalDay ";

        sql += " union all" +
                " select aa.*,IFNULL(b.applyNum,0) as applyNum," +
                "        IFNULL(b.mbdApplyNum,0) as mbdApplyNum, " +
                "        IFNULL(b.xsdApplyNum,0) as xsdApplyNum," +
                "        IFNULL(b.jqwApplyNum,0)  as jqwApplyNum," +
                "        IFNULL(b.jdsApplyNum,0)  as jdsApplyNum," +
                "        IFNULL(b.dsqbApplyNum,0)  as dsqbApplyNum," +
                "        IFNULL(b.dkqbApplyNum,0)  as dkqbApplyNum," +
                "        IFNULL(b.dkdApplyNum,0)  as dkdApplyNum," +
                "        IFNULL(b.pxqbApplyNum,0)  as pxqbApplyNum," +
                "        IFNULL(b.dkblApplyNum,0)  as dkblApplyNum, " +
                "        IFNULL(b.lsqdApplyNum,0)  as lsqdApplyNum, " +
                "        IFNULL(b.sydApplyNum,0)  as sydApplyNum, " +
                "        IFNULL(b.srdApplyNum,0)  as srdApplyNum, " +
                "        IFNULL(b.dqlApplyNum,0)  as dqlApplyNum, " +
                "        IFNULL(b.jdbApplyNum,0)  as jdbApplyNum, " +
                "        IFNULL(b.jjkApplyNum,0)  as jjkApplyNum, " +
                "        IFNULL(b.dkzgApplyNum,0)  as dkzgApplyNum, " +

                "        IFNULL(b.jqhhApplyNum,0)  as jqhhApplyNum, " +
                "        IFNULL(b.dkhApplyNum,0)  as dkhApplyNum, " +
                "        IFNULL(b.mdbApplyNum,0)  as mdbApplyNum, " +
                "        IFNULL(b.xedApplyNum,0)  as xedApplyNum, " +
                "        IFNULL(b.jqsApplyNum,0)  as jqsApplyNum, " +
                "        IFNULL(b.jdjqApplyNum,0)  as jdjqApplyNum, " +

                "        IFNULL(b.kjwApplyNum,0)  as kjwApplyNum, " +
                "        IFNULL(b.dkssApplyNum,0)  as dkssApplyNum, " +
                "        IFNULL(b.jqfApplyNum,0)  as jqfApplyNum, " +
                "        IFNULL(b.dktApplyNum,0)  as dktApplyNum, " +
                "        IFNULL(b.hdbApplyNum,0)  as hdbApplyNum, " +
                "        IFNULL(b.yjxApplyNum,0)  as yjxApplyNum, " +
                "        IFNULL(b.bjjkApplyNum,0)  as bjjkApplyNum, " +
                "        IFNULL(b.bjqwApplyNum,0)  as bjqwApplyNum " +

                " from (select '-' as day,\n" +
                createOperateSqlHead() +
                "  from cw_log t  \n" +
                " where 1=1 ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        sql += "   and t.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null)\n" +
                " ) aa  " +
                " left join " + sqlHeji + " b on aa.day = b.day " +
                " order by day desc \n";

        List<OperateDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(OperateDto.class));
        return homeDto;
    }

    /**
     * 运营市场查询sql
     * @return
     */
    private String createOperateSqlHead(){
        String sql= "   IFNULL(sum(case when app_name='MBD' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as mbdios,\n" +
                "       IFNULL(sum(case when app_name='MBD' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as mbdandroid,\n" +
                "       IFNULL(sum(case when app_name='MBD' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as mbdh5,\n" +
                "       IFNULL(sum(case when app_name='MBD' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as mbdApplyTime,\n" +

                "       IFNULL(sum(case when app_name='JQW' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as jqwios,\n" +
                "       IFNULL(sum(case when app_name='JQW' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as jqwandroid,\n" +
                "       IFNULL(sum(case when app_name='JQW' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as jqwh5,\n" +
                "       IFNULL(sum(case when app_name='JQW' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as jqwApplyTime,\n" +

                "       IFNULL(sum(case when app_name in ('XSD','DKB') and type=0 and channel_no ='IOS' then 1 else 0 end),0) as xsdios,\n" +
                "       IFNULL(sum(case when app_name in ('XSD','DKB') and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as xsdandroid,\n" +
                "       IFNULL(sum(case when app_name in ('XSD','DKB') and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as xsdh5,\n" +
                "       IFNULL(sum(case when app_name in ('XSD','DKB') and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as xsdApplyTime,\n" +

                "       IFNULL(sum(case when app_name='JDS' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as jdsios,\n" +
                "       IFNULL(sum(case when app_name='JDS' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as jdsandroid,\n" +
                "       IFNULL(sum(case when app_name='JDS' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as jdsh5,\n" +
                "       IFNULL(sum(case when app_name='JDS' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as jdsApplyTime,\n" +

                "      IFNULL(sum(case when app_name='DSQB' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as dsqbios,\n" +
                "      IFNULL(sum(case when app_name='DSQB' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as dsqbandroid,\n" +
                "      IFNULL(sum(case when app_name='DSQB' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as dsqbh5,\n" +
                "      IFNULL(sum(case when app_name='DSQB' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as dsqbApplyTime,\n" +

                "      IFNULL(sum(case when app_name='DKD' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as dkdios,\n" +
                "      IFNULL(sum(case when app_name='DKD' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as dkdandroid,\n" +
                "      IFNULL(sum(case when app_name='DKD' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as dkdh5,\n" +
                "      IFNULL(sum(case when app_name='DKD' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as dkdApplyTime,\n" +

                "      IFNULL(sum(case when app_name='PXQB' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as pxqbios,\n" +
                "      IFNULL(sum(case when app_name='PXQB' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as pxqbandroid,\n" +
                "      IFNULL(sum(case when app_name='PXQB' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as pxqbh5,\n" +
                "      IFNULL(sum(case when app_name='PXQB' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as pxqbApplyTime,\n" +

                "      IFNULL(sum(case when app_name='DKBL' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as dkblios,\n" +
                "      IFNULL(sum(case when app_name='DKBL' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as dkblandroid,\n" +
                "      IFNULL(sum(case when app_name='DKBL' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as dkblh5,\n" +
                "      IFNULL(sum(case when app_name='DKBL' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as dkblApplyTime,\n" +

                "      IFNULL(sum(case when app_name='LSQD' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as lsqdios,\n" +
                "      IFNULL(sum(case when app_name='LSQD' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as lsqdandroid,\n" +
                "      IFNULL(sum(case when app_name='LSQD' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as lsqdh5,\n" +
                "      IFNULL(sum(case when app_name='LSQD' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as lsqdApplyTime,\n" +

                "      IFNULL(sum(case when app_name='DKQB' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as dkqbios,\n" +
                "      IFNULL(sum(case when app_name='DKQB' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as dkqbandroid,\n" +
                "      IFNULL(sum(case when app_name='DKQB' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as dkqbh5,\n" +
                "      IFNULL(sum(case when app_name='DKQB' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as dkqbApplyTime,\n" +

                "       IFNULL(sum(case when app_name='SYD' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as sydios,\n" +
                "       IFNULL(sum(case when app_name='SYD' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as sydandroid,\n" +
                "       IFNULL(sum(case when app_name='SYD' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as sydh5,\n" +
                "       IFNULL(sum(case when app_name='SYD' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as sydApplyTime,\n" +

                "       IFNULL(sum(case when app_name='SRD' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as srdios,\n" +
                "       IFNULL(sum(case when app_name='SRD' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as srdandroid,\n" +
                "       IFNULL(sum(case when app_name='SRD' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as srdh5,\n" +
                "       IFNULL(sum(case when app_name='SRD' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as srdApplyTime,\n" +

                "       IFNULL(sum(case when app_name='DKZG' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as dkzgios,\n" +
                "       IFNULL(sum(case when app_name='DKZG' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as dkzgandroid,\n" +
                "       IFNULL(sum(case when app_name='DKZG' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as dkzgh5,\n" +
                "       IFNULL(sum(case when app_name='DKZG' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as dkzgApplyTime,\n" +

                "       IFNULL(sum(case when app_name='JJK' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as jjkios,\n" +
                "       IFNULL(sum(case when app_name='JJK' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as jjkandroid,\n" +
                "       IFNULL(sum(case when app_name='JJK' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as jjkh5,\n" +
                "       IFNULL(sum(case when app_name='JJK' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as jjkApplyTime,\n" +

                "       IFNULL(sum(case when app_name='DQL' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as dqlios,\n" +
                "       IFNULL(sum(case when app_name='DQL' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as dqlandroid,\n" +
                "       IFNULL(sum(case when app_name='DQL' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as dqlh5,\n" +
                "       IFNULL(sum(case when app_name='DQL' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as dqlApplyTime,\n" +

                "       IFNULL(sum(case when app_name='JDB' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as jdbios,\n" +
                "       IFNULL(sum(case when app_name='JDB' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as jdbandroid,\n" +
                "       IFNULL(sum(case when app_name='JDB' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as jdbh5,\n" +
                "       IFNULL(sum(case when app_name='JDB' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as jdbApplyTime,\n" +

                "       IFNULL(sum(case when app_name='JQHH' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as jqhhios,\n" +
                "       IFNULL(sum(case when app_name='JQHH' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as jqhhandroid,\n" +
                "       IFNULL(sum(case when app_name='JQHH' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as jqhhh5,\n" +
                "       IFNULL(sum(case when app_name='JQHH' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as jqhhApplyTime,\n" +


                "       IFNULL(sum(case when app_name='DKH' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as dkhios,\n" +
                "       IFNULL(sum(case when app_name='DKH' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as dkhandroid,\n" +
                "       IFNULL(sum(case when app_name='DKH' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as dkhh5,\n" +
                "       IFNULL(sum(case when app_name='DKH' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as dkhApplyTime,\n" +

                "       IFNULL(sum(case when app_name='MDB' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as mdbios,\n" +
                "       IFNULL(sum(case when app_name='MDB' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as mdbandroid,\n" +
                "       IFNULL(sum(case when app_name='MDB' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as mdbbh5,\n" +
                "       IFNULL(sum(case when app_name='MDB' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as mdbApplyTime,\n" +

                "       IFNULL(sum(case when app_name='XED' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as xedios,\n" +
                "       IFNULL(sum(case when app_name='XED' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as xedandroid,\n" +
                "       IFNULL(sum(case when app_name='XED' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as xedh5,\n" +
                "       IFNULL(sum(case when app_name='XED' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as xedApplyTime,\n" +

                "       IFNULL(sum(case when app_name='JQS' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as jqsios,\n" +
                "       IFNULL(sum(case when app_name='JQS' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as jqsandroid,\n" +
                "       IFNULL(sum(case when app_name='JQS' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as jqsh5,\n" +
                "       IFNULL(sum(case when app_name='JQS' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as jqsApplyTime,\n" +

                "       IFNULL(sum(case when app_name='JDJQ' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as jdjqios,\n" +
                "       IFNULL(sum(case when app_name='JDJQ' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as jdjqandroid,\n" +
                "       IFNULL(sum(case when app_name='JDJQ' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as jdjqh5,\n" +
                "       IFNULL(sum(case when app_name='JDJQ' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as jdjqApplyTime,\n" +

                "       IFNULL(sum(case when app_name='BSYD' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as kjwios,\n" +
                "       IFNULL(sum(case when app_name='BSYD' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as kjwandroid,\n" +
                "       IFNULL(sum(case when app_name='BSYD' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as kjwh5,\n" +
                "       IFNULL(sum(case when app_name='BSYD' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as kjwApplyTime,\n" +

                "       IFNULL(sum(case when app_name='BJQS' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as dkssios,\n" +
                "       IFNULL(sum(case when app_name='BJQS' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as dkssandroid,\n" +
                "       IFNULL(sum(case when app_name='BJQS' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as dkssh5,\n" +
                "       IFNULL(sum(case when app_name='BJQS' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as dkssApplyTime,\n" +

                "       IFNULL(sum(case when app_name='BYDDK' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as jqfios,\n" +
                "       IFNULL(sum(case when app_name='BYDDK' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as jqfandroid,\n" +
                "       IFNULL(sum(case when app_name='BYDDK' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as jqf5,\n" +
                "       IFNULL(sum(case when app_name='BYDDK' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as jqfApplyTime,\n" +

                "       IFNULL(sum(case when app_name='BDKSS' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as dktios,\n" +
                "       IFNULL(sum(case when app_name='BDKSS' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as dktandroid,\n" +
                "       IFNULL(sum(case when app_name='BDKSS' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as dkth5,\n" +
                "       IFNULL(sum(case when app_name='BDKSS' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as dktApplyTime,\n" +

                "       IFNULL(sum(case when app_name='BHDB' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as hdbios,\n" +
                "       IFNULL(sum(case when app_name='BHDB' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as hdbandroid,\n" +
                "       IFNULL(sum(case when app_name='BHDB' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as hdbh5,\n" +
                "       IFNULL(sum(case when app_name='BHDB' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as hdbApplyTime,\n" +

                "       IFNULL(sum(case when app_name='BDKZG' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as yjxios,\n" +
                "       IFNULL(sum(case when app_name='BDKZG' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as yjxandroid,\n" +
                "       IFNULL(sum(case when app_name='BDKZG' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as yjxh5,\n" +
                "       IFNULL(sum(case when app_name='BDKZG' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as yjxApplyTime,\n" +

                "       IFNULL(sum(case when app_name='BJJK' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as bjjkios,\n" +
                "       IFNULL(sum(case when app_name='BJJK' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as bjjkandroid,\n" +
                "       IFNULL(sum(case when app_name='BJJK' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as bjjkh5,\n" +
                "       IFNULL(sum(case when app_name='BJJK' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as bjjkApplyTime,\n" +

                "       IFNULL(sum(case when app_name='BJQW' and type=0 and channel_no ='IOS' then 1 else 0 end),0) as bjqwios,\n" +
                "       IFNULL(sum(case when app_name='BJQW' and type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as bjqwandroid,\n" +
                "       IFNULL(sum(case when app_name='BJQW' and type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as bjqwh5,\n" +
                "       IFNULL(sum(case when app_name='BJQW' and type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as bjqwApplyTime,\n" +

                "       IFNULL(sum(case when type=0 and channel_no ='IOS' then 1 else 0 end),0) as ios,\n" +
                "       IFNULL(sum(case when type=0 and channel_no in (select code from cw_app_market where code != 'IOS') then 1 else 0 end),0) as android,\n" +
                "       IFNULL(sum(case when type=0 and channel_no in (select code from cw_channel)  then 1 else 0 end),0) as h5,\n" +
                "       IFNULL(sum(case when type=2 and channel_no in (select code from cw_app_market union all select code from cw_channel)  then 1 else 0 end),0) as applyTime\n" ;

        return sql;
    }


    /**
     * 构造应用市场报表sql
     * @return
     */
    private String createMarketSqlHead(){
        String sql ="   IFNUll(sum(case when app_name='MBD' and channel_no ='huawei' then 1 else 0 end),0) as mbdHuawei,\n" +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = 'xiaomi' then 1 else 0 end),0) as mbdXiaomi,\n" +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = 'vivo'  then 1 else 0 end),0) as mbdVivo," +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = 'oppo'  then 1 else 0 end),0) as mbdOppo," +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = '360'  then 1 else 0 end),0) as mbd360," +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as mbdYyb, " +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = 'IOS'  then 1 else 0 end),0) as mbdApple, " +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = 'meizu'  then 1 else 0 end),0) as mbdMeizu, " +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = 'wandoujia'  then 1 else 0 end),0) as mbdWandoujia, " +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = 'baidu'  then 1 else 0 end),0) as mbdBaidu, " +
                "       IFNUll(sum(case when app_name='MBD' and channel_no = 'Samsung'  then 1 else 0 end),0) as mbdSamsung, " +
                "       IFNUll(sum(case when app_name='MBD' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as mbdOther, " +

                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no ='huawei' then 1 else 0 end),0) as xsdHuawei,\n" +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = 'xiaomi' then 1 else 0 end),0) as xsdXiaomi,\n" +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = 'vivo'  then 1 else 0 end),0) as xsdVivo," +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = 'oppo'  then 1 else 0 end),0) as xsdOppo," +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = '360'  then 1 else 0 end),0) as xsd360," +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = 'yingyongbao'  then 1 else 0 end),0) as xsdYyb, " +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = 'IOS'  then 1 else 0 end),0) as xsdApple, " +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = 'meizu'  then 1 else 0 end),0) as xsdMeizu, " +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = 'wandoujia'  then 1 else 0 end),0) as xsdWandoujia, " +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = 'baidu'  then 1 else 0 end),0) as xsdBaidu, " +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no = 'Samsung'  then 1 else 0 end),0) as xsdSamsung, " +
                "       IFNUll(sum(case when app_name in ('XSD','DKB') and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as xsdOther, " +

                "       IFNUll(sum(case when app_name='JQW' and channel_no ='huawei' then 1 else 0 end),0) as jqwHuawei,\n" +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = 'xiaomi' then 1 else 0 end),0) as jqwXiaomi,\n" +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = 'vivo'  then 1 else 0 end),0) as jqwVivo," +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = 'oppo'  then 1 else 0 end),0) as jqwOppo," +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = '360'  then 1 else 0 end),0) as jqw360," +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as jqwYyb, " +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = 'IOS'  then 1 else 0 end),0) as jqwApple, " +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = 'meizu'  then 1 else 0 end),0) as jqwMeizu, " +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = 'wandoujia'  then 1 else 0 end),0) as jqwWandoujia, " +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = 'baidu'  then 1 else 0 end),0) as jqwBaidu, " +
                "       IFNUll(sum(case when app_name='JQW' and channel_no = 'Samsung'  then 1 else 0 end),0) as jqwSamsung, " +
                "       IFNUll(sum(case when app_name='JQW' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as jqwOther, " +

                "       IFNUll(sum(case when app_name='JDS' and channel_no ='huawei' then 1 else 0 end),0) as jdsHuawei,\n" +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = 'xiaomi' then 1 else 0 end),0) as jdsXiaomi,\n" +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = 'vivo'  then 1 else 0 end),0) as jdsVivo," +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = 'oppo'  then 1 else 0 end),0) as jdsOppo," +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = '360'  then 1 else 0 end),0) as jds360," +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as jdsYyb, " +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = 'IOS'  then 1 else 0 end),0) as jdsApple, " +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = 'meizu'  then 1 else 0 end),0) as jdsMeizu, " +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = 'wandoujia'  then 1 else 0 end),0) as jdsWandoujia, " +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = 'baidu'  then 1 else 0 end),0) as jdsBaidu, " +
                "       IFNUll(sum(case when app_name='JDS' and channel_no = 'Samsung'  then 1 else 0 end),0) as jdsSamsung, " +
                "       IFNUll(sum(case when app_name='JDS' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as jdsOther, " +


                "       IFNUll(sum(case when app_name='DSQB' and channel_no ='huawei' then 1 else 0 end),0) as dsqbHuawei,\n" +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = 'xiaomi' then 1 else 0 end),0) as dsqbXiaomi,\n" +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = 'vivo'  then 1 else 0 end),0) as dsqbVivo," +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = 'oppo'  then 1 else 0 end),0) as dsqbOppo," +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = '360'  then 1 else 0 end),0) as dsqb360," +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as dsqbYyb, " +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = 'IOS'  then 1 else 0 end),0) as dsqbApple, " +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = 'meizu'  then 1 else 0 end),0) as dsqbMeizu, " +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = 'wandoujia'  then 1 else 0 end),0) as dsqbWandoujia, " +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = 'baidu'  then 1 else 0 end),0) as dsqbBaidu, " +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no = 'Samsung'  then 1 else 0 end),0) as dsqbSamsung, " +
                "       IFNUll(sum(case when app_name='DSQB' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as dsqbOther, " +

                "       IFNUll(sum(case when app_name='DKQB' and channel_no ='huawei' then 1 else 0 end),0) as dkqbHuawei,\n" +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = 'xiaomi' then 1 else 0 end),0) as dkqbXiaomi,\n" +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = 'vivo'  then 1 else 0 end),0) as dkqbVivo," +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = 'oppo'  then 1 else 0 end),0) as dkqbOppo," +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = '360'  then 1 else 0 end),0) as dkqb360," +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as dkqbYyb, " +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = 'IOS'  then 1 else 0 end),0) as dkqbApple, " +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = 'meizu'  then 1 else 0 end),0) as dkqbMeizu, " +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = 'wandoujia'  then 1 else 0 end),0) as dkqbWandoujia, " +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = 'baidu'  then 1 else 0 end),0) as dkqbBaidu, " +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no = 'Samsung'  then 1 else 0 end),0) as dkqbSamsung, " +
                "       IFNUll(sum(case when app_name='DKQB' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as dkqbOther, " +

                "       IFNUll(sum(case when app_name='DKD' and channel_no ='huawei' then 1 else 0 end),0) as dkdHuawei,\n" +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = 'xiaomi' then 1 else 0 end),0) as dkdXiaomi,\n" +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = 'vivo'  then 1 else 0 end),0) as dkdVivo," +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = 'oppo'  then 1 else 0 end),0) as dkdOppo," +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = '360'  then 1 else 0 end),0) as dkd360," +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as dkdYyb, " +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = 'IOS'  then 1 else 0 end),0) as dkdApple, " +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = 'meizu'  then 1 else 0 end),0) as dkdMeizu, " +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = 'wandoujia'  then 1 else 0 end),0) as dkdWandoujia, " +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = 'baidu'  then 1 else 0 end),0) as dkdBaidu, " +
                "       IFNUll(sum(case when app_name='DKD' and channel_no = 'Samsung'  then 1 else 0 end),0) as dkdSamsung, " +
                "       IFNUll(sum(case when app_name='DKD' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as dkdOther, " +

                "       IFNUll(sum(case when app_name='PXQB' and channel_no ='huawei' then 1 else 0 end),0) as pxqbHuawei,\n" +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = 'xiaomi' then 1 else 0 end),0) as pxqbXiaomi,\n" +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = 'vivo'  then 1 else 0 end),0) as pxqbVivo," +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = 'oppo'  then 1 else 0 end),0) as pxqbOppo," +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = '360'  then 1 else 0 end),0) as pxqb360," +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as pxqbYyb, " +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = 'IOS'  then 1 else 0 end),0) as pxqbApple, " +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = 'meizu'  then 1 else 0 end),0) as pxqbMeizu, " +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = 'wandoujia'  then 1 else 0 end),0) as pxqbWandoujia, " +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = 'baidu'  then 1 else 0 end),0) as pxqbBaidu, " +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no = 'Samsung'  then 1 else 0 end),0) as pxqbSamsung, " +
                "       IFNUll(sum(case when app_name='PXQB' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as pxqbOther, " +

                "       IFNUll(sum(case when app_name='DKBL' and channel_no ='huawei' then 1 else 0 end),0) as dkblHuawei,\n" +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = 'xiaomi' then 1 else 0 end),0) as dkblXiaomi,\n" +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = 'vivo'  then 1 else 0 end),0) as dkblVivo," +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = 'oppo'  then 1 else 0 end),0) as dkblOppo," +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = '360'  then 1 else 0 end),0) as dkbl360," +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as dkblYyb, " +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = 'IOS'  then 1 else 0 end),0) as dkblApple, " +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = 'meizu'  then 1 else 0 end),0) as dkblMeizu, " +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = 'wandoujia'  then 1 else 0 end),0) as dkblWandoujia, " +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = 'baidu'  then 1 else 0 end),0) as dkblBaidu, " +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no = 'Samsung'  then 1 else 0 end),0) as dkblSamsung, " +
                "       IFNUll(sum(case when app_name='DKBL' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as dkblOther, " +

                "       IFNUll(sum(case when app_name='LSQD' and channel_no ='huawei' then 1 else 0 end),0) as lsqdHuawei,\n" +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = 'xiaomi' then 1 else 0 end),0) as lsqdXiaomi,\n" +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = 'vivo'  then 1 else 0 end),0) as lsqdVivo," +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = 'oppo'  then 1 else 0 end),0) as lsqdOppo," +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = '360'  then 1 else 0 end),0) as lsqd360," +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as lsqdYyb, " +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = 'IOS'  then 1 else 0 end),0) as lsqdApple, " +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = 'meizu'  then 1 else 0 end),0) as lsqdMeizu, " +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = 'wandoujia'  then 1 else 0 end),0) as lsqdWandoujia, " +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = 'baidu'  then 1 else 0 end),0) as lsqdBaidu, " +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no = 'Samsung'  then 1 else 0 end),0) as lsqdSamsung, " +
                "       IFNUll(sum(case when app_name='LSQD' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as lsqdOther, " +

                "       IFNUll(sum(case when app_name='SYD' and channel_no ='huawei' then 1 else 0 end),0) as sydHuawei,\n" +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = 'xiaomi' then 1 else 0 end),0) as sydXiaomi,\n" +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = 'vivo'  then 1 else 0 end),0) as sydVivo," +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = 'oppo'  then 1 else 0 end),0) as sydOppo," +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = '360'  then 1 else 0 end),0) as syd360," +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as sydYyb, " +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = 'IOS'  then 1 else 0 end),0) as sydApple, " +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = 'meizu'  then 1 else 0 end),0) as sydMeizu, " +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = 'wandoujia'  then 1 else 0 end),0) as sydWandoujia, " +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = 'baidu'  then 1 else 0 end),0) as sydBaidu, " +
                "       IFNUll(sum(case when app_name='SYD' and channel_no = 'Samsung'  then 1 else 0 end),0) as sydSamsung, " +
                "       IFNUll(sum(case when app_name='SYD' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as sydOther, " +

                "       IFNUll(sum(case when app_name='SRD' and channel_no ='huawei' then 1 else 0 end),0) as srdHuawei,\n" +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = 'xiaomi' then 1 else 0 end),0) as srdXiaomi,\n" +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = 'vivo'  then 1 else 0 end),0) as srdVivo," +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = 'oppo'  then 1 else 0 end),0) as srdOppo," +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = '360'  then 1 else 0 end),0) as srd360," +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as srdYyb, " +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = 'IOS'  then 1 else 0 end),0) as srdApple, " +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = 'meizu'  then 1 else 0 end),0) as srdMeizu, " +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = 'wandoujia'  then 1 else 0 end),0) as srdWandoujia, " +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = 'baidu'  then 1 else 0 end),0) as srdBaidu, " +
                "       IFNUll(sum(case when app_name='SRD' and channel_no = 'Samsung'  then 1 else 0 end),0) as srdSamsung, " +
                "       IFNUll(sum(case when app_name='SRD' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as srdOther, " +

                "       IFNUll(sum(case when app_name='DQL' and channel_no ='huawei' then 1 else 0 end),0) as dqlHuawei,\n" +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = 'xiaomi' then 1 else 0 end),0) as dqlXiaomi,\n" +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = 'vivo'  then 1 else 0 end),0) as dqlVivo," +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = 'oppo'  then 1 else 0 end),0) as dqlOppo," +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = '360'  then 1 else 0 end),0) as dql360," +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as dqlYyb, " +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = 'IOS'  then 1 else 0 end),0) as dqlApple, " +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = 'meizu'  then 1 else 0 end),0) as dqlMeizu, " +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = 'wandoujia'  then 1 else 0 end),0) as dqlWandoujia, " +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = 'baidu'  then 1 else 0 end),0) as dqlBaidu, " +
                "       IFNUll(sum(case when app_name='DQL' and channel_no = 'Samsung'  then 1 else 0 end),0) as dqlSamsung, " +
                "       IFNUll(sum(case when app_name='DQL' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as dqlOther, " +

                "       IFNUll(sum(case when app_name='JDB' and channel_no ='huawei' then 1 else 0 end),0) as jdbHuawei,\n" +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = 'xiaomi' then 1 else 0 end),0) as jdbXiaomi,\n" +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = 'vivo'  then 1 else 0 end),0) as jdbVivo," +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = 'oppo'  then 1 else 0 end),0) as jdbOppo," +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = '360'  then 1 else 0 end),0) as jdb360," +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as jdbYyb, " +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = 'IOS'  then 1 else 0 end),0) as jdbApple, " +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = 'meizu'  then 1 else 0 end),0) as jdbMeizu, " +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = 'wandoujia'  then 1 else 0 end),0) as jdbWandoujia, " +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = 'baidu'  then 1 else 0 end),0) as jdbBaidu, " +
                "       IFNUll(sum(case when app_name='JDB' and channel_no = 'Samsung'  then 1 else 0 end),0) as jdbSamsung, " +
                "       IFNUll(sum(case when app_name='JDB' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as jdbOther, " +

                "       IFNUll(sum(case when app_name='DKZG' and channel_no ='huawei' then 1 else 0 end),0) as dkzgHuawei,\n" +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = 'xiaomi' then 1 else 0 end),0) as dkzgXiaomi,\n" +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = 'vivo'  then 1 else 0 end),0) as dkzgVivo," +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = 'oppo'  then 1 else 0 end),0) as dkzgOppo," +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = '360'  then 1 else 0 end),0) as dkzg360," +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as dkzgYyb, " +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = 'IOS'  then 1 else 0 end),0) as dkzgApple, " +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = 'meizu'  then 1 else 0 end),0) as dkzgMeizu, " +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = 'wandoujia'  then 1 else 0 end),0) as dkzgWandoujia, " +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = 'baidu'  then 1 else 0 end),0) as dkzgBaidu, " +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no = 'Samsung'  then 1 else 0 end),0) as dkzgSamsung, " +
                "       IFNUll(sum(case when app_name='DKZG' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as dkzgOther, " +

                "       IFNUll(sum(case when app_name='JJK' and channel_no ='huawei' then 1 else 0 end),0) as jjkHuawei,\n" +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = 'xiaomi' then 1 else 0 end),0) as jjkXiaomi,\n" +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = 'vivo'  then 1 else 0 end),0) as jjkVivo," +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = 'oppo'  then 1 else 0 end),0) as jjkOppo," +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = '360'  then 1 else 0 end),0) as jjk360," +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as jjkYyb, " +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = 'IOS'  then 1 else 0 end),0) as jjkApple, " +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = 'meizu'  then 1 else 0 end),0) as jjkMeizu, " +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = 'wandoujia'  then 1 else 0 end),0) as jjkWandoujia, " +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = 'baidu'  then 1 else 0 end),0) as jjkBaidu, " +
                "       IFNUll(sum(case when app_name='JJK' and channel_no = 'Samsung'  then 1 else 0 end),0) as jjkSamsung, " +
                "       IFNUll(sum(case when app_name='JJK' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as jjkOther, "+

                "       IFNUll(sum(case when app_name='JQHH' and channel_no ='huawei' then 1 else 0 end),0) as jqhhHuawei,\n" +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = 'xiaomi' then 1 else 0 end),0) as jqhhXiaomi,\n" +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = 'vivo'  then 1 else 0 end),0) as jqhhVivo," +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = 'oppo'  then 1 else 0 end),0) as jqhhOppo," +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = '360'  then 1 else 0 end),0) as jqhh360," +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as jqhhYyb, " +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = 'IOS'  then 1 else 0 end),0) as jqhhApple, " +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = 'meizu'  then 1 else 0 end),0) as jqhhMeizu, " +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = 'wandoujia'  then 1 else 0 end),0) as jqhhWandoujia, " +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = 'baidu'  then 1 else 0 end),0) as jqhhBaidu, " +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no = 'Samsung'  then 1 else 0 end),0) as jqhhSamsung, " +
                "       IFNUll(sum(case when app_name='JQHH' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as jqhhOther, "+

                "       IFNUll(sum(case when app_name='DKH' and channel_no ='huawei' then 1 else 0 end),0) as dkhHuawei,\n" +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = 'xiaomi' then 1 else 0 end),0) as dkhXiaomi,\n" +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = 'vivo'  then 1 else 0 end),0) as dkhVivo," +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = 'oppo'  then 1 else 0 end),0) as dkhOppo," +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = '360'  then 1 else 0 end),0) as dkh360," +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as dkhYyb, " +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = 'IOS'  then 1 else 0 end),0) as dkhApple, " +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = 'meizu'  then 1 else 0 end),0) as dkhMeizu, " +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = 'wandoujia'  then 1 else 0 end),0) as dkhWandoujia, " +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = 'baidu'  then 1 else 0 end),0) as dkhBaidu, " +
                "       IFNUll(sum(case when app_name='DKH' and channel_no = 'Samsung'  then 1 else 0 end),0) as dkhSamsung, " +
                "       IFNUll(sum(case when app_name='DKH' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as dkhOther, "+

                "       IFNUll(sum(case when app_name='MDB' and channel_no ='huawei' then 1 else 0 end),0) as mdbHuawei,\n" +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = 'xiaomi' then 1 else 0 end),0) as mdbXiaomi,\n" +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = 'vivo'  then 1 else 0 end),0) as mdbVivo," +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = 'oppo'  then 1 else 0 end),0) as mdbOppo," +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = '360'  then 1 else 0 end),0) as mdb360," +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as mdbYyb, " +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = 'IOS'  then 1 else 0 end),0) as mdbApple, " +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = 'meizu'  then 1 else 0 end),0) as mdbMeizu, " +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = 'wandoujia'  then 1 else 0 end),0) as mdbWandoujia, " +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = 'baidu'  then 1 else 0 end),0) as mdbBaidu, " +
                "       IFNUll(sum(case when app_name='MDB' and channel_no = 'Samsung'  then 1 else 0 end),0) as mdbSamsung, " +
                "       IFNUll(sum(case when app_name='MDB' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as mdbOther, " +

                "       IFNUll(sum(case when app_name='XED' and channel_no ='huawei' then 1 else 0 end),0) as xedHuawei,\n" +
                "       IFNUll(sum(case when app_name='XED' and channel_no = 'xiaomi' then 1 else 0 end),0) as xedXiaomi,\n" +
                "       IFNUll(sum(case when app_name='XED' and channel_no = 'vivo'  then 1 else 0 end),0) as xedVivo," +
                "       IFNUll(sum(case when app_name='XED' and channel_no = 'oppo'  then 1 else 0 end),0) as xedOppo," +
                "       IFNUll(sum(case when app_name='XED' and channel_no = '360'  then 1 else 0 end),0) as xed360," +
                "       IFNUll(sum(case when app_name='XED' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as xedYyb, " +
                "       IFNUll(sum(case when app_name='XED' and channel_no = 'IOS'  then 1 else 0 end),0) as xedApple, " +
                "       IFNUll(sum(case when app_name='XED' and channel_no = 'meizu'  then 1 else 0 end),0) as xedMeizu, " +
                "       IFNUll(sum(case when app_name='XED' and channel_no = 'wandoujia'  then 1 else 0 end),0) as xedWandoujia, " +
                "       IFNUll(sum(case when app_name='XED' and channel_no = 'baidu'  then 1 else 0 end),0) as xedBaidu, " +
                "       IFNUll(sum(case when app_name='XED' and channel_no = 'Samsung'  then 1 else 0 end),0) as xedSamsung, " +
                "       IFNUll(sum(case when app_name='XED' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as xedOther, "+

                "       IFNUll(sum(case when app_name='JQS' and channel_no ='huawei' then 1 else 0 end),0) as jqsHuawei,\n" +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = 'xiaomi' then 1 else 0 end),0) as jqsXiaomi,\n" +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = 'vivo'  then 1 else 0 end),0) as jqsVivo," +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = 'oppo'  then 1 else 0 end),0) as jqsOppo," +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = '360'  then 1 else 0 end),0) as jqs360," +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as jqsYyb, " +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = 'IOS'  then 1 else 0 end),0) as jqsApple, " +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = 'meizu'  then 1 else 0 end),0) as jqsMeizu, " +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = 'wandoujia'  then 1 else 0 end),0) as jqsWandoujia, " +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = 'baidu'  then 1 else 0 end),0) as jqsBaidu, " +
                "       IFNUll(sum(case when app_name='JQS' and channel_no = 'Samsung'  then 1 else 0 end),0) as jqsSamsung, " +
                "       IFNUll(sum(case when app_name='JQS' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as jqsOther, "+

                "       IFNUll(sum(case when app_name='JDJQ' and channel_no ='huawei' then 1 else 0 end),0) as jdjqHuawei,\n" +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = 'xiaomi' then 1 else 0 end),0) as jdjqXiaomi,\n" +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = 'vivo'  then 1 else 0 end),0) as jdjqVivo," +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = 'oppo'  then 1 else 0 end),0) as jdjqOppo," +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = '360'  then 1 else 0 end),0) as jdjq360," +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as jdjqYyb, " +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = 'IOS'  then 1 else 0 end),0) as jdjqApple, " +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = 'meizu'  then 1 else 0 end),0) as jdjqMeizu, " +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = 'wandoujia'  then 1 else 0 end),0) as jdjqWandoujia, " +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = 'baidu'  then 1 else 0 end),0) as jdjqBaidu, " +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no = 'Samsung'  then 1 else 0 end),0) as jdjqSamsung, " +
                "       IFNUll(sum(case when app_name='JDJQ' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as jdjqOther, " +

                "       IFNUll(sum(case when app_name='BSYD' and channel_no ='huawei' then 1 else 0 end),0) as kjwHuawei,\n" +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = 'xiaomi' then 1 else 0 end),0) as kjwXiaomi,\n" +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = 'vivo'  then 1 else 0 end),0) as kjwVivo," +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = 'oppo'  then 1 else 0 end),0) as kjwOppo," +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = '360'  then 1 else 0 end),0) as kjw360," +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as kjwYyb, " +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = 'IOS'  then 1 else 0 end),0) as kjwApple, " +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = 'meizu'  then 1 else 0 end),0) as kjwMeizu, " +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = 'wandoujia'  then 1 else 0 end),0) as kjwWandoujia, " +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = 'baidu'  then 1 else 0 end),0) as kjwBaidu, " +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no = 'Samsung'  then 1 else 0 end),0) as kjwSamsung, " +
                "       IFNUll(sum(case when app_name='BSYD' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as kjwOther, "+

                "       IFNUll(sum(case when app_name='BJQS' and channel_no ='huawei' then 1 else 0 end),0) as dkssHuawei,\n" +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = 'xiaomi' then 1 else 0 end),0) as dkssXiaomi,\n" +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = 'vivo'  then 1 else 0 end),0) as dkssVivo," +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = 'oppo'  then 1 else 0 end),0) as dkssOppo," +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = '360'  then 1 else 0 end),0) as dkss360," +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as dkssYyb, " +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = 'IOS'  then 1 else 0 end),0) as dkssApple, " +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = 'meizu'  then 1 else 0 end),0) as dkssMeizu, " +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = 'wandoujia'  then 1 else 0 end),0) as dkssWandoujia, " +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = 'baidu'  then 1 else 0 end),0) as dkssBaidu, " +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no = 'Samsung'  then 1 else 0 end),0) as dkssSamsung, " +
                "       IFNUll(sum(case when app_name='BJQS' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as dkssOther, " +

                "       IFNUll(sum(case when app_name='BYDDK' and channel_no ='huawei' then 1 else 0 end),0) as jqfHuawei,\n" +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = 'xiaomi' then 1 else 0 end),0) as jqfXiaomi,\n" +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = 'vivo'  then 1 else 0 end),0) as jqfVivo," +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = 'oppo'  then 1 else 0 end),0) as jqfOppo," +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = '360'  then 1 else 0 end),0) as jqf360," +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as jqfYyb, " +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = 'IOS'  then 1 else 0 end),0) as jqfApple, " +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = 'meizu'  then 1 else 0 end),0) as jqfMeizu, " +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = 'wandoujia'  then 1 else 0 end),0) as jqfWandoujia, " +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = 'baidu'  then 1 else 0 end),0) as jqfBaidu, " +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no = 'Samsung'  then 1 else 0 end),0) as jqfSamsung, " +
                "       IFNUll(sum(case when app_name='BYDDK' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as jqfOther, "+

                "       IFNUll(sum(case when app_name='BDKSS' and channel_no ='huawei' then 1 else 0 end),0) as dktHuawei,\n" +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = 'xiaomi' then 1 else 0 end),0) as dktXiaomi,\n" +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = 'vivo'  then 1 else 0 end),0) as dktVivo," +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = 'oppo'  then 1 else 0 end),0) as dktOppo," +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = '360'  then 1 else 0 end),0) as dkt360," +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as dktYyb, " +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = 'IOS'  then 1 else 0 end),0) as dktApple, " +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = 'meizu'  then 1 else 0 end),0) as dktMeizu, " +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = 'wandoujia'  then 1 else 0 end),0) as dktWandoujia, " +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = 'baidu'  then 1 else 0 end),0) as dktBaidu, " +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no = 'Samsung'  then 1 else 0 end),0) as dktSamsung, " +
                "       IFNUll(sum(case when app_name='BDKSS' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as dktOther, "+

                "       IFNUll(sum(case when app_name='BHDB' and channel_no ='huawei' then 1 else 0 end),0) as hdbHuawei,\n" +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = 'xiaomi' then 1 else 0 end),0) as hdbXiaomi,\n" +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = 'vivo'  then 1 else 0 end),0) as hdbVivo," +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = 'oppo'  then 1 else 0 end),0) as hdbOppo," +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = '360'  then 1 else 0 end),0) as hdb360," +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as hdbYyb, " +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = 'IOS'  then 1 else 0 end),0) as hdbApple, " +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = 'meizu'  then 1 else 0 end),0) as hdbMeizu, " +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = 'wandoujia'  then 1 else 0 end),0) as hdbWandoujia, " +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = 'baidu'  then 1 else 0 end),0) as hdbBaidu, " +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no = 'Samsung'  then 1 else 0 end),0) as hdbSamsung, " +
                "       IFNUll(sum(case when app_name='BHDB' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as hdbOther, "+

                "       IFNUll(sum(case when app_name='BDKZG' and channel_no ='huawei' then 1 else 0 end),0) as yjxHuawei,\n" +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = 'xiaomi' then 1 else 0 end),0) as yjxXiaomi,\n" +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = 'vivo'  then 1 else 0 end),0) as yjxVivo," +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = 'oppo'  then 1 else 0 end),0) as yjxOppo," +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = '360'  then 1 else 0 end),0) as yjx360," +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as yjxYyb, " +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = 'IOS'  then 1 else 0 end),0) as yjxApple, " +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = 'meizu'  then 1 else 0 end),0) as yjxMeizu, " +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = 'wandoujia'  then 1 else 0 end),0) as yjxWandoujia, " +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = 'baidu'  then 1 else 0 end),0) as yjxBaidu, " +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no = 'Samsung'  then 1 else 0 end),0) as yjxSamsung, " +
                "       IFNUll(sum(case when app_name='BDKZG' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as yjxOther, "+

                "       IFNUll(sum(case when app_name='BJQW' and channel_no ='huawei' then 1 else 0 end),0) as bjqwHuawei,\n" +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = 'xiaomi' then 1 else 0 end),0) as bjqwXiaomi,\n" +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = 'vivo'  then 1 else 0 end),0) as bjqwVivo," +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = 'oppo'  then 1 else 0 end),0) as bjqwOppo," +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = '360'  then 1 else 0 end),0) as bjqw360," +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as bjqwYyb, " +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = 'IOS'  then 1 else 0 end),0) as bjqwApple, " +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = 'meizu'  then 1 else 0 end),0) as bjqwMeizu, " +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = 'wandoujia'  then 1 else 0 end),0) as bjqwWandoujia, " +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = 'baidu'  then 1 else 0 end),0) as bjqwBaidu, " +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no = 'Samsung'  then 1 else 0 end),0) as bjqwSamsung, " +
                "       IFNUll(sum(case when app_name='BJQW' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as bjqwOther, "+

                "       IFNUll(sum(case when app_name='BJJK' and channel_no ='huawei' then 1 else 0 end),0) as bjjkHuawei,\n" +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = 'xiaomi' then 1 else 0 end),0) as bjjkXiaomi,\n" +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = 'vivo'  then 1 else 0 end),0) as bjjkVivo," +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = 'oppo'  then 1 else 0 end),0) as bjjkOppo," +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = '360'  then 1 else 0 end),0) as bjjk360," +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = 'yingyongbao'  then 1 else 0 end),0) as bjjkYyb, " +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = 'IOS'  then 1 else 0 end),0) as bjjkApple, " +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = 'meizu'  then 1 else 0 end),0) as bjjkMeizu, " +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = 'wandoujia'  then 1 else 0 end),0) as bjjkWandoujia, " +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = 'baidu'  then 1 else 0 end),0) as bjjkBaidu, " +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no = 'Samsung'  then 1 else 0 end),0) as bjjkSamsung, " +
                "       IFNUll(sum(case when app_name='BJJK' and channel_no not in ('IOS','meizu','wandoujia','Samsung','baidu','huawei','xiaomi','vivo','oppo','360','yingyongbao')  then 1 else 0 end),0) as bjjkOther ";


        return sql;
    }

    /**
     * 应用市场数据汇总
     *
     * @param reportSearchDto
     * @return
     */
    public List<AppMarketDto> appMarketReport(ReportSearchDto reportSearchDto) {
        String marketSql= createMarketSqlHead();
        String sql = "select aa.* " +
                " from (select DATE_FORMAT(apply_date,'%Y-%m-%d') as day,\n" +marketSql+
                "  from cw_log t  " +
                " where 1=1 and type='0' and t.channel_no in (select code from cw_app_market)   ";

        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        sql += "   and t.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null)\n" +
                "group by DATE_FORMAT(apply_date,'%Y-%m-%d') ) aa  ";

        sql += " union all " +
                " select aa.* " +
                " from (select '合计' as day,\n" +marketSql+
                "  from cw_log t  \n" +
                " where 1=1 and type = '0'  and t.channel_no in (select code from cw_app_market)  ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "'";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d') ";
        }
        sql += "   and t.user_id  in (select id from pf_se_user b where b.type='user' and b.register_date is not null)\n" +
                " ) aa  " +
                "order by day desc \n";

        List<AppMarketDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppMarketDto.class));

        return homeDto;
    }

    /**
     * 统计申请及申请用户数
     *
     * @return
     */
    public List<HomeDto> merchartApply(String applyDateStr) {
        String sql = "select apply_date as channelNo,\n" +
                "       sum(case when type=2 then applyTime else 0 end) as applyTime, " +
                "       sum(case when type=2 then num else 0 end) as applyNum " +
                " from (select DATE_FORMAT(apply_date,'%d') as apply_date,channel_no,type,count(1) as applyTime,count(distinct user_id) as num  " +
                "         from cw_log where 1=1 ";
        if (applyDateStr != null && !"".equals(applyDateStr)) {
            sql += " and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(STR_TO_DATE('" + applyDateStr + "','%Y-%m-%d'),'%y-%m') ";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(NOW(),'%y-%m') ";
        }
        sql += " and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                " group by DATE_FORMAT(apply_date,'%d'),channel_no,type) aa ";
        sql += " group by apply_date order by apply_date ";

        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));

        return homeDto;
    }

    /**
     * 各商户对比图
     *
     * @param reportSearchDto
     * @return
     */
    public List<HomeDto> merchartChart(ReportSearchDto reportSearchDto) {
        String sql = "select * " +
                "       from (" +
                "     select name as merchantName,\n" +
                "             IFNULL(sum(applyTime),0) as applyTime, " +
                "             IFNULL(sum(num),0) as applyNum " +
                "        from cw_product a " +
                "        left JOIN (select product_id,channel_no,type,count(1) as applyTime,count(distinct user_id) as num  " +
                "           from cw_log where 1=1 and type=2 ";
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyEndDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + reportSearchDto.getApplyStartDate() + "' and '" + reportSearchDto.getApplyEndDate() + "' ";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(NOW(),'%y-%m-%d') ";
        }
        sql += "  and channel_no in (select code from cw_channel union all select code from cw_app_market)" +
                " and user_id in (select id from pf_se_user where type='user' and register_date is not null)" +
                " group by product_id,channel_no,type" +
                " ) b on a.id = b.product_id  ";

        sql += "  where channel != '重庆平讯数据服务有限公司' and is_valid=1 " +
                " group by name ) aa " +
                " order by applyTime desc ";

        List<HomeDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HomeDto.class));

        return homeDto;
    }

    /**
     * 查询单个商户月申请趋势图
     *
     * @param reportSearchDto
     * @return
     */
    public List<AppDevDto> merchantMonthChart(ReportSearchDto reportSearchDto) {

        String sql = "select apply_date as name,\n" +
                "         sum(applyTime) as applyTime, \n" +
                "         sum(num) as applyNum \n" +
                "   from (select DATE_FORMAT(apply_date,'%d') as apply_date,channel_no,type," +
                "              count(1) as applyTime," +
                "              count(distinct user_id) as num" +
                "        from cw_log " +
                "       where user_id in (select id from pf_se_user where type='user' " +
                "          and register_date is not null) and type='2' " +
                "";
        if (reportSearchDto.getApplyMonth() != null && !"".equals(reportSearchDto.getApplyMonth())) {
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m') = '" + reportSearchDto.getApplyMonth() + "'";
        } else {
            if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
                sql += "  and DATE_FORMAT(apply_date,'%Y-%m') between '" + reportSearchDto.getApplyStartDate() + "' and  '" + reportSearchDto.getApplyEndDate() + "'";
            } else {
                sql += "  and DATE_FORMAT(apply_date,'%y-%m') = DATE_FORMAT(now(),'%y-%m') ";
            }
        }

        if (reportSearchDto.getProductId() != null && !"999".equals(reportSearchDto.getProductId())) {
            sql += "  and product_id = '" + reportSearchDto.getProductId() + "'";
        }
        sql += "  group by DATE_FORMAT(apply_date,'%d'),channel_no,type ) b " +
                "  where 1=1 ";
        sql += " group by apply_date \n" +
                " order by apply_date ";

        List<AppDevDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));

        return homeDto;
    }

    /**
     * 留存用户统计
     *
     * @param reportSearchDto
     * @return
     */
    public List<AppDevDto> retainedUserTotal(ReportSearchDto reportSearchDto) {
        String sql = " select aaa.id,aaa.name as appName," +
                "              IFNULL(sum(bbb.applyTime),0) as applyTime," +
                "              IFNULL(sum(bbb.applyNum),0) as applyNum " +
                " from cw_app aaa left join ( " +
                " select aa.app_name,sum(aa.applyTime) as applyTime,count(1) as applyNum " +
                "  from (SELECT app_name,b.user_id as userId, " +
                "        sum(1) as applyTime       " +
                "  FROM cw_log b  " +
                " where channel_no in (select code from cw_channel union all select code from cw_app_market) " +
                "   and b.product_id in (select id from cw_product where channel != '重庆平讯数据服务有限公司') " +
                "   and b.user_id in (select id from pf_se_user where type='user' and register_date is not null) " +
                "   and type=2 ";
        if (reportSearchDto.getAppName() != null && !"".equals(reportSearchDto.getAppName())) {
            sql += "  and app_mame = '" + reportSearchDto.getAppName() + "' ";
        }
        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') = '" + reportSearchDto.getApplyStartDate() + "'";
        } else {
            sql += "  and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }

        sql += " group by app_name,b.user_id)  aa ";

        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " inner JOIN (select id from pf_se_user where DATE_FORMAT(register_date,'%Y-%m-%d')<'" + reportSearchDto.getApplyStartDate() + "' ) bb ";
        } else {
            sql += " inner JOIN (select id from pf_se_user where DATE_FORMAT(register_date,'%Y-%m-%d')<  DATE_FORMAT(now(),'%Y-%m-%d')) bb ";
        }
        sql += "   on aa.userId = bb.id ) bbb " +
                " on aaa.code = bbb.app_name ";

        List<AppDevDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));

        return homeDto;
    }

    /**
     * 查询安卓、苹果、信息流发展数据
     * @param reportSearchDto
     * @return
     */
    public List<AppDevDto> appMarketTotal(ReportSearchDto reportSearchDto){
        String sql = " select '苹果' appName,\n" +
                "       sum(case when type=0 then applyTime else 0 end) as devUser,\n" +
                "       sum(case when type=2 then applyTime else 0 end) as applyTime,\n" +
                "       sum(case when type=2 then applyNum else 0 end) as applyNum\n" +
                " from \n" +
                "(\n" +
                "select type,count(1) as applyTime,count(DISTINCT user_id) as applyNum \n" +
                "  from cw_log t\n" +
                " where channel_no='ios'  \n" +
                "   and user_id in (select id from pf_se_user where type='user' and register_date is not null)\n" ;

        if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
            sql += " and DATE_FORMAT(apply_date,'%Y-%m') =  '"+reportSearchDto.getApplyStartDate()+"' ";
        } else {
            sql += " and DATE_FORMAT(apply_date,'%y-%m') =  DATE_FORMAT(now(),'%y-%m') ";
        }
        sql +=  " group by type\n" +
                ") aa\n" +
                "union all\n" +
                "select '安卓' appName,\n" +
                "       sum(case when type=0 then applyTime else 0 end) as devUser,\n" +
                "       sum(case when type=2 then applyTime else 0 end) as applyTime,\n" +
                "       sum(case when type=2 then applyNum else 0 end) as applyNum\n" +
                " from \n" +
                "(\n" +
                "select type,count(1) as applyTime,count(DISTINCT user_id) as applyNum \n" +
                "  from cw_log t\n" +
                " where channel_no in (SELECT code from cw_app_market) \n" +
                "   and user_id in (select id from pf_se_user where type='user' and register_date is not null)\n" +
                "   and channel_no != 'ios'\n" ;
            if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
                sql += " and DATE_FORMAT(apply_date,'%Y-%m') =  '"+reportSearchDto.getApplyStartDate()+"' ";
            } else {
                sql += " and DATE_FORMAT(apply_date,'%y-%m') =  DATE_FORMAT(now(),'%y-%m') ";
            }
            sql +=  " group by type\n" +
                ") aa\n" +
                "union all\n" +
                "select '信息流' appName,\n" +
                "       sum(case when type=0 then applyTime else 0 end) as devUser,\n" +
                "       sum(case when type=2 then applyTime else 0 end) as applyTime,\n" +
                "       sum(case when type=2 then applyNum else 0 end) as applyNum\n" +
                " from \n" +
                "(\n" +
                "select type,count(1) as applyTime,count(DISTINCT user_id) as applyNum \n" +
                "  from cw_log t\n" +
                " where channel_no in (SELECT code from cw_channel) \n" +
                "   and user_id in (select id from pf_se_user where type='user' and register_date is not null)\n" ;
            if (reportSearchDto.getApplyStartDate() != null && !"".equals(reportSearchDto.getApplyStartDate())) {
                sql += " and DATE_FORMAT(apply_date,'%Y-%m') =  '"+reportSearchDto.getApplyStartDate()+"' ";
            } else {
                sql += " and DATE_FORMAT(apply_date,'%y-%m') =  DATE_FORMAT(now(),'%y-%m')  ";
            }
            sql +=  " group by type\n" +
                    ") aa;";

        List<AppDevDto> homeDto = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));

        return homeDto;
    }

    /**
     * 产品统计
     * @param productSearchDto
     * @return
     */
    public List<ProductListDto> productCount(ProductSearchDto productSearchDto){
        String sort="";
        String sortColumn="applyNum";
        if("".equals(productSearchDto.getSortDesc())||"asc".equals(productSearchDto.getSortDesc()))
        {
            sort = "desc";
        }else{
            sort = "asc";
        }
        if(productSearchDto.getSortColumn()!=null){
            sortColumn = productSearchDto.getSortColumn();
        }
        String sql = " select '' as id," +
                "              '合计' as name,'' as unitPrice ,0 as showOrder," +
                "              '' as cooperationModel," +
                "              '' as isDownloadApp," +
                "              now() as onlineDate," +
                "              IFNULL(sum(b.clickNum),0) as clickNum," +
                "              IFNULL(sum(b.applyNum),0) as applyNum," +
                "              IFNULL(sum(c.recommendApplyNum),0) as recommendApplyNum," +
                "              '"+sort+"' as sortDesc " +
                "\t from  cw_product a\n" +
                " LEFT JOIN (select product_id as productId,\n" +
                "\t\t\t\t\t\t\t\t\t   count(1) as clickNum,  \n" +
                "\t\t\t\t\t\t\t\t\t   count(distinct user_id) as applyNum  \n" +
                "\t\t\t\t\t\tfrom cw_log a \n" +
                "\t\t\t\t\t where type=2   \n" ;
        if(productSearchDto.getApplyStartDate()!=null&&!"".equals(productSearchDto.getApplyStartDate())){
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + productSearchDto.getApplyStartDate() + "' and  '" + productSearchDto.getApplyEndDate() + "'";
        }else{
            sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        sql+=    "\t\t\t\t\t and user_id in (select id from pf_se_user where type='user' and register_date is not null)    \n" +
                "\t\t\t\t\t and channel_no in (select code from cw_channel union all select code from cw_app_market)\n" +
                "\t\tgroup by product_id ) b on a.id = b.productId\n" +
                " left join (select product_id,count(distinct user_id) as recommendApplyNum" +
                "      from cw_log " +
                "     where 1=1 " +
                "       and source_product_id is not null and type=2 ";
        if(productSearchDto.getApplyStartDate()!=null&&!"".equals(productSearchDto.getApplyStartDate())){
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + productSearchDto.getApplyStartDate() + "' and  '" + productSearchDto.getApplyEndDate() + "'";
        }else{
            sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        sql+="\t\t\t\t\t and user_id in (select id from pf_se_user where type='user' and register_date is not null)    \n" +
                "\t\t\t\t\t and channel_no in (select code from cw_channel union all select code from cw_app_market)\n" ;
        sql += " group by product_id) c on a.id = c.product_id "+
                " where a.channel!='重庆平讯数据服务有限公司'\n" ;

        if (productSearchDto.getCooperationModel() != null && !"".equals(productSearchDto.getCooperationModel())) {
            sql += " and cooperation_model =   '"+productSearchDto.getCooperationModel()+"' ";
        }
        //产品名称
        if (productSearchDto.getName() != null && !"".equals(productSearchDto.getName())) {
            sql += " and name like '%"+productSearchDto.getName()+"%' ";
        }

        if(!CPContext.getContext().getSeUserInfo().getUsername().equals("admin")&&
                !CPContext.getContext().getSeUserInfo().getUsername().equals("zhouyue")&&
                !CPContext.getContext().getSeUserInfo().getUsername().equals("yangxin")) {
            sql += " and operate_no = "+CPContext.getContext().getSeUserInfo().getId();
        }

        if (productSearchDto.getIsValid() != null && !"".equals(productSearchDto.getIsValid())) {
            if(productSearchDto.getIsValid()) {
                sql += " and is_valid = '1' ";
            }else{
                sql += " and is_valid = '0' ";
            }
        }
        if (productSearchDto.getBelongApp() != null && !"all".equals(productSearchDto.getBelongApp())) {
            sql += " and belong_app like   '%"+productSearchDto.getBelongApp()+"%' ";
        }
        sql+=" UNION All ";
         sql += " select a.id,a.name,a.unit_price as unitPrice ,a.show_order as showOrder," +
                "        a.cooperation_model as cooperationModel," +
                "        is_download_app as isDownloadApp," +
                "        online_date as onlineDate," +
                "        IFNULL(b.clickNum,0) as clickNum," +
                "        IFNULL(b.applyNum,0) as applyNum," +
                "        IFNULL(c.recommendApplyNum,0) as recommendApplyNum, " +
                "        '"+sort+"' as sortDesc " +
                "\t from  cw_product a\n" +
                " LEFT JOIN (select product_id as productId,\n" +
                "\t\t\t\t\t\t\t\t\t   count(1) as clickNum,  \n" +
                "\t\t\t\t\t\t\t\t\t   count(distinct user_id) as applyNum  \n" +
                "\t\t\t\t\t\tfrom cw_log a \n" +
                "\t\t\t\t\t where type=2   \n" ;
        if(productSearchDto.getApplyStartDate()!=null&&!"".equals(productSearchDto.getApplyStartDate())){
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + productSearchDto.getApplyStartDate() + "' and  '" + productSearchDto.getApplyEndDate() + "'";
        }else{
            sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        sql+=    "\t\t\t\t\t and user_id in (select id from pf_se_user where type='user' and register_date is not null)    \n" +
                "\t\t\t\t\t and channel_no in (select code from cw_channel union all select code from cw_app_market)\n" +
                "\t\tgroup by product_id ) b on a.id = b.productId \n" +
                " left join (select product_id,count(distinct user_id) as recommendApplyNum" +
                "      from cw_log " +
                "     where 1=1 " +
                "       and source_product_id is not null and type=2 ";
        if(productSearchDto.getApplyStartDate()!=null&&!"".equals(productSearchDto.getApplyStartDate())){
            sql += "  and DATE_FORMAT(apply_date,'%Y-%m-%d') between '" + productSearchDto.getApplyStartDate() + "' and  '" + productSearchDto.getApplyEndDate() + "'";
        }else{
            sql += " and DATE_FORMAT(apply_date,'%y-%m-%d') = DATE_FORMAT(now(),'%y-%m-%d') ";
        }
        sql+="\t\t\t\t\t and user_id in (select id from pf_se_user where type='user' and register_date is not null)    \n" +
                "\t\t\t\t\t and channel_no in (select code from cw_channel union all select code from cw_app_market)\n" ;
        sql += " group by product_id) c on a.id = c.product_id "+
               " where a.channel!='重庆平讯数据服务有限公司'\n" ;

        if (productSearchDto.getCooperationModel() != null && !"".equals(productSearchDto.getCooperationModel())) {
            sql += " and cooperation_model =   '"+productSearchDto.getCooperationModel()+"' ";
        }
        //产品名称
        if (productSearchDto.getName() != null && !"".equals(productSearchDto.getName())) {
            sql += " and name like '%"+productSearchDto.getName()+"%' ";
        }

        if(!CPContext.getContext().getSeUserInfo().getUsername().equals("admin")&&
                !CPContext.getContext().getSeUserInfo().getUsername().equals("zhouyue")&&
                !CPContext.getContext().getSeUserInfo().getUsername().equals("yangxin")) {
            //sql += " and operate_no = "+CPContext.getContext().getSeUserInfo().getId();
        }

        if (productSearchDto.getIsValid() != null && !"".equals(productSearchDto.getIsValid())) {
            if(productSearchDto.getIsValid()) {
                sql += " and is_valid = '1' ";
            }else{
                sql += " and is_valid = '0' ";
            }
        }
        if (productSearchDto.getBelongApp() != null && !"all".equals(productSearchDto.getBelongApp())) {
            sql += " and belong_app like   '%"+productSearchDto.getBelongApp()+"%' ";
        }

        sql +=   " order by  "+sortColumn+" "+sort ;

        List<ProductListDto> productListDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ProductListDto.class));

        return productListDtoList;
    }


    /**
     * 博达用户数据导入
     * @param userInfoDto
     * @return
     */
    public List<CwUserInfoDto> exportUserInfo(CwUserInfoDto userInfoDto){
        //按批次导入数据
        String sql = "select a.*,b.phone from cw_user_info a left join pf_se_user b on a.user_id = b.id" +
                "      where (a.address like '%重庆%' or a.address like '%成都%') and  a.batch_no is null limit "+userInfoDto.getExportNum();
        List<CwUserInfoDto> productListDtoList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(CwUserInfoDto.class));

        return productListDtoList;
    }

    /**
     * 用户区域统计
     * @return
     */
    public List<AppDevDto> devAreaCount(){
        String sql="select * " +
                "     from (select province as appName,sum(devNum) as devUser \n" +
                "             from (select a.*,IFNULL(b.province,a.areaName) as province\n" +
                "                     from (select IFNULL(SUBSTR(apply_area,1,LOCATE('/',apply_area)-1),'未知') as areaName,count(1) as devNum\n" +
                "                              from cw_log \n" +
                "                           where type=0 \n" +
                "                    group by IFNULL(SUBSTR(apply_area,1,LOCATE('/',apply_area)-1),'未知')\n" +
                ") a \n" +
                "LEFT JOIN (select aa.*,bb.province from cities aa LEFT JOIN provinces bb on aa.provinceid= bb.provinceid) b\n" +
                "on a.areaName like CONCAT('%',city,'%')\n" +
                ") aa\n" +
                " group by province ) aa " +
                " order by devUser desc ";
        List<AppDevDto> devDtosList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));
        return devDtosList;
    }


    /**
     * 修改已导入过去的数据标识
     * @param userInfoDto
     * @return
     */
    public Integer updateUserBatchNo(CwUserInfoDto userInfoDto){
        //按批次导入数据
        String sql = "update cw_user_info set batch_no='"+userInfoDto.getBatchNo()+"' where user_id in ("+userInfoDto.getIds()+") ";
        int result = jdbcTemplate.update(sql);

        return result;
    }

    /**
     * 排除重复接口
     * @param idfa
     * @return
     */
    public Map<String,Integer> removeRepeat(String idfa){
        Map<String,Integer> param = new HashMap();
        //按批次导入数据
        String sql = "select count(1) as devUser  from cw_log a where type=1 and channel_no ='ios' and device_number='"+idfa+"' ";
        List<AppDevDto> appDevDtos = jdbcTemplate.query(sql, new BeanPropertyRowMapper(AppDevDto.class));
        if(appDevDtos.size()>0){
            Integer total = appDevDtos.get(0).getDevUser();
            if(total>1){
                total =1;
            }
            param.put(idfa,total);
        }
        return param;
    }

    /**
     * 统计分发系数
     * @return
     */
    public List<HomeDto> countUserApplyRatio(ProductSearchDto searchDto){
        StringBuffer sql = new StringBuffer();

        sql.append("select '合计' channelNo,\n" +
                "          sum(todayDevUser) as todayRegisterNum,\n" +
                "          sum(devUser) as registerNum,\n" +
                "          sum(todayApplyNum) as todayApplyNum,\n" +
                "          sum(applyNum) as applyNum, \n" +
                "          round(sum(todayApplyNum)/sum(todayDevUser),2) as dayRatio,\n" +
                "          round(sum(applyNum)/sum(devUser),2) as distributeRatio \n" +
                "   from ( \n");
        sql.append(createProductApplyNum(searchDto,"M"));
        sql.append(" union all \n");
        sql.append(createProductApplyNum(searchDto,"D"));
        sql.append(" union all \n");
        sql.append(createDevUser(searchDto));
        sql.append(" ) a \n");
        sql.append(" union all \n");
        sql.append("select channelNo,\n" +
                "          sum(todayDevUser) as todayRegisterNum,\n" +
                "          sum(devUser) as registerNum,\n" +
                "          sum(todayApplyNum) as todayApplyNum,\n" +
                "          sum(applyNum) as applyNum, \n" +
                "          round(sum(todayApplyNum)/sum(todayDevUser),2) as dayRatio,\n" +
                "          round(sum(applyNum)/sum(devUser),2) as distributeRatio \n" +
                "   from ( "+createProductApplyNum(searchDto,"M"));
        sql.append(" union all \n");
        sql.append(createProductApplyNum(searchDto,"D"));
        sql.append(" union all \n");
        sql.append(createDevUser(searchDto));
        sql.append(" ) a \n");
        sql.append(" group by channelNo");

        List<HomeDto> devDtosList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(HomeDto.class));

        return devDtosList;
    }

    /**
     * 产品申请用户数统计
     * @return
     */
    private String createProductApplyNum(ProductSearchDto searchDto, String dateType){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select '安卓' as channelNo,0 as todayDevUser,0 as devUser," );
        if("M".equals(dateType)) {
            stringBuffer.append(" 0 as todayApplyNum,sum(num) as applyNum \n" );
        }else {
            stringBuffer.append(" sum(num) as todayApplyNum,0 as applyNum \n" );
        }
        stringBuffer.append(" from(\tselect product_id,\n" +
                    "              count(distinct user_id) as num\n" +
                    "\t\t\t\t from cw_log a INNER JOIN cw_app_market b on a.channel_no = b.code     \n" +
                "          INNER JOIN (select id from cw_product where channel!='重庆平讯数据服务有限公司' ) c on a.product_id = c.id \n" +
                "          INNER JOIN (select id from pf_se_user where type='user' and register_date is not null) d on a.user_id = d.id    \n" +
                    "\t\t\t\twhere 1=1 \n" +
                    "\t\t\t\t\tand type=2         \n" +
                    "\t\t\t\t\tand channel_no !='IOS'\n");
        stringBuffer.append(buildQueryCondition(searchDto,dateType));
        if(searchDto.getBelongApp()!=null && !"all".equals(searchDto.getBelongApp())){
            stringBuffer.append(" and app_name = '"+searchDto.getBelongApp()+"'");
        }
        stringBuffer.append("  group by product_id\n" +
                ") aa\n" +
                "union ALL\n" +
                "select '苹果' as channelNo,0 as todayDevUser,0 as devUser," );
        if("M".equals(dateType)) {
            stringBuffer.append(" 0 as todayApplyNum,sum(num) as applyNum \n" );
        }else {
            stringBuffer.append(" sum(num) as todayApplyNum,0 as applyNum \n" );
        }
        stringBuffer.append(" from(\t select product_id,count(distinct user_id) as num\n" +
                "\t\t\t\t from cw_log a        \n" +
                "          INNER JOIN (select id from cw_product where channel!='重庆平讯数据服务有限公司' ) c on a.product_id = c.id \n" +
                "          INNER JOIN (select id from pf_se_user where type='user' and register_date is not null) d on a.user_id = d.id    \n" +
                "\t\t\t\twhere 1=1 \n" +
                "\t\t\t\t\tand type=2         \n" +
                "\t\t\t\t\tand channel_no = 'IOS'\n");
        stringBuffer.append(buildQueryCondition(searchDto,dateType));
        if(searchDto.getBelongApp()!=null && !"all".equals(searchDto.getBelongApp())){
            stringBuffer.append(" and app_name = '"+searchDto.getBelongApp()+"'");
        }
        stringBuffer.append("  group by product_id\n" +
                " ) aa\n" +
                " union ALL\n" +
                " select '渠道' as channelNo,0 as todayDevUser,0 as devUser," );
        if("M".equals(dateType)) {
            stringBuffer.append(" 0 as todayApplyNum,sum(num) as applyNum \n" );
        }else {
            stringBuffer.append(" sum(num) as todayApplyNum,0 as applyNum \n" );
        }
        stringBuffer.append(" from (\t select product_id,count(distinct user_id) as num\n" +
                "\t\t from cw_log a  inner join cw_channel b on a.channel_no=b.code       \n" +
                "          INNER JOIN (select id from cw_product where channel!='重庆平讯数据服务有限公司' ) c on a.product_id = c.id \n" +
                "          INNER JOIN (select id from pf_se_user where type='user' and register_date is not null) d on a.user_id = d.id    \n" +
                "\t\t where 1=1 \n" +
                "\t\t\tand type=2         \n");
        stringBuffer.append(buildQueryCondition(searchDto,dateType));
        if(searchDto.getBelongApp()!=null && !"all".equals(searchDto.getBelongApp())){
            stringBuffer.append(" and app_name = '"+searchDto.getBelongApp()+"'");
        }
        stringBuffer.append("group by product_id ) aa ");

        return stringBuffer.toString();
    }

    private StringBuffer buildQueryCondition(ProductSearchDto searchDto,String dateType){
        StringBuffer stringBuffer = new StringBuffer();
        if("M".equals(dateType)) {
            if(searchDto.getApplyStartDate()!=null&&!"".equals(searchDto.getApplyStartDate())){
                stringBuffer.append("\t\t\t\t\tand  DATE_FORMAT(apply_date,'%Y-%m') = DATE_FORMAT(STR_TO_DATE('"+searchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m')  \n");
            }else {
                stringBuffer.append("\t\t\t\t\tand  DATE_FORMAT(apply_date,'%Y-%m') = DATE_FORMAT(now(),'%Y-%m')  \n");
            }
        }else{
            if(searchDto.getApplyStartDate()!=null&&!"".equals(searchDto.getApplyStartDate())) {
                stringBuffer.append("\t\t\t\t\tand  DATE_FORMAT(apply_date,'%Y-%m-%d') = '"+searchDto.getApplyStartDate()+"'  \n");
            }else{
                stringBuffer.append("\t\t\t\t\tand  DATE_FORMAT(apply_date,'%Y-%m-%d') = DATE_FORMAT(now(),'%Y-%m-%d')  \n");
            }
        }
        return stringBuffer;
    }
    /**
     * 统计发展用户
     * @return
     */
    private String createDevUser(ProductSearchDto searchDto){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select '安卓' as channelNo,sum(todayDevUser) as todayDevUser,sum(num) as devUser,0 todayApplyNum,0 as applyNum\n" +
                "from(\t select " );
        if(searchDto.getApplyStartDate()!=null&&!"".equals(searchDto.getApplyStartDate())) {
            stringBuffer.append("    sum(case when DATE_FORMAT(apply_date,'%Y-%m-%d')= '"+searchDto.getApplyStartDate()+"' then 1 else 0 end) as todayDevUser,\n");
        }else{
            stringBuffer.append("    sum(case when DATE_FORMAT(apply_date,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d') then 1 else 0 end) as todayDevUser,\n" );
        }
        stringBuffer.append("  sum(1) as num\n" +
                "\t\t\t\t from cw_log a  inner join cw_app_market b on a.channel_no=b.code  \n" +
                "          INNER JOIN (select id from pf_se_user where type='user' and register_date is not null) d on a.user_id = d.id    \n" +
                "\t\t\t\twhere 1=1 \n" +
                "\t\t\t\t\tand type=0   \n" +
                "          and channel_no!='IOS'\n");
            if(searchDto.getBelongApp()!=null && !"all".equals(searchDto.getBelongApp())){
               stringBuffer.append(" and app_name = '"+searchDto.getBelongApp()+"'");
            }
            if(searchDto.getApplyStartDate()!=null&&!"".equals(searchDto.getApplyStartDate())) {
                stringBuffer.append("\t\t\t\t\tand DATE_FORMAT(apply_date,'%Y-%m') =  " +
                        " DATE_FORMAT(STR_TO_DATE('"+searchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m')  " +
                        " and DATE_FORMAT(apply_date,'%Y-%m-%d')<='"+searchDto.getApplyStartDate()+"' \n");
            }else {
                stringBuffer.append("\t\t\t\t\tand DATE_FORMAT(apply_date,'%Y-%m') =  DATE_FORMAT(now(),'%Y-%m')  \n");
            }
        stringBuffer.append("  group by product_id\n" +
                ") aa\n" +
                "union ALL\n" +
                "select '苹果' as channelNo,sum(todayDevUser) as todayDevUser,sum(num) as devUser,0 todayApplyNum,0 as applyNum\n" +
                "from(\t select ");
        if(searchDto.getApplyStartDate()!=null&&!"".equals(searchDto.getApplyStartDate())) {
            stringBuffer.append("    sum(case when DATE_FORMAT(apply_date,'%Y-%m-%d')= '"+searchDto.getApplyStartDate()+"' then 1 else 0 end) as todayDevUser,\n");
        }else{
            stringBuffer.append("    sum(case when DATE_FORMAT(apply_date,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d') then 1 else 0 end) as todayDevUser,\n" );
        }
        stringBuffer.append("        sum(1) as num\n" +
                "\t\t\t\t from cw_log a        \n" +
                "          INNER JOIN (select id from pf_se_user where type='user' and register_date is not null) d on a.user_id = d.id    \n" +
                "\t\t\t\twhere 1=1 \n" +
                "\t\t\t\t\tand a.type=0\n" +
                "          and a.channel_no='IOS'\n" );
                if(searchDto.getBelongApp()!=null && !"all".equals(searchDto.getBelongApp())){
                    stringBuffer.append(" and app_name = '"+searchDto.getBelongApp()+"'");
                }
        if(searchDto.getApplyStartDate()!=null&&!"".equals(searchDto.getApplyStartDate())) {
            stringBuffer.append("\t\t\t\t\tand DATE_FORMAT(apply_date,'%Y-%m') =  " +
                    " DATE_FORMAT(STR_TO_DATE('"+searchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m')  " +
                    " and DATE_FORMAT(apply_date,'%Y-%m-%d')<='"+searchDto.getApplyStartDate()+"' \n");
        }else {
            stringBuffer.append("\t\t\t\t\tand DATE_FORMAT(apply_date,'%Y-%m') =  DATE_FORMAT(now(),'%Y-%m')  \n");
        }
               stringBuffer.append(") aa\n" +
                "union ALL\n" +
                "select '渠道' as channelNo,sum(todayDevUser) as todayDevUser,sum(num) as devUser,0 todayApplyNum,0 as applyNum \n" +
                "from(\t select " );
        if(searchDto.getApplyStartDate()!=null&&!"".equals(searchDto.getApplyStartDate())) {
            stringBuffer.append("    sum(case when DATE_FORMAT(apply_date,'%Y-%m-%d')= '"+searchDto.getApplyStartDate()+"' then 1 else 0 end) as todayDevUser,\n");
        }else{
            stringBuffer.append("    sum(case when DATE_FORMAT(apply_date,'%Y-%m-%d')=DATE_FORMAT(now(),'%Y-%m-%d') then 1 else 0 end) as todayDevUser,\n" );
        }
        stringBuffer.append("        sum(1) as num\n" +
                "\t\t\t\t from cw_log a   inner join cw_channel b on a.channel_no = b.code    \n" +
                "          INNER JOIN (select id from pf_se_user where type='user' and register_date is not null) d on a.user_id = d.id    \n" +
                "\t\t\t\twhere 1=1 \n" +
                "\t\t\t\t\tand type=0\n");
        if(searchDto.getBelongApp()!=null && !"all".equals(searchDto.getBelongApp())){
            stringBuffer.append(" and app_name = '"+searchDto.getBelongApp()+"'");
        }
        if(searchDto.getApplyStartDate()!=null&&!"".equals(searchDto.getApplyStartDate())) {
            stringBuffer.append("\t\t\t\t\tand DATE_FORMAT(apply_date,'%Y-%m') =  " +
                    " DATE_FORMAT(STR_TO_DATE('"+searchDto.getApplyStartDate()+"','%Y-%m-%d'),'%Y-%m')  " +
                    " and DATE_FORMAT(apply_date,'%Y-%m-%d')<='"+searchDto.getApplyStartDate()+"' \n");
        }else {
            stringBuffer.append("\t\t\t\t\tand DATE_FORMAT(apply_date,'%Y-%m') =  DATE_FORMAT(now(),'%Y-%m')  \n");
        }
        stringBuffer.append(") aa ");
        return stringBuffer.toString();
    }

}
