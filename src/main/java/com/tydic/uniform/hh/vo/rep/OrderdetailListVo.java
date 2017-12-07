package com.tydic.uniform.hh.vo.rep;
/**
 * 
 * <p></p>
 * @author gengtian 2015年10月14日 下午4:42:57
 * @ClassName OrderdetailListVo
 * @Description TODO 订单详情查询接口入参映射
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public class OrderdetailListVo{
	private String channel_code;
	private String ext_system;
	private String order_id;
	private String custid;
	
	public String getCustid() {
		return custid;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public String getChannel_code() {
		return channel_code;
	}
	
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	
	public String getExt_system() {
		return ext_system;
	}
	
	public void setExt_system(String ext_system) {
		this.ext_system = ext_system;
	}
	
	public String getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	
}