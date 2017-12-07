package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2016年8月10日15:58:17
 * @ClassName CallerIDController
 * @Description TODO 代理商关闭来电显示功能
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月28日
 * @modify by reason:{方法名}:{原因}
 */
public class CallerIDVo {

	private String msisdn;
	private String serviceCode;
	private String action;
	
	
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	
	
}
