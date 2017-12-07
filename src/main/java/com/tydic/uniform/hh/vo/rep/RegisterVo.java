package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author yiyaohong 2015年10月8日 下午3:37:42
 * @ClassName RegisterVo 用户注册VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
public class RegisterVo {
	
/*	private String channel_code;
	private String ext_system;
	private String cust_name;
	private String service_nbr;
	private String pwd;
	private String sex;
	private String email;
	private String birthday;
	private String certi_type;
	private String certi_nbr;*/
	
	private String channel_code;
	private String ext_system;
	private String cust_name;
	private String service_nbr;
	private String pwd;
	
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
	
    public String getCust_name() {
    	return cust_name;
    }
	
    public void setCust_name(String cust_name) {
    	this.cust_name = cust_name;
    }
	
    public String getService_nbr() {
    	return service_nbr;
    }
	
    public void setService_nbr(String service_nbr) {
    	this.service_nbr = service_nbr;
    }
	
    public String getPwd() {
    	return pwd;
    }
	
    public void setPwd(String pwd) {
    	this.pwd = pwd;
    }
	
}
	
	