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
import com.tydic.uniform.hh.service.interfaces.RegisterServiceServ;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.vo.rep.RegisterVo;

/**
 * <p>
 * </p>
 * @author yiyaohong 2015年10月8日 下午3:46:05
 * @ClassName RegisterServiceImpl 用户注册服务的实现
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
@Service("RegisterServiceServ")
public class RegisterServiceImpl extends AbstractService implements RegisterServiceServ {
	
	private static Logger log = Logger.getLogger(RegisterServiceImpl.class);
	
	@Override
	public Map<String, Object> register(RegisterVo registervo) {
		log.info("*************************APP用户注册接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(registervo).toString());
		
		List<Map> soolist = new ArrayList<Map>();
		soolist.add(0, new HashMap());
		
		// 对密码进行加密
		
		String pwd = MD5Utils.MD5(registervo.getPwd());
		Map req = new HashMap();
		req.put(HhConstants.CHANNEL_CODE, "01");
		req.put(HhConstants.EXT_SYSTEM, "102");
		// 根据页面的的需求是否提供 cust_name
		// req.put(HhConstants.CUST_NAME, registervo.getCust_name());
		req.put(HhConstants.SERVICE_NBR, registervo.getService_nbr());
		req.put(HhConstants.PWD, pwd);
		
		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "SAVE_CUST_REQ");
		
		soolist.get(0).put(HhConstants.CUST, req);
		soolist.get(0).put(HhConstants.PUB_REQ, pub_req);
		/*
		 * String ll=null; try { ll=JSON.toJSONString(soolist); } catch (Exception e) {
		 *//** TODO Auto-generated catch block */
		/*
		 * e.printStackTrace(); }
		 */
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", soolist);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS用户注册接口入参*************************");
		log.info("BOSS response str:" + soolist.toString());
		
		/*
		 * SoapUtil soapUilt=new SoapUtil(); String rspjson=soapUilt.SoapWebService("/ServiceBus/custView/cust/custReg002", "/esb/Register", "108", ll);
		 */
		
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean)this.appApiInvoke(body, "SC1001002", ResponseBean.class);
		
		log.info("*************************BOSS用户注册接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// Map resultBODYMap = (Map)resultJson.get(HhConstants.BODY);
		List resultSooList = (List<Map<String, Object>>)bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map)resultSooList.get(0);
		String custid = (String)resultsooMap.get(HhConstants.CUST_ID);
		Map resultpub_res = (Map)resultsooMap.get(HhConstants.PUB_RES);
		Map resultresp = (Map)resultsooMap.get(HhConstants.RESP);
		String resulttype = (String)resultpub_res.get(HhConstants.TYPE);
		String status = (String)resultresp.get(HhConstants.RESULT);
		
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.TYPE, resulttype);
			resultMap.put(HhConstants.CUST_ID, custid);
			//过滤重复注册问题{"RESP":{"MSG":"账号(手机号码) 已被注册。","RESULT":"0","CODE":""}
			if(resultresp.get(HhConstants.MSG)!=null&&resultresp.get(HhConstants.MSG).toString().contains("已被注册")){
				resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
				if(resultresp.get(HhConstants.MSG)!=null&&!resultresp.get(HhConstants.MSG).toString().equals("")){
					resultMap.put(HhConstants.MESSAGE, resultresp.get(HhConstants.MSG).toString());// 失败描述
				}
			}
		} else
			if (HhConstants.ERROR.equals(status)) {
				resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.REG_ERRORMESSAGE);// 失败描述
			}
		
		log.info("*************************APP用户注册接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
}
