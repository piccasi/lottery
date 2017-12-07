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
import com.tydic.uniform.hh.service.interfaces.CardRechargeServiceServ;
import com.tydic.uniform.hh.vo.rep.CardRechargeVo;
import com.tydic.uniform.hh.vo.resp.CardRechargeResp;

import net.sf.json.JSONObject;
/**
 * CardRecharge
 * <p></p>
 * @author Administrator 2015年10月18日 下午12:56:20
 * @ClassName CardRechargeServiceImpl
 * @Description 充值卡充值
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月18日
 * @modify by reason:{方法名}:{原因}
 */
@Service("CardRechargeServiceServ")
@SuppressWarnings("unchecked")
public class CardRechargeServiceImpl extends AbstractService implements CardRechargeServiceServ {
	private static Logger log = Logger.getLogger(CardRechargeServiceImpl.class);

	@Override
	public Map<String, Object> cardrechargeserviceserv(CardRechargeVo cardrechargevo) {
		log.info("*************************APP号码查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(cardrechargevo).toString());
		/*
		 * 按照文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.REQUEST_ID,cardrechargevo.getRequest_id());
		req.put(HhConstants.REQUEST_USER,cardrechargevo.getRequest_user());
		req.put(HhConstants.REQUEST_TIME,cardrechargevo.getRequest_time());
		req.put(HhConstants.CARD_PIN,cardrechargevo.getCard_pin());
		req.put(HhConstants.DESTINATION_ID,cardrechargevo.getDestination_id());
		req.put(HhConstants.ACCESS_TYPE,cardrechargevo.getAccess_type());
		req.put(HhConstants.CHANNEL_CODE,HhConstants.EXT_SYSTEMVALUE);
		req.put(HhConstants.EXT_SYSTEM,HhConstants.EXT_SYSTEM_VALUE);
		req.put(HhConstants.STAFF_ID,HhConstants.STAFF_IDMAVLUE);
		sooList.get(0).put(HhConstants.CARD_RECHARGE, req);
		
		Map<String,Object> typemap = new HashMap<String,Object>();
		typemap.put(HhConstants.TYPE, HhConstants.CARDRECHARGE);
		sooList.get(0).put(HhConstants.PUB_REQ, typemap);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		/*
		 * 请求接口
		 */
		log.info("*************************充值卡充值接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003025", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************充值卡充值接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
		String result = (String) resultResultMap.get(HhConstants.BILLMSG);
		
		CardRechargeResp cardRechargeResp = new CardRechargeResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			   Map<String,Object> respmap = new HashMap<String,Object>();
			   if (resultsooMap.containsKey(HhConstants.RESP)) {
				   respmap.put("balance", resultResultMap.get("Balance"));
				   respmap.put("rechargeamount", resultResultMap.get("RechargeAmount"));
				   respmap.put("responseid", resultResultMap.get("ResponseId"));
				   respmap.put("responsetime", resultResultMap.get("ResponseTime"));
			   }
			    resultMap.put(HhConstants.RESP, respmap);
			    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    resultMap.put(HhConstants.RESULT, status);
			    resultMap.put(HhConstants.MESSAGE, result);
			    cardRechargeResp.setMapInfo(resultMap);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
		    }
		    resultMap.put("cardRechargeResp", cardRechargeResp);
		    log.info("*************************APP充值卡充值接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
}
