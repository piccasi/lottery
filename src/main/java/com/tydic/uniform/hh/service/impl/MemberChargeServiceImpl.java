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
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.MemberChargeServiceServ;
import com.tydic.uniform.hh.vo.resp.NumberListResp;

import net.sf.json.JSONObject;
import sun.util.logging.resources.logging;

/**
 * <p></p>
 * @author cws 2015年9月29日 下午6:16:12
 * @ClassName ChargeServiceImpl  话费充值实体
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
@Service("chargeServiceServ")
public class MemberChargeServiceImpl extends AbstractService implements MemberChargeServiceServ{

	private static Logger log = Logger.getLogger(ChargeServiceImpl.class);
	/**
	 * @author cws 2015年10月14日 下午2:45:08
	 * @Method: chargeMoney 
	 * @Description: 话费充值 接口
	 * @param memberId,pay_amount
	 * @return 
	 * @see com.tydic.uniform.hh.service.interfaces.ChargeServiceServ#chargeMoney(java.lang.String) 
	 */
	@Override
	public Map<String, Object> chargeMoney(String memberId,String pay_amount) {
		log.info("*************************APP客户资料查询接口入参*************************");
		//log.info("APP request str:" + JSONObject.fromObject(memberId).toString()+ JSONObject.fromObject(pay_amount).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		
		Map<String, Object> chargeInfo = new HashMap<String,Object>();
		//chargeInfo.put(HhConstants.MEMBER_ID, memberId);
		chargeInfo.put(HhConstants.MEMBER_ID, "31000000002598");
		chargeInfo.put(HhConstants.PAY_AMOUNT, pay_amount);
		chargeInfo.put(HhConstants.REQUEST_ID, "112234693");
		chargeInfo.put(HhConstants.PAY_METHOD, "10");
		chargeInfo.put(HhConstants.BANK_CODE, "2");
		chargeInfo.put(HhConstants.BANK_SERIAL_NBR, "1231231231231");
		chargeInfo.put(HhConstants.STAFF_ID, "456");
		chargeInfo.put(HhConstants.SYSTEM_ID, "102");
		
		sooList.get(0).put(HhConstants.RECHARGE_INFO, chargeInfo);
		Map<String,Object> pub_req = new HashMap<String,Object>();
		pub_req.put(HhConstants.TYPE, "RECHARGE");
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS客户资料查询接口入入参*************************");
		log.info("BOSS response str:" + body.toString());
		
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003002", ResponseBean.class);
		

		Map<String,Object> map = returnMapvalue(bean);
		
		return map;
	}
	public Map<String,Object> returnMapvalue(ResponseBean bean){
//		JSONObject resultJson = JSONObject.fromObject(string);
		log.info("*************************BOSS客户资料查询接口入出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSooList.get(0);
		Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		
		NumberListResp  numberListResp=new NumberListResp();
	    if(HhConstants.SUCCESS.equals(status)){
	    	Map<String, Object> cashInfo = (Map)resultsooMap.get("CASH_INFO");
	    	Map<String, Object> pub_res = (Map)resultsooMap.get("PUB_RES");
	    	Map<String,Object> mapInfo = new HashMap<>();
	    	mapInfo.put(HhConstantsResp.CASH.toUpperCase(),cashInfo.get(HhConstants.CASH));
	    	mapInfo.put(HhConstantsResp.TYPE, cashInfo.get(HhConstants.TYPE));

	    	numberListResp.setMapInfo(mapInfo);

	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
	    resultMap.put("numberListResp", numberListResp);
	    log.info("*************************APP客户资料查询接口入出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}
	
}