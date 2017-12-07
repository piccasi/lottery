package com.tydic.uniform.hh.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.util.PayUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.AgentOrderRechargeServiceServ;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.vo.rep.AgentOrderRechargeVo;

import net.sf.json.JSONObject;

@Service("AgentOrderRechargeServiceServ")
public class AgentOrderRechargeServiceImpl extends AbstractService implements AgentOrderRechargeServiceServ{
	private static Logger log = Logger.getLogger(AgentBackFileByOCRServiceImpl.class);
	/**
	 * 170号码充值
	 * @author ghp 2016年7月14日10:15:28
	 * @Method: agentFaceBackfile 
	 * @Description: TODO
	 * @param agentbackfilebyocrvo
	 * @return 
	 */
	@Override
	public String recharge(AgentOrderRechargeVo agentOrderRechargeVo) {

		log.info("*************************APP空中充值接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentOrderRechargeVo).toString());

		/*
		 * 按照文档设置接口入参
		 */
		String pwd = MD5Utils.toMD5(agentOrderRechargeVo.getPwd());
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());

		SimpleDateFormat sf = new SimpleDateFormat("YYYYMMddHHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmssSSS");
		
		//流水号
		String requestId = "p12345611"+sdf.format(new Date())+(new Random().nextInt(9999)%(9999-1001+1) + 1001);
		//时间戳
		String requestTime = sf.format(new Date());
		
		Map<String, Object> req = new HashMap<String, Object>();
		req.put("PayAcct", agentOrderRechargeVo.getOrg_code());
		req.put("Pwd", pwd);
		req.put("RequestSource", "1");
		req.put("RequestUser", "hello");
		req.put("RequestId", requestId);
		req.put("RequestTime", requestTime);
		req.put("DestinationId", agentOrderRechargeVo.getMobile_170());
		req.put("DestinationAttr", "8");
		req.put("DestinationAttrDetail", "9");
		req.put("ObjType", "1");
		req.put("BalanceType", "1");
		req.put("RechargeUnit", "1");
		req.put("RechargeAmount", agentOrderRechargeVo.getRechargeAmount());
		req.put("RechargeType", "0");
		req.put("StaffId", agentOrderRechargeVo.getSystemuserid());
		sooList.get(0).put("RechargeReq", req);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		/*
		 * 请求接口
		 */
		log.info("*************************空中充值接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1003035", ResponseBean.class);

		log.info("*************************空中充值接口出参*************************");
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

		String resultString = "";
		if (HhConstants.SUCCESS.equals(status)) {
			if (resultsooMap.containsKey(HhConstants.RESP)) {
				resultMap.put("responseid", resultResultMap.get("ResponseId"));
				resultMap.put("responsetime", resultResultMap.get("ResponseTime"));
				resultMap.put("balanceunit", resultResultMap.get("BalanceUnit"));
				resultMap.put("balancetype", resultResultMap.get("BalanceType"));
				resultMap.put("balance", resultResultMap.get("Balance"));
				resultMap.put("effectivetime", resultResultMap.get("EffectiveTime"));
				resultMap.put("expirationtime", resultResultMap.get("ExpirationTime"));
			}
			resultString = JsonResponse.toSuccessResult(resultMap);
		} else if ("100".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "输入参数缺失");
		} else if ("101".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "输入参数格式错误");
		} else if ("201".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "查询用户资料失败");
		} else if ("203".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "号码未返档，无法充值");
		} else if ("209".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "代理商账号密码错误");
		} else if ("501".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "代理商余额不足");
		} else if ("403".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "流水号已充值");
		} else if ("700".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "系统异常");
		} else if ("507".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "充值超时");
		} else if ("701".equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "该用户当前不可用，不能进行充值");
		}else{
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "未知异常"+status);
		}
		log.info("*************************APP空中充值接口出参*************************");
		log.info("APP response str:" + DesEncryptUtil.decrypt(resultString));

		return resultString;
	}
	
	/**
	 * 代理商加款
	 * @author ghp 2016年7月14日10:15:28
	 * @Method: agentFaceBackfile 
	 * @Description: TODO
	 * @param agentbackfilebyocrvo
	 * @return 
	 */
	@Override
	public String agentRecharge(AgentOrderRechargeVo agentOrderRechargeVo) {

		log.info("*************************APP 代理商充值接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentOrderRechargeVo).toString());

		/*
		 * 按照文档设置接口入参
		 */
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();

		Map<String,Object> sso = new HashMap<String,Object>();
		
		Map<String, Object> req = new HashMap<String, Object>();
		req.put("StaffId", agentOrderRechargeVo.getSystemuserid());
		req.put("Type", "0");
		req.put("Balance", agentOrderRechargeVo.getRechargeAmount());
		req.put("AccessType", "2");
		sso.put("AgentOrder", req);
		
		Map<String, Object> pub = new HashMap<String, Object>();
		pub.put("TYPE", "AgentOrder");
		sso.put("PUB_REQ", pub);
		
		sooList.add(sso);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS 订单保存接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1003044", ResponseBean.class);

		log.info("*************************BOSS 订单保存接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());

		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = null;

		List<Map<String, Object>> resultSooList = (List<Map<String, Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);

		String resultString = "";
		if (HhConstants.SUCCESS.equals(status)) {
			//关闭老的返回信息
//			resultMap = PayUtil.getPay(resultResultMap);
			
			resultMap = PayUtil.aliToPay(resultResultMap);
			
			resultString = JsonResponse.toSuccessResult(resultMap);
		
		}else{
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, (String) resultResultMap.get(HhConstants.BILLMSG));
		}
		log.info("*************************APP 代理商充值接口出参*************************");
		log.info("APP response str:" + DesEncryptUtil.decrypt(resultString));

		return resultString;
	}
}
