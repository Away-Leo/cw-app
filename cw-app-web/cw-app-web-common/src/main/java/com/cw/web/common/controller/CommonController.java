package com.cw.web.common.controller;


import com.cw.biz.CwException;
import com.cw.biz.banner.app.dto.BannerDto;
import com.cw.biz.banner.app.service.BannerAppService;
import com.cw.biz.channel.app.dto.AppMarketDto;
import com.cw.biz.channel.app.service.AppMarketAppService;
import com.cw.biz.discount.domain.service.DiscountSettingDomainService;
import com.cw.biz.home.app.dto.AppInfoDto;
import com.cw.biz.home.app.dto.LoanServiceDto;
import com.cw.biz.home.app.service.AppInfoAppService;
import com.cw.biz.home.app.service.HomeAppService;
import com.cw.biz.home.app.service.LoanServiceAppService;
import com.cw.biz.log.app.dto.SendSmsLogDto;
import com.cw.biz.log.app.dto.SendSmsTimeDto;
import com.cw.biz.log.app.service.SendSmsLogAppService;
import com.cw.biz.log.app.service.SendSmsTimeAppService;
import com.cw.biz.parameter.app.dto.IntegralWallLogDto;
import com.cw.biz.parameter.app.dto.SpinnerParameterDto;
import com.cw.biz.parameter.app.service.IntegralWallLogAppService;
import com.cw.biz.parameter.app.service.SpinnerParameterAppService;
import com.cw.biz.push.app.dto.PushMessageDto;
import com.cw.biz.push.app.service.PushMessageAppService;
import com.cw.biz.push.domain.entity.PushMessage;
import com.cw.biz.user.app.dto.UserDto;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.user.domain.service.UserDomainService;
import com.cw.core.common.util.Utils;
import com.cw.web.common.component.LoginComponent;
import com.cw.web.common.component.SendSmsComponent;
import com.cw.web.common.component.UploadFileComponent;
import com.cw.web.common.dto.CPViewResultInfo;
import com.cw.web.common.model.LoginModel;
import com.cw.web.common.model.SendSmsModel;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *通用请求
 */
@Controller
@RequestMapping("/common")
public class CommonController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private LoginComponent loginComponent;

    @Autowired
    private SendSmsComponent sendSmsComponent;

    @Autowired
    private UploadFileComponent uploadFileComponent;

    @Autowired
    private SendSmsLogAppService sendSmsLogAppService;

    @Autowired
    private SendSmsTimeAppService sendSmsTimeAppService;
    @Autowired
    private SpinnerParameterAppService spinnerParameterAppService;
    @Autowired
    private BannerAppService bannerAppService;
    @Autowired
    private AppInfoAppService appInfoAppService;
    @Autowired
    private AppMarketAppService appMarketAppService;
    @Autowired
    private LoanServiceAppService loanServiceAppService;
    @Autowired
    private HomeAppService homeAppService;
    @Autowired
    private IntegralWallLogAppService integralWallLogAppService;
    @Autowired
    private PushMessageAppService pushMessageAppService;

    @Autowired
    private UserDomainService userDomainService;
    @Autowired
    private DiscountSettingDomainService discountSettingDomainService;

    @Value("${spring.profiles.active}")
    private String active;

    /**
     * 用户登录
     * @param httpServletRequest
     * @param loginModel
     * @return
     */
    @PostMapping("passwordLogin.json")
    @ResponseBody
    public CPViewResultInfo passwordLogin(HttpServletRequest httpServletRequest,CPViewResultInfo info, @RequestBody LoginModel loginModel) {
//        if("dev".equalsIgnoreCase(active)&&!loginModel.getUserName().equals("18623270209")) {
//            loginModel.setPassword("Yx18623185183");
//        }
        try{
            String openid = (String) httpServletRequest.getSession().getAttribute("openid");
            UserDto user=loginComponent.wechatBinding(httpServletRequest, loginModel, openid, loginModel.getLoginType());
            info.newSuccess(user);
        }catch (Exception e){
            info.newFalse(e);
        }
        return info;
    }

    /**
     * 发送验证码
     * @param sendSmsModel
     * @return
     */
    @PostMapping("sendSmsVerify.json")
    @ResponseBody
    public CPViewResultInfo sendSmsVerify(@RequestBody SendSmsModel sendSmsModel) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        if(!isMobilePhone(sendSmsModel.getPhone()))
        {
            CwException.throwIt("手机号码格式不正确");
        }
        logger.info("发送区域："+sendSmsModel.getApplyArea());
        //TODO 待验证
//        List<SpinnerParameterDto> parameterDtoList = this.spinnerParameterAppService.findByType("areaBan");
//        for (SpinnerParameterDto dto : parameterDtoList) {
//            if (dto.getName().equalsIgnoreCase(sendSmsModel.getApplyArea()))
//            {
//                logger.info("发送区域属于被禁止范围" + sendSmsModel.getApplyArea());
//                CwException.throwIt("手机号码格式不正确");
//            }
//        }
        //检查用户已发送次数
        SendSmsTimeDto sendSmsTimeDto = sendSmsTimeAppService.findByPhone(sendSmsModel.getPhone(),sendSmsModel.getAppName());
        if(!Objects.isNull(sendSmsTimeDto))
        {
            if(sendSmsTimeDto.getSendTime() > 2)
            {
                CwException.throwIt("短信发送超过次数,最近一次验证码可直接登录");
            }
        }

        //查询上次是否发送成功
        SendSmsLogDto isSendSmsLog = sendSmsLogAppService.findById(sendSmsModel.getPhone());
        if(Objects.isNull(isSendSmsLog)) {
            //记录发送日志
            SendSmsLogDto sendSmsLogDto = new SendSmsLogDto();
            sendSmsLogDto.setDeviceNumber(sendSmsModel.getDeviceNumber());
            sendSmsLogDto.setApplyArea(sendSmsModel.getApplyArea());
            sendSmsLogDto.setSendDate(new Date());
            sendSmsLogDto.setChannelNo(sendSmsModel.getChannelNo());
            sendSmsLogDto.setPhone(sendSmsModel.getPhone());
            sendSmsLogDto.setAppName(sendSmsModel.getAppName());
            sendSmsLogDto.setIsSuccess(Boolean.FALSE);
            Long smsId = sendSmsLogAppService.create(sendSmsLogDto);

            //发送短信
            String result = sendSmsComponent.sendSms(sendSmsModel);
            if (result.contains("success")) {
                //修改发送标识
                sendSmsLogDto.setId(smsId);
                sendSmsLogDto.setIsSuccess(Boolean.TRUE);
                sendSmsLogAppService.update(sendSmsLogDto);
                //记录发送次数
                sendSmsTimeDto = new SendSmsTimeDto();
                sendSmsTimeDto.setPhone(sendSmsModel.getPhone());
                sendSmsTimeDto.setAppName(sendSmsModel.getAppName());
                sendSmsTimeAppService.update(sendSmsTimeDto);
            } else {
                CwException.throwIt("短信发送失败");
            }
        }
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("发送成功");
        return cpViewResultInfo;
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("upload.json")
    @ResponseBody
    public CPViewResultInfo upload(MultipartFile file) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        String filePath = uploadFileComponent.upload(file);
        cpViewResultInfo.setData(filePath);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("上传成功");
        return cpViewResultInfo;
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("logout.json")
    @ResponseBody
    public CPViewResultInfo logout() {
        CPViewResultInfo resultInfo = new CPViewResultInfo();
        SecurityUtils.getSubject().logout();
        resultInfo.setSuccess(true);
        return resultInfo;
    }

    /**
     * 查询下拉列表
     * @return
     */
    @GetMapping("findByType.json")
    @ResponseBody
    public CPViewResultInfo findByType(String type,String loanType) {
        if(type==null)
        {
            type = loanType;
        }
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<SpinnerParameterDto> spinnerParameterDto = spinnerParameterAppService.findByType(type);
        cpViewResultInfo.setData(spinnerParameterDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("查询成功");
        return cpViewResultInfo;
    }


    /**
     * 查询banner图标
     * @param position
     * @return
     */
    @GetMapping("findBanner.json")
    @ResponseBody
    public CPViewResultInfo findCreditCardBanner(String position,String versionNo)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<BannerDto> bannerDtoList = bannerAppService.findAll(position,versionNo);
        cpViewResultInfo.setData(bannerDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询所有app
     * @return
     */
    @GetMapping("getAppList.json")
    @ResponseBody
    public CPViewResultInfo getAppList()
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AppInfoDto> appInfoDtoList = appInfoAppService.getAppList();
        cpViewResultInfo.setData(appInfoDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询应用市场
     * @return
     */
    @GetMapping("getAppMarketList.json")
    @ResponseBody
    public CPViewResultInfo getAppMarketList()
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        List<AppMarketDto> appInfoDtoList = appMarketAppService.findAll();
        cpViewResultInfo.setData(appInfoDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询放款统计数据
     * @return
     */
    @GetMapping("getLoanService.json")
    @ResponseBody
    public CPViewResultInfo getLoanService()
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        LoanServiceDto appInfoDtoList = loanServiceAppService.getLoanService();
        cpViewResultInfo.setData(appInfoDtoList);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 查询推送信息跳转链接
     * @return
     */
    @GetMapping("getPushMessage.json")
    @ResponseBody
    public CPViewResultInfo getPushMessage(String title)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        PushMessageDto pushMessageDto = pushMessageAppService.findByContent(title);
        cpViewResultInfo.setData(pushMessageDto);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("成功");
        return cpViewResultInfo;
    }

    /**
     * 判断移动号段
     * @param phone
     * @return
     */
    private Boolean isMobilePhone(String phone)
    {
        String PHONE_PATTERN="^((13[0-9])|(19[0-9])|(16[6|7])|(14[1|5|6|7|8])|(15([0-3]|[5-9]))|(17([0,1,3,6,7,8,]))|(18[0-9]))\\d{8}$";
        Pattern p = Pattern.compile(PHONE_PATTERN);
        Matcher m = p.matcher(phone);
        return m.find();
    }

    /**
     * idfa去重复接口
     * @param source
     * @param appid
     * @param idfa
     * @return
     */
    @GetMapping("removeRepeatIdfa.json")
    @ResponseBody
    public CPViewResultInfo removeRepeatIdfa(String source,String appid,String idfa)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        //验证重复
        if(!"depu".equals(source)){
            cpViewResultInfo.setSuccess(false);
            cpViewResultInfo.setCode("999999");
            cpViewResultInfo.setMessage("请求来源非法");
        }
        //验证appid是否正确
        if(!"1094630132".equals(appid)){
            cpViewResultInfo.setSuccess(false);
            cpViewResultInfo.setCode("999998");
            cpViewResultInfo.setMessage("APPID非法");
        }
        //正确的来源
        if("depu".equals(source)&&"1094630132".equals(appid))
        {
            Map<String,Integer> appInfoDtoList = homeAppService.removeRepeat(idfa);
            cpViewResultInfo.setMessage("成功");
            cpViewResultInfo.setSuccess(true);
            cpViewResultInfo.setData(appInfoDtoList);
        }

        return cpViewResultInfo;
    }

    /**
     * 点击上报
     * @param source
     * @param appid
     * @param idfa
     * @return
     */
    @GetMapping("clickUploadIdfa.json")
    @ResponseBody
    public CPViewResultInfo clickUploadIdfa(String source,String appid,String idfa)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        //验证重复
        if(!"depu".equals(source)){
            cpViewResultInfo.setSuccess(false);
            cpViewResultInfo.setCode("999999");
            cpViewResultInfo.setMessage("请求来源非法");
        }
        //验证appid是否正确
        if(!"1094630132".equals(appid)){
            cpViewResultInfo.setSuccess(false);
            cpViewResultInfo.setCode("999998");
            cpViewResultInfo.setMessage("APPID非法");
        }
        //正确的来源
        if("depu".equals(source)&&"1094630132".equals(appid))
        {
            Map<String,Integer> appInfoDtoList = new HashMap();
            appInfoDtoList.put("status",1);
            cpViewResultInfo.setMessage("成功");
            cpViewResultInfo.setSuccess(true);
            cpViewResultInfo.setData(appInfoDtoList);
        }

        return cpViewResultInfo;
    }

    /**
     * 激活上报
     * @param source
     * @param appid
     * @param idfa
     * @return
     */
    @GetMapping("activeUploadIdfa.json")
    @ResponseBody
    public CPViewResultInfo activeUploadIdfa(String source,String appid,String idfa)
    {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        //验证重复
        if(!"depu".equals(source)){
            cpViewResultInfo.setSuccess(false);
            cpViewResultInfo.setCode("999999");
            cpViewResultInfo.setMessage("请求来源非法");
        }
        //验证appid是否正确
        if(!"1094630132".equals(appid)){
            cpViewResultInfo.setSuccess(false);
            cpViewResultInfo.setCode("999998");
            cpViewResultInfo.setMessage("APPID非法");
        }
        //正确的来源
        if("depu".equals(source)&&"1094630132".equals(appid))
        {
            Map<String,Integer> appInfoDtoList = new HashMap();
            appInfoDtoList.put("status",1);
            cpViewResultInfo.setSuccess(true);
            cpViewResultInfo.setData(appInfoDtoList);
            cpViewResultInfo.setMessage("成功");
        }

        return cpViewResultInfo;
    }

    /**
     * 验证设备号唯一
     * @param integralWallLogDto
     * @return
     */
    @PostMapping("verifyIdfaValid.json")
    @ResponseBody
    public CPViewResultInfo verifyIdfaValid(@RequestBody IntegralWallLogDto integralWallLogDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long result = integralWallLogAppService.create(integralWallLogDto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("保存成功");
        return cpViewResultInfo;
    }


    /**
     * API接口
     * @param integralWallLogDto
     * @return
     */
    @PostMapping("apiApplyLoan.json")
    @ResponseBody
    public CPViewResultInfo apiApplyLoan(@RequestBody IntegralWallLogDto integralWallLogDto) {
        CPViewResultInfo cpViewResultInfo = new CPViewResultInfo();
        Long result = integralWallLogAppService.create(integralWallLogDto);
        cpViewResultInfo.setData(result);
        cpViewResultInfo.setSuccess(true);
        cpViewResultInfo.setMessage("申请成功");
        return cpViewResultInfo;
    }

    @GetMapping("getUserPage.json")
    @ResponseBody
    public CPViewResultInfo getUserPage(CPViewResultInfo info, UserDto dto){
        try {
            info.newSuccess(userDomainService.findByCondition(new PageRequest(dto.getPage(),dto.getSize()),dto));
        }catch (Exception e){
            info.newFalse(e);
        }
        return info;
    }

}
