package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.CardChargeVo;
import com.tydic.uniform.hh.vo.rep.CashChargeVo;
import com.tydic.uniform.hh.vo.rep.Judge170NumberVo;
/**
 * 
 * @author DIC_YINSHUANGHUA
 *话费充值接口
 */
public interface ChargeServiceServ {
	public Map<String,Object> CardCharge(CardChargeVo cardChagerVo);
	public Map<String,Object> CashCharge(CashChargeVo cashChagerVo);
	public Map<String,Object> getRandomCode(Judge170NumberVo judge170NumberVo);
}
