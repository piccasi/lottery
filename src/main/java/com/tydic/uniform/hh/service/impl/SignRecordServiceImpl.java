package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.SignRecordServiceServ;
import com.tydic.uniform.hh.vo.rep.SignRecordVo;
import com.tydic.uniform.hh.vo.resp.SignRecordResp;

/**
 * <p>
 * </p>
 * @author fangxiangxiang 2015年11月17日 下午4:48:09
 * @ClassName SignRecordServiceImpl 查询签到记录
 */

@Service("SignRecordServiceServ")
public class SignRecordServiceImpl extends AbstractService implements SignRecordServiceServ {

	private static Logger log = Logger.getLogger(SignRecordServiceImpl.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> Signrecord(SignRecordVo signrecordvo) {
		
		log.info("*************************APP签到记录接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(signrecordvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		Map param = new HashMap();
		
		param.put(HhConstants.CUSTID,signrecordvo.getCustId());
		param.put(HhConstants.SIGNYEAR,signrecordvo.getSignYear());
		param.put(HhConstants.SIGNMONTH,signrecordvo.getSignMonth());
		param.put(HhConstants.SIGNTYPE,signrecordvo.getSignType());
		
		Map<String,Object> body= new HashMap<String,Object>();
		body.put(HhConstants.PARAM,param);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS用户签到记录接口入参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(body).toString());
			
		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1002017", ResponseBean.class);
		
		
		log.info("*************************BOSS用户签到记录接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		 */
		
		List resultRspDatalist = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.ESHOPREPDATA);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map resultDataMap = bean.getBody().get(0);
		String status = (String)bean.getBody().get(0).get(HhConstants.ESHOPRESULT);
	
		SignRecordResp  signRecordResp = new SignRecordResp();
		
	    if(HhConstants.SUCCESS.equals(status)){    
	    	if(resultDataMap.containsKey(HhConstants.ESHOPREPDATA)){
	    	       Map<String,Object> mapInfo = new HashMap<>();
	    		   ArrayList resplist = new ArrayList();
	    		   
	    		   //如果当月全部签到，就调取大礼包
	    		   Calendar time=Calendar.getInstance(); 
	    		   time.clear(); 
	    		   time.set(Calendar.YEAR,Integer.parseInt(signrecordvo.getSignYear())); 
	    		   //year年
	    		   time.set(Calendar.MONTH,Integer.parseInt(signrecordvo.getSignMonth())-1);
	    		   //Calendar对象默认一月为0,month月 
	    		   int day=time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
	    		   Object bigresultMap =false;
	    		   //bigresultmap true已领    false未领
	    		   if(resultRspDatalist.size() == day){
	    			   //判断大礼包是否已领
	    			   bigresultMap = getSignBigrecord(signrecordvo);
	    		   }
	    		   
	    		// List service_infolist=(List) resultsooMap.get(HhConstants.SERVICE_INFO);
	    		 for(int i=0; i<resultRspDatalist.size(); i++){
	    			 Map sign_infomap = (Map) resultRspDatalist.get(i);
	    			 HashMap respmap =new HashMap();
	    			 respmap.put(HhConstantsResp.CUSTID, sign_infomap.get(HhConstants.CUSTID));
	    			 respmap.put(HhConstantsResp.SIGNAMOUNT, sign_infomap.get(HhConstants.SIGNAMOUNT));
	    			 respmap.put(HhConstantsResp.SIGNID, sign_infomap.get(HhConstants.SIGNID));
	    			 respmap.put(HhConstantsResp.SIGNMONTH, sign_infomap.get(HhConstants.SIGNMONTH));
	    			 respmap.put(HhConstantsResp.SIGNYEAR, sign_infomap.get(HhConstants.SIGNYEAR));
	    			 respmap.put(HhConstantsResp.SIGNTIME, sign_infomap.get(HhConstants.SIGNTIME));
	    			 respmap.put(HhConstantsResp.SIGNTYPE, sign_infomap.get(HhConstants.SIGNTYPE));
	    			 resplist.add(respmap);
	    		}
	    		 
	    		 mapInfo.put(HhConstantsResp.ESHOPREPDATA, resplist);
	    		 mapInfo.put("bigresultmap", bigresultMap);
	    		 
	    		 signRecordResp.setMapInfo(mapInfo);
	    	}
	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
	    	
	    }else if(HhConstants.ERROR.equals(status)){
	    	String ERRORCODE = (String)bean.getBody().get(0).get(HhConstants.RSPERRCODE);
	    	String ERRORMESSAGE = (String)bean.getBody().get(0).get(HhConstants.RSPMSG);
	    	resultMap.put(HhConstants.CODE, ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, ERRORMESSAGE);//失败描述
	    } 
		
	
		    resultMap.put("signrecordResp",signRecordResp);
		    
		log.info("*************************APP签到记录接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
	
	
	public Object getSignBigrecord(SignRecordVo signrecordvo) {
		
		//boolean hasAllSigned = false;
		Object hasAllSigned = null;
		Map param = new HashMap();
		param.put(HhConstants.CUSTID,signrecordvo.getCustId());
		param.put(HhConstants.SIGNYEAR,signrecordvo.getSignYear());
		param.put(HhConstants.SIGNMONTH,signrecordvo.getSignMonth());
		param.put(HhConstants.SIGNTYPE,"2");   //传入signtype为2，去获取是否获取大礼包。
		
		Map<String,Object> body= new HashMap<String,Object>();
		body.put(HhConstants.PARAM,param);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS用户大礼包签到记录接口入参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(body).toString());
			
		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1002017", ResponseBean.class);
		
		
		log.info("*************************BOSS用户大礼包签到记录接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		 */
		
		List resultRspDatalist = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.ESHOPREPDATA);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map resultDataMap = bean.getBody().get(0);
		String status = (String)bean.getBody().get(0).get(HhConstants.ESHOPRESULT);
	
		SignRecordResp  signRecordResp = new SignRecordResp();
		
	    if(HhConstants.SUCCESS.equals(status)){    
	    	if(resultDataMap.containsKey(HhConstants.ESHOPREPDATA)){
	    		if(resultRspDatalist.size()>0){
	    			 Map sign2_infomap = (Map) resultRspDatalist.get(0);
	    			return sign2_infomap.get(HhConstants.SIGNFLOW);
	    		}
	    	}
	    }else if(HhConstants.ERROR.equals(status)){
	    	hasAllSigned = null;
	    } 
		log.info("*************************APP大礼包签到记录接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return hasAllSigned;
	}
}
