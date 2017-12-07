package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author cws 2015年9月29日 下午5:02:54
 * @ClassName ChargeInfo_phoneVo   话费充值 基类VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public class ChargeInfo_phoneVo {
	private String phoneNumber;//电话号码
	private String pay_amount;//充值金额
	public String getPay_amount() {
		return pay_amount;
	}
	public void setPay_amount(String pay_amount) {
		this.pay_amount = pay_amount;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	} 
	
}