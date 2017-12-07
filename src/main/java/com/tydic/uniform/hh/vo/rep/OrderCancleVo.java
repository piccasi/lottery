package com.tydic.uniform.hh.vo.rep;

public class OrderCancleVo {
//	"CUST_ID":"31000000028377",
//	"EXT_ORDER_ID":"-1",
//	"ORDER_TYPE":"2000",
//	"SALE_ORDER_ID":"100071451887025198",
//	"SEL_IN_ORG_ID":"10002"
	private String cust_id;
	private String ext_order_id;
	private String order_type;
	private String sale_order_id;
	private String sel_in_org_id;
	
	
//	"AUTH_FLAG":"1",
//	"CUST_ID":"31000000028377",
//	"NEW_FLAG":"1",
//	"SALE_ORDER_ID":"$581007$"
	private String auth_flag;
	private String new_flag;
	private String sale_order_id_id;
	
	
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getExt_order_id() {
		return ext_order_id;
	}
	public void setExt_order_id(String ext_order_id) {
		this.ext_order_id = ext_order_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getSale_order_id() {
		return sale_order_id;
	}
	public void setSale_order_id(String sale_order_id) {
		this.sale_order_id = sale_order_id;
	}
	public String getSel_in_org_id() {
		return sel_in_org_id;
	}
	public void setSel_in_org_id(String sel_in_org_id) {
		this.sel_in_org_id = sel_in_org_id;
	}
	public String getAuth_flag() {
		return auth_flag;
	}
	public void setAuth_flag(String auth_flag) {
		this.auth_flag = auth_flag;
	}
	public String getNew_flag() {
		return new_flag;
	}
	public void setNew_flag(String new_flag) {
		this.new_flag = new_flag;
	}
	public String getSale_order_id_id() {
		return sale_order_id_id;
	}
	public void setSale_order_id_id(String sale_order_id_id) {
		this.sale_order_id_id = sale_order_id_id;
	}
	
	
	
	
}
