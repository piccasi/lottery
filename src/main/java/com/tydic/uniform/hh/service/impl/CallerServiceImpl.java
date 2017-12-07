package com.tydic.uniform.hh.service.impl;

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
import com.tydic.uniform.hh.constant.NumberStatus;
import com.tydic.uniform.hh.service.interfaces.AgentOrderServiceServ;
import com.tydic.uniform.hh.service.interfaces.BaseBusinessQueryServiceServ;
import com.tydic.uniform.hh.service.interfaces.CallerServiceServ;
import com.tydic.uniform.hh.service.interfaces.IdentifyCodeService;
import com.tydic.uniform.hh.vo.rep.AgentOrderVo;
import com.tydic.uniform.hh.vo.rep.BaseBusinessQueryVo;
import com.tydic.uniform.hh.vo.rep.IdentifyCodeVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("CallerServiceServ")
public class CallerServiceImpl extends AbstractService implements CallerServiceServ {
	
	private static Logger logger = Logger.getLogger(CallerServiceImpl.class);
	
	@Autowired
	private BaseBusinessQueryServiceServ baseBusinessQueryServiceServ;
	
	@Autowired
	private AgentOrderServiceServ agentOrderServiceServ;
	
	@Autowired
	private IdentifyCodeService identifyCodeService;
	
	/**
	 * 来显查询
	 */
	@Override
	public String query(String mobile) {
		if(null == mobile || "".equals(mobile)){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "查询号码不能为空");
		}
		AgentOrderVo agentOrderVo = new AgentOrderVo();
		agentOrderVo.setMobile_170(mobile);
		Map<String, Object> mobile170Map = agentOrderServiceServ.isMobile170(agentOrderVo);
		
		if(HhConstants.SUCCESSCODE.equals(mobile170Map.get(HhConstants.CODE))){
			
			Map<String, Object> userInfo = agentOrderServiceServ.getUserInfo(agentOrderVo);
			//校验号码是否支持查询
			if(HhConstants.SUCCESSCODE.equals(userInfo.get(HhConstants.CODE))){
				BaseBusinessQueryVo baseBusinessQueryVo = new BaseBusinessQueryVo();
				baseBusinessQueryVo.setMsisdn(mobile);
				Map<String, Object> baseBusinessMap = baseBusinessQueryServiceServ.baseBusinessQueryMethod(baseBusinessQueryVo);
				if(HhConstants.SUCCESSCODE.equals(baseBusinessMap.get(HhConstants.CODE))){
					Map<String, Object> cust = (Map<String, Object>)userInfo.get("CUST");
					Map<String, Object> prodInst = ((List<Map<String, Object>>)cust.get("PROD_INST")).get(0);
					String status = prodInst.get("STATUS_CD").toString();
					if(!"100000".equals(status)){
						return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "该号码状态非在用，不可办理此业务");
					}
					
					//号码状态
					baseBusinessMap.put("status", NumberStatus.getDescByCode(Integer.parseInt(prodInst.get("STATUS_CD").toString())));
					//号码归属地
					baseBusinessMap.put("attribution", prodInst.get("LOCAL_NET_NAME").toString());
					baseBusinessMap.put("prodInstId", prodInst.get("PROD_INST_ID").toString());
					return JsonResponse.toSuccessResult(baseBusinessMap);
				}else{
					return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, baseBusinessMap.get(HhConstants.MESSAGE).toString());
				}
			}else{
				return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, userInfo.get(HhConstants.MESSAGE).toString());
			}
		}else{
			String resultString = "";
			if(mobile170Map.get(HhConstants.MESSAGE).toString().contains("非在网用户")){
				resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "号码非在网用户");
			}else{
				resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mobile170Map.get(HhConstants.MESSAGE).toString());
			}
			return resultString;
		}
		
	}
	
	/**
	 * 来显订购或退订
	 */
	@Override
	public String order(Map<String, String> map) {
		logger.info("*************************APP来显订购接口入参*************************");
		logger.info("APP request str:" + JSONObject.fromObject(map).toString());
		
		String mobile = map.get("mobile");
		String code = map.get("code");
		String orderToShow = map.get("orderToShow");
		String prodInstId = map.get("prodInstId");
		String productId = map.get("productId");
		IdentifyCodeVo codeVo = new IdentifyCodeVo();
		codeVo.setMobile_170(mobile);
		codeVo.setType(BusinessType.DISPLAY);
		codeVo.setCode(code);
		//校验短信码
		boolean checkResult = identifyCodeService.checkCodeSync(codeVo);
		if(!checkResult){
			return JsonResponse.toErrorResult(CODE.VALIDATE_ERROR, "短信验证码错误");
		}
		AgentOrderVo agentOrderVo = new AgentOrderVo();
		agentOrderVo.setMobile_170(mobile);
		Map<String, Object> userInfo = agentOrderServiceServ.getUserInfo(agentOrderVo);
		//校验号码是否支持查询
		if(HhConstants.SUCCESSCODE.equals(userInfo.get(HhConstants.CODE))){
			Map<String, Object> cust = (Map<String, Object>)userInfo.get("CUST");
			Map<String, Object> prodInst = ((List<Map<String, Object>>)cust.get("PROD_INST")).get(0);
			String status = prodInst.get("STATUS_CD").toString();
			if(!(!"110000".equals(status) && !"110098".equals(status)
					&& !"404".equals(status)&&!"123001".equals(status)&&!"180000".equals(status)
					&& !"140000".equals(status))){
				return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "该用户状态受限制");
			}
		}else{
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, userInfo.get(HhConstants.MESSAGE).toString());
		}
		JSONArray sooList = new JSONArray();
		JSONObject outsideMap = new JSONObject();
		JSONArray ServiceDtoList = new JSONArray();
		JSONObject insideMap = new JSONObject();
		if(orderToShow.equals("300")){
			insideMap.put("Action", "A");
			//insideMap.put("EffType", "0");
		}else{
			insideMap.put("Action", "D");
			//insideMap.put("EffType", "1");
		}
		insideMap.put("ServiceCode", productId);
		ServiceDtoList.add(insideMap);
		outsideMap.put("JDOrderID", "");
		outsideMap.put("MSISDN", mobile);
		outsideMap.put("ContactChannle", HhConstants.CHANNEL_CODEVALUE);
		outsideMap.put("ServiceDtoList", ServiceDtoList);
		outsideMap.put("UserEventCode", "4");
		sooList.add(outsideMap);
		JSONObject body = new JSONObject();
		body.put("SOO", sooList);
		
		logger.info("*************************CRM基础业务变更接口入参*************************");
		logger.info("CRM request str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001033", ResponseBean.class);
		logger.info("*************************CRM基础业务变更接口出参*************************");
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
		logger.info("*************************APP来显订购接口出参*************************");
		logger.info("APP response str:"+resultStr);
		return resultStr;
	}

}
