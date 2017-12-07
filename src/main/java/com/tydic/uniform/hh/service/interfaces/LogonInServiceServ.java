package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.tydic.uniform.hh.vo.rep.LogonInVo;

/**
 * <p></p>
 * @author yiyaohong 2015年10月8日 下午3:41:09
 * @ClassName LogonInServiceServ  用户登录接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
public interface LogonInServiceServ {
	 public String Logonin(LogonInVo logoninvo);
	 
	 public String Logout(String token, LogonInVo logoninvo);
	 
	 String login(LogonInVo logoninvo, HttpServletRequest request);
	 
	 String loginPwdBack(LogonInVo logoninvo);
	 
	 Map<String, Object> Logonin_bak(LogonInVo logoninvo);
}
