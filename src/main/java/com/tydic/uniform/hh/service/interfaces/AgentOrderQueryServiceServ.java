package com.tydic.uniform.hh.service.interfaces;

import com.tydic.uniform.hh.vo.rep.AgentOrderQueryVo;

public interface AgentOrderQueryServiceServ {

	String getOrderList(AgentOrderQueryVo agentOrderQueryVo);

	String getOrderDetail(AgentOrderQueryVo agentOrderQueryVo);
}
