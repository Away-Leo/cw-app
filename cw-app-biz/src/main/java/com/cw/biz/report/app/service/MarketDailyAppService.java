package com.cw.biz.report.app.service;

import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.biz.report.app.dto.SettleDto;
import com.cw.biz.report.domain.entity.MarketDaily;
import com.cw.biz.report.domain.service.MarketDailyDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品分类
 * Created by dujy on 2017-05-20.
 */
@Transactional
@Service
public class MarketDailyAppService {

    @Autowired
    private MarketDailyDomainService marketDailyDomainService;

    /**
     * 修改日报信息
     * @param marketDailyDto
     * @return
     */
    public Long update(MarketDailyDto marketDailyDto)
    {
        return marketDailyDomainService.update(marketDailyDto).getId();
    }


    /**
     * 日结数据回盘
     * @param marketDailyDto
     * @return
     */
    public Long updateDaily(MarketDailyDto marketDailyDto) {
        return marketDailyDomainService.updateDaily(marketDailyDto).getId();
    }

    /**
     * 查询日报数据
     * @return
     */
    public List<MarketDailyDto> findAll(ReportSearchDto dto)
    {
        List<MarketDailyDto> marketDailyList = marketDailyDomainService.findAll(dto);
        return marketDailyList;
    }

    /**
     * 查询月报数据
     * @return
     */
    public List<MarketDailyDto> getMarketMonth(ReportSearchDto dto){
        List<MarketDailyDto> marketDailyList = marketDailyDomainService.getMarketMonth(dto);
        return marketDailyList;
    }

    /**
     * 财务月度结算
     * @param dto
     * @return
     */
    public List<MarketDailyDto> getMarketMonthSettle(ReportSearchDto dto){
        List<MarketDailyDto> marketDailyList = marketDailyDomainService.getMarketMonthSettle(dto);
        return marketDailyList;
    }
    /**
     * 邮递快递信息
     * @param dto
     * @return
     */
    public List<MarketDailyDto> marketMonthList(ReportSearchDto dto){
        List<MarketDailyDto> marketDailyList = marketDailyDomainService.marketMonthList(dto);
        return marketDailyList;
    }

    /**
     * 查询商品每日回盘明细
     * @param dto
     * @return
     */
    public List<MarketDailyDto> marketDailyDetail(ReportSearchDto dto)
    {
        List<MarketDailyDto> marketDailyList = marketDailyDomainService.marketDailyDetail(dto);
        return marketDailyList;
    }


    /**
     * 收入回盘明细
     * @param dto
     * @return
     */
    public List<MarketDailyDto> incomeBackList(ReportSearchDto dto)
    {
        List<MarketDailyDto> marketDailyList = marketDailyDomainService.incomeBackList(dto);
        return marketDailyList;
    }

     /** 测试用例修改收入报表数据
     * @param dto
     * @return
             */
    public List<MarketDailyDto> marketDailyDetailTest(ReportSearchDto dto)
    {
        List<MarketDailyDto> marketDailyList = marketDailyDomainService.marketDailyDetailTest(dto);
        return marketDailyList;
    }


    /**
     * 修改回盘数据
     * @param marketDailyDto
     * @return
     */
    public Boolean updateCounteroffer(MarketDailyDto marketDailyDto)
    {
        return marketDailyDomainService.updateCounteroffer(marketDailyDto);
    }

    /**
     * 修改快递信息
     * @param marketDailyDto
     * @return
     */
    public Boolean postInfoUpdate(MarketDailyDto marketDailyDto){
        return marketDailyDomainService.postInfoUpdate(marketDailyDto);
    }
    /**
     * 商户明细回盘数据
     * @param marketDailyDto
     * @return
     */
    public Boolean updateMerchantIncome(MarketDailyDto marketDailyDto)
    {
        return marketDailyDomainService.updateMerchantIncome(marketDailyDto);
    }

    /**
     * 修改审核状态
     * @param marketDailyDto
     * @return
     */
    public Boolean updateStatus(MarketDailyDto marketDailyDto)
    {
        return marketDailyDomainService.updateStatus(marketDailyDto);
    }

    /**
     * 结算单据数据
     * @param dto
     * @return
     */
    public SettleDto getSettleInfo(ReportSearchDto dto){
        return marketDailyDomainService.getSettleInfo(dto);
    }

    /**
     * 结算关联
     * @param dto
     * @return
     */
    public List<SettleDto> getSettleRelation(ReportSearchDto dto){
        return marketDailyDomainService.getSettleRelation(dto);
    }

    /**
     * 收入详情
     * @param dto
     * @return
     */
    public List<SettleDto> getSettleIncomeDetail(ReportSearchDto dto){
        return marketDailyDomainService.getSettleIncomeDetail(dto);
    }

    /**
     * 结算产品关联
     * @param dto
     * @return
     */
    public int updateIncomeSettle(SettleDto dto){
        return marketDailyDomainService.updateIncomeSettle(dto);
    }

    /**
     * 取消关联
     * @param dto
     * @return
     */
    public int cancelIncomeSettle(SettleDto dto){
        return marketDailyDomainService.cancelIncomeSettle(dto);
    }

    /**
     * 查询所有收入情况
     * @param dto
     * @return
     */
    public List<MarketDailyDto> getAccountIncomeStatus(ReportSearchDto dto){
        return marketDailyDomainService.getAccountIncomeStatus(dto);
    }

}
