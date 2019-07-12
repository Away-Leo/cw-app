package com.cw.biz.report.domain.service;

import com.cw.biz.CwException;
import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.biz.report.app.dto.SettleDto;
import com.cw.biz.report.domain.dao.MarketDailyDao;
import com.cw.biz.report.domain.entity.MarketDaily;
import com.cw.biz.report.domain.repository.MarketDailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 市场日报
 * Created by dujy on 2017-05-20.
 */
@Service
public class MarketDailyDomainService {

    @Autowired
    private MarketDailyRepository repository;

    @Autowired
    private MarketDailyDao marketDailyDao;

    /**
     * 修改日报数据
     * @param marketDailyDto
     * @return
     */
    public MarketDaily update(MarketDailyDto marketDailyDto)
    {
        MarketDaily marketDaily = repository.findOne(marketDailyDto.getId());
        if(marketDaily == null){
            CwException.throwIt("日报不存在");
        }
        marketDaily.from(marketDailyDto);

        return marketDaily;
    }

    /**
     * 日结回盘数据
     * @param marketDailyDto
     * @return
     */
    public MarketDaily updateDaily(MarketDailyDto marketDailyDto)
    {
        MarketDaily marketDaily = repository.findOne(marketDailyDto.getId());
        if(marketDaily == null){
            CwException.throwIt("日报不存在");
        }
        marketDaily.setStatus(1);
        marketDaily.setIsBilling(marketDailyDto.getIsBilling());
        marketDaily.setConfirmSettleFee(marketDailyDto.getConfirmSettleFee());
        return marketDaily;
    }
    /**
     * 查询当月日报信息
     * @return
     */
    public List<MarketDailyDto> findAll(ReportSearchDto dto) {

        return marketDailyDao.findMarketDailyByDayId(dto);
    }

    /**
     * 查询当月汇总数据
     * @param dto
     * @return
     */
    public List<MarketDailyDto> getMarketMonth(ReportSearchDto dto) {

        return marketDailyDao.getMarketMonth(dto);
    }

    /**
     * 新结算关联设置
     * @param dto
     * @return
     */
    public List<MarketDailyDto> getMarketMonthSettle(ReportSearchDto dto) {

        return marketDailyDao.getMarketMonthSettle(dto);
    }

    /**
     * 邮递快递信息
     * @param dto
     * @return
     */
    public List<MarketDailyDto> marketMonthList(ReportSearchDto dto) {

        return marketDailyDao.marketMonthList(dto);
    }


    /**
     * 查询商品每日回盘明细
     * @param dto
     * @return
     */
    public List<MarketDailyDto> marketDailyDetail(ReportSearchDto dto) {

        return marketDailyDao.marketDailyDetail(dto);
    }

    /**
     * 收入回盘明细
     * @param dto
     * @return
     */
    public List<MarketDailyDto> incomeBackList(ReportSearchDto dto) {

        return marketDailyDao.incomeBackList(dto);
    }

    /**
     * 测试用例修改收入回盘数据
     * @param dto
     * @return
     */
    public List<MarketDailyDto> marketDailyDetailTest(ReportSearchDto dto) {

        return marketDailyDao.marketDailyDetailTest(dto);
    }
    /**
     * 查询当天日报记录
     * @param id
     * @return
     */
    public MarketDaily findById(Long id)
    {
        return repository.findOne(id);
    }

    /**
     * 修改回盘数据
     * @param marketDailyDto
     * @return
     */
    public Boolean updateCounteroffer(MarketDailyDto marketDailyDto){
        return marketDailyDao.updateDailySettleIncome(marketDailyDto)>0?Boolean.TRUE:Boolean.FALSE;
    }

    /**
     * 修改快递信息
     * @param marketDailyDto
     * @return
     */
    public Boolean postInfoUpdate(MarketDailyDto marketDailyDto){
        return marketDailyDao.postInfoUpdate(marketDailyDto)>0?Boolean.TRUE:Boolean.FALSE;
    }
    /**
     * 商户明细回盘收入
     * @param marketDailyDto
     * @return
     */
    public Boolean updateMerchantIncome(MarketDailyDto marketDailyDto){
        return marketDailyDao.updateMerchantIncome(marketDailyDto)>0?Boolean.TRUE:Boolean.FALSE;
    }

    /**
     * 修改状态
     * @param marketDailyDto
     * @return
     */
    public Boolean updateStatus(MarketDailyDto marketDailyDto){
        int result = marketDailyDao.updateStatus(marketDailyDto);
        if(result==0){
            CwException.throwIt("该账期已确认或操作失败!");
        }
        return Boolean.TRUE;
    }

    /***
     * 查询结算信息
     * @param dto
     * @return
     */
    public SettleDto getSettleInfo(ReportSearchDto dto){
        return marketDailyDao.getSettleInfo(dto);
    }

    /**
     * 结算关联查询
     * @param dto
     * @return
     */
    public List<SettleDto> getSettleRelation(ReportSearchDto dto){
        return marketDailyDao.getSettleRelation(dto);
    }

    /**
     * 收入详情
     * @param dto
     * @return
     */
    public List<SettleDto> getSettleIncomeDetail(ReportSearchDto dto){
        return marketDailyDao.getSettleIncomeDetail(dto);
    }

    /**
     * 结算产品关联
     * @param dto
     * @return
     */
    public int updateIncomeSettle(SettleDto dto){
        return marketDailyDao.updateIncomeSettle(dto);
    }

    /**
     * 取消关联
     * @param dto
     * @return
     */
    public int cancelIncomeSettle(SettleDto dto){
        return marketDailyDao.cancelIncomeSettle(dto);
    }

    /**
     * 查询所有收入情况
     * @param dto
     * @return
     */
    public List<MarketDailyDto> getAccountIncomeStatus(ReportSearchDto dto){
        return marketDailyDao.getAccountIncomeStatus(dto);
    }

}
