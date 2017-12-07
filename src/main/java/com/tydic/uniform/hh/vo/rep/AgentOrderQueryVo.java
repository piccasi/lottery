package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年9月29日 下午3:30:03
 * @ClassName AccountOrderVo   开户新增订单接口VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public class AgentOrderQueryVo {

	private String mobile_170;
	private String startDate;
	private String endDate;
	private String systemuserid;
	private String org_id;
	private String orderType;
	private String orderId;
	private String userEventCode;
	
	public String getMobile_170() {
		return mobile_170;
	}
	public void setMobile_170(String mobile_170) {
		this.mobile_170 = mobile_170;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getUserEventCode() {
		return userEventCode;
	}
	public void setUserEventCode(String userEventCode) {
		this.userEventCode = userEventCode;
	}
	
}
