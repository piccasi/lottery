package com.tydic.uniform.hh.vo.rep;

/**
 * <p>
 * </p>
 * @author panxinxing 2016年01月07日 上午10:32:24
 * @ClassName HistoryBillInfoVo 我的账单详情
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public class HistoryBillInfoVo {
	private String cust_id;
	private String beginDate;
	private String endDate;
	private String index;
	private String records;
	private String qryType;
	
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
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
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getRecords() {
		return records;
	}
	public void setRecords(String records) {
		this.records = records;
	}
	public String getQryType() {
		return qryType;
	}
	public void setQryType(String qryType) {
		this.qryType = qryType;
	}
	
}
