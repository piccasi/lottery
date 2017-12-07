package com.tydic.uniform.hh.vo.rep;

import java.util.List;

public class RechargeOrderVo {
	private Long orderfrom;
	private String orderfromview;
	private List<RechargeOrderPayVo> orderpaymentslist;
	private Long rcgamt;
	private String rcgmobile;
	private String orderremark;
	private String ordersource;
	private String ordersourceview;
	private Long payamt;
	private Long promotionamt;
	private Long totalamt;
	private float totalamtview;
	private String type;
	private String typeview;
	private String useraccount;
	private String orderflag;
	
	public Long getOrderfrom() {
		return orderfrom;
	}
	public void setOrderfrom(Long orderfrom) {
		this.orderfrom = orderfrom;
	}
	public String getOrderfromview() {
		return orderfromview;
	}
	public void setOrderfromview(String orderfromview) {
		this.orderfromview = orderfromview;
	}
	public List<RechargeOrderPayVo> getOrderpaymentslist() {
		return orderpaymentslist;
	}
	public void setOrderpaymentslist(List<RechargeOrderPayVo> orderpaymentslist) {
		this.orderpaymentslist = orderpaymentslist;
	}
	public Long getRcgamt() {
		return rcgamt;
	}
	public void setRcgamt(Long rcgamt) {
		this.rcgamt = rcgamt;
	}
	public String getRcgmobile() {
		return rcgmobile;
	}
	public void setRcgmobile(String rcgmobile) {
		this.rcgmobile = rcgmobile;
	}
	public String getOrderremark() {
		return orderremark;
	}
	public void setOrderremark(String orderremark) {
		this.orderremark = orderremark;
	}
	public String getOrdersource() {
		return ordersource;
	}
	public void setOrdersource(String ordersource) {
		this.ordersource = ordersource;
	}
	public String getOrdersourceview() {
		return ordersourceview;
	}
	public void setOrdersourceview(String ordersourceview) {
		this.ordersourceview = ordersourceview;
	}
	public Long getPayamt() {
		return payamt;
	}
	public void setPayamt(Long payamt) {
		this.payamt = payamt;
	}
	public Long getPromotionamt() {
		return promotionamt;
	}
	public void setPromotionamt(Long promotionamt) {
		this.promotionamt = promotionamt;
	}
	public Long getTotalamt() {
		return totalamt;
	}
	public void setTotalamt(Long totalamt) {
		this.totalamt = totalamt;
	}
	public float getTotalamtview() {
		return totalamtview;
	}
	public void setTotalamtview(float totalamtview) {
		this.totalamtview = totalamtview;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeview() {
		return typeview;
	}
	public void setTypeview(String typeview) {
		this.typeview = typeview;
	}
	public String getUseraccount() {
		return useraccount;
	}
	public void setUseraccount(String useraccount) {
		this.useraccount = useraccount;
	}
	public String getOrderflag() {
		return orderflag;
	}
	public void setOrderflag(String orderflag) {
		this.orderflag = orderflag;
	}
	
	
	
}
