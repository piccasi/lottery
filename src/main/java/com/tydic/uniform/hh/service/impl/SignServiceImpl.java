package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.SignServiceServ;
import com.tydic.uniform.hh.vo.rep.SignVo;
import com.tydic.uniform.hh.vo.resp.NumberListResp;
import com.tydic.uniform.hh.vo.resp.SignResp;

/**
 * <p>
 * </p>
 * @author fangxiangxiang 2015年11月18日 下午2:48:09
 * @ClassName SignServiceImpl 签到
 */

@Service("SignServiceServ")
public class SignServiceImpl extends AbstractService implements SignServiceServ {

	private static Logger log = Logger.getLogger(SignServiceImpl.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Sign(SignVo signvo) {
		
		log.info("*************************APP客户资料查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(signvo).toString());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/**
		 * 屏蔽领取大礼包  signvo.getSignType().equals("2")
		 * add by panxinxing 2016/01/06 
		 */
		if(signvo.getSignType().equals("2")){
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			return resultMap;
		}
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		
		Map req = new HashMap();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		if(signvo.getCustId()!=""&&signvo.getCustId()!=null){
			req .put(HhConstants.CUST_ID, signvo.getCustId());
		}
		
		sooList.get(0).put(HhConstants.CUST_QRY,req);
		
		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "CUST_QUERY");
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS客户资料查询接口入入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001004", ResponseBean.class);
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSooList.get(0);
		Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		

		
	    if(HhConstants.SUCCESS.equals(status)){
	    	List custlist=(List) resultsooMap.get("CUST");
	    	Map<String, Object> custmap = (Map) custlist.get(0);
	    	String mobile_170 = custmap.get(HhConstants.MOBILE_170).toString();
	    	if(mobile_170!=null&&!mobile_170.equals("")){
	    		resultMap = dateSign(signvo);
	    	}else{
	    		resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, "您还不是170用户,不能进行签到");//失败描述
	    	}
	    }else{
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			if(result!=null&&!result.equals(""))
				resultMap.put(HhConstants.MESSAGE,result);//失败描述
	    }
	    return resultMap;
	}
	
	
	public Map<String, Object> dateSign(SignVo signvo) {
		
		log.info("*************************APP签到接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(signvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		Map param = new HashMap();
		
		param.put(HhConstants.CUSTID,signvo.getCustId());
		param.put(HhConstants.SIGNYEAR,signvo.getSignYear());
		param.put(HhConstants.SIGNMONTH,signvo.getSignMonth());
		param.put(HhConstants.SIGNTYPE,signvo.getSignType());
		
		Map<String,Object> body= new HashMap<String,Object>();
		body.put(HhConstants.PARAM,param);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS用户签到接口入参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(body).toString());
			
		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1002018", ResponseBean.class);
		
		
		log.info("*************************BOSS用户签到接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		 */
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String status = (String)bean.getBody().get(0).get(HhConstants.ESHOPRESULT);
		String msg = (String)bean.getBody().get(0).get(HhConstants.RSPMSG);
		Map rspDataMap = (Map) bean.getBody().get(0).get(HhConstants.ESHOPREPDATA);
	
		
	    if(HhConstants.SUCCESS.equals(status)){    	    	
	        resultMap.put(HhConstantsResp.SIGNNUMBER, rspDataMap.get(HhConstants.SIGNNUMBER));	    	
            resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功	    	
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, msg);//失败描述
	    } 
		
		    
		log.info("*************************APP签到接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
		
	}
}
