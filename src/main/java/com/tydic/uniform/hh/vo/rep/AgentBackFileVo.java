/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author ghp
 * @date: 2015年11月28日 下午6:22:54
 * @Title: AgentBackFileVo.java
 * @Package com.tydic.uniform.hh.vo.rep
 * @Description: TODO
 */
package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年11月28日 下午6:22:54
 * @ClassName AgentBackFileVo
 * @Description TODO 代理商返档Vo
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月28日
 * @modify by reason:{方法名}:{原因}
 */
public class AgentBackFileVo {
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
	private String nick_name;
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
	 * 匹配相似度
	 */
	private String confidence;
	/**
	 * Excl批开套餐为空时，返档需要传办理的套餐ID
	 */
	private String offr_id;
	
	private String org_id;
	
	private String pwd;
	
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	public String getConfidence() {
		return confidence;
	}
	public void setConfidence(String confidence) {
		this.confidence = confidence;
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
	public String getOffr_id() {
		return offr_id;
	}
	public void setOffr_id(String offr_id) {
		this.offr_id = offr_id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	
}
