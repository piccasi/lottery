package com.tydic.uniform.hh.vo.rep;

import java.util.List;

public class BaseBusinessChangeVo {
	
	private String jdorderid;
	private String msisdn;
	private String contactchannle;
	private List<BaseBusinessChangeSerDtoListVo> servicedtolist;
	private String usereventcode;
	
	public String getJdorderid() {
		return jdorderid;
	}
	public void setJdorderid(String jdorderid) {
		this.jdorderid = jdorderid;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getContactchannle() {
		return contactchannle;
	}
	public void setContactchannle(String contactchannle) {
		this.contactchannle = contactchannle;
	}
	public List<BaseBusinessChangeSerDtoListVo> getServicedtolist() {
		return servicedtolist;
	}
	public void setServicedtolist(List<BaseBusinessChangeSerDtoListVo> servicedtolist) {
		this.servicedtolist = servicedtolist;
	}
	public String getUsereventcode() {
		return usereventcode;
	}
	public void setUsereventcode(String usereventcode) {
		this.usereventcode = usereventcode;
	}
	
	
}
