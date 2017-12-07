package com.tydic.uniform.hh.vo.rep;

import java.util.List;

/**
 * PlaceOrder
 * <p></p>
 * @author Administrator 2015年10月16日 下午6:01:28
 * @ClassName PlaceOrderVo
 * @Description eshop 下单
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月16日
 * @modify by reason:{方法名}:{原因}
 */
public class PlaceOrderVo {
	private String address;
	private String consignee;
	private String detailaddress;
	private String mobilenumber;
	private String regionid;
	private String telnumber;
	private String orderflag;
	private long orderfrom;
	private String orderfromview;
	private List<PlaceOrderNetWorkVo> ordernetworkslist;
	private List<PlaceOrderNumbersVo> ordernumberslist;
	private String offerid;
	private List<PlaceOrderPaymentsVo> orderpaymentslist;
	private String orderremark;
	private String ordersource;
	private String ordersourceview;
	private long payamt;
	private long promotionamt;
	private long totalamt;
	private long totalamtview;
	private String type;
	private String typeview;
	private String useraccount;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getDetailaddress() {
		return detailaddress;
	}
	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}
	public String getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getRegionid() {
		return regionid;
	}
	public void setRegionid(String regionid) {
		this.regionid = regionid;
	}
	public String getTelnumber() {
		return telnumber;
	}
	public void setTelnumber(String telnumber) {
		this.telnumber = telnumber;
	}
	public String getOrderflag() {
		return orderflag;
	}
	public void setOrderflag(String orderflag) {
		this.orderflag = orderflag;
	}
	public long getOrderfrom() {
		return orderfrom;
	}
	public void setOrderfrom(long orderfrom) {
		this.orderfrom = orderfrom;
	}
	public String getOrderfromview() {
		return orderfromview;
	}
	public void setOrderfromview(String orderfromview) {
		this.orderfromview = orderfromview;
	}
	public List<PlaceOrderNetWorkVo> getOrdernetworkslist() {
		return ordernetworkslist;
	}
	public void setOrdernetworkslist(List<PlaceOrderNetWorkVo> ordernetworkslist) {
		this.ordernetworkslist = ordernetworkslist;
	}
	public List<PlaceOrderNumbersVo> getOrdernumberslist() {
		return ordernumberslist;
	}
	public void setOrdernumberslist(List<PlaceOrderNumbersVo> ordernumberslist) {
		this.ordernumberslist = ordernumberslist;
	}
	public String getOfferid() {
		return offerid;
	}
	public void setOfferid(String offerid) {
		this.offerid = offerid;
	}
	public List<PlaceOrderPaymentsVo> getOrderpaymentslist() {
		return orderpaymentslist;
	}
	public void setOrderpaymentslist(List<PlaceOrderPaymentsVo> orderpaymentslist) {
		this.orderpaymentslist = orderpaymentslist;
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
	public long getPayamt() {
		return payamt;
	}
	public void setPayamt(long payamt) {
		this.payamt = payamt;
	}
	public long getPromotionamt() {
		return promotionamt;
	}
	public void setPromotionamt(long promotionamt) {
		this.promotionamt = promotionamt;
	}
	public long getTotalamt() {
		return totalamt;
	}
	public void setTotalamt(long totalamt) {
		this.totalamt = totalamt;
	}
	public long getTotalamtview() {
		return totalamtview;
	}
	public void setTotalamtview(long totalamtview) {
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
	
}
