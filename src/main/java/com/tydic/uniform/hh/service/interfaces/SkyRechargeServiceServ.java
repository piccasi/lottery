package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.SkyRechargeVo;

public interface SkyRechargeServiceServ {

	/**
	 * 
	 *  话费充值接口
	 */

	public Map<String, Object> skyRecharge(SkyRechargeVo skychargevo);

}
