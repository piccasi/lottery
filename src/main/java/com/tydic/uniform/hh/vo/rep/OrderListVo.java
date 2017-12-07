package com.tydic.uniform.hh.vo.rep;
/**
 * 
 * <p></p>
 * @author gengtian 2015年10月13日 下午12:31:42
 * @ClassName OrderListVo
 * @Description TODO 订单查询接口入参映射
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月13日
 * @modify by reason:{方法名}:{原因}
 */
public class OrderListVo{
	private String channel_code;
	private String ext_system;
	private String order_id;
	private String start_time;
	private String end_time;
	private String cust_id;
	private String status_cd;
	private String  process_sts;
	private String pay_status;
	private String page;
	private String page_no;
	
	public String getChannel_code() {
		return channel_code;
	}
	
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	
	public String getExt_system() {
		return ext_system;
	}
	
	public void setExt_system(String ext_system) {
		this.ext_system = ext_system;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
	public String getStart_time() {
		return start_time;
	}
	
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEnd_time() {
		return end_time;
	}
	
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public String getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	
	public String getStatus_cd() {
		return status_cd;
	}
	
	public void setStatus_cd(String status_cd) {
		this.status_cd = status_cd;
	}
	
	public String getProcess_sts() {
		return process_sts;
	}
	
	public void setProcess_sts(String process_sts) {
		this.process_sts = process_sts;
	}
	
	public String getPay_status() {
		return pay_status;
	}
	
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	
	public String getPage() {
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	
	public String getPage_no() {
		return page_no;
	}
	
	public void setPage_no(String page_no) {
		this.page_no = page_no;
	}
	
	
}