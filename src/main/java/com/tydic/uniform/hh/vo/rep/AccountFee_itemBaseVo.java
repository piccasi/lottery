package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年9月29日 下午5:02:54
 * @ClassName AccountFee_itemBaseVo   开户新增订单接口费用信息 基类VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public class AccountFee_itemBaseVo {
	private String acct_item_type;
	private String amount;
	private String obj_inst_id;
	private String obj_inst_type;
	private String sale_order_id;
	
	
	public String getAcct_item_type() {
		return acct_item_type;
	}
	public void setAcct_item_type(String acct_item_type) {
		this.acct_item_type = acct_item_type;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getObj_inst_id() {
		return obj_inst_id;
	}
	public void setObj_inst_id(String obj_inst_id) {
		this.obj_inst_id = obj_inst_id;
	}
	public String getObj_inst_type() {
		return obj_inst_type;
	}
	public void setObj_inst_type(String obj_inst_type) {
		this.obj_inst_type = obj_inst_type;
	}
	public String getSale_order_id() {
		return sale_order_id;
	}
	public void setSale_order_id(String sale_order_id) {
		this.sale_order_id = sale_order_id;
	}
	
	
}
