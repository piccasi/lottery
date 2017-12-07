package com.tydic.uniform.hh.vo.rep;

public class RechargeQueryVo {
	private String requestSource;
	private String requestUser;
	private String requestId;
	private String type;
	private String value;
	private String staffId;
	private String payAcct;
	private String beginTime;
	private String endTime;
	private int pageSize;//每页存的条数
	private int pageNo; //第几页
	private int pageCount; //总页数
	
	
	
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getPayAcct() {
		return payAcct;
	}
	public void setPayAcct(String payAcct) {
		this.payAcct = payAcct;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public RechargeQueryVo() {
		super();
	}
	public RechargeQueryVo(String requestSource, String requestUser, String requestId, String type, String value,
			String beginTime, String endTime, int pageSize, int pageNo, int pageCount) {
		super();
		this.requestSource = requestSource;
		this.requestUser = requestUser;
		this.requestId = requestId;
		this.type = type;
		this.value = value;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.pageCount = pageCount;
	}
	@Override
	public String toString() {
		return "RechargeQueryVo [requestSource=" + requestSource + ", requestUser=" + requestUser + ", requestId="
				+ requestId + ", type=" + type + ", value=" + value + ", beginTime=" + beginTime + ", endTime="
				+ endTime + ", pageSize=" + pageSize + ", pageNo=" + pageNo + ", pageCount=" + pageCount + "]";
	}
	
}
