package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年10月7日 下午8:56:18
 * @ClassName UsermsgVo   客户资料查寻 Vo
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月7日
 * @modify by reason:{方法名}:{原因}
 */
public class UsermsgVo {
	
	private String qry_type;
	private String qry_number;
	private String cust_id;
	private String ext_system;
	private String channel_code;
	private String code;
	public String getExt_system() {
		return ext_system;
	}
	public void setExt_system(String ext_system) {
		this.ext_system = ext_system;
	}
	public String getChannel_code() {
		return channel_code;
	}
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	public String getQry_type() {
		return qry_type;
	}
	public void setQry_type(String qry_type) {
		this.qry_type = qry_type;
	}
	public String getQry_number() {
		return qry_number;
	}
	public void setQry_number(String qry_number) {
		this.qry_number = qry_number;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
}
