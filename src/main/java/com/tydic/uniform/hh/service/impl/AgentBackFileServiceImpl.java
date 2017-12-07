/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author ghp
 * @date: 2015年11月28日 下午7:03:26
 * @Title: AgentBackFileServiceImpl.java
 * @Package com.tydic.uniform.hh.service.impl
 * @Description: TODO
 */
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
import com.tydic.uniform.hh.service.interfaces.AgentBackFileServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentBackFileVo;

import net.sf.json.JSONObject;

/**
 * <p></p>
 * @author ghp 2015年11月28日 下午7:03:26
 * @ClassName AgentBackFileServiceImpl
 * @Description TODO  代理商返档实体
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月28日
 * @modify by reason:{方法名}:{原因}
 */
@Service("AgentBackFileServiceServ")
public class AgentBackFileServiceImpl extends AbstractService implements AgentBackFileServiceServ{
	private static Logger log = Logger.getLogger(AgentBackFileServiceImpl.class);
	/**
	 * @author ghp 2015年11月28日 下午7:04:33
	 * @Method: agentBackfile 
	 * @Description: TODO
	 * @param agentbackfilevo
	 * @return 
	 * @see com.tydic.uniform.hh.service.interfaces.AgentBackFileServiceServ#agentBackfile(com.tydic.uniform.hh.vo.rep.AgentBackFileVo) 
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> agentBackfile(AgentBackFileVo agentbackfilevo) {
		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP代理商返档接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentbackfilevo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(agentbackfilevo).toString());
		/*
		 * APP入参解析
		 */
		//开始判断是校检 还是反档
		  String operate_type = agentbackfilevo.getOperate_type();
		  int optype = Integer.parseInt(operate_type);
		if (optype==1) {
			//则是校检
			String cust_id = agentbackfilevo.getCust_id();
			if (null==cust_id||""==cust_id) {
				resultMap.put(HhConstants.MESSAGE, "CUST_ID为空");//失败描述
			}
			String mobile_170 = agentbackfilevo.getMobile_170();
			if (null==mobile_170||""==mobile_170) {
				resultMap.put(HhConstants.MESSAGE, "MOBILE_170为空");//失败描述
			}
			String systemuserid = agentbackfilevo.getSystemuserid();
			if (null==systemuserid||""==systemuserid) {
				resultMap.put(HhConstants.MESSAGE, "操作人员ID为空");//失败描述
				return resultMap;
			}
			String app_flag = agentbackfilevo.getApp_flag();
			String iccid = agentbackfilevo.getIccid();
			if (null==iccid||""==iccid) {
				resultMap.put(HhConstants.MESSAGE, "ICCID为空");//失败描述
				return resultMap;
			}
			Map<String,String> pub_rep = new HashMap<String,String>();
			pub_rep.put("TYPE", "CUST_UPDATE");
			Map<String,String> req = new HashMap<String,String>();
			req.put("ICCID", iccid); // 必选
			List<Map<String,String>> result = new ArrayList<Map<String,String>>();
			Map body = new HashMap();
			List<Map> sooList = new ArrayList<Map>();
			sooList.add(0, new HashMap());
			sooList.get(0).put("CUST", req);
			sooList.get(0).put("MSISDN", mobile_170);
			sooList.get(0).put("OPERATE_TYPE", operate_type);
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
			    }else if(HhConstants.ERROR.equals(status)){
			    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
			    }
			    log.info("*************************APP校检ICCID*************************");
				log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			
		}else{
			//反档
			String systemuserid = agentbackfilevo.getSystemuserid();
			if(systemuserid.equals(null)||systemuserid.equals("")){
				systemuserid = "";
			}
			String cust_id = agentbackfilevo.getCust_id();
			String email = agentbackfilevo.getEmail();
			String birthdate = agentbackfilevo.getBirthdate();
			String mobile = agentbackfilevo.getMobile();
			String nick_name = agentbackfilevo.getNick_name();
			String sex = agentbackfilevo.getSex();
			String contact_addr = agentbackfilevo.getContact_addr();
			String certi_addr = agentbackfilevo.getCerti_addr();
			String cust_name = agentbackfilevo.getCust_name();
			String certi_nbr = agentbackfilevo.getCerti_nbr();
			String appendix = agentbackfilevo.getAppendix();
			String mobile_170 = agentbackfilevo.getMobile_170();
			
			String check_type = agentbackfilevo.getCheck_type();
			String app_flag = agentbackfilevo.getApp_flag();
			String iccid = agentbackfilevo.getIccid();
			String OFR_ID = agentbackfilevo.getOffr_id();
			if (OFR_ID==null||OFR_ID=="") {
				OFR_ID="HNDLS001";
			}
			String org_id = agentbackfilevo.getOrg_id();
			if (null==org_id||""==org_id) {
				resultMap.put(HhConstants.MESSAGE, "org_id为空");//失败描述
				return resultMap;
			}
			/*
			 * 
			 * 组装能力平台入参
			 */
			Map pub_rep = new HashMap();
			pub_rep.put("TYPE", "CUST_UPDATE");
			Map req = new HashMap();
			req.put("CHANNEL_CODE", org_id); // 必选
			req.put("EXT_SYSTEM", "10002");// 必选
			req.put("CUST_ID", cust_id);// 必选
			req.put("EMAIL", email);
			req.put("BIRTHDATE", birthdate);
			req.put("MOBILE", mobile);
			req.put("NICK_NAME", nick_name);
			req.put("SEX", sex);
			req.put("CONTACT_ADDR", contact_addr);
			req.put("CERTI_ADDR", certi_addr);
			req.put("CUST_NAME", cust_name);
			req.put("CERTI_NBR", certi_nbr);
			req.put("APPENDIX", appendix);// 必选
			req.put("ICCID", iccid);
			if (Integer.valueOf(check_type)==5) {
				String confidence = agentbackfilevo.getConfidence();
				if (""==confidence||null==confidence) {
					resultMap.put(HhConstants.MESSAGE, "人脸对比失败");//失败描述
				}else {
					req.put("CONFIDENCE", confidence);	
				}
			}
			List<Map> result = new ArrayList<Map>();
			Map body = new HashMap();
			List<Map> sooList = new ArrayList<Map>();
			sooList.add(0, new HashMap());
			sooList.get(0).put("CUST", req);
			sooList.get(0).put("MSISDN", mobile_170);
			sooList.get(0).put("OPERATE_TYPE", operate_type);
			sooList.get(0).put("CHECK_TYPE", check_type);
			sooList.get(0).put("APP_FLAG", app_flag);
			sooList.get(0).put("OFR_ID", OFR_ID);
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
			
			   if(HhConstants.SUCCESS.equals(status)){
				    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
				    resultMap.put(HhConstants.RESULT, status);
				    resultMap.put(HhConstants.MESSAGE, msg);
			    }else if(HhConstants.ERROR.equals(status)){
			    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			    	resultMap.put(HhConstants.RESULT, status);
			    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
			    }
			    log.info("*************************APP代理商返档接口出参*************************");
				log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			
		}
		return resultMap;
		
	}
	
}
