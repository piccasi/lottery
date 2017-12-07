package com.tydic.uniform.hh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.BusinessType;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.AgentOrderServiceServ;
import com.tydic.uniform.hh.service.interfaces.IdentifyCodeService;
import com.tydic.uniform.hh.service.interfaces.OilServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentOrderVo;
import com.tydic.uniform.hh.vo.rep.IdentifyCodeVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("OilServiceServ")
public class OilServiceImpl extends AbstractService implements OilServiceServ {
	private static Logger logger = Logger.getLogger(OilServiceImpl.class);
	@Autowired
	private AgentOrderServiceServ agentOrderServiceServ;
	
	@Autowired
	private IdentifyCodeService identifyCodeService;
	@Override
	public String query(Map<String, String> map) {
		String mobile = map.get("mobile");
		String org_id = map.get("org_id");
		// TODO Auto-generated method stub
		AgentOrderVo agentOrderVo = new AgentOrderVo();
		agentOrderVo.setMobile_170(mobile);
		Map<String, Object> userInfo = agentOrderServiceServ.getUserInfo(agentOrderVo);
		
		if(HhConstants.SUCCESSCODE.equals(userInfo.get(HhConstants.CODE))){
			Map<String, Object> cust = (Map<String, Object>)userInfo.get("CUST");
			Map<String, Object> prodInst = ((List<Map<String, Object>>)cust.get("PROD_INST")).get(0);
			String status = prodInst.get("STATUS_CD").toString();
			if(!"100000".equals(status)){
				return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "该号码状态非在用，不可办理此业务");
			}
			
			String custId = cust.get("CUST_ID").toString();
			Map<String, Object> queryResource = queryResource(custId);
			String phone = "";
			if(HhConstants.SUCCESSCODE.equals(queryResource.get(HhConstants.CODE))) {
				JSONArray array = JSONArray.fromObject(cust.get("PROD_INST"));
				if(null != array && array.size() > 0){
					for(int i = 0; i < array.size(); i++){
						JSONObject obj = (JSONObject)(array.get(i));
						if("1".equals((String)obj.get("MAIN_FLAG"))){
							phone = (String)obj.get("ACC_NBR");
						}
					}
					Map<String, Object> canOrderOffers = queryCanOrderOffer(phone,org_id);
					if(HhConstants.SUCCESSCODE.equals(canOrderOffers.get(HhConstants.CODE))) {
						JSONObject result = new JSONObject();
						result.put("ofrList", (JSONArray)canOrderOffers.get("ofrList"));
						result.put("mobile", phone);
						result.put("cash", cust.get("CASH").toString());
						return JsonResponse.toSuccessResult(result.toString());
					}else{
						return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, canOrderOffers.get(HhConstants.MESSAGE).toString());
					}
				}else{
					return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "查询不到号码对应的信息");
				}
			}else{
				return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, queryResource.get(HhConstants.MESSAGE).toString());
			}
		}else{
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, userInfo.get(HhConstants.MESSAGE).toString());
		}
	}
	
	private Map<String, Object> queryCanOrderOffer(String phone,String org_id) {
		// TODO Auto-generated method stub
		JSONArray sooList =  new JSONArray();
		JSONObject param = new JSONObject();
		JSONObject DNR_CHECK = new JSONObject();
		DNR_CHECK.put(HhConstants.CHANNEL_CODE, org_id);
		DNR_CHECK.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEM_VALUE);
		DNR_CHECK.put(HhConstants.DEVICE_NUMBER, phone);
		DNR_CHECK.put("SERVICE_CODE", "889");
		DNR_CHECK.put("OFR_MODE", "02");
		param.put("DNR_CHECK", DNR_CHECK);
		sooList.add(param);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);	
		logger.info("*************************crm可订购套餐查询接口入参*************************");
		logger.info("BOSS request str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001029", ResponseBean.class);
		logger.info("*************************crm可订购套餐查询接口出参*************************");
		logger.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		if(HhConstants.SUCCESS.equals(status)){
		    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
		    resultMap.put(HhConstants.RESULT, status);
		    resultMap.put(HhConstants.MESSAGE, result);
		    JSONArray object = JSONArray.fromObject(resultsooMap.get("OFR_LIST"));
		    resultMap.put("ofrList", object);
	    }else if(HhConstants.ERROR.equals(status)){
    		resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
	    	resultMap.put(HhConstants.MESSAGE, result);//失败描述
	    	resultMap.put(HhConstants.RESULT, status);
	    }
		return resultMap;
	}

	public Map<String,Object> queryResource(String custId){
		JSONArray sooList =  new JSONArray();
		JSONObject BALANCEQRY = new JSONObject();
		BALANCEQRY.put("Value", custId);
		BALANCEQRY.put("Type", "2");
		BALANCEQRY.put("MemberType", "1");
		JSONObject param = new JSONObject();
		param.put("OfrInst", BALANCEQRY);
		JSONObject PUB_REQ = new JSONObject();
		PUB_REQ.put("TYPE", "OfrInst");
		param.put("PUB_REQ", PUB_REQ);
		sooList.add(param);
		JSONObject body = new JSONObject();
		body.put("SOO", sooList);
		
		logger.info("*************************通信资源查询接口入参*************************");
		logger.info("BILL request str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003009", ResponseBean.class);
		logger.info("*************************通信资源查询接口出参*************************");
		logger.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.Result);
		String result = (String) resultResultMap.get(HhConstants.Msg);
		if(HhConstants.SUCCESS.equals(status)){
		    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
		    resultMap.put(HhConstants.RESULT, status);
		    resultMap.put(HhConstants.MSG, result);
		    
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
	    	resultMap.put(HhConstants.MESSAGE, result);//失败描述
	    }
		logger.info("*************************APP通信资源查询接口出参*************************");
		logger.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

	@Override
	public String order(String mobile,String code,String ofrId,String org_id) {
		// TODO Auto-generated method stub
		IdentifyCodeVo codeVo = new IdentifyCodeVo();
		codeVo.setMobile_170(mobile);
		codeVo.setType(BusinessType.PACKAGE);
		codeVo.setCode(code);
		//校验短信码
		boolean checkResult = identifyCodeService.checkCodeSync(codeVo);
		if(!checkResult){
			return JsonResponse.toErrorResult(CODE.VALIDATE_ERROR, "短信验证码错误");
		}
		AgentOrderVo agentOrderVo = new AgentOrderVo();
		agentOrderVo.setMobile_170(mobile);
		Map<String, Object> userInfo = agentOrderServiceServ.getUserInfo(agentOrderVo);
		
		if(HhConstants.SUCCESSCODE.equals(userInfo.get(HhConstants.CODE))){
			Map<String, Object> cust = (Map<String, Object>)userInfo.get("CUST");
			String custId = cust.get("CUST_ID").toString();
			String phone = "";
			JSONArray array = JSONArray.fromObject(cust.get("PROD_INST"));
			boolean oFlag = false;
			if(null != array && array.size() > 0){
				for(int i = 0; i < array.size(); i++){
					JSONObject obj = (JSONObject)(array.get(i));
					if("1".equals((String)obj.get("MAIN_FLAG"))){
						phone = (String)obj.get("ACC_NBR");
					}
				}
				if(array!=null && array.size()>0 && array.get(0)!=null){
					JSONObject pord = (JSONObject) array.get(0);
					if("1".equals((String)pord.get("OFFER_FLAG"))){
						oFlag=true;
					}
				}
			}else{
				return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "查询不到号码对应的信息");
			}
			
			JSONArray sooList =  new JSONArray();
			
			JSONObject SALE_ORDER_INST_MAP = new JSONObject();
			JSONObject SALE_ORDER_INST = new JSONObject();
			SALE_ORDER_INST.put("SALE_ORDER_ID", "$581007$");
			/*if(oFlag){
				SALE_ORDER_INST.put(HhConstants.CHANNEL_CODE, HhConstants.MOZU_CHANNEL_CODE);
			}else{
				SALE_ORDER_INST.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
			}*/
			SALE_ORDER_INST.put(HhConstants.CHANNEL_CODE,org_id);
			SALE_ORDER_INST.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEM_VALUE);
			SALE_ORDER_INST.put("CUST_ID", custId);
			SALE_ORDER_INST.put("EXT_ORDER_ID", "-1");
			JSONObject SALE_ORDER_INST_PUB_REQ = new JSONObject();
			SALE_ORDER_INST_PUB_REQ.put("TYPE", "SAVE_SALE_ORDER_INST");
			SALE_ORDER_INST_MAP.put("PUB_REQ", SALE_ORDER_INST_PUB_REQ);
			SALE_ORDER_INST_MAP.put("SALE_ORDER_INST", SALE_ORDER_INST);
			sooList.add(SALE_ORDER_INST_MAP);
			
			JSONObject SALE_PROD_INST_MAP = new JSONObject();
			JSONObject SALE_PROD_INST = new JSONObject();
			SALE_PROD_INST.put("PROD_INST_ID", "107107156");
			SALE_PROD_INST.put("SALE_ORDER_ID","$581007$");
			SALE_PROD_INST.put("ACC_NBR",phone);
			SALE_PROD_INST.put("MAIN_FLAG","1");
			SALE_PROD_INST.put("PRODUCT_ID","1360");
			SALE_PROD_INST.put("NEW_FLAG","1");
			SALE_PROD_INST.put("SERVICE_OFFER_ID","889");
			JSONObject SALE_PROD_INST_PUB_REQ = new JSONObject();
			SALE_PROD_INST_PUB_REQ.put("TYPE", "SAVE_SALE_PROD_INST");
			SALE_PROD_INST_MAP.put("PUB_REQ", SALE_PROD_INST_PUB_REQ);
			SALE_PROD_INST_MAP.put("SALE_PROD_INST", SALE_PROD_INST);
			sooList.add(SALE_PROD_INST_MAP);
			
			JSONObject SALE_OFFER_INST_MAP = new JSONObject();
			JSONObject SALE_OFFER_INST_PUB_REQ = new JSONObject();
			SALE_OFFER_INST_PUB_REQ.put("TYPE", "SAVE_SALE_OFFER_INST");
			SALE_OFFER_INST_MAP.put("PUB_REQ", SALE_OFFER_INST_PUB_REQ);
			JSONArray SALE_OFFER_INST_ARRAY = new JSONArray();
			JSONObject SALE_OFFER_INST = new JSONObject();
			SALE_OFFER_INST.put("OFFER_INST_ID", "123");
			SALE_OFFER_INST.put("OFFER_ID", ofrId);
			SALE_OFFER_INST.put("NEW_FLAG", "1");
			SALE_OFFER_INST.put("SALE_ORDER_ID", "$581007$");
			SALE_OFFER_INST.put("SERVICE_OFFER_ID", "250");
			SALE_OFFER_INST_ARRAY.add(SALE_OFFER_INST);
			SALE_OFFER_INST_MAP.put("SALE_OFFER_INST", SALE_OFFER_INST_ARRAY);
			sooList.add(SALE_OFFER_INST_MAP);
			
			JSONObject body = new JSONObject();
			body.put("SOO", sooList);
			
			logger.info("*************************CRM加油包订购接口入参*************************");
			logger.info("CRM request str:" + body.toString());
			setIntefaceType(IntefaceType.CRM);
			ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001025", ResponseBean.class);
			logger.info("*************************CRM加油包订购接口出参*************************");
			logger.info("CRM response str:" + JSONObject.fromObject(bean).toString());
			
			/*
			 * 解析接口返参
			*/
			String resultStr = new String();
			List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
			Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
			Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
			String status = (String) resultResultMap.get(HhConstants.RESULT);
			String result = (String) resultResultMap.get(HhConstants.MSG);
			if(HhConstants.SUCCESS.equals(status)){
				resultStr = JsonResponse.toSuccessResult(null);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultStr = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, result);
		    }
			logger.info("*************************APP加油包订购接口出参*************************");
			logger.info("APP response str:"+resultStr);
			return resultStr;
		}else{
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, userInfo.get(HhConstants.MESSAGE).toString());
		}
	}
}
