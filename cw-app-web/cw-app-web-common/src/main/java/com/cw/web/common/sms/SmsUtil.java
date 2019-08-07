package com.cw.web.common.sms;

import com.cw.web.common.sms.message.SubmitFullTextSmsMessage;
import com.cw.web.common.sms.message.SubmitFullTextSmsRespMessage;
import com.cw.web.common.sms.util.JsonUtil;
import com.cw.web.common.sms.util.http.HttpClientUtil;

public class SmsUtil {

	/**
	 * @Description 短信发送接口
	 * @param url 短信发送地址
	 * @param message 短信发送的内容
	 * @return
	 * @throws Exception
	 */
	public static SubmitFullTextSmsRespMessage sendSms(String url, SubmitFullTextSmsMessage message)
			throws Exception {
		String resultJson = HttpClientUtil.sendPostRequest(url, message.toJsonString(), "UTF-8");
		// 发送出现异常
		if (resultJson.equals("failed")) {
			throw new Exception("短信发送失败");
		}

		SubmitFullTextSmsRespMessage respMsg = JsonUtil.getObjectFromJsonString(resultJson,
				SubmitFullTextSmsRespMessage.class);

		return respMsg;
	}

	public static SubmitFullTextSmsRespMessage send(String appkey,String appSecret,String phone,String templateid,String content){
		SubmitFullTextSmsMessage message=new SubmitFullTextSmsMessage();
		message.setAppkey(appkey);
		message.setAppsecret(appSecret);
		message.setPhone(phone);
		message.setTemplateid(templateid);
		message.setContent(content);
		try {
			return sendSms("http://www.cqhuatang.com/SMSServer/sendFullTextSms",message);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
