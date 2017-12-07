package com.tydic.uniform.hh.vo.rep;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

public class SsoIdetntyInfoVo implements Serializable{

	/**
	 * 一号通实名补登记校验接口入参
	 */
	private static final long serialVersionUID = -7346108143723084188L;

	@JSONField(name = "name")
	private String name; // 会员名称
	
	@JSONField(name = "userId")
	private String userId; 
	
	@JSONField(name = "mobile")
	private String mobile; // 
	
	@JSONField(name = "certiNbr")
	private String certiNbr; //

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCertiNbr() {
		return certiNbr;
	}

	public void setCertiNbr(String certiNbr) {
		this.certiNbr = certiNbr;
	}
}
