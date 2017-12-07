package com.tydic.uniform.hh.vo.rep;

import com.tydic.uniform.hh.constant.BusinessType;

public class IdentifyCodeVo {
	private String login_nbr;
	
	private String mobile_170;
	
	private int expTime;
	
	private BusinessType type;
	
	private String code;

	public String getLogin_nbr() {
		return login_nbr;
	}

	public void setLogin_nbr(String login_nbr) {
		this.login_nbr = login_nbr;
	}

	public String getMobile_170() {
		return mobile_170;
	}

	public void setMobile_170(String mobile_170) {
		this.mobile_170 = mobile_170;
	}

	public int getExpTime() {
		return expTime;
	}

	public void setExpTime(int expTime) {
		this.expTime = expTime;
	}

	public BusinessType getType() {
		return type;
	}

	public void setType(BusinessType type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "IdentifyCodeVo [login_nbr=" + login_nbr + ", mobile_170=" + mobile_170 + ", expTime=" + expTime
				+ ", type=" + type + ", code=" + code + "]";
	}
	

	
}
