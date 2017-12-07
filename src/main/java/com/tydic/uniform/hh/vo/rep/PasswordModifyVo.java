package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author yiyaohong 2015年10月8日 下午3:36:53
 * @ClassName PasswordModifyVo 密码修改和重置VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
public class PasswordModifyVo {
	
	private String channel_code;
	private String cust_id;
	private String ext_system;
	private String pwd_type;
	private String old_pwd;
	private String new_pwd;
	private String reset_type;
	
	public String getChannel_code() {
		return channel_code;
	}
	
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	
	public String getCust_id() {
		return cust_id;
	}
	
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	
	public String getExt_system() {
		return ext_system;
	}
	
	public void setExt_system(String ext_system) {
		this.ext_system = ext_system;
	}
	
	public String getPwd_type() {
		return pwd_type;
	}
	
	public void setPwd_type(String pwd_type) {
		this.pwd_type = pwd_type;
	}
	
	public String getOld_pwd() {
		return old_pwd;
	}
	
	public void setOld_pwd(String old_pwd) {
		this.old_pwd = old_pwd;
	}
	
	public String getNew_pwd() {
		return new_pwd;
	}
	
	public void setNew_pwd(String new_pwd) {
		this.new_pwd = new_pwd;
	}
	
	public String getReset_type() {
		return reset_type;
	}
	
	public void setReset_type(String reset_type) {
		this.reset_type = reset_type;
	}
	
}
