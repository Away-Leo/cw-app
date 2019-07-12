package com.cw.biz.home.domain.service;


import com.cw.biz.home.app.dto.*;
import com.cw.biz.home.domain.dao.HomeDao;
import com.cw.biz.product.app.dto.ProductListDto;
import com.cw.biz.product.app.dto.ProductSearchDto;
import com.cw.biz.user.app.dto.CwUserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * banner查询服务
 * Created by dujy on 2017-05-20.
 */
@Service
public class HomeDomainService {

    @Autowired
    private HomeDao homeDao;

    /**
     * 统计新增用户数
     * @return
     */
    public HomeDto findTotalUser(String applyDate)
    {
        HomeDto homeDto = homeDao.countUser(applyDate);
        return homeDto;
    }

    /**
     * app用户分布情况
     * @return
     */
    public List<HomeDto> getAppUser(String applyDate)
    {
        return homeDao.appDev(applyDate);
    }


    /**
     * 日新增用户
     * @return
     */
    public List<HomeDto> dayDev(ReportSearchDto reportSearchDto)
    {
        List<HomeDto> homeDtoList = homeDao.dayDevUser(reportSearchDto);
        return homeDtoList;
    }

    /**
     * 统计应用市场数据
     * @return
     */
    public List<HomeDto> findApplicationStoreCount(String appName,String cycle,String applyDate)
    {
        List<HomeDto> homeDto = homeDao.countChannelUser(appName,cycle,applyDate);
        return homeDto;
    }

    /**
     * 查询统计数据
     * @param type
     * @return
     */
    public List<HomeDto> findApplicationStoreListCount(Integer type,String appName,String cycle,String applyDate)
    {
        List<HomeDto> homeDto = homeDao.countChannelList(type,appName,cycle,applyDate);
        return homeDto;
    }

    /**
     * 来量统计
     * @param appName
     * @param cycle
     * @return
     */
    public List<AppDevDto> devUserLineChart(String appName,String cycle,String applyDate)
    {
        List<AppDevDto> homeDto = homeDao.devUserLineChart(appName,cycle,applyDate);
        return homeDto;
    }



    /**
     * 渠道收入支出
     * @return
     */
    public List<HomeDto> channelInOrOutcomeTotal(ReportSearchDto reportSearchDto)
    {
        List<HomeDto> homeDto = homeDao.channelInOrOutcomeTotal(reportSearchDto);
        return homeDto;
    }

    /**
     * 用户月报
     * @return
     */
    public List<HomeDto> monthDev(ReportSearchDto reportSearchDto)
    {
        List<HomeDto> homeDtoList = homeDao.monthDevUser(reportSearchDto);
        return homeDtoList;
    }

    /**
     * 商户收入
     * @return
     */
    public List<HomeDto> merchantIncome(ReportSearchDto reportSearchDto)
    {
        List<HomeDto> homeDtoList = homeDao.merchantIncome(reportSearchDto);
        return homeDtoList;
    }

    /**
     * 新增用户数
     * @return
     */
    public List<AppDevDto> appDevUser(ReportSearchDto reportSearchDto)
    {
        List<AppDevDto> homeDtoList = homeDao.appDevUser(reportSearchDto);
        return homeDtoList;
    }

    /**
     * 每天时段新增用户分布
     * @return
     */
    public List<AppDevDto> devUserHour(ReportSearchDto reportSearchDto)
    {
        List<AppDevDto> homeDtoList = homeDao.devUserHour(reportSearchDto);
        return homeDtoList;
    }

    /**
     * 商户月发展趋势图
     * @param reportSearchDto
     * @return
     */
    public List<AppDevDto> merchantMonthChart(ReportSearchDto reportSearchDto)
    {
        List<AppDevDto> homeDtoList = homeDao.merchantMonthChart(reportSearchDto);
        return homeDtoList;
    }
    /**
     * 统计渠道数据
     * @param reportSearchDto
     * @return
     */
    public List<HomeDto> getChannelData(ReportSearchDto reportSearchDto)
    {
        return homeDao.getChannelData(reportSearchDto);
    }

    /**
     * 运营统计报表
     * @param reportSearchDto
     * @return
     */
    public List<OperateDto> operateReport(ReportSearchDto reportSearchDto)
    {
        return homeDao.operateReport(reportSearchDto);
    }

    /**
     * 应用市场汇总
     * @param reportSearchDto
     * @return
     */
    public List<AppMarketDto> appMarketReport(ReportSearchDto reportSearchDto)
    {
        return homeDao.appMarketReport(reportSearchDto);
    }
    /**
     * 商户申请
     * @param applyDate
     * @return
     */
    public List<HomeDto> merchartApply(String applyDate)
    {
        return homeDao.merchartApply(applyDate);
    }

    /**
     * 各商户对比图
     * @param reportSearchDto
     * @return
     */
    public List<HomeDto> merchartChart(ReportSearchDto reportSearchDto)
    {
        return homeDao.merchartChart(reportSearchDto);
    }

    /**
     * 用户留存统计
     * @param reportSearchDto
     * @return
     */
    public List<AppDevDto> retainedUserTotal(ReportSearchDto reportSearchDto)
    {
        return homeDao.retainedUserTotal(reportSearchDto);
    }

    /**
     * 各应用市场数据统计
      * @param reportSearchDto
     * @return
     */
    public List<AppDevDto> appMarketTotal(ReportSearchDto reportSearchDto)
    {
        return homeDao.appMarketTotal(reportSearchDto);
    }

    /***
     * 产品统计
     * @param reportSearchDto
     * @return
     */
    public List<ProductListDto> productCount(ProductSearchDto reportSearchDto)
    {
        return homeDao.productCount(reportSearchDto);
    }

    /**
     * 查询需要导入数据
     * @param reportSearchDto
     * @return
     */
    public List<CwUserInfoDto> exportUserInfo(CwUserInfoDto reportSearchDto)
    {
        return homeDao.exportUserInfo(reportSearchDto);
    }

    /**
     * 修改已导入数据标识
     * @param reportSearchDto
     * @return
     */
    public Integer updateExportUserInfo(CwUserInfoDto reportSearchDto){
        return homeDao.updateUserBatchNo(reportSearchDto);
    }

    /**
     * 排除重复IDFA
     * @param idfa
     * @return
     */
    public Map<String,Integer> removeRepeat(String idfa){
        return homeDao.removeRepeat(idfa);
    }

    /**
     * 用户区域分布
     * @return
     */
    public List<AppDevDto> devAreaCount(){
        return homeDao.devAreaCount();
    }

    /**
     * 市场分发系数
     * @return
     */
    public List<HomeDto> countUserApplyRatio(ProductSearchDto searchDto){
        return homeDao.countUserApplyRatio(searchDto);
    }
}

