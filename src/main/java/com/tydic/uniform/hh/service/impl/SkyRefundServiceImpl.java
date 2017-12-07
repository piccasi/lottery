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
import com.tydic.uniform.hh.service.interfaces.SkyRefundServiceServ;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.vo.rep.SkyRefundVo;
import com.tydic.uniform.hh.vo.resp.SkyRefundResp;

import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Service("SkyRefundServiceServ")
public class SkyRefundServiceImpl extends AbstractService implements SkyRefundServiceServ {
	private static Logger log = Logger.getLogger(SkyRefundServiceImpl.class);

	@Override
	public Map<String, Object> airRefund(SkyRefundVo skyrefundvo) {
		log.info("*************************空中充值交易冲正接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(skyrefundvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		String pwd = MD5Utils.MD5(skyrefundvo.getPwd());

		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());

		Map<String, Object> req = new HashMap<String, Object>();
		req.put(HhConstants.PAYACCT, skyrefundvo.getPayAcct());
		req.put(HhConstants.PWD, pwd);
		req.put(HhConstants.REQUESTSOURCE, skyrefundvo.getRequestSource());
		req.put(HhConstants.REQUESTUSER, skyrefundvo.getRequestUser());
		req.put(HhConstants.REQUESTID, skyrefundvo.getRequestId());
		req.put(HhConstants.REQUESTTIME, skyrefundvo.getRequestTime());
		req.put(HhConstants.OLDREQUESTID, skyrefundvo.getOldRequestTime());
		req.put(HhConstants.DESTINATIONID, skyrefundvo.getDestinationId());
		req.put(HhConstants.DESTINATIONATTR, skyrefundvo.getDestinationAttr());
		req.put(HhConstants.OBJTYPE, skyrefundvo.getObjType());

		Map<String, Object> pub_req = new HashMap<String, Object>();
		pub_req.put(HhConstants.TYPE, "Refund");

		sooList.get(0).put(HhConstants.PUB_REQ, pub_req);
		sooList.get(0).put("REFUND_REQ", req);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS空中充值交易冲正接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001036", ResponseBean.class);
		log.info("*************************BOSS空中充值交易冲正接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> result_resp = (Map<String, Object>) bean.getBody().get(0).get(HhConstants.RESP);
		String status = (String) result_resp.get(HhConstants.RESULT);
		String result = (String) result_resp.get(HhConstants.MSG);

		SkyRefundResp skyrefundresp = new SkyRefundResp();

		if (HhConstants.SUCCESS.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.MSG, result);
		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
		}
		resultMap.put("skyrefundresp", skyrefundresp);
		log.info("*************************APP空中充值交易冲正接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

}
