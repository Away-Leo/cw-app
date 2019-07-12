package com.cw.web.backend.controller.home;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cw.biz.CPContext;
import com.cw.biz.home.app.dto.*;
import com.cw.biz.home.app.service.ExportDataLogAppService;
import com.cw.biz.home.app.service.HomeAppService;
import com.cw.biz.home.domain.entity.ExportDataLog;
import com.cw.biz.product.app.dto.ProductSearchDto;
import com.cw.biz.user.app.dto.BoDaApplyDto;
import com.cw.biz.user.app.dto.BoDaInfoDto;
import com.cw.biz.user.app.dto.BoDaLoanDto;
import com.cw.biz.user.app.dto.CwUserInfoDto;
import com.cw.core.common.util.AppUtils;
import com.cw.core.common.util.Utils;
import com.cw.web.backend.controller.AbstractBackendController;
import com.cw.web.common.component.SendSmsComponent;
import com.cw.web.common.dto.CPViewResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后台首页
 * Created by Administrator on 2017/6/5.
 */
@Controller
public class HomeController extends AbstractBackendController {

    @Autowired
    private HomeAppService homeAppService;

    @Autowired
    private ExportDataLogAppService exportDataLogAppService;
    /**
     * 加载首页数据
     *
     * @return
     */
    @GetMapping("/initHome.json")
    @ResponseBody
    public CPViewResultInfo initHome(String applyDate) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        HomeDto homeDto = homeAppService.findTotalData(applyDate);
        cpViewResultInfo.setData(homeDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 各APP用户分布情况
     * @return
     */
    @GetMapping("/getAppUser.json")
    @ResponseBody
    public CPViewResultInfo getAppUser(String applyDate) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<HomeDto> homeDto = homeAppService.getAppUser(applyDate);
        cpViewResultInfo.setData(homeDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 新增用户分布
     * @return
     */
    @GetMapping("/appstoreCount.json")
    @ResponseBody
    public CPViewResultInfo appstoreCount(String appName,String cycle,String applyDate) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<HomeDto> homeDto = homeAppService.findApplicationStoreCount(appName,cycle,applyDate);
        String result="";
        int i=0;
        for(HomeDto homeDto1:homeDto)
        {
            i++;
            result+= "['" +homeDto1.getChannelNo()+"',"+homeDto1.getApplyNum()+"]";
            if( i < homeDto.size()) {
                result += ",";
            }
        }
        cpViewResultInfo.setData("["+result+"]");
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询申请分布图
     * @return
     */
    @GetMapping("/appstoreList.json")
    @ResponseBody
    public CPViewResultInfo appstoreList(String appName,String cycle,String applyDate){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String data="[{name:'注册',data:[";
        List<HomeDto> homeDtoList = homeAppService.findApplicationStoreListCount(0,appName,cycle,applyDate);
        data += createData(homeDtoList);
//        data+="]},{name:'登录',data:[";
//        //登录用户
//        List<HomeDto> loginList = homeAppService.findApplicationStoreListCount(1,appName,cycle,applyDate);
//        data += createData(loginList);
        data+="]},{name:'申请',data:[";
        //注册用户数
        List<HomeDto> applyList = homeAppService.findApplicationStoreListCount(2,appName,cycle,applyDate);
        data += createData(applyList);
        data+="]}]";
        cpViewResultInfo.setData(data);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 来量统计趋势图
     * @param cycle
     * @return
     */
    @GetMapping("/devUserLineChart.json")
    @ResponseBody
    public CPViewResultInfo devUserLineChart(String cycle,String applyDate,String appName){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String data="[{name:'总体',data:[";
        List<AppDevDto> totalDtoList = homeAppService.devUserLineChart("",cycle,applyDate);
        data += createDevData(totalDtoList,"register",cycle);
        data+="]}";
        if("MBD".equals(appName)||appName==null) {
            data += ",{name:'秒必贷',data:[";
            List<AppDevDto> homeDtoList = homeAppService.devUserLineChart(AppUtils.appNameMBD, cycle, applyDate);
            data += createDevData(homeDtoList, "register", cycle);
            data += "]}";
        }
        if("XSD".equals(appName)||appName==null) {
            data += ",{name:'迅闪贷',data:[";
            //迅闪贷
            List<AppDevDto> loginList = homeAppService.devUserLineChart(AppUtils.appNameXSD, cycle, applyDate);
            data += createDevData(loginList, "register", cycle);
            data += "]}";
        }
        if("JQW".equals(appName)||appName==null) {
            data += ",{name:'借钱帝',data:[";
            //借钱帝
            List<AppDevDto> applyList = homeAppService.devUserLineChart(AppUtils.appNameJQW, cycle, applyDate);
            data += createDevData(applyList, "register", cycle);
            data += "]}";
        }
        if("JDS".equals(appName)||appName==null) {
            data += ",{name:'借多少',data:[";
            //借多少
            List<AppDevDto> jieduoshao = homeAppService.devUserLineChart(AppUtils.appNameJDS, cycle, applyDate);
            data += createDevData(jieduoshao, "register", cycle);
            data += "]}";
        }
        data+= "]";
        cpViewResultInfo.setData(data);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 构造数据
     * @param homeDtoList
     * @return
     */
    private String createData(List<HomeDto> homeDtoList)
    {
        int i=0;
        String result="";
        for(HomeDto homeDto:homeDtoList)
        {
            i++;
            result += "" + homeDto.getNum() + "";
            if( i < homeDtoList.size()) {
                result += ",";
            }
        }
        return result;
    }

    /**
     * 构造数据
     * @param homeDtoList
     * @return
     */
    private String createDevData(List<AppDevDto> homeDtoList,String quota,String cycle)
    {
        int index=0;
        String result="";
        int kedu=0;
        if("D".equals(cycle)){
            kedu = 23;
        }else{
            index = 1;
            kedu = 31;
        }
        boolean flag=false;
        for(int idx = index ;idx<=kedu;idx++) {
            flag =false;
            for (AppDevDto appDevDto : homeDtoList) {
                if(Integer.parseInt(appDevDto.getName())==idx) {
                    if ("register".equals(quota)||"month".equals(quota)) {
                        result += "" + appDevDto.getDevUser() + "";
                    } else if ("apply".equals(quota)) {
                        result += "" + appDevDto.getApplyTime() + "";
                    } else {
                        result += "" + appDevDto.getApplyNum() + "";
                    }
                    flag = true;
                }
            }
            if(!flag)
            {
                result +="0";
            }
            if( idx < kedu) {
                result += ",";
            }
        }
        return result;
    }

    /**
     * 用户新增日报表
     * @return
     */
    @GetMapping("/dayDev.json")
    @ResponseBody
    public CPViewResultInfo dayDev(ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<HomeDto> homeDto = homeAppService.dayDev(reportSearchDto);
        cpViewResultInfo.setData(homeDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 单渠道转化率统计表
     * @return
     */
    @GetMapping("/channelIncomeTotal.json")
    @ResponseBody
    public CPViewResultInfo channelInOrOutcomeTotal(ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<HomeDto> homeDto = homeAppService.channelInOrOutcomeTotal(reportSearchDto);
        cpViewResultInfo.setData(homeDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 用户新增日报表
     * @return
     */
    @GetMapping("/monthDev.json")
    @ResponseBody
    public CPViewResultInfo monthDev(ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<HomeDto> homeDto = homeAppService.monthDev(reportSearchDto);
        cpViewResultInfo.setData(homeDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 用户新增日报表
     * @return
     */
    @PostMapping("/merchantIncome.json")
    @ResponseBody
    public CPViewResultInfo merchantIncome(@RequestBody ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<HomeDto> homeDto = homeAppService.merchantIncome(reportSearchDto);
        cpViewResultInfo.setData(homeDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * app新增用户数
     * @return
     */
    @PostMapping("/appDevUser.json")
    @ResponseBody
    public CPViewResultInfo appDevUser(@RequestBody ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AppDevDto> homeDto = homeAppService.appDevUser(reportSearchDto);
        cpViewResultInfo.setData(homeDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 按时段新增用户数分布
     * @return
     */
    @PostMapping("/devUserHour.json")
    @ResponseBody
    public CPViewResultInfo devUserHour(@RequestBody ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AppDevDto> homeDto = homeAppService.devUserHour(reportSearchDto);
        String cycle="D";
        if(!reportSearchDto.getApplyEndDate().equals(reportSearchDto.getApplyStartDate())){
            cycle = "M";
        }
        if("month".equals(reportSearchDto.getQuota()))
        {
            cycle = "M";
        }
        String title="";
        if("register".equals(reportSearchDto.getQuota())||"month".equals(reportSearchDto.getQuota())){
            title = "注册";
        }else if ("apply".equals(reportSearchDto.getQuota()))
        {
            title = "申请次数";
        }else{
            title = "申请人数";
        }
        String data="[{name:'"+title+"',data:[";
        data += createDevData(homeDto,reportSearchDto.getQuota(),cycle);
        data +="]}]";
        cpViewResultInfo.setData(data);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 查询渠道数据
     * @param reportSearchDto
     * @return
     */
    @PostMapping("/channelData.json")
    @ResponseBody
    public CPViewResultInfo channelData(@RequestBody ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<HomeDto> homeDto = homeAppService.getChannelData(reportSearchDto);
        cpViewResultInfo.setData(homeDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 运营报表统计
     * @param reportSearchDto
     * @return
     */
    @PostMapping("/operateReport.json")
    @ResponseBody
    public CPViewResultInfo operateReport(@RequestBody ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<OperateDto> operateDto = homeAppService.operateReport(reportSearchDto);
        cpViewResultInfo.setData(operateDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 应用市场发展数据汇总
     * @param reportSearchDto
     * @return
     */
    @PostMapping("/appMarketReport.json")
    @ResponseBody
    public CPViewResultInfo appMarketReport(@RequestBody ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AppMarketDto> operateDto = homeAppService.appMarketReport(reportSearchDto);
        cpViewResultInfo.setData(operateDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }

    /**
     * 商户申请月分布图
     * @param applyDate
     * @return
     */
    @GetMapping("/merchantTotalChart.json")
    @ResponseBody
    public CPViewResultInfo merchantTotalChart(String applyDate){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String data="[{name:'申请次数',data:[";
        List<HomeDto> totalDtoList = homeAppService.merchartApply(applyDate);
        data += createMerchantData(totalDtoList,"applyTime");
        data+="]},{name:'申请用户',data:[";
        data += createMerchantData(totalDtoList,"applyNum");
        data+="]}]";
        cpViewResultInfo.setData(data);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 商户对比趋势图
     * @param reportSearchDto
     * @return
     */
    @GetMapping("/merchantChart.json")
    @ResponseBody
    public CPViewResultInfo merchantChart(ReportSearchDto reportSearchDto){
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String data="[{name:'申请次数',data:[";
        List<HomeDto> totalDtoList = homeAppService.merchartChart(reportSearchDto);
        data += createMerchant(totalDtoList,"applyTime");
        data+="]},{name:'申请用户',data:[";
        data += createMerchant(totalDtoList,"applyNum");
        data+="]}]";
        cpViewResultInfo.setData(data);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage(createMerchant(totalDtoList,""));
        return cpViewResultInfo;
    }


    /**
     * 按时段新增用户数分布
     * @return
     */
    @PostMapping("/devMerchantMonth.json")
    @ResponseBody
    public CPViewResultInfo devMerchantMonth(@RequestBody ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AppDevDto> homeDto = homeAppService.merchantMonthChart(reportSearchDto);
        String title="申请次数";
        String data="[{name:'"+title+"',data:[";
        data += createDevData(homeDto,"apply","M");
        data +="]}]";
        cpViewResultInfo.setData(data);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 构建商户趋势图数据
      * @param homeDtoList
     * @param type
     * @return
     */
    private String createMerchant(List<HomeDto> homeDtoList,String type){
        int i=0;
        String result="";
        for(HomeDto homeDto:homeDtoList)
        {
            i++;
            if("applyTime".equals(type)) {
                result += "" + homeDto.getApplyTime() + "";
            }else if("applyNum".equals(type)){
                result += "" + homeDto.getApplyNum() + "";
            }else{
                result += "'" + homeDto.getMerchantName() + "'";
            }
            if( i < homeDtoList.size()) {
                result += ",";
            }
        }
        return result;
    }

    /**
     * 构建商户数据
     * @param homeDtoList
     * @param quota
     * @return
     */
    private String createMerchantData(List<HomeDto> homeDtoList,String quota){
        int index=1;
        String result="";
        boolean flag=false;
        for(int idx = index ;idx<=31;idx++) {
            flag =false;
            for (HomeDto homeDto : homeDtoList) {
                if(Integer.parseInt(homeDto.getChannelNo())==idx) {
                    if ("applyTime".equals(quota)) {
                        result += "" + homeDto.getApplyTime() + "";
                    } else if ("applyNum".equals(quota)) {
                        result += "" + homeDto.getApplyNum() + "";
                    }
                    flag = true;
                }
            }
            if(!flag)
            {
                result +="0";
            }
            if( idx < 31) {
                result += ",";
            }
        }
        return result;
    }

    /**
     * 留存用户统计
     * @param reportSearchDto
     * @return
     */
    @GetMapping("/retainedUserTotal.json")
    @ResponseBody
    public CPViewResultInfo retainedUserTotal(ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AppDevDto> appDevDtos = homeAppService.retainedUserTotal(reportSearchDto);
        cpViewResultInfo.setData(appDevDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("留存用户统计成功");
        return cpViewResultInfo;
    }

    /**
     * 应用市场数据统计
     * @param reportSearchDto
     * @return
     */
    @GetMapping("/appMarketTotal.json")
    @ResponseBody
    public CPViewResultInfo appMarketTotal(ReportSearchDto reportSearchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AppDevDto> appDevDtos = homeAppService.appMarketTotal(reportSearchDto);
        cpViewResultInfo.setData(appDevDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("应用市场数据统计");
        return cpViewResultInfo;
    }

    /**
     * 博达数据导入
     * @param cwUserInfoDto
     * @return
     */
    @PostMapping("/exportUserInfo.json")
    @ResponseBody
    public CPViewResultInfo exportUserInfo(CwUserInfoDto cwUserInfoDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<CwUserInfoDto> homeDto = homeAppService.exportUserInfo(cwUserInfoDto);
        List<BoDaLoanDto> boDaLoanDtos = new ArrayList<>();
        String result="";
        String ids="";
        int i=0;
        if(homeDto.size()>0){
            for(CwUserInfoDto homeDto1:homeDto)
            {
                BoDaLoanDto boDaLoanDto = new BoDaLoanDto();
                boDaLoanDto.setLoanType("工薪人士");
                boDaLoanDto.setClientName(homeDto1.getName());
                boDaLoanDto.setClientTel(homeDto1.getPhone()+"");
                boDaLoanDto.setHouseAddress(homeDto1.getAddress()==null?homeDto1.getCity():homeDto1.getAddress());
                boDaLoanDtos.add(boDaLoanDto);
                ids += "" +homeDto1.getUserId();
                if( i < homeDto.size()-1) {
                    ids += ",";
                }
                i++;
            }
            //生成订单编号
            String batchNo = Utils.convertDate(new Date()).replaceAll("-","")+Utils.randomStr();
            BoDaApplyDto boDaApplyDto = new BoDaApplyDto();
            boDaApplyDto.setBatchNo(batchNo);
            boDaApplyDto.setData(boDaLoanDtos);
            //构造请求参数
            StringBuffer send = new StringBuffer();
            try {
                send.append("username=").append("pingxun");
                send.append("&password=").append(URLEncoder.encode("0f9bd5988a6ceb2b05a41b9b05721daff8b70750", "UTF-8"));
                send.append("&loanApply=").append(URLEncoder.encode(JSON.toJSONString(boDaApplyDto).toString(), "UTF-8"));
                String url="http://123.147.164.8:9600/appserver/thirdData/pingXun/ajax/submitLoanApply";
                String testUrl="http://123.147.164.8:9800/appserver/thirdData/pingXun/ajax/submitLoanApply";
                //发送产品入库
                result = Utils.sendDateInterface(url,send.toString(),"POST");
                JSONObject jsonObject = JSONObject.parseObject(result);
                Boolean isResult = (Boolean) jsonObject.get("success");
                if(isResult){
                    //更新导入批次号
                    CwUserInfoDto cwUserInfoDto1 = new CwUserInfoDto();
                    cwUserInfoDto1.setBatchNo(batchNo);
                    cwUserInfoDto1.setIds(ids);
                    homeAppService.updateExportUserInfo(cwUserInfoDto1);
                    cpViewResultInfo.setSuccess(true);
                    cpViewResultInfo.setMessage("导入成功");
                }else{
                    cpViewResultInfo.setMessage("导入失败");
                    cpViewResultInfo.setSuccess(false);
                }
                //记录数据导入日志
                ExportDataLogDto exportDataLogDto = new ExportDataLogDto();
                exportDataLogDto.setBatchNo(batchNo);
                exportDataLogDto.setExportResult(jsonObject.get("returnInfo").toString());
                exportDataLogDto.setExportDate(new Date());
                exportDataLogDto.setExportNum(homeDto.size());
                exportDataLogDto.setExportUserId(CPContext.getContext().getSeUserInfo().getId());
                exportDataLogAppService.create(exportDataLogDto);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            cpViewResultInfo.setMessage("暂无新数据");
            cpViewResultInfo.setSuccess(false);
        }

        return cpViewResultInfo;
    }

    /**
     * 查询导入日志
     * @param batchNo
     * @return
     */
    @GetMapping("/findExportLog.json")
    @ResponseBody
    public CPViewResultInfo findExportLog(String batchNo) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<ExportDataLogDto> appDevDtos = exportDataLogAppService.findAll(batchNo);
        cpViewResultInfo.setData(appDevDtos);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("导入日志查询成功");
        return cpViewResultInfo;
    }


    /**
     * 查询贷款进度
     * @param batchNo
     * @return
     */
    @GetMapping("/findLoanProcess.json")
    @ResponseBody
    public CPViewResultInfo findLoanProcess(String batchNo) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();

        String url="http://123.147.164.8:9600/appserver/thirdData/pingXun/ajax/queryLoanProgress";
        String testUrl="http://123.147.164.8:9800/appserver/thirdData/pingXun/ajax/queryLoanProgress";

        StringBuffer send = new StringBuffer();
        try {
            send.append("username=").append("pingxun");
            send.append("&password=").append(URLEncoder.encode("0f9bd5988a6ceb2b05a41b9b05721daff8b70750", "UTF-8"));
            send.append("&batchNo=").append(batchNo);
            String result = Utils.sendDateInterface(url, send.toString(), "POST");
            JSONObject jsonObject = JSONObject.parseObject(result);
            JSONArray jsonArray = JSONArray.parseArray(jsonObject.get("result").toString());
            cpViewResultInfo.setData(jsonArray.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询跟踪结果");
        return cpViewResultInfo;
    }

    /**
     * 用户区域分布
     * @return
     */
    @GetMapping("/devAreaCount.json")
    @ResponseBody
    public CPViewResultInfo devAreaCount() {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AppDevDto> appDevDtoList = homeAppService.devAreaCount();
        cpViewResultInfo.setData(appDevDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 分发系数
     * @return
     */
    @GetMapping("/countUserApplyRatio.json")
    @ResponseBody
    public CPViewResultInfo countUserApplyRatio(ProductSearchDto searchDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<HomeDto> appDevDtoList = homeAppService.countUserApplyRatio(searchDto);
        cpViewResultInfo.setData(appDevDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

}
