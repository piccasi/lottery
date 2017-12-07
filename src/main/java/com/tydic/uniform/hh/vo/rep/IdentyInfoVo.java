package com.tydic.uniform.hh.vo.rep;

public class IdentyInfoVo {
	/**
	 * 代理商编号
	 */
	private String systemuserid;
	/**
	 * 代理商渠道编码
	 */
	private String org_id;
	/**
	 * 登录账号
	 */
	private String login_nbr;
	
	/**
	 * 身份证地址
	 */
	private String certi_addr;
	
	/**
	 * 姓名
	 */
	private String cust_name;
	
	/**
	 * 身份证号码
	 */
	private String certi_nbr;
	
	/**
	 * 附件，图片名称，多个用逗号分隔
	 */
	private String appendix;
	
	/**
	 * ICCID后六位
	 */
	private String iccid;
	
	/**
	 * 联系方式
	 */
	private String recentMobile;
	
	/**
	 * 分值
	 */
	private String confidence;
	
	/**
	 * 短信验证成功随机码
	 */
	private String code;
	
	/**
	 * 实名补登号码
	 */
	private String mobile;
	
	private String contact_address; //开户人联系地址
	
	public String getContact_address() {
		return contact_address;
	}
	public void setContact_address(String contact_address) {
		this.contact_address = contact_address;
	}
	public String getSystemuserid() {
		return systemuserid;
	}
	public void setSystemuserid(String systemuserid) {
		this.systemuserid = systemuserid;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getLogin_nbr() {
		return login_nbr;
	}
	public void setLogin_nbr(String login_nbr) {
		this.login_nbr = login_nbr;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCerti_addr() {
		return certi_addr;
	}
	public void setCerti_addr(String certi_addr) {
		this.certi_addr = certi_addr;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getCerti_nbr() {
		return certi_nbr;
	}
	public void setCerti_nbr(String certi_nbr) {
		this.certi_nbr = certi_nbr;
	}
	public String getAppendix() {
		return appendix;
	}
	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getRecentMobile() {
		return recentMobile;
	}
	public void setRecentMobile(String recentMobile) {
		this.recentMobile = recentMobile;
	}
	public String getConfidence() {
		return confidence;
	}
	public void setConfidence(String confidence) {
		this.confidence = confidence;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}	
