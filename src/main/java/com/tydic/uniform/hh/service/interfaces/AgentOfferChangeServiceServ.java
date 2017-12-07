package com.tydic.uniform.hh.service.interfaces;

import com.tydic.uniform.hh.vo.rep.MealChangeVo;
import com.tydic.uniform.hh.vo.rep.UsermsgVo;

public interface AgentOfferChangeServiceServ {

	String getNumberOfferChange (UsermsgVo usermsgvo);
	
	String getNumberOfferChangeQuery (UsermsgVo usermsgvo);
	
	String getNumberOfferChangeHandle(MealChangeVo mealchangevo);
}
