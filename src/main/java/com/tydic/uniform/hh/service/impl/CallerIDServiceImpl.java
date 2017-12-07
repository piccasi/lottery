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
import com.tydic.uniform.hh.service.interfaces.CallerIDServiceServ;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.vo.rep.CallerIDVo;

import net.sf.json.JSONObject;
/**
 * <p></p>
 * @author ghp 2016年8月10日15:58:17
 * @ClassName CallerIDController
 * @Description TODO 代理商关闭来电显示功能
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月28日
 * @modify by reason:{方法名}:{原因}
 */
@Service("CallerIDServiceServ")
public class CallerIDServiceImpl  extends AbstractService implements CallerIDServiceServ{
	
	private static Logger log = Logger.getLogger(CallerIDServiceImpl.class);
	
	@Override
	public Map<String, Object> callerIDMethod(CallerIDVo calleridvo) {
		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP代理商关闭来电显示接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(calleridvo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(calleridvo).toString());
		/**
		 * APP入参解析
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		sooList.get(0).put("ContactChannle", "10002");
		sooList.get(0).put("JDOrderID", "");
		sooList.get(0).put("MSISDN", calleridvo.getMsisdn());
		sooList.get(0).put("UserEventCode", "4");
		List<Map> linkfacelist = new ArrayList<Map>();

		Map<String,String> callIDMap = new HashMap<String,String>();
		linkfacelist.add(0,new HashMap<>());
		linkfacelist.get(0).put("Action",calleridvo.getAction());
		linkfacelist.get(0).put("ServiceCode", calleridvo.getServiceCode());
		sooList.get(0).put("ServiceDtoList", linkfacelist);
		
		Map body = new HashMap();
		body.put("SOO", sooList);
		log.info("*************************BOSS代理商关闭来电显示接口入参*************************");
		log.info("BOSS request str:" + JSONObject.fromObject(body).toString());
		/*
		 * 请求接口
		 */
		setIntefaceType(IntefaceType.CRM);
		/*
		 * 调用能力平台
		 */
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001033", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.MSG);
		
		 if(HhConstants.SUCCESS.equals(status)){
			 resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			 resultMap.put(HhConstants.RESULT, status);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.RESULT, status);
		    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
		    }
		    log.info("*************************APP代理商关闭来电显示接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			
		return resultMap;
	}

}
