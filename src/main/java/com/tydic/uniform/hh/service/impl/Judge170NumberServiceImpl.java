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
import com.tydic.uniform.hh.service.interfaces.Judge170NumberServiceServ;
import com.tydic.uniform.hh.vo.rep.Judge170NumberVo;
import com.tydic.uniform.hh.vo.rep.JudgeSameMemberVo;
import com.tydic.uniform.hh.vo.resp.Judge170NumberResp;
import com.tydic.uniform.hh.vo.resp.MemberInfoModifyResp;

import net.sf.json.JSONObject;
@Service("Judge170NumberServiceServ")
public class Judge170NumberServiceImpl extends AbstractService implements Judge170NumberServiceServ {
	private static Logger log = Logger.getLogger(Judge170NumberServiceImpl.class);
	@Override
	public Map<String, Object> Judge170Numberserviceserv(Judge170NumberVo judge170numbervo) {
		log.info("*************************APP号码查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(judge170numbervo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEM_VALUE);
		req.put(HhConstants.ACC_NBR_170, judge170numbervo.getAcc_nbr_170().trim());
		
		sooList.get(0).put(HhConstants.PROD, req);
		
		Map<String,Object> typemap=new HashMap<String,Object>();
		typemap.put(HhConstants.TYPE, HhConstants.QRY_PROD);
		sooList.get(0).put(HhConstants.PUB_REQ, typemap);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS号码查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001014", ResponseBean.class);
		
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
		
		Judge170NumberResp judge170NumberResp = new Judge170NumberResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    resultMap.put(HhConstants.RESULT, status);
			    resultMap.put(HhConstants.MSG, result);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.MESSAGE, result);//失败描述
		    	if(result.contains("非在网用户")){
		    		resultMap.put(HhConstants.MESSAGE, "号码非在网用户");//失败描述
		    	}
		    }
		    resultMap.put("judge170NumberResp", resultResultMap);
		    log.info("*************************APP号码查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
	/**
	 * 判断推荐人身份证号码是否相同  add by panxinxing  date 2016/01/04
	 */
	@Override
	public Map<String, Object> judgeSameMemberserv(JudgeSameMemberVo judgeSameMemberVo){
		log.info("*************************APP号码查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(judgeSameMemberVo).toString());
		
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEM_VALUE);
		req.put(HhConstants.ACC_NBR_170, judgeSameMemberVo.getAcc_nbr_170());
		
		sooList.get(0).put(HhConstants.PROD, req);
		
		Map<String,Object> typemap=new HashMap<String,Object>();
		typemap.put(HhConstants.TYPE, HhConstants.QRY_PROD);
		sooList.get(0).put(HhConstants.PUB_REQ, typemap);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS号码查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001014", ResponseBean.class);
		
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
		String cert_nbr = (String) resultResultMap.get("CERT_NBR");
		
		Judge170NumberResp judge170NumberResp = new Judge170NumberResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			    if(cert_nbr.equals(judgeSameMemberVo.getCerti_nbr())){
			    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			    	resultMap.put(HhConstants.MESSAGE, "同一证件的170号码不可以作为推荐人手机号");//失败描述
			    }else{
			    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    	resultMap.put(HhConstants.RESULT, status);
			    	resultMap.put(HhConstants.MSG, result);
			    }
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.MESSAGE, result);//失败描述
		    	if(result.contains("非在网用户")){
		    		resultMap.put(HhConstants.MESSAGE, "号码非在网用户");//失败描述
		    	}
		    }
		    resultMap.put("judge170NumberResp", resultResultMap);
		    log.info("*************************APP号码查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
}
