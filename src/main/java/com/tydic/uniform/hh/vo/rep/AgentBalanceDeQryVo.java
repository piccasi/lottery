package com.tydic.uniform.hh.vo.rep;
/**
 * 
* @ClassName: AgentBalanceDeQryVo 
* @Description: AgentBalanceDeQryVo
* @author 天源迪科
* @date 2016年9月1日 下午4:33:43 
*
 */
public class AgentBalanceDeQryVo {
	/**
	 * 页码
	 */
	private String pageNumber;
	/**
	 * 
	 */
	private String staffId;
	/**
	 * 开始日期
	 */
	private String beginDate;
	/**
	 * 结束日期
	 */
	private String endDate;
	/**
	 * 
	 */
	private String payAcct;
	/**
	 * 一夜的大小
	 */
	private String pageSize;
	
	
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPayAcct() {
		return payAcct;
	}
	public void setPayAcct(String payAcct) {
		this.payAcct = payAcct;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
}
