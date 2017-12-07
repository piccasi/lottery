package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年10月15日 下午6:23:18
 * @ClassName RechargeRecordVo   1.10 会员充值记录查询VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月15日
 * @modify by reason:{方法名}:{原因}
 */
public class RechargeRecordVo {
	private String type;
	private String value;
	private String billcycleid;
	private String pageSize;
	private String pageNo;
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
	public String getBillcycleid() {
		return billcycleid;
	}
	public void setBillcycleid(String billcycleid) {
		this.billcycleid = billcycleid;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageNo() {
		return pageNo;
	}
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}

	
	
 
}
