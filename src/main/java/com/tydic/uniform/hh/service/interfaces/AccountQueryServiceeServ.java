package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.AccountQueryVo;

public interface AccountQueryServiceeServ {

	/**
	 * 
	 * 代理商余额查询接口
	 */

	public Map<String, Object> AccountQuery(AccountQueryVo accountqueryvo);

}
