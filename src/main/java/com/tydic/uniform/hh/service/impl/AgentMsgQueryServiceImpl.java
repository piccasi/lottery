package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.AgentMsgQueryServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentMsgQueryVo;
import com.tydic.uniform.hh.vo.resp.AccountQueryResp;
import com.tydic.uniform.hh.vo.resp.AgentMsgQueryResp;

import net.sf.json.JSONObject;

@Service("AgentMsgQueryServiceServ")
public class AgentMsgQueryServiceImpl extends AbstractService implements AgentMsgQueryServiceServ {
	private static Logger log = Logger.getLogger(AgentMsgQueryServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> agentMsg(AgentMsgQueryVo agentmsgqueryvo) {

		log.info("*************************APP代理商信息查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentmsgqueryvo).toString());

		/*
		 * 按照文档设置接口入参
		 */

		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());

		Map<String, Object> req = new HashMap<String, Object>();
		agentmsgqueryvo.setType("AgentInfomation");
		req.put(HhConstants.TYPE, agentmsgqueryvo.getType());
		req.put("agentinfomation", agentmsgqueryvo.getAgentinfomation());

		sooList.get(0).put("Msg_Query", req);

		Map<String, Object> typemap = new HashMap<String, Object>();
		typemap.put(HhConstants.TYPE, "AgentMsgQuery");
		sooList.get(0).put(HhConstants.PUB_REQ, typemap);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		/*
		 * 请求接口
		 */
		log.info("*************************代理商信息查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1003025", ResponseBean.class);
		log.info("*************************代理商余额查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());

		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();

		List<Map<String, Object>> resultSooList = (List<Map<String, Object>>) bean.getBody().get(0)
				.get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
		String result = (String) resultResultMap.get(HhConstants.BILLMSG);

		AgentMsgQueryResp agentmsgqueryresp = new AgentMsgQueryResp();

		if (HhConstants.SUCCESS.equals(status)) {
			Map<String, Object> respmap = new HashMap<String, Object>();
			if (resultsooMap.containsKey(HhConstants.RESP)) {
				respmap.put("balance", resultResultMap.get("Balance"));
				respmap.put("agentinfomation", resultResultMap.get("AgentInfomation"));
				
			}
			resultMap.put(HhConstants.RESP, respmap);
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.MESSAGE, result);
			agentmsgqueryresp.setMapInfo(resultMap);
		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
		}
		resultMap.put("accountQueryResp", agentmsgqueryresp);
		log.info("*************************APP代理商余额查询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());

		return resultMap;
	}

}
