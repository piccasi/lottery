package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.UserInfoVo;
import com.tydic.uniform.hh.vo.rep.UsermsgVo;

public interface UsermsgServiceServ {
	public Map<String,Object> getUsermsgByCustid (UsermsgVo usermsgvo);
	public Map<String,Object> getUserInfo (UserInfoVo userInfoVo);
//	public Map<String,Object> getUsermsgBynumber (UsermsgVo usermsgvo);
}
