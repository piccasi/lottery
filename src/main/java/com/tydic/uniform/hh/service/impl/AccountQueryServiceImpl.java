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
import com.tydic.uniform.hh.service.interfaces.AccountQueryServiceeServ;
import com.tydic.uniform.hh.util.MD5Encrypt2;
import com.tydic.uniform.hh.vo.rep.AccountQueryVo;
import com.tydic.uniform.hh.vo.resp.AccountQueryResp;
import com.tydic.uniform.hh.vo.resp.SkyRechargeResp;

import net.sf.json.JSONObject;

@Service("AccountQueryServiceeServ")
public class AccountQueryServiceImpl extends AbstractService implements AccountQueryServiceeServ {
	private static Logger log = Logger.getLogger(AccountQueryServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> AccountQuery(AccountQueryVo accountqueryvo) {

		log.info("*************************APP代理商余额查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(accountqueryvo).toString());

		/*
		 * 按照文档设置接口入参
		 */

		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();

		sooList.add(0, new HashMap<String, Object>());

		Map<String, Object> req = new HashMap<String, Object>();
		req.put("RequestSource", accountqueryvo.getRequestsource());
		req.put("RequestUser", accountqueryvo.getRequestuser());
		req.put("RequestId", accountqueryvo.getRequestid());
		req.put("RequestTime", accountqueryvo.getRequesttime());
		req.put("DestinationId", accountqueryvo.getDestinationid());
		req.put("DestinationAttr", accountqueryvo.getDestinationattr());
		req.put("ObjType", accountqueryvo.getObjtype());
		req.put("BalanceType", accountqueryvo.getBalancetype());
		req.put("BalanceQueryFlag", accountqueryvo.getBalancequeryflag());

		sooList.get(0).put("ACCOUNTQUERY_REQ", req);

		Map<String, Object> typemap = new HashMap<String, Object>();
		typemap.put(HhConstants.TYPE, "AccountQuery");
		sooList.get(0).put(HhConstants.PUB_REQ, typemap);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		/*
		 * 请求接口
		 */
		log.info("*************************余额查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1003037", ResponseBean.class);

		log.info("*************************余额查询接口出参*************************");
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
		String result = (String) resultResultMap.get(HhConstants.Msg);

//		AccountQueryResp accountqueryresp = new AccountQueryResp();

		if (HhConstants.SUCCESS.equals(status)) {
			Map<String, Object> respmap = new HashMap<String, Object>();
			if (resultsooMap.containsKey(HhConstants.RESP)) {
//				respmap.put(HhConstants.BALANCE, resultResultMap.get("Balance"));
				resultMap.put("responseid", resultResultMap.get("ResponseId"));
				resultMap.put("responsetime", resultResultMap.get("ResponseTime"));
				resultMap.put("responseamount", resultResultMap.get("ResponseAmount"));
				resultMap.put("QueryItem", resultResultMap.get("Query-Item"));
			}
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.MESSAGE, result);
//			accountqueryresp.setMapInfo(resultMap);
		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
		}
//		resultMap.put(HhConstants.RESP, accountqueryresp);
//		System.out.println(accountqueryresp + "kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		log.info("*************************APP代理商余额查询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());

		return resultMap;
	}

}
