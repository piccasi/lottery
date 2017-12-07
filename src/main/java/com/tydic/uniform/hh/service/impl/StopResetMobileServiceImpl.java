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
import com.tydic.uniform.hh.service.interfaces.StopResetMobileServiceServ;
import com.tydic.uniform.hh.vo.rep.StopResetMobileVo;
import com.tydic.uniform.hh.vo.resp.StopResetMobileResp;

import net.sf.json.JSONObject;
/**
 * 
 * <p></p>
 * @author Administrator 2015年10月15日 下午10:01:03
 * @ClassName StopResetMobileServiceImpl
 * @Description 停复机
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月15日
 * @modify by reason:{方法名}:{原因}
 */
@Service("StopResetMobileServiceServ")
public class StopResetMobileServiceImpl extends AbstractService implements StopResetMobileServiceServ {
	private static Logger log = Logger.getLogger(StopResetMobileServiceImpl.class);
	@Override
	public Map<String, Object> stopresetmobileserviceserv(StopResetMobileVo stopresetmobilevo) {
		log.info("*************************APP号码查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(stopresetmobilevo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		String ORDER_ID = System.currentTimeMillis()+"";
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.CUST_ID, stopresetmobilevo.getCust_id());
		req.put(HhConstants.EXT_ORDER_ID, stopresetmobilevo.getExt_order_id());
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		req.put(HhConstants.SALE_ORDER_ID, ORDER_ID);
		sooList.get(0).put(HhConstants.SALE_ORDER_INST, req);
		
		Map<String,Object> typemap = new HashMap<String,Object>();
		typemap.put(HhConstants.TYPE, HhConstants.SAVE_SALE_ORDER_INST);
		sooList.get(0).put(HhConstants.PUB_REQ, typemap);
		
		sooList.add(1, new HashMap<String,Object>());
		Map<String,Object> req_s = new HashMap<String,Object>();
		req_s.put(HhConstants.ACC_NBR, stopresetmobilevo.getAcc_nbr());
		req_s.put(HhConstants.MAIN_FLAG, stopresetmobilevo.getMain_flag());
		req_s.put(HhConstants.NEW_FLAG, stopresetmobilevo.getNew_flag());
		req_s.put(HhConstants.PRODUCT_ID, stopresetmobilevo.getProduct_id());
		req_s.put(HhConstants.PROD_INST_ID, stopresetmobilevo.getProd_inst_id());
		req_s.put(HhConstants.SALE_ORDER_ID, ORDER_ID);
		req_s.put(HhConstants.SERVICE_OFFER_ID, stopresetmobilevo.getService_offer_id());
		sooList.get(1).put(HhConstants.SALE_PROD_INST, req_s);
		
		Map<String,Object> typemap_s = new HashMap<String,Object>();
		typemap_s.put(HhConstants.TYPE, HhConstants.SAVE_SALE_PROD_INST);
		sooList.get(1).put(HhConstants.PUB_REQ, typemap_s);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS号码查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001017", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS号码查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		
		StopResetMobileResp stopResetMobileResp = new StopResetMobileResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    resultMap.put(HhConstants.RESULT, status);
			    resultMap.put(HhConstants.MSG, result);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, result);//失败描述
		    }
		    resultMap.put("stopResetMobileResp", stopResetMobileResp);
		    log.info("*************************APP号码查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
}
