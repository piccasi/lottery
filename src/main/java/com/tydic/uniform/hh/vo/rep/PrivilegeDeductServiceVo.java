package com.tydic.uniform.hh.vo.rep;

/**
 * 
 * <p></p>
 * @author panxinxing 2015年11月19日 下午4:42:57
 * @ClassName PrivilegeDeductServiceVo
 * @Description TODO 航空特权扣减服务接口入参映射
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月16日
 * @modify by reason:{方法名}:{原因}
 */
public class PrivilegeDeductServiceVo{
	private String balancetypeid;
	private String mobile170;
	private String smscode;
	
	public String getBalancetypeid() {
		return balancetypeid;
	}
	public void setBalancetypeid(String balancetypeid) {
		this.balancetypeid = balancetypeid;
	}
	public String getMobile170() {
		return mobile170;
	}
	public void setMobile170(String mobile170) {
		this.mobile170 = mobile170;
	}
	public String getSmscode() {
		return smscode;
	}
	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}
	
}