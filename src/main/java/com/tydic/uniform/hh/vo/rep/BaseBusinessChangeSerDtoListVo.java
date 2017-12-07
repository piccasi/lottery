package com.tydic.uniform.hh.vo.rep;

import java.util.List;

public class BaseBusinessChangeSerDtoListVo {
	private String action;
	private Long efftype;
	private Long expduration;
	private Long number;
	private List<BaseBusinessChangeSerAttrDtoListVo> serviceattrdtolist;
	private String servicecode;
	private String timeunit;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Long getEfftype() {
		return efftype;
	}
	public void setEfftype(Long efftype) {
		this.efftype = efftype;
	}

	public Long getExpduration() {
		return expduration;
	}
	public void setExpduration(Long expduration) {
		this.expduration = expduration;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public List<BaseBusinessChangeSerAttrDtoListVo> getServiceattrdtolist() {
		return serviceattrdtolist;
	}
	public void setServiceattrdtolist(List<BaseBusinessChangeSerAttrDtoListVo> serviceattrdtolist) {
		this.serviceattrdtolist = serviceattrdtolist;
	}
	public String getServicecode() {
		return servicecode;
	}
	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}
	public String getTimeunit() {
		return timeunit;
	}
	public void setTimeunit(String timeunit) {
		this.timeunit = timeunit;
	}
	
	
}
