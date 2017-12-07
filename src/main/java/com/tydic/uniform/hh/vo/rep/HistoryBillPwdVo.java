package com.tydic.uniform.hh.vo.rep;

/**
 * <p>
 * </p>
 * @author panxinxing 2016年01月08日 上午10:32:24
 * @ClassName HistoryBillPwdVo 服务密码验证
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public class HistoryBillPwdVo {
	private String cust_id;
	private String pwd;
	private String phoneNum;
	
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
}
