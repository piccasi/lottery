package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONReader;
import com.tydic.uniform.common.constant.Constants;
import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.LogonInServiceServ;
import com.tydic.uniform.hh.service.interfaces.UsermsgServiceServ;
import com.tydic.uniform.hh.util.LoginUtil;
import com.tydic.uniform.hh.util.MD5Encrypt2;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.vo.rep.LogonInVo;
import com.tydic.uniform.hh.vo.rep.UsermsgVo;
import com.tydic.uniform.hh.vo.resp.LogonInResp;

import net.sf.json.JSONObject;

/**
 * <p>
 * </p>
 * 
 * @author yiyaohong 2015年10月8日 下午3:47:09
 * @ClassName LogonInServiceImpl 用户登录服务的实现
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
@Service("LogonInServiceServ")
public class LogonInServiceImpl extends AbstractService implements LogonInServiceServ {
	@Autowired
	private UsermsgServiceServ usermsgserviceserv;
	
	private static Logger log = Logger.getLogger(LogonInServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public String Logonin(LogonInVo logoninvo) {

		log.info("*************************APP登录接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(logoninvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		String password;

		List<Map> sooList = new ArrayList<Map>();

		sooList.add(0, new HashMap());
		Map cust_login = new HashMap();

		logoninvo.setLogin_type("50");//暂时写死50
		password = MD5Encrypt2.md5Encode(logoninvo.getPwd());// 代理商登录
		/*if (logoninvo.getLogin_type().equals("50")) {
			password = MD5Encrypt2.md5Encode(logoninvo.getPwd());// 代理商登录

		} else {
			password = MD5Utils.MD5(logoninvo.getPwd()); // 使用ＭＤ对密码进行加密// 客户登陆
		}*/

		cust_login.put(HhConstants.CHANNEL_CODE, "01");
		cust_login.put(HhConstants.EXT_SYSTEM, "10002");
		cust_login.put(HhConstants.LOGIN_NBR, logoninvo.getLogin_nbr());
		cust_login.put(HhConstants.LOGIN_TYPE, logoninvo.getLogin_type());
		cust_login.put(HhConstants.PWD, password);

		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "LOGIN_CHECK");

		sooList.get(0).put(HhConstants.CUST_LOGIN, cust_login);
		sooList.get(0).put(HhConstants.PUB_REQ, pub_req);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS用户登录接口入参*************************");
		log.info("BOSS resquest str:" + JSONObject.fromObject(body).toString());


		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001001", ResponseBean.class);

		// JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS用户登录接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());

		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List resultSoolist = (List<Map<String, Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSoolist.get(0);
		Map pubreq = (Map) resultsooMap.get(HhConstants.PUB_REQ);
		String type = (String) (pubreq.containsKey(HhConstants.TYPE) ? pubreq.get(HhConstants.TYPE) : "");

		Map resp = (Map) resultsooMap.get(HhConstants.RESP);
/*		if (resp.get("RESULT").equals(HhConstants.ERROR)) {			
			if (resp.get("MSG").equals("无此账号")) {
				resp.put("MSG", "密码错误，请重新输入");				
				return JsonResponse.toErrorResult(CODE.ACCOUNT_NOT_EXSIT, null);
			}
			
			return JsonResponse.toErrorResult(CODE.UNKNOW, null);
		}*/
		
		String status = (String) resp.get(HhConstants.RESULT);

		//LogonInResp logonInResp = new LogonInResp();
		if (HhConstants.SUCCESS.equals(status)) {
			
			if(null != resp.get("MSG") && ((String)resp.get("MSG")).contains("非代理商")){
				return JsonResponse.toErrorResult(CODE.NOT_AGENT_ACCOUNT, null);
			}
			
			Map cust = (Map) resultsooMap.get(HhConstants.CUST);
			String custid = "" + cust.get(HhConstants.CUST_ID);
			String custname = (String) cust.get(HhConstants.CUST_NAME);
			//String logonnbrtype = (String) cust.get(HhConstants.LOGON_NBR_TYPE);
			String org_id = ""+ cust.get(HhConstants.ORG_ID);
			resultMap.put(HhConstants.LOGIN_NBR, logoninvo.getLogin_nbr());
			resultMap.put(HhConstants.CUST_ID, custid);
			resultMap.put(HhConstants.ORG_ID, org_id);
			resultMap.put(HhConstants.CUST_NAME, custname);
			resultMap.put(HhConstants.LOGON_NBR_TYPE, logoninvo.getLogin_type());
			resultMap.put(HhConstants.ORG_CODE, cust.get("ORG_CODE"));
			//resultMap.put(HhConstants.TYPE, type);
			//resultMap.put(HhConstants.RESULT, status);
			//resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);
			if (logoninvo.getLogin_type().equals("50")) {
				if (cust.containsKey("ORG_CODE")) {
					if (null == cust.get("ORG_CODE")) {
						//resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
						//resultMap.put(HhConstants.MESSAGE, "ORG_CODE为空");// 失败描述
						
						return JsonResponse.toErrorResult(CODE.AGENT_CODE_ERROR, null);
						
					} /*else {
						resultMap.put("ORG_CODE", cust.get("ORG_CODE"));
					}*/
				} else {
					//resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
					//resultMap.put(HhConstants.MESSAGE, "非代理商工号，请验证");// 失败描述
					return JsonResponse.toErrorResult(CODE.NOT_AGENT_ACCOUNT, null);
				}
			}

		} else {
			/*resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
			if (resp.get(HhConstants.MSG) != null && !resp.get(HhConstants.MSG).toString().equals("")) {
				resultMap.put(HhConstants.MESSAGE, resp.get(HhConstants.MSG).toString());// 失败描述
			}*/
			
			if (resp.get("MSG").equals("无此账号")) {
				resp.put("MSG", "密码错误，请重新输入");				
				return JsonResponse.toErrorResult(CODE.ACCOUNT_NOT_EXSIT, null);
			}
			
			if("密码不正确".equals((String)resp.get("MSG"))){
				return JsonResponse.toErrorResult(CODE.PWD_ERROR, null);
			}
			
			if(((String)resp.get("MSG")).contains("非代理商")){
				return JsonResponse.toErrorResult(CODE.NOT_AGENT_ACCOUNT, null);
			}
			
			if(!"".equals((String)resp.get("MSG"))){
				return JsonResponse.toErrorResult(CODE.PWD_ERROR, null);
			}
			
			return JsonResponse.toErrorResult(CODE.UNKNOW, null);
			
			//return JsonResponse.toErrorResult(CODE.NOT_AGENT_ACCOUNT, null);
		}
		
		//String loginInfo = JSONObject.fromObject(resultMap).toString();
		
		
		String token = LoginUtil.logon(logoninvo.getLogin_nbr(), resultMap);
		resultMap.put("token", token);
		// resultMap.put("logonInResp",logonInResp);
		log.info("*************************APP登录接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		//resultMap.put("token", token);
		return JsonResponse.toSuccessResult(resultMap);

	}

	@Deprecated
	@SuppressWarnings("unchecked")
	public Map<String, Object> Logonin_bak(LogonInVo logoninvo) {

		log.info("*************************APP登录接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(logoninvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		String password;

		List<Map> sooList = new ArrayList<Map>();

		sooList.add(0, new HashMap());
		Map cust_login = new HashMap();

		if (logoninvo.getLogin_type().equals("50")) {
			password = MD5Encrypt2.md5Encode(logoninvo.getPwd());// 代理商登录

		} else {
			password = MD5Utils.MD5(logoninvo.getPwd()); // 使用ＭＤ对密码进行加密// 客户登陆
		}

		cust_login.put(HhConstants.CHANNEL_CODE, "01");
		cust_login.put(HhConstants.EXT_SYSTEM, "10002");
		cust_login.put(HhConstants.LOGIN_NBR, logoninvo.getLogin_nbr());
		cust_login.put(HhConstants.LOGIN_TYPE, logoninvo.getLogin_type());
		cust_login.put(HhConstants.PWD, password);

		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "LOGIN_CHECK");

		sooList.get(0).put(HhConstants.CUST_LOGIN, cust_login);
		sooList.get(0).put(HhConstants.PUB_REQ, pub_req);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		/*
		 * String ll = null; try { ll = JSON.toJSONString(sooList); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS用户登录接口入参*************************");
		log.info("BOSS resquest str:" + JSONObject.fromObject(body).toString());

		/*
		 * SoapUtil soapUilt = new SoapUtil(); String rspJson =
		 * soapUilt.SoapWebService("/ServiceBus/custView/cust/login001",
		 * "/esb/LogonIN", "108", ll);
		 */

		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001001", ResponseBean.class);

		// JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS用户登录接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());

		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List resultSoolist = (List<Map<String, Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSoolist.get(0);
		Map pubreq = (Map) resultsooMap.get(HhConstants.PUB_REQ);
		String type = (String) (pubreq.containsKey(HhConstants.TYPE) ? pubreq.get(HhConstants.TYPE) : "");

		Map resp = (Map) resultsooMap.get(HhConstants.RESP);
		if (resp.get("RESULT").equals("1")) {
			if (resp.get("MSG").equals("无此账号")) {
				resp.put("MSG", "密码错误，请重新输入");
			}
		}
		String status = (String) resp.get(HhConstants.RESULT);

		LogonInResp logonInResp = new LogonInResp();
		if (HhConstants.SUCCESS.equals(status)) {
			Map cust = (Map) resultsooMap.get(HhConstants.CUST);
			String custid = "" + cust.get(HhConstants.CUST_ID);
			String custname = (String) cust.get(HhConstants.CUST_NAME);
			String logonnbrtype = (String) cust.get(HhConstants.LOGON_NBR_TYPE);
			String org_id = ""+ cust.get(HhConstants.ORG_ID);
			resultMap.put(HhConstants.CUST_ID, custid);
			resultMap.put(HhConstants.ORG_ID, org_id);
			resultMap.put(HhConstants.CUST_NAME, custname);
			resultMap.put(HhConstants.LOGON_NBR_TYPE, logonnbrtype);
			resultMap.put(HhConstants.TYPE, type);
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);
			if (logoninvo.getLogin_type().equals("50")) {
				if (cust.containsKey("ORG_CODE")) {
					if (null == cust.get("ORG_CODE")) {
						resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
						resultMap.put(HhConstants.MESSAGE, "ORG_CODE为空");// 失败描述
					} else {
						resultMap.put("ORG_CODE", cust.get("ORG_CODE"));
					}
				} else {
					resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
					resultMap.put(HhConstants.MESSAGE, "非代理商工号，请验证");// 失败描述
				}
			}

		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
			if (resp.get(HhConstants.MSG) != null && !resp.get(HhConstants.MSG).toString().equals("")) {
				resultMap.put(HhConstants.MESSAGE, resp.get(HhConstants.MSG).toString());// 失败描述
			}
		}
		
		// resultMap.put("logonInResp",logonInResp);
		log.info("*************************APP登录接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;

	}

	
	@SuppressWarnings("unchecked")
	@Override
	public String login(LogonInVo logoninvo, HttpServletRequest request) {

		log.info("*************************APP登录接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(logoninvo).toString());
		
		/**
		 * 验证短信验证码
		 */
		
		{
			//缺失
		}
		

		
		/*
		 * 按照BOSS文档设置接口入参
		 */
		String password;

		logoninvo.setLogin_type("50");
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();

		sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> cust_login = new HashMap<String,Object>();

		password = MD5Encrypt2.md5Encode(logoninvo.getPwd());// 代理商登录
		
		cust_login.put(HhConstants.CHANNEL_CODE, "01");
		cust_login.put(HhConstants.EXT_SYSTEM, "10002");
		cust_login.put(HhConstants.LOGIN_NBR, logoninvo.getLogin_nbr());
		cust_login.put(HhConstants.LOGIN_TYPE, logoninvo.getLogin_type());
		cust_login.put(HhConstants.PWD, password);

		Map<String,Object> pub_req = new HashMap<String,Object>();
		pub_req.put(HhConstants.TYPE, "LOGIN_CHECK");

		sooList.get(0).put(HhConstants.CUST_LOGIN, cust_login);
		sooList.get(0).put(HhConstants.PUB_REQ, pub_req);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS用户登录接口入参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(body).toString());

		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001001", ResponseBean.class);

		log.info("*************************BOSS用户登录接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());

		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultSoolist = (List<Map<String, Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSoolist.get(0);
		Map<String, Object> pubreq = (Map<String, Object>) resultsooMap.get(HhConstants.PUB_REQ);
		String type = (String) (pubreq.containsKey(HhConstants.TYPE) ? pubreq.get(HhConstants.TYPE) : "");

		Map<String, Object> resp = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		if (resp.get("RESULT").equals("1")) {
			if (resp.get("MSG").equals("无此账号")) {
				resp.put("MSG", "密码错误，请重新输入");
			}
		}
		String status = (String) resp.get(HhConstants.RESULT);
		
		String resultString = "";
		if (HhConstants.SUCCESS.equals(status)) {
			
			if(null != resp.get("MSG") && ((String)resp.get("MSG")).contains("非代理商")){
				return JsonResponse.toErrorResult(CODE.NOT_AGENT_ACCOUNT, null);
			}
			
			Map<String, Object> cust = (Map<String, Object>) resultsooMap.get(HhConstants.CUST);
			String custid = "" + cust.get(HhConstants.CUST_ID);
			String custname = (String) cust.get(HhConstants.CUST_NAME);
			String logonnbrtype = (String) cust.get(HhConstants.LOGON_NBR_TYPE);
			String org_id = ""+ cust.get(HhConstants.ORG_ID);
			resultMap.put(HhConstants.CUST_ID, custid);
			resultMap.put(HhConstants.ORG_ID, org_id);
			resultMap.put(HhConstants.CUST_NAME, custname);
			resultMap.put(HhConstants.LOGON_NBR_TYPE, logonnbrtype);
			resultMap.put(HhConstants.TYPE, type);
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);
			if (cust.containsKey("ORG_CODE")) {
				if (null == cust.get("ORG_CODE")) {
					resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "ORG_CODE为空");
				} else {
					resultMap.put("ORG_CODE", cust.get("ORG_CODE"));
					resultString = JsonResponse.toSuccessResult(resultMap);
					request.getSession().setAttribute(Constants.USER_SESSION_KEY , resultMap);
				}
			} else {
				resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "非代理商工号，请验证");
			}
			
		} else if (HhConstants.ERROR.equals(status)) {
			String msg = HhConstants.ERRORMESSAGE;
			
			if(null != resp.get("MSG") && ((String)resp.get("MSG")).contains("非代理商")){
				return JsonResponse.toErrorResult(CODE.NOT_AGENT_ACCOUNT, null);
			}
			
			if (resp.get(HhConstants.MSG) != null && !resp.get(HhConstants.MSG).toString().equals("")) {
				msg = resp.get(HhConstants.MSG).toString();
			}
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, msg);
		}
		// resultMap.put("logonInResp",logonInResp);
		log.info("*************************APP登录接口出参*************************");
		log.info("APP response str:" + DesEncryptUtil.decrypt(resultString));
		return resultString;

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String loginPwdBack(LogonInVo logoninvo) {

		log.info("*************************APP登录接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(logoninvo).toString());
		
		/**
		 * 验证短信验证码
		 */
		
		{
			//缺失
		}
		
		/**
		 * 获取CUST_ID
		 */
		String custId = "";
		{
			UsermsgVo usermsgvo = new UsermsgVo();
			usermsgvo.setQry_number(logoninvo.getLogin_nbr());
			Map<String, Object> result = usermsgserviceserv.getUsermsgByCustid(usermsgvo);
		}
		
		/**
		 * 按照BOSS文档进行入参
		 * */
		List<Map<String,Object>> soolist = new ArrayList<Map<String,Object>>();
		soolist.add(0, new HashMap<String,Object>());
		
		//对密码敏感字段进行加密
		String newpwd = MD5Utils.MD5(logoninvo.getPwd());
		
		Map<String,Object> cust = new HashMap<String,Object>();
		cust.put(HhConstants.CHANNEL_CODE, "10002");
		cust.put(HhConstants.EXT_SYSTEM,"102");
		cust.put(HhConstants.CUST_ID, custId);
		cust.put(HhConstants.NEW_PWD, newpwd);
		// 密码类型在前台写定 1，登陆密码，2，服务密码
		cust.put(HhConstants.PWD_TYPE, "1");
		// 密码更新类型 1:修改 2:重置 3:找回密码
		cust.put(HhConstants.RESET_TYPE, "3");
		
		soolist.get(0).put(HhConstants.CUST, cust);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", soolist);
		
		/**
		 * 请求接口
		 * */
		log.info("*************************BOSS密码重置/修改接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean)this.appApiInvoke(body, "SC1001003", ResponseBean.class);
		
		log.info("*************************BOSS密码重置/修改接口出参**************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/**
		 * 解析接口返参
		 * */
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> resultsooList = (List<Map<String, Object>>)bean.getBody().get(0).get(HhConstants.SOO);
		
		Map<String,Object> resultsooMap = (Map<String,Object>)resultsooList.get(0);
		
		Map<String,Object> resp = (Map<String,Object>)resultsooMap.get(HhConstants.RESP);
		Map<String,Object> pub_res = (Map<String,Object>)resultsooMap.get(HhConstants.PUB_RES);
		
		String msg = (String)resp.get(HhConstants.MSG);
		String stauts = (String)resp.get(HhConstants.RESULT);
		String type = (String)pub_res.get(HhConstants.TYPE);
		
		if (HhConstants.SUCCESS.equals(stauts)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.MSG, msg);// 成功
			resultMap.put(HhConstants.RESULT, stauts);// 成功
			resultMap.put(HhConstants.TYPE, type);// 成功
			
		} else
			if (HhConstants.ERROR.equals(stauts)) {
				resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
				
			}
		log.info("*************************APP忘记密码接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return null;

	}

	@Override
	public String Logout(String token, LogonInVo logoninvo) {
		boolean logout = LoginUtil.logout(logoninvo.getLogin_nbr(), token);
		log.info("token:" + token + ", log_nur:" + logoninvo.getLogin_nbr() + ",退出:" + logout);
		return JsonResponse.toSuccessResult(null);
	}
	
}
