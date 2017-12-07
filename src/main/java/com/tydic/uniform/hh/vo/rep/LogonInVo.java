package com.tydic.uniform.hh.vo.rep;

import com.tydic.uniform.hh.constant.BusinessType;

/**
 * <p></p>
 * @author yiyaohong 2015年10月8日 下午3:38:24
 * @ClassName LogonInVo   用户登录VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
public class LogonInVo {
	private String channel_code;
	private String ext_system;
	private String login_type;
	private String login_nbr;
	private String pwd;
	private String code;
	//private BusinessType type;
	
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
	
    public String getLogin_type() {
    	return login_type;
    }
	
    public void setLogin_type(String login_type) {
    	this.login_type = login_type;
    }
	
    public String getLogin_nbr() {
    	return login_nbr;
    }
	
    public void setLogin_nbr(String login_nbr) {
    	this.login_nbr = login_nbr;
    }
	
    public String getPwd() {
    	return pwd;
    }
	
    public void setPwd(String pwd) {
    	this.pwd = pwd;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	@Override
	public String toString() {
		return "LogonInVo [channel_code=" + channel_code + ", ext_system=" + ext_system + ", login_type=" + login_type
				+ ", login_nbr=" + login_nbr + ", pwd=" + pwd + ", code=" + code + "]";
	}
	
	
	
}
