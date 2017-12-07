package com.tydic.uniform.hh.vo.rep;
/**
 * <p></p>
 * @author ghp 2015年9月29日 下午5:02:54
 * @ClassName ContractMealVo   合约机套餐 基类VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public class ContractMealVo {
	private String plancode;
	private String productcode;
	private String sellchannel;
	public String getPlancode() {
		return plancode;
	}
	public void setPlancode(String plancode) {
		this.plancode = plancode;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getSellchannel() {
		return sellchannel;
	}
	public void setSellchannel(String sellchannel) {
		this.sellchannel = sellchannel;
	}
	
	
}
