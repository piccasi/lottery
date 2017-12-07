/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author Administrator
 * @date: 2015年10月12日 下午3:05:52
 * @Title: ForgetPasswordServiceImpl.java
 * @Package com.tydic.uniform.hh.service.impl
 * @Description: TODO
 */
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
import com.tydic.uniform.hh.service.interfaces.ForgetPasswordServiceServ;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.vo.rep.ForgetPasswordVo;
import com.tydic.uniform.hh.vo.resp.ForgetPasswordResp;

/**
 * <p>
 * </p>
 * @author yiyaohong 2015年10月12日 下午3:05:52
 * @ClassName ForgetPasswordServiceImpl 忘记密码实现类
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月12日
 * @modify by reason:{方法名}:{原因}
 */

@Service("ForgetPasswordServiceServ")
public class ForgetPasswordServiceImpl extends AbstractService implements ForgetPasswordServiceServ {
	
	private static Logger log = Logger.getLogger(ForgetPasswordServiceImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> forgetPasswordService(ForgetPasswordVo forgetpasswordvo) {
		
		log.info("*************************APP忘记密码接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(forgetpasswordvo).toString());
		/**
		 * 按照BOSS文档进行入参
		 * */
		List<Map> soolist = new ArrayList<Map>();
		soolist.add(0, new HashMap());
		
		//对密码敏感字段进行加密
		String newpwd = MD5Utils.MD5(forgetpasswordvo.getNew_pwd());
		
		Map cust = new HashMap();
		cust.put(HhConstants.CHANNEL_CODE, "10002");
		cust.put(HhConstants.EXT_SYSTEM,"102");
		cust.put(HhConstants.CUST_ID, forgetpasswordvo.getCust_id());
		cust.put(HhConstants.NEW_PWD, newpwd);
		// 密码类型在前台写定 1，登陆密码，2，服务密码
		cust.put(HhConstants.PWD_TYPE, forgetpasswordvo.getPwd_type());
		// 密码更新类型 1:修改 2:重置 3:找回密码
		cust.put(HhConstants.RESET_TYPE, "3");
		
//		Map pub_req = new HashMap();
//		pub_req.put(HhConstants.TYPE, "RESET_PWD");
		
		soolist.get(0).put(HhConstants.CUST, cust);
//		soolist.get(0).put(HhConstants.PUB_REQ, pub_req);
		
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
		
		List resultsooList = (List<Map<String, Object>>)bean.getBody().get(0).get(HhConstants.SOO);
		
		Map resultsooMap = (Map)resultsooList.get(0);
		
		Map resp = (Map)resultsooMap.get(HhConstants.RESP);
		Map pub_res = (Map)resultsooMap.get(HhConstants.PUB_RES);
		
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
		return resultMap;
	}
}
