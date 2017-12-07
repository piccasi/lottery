package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.SkyRefundVo;

public interface SkyRefundServiceServ {
	/**
	 * 
	 * 空中充值交易冲正
	 */
	public Map<String, Object> airRefund(SkyRefundVo airRefundvo);
}
