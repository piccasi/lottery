package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.AgentBackFileVo;
import com.tydic.uniform.hh.vo.rep.AgentOrderBackFileVo;
import com.tydic.uniform.hh.vo.rep.AgentOrderVo;
import com.tydic.uniform.hh.vo.rep.MealListVo;

public interface AgentOrderServiceServ {

	String actCheck(AgentOrderVo gentOrderVo);
	
	String getMealList(MealListVo mealListvo);
	
	String agentFaceBackfile(AgentOrderBackFileVo agentOrderBackFileVo);
	
	String agentBackfile(AgentBackFileVo agentbackfilevo);
	
	Map<String, Object> getUserInfo(AgentOrderVo gentOrderVo);

	Map<String, Object> isMobile170(AgentOrderVo gentOrderVo);

}
