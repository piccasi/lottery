package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.AgentMsgQueryVo;

public interface AgentMsgQueryServiceServ {
	
	public Map<String,Object> agentMsg(AgentMsgQueryVo agentmsgqueryvo);

}
