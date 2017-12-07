package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年9月29日 下午5:00:00
 * @ClassName AccountOfferBaseVo   开户新增订单接口套餐信息基类VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public class AccountOfferBaseVo {
	private String new_flag;
	private String offer_id;
	private String offer_inst_id;
	private String offer_type_id;
	private String sale_order_id;
	
	public String getNew_flag() {
		return new_flag;
	}
	public void setNew_flag(String new_flag) {
		this.new_flag = new_flag;
	}
	public String getOffer_id() {
		return offer_id;
	}
	public void setOffer_id(String offer_id) {
		this.offer_id = offer_id;
	}
	public String getOffer_inst_id() {
		return offer_inst_id;
	}
	public void setOffer_inst_id(String offer_inst_id) {
		this.offer_inst_id = offer_inst_id;
	}
	public String getOffer_type_id() {
		return offer_type_id;
	}
	public void setOffer_type_id(String offer_type_id) {
		this.offer_type_id = offer_type_id;
	}
	public String getSale_order_id() {
		return sale_order_id;
	}
	public void setSale_order_id(String sale_order_id) {
		this.sale_order_id = sale_order_id;
	}
	
	
}
