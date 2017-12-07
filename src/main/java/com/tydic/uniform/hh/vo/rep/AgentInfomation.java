package com.tydic.uniform.hh.vo.rep;

public class AgentInfomation {

	private String payacct;
	private String paypwd;

	public String getPayacct() {
		return payacct;
	}

	public void setPayacct(String payacct) {
		this.payacct = payacct;
	}

	public String getPaypwd() {
		return paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}

	public AgentInfomation() {
		super();
	}

	public AgentInfomation(String payacct, String paypwd) {
		this.payacct = payacct;
		this.paypwd = paypwd;
	}

	@Override
	public String toString() {
		return "AgentInfomation [payacct=" + payacct + ", paypwd=" + paypwd + "]";
	}

}
