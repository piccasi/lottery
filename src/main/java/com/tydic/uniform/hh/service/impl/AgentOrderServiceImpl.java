package com.tydic.uniform.hh.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.AgentOrderServiceServ;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.vo.rep.AgentBackFileVo;
import com.tydic.uniform.hh.vo.rep.AgentOrderBackFileVo;
import com.tydic.uniform.hh.vo.rep.AgentOrderVo;
import com.tydic.uniform.hh.vo.rep.MealListVo;

import net.sf.json.JSONObject;

@Service("AgentOrderServiceServ")
public class AgentOrderServiceImpl extends AbstractService implements AgentOrderServiceServ{
	private static Logger log = Logger.getLogger(AgentBackFileByOCRServiceImpl.class);
	/**
	 * @author ghp 2016年7月14日10:15:28
	 * @Method: agentFaceBackfile 
	 * @Description: TODO
	 * @param agentbackfilebyocrvo
	 * @return 
	 */
	@Override
	public String actCheck(AgentOrderVo gentOrderVo) {
		log.info("*************************APP返档激活校验接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(gentOrderVo).toString());
		
		//获取是否海航170号码信息
		Map<String, Object> mobile170Map = isMobile170(gentOrderVo);
		if(HhConstants.SUCCESSCODE.equals(mobile170Map.get(HhConstants.CODE))){
			
			//获取会员资料
			Map<String, Object> userInfoMap = getUserInfo(gentOrderVo);
			if(HhConstants.SUCCESSCODE.equals(userInfoMap.get(HhConstants.CODE))){
				
				//号码激活校验
				Map<String, Object> checkMap = check(gentOrderVo,userInfoMap);
				if(HhConstants.SUCCESSCODE.equals(checkMap.get(HhConstants.CODE))){
					
					Map<String, Object> data = new HashMap<String, Object>();
					Map<String,Object> cust = (Map<String,Object>)userInfoMap.get("CUST");
					data.put("cust_id", cust.get("CUST_ID"));
					data.put("nick_name", cust.get("NICK_NAME"));
					data.put("mobile_170", gentOrderVo.getMobile_170());
					data.put("act_amount", checkMap.get("ACT_AMOUNT"));
					data.put("pay_status", checkMap.get("PAY_STATUS"));
					List<Map<String, Object>> offerMapList = (List<Map<String, Object>>)cust.get("PROD_OFFER_INST");
					for(int i=0;i<offerMapList.size();i++){
						Map<String, Object> offerMap = offerMapList.get(i);
						//获取基础包
						if("11".equals(offerMap.get("OFFER_TYPE"))){
							data.put("offr_id", offerMap.get("PROD_OFFER_ID"));
							data.put("offr_name", offerMap.get("PROD_OFFER_NAME"));
						}
					}
					log.info("*************************APP号码套餐查询接口出参*************************");
					log.info("APP response str:"+DesEncryptUtil.decrypt(JsonResponse.toSuccessResult(data)));
					return JsonResponse.toSuccessResult(data);
				}else{
					return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, checkMap.get(HhConstants.MESSAGE).toString());
				}
			}else{
				return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, userInfoMap.get(HhConstants.MESSAGE).toString());
			}
		}else{
			String resultString = "";
			if(mobile170Map.get(HhConstants.MESSAGE).toString().contains("非在网用户")){
				resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "号码非在网用户");
			}else{
				resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mobile170Map.get(HhConstants.MESSAGE).toString());
			}
			return resultString;
		}
	}
	
	@Override
	public String getMealList(MealListVo mealListvo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP号码套餐查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(mealListvo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(mealListvo).toString());
		
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String,Object> req = new HashMap<String,Object>();
		req.put("TYPE", "NUMBER_QRY");
		Map<String,Object> req1 = new HashMap<String,Object>();
		req1.put("QRY_NUMBER", mealListvo.getQry_number());
		req1.put(HhConstants.CHANNEL_CODE, mealListvo.getOrg_id());
		req1.put("OFR_MODE","01");
		Map<String,Object> body = new HashMap<String,Object>();
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		sooList.get(0).put("PUB_REQ",req);
		sooList.get(0).put("CUST_QRY",req1);
		body.put("SOO", sooList);
		setIntefaceType(IntefaceType.CRM);
		log.info("*************************APP号码套餐查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		ResponseBean bean=(ResponseBean) this.appApiInvoke(body, "SC1001026", ResponseBean.class);
		result = (List<Map<String,Object>>)((Map)((List<Map>) bean.getBody().get(0).get("SOO")).get(0)).get("OFFER_LIST");
		if(StringUtils.isNotEmpty(mealListvo.getCust_flag()) && (mealListvo.getCust_flag().equals("0")||mealListvo.getCust_flag().equals("1"))){
			req1.put("OFR_MODE","05");
			sooList.get(0).put("CUST_QRY",req1);
			body.put("SOO", sooList);
			ResponseBean bean2=(ResponseBean) this.appApiInvoke(body, "SC1001026", ResponseBean.class);
			List<Map<String,Object>> template2 = (List<Map<String,Object>>)((Map)((List<Map>) bean2.getBody().get(0).get("SOO")).get(0)).get("OFFER_LIST");
			result.addAll(template2);
		}
		
		String resultString = "";
		if(result.size()>0){
			resultMap.put("mealListResp", result);
			resultString= JsonResponse.toSuccessResult(resultMap);
		}else{
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, HhConstants.ERRORMESSAGE);
		}
		    
		log.info("*************************APP号码套餐查询接口出参*************************");
		log.info("APP response str:"+DesEncryptUtil.decrypt(resultString));
		return resultString;
	}
	
	@Override
	public String agentFaceBackfile(AgentOrderBackFileVo agentOrderBackFileVo) {
		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP代理商OCR人脸对比接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentOrderBackFileVo).toString());
//		System.out.println("APP request str:" + JSONObject.fromObject(agentOrderBackFileVo).toString());
		
		/*
		 * 获取jquery入参
		 */

		Boolean resultStatus = true;
		String resultString = "";
		
		String name = agentOrderBackFileVo.getName(); //姓名
		if (StringUtils.isEmpty(name)) {
			resultStatus = false;
			resultString = JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "姓名不能为空");
		}
		String certi_nbr = agentOrderBackFileVo.getCertNbr(); //身份张编号
		if (StringUtils.isEmpty(certi_nbr)) {
			resultStatus = false;
			resultString = JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "身份证号码不能为空");
		}
		String selfieFile = agentOrderBackFileVo.getSelfieFile(); //身份证地址
		if (StringUtils.isEmpty(selfieFile)) {
			resultStatus = false;
			resultString = JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "人脸识别照片不能为空");
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
			String picture_Path = sdf.format(new Date());
			String certiid_addr = PropertiesUtil.getPropertyValue("CERTIID_ADDR")+picture_Path+"/";
			selfieFile = certiid_addr + selfieFile;
		}
		
		if(!resultStatus){
			return resultString;
		}
		//获取当前时间戳
		SimpleDateFormat sf = new SimpleDateFormat("YYYYMMDDHHmmssSSS");
		String date_string = sf.format(new Date());
		
		/*,.
		 * APP入参解析
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		sooList.get(0).put("EXT_SYSTEM", "102");
		sooList.get(0).put("CHANNEL_CODE", "10015");
		List<Map> linkfacelist = new ArrayList<Map>();

		Map<String,String> linkfaceMap = new HashMap<String,String>();
		linkfacelist.add(0,new HashMap<>());
		linkfacelist.get(0).put("SYN_ID", date_string);
		linkfacelist.get(0).put("CERTI_NBR", certi_nbr);
		linkfacelist.get(0).put("NAME", name);
		linkfacelist.get(0).put("SELFIE_FILE", selfieFile);
		linkfacelist.get(0).put("SELFIE_AUTO_ROTATE", "0");
		sooList.get(0).put("LINK_FACE", linkfacelist);
		
		Map<String,String> typeMap = new HashMap<String,String>();
		typeMap.put("TYPE", "LINK_FACE_SELFIELD");
		
		sooList.get(0).put("PUB_REQ", typeMap);
		Map body = new HashMap();
		body.put("SOO", sooList);
		log.info("*************************BOSS代理商OCR人脸对比接口出参*************************");
		log.info("BOSS request str:" + JSONObject.fromObject(body).toString());
		/*
		 * 请求接口
		 */
		setIntefaceType(IntefaceType.CRM);
		/*
		 * 调用能力平台
		 */
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001052", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.MSG);
		
		if(HhConstants.SUCCESS.equals(status)){
			resultMap.put(HhConstants.CONFIDENCE, resultResultMap.get("CONFIDENCE"));
			resultString = JsonResponse.toSuccessResult(resultMap);
		}else if(HhConstants.ERROR.equals(status)){
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, msg);
		}
		log.info("*************************APP代理商OCR人脸对比接口出参*************************");
		log.info("APP response str:"+DesEncryptUtil.decrypt(resultString));
		
		return resultString;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String agentBackfile(AgentBackFileVo agentbackfilevo) {
		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		log.info("*************************APP代理商返档接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentbackfilevo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(agentbackfilevo).toString());
		/*
		 * APP入参解析
		 */

		//反档
		String systemuserid = agentbackfilevo.getSystemuserid();
		if(systemuserid.equals(null)||systemuserid.equals("")){
			systemuserid = "";
		}
		String cust_id = agentbackfilevo.getCust_id();
		String mobile = agentbackfilevo.getMobile();
		String nick_name = agentbackfilevo.getNick_name();
		String contact_addr = agentbackfilevo.getContact_addr();
		String certi_addr = agentbackfilevo.getCerti_addr();
		String cust_name = agentbackfilevo.getCust_name();
		String certi_nbr = agentbackfilevo.getCerti_nbr();
		String appendix = agentbackfilevo.getAppendix();
		String mobile_170 = agentbackfilevo.getMobile_170();
		
		String iccid = agentbackfilevo.getIccid();
		String OFR_ID = agentbackfilevo.getOffr_id();
		if (OFR_ID==null||OFR_ID=="") {
			OFR_ID="HNDLS001";
		}
		String org_id = agentbackfilevo.getOrg_id();
		if (null==org_id||""==org_id) {
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "org_id为空");
		}
		String pwd = agentbackfilevo.getPwd();
		/*
		 * 
		 * 组装能力平台入参
		 */
		Map<String,String> pub_rep = new HashMap<String,String>();
		pub_rep.put("TYPE", "CUST_UPDATE");
		Map<String,String> req = new HashMap<String,String>();
		req.put("CHANNEL_CODE", org_id); // 必选
		req.put("EXT_SYSTEM", "102");// 必选
		req.put("CUST_ID", cust_id);// 必选
		req.put("EMAIL", "test@163.com");
		req.put("BIRTHDATE", "1988-08-08");
		req.put("MOBILE", mobile);
		req.put("NICK_NAME", nick_name);
		req.put("SEX", "3");
		req.put("CONTACT_ADDR", contact_addr);
		req.put("CERTI_ADDR", certi_addr);
		req.put("CUST_NAME", cust_name);
		req.put("CERTI_NBR", certi_nbr);
		req.put("APPENDIX", appendix);// 必选
		req.put("ICCID", iccid);

		String confidence = agentbackfilevo.getConfidence();
		if (""==confidence||null==confidence) {
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "人脸对比失败");
		}else {
			req.put("CONFIDENCE", confidence);
		}
		Map<String,Object> body = new HashMap<String,Object>();
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		sooList.get(0).put("CUST", req);
		sooList.get(0).put("MSISDN", mobile_170);
		sooList.get(0).put("OPERATE_TYPE", "2");
		sooList.get(0).put("CHECK_TYPE", "5");
		sooList.get(0).put("APP_FLAG", "1");
		sooList.get(0).put("OFR_ID", OFR_ID);
		sooList.get(0).put("PWD", MD5Utils.toMD5(pwd));
		if(systemuserid!=""){
			sooList.get(0).put("SYSTEMUSERID", systemuserid);
		}
		sooList.get(0).put("PUB_REQ", pub_rep);
		body.put("SOO", sooList);
		setIntefaceType(IntefaceType.CRM);
		/*
		 * 调用能力平台
		 */
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001036", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.MSG);
		String resultString = "";
		if(HhConstants.SUCCESS.equals(status)){
			resultString = JsonResponse.toSuccessResult(null);
		}else if(HhConstants.ERROR.equals(status)){
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, msg);
		}
		log.info("*************************APP代理商返档接口出参*************************");
		log.info("APP response str:"+DesEncryptUtil.decrypt(resultString));

		return resultString;
		
	}
	/**
	 * 是否海航170号码接口查询
	 * @param gentOrderVo
	 * @return
	 */
	@Override
	public Map<String, Object> isMobile170(AgentOrderVo gentOrderVo){
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEM_VALUE);
		req.put(HhConstants.ACC_NBR_170, gentOrderVo.getMobile_170().trim());
		
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
	 * 会员资料查询
	 * @param gentOrderVo
	 * @return
	 */
	public Map<String, Object> getUserInfo(AgentOrderVo gentOrderVo){
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());
		Map<String, Object> req = new HashMap<String, Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		req.put(HhConstants.QRY_TYPE, "1");
		req.put(HhConstants.QRY_NUMBER, gentOrderVo.getMobile_170());
		sooList.get(0).put(HhConstants.CUST_QRY,req);
		Map<String, Object> pub_req = new HashMap<String, Object>();
		pub_req.put(HhConstants.TYPE, "CUST_QUERY");
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS客户资料查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001004", ResponseBean.class);
		log.info("*************************BOSS客户资料查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		Map<String, Object> resultMap = new HashMap<String, Object>();
	    if(HhConstants.SUCCESS.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//失败编码
	    	List custlist=(List) resultsooMap.get("CUST");
	    	Map<String, Object> custmap = (Map) custlist.get(0);
	    	resultMap.put("CUST", custmap);
	    }else{
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			if(result!=null&&!result.equals(""))
				resultMap.put(HhConstants.MESSAGE,result);//失败描述
	    }
	    return resultMap;
	}
	
	/**
	 * 会员资料查询
	 * @param gentOrderVo
	 * @return
	 */
	public Map<String, Object> check(AgentOrderVo gentOrderVo,Map<String, Object> userInfo){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> cust = (Map<String,Object>)userInfo.get("CUST");
		//则是校检
		String cust_id = cust.get("CUST_ID").toString();
		if (null==cust_id||""==cust_id) {
			resultMap.put(HhConstants.MESSAGE, "CUST_ID为空");//失败描述
		}
		String mobile_170 = gentOrderVo.getMobile_170();
		if (null==mobile_170||""==mobile_170) {
			resultMap.put(HhConstants.MESSAGE, "MOBILE_170为空");//失败描述
		}
		String systemuserid = gentOrderVo.getSystemuserid();
		if (null==systemuserid||""==systemuserid) {
			resultMap.put(HhConstants.MESSAGE, "操作人员ID为空");//失败描述
			return resultMap;
		}
		String app_flag = "1";
		String iccid = gentOrderVo.getIccid();
		if (null==iccid||""==iccid) {
			resultMap.put(HhConstants.MESSAGE, "ICCID为空");//失败描述
			return resultMap;
		}
		Map<String,String> pub_rep = new HashMap<String,String>();
		pub_rep.put("TYPE", "CUST_UPDATE");
		Map<String,String> req = new HashMap<String,String>();
		req.put("ICCID", iccid); // 必选
		Map<String,Object> body = new HashMap<String,Object>();
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		sooList.get(0).put("CUST", req);
		sooList.get(0).put("MSISDN", mobile_170);
		sooList.get(0).put("OPERATE_TYPE", "1");
		if(systemuserid!=""){
			sooList.get(0).put("SYSTEMUSERID", systemuserid);
		}
		sooList.get(0).put("APP_FLAG", app_flag);
		sooList.get(0).put("PUB_REQ", pub_rep);
		body.put("SOO", sooList);
		setIntefaceType(IntefaceType.CRM);
		/*
		 * 请求接口
		 */
		log.info("*************************代理商ICCID校检*************************");
		log.info("BOSS response str:" + body.toString());
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001036", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.MSG);
		
		   if(HhConstants.SUCCESS.equals(status)){
			    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    resultMap.put(HhConstants.RESULT, status);
			    resultMap.put(HhConstants.MSG, msg);
			    if(resultResultMap.get("NUMBER_INFO")!=null){
			    	Map<String,Object> NUMBER_INFO = (Map<String,Object>)resultResultMap.get("NUMBER_INFO");
			    	resultMap.put("ACT_AMOUNT", NUMBER_INFO.get("ACT_AMOUNT"));
			    	resultMap.put("PAY_STATUS", NUMBER_INFO.get("PAY_STATUS"));
			    }
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
		    }
		    log.info("*************************APP校检ICCID*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
}
