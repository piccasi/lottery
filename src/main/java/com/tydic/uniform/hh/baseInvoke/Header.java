/*
 * Copyright  2014，the original authors or Tianyuan DIC Computer Co., Ltd.
 *
 * Please see the tydic success stories and it solutions 
 *
 *      http://www.tydic.com/Default.aspx
 *
 * Statement: This document's code after sufficiently has not permitted does not have 
 * any way dissemination and the change, once discovered violates the stipulation, will
 * receive the criminal sanction.
 * Address: 3/F,T3 Building, South 7th Road, South Area, Hi-tech Industrial park, Shenzhen, P.R.C.
 * Email: webmaster@tydic.com　
 * Tel: +86 755 26745688 
 */

package com.tydic.uniform.hh.baseInvoke;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *	//TODO
 *
 * @author Sun Liang {@link sunliang@tydic.com} 
 * @version  PNT Aug 28, 2014 11:08:24 AM 
 * @since 2.0
 **/
public class Header implements Serializable
{
	public static final String RC_SUCCESS="RC2000000000";
	
	@JSONField(name="LENGTH")
	private String length;
	
	@JSONField(name="MID")
	private String mid;
	/**
	 * 服务编码
	 */
	@JSONField(name="SID")
	private String sid;
	
	@JSONField(name="SYSID")
	private String sysId;
	
	@JSONField(name="SIGN")
	private String sign;
	
	@JSONField(name="TOKEN")
	private String token;
	
	@JSONField(name="FORMAT")
	private String format;
	
	@JSONField(name="REQTIME")
	private String reqTime;
	
	@JSONField(name="REPTIME")
	private String repTime;
	
	@JSONField(name="RC")
	private String rc;
	
	
	
	public void getHeader(Header head)
	{
		this.setSid(head.getSid());
		this.setFormat(head.getFormat());
		this.setSign(head.getSign());
		this.setReqTime(head.getReqTime());
		this.setMid(head.getMid());
		this.setSysId(head.getSysId());
		this.setSign(head.getSign());
		this.setToken(head.getToken());
	}
	/**
	 * 0 表示成功
	 * 9001 systemid 错误
	 * 9002 smstype  错误
	 * 9003 msisdn   错误
	 * 9004 msgcontent 错误  
	 * 9005 systemid 与 smstype不匹配
	 * 9006 不允许下发时段
	 * 9007 srctermid 错误
	 * 9999 其他错误
	 * 
	 */
	@JSONField(name="DS")
	private String ds;
	

	public String getLength() {
		return length;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSid() {
		return sid;
	}

	
	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}

	public String getRepTime() {
		return repTime;
	}

	public void setRepTime(String repTime) {
		this.repTime = repTime;
	}

	public String getRc() {
		return rc;
	}

	public void setRc(String rc) {
		this.rc = rc;
	}

	public String getDs() {
		return ds;
	}

	public void setDs(String ds) {
		this.ds = ds;
	}

	public void setLength(String length) {
		this.length = length;
	}
}
