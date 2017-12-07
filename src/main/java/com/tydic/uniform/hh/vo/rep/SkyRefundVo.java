package com.tydic.uniform.hh.vo.rep;

public class SkyRefundVo {
	private String payAcct;//代理商账号
	private String pwd;//代理商密码
	private String requestSource; //自服务平台标识或IP地址
	private String requestUser; //用户如果登录填写登录用户名
	private String requestId; //请求流水
	private String requestTime; //请求时间
	private String oldRequestTime; //被冲正的充值流水号
	private String destinationId; //被充值用户号码
	private int destinationAttr; //被充值用户属性
	private int objType; //号码类型  1：账号   2：客户  3：用户  99：其他
	
	public String getPayAcct() {
		return payAcct;
	}
	public void setPayAcct(String payAcct) {
		this.payAcct = payAcct;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getRequestSource() {
		return requestSource;
	}
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}
	public String getRequestUser() {
		return requestUser;
	}
	public void setRequestUser(String requestUser) {
		this.requestUser = requestUser;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getOldRequestTime() {
		return oldRequestTime;
	}
	public void setOldRequestTime(String oldRequestTime) {
		this.oldRequestTime = oldRequestTime;
	}
	public String getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}
	public int getDestinationAttr() {
		return destinationAttr;
	}
	public void setDestinationAttr(int destinationAttr) {
		this.destinationAttr = destinationAttr;
	}
	public int getObjType() {
		return objType;
	}
	public void setObjType(int objType) {
		this.objType = objType;
	}
	
	@Override
	public String toString() {
		return "AirRefundVo [payAcct=" + payAcct + ", pwd=" + pwd + ", requestSource=" + requestSource
				+ ", requestUser=" + requestUser + ", requestId=" + requestId + ", requestTime=" + requestTime
				+ ", oldRequestTime=" + oldRequestTime + ", destinationId=" + destinationId + ", destinationAttr="
				+ destinationAttr + ", objType=" + objType + "]";
	}
	public SkyRefundVo(String payAcct, String pwd, String requestSource, String requestUser, String requestId,
			String requestTime, String oldRequestTime, String destinationId, int destinationAttr, int objType) {
		super();
		this.payAcct = payAcct;
		this.pwd = pwd;
		this.requestSource = requestSource;
		this.requestUser = requestUser;
		this.requestId = requestId;
		this.requestTime = requestTime;
		this.oldRequestTime = oldRequestTime;
		this.destinationId = destinationId;
		this.destinationAttr = destinationAttr;
		this.objType = objType;
	}
	public SkyRefundVo() {
	}
}
