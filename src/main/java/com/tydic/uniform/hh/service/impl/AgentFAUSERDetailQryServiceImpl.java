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
import com.tydic.uniform.hh.service.interfaces.AgentFAUSERDetailQryServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentFAUSERDetailQryVo;

import net.sf.json.JSONObject;


@Service("AgentFAUSERDetailQryServiceServ")
public class AgentFAUSERDetailQryServiceImpl extends AbstractService implements AgentFAUSERDetailQryServiceServ {

	private static Logger log = Logger.getLogger(AgentFAUSERDetailQryServiceImpl.class);
	
	/**
	 * @author 2016年8月21日14:00:18
	 * @Method: agentFAUSERDetailQryMethod 
	 * @Description: 代理商发展用户收入查询
	 * @param agentfauserdetailqryvo
	 * @return 
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> agentFAUSERDetailQryMethod(AgentFAUSERDetailQryVo agentfauserdetailqryvo) {
		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP代理商发展用户收入查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentfauserdetailqryvo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(agentfauserdetailqryvo).toString());
		/*,.
		 * APP入参解析
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		Map<String,Object> pub_req_map = new HashMap<String,Object>();
		pub_req_map.put("TYPE", "FAUSER_DETAIL");
		
		Map<String,Object> query_info_map = new HashMap<String,Object>();
		query_info_map.put("StaffId", agentfauserdetailqryvo.getStaffId());
		query_info_map.put("AcctMonth", agentfauserdetailqryvo.getAcctMonth());
		
//		Map<String,Object> parameters_map = new HashMap<String,Object>();
//		parameters_map.put("", fauserdettail);
//		parameters_map.put("", query_info_map);
		
		sooList.add(0, new HashMap<String,Object>());
		sooList.get(0).put("PUB_REQ", pub_req_map);
		sooList.get(0).put("QUERY_INFO", query_info_map);
//		sooList.add(1, new HashMap<String,Object>());
//		sooList.get(1).put("QUERY_INFO", query_info_map);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		log.info("*************************BOSS代理商发展用户收入查询接口入参*************************");
		log.info("BOSS request str:" + JSONObject.fromObject(body).toString());
		/*
		 * 请求接口
		 */
		setIntefaceType(IntefaceType.BILL);
		/*
		 * 调用能力平台
		 */
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003041", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.MSG);
		
		 if(HhConstants.SUCCESS.equals(status)){
			 resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			 resultMap.put(HhConstants.RESULT, status);
			 List<Map<String,Object>> productAmountInfoMap = (List<Map<String, Object>>) resultResultMap.get("ProductAmountInfo");
			 List<Map<String,Object>> userAmountInfoMap = (List<Map<String, Object>>) resultResultMap.get("UserAmountInfo");
			 if (productAmountInfoMap!=null) {	
				 resultMap.put("ProductAmountInfo", productAmountInfoMap);
			}
			 if (userAmountInfoMap!=null) {
				 resultMap.put("UserAmountInfo", userAmountInfoMap);
			}
			 
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.RESULT, status);
		    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
		    }
		    log.info("*************************APP代理商发展用户收入查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			
		return resultMap;
	}

}
