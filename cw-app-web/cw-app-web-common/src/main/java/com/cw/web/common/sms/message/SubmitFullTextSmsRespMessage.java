package com.cw.web.common.sms.message;


import com.cw.web.common.sms.util.AJsonEntity;

public class SubmitFullTextSmsRespMessage extends AJsonEntity {
	private Integer code;
	private String msg;
	private String msgid;
	private String errPhone;
	private String blackPhone;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getErrPhone() {
		return errPhone;
	}

	public void setErrPhone(String errPhone) {
		this.errPhone = errPhone;
	}

	public String getBlackPhone() {
		return blackPhone;
	}

	public void setBlackPhone(String blackPhone) {
		this.blackPhone = blackPhone;
	}
}
