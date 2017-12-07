package com.tydic.uniform.hh.vo.rep;

public class AgentMsgQueryVo {

	private String type;
	private AgentInfomation agentinfomation;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AgentInfomation getAgentinfomation() {
		return agentinfomation;
	}

	public void setAgentinfomation(AgentInfomation agentinfomation) {
		this.agentinfomation = agentinfomation;
	}

	public AgentMsgQueryVo(String type, AgentInfomation agentinfomation) {
		this.type = type;
		this.agentinfomation = agentinfomation;
	}

	public AgentMsgQueryVo() {
		super();
	}

	@Override
	public String toString() {
		return "AgentMsgQueryVo [type=" + type + ", agentinfomation=" + agentinfomation + "]";
	}
	
}
