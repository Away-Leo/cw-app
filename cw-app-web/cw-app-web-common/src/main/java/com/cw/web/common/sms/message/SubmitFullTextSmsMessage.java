package com.cw.web.common.sms.message;

import com.cw.web.common.sms.util.AJsonEntity;

public class SubmitFullTextSmsMessage extends AJsonEntity {
	private String appkey;
	private String appsecret;
	private String templateid;
	private String phone;
	private String extnum;
	private String content;

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getTemplateid() {
		return templateid;
	}

	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getExtnum() {
		return extnum;
	}

	public void setExtnum(String extnum) {
		this.extnum = extnum;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
