package com.cw.web.backend.controller.report;

import com.cw.biz.home.app.dto.ReportSearchDto;
import com.cw.biz.report.app.dto.MarketDailyDto;
import com.cw.biz.report.app.dto.SettleDto;
import com.cw.biz.report.app.service.MarketDailyAppService;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 产品控制器
 * Created by dujy on 2017-05-20.
 */
@RestController
public class MarketDailyController extends AbstractBackendController {

    @Autowired
    private MarketDailyAppService appService;

    /**
     * 查询日报数据
     * @return
     */
    @PostMapping("/report/marketDailyReport.json")
    public CPViewResultInfo findAll(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<MarketDailyDto> productDtos = appService.findAll(dto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 商户数据月报
     * @param dto
     * @return
     */
    @PostMapping("/report/marketMonthReport.json")
    public CPViewResultInfo marketMonthReport(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<MarketDailyDto> productDtos = appService.getMarketMonth(dto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("月报成功");
        return cpViewResultInfo;
    }

    /**
     * 财务结算
      * @param dto
     * @return
     */
    @PostMapping("/report/getMarketMonthSettle.json")
    public CPViewResultInfo getMarketMonthSettle(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<MarketDailyDto> productDtos = appService.getMarketMonthSettle(dto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("月报成功");
        return cpViewResultInfo;
    }

    /**
     * 月收入发票邮递信息列表
     * @param dto
     * @return
     */
    @PostMapping("/report/marketMonthList.json")
    public CPViewResultInfo marketMonthList(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<MarketDailyDto> productDtos = appService.marketMonthList(dto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询回盘每日明细
     * @param dto
     * @return
     */
    @PostMapping("/report/marketDailyDetail.json")
    public CPViewResultInfo marketDailyDetail(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<MarketDailyDto> productDtos = appService.marketDailyDetail(dto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 保存回盘数据
     * @param dto
     * @return
     */
    @PostMapping("/report/marketDailyUpdate.json")
    public CPViewResultInfo marketDailyUpdate(@RequestBody MarketDailyDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Boolean result = appService.updateCounteroffer(dto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 修改快递信息
     * @param dto
     * @return
     */
    @PostMapping("/report/postInfoUpdate.json")
    public CPViewResultInfo postInfoUpdate(@RequestBody MarketDailyDto dto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Boolean result = appService.postInfoUpdate(dto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 回填商户明细收入
     * @param dto
     * @return
     */
    @PostMapping("/report/updateMerchantIncome.json")
    public CPViewResultInfo updateMerchantIncome(@RequestBody MarketDailyDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Boolean result = appService.updateMerchantIncome(dto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 修改回盘数据状态
     * @param dto
     * @return
     */
    @PostMapping("/report/marketUpdateStatus.json")
    public CPViewResultInfo marketUpdateStatus(@RequestBody MarketDailyDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Boolean result = appService.updateStatus(dto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 日结数据回盘
     * @param dto
     * @return
     */
    @PostMapping("/report/updateDaily.json")
    public CPViewResultInfo updateDaily(@RequestBody MarketDailyDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long result = appService.updateDaily(dto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 查看结算单
     * @param dto
     * @return
     */
    @PostMapping("/report/marketSettle.json")
    public CPViewResultInfo marketSettle(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        SettleDto settleDto = appService.getSettleInfo(dto);
        cpViewResultInfo.setData(settleDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 产品结算关联设置
     * @param dto
     * @return
     */
    @PostMapping("/report/settleRelation.json")
    public CPViewResultInfo settleRelation(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<SettleDto> settleDto = appService.getSettleRelation(dto);
        cpViewResultInfo.setData(settleDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 结算收入详情
     * @param dto
     * @return
     */
    @PostMapping("/report/getSettleIncomeDetail.json")
    public CPViewResultInfo getSettleIncomeDetail(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<SettleDto> settleDto = appService.getSettleIncomeDetail(dto);
        cpViewResultInfo.setData(settleDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }


    /**
     * 产品结算关联保存
     * @param dto
     * @return
     */
    @PostMapping("/report/updateIncomeSettle.json")
    public CPViewResultInfo updateIncomeSettle(@RequestBody SettleDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        int result = appService.updateIncomeSettle(dto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }

    /**
     * 取消产品关联
     * @param dto
     * @return
     */
    @PostMapping("/report/cancelIncomeSettle.json")
    public CPViewResultInfo cancelIncomeSettle(@RequestBody SettleDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        int result = appService.cancelIncomeSettle(dto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询所有收入情况
     * @param dto
     * @return
     */
    @PostMapping("/report/getAccountIncomeStatus.json")
    public CPViewResultInfo getAccountIncomeStatus(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<MarketDailyDto> result = appService.getAccountIncomeStatus(dto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }


    /**
     * 收入回盘明细
     * @param dto
     * @return
     */
    @PostMapping("/report/incomeBackList.json")
    public CPViewResultInfo incomeBackList(@RequestBody ReportSearchDto dto)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<MarketDailyDto> productDtos = appService.incomeBackList(dto);
        cpViewResultInfo.setData(productDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

}
