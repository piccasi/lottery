package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.util.Base64;
import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.FirstInstallLiftServ;
import com.tydic.uniform.hh.util.ValidateCodeUtil;
import com.tydic.uniform.hh.vo.rep.FirstInstallLiftSmsVo;
import com.tydic.uniform.hh.vo.rep.FirstInstallLiftVo;
import com.tydic.uniform.hh.vo.rep.SMSInfoVo;

import net.sf.json.JSONObject;

/**
 * 
 * <p>
 * </p>
 * 
 * @author panxinxing 2015年12月16日 上午11:36:17
 * @ClassName FirstInstallLiftServiceImpl
 * @Description 首次安装赠送流量
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月16日
 * @modify by reason:{方法名}:{原因}
 */
@Service("FirstInstallLiftServ")
public class FirstInstallLiftServiceImpl extends AbstractService implements FirstInstallLiftServ {
	private static Logger log = Logger.getLogger(FirstInstallLiftServiceImpl.class);
	
	@Override
	public Map<String, Object> getFirstInstallLiftSmsServ(FirstInstallLiftSmsVo firstInstallLiftSmsVo){
		//首先验证传进来的号码是否为170号码，若是则发送验证码，不是则返回
		log.info("*************************APP客户资料查询接口（领取首次安装大礼包并发送验证短信）入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(firstInstallLiftSmsVo).toString());
		
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());
		Map<String, Object> req = new HashMap<String, Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		if(firstInstallLiftSmsVo.getCustId()!=""&&firstInstallLiftSmsVo.getCustId()!=null){
			req .put(HhConstants.CUST_ID, firstInstallLiftSmsVo.getCustId());
		}
		sooList.get(0).put(HhConstants.CUST_QRY,req);
		Map<String, Object> pub_req = new HashMap<String, Object>();
		pub_req.put(HhConstants.TYPE, "CUST_QUERY");
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS客户资料查询接口（领取首次安装大礼包并发送验证短信）入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001004", ResponseBean.class);
		log.info("*************************BOSS客户资料查询接口（领取首次安装大礼包并发送验证短信）出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSooList.get(0);
		Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		Map<String, Object> resultMap = new HashMap<String, Object>();
	    if(HhConstants.SUCCESS.equals(status)){
	    	List custlist=(List) resultsooMap.get("CUST");
	    	Map<String, Object> custmap = (Map) custlist.get(0);
	    	String mobile_170 = custmap.get(HhConstants.MOBILE_170).toString();
	    	if(mobile_170!=null&&!mobile_170.equals("")){
	    		//若为海航170号码，发送验证码
	    		
	    		// 生成验证码
	    		String validateCode = ValidateCodeUtil.create(6);
	    		ValidateCodeUtil.put(mobile_170, validateCode);
	    		SMSInfoVo smsinfovo = new SMSInfoVo();
	    		String content = "尊敬的客户，您好！感谢您参与“首次安装海航通信APP赠送100M流量活动”，验证码："+validateCode;
	    		smsinfovo.setContent(content);
	    		smsinfovo.setMobile(mobile_170);
	    		log.info("===========resp 发送手机号：" + mobile_170 + "短信内容 ：" + content + "===========");

	    		// 发送短信
	    		//正式环境启用如下代码   BEGIN
	    		SMSSendServiceImpl smssendserviceimpl = new SMSSendServiceImpl();
	    		Map<String, Object> map = smssendserviceimpl.sendSMS(smsinfovo);
	    		//正式环境启用以上代码  END
	    		
	    		//以下代码为屏蔽发送短信接口验证使用，正式环境请删除
	    		//TestCode BEGIN
	    		/*Map<String, Object> map = new HashMap<String, Object>();
	    		map.put(HhConstants.CODE, "0000");
	    		map.put(HhConstants.SUCCESSCODE, HhConstants.MESSAGE);*/
	    		//TestCode END
	    		 
	    		String smsStatus = (String) map.get(HhConstants.CODE);
	    		if (HhConstants.SUCCESSCODE.equals(smsStatus)) {
	    			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
	    			resultMap.put("smsCode", DesEncryptUtil.encrypt(validateCode));// 成功
	    			resultMap.put(HhConstants.MESSAGE, map.get(HhConstants.MESSAGE));
	    			log.info("===========resp 短信发送 成功===========");
	    		} else if (HhConstants.ERRORCODE.equals(smsStatus)) {
	    			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
	    			resultMap.put(HhConstants.MESSAGE, map.get(HhConstants.MESSAGE));
	    			log.info("===========resp 短信发送 失败===========");
	    			return resultMap;
	    		}
	    	}else{
	    		resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, "您还不是170用户,不能参与活动");//失败描述
	    	}
	    }else{
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			if(result!=null&&!result.equals(""))
				resultMap.put(HhConstants.MESSAGE,result);//失败描述
	    }
	    return resultMap;
	}
	
	@Override
	public Map<String, Object> validateFirstInstallLiftSmsServ(FirstInstallLiftVo firstInstallLiftVo){
		log.info("*************************APP领取首次安装大礼包接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(firstInstallLiftVo).toString());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//判断是否获取MAC地址和IMEI，若为11则证明未获取到
		if(firstInstallLiftVo.getMacAddress().equals("11") || firstInstallLiftVo.getImei().equals("11")){
			log.info("*************************APP“领取首次安装大礼包”获取机器码失败*************************");
			resultMap.put("result", "2");
    		resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			return resultMap;
		}
		//校验短信验证码
		String phone = firstInstallLiftVo.getPhoneNum();
		String sms = firstInstallLiftVo.getSmsCode();
		boolean flag = ValidateCodeUtil.isSuccess(phone, sms);
		String macAddress = new String(Base64.decode(firstInstallLiftVo.getMacAddress()));
		String imei = new String(Base64.decode(firstInstallLiftVo.getImei()));
		String custId = firstInstallLiftVo.getCustId();
		if(flag){
			//与ECP后台交互
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("mobile", phone);
			param.put("custId", custId);
			param.put("macAddress", macAddress);
			param.put("imei", imei);
			
			Map<String, Object> body= new HashMap<String, Object>();
			body.put(HhConstants.PARAM, param);

			log.info("*************************BOSS领取首次安装大礼包接口入参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(body).toString());		
			setIntefaceType(IntefaceType.ESHOP);
			ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1002021", ResponseBean.class);
			log.info("*************************BOSS领取首次安装大礼包到接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
			
			
			String status = (String)bean.getBody().get(0).get(HhConstants.ESHOPRESULT);
			String rspErrCode = (String)bean.getBody().get(0).get(HhConstants.RSPERRCODE);
			//Map rspDataMap = (Map) bean.getBody().get(0).get(HhConstants.ESHOPREPDATA);
		
			if(HhConstants.SUCCESS.equals(status)){    	    	
				//领取成功
				SMSInfoVo smsinfovo = new SMSInfoVo();
	    		String content = "尊敬的用户，您好！您所参加登录海航通信移动营业厅送100M流量的活动奖励已经下发至账户中。您可使用客户端，随时查询账户余额，赠送流量2年有效，不可转赠。";
	    		smsinfovo.setContent(content);
	    		smsinfovo.setMobile(phone);

	    		// 发送短信
	    		//正式环境启用如下代码   BEGIN
	    		SMSSendServiceImpl smssendserviceimpl = new SMSSendServiceImpl();
	    		Map<String, Object> map = smssendserviceimpl.sendSMS(smsinfovo);
	    		//正式环境启用以上代码  END
	    		
	    		
	    		//以下代码为屏蔽发送短信接口验证使用，正式环境请删除
	    		//TestCode BEGIN
	    		/*Map<String, Object> map = new HashMap<String, Object>();
	    		map.put(HhConstants.CODE, "0000");
	    		map.put(HhConstants.SUCCESSCODE, HhConstants.MESSAGE);*/
	    		//TestCode END
	    		
	    		
	    		String smsStatus = (String) map.get(HhConstants.CODE);
	    		if (HhConstants.SUCCESSCODE.equals(smsStatus)) {
	    			log.info("===========resp 短信发送 成功===========");
	    		} else if (HhConstants.ERRORCODE.equals(smsStatus)) {
	    			log.info("===========resp 短信发送 失败===========");
	    		}
	    		resultMap.put("result", "0");
				resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功    	
		    }else if(HhConstants.ERROR.equals(status)){
		    	//已领取
		    	if(rspErrCode.equals("7000")){
		    		resultMap.put("result", "1");
		    		resultMap.put("msg", (String)bean.getBody().get(0).get(HhConstants.RSPMSG));
		    		resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
		    	}else{
		    		resultMap.put(HhConstants.MESSAGE, (String)bean.getBody().get(0).get(HhConstants.RSPMSG));
					resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败
		    	}
		    } 
		} else {
			resultMap.put(HhConstants.MESSAGE, "您输入的验证码错误，请重新输入!");
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败
		}
		return resultMap;
	}
}
