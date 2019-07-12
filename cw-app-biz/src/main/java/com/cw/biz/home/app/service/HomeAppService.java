package com.cw.biz.home.app.service;

import com.cw.biz.home.app.dto.*;
import com.cw.biz.home.domain.service.HomeDomainService;
import com.cw.biz.product.app.dto.ProductListDto;
import com.cw.biz.product.app.dto.ProductSearchDto;
import com.cw.biz.user.app.dto.CwUserInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * 后台首页统计数据服务
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class HomeAppService {

    @Autowired
    private HomeDomainService homeDomainService;

    /**
     * 统计用户数据
     * @return
     */
    public HomeDto findTotalData(String applyDate)
    {
        return homeDomainService.findTotalUser(applyDate);
    }

    /**
     * 各app用户分布
     * @return
     */
    public List<HomeDto> getAppUser(String applyDate)
    {
        return homeDomainService.getAppUser(applyDate);
    }

    /**
     * 用户日报
     * @return
     */
    public List<HomeDto> dayDev(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.dayDev(reportSearchDto);
    }

    /**
     * 统计应用市场数据
     * @return
     */
    public List<HomeDto> findApplicationStoreCount(String appName,String cycle,String applyDate)
    {
        return homeDomainService.findApplicationStoreCount(appName,cycle,applyDate);
    }

    /**
     * 统计应用市场数据
     * @return
     */
    public List<HomeDto> findApplicationStoreListCount(Integer type,String appName,String cycle,String applyDate)
    {
        return homeDomainService.findApplicationStoreListCount(type,appName,cycle,applyDate);
    }

    /**
     * 来量统计
     * @param appName
     * @param cycle
     * @return
     */
    public List<AppDevDto> devUserLineChart(String appName,String cycle,String applyDate)
    {
        return homeDomainService.devUserLineChart(appName,cycle,applyDate);
    }

    /**
     * 渠道收入支出统计
     * @return
     */
    public List<HomeDto> channelInOrOutcomeTotal(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.channelInOrOutcomeTotal(reportSearchDto);
    }

    /**
     * 用户日报
     * @return
     */
    public List<HomeDto> monthDev(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.monthDev(reportSearchDto);
    }

    /**
     * 商户收入
     * @return
     */
    public List<HomeDto> merchantIncome(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.merchantIncome(reportSearchDto);
    }

    /**
     * app分包用户数
     * @return
     */
    public List<AppDevDto> appDevUser(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.appDevUser(reportSearchDto);
    }

    /**
     * 每天按时段新增用户数
     * @return
     */
    public List<AppDevDto> devUserHour(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.devUserHour(reportSearchDto);
    }


    /**
     * 商户月发展趋势图
     * @param reportSearchDto
     * @return
     */
    public List<AppDevDto> merchantMonthChart(ReportSearchDto reportSearchDto)
    {
        List<AppDevDto> homeDtoList = homeDomainService.merchantMonthChart(reportSearchDto);
        return homeDtoList;
    }

    /**
     * 查询渠道数据
     * @param reportSearchDto
     * @return
     */
    public List<HomeDto> getChannelData(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.getChannelData(reportSearchDto);
    }

    /**
     * 运营报表
     * @param reportSearchDto
     * @return
     */
    public List<OperateDto> operateReport(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.operateReport(reportSearchDto);
    }

    /**
     * 应用市场数据汇总
     * @param reportSearchDto
     * @return
     */
    public List<AppMarketDto> appMarketReport(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.appMarketReport(reportSearchDto);
    }


    /**
     * 商户申请
     * @param applyDate
     * @return
     */
    public List<HomeDto> merchartApply(String applyDate)
    {
        return homeDomainService.merchartApply(applyDate);
    }

    /**
     * 商户对比趋势图
     * @param reportSearchDto
     * @return
     */
    public List<HomeDto> merchartChart(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.merchartChart(reportSearchDto);
    }

    /**
     * 留存用户分析
     * @param reportSearchDto
     * @return
     */
    public List<AppDevDto> retainedUserTotal(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.retainedUserTotal(reportSearchDto);
    }

    /**
     * 各应用市场数据统计
     * @param reportSearchDto
     * @return
     */
    public List<AppDevDto> appMarketTotal(ReportSearchDto reportSearchDto)
    {
        return homeDomainService.appMarketTotal(reportSearchDto);
    }

    /**
     * 各应用市场数据统计
     * @param reportSearchDto
     * @return
     */
    public List<ProductListDto> productCount(ProductSearchDto reportSearchDto)
    {
        return homeDomainService.productCount(reportSearchDto);
    }

    /**
     * 查询需要导入数据
     * @param reportSearchDto
     * @return
     */
    public List<CwUserInfoDto> exportUserInfo(CwUserInfoDto reportSearchDto)
    {
        return homeDomainService.exportUserInfo(reportSearchDto);
    }

    /**
     * 区域分布
     * @return
     */
    public List<AppDevDto> devAreaCount(){
        return homeDomainService.devAreaCount();
    }

    /**
     * 修改已导入数据标识
     * @param reportSearchDto
     * @return
     */
    public Integer updateExportUserInfo(CwUserInfoDto reportSearchDto){
        return homeDomainService.updateExportUserInfo(reportSearchDto);
    }

    /**
     * 排除重复IDFA
     * @param idfa
     * @return
     */
    public Map<String,Integer> removeRepeat(String idfa){
        return homeDomainService.removeRepeat(idfa);
    }

    /**
     * 市场分发系数
     * @return
     */
    public List<HomeDto> countUserApplyRatio(ProductSearchDto searchDto){
        return homeDomainService.countUserApplyRatio(searchDto);
    }
}
