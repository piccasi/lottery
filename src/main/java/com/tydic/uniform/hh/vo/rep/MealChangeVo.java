package com.tydic.uniform.hh.vo.rep;

import java.util.List;

/**
 * <p></p>
 * @author Administrator 2015年10月13日 下午4:46:53
 * @ClassName MealChangeVo
 * @Description 套餐变更
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月13日
 * @modify by reason:{方法名}:{原因}
 */
public class MealChangeVo {
	private String number;
	private String validatecode;
	private String sale_order_id;
	private String cust_id;
	private String ext_order_id;
	private String prod_inst_id;
	private String acc_nbr;
	private String main_flag;
	private String product_id;
	private String new_flag;
	private String service_offer_id;
	private String org_id;
	private List<MealChangeOfferVo> sale_offer_instlist;
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getValidatecode() {
		return validatecode;
	}
	public void setValidatecode(String validatecode) {
		this.validatecode = validatecode;
	}
	public String getSale_order_id() {
		return sale_order_id;
	}
	public void setSale_order_id(String sale_order_id) {
		this.sale_order_id = sale_order_id;
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
	public String getProd_inst_id() {
		return prod_inst_id;
	}
	public void setProd_inst_id(String prod_inst_id) {
		this.prod_inst_id = prod_inst_id;
	}
	public String getAcc_nbr() {
		return acc_nbr;
	}
	public void setAcc_nbr(String acc_nbr) {
		this.acc_nbr = acc_nbr;
	}
	public String getMain_flag() {
		return main_flag;
	}
	public void setMain_flag(String main_flag) {
		this.main_flag = main_flag;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getNew_flag() {
		return new_flag;
	}
	public void setNew_flag(String new_flag) {
		this.new_flag = new_flag;
	}
	public String getService_offer_id() {
		return service_offer_id;
	}
	public void setService_offer_id(String service_offer_id) {
		this.service_offer_id = service_offer_id;
	}
	public List<MealChangeOfferVo> getSale_offer_instlist() {
		return sale_offer_instlist;
	}
	public void setSale_offer_instlist(List<MealChangeOfferVo> sale_offer_instlist) {
		this.sale_offer_instlist = sale_offer_instlist;
	}
	public String getOrg_id() {
		return org_id;
	}
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

}
