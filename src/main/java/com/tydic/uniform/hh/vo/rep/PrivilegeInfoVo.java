package com.tydic.uniform.hh.vo.rep;

/**
 * 
 * <p></p>
 * @author panxinxing 2015年11月18日 下午4:42:57
 * @ClassName PrivilegeInfoVo
 * @Description TODO 航空特权查询接口入参映射
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月16日
 * @modify by reason:{方法名}:{原因}
 */
public class PrivilegeInfoVo{
	private String custid;
	private String info;
	private String mobile_170;
	
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getMobile_170() {
		return mobile_170;
	}

	public void setMobile_170(String mobile_170) {
		this.mobile_170 = mobile_170;
	}
	
}