/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yiyaohong
 * @date: 2015年10月14日 上午10:32:24
 * @Title: BillInfoVo.java
 * @Package com.tydic.uniform.hh.vo.rep
 * @Description: TODO
 */
package com.tydic.uniform.hh.vo.rep;

/**
 * <p>
 * </p>
 * @author yiyaohong 2015年10月14日 上午10:32:24
 * @ClassName BillInfoVo 会员账单查询Vo
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public class BillInfoVo {
	
	private String type;
	private String value;
	private String billcycleid;
	private String systemid;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getBillcycleid() {
		return billcycleid;
	}
	
	public void setBillcycleid(String billcycleid) {
		this.billcycleid = billcycleid;
	}
	
	public String getSystemid() {
		return systemid;
	}
	
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	
}
