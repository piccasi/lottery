package com.tydic.uniform.hh.vo.rep;

public class MealListVo {
	/**
	 * 号码信息
	 */
	private String qry_number;
	/**
	 * 套餐模式
		01 个人套餐
		02 加油包/积木套餐
		03合约
		04增值套餐 
		05员工套餐
		01：主套餐
		02：加油包
		不传则返回全部
	 */
	private String ofr_mode;
	/**
	 * 网络类型0：3G，1：4G，3：所有不传也返回全部
	 */
	private String cust_flag;
	/**
	 * 代理商渠道编码
	 */
	private String org_id;
	public String getQry_number() {
		return qry_number;
	}
	public void setQry_number(String qry_number) {
		this.qry_number = qry_number;
	}
	public String getOfr_mode() {
		return ofr_mode;
	}
	public void setOfr_mode(String ofr_mode) {
		this.ofr_mode = ofr_mode;
	}
	public String getCust_flag() {
		return cust_flag;
	}
	public void setCust_flag(String cust_flag) {
		this.cust_flag = cust_flag;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}
	
}
