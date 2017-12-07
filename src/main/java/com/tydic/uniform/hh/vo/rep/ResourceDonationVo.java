package com.tydic.uniform.hh.vo.rep;

import java.util.List;

public class ResourceDonationVo {
	private String outid;
	
	private String number;
	private String validatecode;
	private List<ResourceDonationInputVo> presentinfolist;
	
	
	

	public String getOutid() {
		return outid;
	}

	public void setOutid(String outid) {
		this.outid = outid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getValidatecode() {
		return validatecode;
	}

	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}

	public List<ResourceDonationInputVo> getPresentinfolist() {
		return presentinfolist;
	}

	public void setPresentinfolist(List<ResourceDonationInputVo> presentinfolist) {
		this.presentinfolist = presentinfolist;
	}
	
}
