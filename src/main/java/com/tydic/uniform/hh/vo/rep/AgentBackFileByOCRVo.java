package com.tydic.uniform.hh.vo.rep;

public class AgentBackFileByOCRVo {
	private String cust_id;
	/**
	 * 电子邮箱
	 */
	private String email;
	private String birthdate;
	private String mobile;
	/**
	 * 会员昵称
	 */
	private String nick_name; //会员真实姓名
	private String sex;
	/**
	 * 联系地址
	 */
	private String contact_addr;
	/**
	 * 证件地址
	 */
	private String certi_addr;
	/**
	 * 会员名称
	 */
	private String cust_name;
	/**
	 * 身份证
	 */
	private String certi_nbr;
	/**
	 * 附件，如果有多个附件，则以英文逗号分隔
	 */
	private String appendix;
	/**
	 * 要反档的号码
	 */
	private String mobile_170;
	/**
	 * 操作员id
	 */
	private String systemuserid;
	/**
	 * 预校验：1；正式提交传其他值，如2
	 */
	private String operate_type;
	/**
	 * 是否APP调用1：是0：否.始终为1
	 */
	private String app_flag;
	/**
	 * 卡号后6位
	 */
	private String iccid;
	/**
	 * 身份校验方式：NFC：1；国政通：2；OTG：3

	 */
	private String check_type;
	/**
	 * 图片一的名称
	 */
	private String image1;
	/**
	 * 图片二的名称
	 */
	private String image2;
	/**
	 * 图片三的名称
	 */
	private String image3;
	//时间戳
	private String date_string;
	
	
	
	public String getDate_string() {
		return date_string;
	}
	public void setDate_string(String date_string) {
		this.date_string = date_string;
	}
	public String getSystemuserid() {
		return systemuserid;
	}
	public void setSystemuserid(String systemuserid) {
		this.systemuserid = systemuserid;
	}
	public String getMobile_170() {
		return mobile_170;
	}
	public void setMobile_170(String mobile_170) {
		this.mobile_170 = mobile_170;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getContact_addr() {
		return contact_addr;
	}
	public void setContact_addr(String contact_addr) {
		this.contact_addr = contact_addr;
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
	public String getOperate_type() {
		return operate_type;
	}
	public void setOperate_type(String operate_type) {
		this.operate_type = operate_type;
	}
	public String getApp_flag() {
		return app_flag;
	}
	public void setApp_flag(String app_flag) {
		this.app_flag = app_flag;
	}
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getCheck_type() {
		return check_type;
	}
	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}
	public String getImage1() {
		return image1;
	}
	public void setImage1(String image1) {
		this.image1 = image1;
	}
	public String getImage2() {
		return image2;
	}
	public void setImage2(String image2) {
		this.image2 = image2;
	}
	public String getImage3() {
		return image3;
	}
	public void setImage3(String image3) {
		this.image3 = image3;
	}
	

}
