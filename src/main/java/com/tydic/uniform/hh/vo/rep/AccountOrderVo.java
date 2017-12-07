package com.tydic.uniform.hh.vo.rep;

import java.util.List;


/**
 * <p></p>
 * @author ghp 2015年9月29日 下午3:30:03
 * @ClassName AccountOrderVo   开户新增订单接口VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public class AccountOrderVo {
	//1订单信息
	private String business_type;
	private String cust_id;
	private String ext_order_id;
	private String order_type;
	private String pay_status;
	private String sale_order_id;
	private String appendix;
	
	
	//2       套餐信息   SALE_OFFER_INST
	
	private List<AccountOfferBaseVo> sale_offer_instlist;
	
	//3费用信息 "FEE_ITEM"
	
	private List<AccountFee_itemBaseVo> servicedtolist;
	
	//4客户信息 "SALE_CUST"
	private String auth_flag;
	private String cert_nbr;
	private String cert_type;
//	private String cust_id;
	private String custname;

	private String birthdate;
//	private String new_flag;
	private String email;
//	private String sale_order_id;
	
	
	//6产品信息 "SALE_PROD_INST"
	private String acc_nbr;
	private String new_flag;
	private String product_id;
	private String prod_inst_id;
//	private String sale_order_id;
	
	//7产品实例属性 "SALE_PROD_INST_ATTR"
	private String attr_id;
	private String attr_name;
	private String attr_value;
//	private String prod_inst_id;
	
	//8 收货信息 "RECEIVE_INFO"
	private String address;
	private String mobile;
	private String receive_name;
//	private String sale_order_id;
	
	
	//9NUMBER_INFO号码信息
	private String city_code;
	private String city_name;
	private String number_level;
//	private String prod_inst_id;
	private String provice_name;
	private String province_code;
	public String getBusiness_type() {
		return business_type;
	}
	public void setBusiness_type(String business_type) {
		this.business_type = business_type;
	}
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
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getSale_order_id() {
		return sale_order_id;
	}
	public void setSale_order_id(String sale_order_id) {
		this.sale_order_id = sale_order_id;
	}
	public String getAppendix() {
		return appendix;
	}
	public void setAppendix(String appendix) {
		this.appendix = appendix;
	}
	public List<AccountOfferBaseVo> getSale_offer_instlist() {
		return sale_offer_instlist;
	}
	public void setSale_offer_instlist(List<AccountOfferBaseVo> sale_offer_instlist) {
		this.sale_offer_instlist = sale_offer_instlist;
	}
	public List<AccountFee_itemBaseVo> getServicedtolist() {
		return servicedtolist;
	}
	public void setServicedtolist(List<AccountFee_itemBaseVo> servicedtolist) {
		this.servicedtolist = servicedtolist;
	}
	public String getAuth_flag() {
		return auth_flag;
	}
	public void setAuth_flag(String auth_flag) {
		this.auth_flag = auth_flag;
	}
	public String getCert_nbr() {
		return cert_nbr;
	}
	public void setCert_nbr(String cert_nbr) {
		this.cert_nbr = cert_nbr;
	}
	public String getCert_type() {
		return cert_type;
	}
	public void setCert_type(String cert_type) {
		this.cert_type = cert_type;
	}

	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	
	
	public String getNew_flag() {
		return new_flag;
	}
	public void setNew_flag(String new_flag) {
		this.new_flag = new_flag;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProd_inst_id() {
		return prod_inst_id;
	}
	public void setProd_inst_id(String prod_inst_id) {
		this.prod_inst_id = prod_inst_id;
	}
	public String getAttr_id() {
		return attr_id;
	}
	public void setAttr_id(String attr_id) {
		this.attr_id = attr_id;
	}
	public String getAttr_name() {
		return attr_name;
	}
	public void setAttr_name(String attr_name) {
		this.attr_name = attr_name;
	}
	public String getAttr_value() {
		return attr_value;
	}
	public void setAttr_value(String attr_value) {
		this.attr_value = attr_value;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getReceive_name() {
		return receive_name;
	}
	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getCity_name() {
		return city_name;
	}
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}
	public String getNumber_level() {
		return number_level;
	}
	public void setNumber_level(String number_level) {
		this.number_level = number_level;
	}
	public String getProvice_name() {
		return provice_name;
	}
	public void setProvice_name(String provice_name) {
		this.provice_name = provice_name;
	}
	public String getProvince_code() {
		return province_code;
	}
	public void setProvince_code(String province_code) {
		this.province_code = province_code;
	}
	
	
}
