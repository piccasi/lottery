package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.constant.OrderType;
import com.tydic.uniform.hh.service.interfaces.AgentOrderQueryServiceServ;
import com.tydic.uniform.hh.service.interfaces.AgentOrderServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentOrderQueryVo;
import com.tydic.uniform.hh.vo.rep.AgentOrderVo;
import com.tydic.uniform.hh.vo.resp.OrderdetailListResp;

import net.sf.json.JSONObject;

@Service("AgentOrderQueryServiceServ")
public class AgentOrderQueryServiceImpl extends AbstractService implements AgentOrderQueryServiceServ{
	private static Logger log = Logger.getLogger(AgentOrderQueryServiceImpl.class);
	/**
	 * @author ghp 2016年7月14日10:15:28
	 * @Method: agentFaceBackfile 
	 * @Description: TODO
	 * @param agentbackfilebyocrvo
	 * @return 
	 */
	
	@Autowired
	private AgentOrderServiceServ agentOrderServiceServ;

	@Override
	public String getOrderList(AgentOrderQueryVo agentOrderQueryVo) {
		log.info("*************************APP订单查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentOrderQueryVo).toString());
		
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, agentOrderQueryVo.getOrg_id());
		req.put(HhConstants.EXT_SYSTEM, "102");
		req.put(HhConstants.DEVICE_NUMBER, agentOrderQueryVo.getMobile_170());
		req.put(HhConstants.PAGE, "1000");
		req.put(HhConstants.PAGE_NO, "1");
		
		if(StringUtils.isNotEmpty(agentOrderQueryVo.getStartDate()) && StringUtils.isNotEmpty(agentOrderQueryVo.getEndDate())){
			req.put(HhConstants.START_TIME, agentOrderQueryVo.getStartDate());
			req.put(HhConstants.END_TIME, agentOrderQueryVo.getEndDate());
		}
		
		if(StringUtils.isNotEmpty(agentOrderQueryVo.getOrderType())){
			req.put(HhConstants.USER_EVENT_CODE, agentOrderQueryVo.getOrderType());
		}
		
		sooList.get(0).put(HhConstants.ORDER,req);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS订单查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001007", ResponseBean.class);
		log.info("*************************BOSS订单查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		*/
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.CODE);
		String resultString = "";
		List<Map<String, Object>> orderListWithStatus = new ArrayList<Map<String, Object>>();
		if(HhConstants.SUCCESS.equals(status)){
			if(resultsooMap.containsKey(HhConstants.ORDER)){
				List<Map<String, Object>> orderList = (List<Map<String, Object>>) resultsooMap.get(HhConstants.ORDER);
				for(int i=orderList.size()-1;i>=0;i--){
					Map<String,Object> orderMap = new HashMap<String,Object>();
					Map<String, Object> order = orderList.get(i);
					
					if(order.containsKey(HhConstants.USER_EVENT_CODE)){
						if(("206".equals(order.get(HhConstants.USER_EVENT_CODE)) || "200".equals(order.get(HhConstants.USER_EVENT_CODE))) 
								&& agentOrderQueryVo.getOrg_id().equals(order.get(HhConstants.CHANNEL_CODE))){
							if(order.containsKey(HhConstants.ORDER_ID)){
								orderMap.put(HhConstantsResp.ORDER_ID,order.get(HhConstants.ORDER_ID));
							}
							if(order.containsKey(HhConstants.CREATE_TIME)){
								orderMap.put(HhConstantsResp.CREATE_TIME,order.get(HhConstants.CREATE_TIME));
							}
							if(order.containsKey(HhConstants.ORDER_PRICE)){
								orderMap.put(HhConstantsResp.ORDER_PRICE,order.get(HhConstants.ORDER_PRICE));
							}
							if(order.containsKey(HhConstants.STATUS_CD)){
								orderMap.put(HhConstantsResp.STATUS_CD,order.get(HhConstants.STATUS_CD));
							}
							if(order.containsKey(HhConstants.PROCESS_STS)){
								orderMap.put(HhConstantsResp.PROCESS_STS,order.get(HhConstants.PROCESS_STS));
							}
							if(order.containsKey(HhConstants.ORDER_TYPE)){
								orderMap.put(HhConstantsResp.ORDER_TYPE,order.get(HhConstants.ORDER_TYPE));
							}
							if(order.containsKey(HhConstants.PAY_STATUS)){
								orderMap.put(HhConstantsResp.PAY_STATUS,order.get(HhConstants.PAY_STATUS));
							}
							if(order.containsKey(HhConstants.STATUS_NAME)){
								orderMap.put(HhConstantsResp.STATUS_NAME,order.get(HhConstants.STATUS_NAME));
							}
							if(order.containsKey(HhConstants.DEVICE_NUMBER)){
								orderMap.put(HhConstantsResp.DEVICE_NUMBER,order.get(HhConstants.DEVICE_NUMBER));
							}
							if(order.containsKey(HhConstants.LOG_NBR)){
								orderMap.put(HhConstantsResp.LOG_NBR,order.get(HhConstants.LOG_NBR));
							}
							if(order.containsKey(HhConstants.LOG_CAMP)){
								orderMap.put(HhConstantsResp.LOG_CAMP,order.get(HhConstants.LOG_CAMP));
							}
							if(order.containsKey(HhConstants.ORDER_DESC)){
								orderMap.put(HhConstantsResp.ORDER_DESC,order.get(HhConstants.ORDER_DESC));
							}
							if(order.containsKey(HhConstants.USER_EVENT_CODE)){
								orderMap.put(HhConstantsResp.USER_EVENT_CODE,order.get(HhConstants.USER_EVENT_CODE));
								orderMap.put(HhConstantsResp.USER_EVENT_NAME,
										OrderType.getDescByCode(Integer.parseInt(order.get(HhConstants.USER_EVENT_CODE).toString())));
							}
							
							
							orderListWithStatus.add(orderMap);
						}
					}
				}
			}
			resultString = JsonResponse.toSuccessResult(orderListWithStatus);
		}else if(HhConstants.ERROR.equals(status)){
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, msg);
	    }
		log.info("*************************APP订单查询接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(DesEncryptUtil.decrypt(resultString)).toString());
	    return resultString;
	}
	@Override
	public String getOrderDetail(AgentOrderQueryVo agentOrderQueryVo) {
		log.info("*************************APP 订单详情查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentOrderQueryVo).toString());
		
		AgentOrderVo agentOrderVo = new AgentOrderVo();
		agentOrderVo.setMobile_170(agentOrderQueryVo.getMobile_170());
		Map<String, Object> userInfo = agentOrderServiceServ.getUserInfo(agentOrderVo);
		String custId = "";
		{
			//校验号码是否支持查询
			if(HhConstants.SUCCESSCODE.equals(userInfo.get(HhConstants.CODE))){
				Map<String, Object> cust = (Map<String, Object>)userInfo.get("CUST");
				//会员标识
				custId = cust.get("CUST_ID").toString();
			}else{
				return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, userInfo.get(HhConstants.MESSAGE).toString());
			}
		}
		
		
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, agentOrderQueryVo.getOrg_id());
		req.put(HhConstants.EXT_SYSTEM, "102");
		req.put(HhConstants.ORDER_ID, agentOrderQueryVo.getOrderId());
		req.put(HhConstants.CUST_ID, custId);
		
		sooList.get(0).put(HhConstants.ORDER,req);
		
		Map<String,Object> pub_req = new HashMap<String,Object>();
		pub_req.put(HhConstants.TYPE, "ORDER_DETAIL_QRY");
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS订单详情查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001008", ResponseBean.class);
		log.info("*************************BOSS订单详情查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		*/
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String resultString = "";
		if(HhConstants.SUCCESS.equals(status)){
			Map<String,Object> resultMap = new HashMap<String,Object>();
			Map<String,Object> cust= (Map<String,Object>)resultsooMap.get("CUST");
			Map<String,Object> order= (Map<String,Object>)resultsooMap.get("ORDER");
			List<Map<String,Object>> orderAuditList= (List<Map<String,Object>>)resultsooMap.get("ORDER_AUDIT");
			
			//姓名
			String cretiName = cust.get("CUST_NAME").toString();
			String name = cretiName.substring(0, 1);
			for(int i=0;i<cretiName.length()-1;i++){
				name+="*";
			}
			cust.put("CUST_NAME", name);
			
			//身份证号码
			String cretiNbr = cust.get("CERT_NBR").toString();
			if(cretiNbr.length()==18){
				cretiNbr = cretiNbr.substring(0, 3) + "************" +cretiNbr.substring(cretiNbr.length()-3, cretiNbr.length());
			}else if(cretiNbr.length()==15){
				cretiNbr = cretiNbr.substring(0, 3) + "*********" +cretiNbr.substring(cretiNbr.length()-3, cretiNbr.length());
			}
			cust.put("CERT_NBR", cretiNbr);
			
			resultMap.put("cust", cust);
			resultMap.put("order", order);
			if(orderAuditList.size()>0){
				resultMap.put("orderAudit", orderAuditList.get(0));
			}
			
			resultString = JsonResponse.toSuccessResult(resultMap);
		}else if(HhConstants.ERROR.equals(status)){
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, HhConstants.ERRORMESSAGE);
	    }
		log.info("*************************APP订单详情查询接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(DesEncryptUtil.decrypt(resultString)).toString());
	    return resultString;
	}
	
}
