package com.tydic.uniform.hh.service.interfaces;

import com.tydic.uniform.hh.vo.rep.AgentInfoQueryVo;
import com.tydic.uniform.hh.vo.rep.AgentSaleQuery;
import com.tydic.uniform.hh.vo.rep.UsermsgVo;

public interface AgentQueryInfoServiceServ {
	String getNumberInfo (UsermsgVo usermsgvo);
	
	String getAgentRes (AgentInfoQueryVo agentinfoqueryvo);
	
	String getNumberOffer (UsermsgVo usermsgvo);

	String getSaleQuery(AgentSaleQuery query);
}
