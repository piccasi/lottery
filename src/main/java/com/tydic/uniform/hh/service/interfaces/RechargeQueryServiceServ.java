package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.RechargeQueryVo;

public interface RechargeQueryServiceServ {
	

	/**
	 * 
	 *  空中充值记录查询
	 */
	public String rechargeQuery(RechargeQueryVo rechargeQueryVo);
}
