package com.cw.web.common.component;

import com.alibaba.fastjson.JSONObject;
import com.cw.biz.CPContext;
import com.cw.biz.CwException;
import com.cw.biz.discount.domain.service.DiscountSettingDomainService;
import com.cw.biz.home.app.dto.AppInfoDto;
import com.cw.biz.log.app.LogEnum;
import com.cw.biz.log.app.dto.LogDto;
import com.cw.biz.log.app.service.LogAppService;
import com.cw.biz.user.app.dto.RegisterDto;
import com.cw.biz.user.app.dto.UserDto;
import com.cw.biz.user.domain.entity.SeUser;
import com.cw.biz.user.domain.entity.UserEntity;
import com.cw.biz.user.domain.relm.CwAuthenticationToken;
import com.cw.biz.user.domain.service.SeUserService;
import com.cw.biz.xinyanlogin.component.GetMobileComponent;
import com.cw.core.common.base.ENUM_EXCEPTION;
import com.cw.core.common.base.ENUM_LOGINTYPE;
import com.cw.core.common.util.ObjectHelper;
import com.cw.core.common.util.ObjectProperUtil;
import com.cw.web.common.model.LoginModel;
import com.cw.web.common.util.Apps;
import com.zds.common.lang.beans.Copier;
import com.zds.common.lang.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 */
@Service
@Slf4j
public class LoginComponent {
    public static Logger logger = LoggerFactory.getLogger(LoginComponent.class);

    @Autowired
    private SeUserService userService;

    @Autowired
    private LogAppService logAppService;

    @Autowired
    private DiscountSettingDomainService discountSettingDomainService;

    private SeUser loginWithPassword(HttpServletRequest httpServletRequest, LoginModel loginModel,String wechatId, String loginType) {
        SeUser seUser = loginVerify(httpServletRequest, loginModel, LoginModel.PasswordLogin.class, loginType);
        if(ObjectHelper.isEmpty(seUser))throw new BusinessException(ENUM_EXCEPTION.E10011.code,ENUM_EXCEPTION.E10011.msg);
        seUser.setWechatId(wechatId);
        AuthenticationToken token = new CwAuthenticationToken(loginModel.getUserName(), loginModel.getPassword(), loginModel.getMerchantId());
        login(httpServletRequest, seUser, token);
        return  seUser;
    }

    private SeUser loginVerify(HttpServletRequest httpServletRequest, LoginModel loginModel, Class group, String loginType) {
        //不是微信登陆和手机验证码登陆的需要判断图形验证码
//        if (!isWechatAccess(httpServletRequest) && !"phoneLogin".equals(loginType)) {
//            if (LoginModel.PasswordLogin.class.isAssignableFrom(group)) {
//                if (loginModel.getVerifyCode() == null || ("".trim()).equals(loginModel.getVerifyCode())) {
//                    CwException.throwIt("图形验证码不能为空");
//                } else {
//                    VerifyCodeUtil.verify(httpServletRequest, loginModel.getVerifyCode());
//                }
//            }
//        }
        SeUser seUser = userService.findByUserNameAndMerchantId(loginModel.getUserName(), loginModel.getMerchantId());
        if (seUser == null) {
//            CwException.throwIt("用户不存在");
            log.error("用户不存在");
        }
        if (ObjectHelper.isNotEmpty(seUser)&&!loginModel.getType().equalsIgnoreCase(seUser.getType())) {
            CwException.throwIt("用户类型不匹配");
        }
        return seUser;
    }

    private SeUser loginWithPhone(HttpServletRequest httpServletRequest, LoginModel loginModel,String wechatId, String loginType) {
        SeUser seUser = loginVerify(httpServletRequest, loginModel, LoginModel.PhoneLogin.class,loginType);
        if(ObjectHelper.isEmpty(seUser))throw new BusinessException(ENUM_EXCEPTION.E10011.code,ENUM_EXCEPTION.E10011.msg);
        seUser.setWechatId(wechatId);
//        SmsValidation.validate(httpServletRequest, loginModel.getSmsVerifyCode());
        AuthenticationToken token = new CwAuthenticationToken(loginModel.getUserName(), true, loginModel.getMerchantId());
        login(httpServletRequest, seUser, token);
        return seUser;
    }

    private SeUser loginWithToken(HttpServletRequest httpServletRequest, LoginModel loginModel,String wechatId, String loginType) {
        JSONObject netResult= GetMobileComponent.getMobile(loginModel.getOclToken());
        if(ObjectHelper.isNotEmpty(netResult.getJSONObject("result"))){
            String phoneNum=netResult.getJSONObject("result").getString("phoneNum");
            loginModel.setUserName(phoneNum);
            SeUser seUser = loginVerify(httpServletRequest, loginModel, LoginModel.PhoneLogin.class,loginType);
            if(ObjectHelper.isNotEmpty(seUser)){
                //如果用户已经注册进行正常无密码登录
                seUser.setWechatId(wechatId);
                AuthenticationToken token = new CwAuthenticationToken(loginModel.getUserName(), true, loginModel.getMerchantId());
                login(httpServletRequest, seUser, token);
                return seUser;
            }else{
                //如果不存在用户则将来源号设置为APP渠道并且新建用户
                SeUser newUser = new SeUser();
                newUser.setDisplayName(phoneNum);
                newUser.setUsername(phoneNum);
                newUser.setPhone(phoneNum);
                newUser.setMerchantId(1L);
                newUser.setType("user");
                newUser.setrId(1L);
                newUser.setPassword("");
                newUser.setSourceCode(loginModel.getChannelNo());
                seUser=userService.createUser(newUser);
                seUser.setWechatId(wechatId);
                AuthenticationToken token = new CwAuthenticationToken(loginModel.getUserName(), true, loginModel.getMerchantId());
                login(httpServletRequest, seUser, token);
                return seUser;
            }
            //如果用户为空则注册用户再进行无密码登录
        }else {
            throw new BusinessException(ENUM_EXCEPTION.E10033.code,netResult.getString("errorMsg"));
        }
    }

    public void loginWithWechatId(HttpServletRequest httpServletRequest, String wechatId, Long merchantId) {
        Assert.notNull(wechatId,"微信绑定登录openid为空!");
        Assert.notNull(merchantId,"微信绑定登录merchantId为空!");
        SeUser seUser = userService.findByWechatIdAndMerchantId(wechatId, merchantId);
        if (seUser == null) {
            CwException.throwIt("用户不存在");
        }
        AuthenticationToken token = new CwAuthenticationToken(seUser.getUsername(), true, merchantId);
        login(httpServletRequest, seUser, token);
    }

    public void loginWithDevModel(HttpServletRequest httpServletRequest, String userName, Long merchantId) {
        SeUser seUser = userService.findByUserNameAndMerchantId(userName, merchantId);
        if (seUser == null) {
            CwException.throwIt("用户不存在");
        }
        AuthenticationToken token = new CwAuthenticationToken(seUser.getUsername(), true, merchantId);
        login(httpServletRequest, seUser, token);
    }

    public UserDto wechatBinding(HttpServletRequest httpServletRequest, LoginModel loginModel, String wechatId, String loginType) throws Exception {
        logger.info("登录类型,{},openId,{}",loginType,wechatId);
        SeUser seUser=null;
        //如果没有传递登陆类型则默认设置为密码登录
        loginType=ENUM_LOGINTYPE.getType(loginType);
        //手机号无密码登录
        if(loginType.equals(ENUM_LOGINTYPE.PHONELOGIN.code)&&ObjectHelper.isEmpty(loginModel.getOclToken())){
          seUser =loginWithPhone(httpServletRequest, loginModel,wechatId,loginType);
        }else if(loginType.equals(ENUM_LOGINTYPE.PASSWORDLOGIN.code)&&ObjectHelper.isEmpty(loginModel.getOclToken())){
            //密码登录
           seUser =loginWithPassword(httpServletRequest, loginModel,wechatId,loginType);
        }
        if(loginType.equalsIgnoreCase(ENUM_LOGINTYPE.PHONELOGIN.code)&&ObjectHelper.isNotEmpty(loginModel.getOclToken())){
            //token登录
            seUser=loginWithToken(httpServletRequest, loginModel, wechatId, loginType);
        }
        //微信登录绑定openid
        if(isWechatAccess(httpServletRequest)){
           userService.updateUser(seUser, false);
        }

        //修改渠道计数
        if(ObjectHelper.isNotEmpty(seUser)&&seUser.getType().equalsIgnoreCase("user")&&ObjectHelper.isNotEmpty(seUser.getSourceCode())){
            discountSettingDomainService.incrementRegisNum(seUser.getPhone(),seUser.getSourceCode(),2);
            if(ObjectHelper.isNotEmpty(loginModel.getDevice())){
                SeUser userEntity=userService.findOne(seUser.getId());
                userEntity.setActived(true);
                userEntity.setActivedevice(loginModel.getDevice());
                userService.updateUser(userEntity,false);
            }
        }

        //记录登录日志
        if(loginModel.getDeviceNumber()!=null&&loginModel.getDeviceNumber().toUpperCase().contains("IDFA")) {
            LogDto logDto = new LogDto();
            logDto.setType(LogEnum.USER_LOGIN);
            logDto.setChannelNo(loginModel.getChannelNo());
            logDto.setAppName(loginModel.getAppName());
            logDto.setDeviceNumber(loginModel.getDeviceNumber());
            logDto.setApplyArea(loginModel.getApplyArea());
            logDto.setApplyDate(new Date());
            logDto.setUserId(seUser.getId());
            logAppService.create(logDto);
        }
        return ObjectProperUtil.compareAndValue(seUser,new UserDto(),true,new String[]{"merchantId","password","salt","roleIds","id"});
    }


    private void login(HttpServletRequest httpServletRequest, SeUser seUser, AuthenticationToken token) {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        } catch (AuthenticationException e) {
            CwException.throwIt(e.getMessage());
        }
        CPContext.SeUserInfo seUserInfo = Copier.copy(seUser, CPContext.SeUserInfo.class);
        httpServletRequest.getSession().setAttribute("seUserInfo", seUserInfo);
    }

    public static boolean isSessionTimeout(HttpServletRequest request) {
        String sessionId = getSessionIdFormCookie(request);
        HttpSession session = request.getSession();
        return sessionId != null && !session.getId().equalsIgnoreCase(sessionId);
    }

    private static String getSessionIdFormCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        logger.info(":::::::::::::::cookies:::{}",cookies);
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(Apps.getAppSessionCookieName())) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    private static boolean isWechatAccess(HttpServletRequest request) {
        return request.getHeader("User-Agent").contains("MicroMessenger");
    }
}
