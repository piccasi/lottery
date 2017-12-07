package com.tydic.uniform.hh.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.PrivilegeListServiceServ;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.util.ValidateCodeUtil;
import com.tydic.uniform.hh.vo.rep.PrivilegeDeductServiceVo;
import com.tydic.uniform.hh.vo.rep.PrivilegeInfoVo;
import com.tydic.uniform.hh.vo.rep.PrivilegeListVo;
import com.tydic.uniform.hh.vo.rep.PrivilegeQrcodeVo;
import com.tydic.uniform.hh.vo.rep.PrivilegeSmsVo;
import com.tydic.uniform.hh.vo.rep.SMSInfoVo;
import com.tydic.uniform.hh.vo.resp.PrivilegeListResp;

import net.sf.json.JSONObject;

/**
 * 
 * <p>
 * </p>
 * 
 * @author panxinxing 2015年11月16日 上午11:36:17
 * @ClassName OrderListServiceImpl
 * @Description 航空特权查询接口实体
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月16日
 * @modify by reason:{方法名}:{原因}
 */
@Service("PrivilegeListServiceServ")
public class PrivilegeListServiceImpl extends AbstractService implements PrivilegeListServiceServ {
	private static Logger log = Logger.getLogger(PrivilegeListServiceImpl.class);

	@Override
	public Map<String, Object> getPrivilegeList(PrivilegeListVo privilegeListVo) {
		log.info("*************************APP航空特权查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(privilegeListVo).toString());

		/*
		 * 按照BOSS文档设置接口入参
		 */
		Map<String, Object> req = new HashMap<String, Object>();
		String cust_ID = privilegeListVo.getCustid();
		if ((null == cust_ID) || (cust_ID.toString().equals(""))) {
			List<Map<String, Object>> errorResult = new ArrayList<Map<String, Object>>();
			Map<String, Object> errorResultMap = new HashMap<String, Object>();
			Map<String, Object> mapInfo = new HashMap<String, Object>();
			errorResult.add(0, new HashMap<String, Object>());
			errorResult.get(0).put("result", "1");
			errorResult.get(0).put("msg", "请登录");
			PrivilegeListResp privilegeListResp = new PrivilegeListResp();
			errorResultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			mapInfo.put("result", errorResult);
			privilegeListResp.setMapInfo(mapInfo);
			errorResultMap.put("privilegeListResp", privilegeListResp);
			log.info("*************************APP航空特权查询接口出参*************************");
			log.info("APP response str:" + JSONObject.fromObject(errorResultMap).toString());
			return errorResultMap;
		}

		req.put("MemberId", cust_ID);// 会员唯一标识
		req.put("QRY_TYPE", "2"); // 查询类型1：查总额 2：查明细
		req.put("BalType", "0");
		req.put("SystemID", "110");// 接入系统标识

		Map<String, Object> body = new HashMap<String, Object>();
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());
		sooList.get(0).put("BALANCEQRY_REQ", req);
		body.put("SOO", sooList);
		setIntefaceType(IntefaceType.BILL);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS航空特权查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1003029", ResponseBean.class);
		log.info("*************************BOSS航空特权查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());

		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultSooList = (List<Map<String, Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
		PrivilegeListResp privilegeListResp = new PrivilegeListResp();
		String status = (String) resultResultMap.get(HhConstants.PRIVILEGERESULT);

		if (HhConstants.SUCCESS.equals(status)) {
			Map<String, Object> mapInfo = new HashMap<String, Object>();
			temp = (List<Map<String, Object>>) (resultResultMap).get("BalDetail");
			for (Map<String, Object> airMap : temp) {
				if (null != airMap.get("BalanceTypeId")) {
					if (airMap.get("BalanceTypeId").toString().equals("30")
							|| airMap.get("BalanceTypeId").toString().equals("31")
							|| airMap.get("BalanceTypeId").toString().equals("32")) {
						airMap.remove("BalanceName");
						result.add(airMap);
					}
				}
			}
			mapInfo.put("result", result);
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			privilegeListResp.setMapInfo(mapInfo);
		} else if (status == null) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			Map<String, Object> mapInfo = new HashMap<String, Object>();
			mapInfo.put("result", null);
			privilegeListResp.setMapInfo(mapInfo);
		} else {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
		}
		resultMap.put("privilegeListResp", privilegeListResp);
		log.info("*************************APP航空特权查询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

	@Override
	public Map<String, Object> getPrivilegeInfo(PrivilegeInfoVo privilegeInfoVo) {
		log.info("*************************APP航空特权查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(privilegeInfoVo).toString());

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (null == privilegeInfoVo.getInfo() || privilegeInfoVo.getInfo().toString().equals("")) {
			resultMap.put("result", "1");
			resultMap.put("msg", "获取服务信息失败");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功

			log.info("*************************APP航空特权查询接口出参*************************");
			log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
			return resultMap;
		}
		if (null == privilegeInfoVo.getCustid() || "".equals(privilegeInfoVo.getCustid())
				|| null == privilegeInfoVo.getMobile_170() || "".equals(privilegeInfoVo.getMobile_170())) {
			resultMap.put("result", "1");
			resultMap.put("msg", "您的170手机号未激活");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功

			log.info("*************************APP航空特权查询接口出参*************************");
			log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
			return resultMap;
		}
		JSONObject jsonObj = JSONObject.fromObject(privilegeInfoVo.getInfo());
		List<JSONObject> list = new ArrayList<JSONObject>();
		list.add(jsonObj);
		Map<String, Object> infomap = (Map<String, Object>) (list).get(0);
		infomap.put("custId", privilegeInfoVo.getCustid());
		infomap.put("mobile170", privilegeInfoVo.getMobile_170());
		resultMap.put("info", DesEncryptUtil.encrypt(JSON.toJSONString(infomap)));
		resultMap.put("result", "0");
		resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功

		log.info("*************************APP航空特权查询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

	@Override
	public Map<String, Object> getPrivilegeQrcode(PrivilegeQrcodeVo privilegeQrcodeVo) {
		log.info("*************************APP航空特权查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(privilegeQrcodeVo).toString());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		String info = DesEncryptUtil.decrypt(privilegeQrcodeVo.getInfo());

		Map<String, Object> infoMap = JSON.parseObject(info);
		infoMap.put("name", privilegeQrcodeVo.getCustname());

		if (null == privilegeQrcodeVo.getInfo() || privilegeQrcodeVo.getInfo().equals("")) {
			resultMap.put("result", "1");
			resultMap.put("msg", "生成二维码错误");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			return resultMap;
		}
		if ((null == infoMap.get("mobile170")) || infoMap.get("mobile170").toString().equals("")) {
			resultMap.put("result", "1");
			resultMap.put("msg", "无法获取您的170手机号");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			return resultMap;
		}

		String phone = infoMap.get("mobile170").toString();
		Map<String, Object> smsMap = new HashMap<String, Object>();
		smsMap.put("phone", phone);

		// 生成验证码
		String validateCode = ValidateCodeUtil.create(6);
		ValidateCodeUtil.put(phone, validateCode);
		SMSInfoVo smsinfovo = new SMSInfoVo();
		String content = PropertiesUtil.getPropertyValue("PREVILGEMSG");
		content = content.replace("#pwd#", validateCode);
		smsinfovo.setContent(content);
		smsinfovo.setMobile(phone);
		log.info("===========resp 发送手机号：" + phone + "短信内容 ：" + content);

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

		String status = (String) map.get(HhConstants.CODE);
		if (HhConstants.SUCCESSCODE.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.MESSAGE, map.get(HhConstants.MESSAGE));
			log.info("===========resp 短信发送 成功");
		} else if (HhConstants.ERRORCODE.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, map.get(HhConstants.MESSAGE));
			log.info("===========resp 短信发送 失败");
			return resultMap;
		}

		String aid = PropertiesUtil.getPropertyValue("airAcctId");
		infoMap.put("aid", aid);
		String url = PropertiesUtil.getPropertyValue("airUrl");
		String urlParam = "AcctBalanceId=" + infoMap.get("AcctBalanceId") + "&" + "BalanceTypeId="
				+ infoMap.get("BalanceTypeId") + "&" + "ExpiryDate=" + infoMap.get("ExpiryDate") + "#";
		String encryptUrlParam = "name=" + infoMap.get("name") + "&" + "mobile170=" + infoMap.get("mobile170");
		log.info("*************************二维码对应的链接*************************");
		log.info("二维码对应的链接:" + url);
		resultMap.put("reslut", "0");
		resultMap.put("infoMap", infoMap);
		resultMap.put("url", url + "#" + urlParam + DesEncryptUtil.encrypt(encryptUrlParam));// 二维码对应的链接

		log.info("*************************APP航空特权查询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

	@Override
	public Map<String, Object> provPrivilegeSms(PrivilegeSmsVo privilegeSmsVo) {
		log.info("*************************APP航空特权查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(privilegeSmsVo).toString());

		Map<String, Object> resultMap = new HashMap<String, Object>();
		if ((null == privilegeSmsVo.getMobile()) || privilegeSmsVo.getMobile().equals("")) {
			resultMap.put("result", "1");
			resultMap.put("msg", "手机号码不能为空");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			return resultMap;
		}
		if ((null == privilegeSmsVo.getSms()) || privilegeSmsVo.getSms().equals("")) {
			resultMap.put("result", "1");
			resultMap.put("msg", "验证码不能为空");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			return resultMap;
		}
		String phone = privilegeSmsVo.getMobile();
		String sms = privilegeSmsVo.getSms();
		boolean flag = ValidateCodeUtil.isSuccess(phone, sms);
		
		//当用户没有确认使用服务前，短信验证码不失效 
		ValidateCodeUtil.put(phone, sms);

		if (flag) {
			resultMap.put("result", "0");
			resultMap.put("msg", "校验短信验证码成功");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
		} else {
			resultMap.put("result", "1");
			resultMap.put("msg", "校验短信验证码失败");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
		}

		log.info("*************************APP航空特权查询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

	@Override
	public Map<String, Object> deductServiceInfo(PrivilegeDeductServiceVo privilegeDeductServiceVo) {
		log.info("*************************APP航空特权服务使用接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(privilegeDeductServiceVo).toString());
		
		//注销验证码
		@SuppressWarnings("unused")
		boolean flag = ValidateCodeUtil.isSuccess(privilegeDeductServiceVo.getMobile170(), privilegeDeductServiceVo.getSmscode());

		Map<String, Object> type = new HashMap<String, Object>();
		type.put("30", "1");
		type.put("31", "2");
		type.put("32", "3");

		Map<String, Object> PRIVILEGEDTOLIST = new HashMap<String, Object>();
		PRIVILEGEDTOLIST.put("DESC", "测试航空特权");
		PRIVILEGEDTOLIST.put("PRIVILEGETYPE", type.get(privilegeDeductServiceVo.getBalancetypeid()));
		PRIVILEGEDTOLIST.put("ACTION", "2");// 1.增加 2.删除
		PRIVILEGEDTOLIST.put("AMOUNT", "1");

		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmSSsss");
		String REQUEST_ID = formatter.format(new Date()) + "" + String.valueOf(Math.random()).substring(2, 7) + "102";

		List<Map<String, Object>> PRIVILEGE_INFO = new ArrayList<Map<String, Object>>();
		PRIVILEGE_INFO.add(0, new HashMap<String, Object>());
		PRIVILEGE_INFO.get(0).put("PRIVILEGEDTOLIST", PRIVILEGEDTOLIST);
		PRIVILEGE_INFO.get(0).put("SYSTEM_ID", "6");
		PRIVILEGE_INFO.get(0).put("DEVICE_NUMBER", privilegeDeductServiceVo.getMobile170());
		PRIVILEGE_INFO.get(0).put("STAFF_ID", "6");
		PRIVILEGE_INFO.get(0).put("REQUEST_ID", REQUEST_ID);

		Map<String, Object> AirPayment = new HashMap<String, Object>();
		AirPayment.put("PRIVILEGE_INFO", PRIVILEGE_INFO);

		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		Map<String, Object> body = new HashMap<String, Object>();
		sooList.add(0, new HashMap<String, Object>());
		sooList.get(0).put("AirPayment", AirPayment);
		body.put("SOO", sooList);

		setIntefaceType(IntefaceType.BILL);
		log.info("*************************BOSS航空特权查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1003028", ResponseBean.class);
		log.info("*************************BOSS航空特权查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultSooList = (List<Map<String, Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		List<Map<String, Object>> remainPrivilegeList = (List<Map<String, Object>>) resultsooMap.get("REMAINPRIVILEGEDTOLIST");
		String status = (String) resultResultMap.get(HhConstants.PRIVILEGERESULT);
		
		if (HhConstants.SUCCESS.equals(status)){
			resultMap.put("result", "0");
			resultMap.put("count", remainPrivilegeList.get(0).get("AMOUNT"));
			ValidateCodeUtil.put("Qrcode" + privilegeDeductServiceVo.getMobile170(), "Qrcode");
			ValidateCodeUtil.put("Count" + privilegeDeductServiceVo.getMobile170(), remainPrivilegeList.get(0).get("AMOUNT").toString());
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
		}else{
			resultMap.put("result", "1");
			resultMap.put("msg", resultResultMap.get("Msg"));
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
		}

		log.info("*************************APP航空特权查询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
	@Override
	public Map<String, Object> queryQrcodeDeduct(PrivilegeSmsVo privilegeSmsVo) {
		log.info("*************************APP航空特权服务使用接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(privilegeSmsVo).toString());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = ValidateCodeUtil.isSuccess("Qrcode" + privilegeSmsVo.getMobile(), privilegeSmsVo.getSms());
		if(flag){
			resultMap.put("result", "0");
			for(int i = 0; i < 99999; i++){
				if(ValidateCodeUtil.isSuccess("Count" + privilegeSmsVo.getMobile(), Integer.toString(i))){
					resultMap.put("count", i);
					break;
				}
			}
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
		}else{
			resultMap.put("result", "1");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
		}
		
		return resultMap;
	}
}