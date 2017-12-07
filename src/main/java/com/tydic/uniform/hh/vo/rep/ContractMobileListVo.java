package com.tydic.uniform.hh.vo.rep;
/**
 * <p></p>
 * @author ghp 2015年9月29日 下午5:02:54
 * @ClassName ContractMobileListVo   合约机列表 基类VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public class ContractMobileListVo {
	private String pagesize;
	private String pageindex;
	private String sellchannel;
	private String productgoods;
	
	public String getPagesize() {
		return pagesize;
	}
	public void setPagesize(String pagesize) {
		this.pagesize = pagesize;
	}
	public String getPageindex() {
		return pageindex;
	}
	public void setPageindex(String pageindex) {
		this.pageindex = pageindex;
	}
	public String getSellchannel() {
		return sellchannel;
	}
	public void setSellchannel(String sellchannel) {
		this.sellchannel = sellchannel;
	}
	public String getProductgoods() {
		return productgoods;
	}
	public void setProductgoods(String productgoods) {
		this.productgoods = productgoods;
	}
	
	
}
