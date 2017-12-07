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
import com.tydic.uniform.hh.service.interfaces.BusinessAcceptValidateServiceServ;
import com.tydic.uniform.hh.vo.rep.BusinessAcceptValidateVo;
import com.tydic.uniform.hh.vo.resp.BusinessAcceptValidateResp;
import com.tydic.uniform.hh.vo.resp.ResourceDonationResp;

import net.sf.json.JSONObject;

/**
 * 
 * <p></p>
 * @author Administrator 2015年10月15日 上午9:50:15
 * @ClassName BusinessAcceptValidateServiceImpl
 * @Description 业务受理验证
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月15日
 * @modify by reason:{方法名}:{原因}
 */
@Service("BusinessAcceptValidateServiceServ")
public class BusinessAcceptValidateServiceImpl extends AbstractService implements BusinessAcceptValidateServiceServ{
	private static Logger log = Logger.getLogger(BusinessAcceptValidateServiceImpl.class);
	@Override
	public Map<String, Object> businessAcceptValidateService(BusinessAcceptValidateVo businessacceptvalidatevo) {

		log.info("*************************APP号码查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(businessacceptvalidatevo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEM_VALUE);
		if(businessacceptvalidatevo.getDevice_number()!=null){
			req.put(HhConstants.DEVICE_NUMBER, businessacceptvalidatevo.getDevice_number());
		}
		if(businessacceptvalidatevo.getService_code()!=null){
			req.put(HhConstants.SERVICE_CODE,businessacceptvalidatevo.getService_code());
		}
		sooList.get(0).put(HhConstants.BUSI_CHECK,req);
		
		Map<String,Object> pub_req = new HashMap<String,Object>();
		pub_req.put(HhConstants.TYPE, HhConstants.BUSI_CHECK);
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS号码查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001027", ResponseBean.class);
		
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
		
		BusinessAcceptValidateResp businessAcceptValidateResp = new BusinessAcceptValidateResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    resultMap.put(HhConstants.RESULT, status);
			    resultMap.put(HhConstants.MSG, result);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, result);//失败描述
		    }
		    resultMap.put("businessAcceptValidateResp", businessAcceptValidateResp);
		    log.info("*************************APP号码查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
}
