package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.PasswordModifyServiceServ;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.vo.rep.PasswordModifyVo;

/**
 * <p></p>
 * @author yiyaohong 2015年10月8日 下午3:30:15
 * @ClassName PasswordModifyServiceImpl 密码修改
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
@Service("PasswordModifyServiceServ")
public class PasswordModifyServiceImpl extends AbstractService implements PasswordModifyServiceServ {
	
	private static Logger log = Logger.getLogger(PasswordModifyServiceImpl.class);
	
	@Override
	public Map<String, Object> passwordModify(PasswordModifyVo passwordmodifyVo) {
		log.info("*************************APP密码重置/修改接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(passwordmodifyVo).toString());
		
		List<Map> sooList=new ArrayList<Map>();
		sooList.add(0,new HashMap());
		
		//DesEncrypt解密
		String oldpwd = DesEncryptUtil.decrypt(passwordmodifyVo.getOld_pwd());
		String newpwd = DesEncryptUtil.decrypt(passwordmodifyVo.getNew_pwd());
		/**
		 * 密码敏感字段进行加密
		 * */
		oldpwd = MD5Utils.MD5(oldpwd);
		newpwd = MD5Utils.MD5(newpwd);
		
		Map<String, Object> cust=new HashMap<String, Object>();
		cust.put(HhConstants.CHANNEL_CODE,"10002");
		cust.put(HhConstants.EXT_SYSTEM,"102");
		cust.put(HhConstants.CUST_ID, passwordmodifyVo.getCust_id());
		cust.put(HhConstants.OLD_PWD, oldpwd);
		cust.put(HhConstants.NEW_PWD, newpwd);
		//默认的为登录密码
		cust.put(HhConstants.PWD_TYPE, passwordmodifyVo.getPwd_type());
		//从页面得到重置类型
		cust.put(HhConstants.RESET_TYPE, passwordmodifyVo.getReset_type());
		
		Map<String,Object> pub_req=new HashMap<String,Object>();
		pub_req.put(HhConstants.TYPE, "RESET_PWD");
		
		sooList.get(0).put(HhConstants.CUST, cust);
//		sooList.get(0).put(HhConstants.PUB_REQ, pub_req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
	
	/*	String ll=null;
		try {
	        ll=JSON.toJSONString(sooList);
        } catch (Exception e) {
	        *//** TODO Auto-generated catch block *//*
	        e.printStackTrace();
        }*/
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS密码重置/修改接口入参*************************");
		log.info("BOSS response str:" + body.toString());
	/*	SoapUtil soapUilt=new SoapUtil();
		String rspJson=soapUilt.SoapWebService("/ServiceBus/custView/cust/custPwd003", "/esb/PasswordModify", "108", ll);*/
		//JSONObject resultJson = JSONObject.fromObject(rspJson);
		
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001003", ResponseBean.class);
		
		
		log.info("*************************BOSS密码重置/修改接口出参**************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/**
		 * 解析接口返参
		 * */
	    Map<String,Object> resultMap=new HashMap<String,Object>();
	 
		List resultsooMap =(List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map pub_res =(Map) resultsooMap.get(0);
		Map resp=(Map)pub_res.get(HhConstants.RESP);
		String status =(String) resp.get(HhConstants.RESULT);
		String msg=(String) resp.get(HhConstants.MSG);
		if(HhConstants.SUCCESS.equals(status)){
		   resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);
		//   resultMap.put(HhConstants.MSG, msg); //便于查看信息
		   resultMap.put(HhConstants.RESULT,status);
		}else if(HhConstants.ERROR.equals(status)){
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);
			resultMap.put(HhConstants.MESSAGE, resp.get(HhConstants.MSG));
		}
		resultMap.put(HhConstants.RESULT, status);
		log.info("*************************APP密码修改或重置接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
}
