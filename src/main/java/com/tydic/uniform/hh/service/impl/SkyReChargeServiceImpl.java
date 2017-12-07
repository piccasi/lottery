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
import com.tydic.uniform.hh.service.interfaces.SkyRechargeServiceServ;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.vo.rep.SkyRechargeVo;

import net.sf.json.JSONObject;

@Service("SkyRechargeServiceServ")

public class SkyReChargeServiceImpl extends AbstractService implements SkyRechargeServiceServ {

	private static Logger log = Logger.getLogger(SkyReChargeServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> skyRecharge(SkyRechargeVo skyRechargevo) {

		log.info("*************************APP空中充值接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(skyRechargevo).toString());

		/*
		 * 按照文档设置接口入参
		 */
//		String pwd = MD5BillAgentUtil.GetMD5Code(skyRechargevo.getPwd());
		String pwd = MD5Utils.toMD5(skyRechargevo.getPwd());
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());

		Map<String, Object> req = new HashMap<String, Object>();
		req.put("PayAcct", skyRechargevo.getPayacct());
		req.put("Pwd", pwd);
		req.put("RequestSource", skyRechargevo.getRequestsource());
		req.put("RequestUser", skyRechargevo.getRequestuser());
		req.put("RequestId", skyRechargevo.getRequestid());
		req.put("RequestTime", skyRechargevo.getRequesttime());
		req.put("DestinationId", skyRechargevo.getDestinationid());
		req.put("DestinationAttr", skyRechargevo.getDestinationattr());
		req.put("DestinationAttrDetail", skyRechargevo.getDestination_attr_detail());
		req.put("ObjType", skyRechargevo.getObjtype());
		req.put("BalanceType", skyRechargevo.getBalancetype());
		req.put("RechargeUnit", skyRechargevo.getRechargeunit());
		req.put("RechargeAmount", skyRechargevo.getRechargeamount());
		req.put("RechargeType", skyRechargevo.getRechargetype());
		req.put("StaffId", skyRechargevo.getStaffid());
		sooList.get(0).put("RechargeReq", req);

//		Map<String, Object> typemap = new HashMap<String, Object>();
//		typemap.put(HhConstants.TYPE, "AirRecharge");
//		sooList.get(0).put(HhConstants.PUB_REQ, typemap);

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
		String result = (String) resultResultMap.get(HhConstants.BILLMSG);

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
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.MESSAGE, "成功");
		} else if ("100".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "输入参数缺失");// 失败描述
		} else if ("101".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE,"输入参数格式错误");// 失败描述
		} else if ("201".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "查询用户资料失败");// 失败描述
		} else if ("203".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "号码未返档，无法充值");// 失败描述
		} else if ("209".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "代理商账号密码错误");// 失败描述
		} else if ("501".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "代理商余额不足");// 失败描述
		} else if ("403".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "流水号已充值");// 失败描述
		} else if ("700".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "系统异常");// 失败描述
		} else if ("507".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "充值超时");// 失败描述
		}
		log.info("*************************APP空中充值接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());

		return resultMap;
	}

}
