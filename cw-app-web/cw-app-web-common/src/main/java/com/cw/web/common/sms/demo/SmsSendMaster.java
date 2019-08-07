package com.cw.web.common.sms.demo;

import com.alibaba.fastjson.JSON;
import com.cw.web.common.sms.model.request.SmsSendRequest;
import com.cw.web.common.sms.model.response.SmsSendResponse;
import com.cw.web.common.sms.util.ChuangLanSmsUtil;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author tianyh
 * @Description:普通短信发送
 */
public class SmsSendMaster {
    public static final String charset = "utf-8";

    public static String sendSms(String phone,String content) throws UnsupportedEncodingException {
        String account = "N3131027";
        // 用户平台API密码(非登录密码)
        String password = "POzB64di1j8cbe";
        //请求地址请登录253云通讯自助通平台查看或者询问您的商务负责人获取
        String smsSingleRequestServerUrl = "https://smssh1.253.com/msg/send/json";
        //状态报告
        String report= "true";

        SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, content, phone,report);

        String requestJson = JSON.toJSONString(smsSingleRequest);

        System.out.println("before request string is: " + requestJson);

        String response = ChuangLanSmsUtil.sendSmsByPost(smsSingleRequestServerUrl, requestJson);

        System.out.println("response after request result is :" + response);

        SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);

        return smsSingleResponse.getMsgId();
    }
}
