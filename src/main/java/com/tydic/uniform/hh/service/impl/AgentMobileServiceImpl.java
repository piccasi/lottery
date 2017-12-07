package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.AgentMobileService;

import net.sf.json.JSONObject;

@Service("agentMobileService")
public class AgentMobileServiceImpl extends AbstractService implements AgentMobileService {
	private static Logger log = Logger.getLogger(AgentMobileServiceImpl.class);
	

	@Override
	public String  getMobile(String login_nbr) {
		
		log.info("*************************APP获取短信前获取号码接口*************************");
		
		String mobile = null;
		Map<String, Object> body = new HashMap<String, Object>();
		List<Map> sooList = new ArrayList<Map>();
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put(HhConstants.EXT_SYSTEM, "10002");
		param.put("STAFF_CODE", login_nbr);
		
		sooList.add(0, param);
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS用户登录接口入参*************************");
		log.info("BOSS resquest str:" + JSONObject.fromObject(body).toString());


		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001065", ResponseBean.class);

		// JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS用户登录接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List resultSoolist = (List<Map<String, Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSoolist.get(0);
		Map resp = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resp.get(HhConstants.RESULT);
		
		if(HhConstants.SUCCESS.equals(status)){
			
			if(null != resp.get("MSG") && ((String)resp.get("MSG")).contains("非代理商")){
				return "非代理商工号，请验证";
			}
			
			Map retMap = (Map) resultsooMap.get("STAFF_INFO");
			mobile = (String) retMap.get("CONTACT_NUMBER");
			//mobileMap.put("mobile", mobile);
		}
		//mobile.put("mobile", "17188960086");
		return mobile;
	}

}
