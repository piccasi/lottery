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
import com.tydic.uniform.hh.service.interfaces.AgentDepositServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentDepositVo;

import net.sf.json.JSONObject;

@Service("AgentDepositServiceServ")
public class AgentDepositServiceImpl extends AbstractService implements AgentDepositServiceServ {

	private static Logger log = Logger.getLogger(AgentDepositServiceServ.class);
	
	
	@Override
	public Map<String, Object> agentDeposit(AgentDepositVo agentDepositVo) {
		log.info("*************************代理商预存退预存接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentDepositVo).toString());

		/*
		 * 按照BOSS文档设置接口入参
		 */

		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());
		
		Map<String, Object> req = new HashMap<String, Object>();
		req.put("CHARGE", agentDepositVo.getCharge());
		req.put("FREETYPE", agentDepositVo.getFreeType());
		req.put("FREETYPE", agentDepositVo.getPayAcct());
		req.put("PAYACCT", agentDepositVo.getRequestId());
		req.put("REQUESTID", agentDepositVo.getStaffId());
		req.put("DE_TYPE", agentDepositVo.getType());
		
		Map<String, Object> pub_req = new HashMap<String, Object>();
		pub_req.put(HhConstants.TYPE, "Deposit");

		sooList.get(0).put(HhConstants.PUB_REQ, pub_req);
		sooList.get(0).put("REFUND_REQ", req);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);
		
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS代理商基本信息接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001036", ResponseBean.class);
		log.info("*************************BOSS代理商基本信息接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		 */
		
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> result_resp = (Map<String, Object>) bean.getBody().get(0).get(HhConstants.RESP);
		String status = (String) result_resp.get(HhConstants.RESULT);
		String result = (String) result_resp.get(HhConstants.MSG);
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.MSG, result);
		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
		}
		//resultMap.put(HhConstants.PUB_RES, pub_req);
		log.info("*************************APP代理商基本信息接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

}
