package com.tydic.uniform.hh.vo.rep;

public class AgentDepositVo {
	private String type;
	private String staffId;
	private String requestId;
	private String payAcct;
	private String charge;
	private String freeType;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getPayAcct() {
		return payAcct;
	}
	public void setPayAcct(String payAcct) {
		this.payAcct = payAcct;
	}
	public String getCharge() {
		return charge;
	}
	public void setCharge(String charge) {
		this.charge = charge;
	}
	public String getFreeType() {
		return freeType;
	}
	public void setFreeType(String freeType) {
		this.freeType = freeType;
	}
	public AgentDepositVo() {
		super();
	}
	public AgentDepositVo(String type, String staffId, String requestId, String payAcct, String charge,
			String freeType) {
		super();
		this.type = type;
		this.staffId = staffId;
		this.requestId = requestId;
		this.payAcct = payAcct;
		this.charge = charge;
		this.freeType = freeType;
	}
	@Override
	public String toString() {
		return "AgentDepositVo [type=" + type + ", staffId=" + staffId + ", requestId=" + requestId + ", payAcct="
				+ payAcct + ", charge=" + charge + ", freeType=" + freeType + "]";
	}
	
}
