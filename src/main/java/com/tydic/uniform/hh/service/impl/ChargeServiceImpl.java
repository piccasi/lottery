package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.ChargeServiceServ;
import com.tydic.uniform.hh.service.interfaces.UsermsgServiceServ;
import com.tydic.uniform.hh.util.ValidateCodeUtil;
import com.tydic.uniform.hh.vo.rep.CardChargeVo;
import com.tydic.uniform.hh.vo.rep.CashChargeVo;
import com.tydic.uniform.hh.vo.rep.Judge170NumberVo;
import com.tydic.uniform.hh.vo.rep.UserInfoVo;
@Service("ChargeServiceServ")
public class ChargeServiceImpl extends AbstractService implements
		ChargeServiceServ {
	public static Logger log = Logger.getLogger(ChargeServiceImpl.class);
	/**
	 * 充值卡充值
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map<String, Object> CardCharge(CardChargeVo cardChagerVo) {
		log.info("*************************充值卡充值接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(cardChagerVo).toString());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//验证码校验  add by panxinxing 2015/12/03
		String phone = "randomCode" + cardChagerVo.getAcc_nbr_170();
		String randomCode = cardChagerVo.getRandomCode();
		boolean flag = ValidateCodeUtil.isSuccess(phone, randomCode);
		if (!flag) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, "验证码错误！");//失败描述
			return resultMap;
		}
		
		String dateStr = com.tydic.uniform.hh.util.DateUtil.getDateFormat(new Date(), "yyyyMMddHHmmss");
		String requestId = dateStr + String.valueOf(Math.random()).substring(2,8)+"8999";//
		Map<String,String> req = new HashMap<String,String>();
		req.put("REQUEST_ID",requestId);
		req.put("REQUEST_USER",cardChagerVo.getDestinationId());
		req.put("REQUEST_TIME",dateStr);
		req.put("CARD_PIN",cardChagerVo.getCardPin());//充值卡密码
		req.put("DESTINATION_ID", cardChagerVo.getDestinationId());//充值号码
		req.put("CARD_NO", cardChagerVo.getCardNo());//充值卡号
		req.put("ACCESS_TYPE","4");
		req.put("CHANNEL_CODE","10002");
		req.put("STAFF_ID","10002");
		
		Map<String,String> pubSeq = new HashMap<String,String>();
		pubSeq.put("TYPE", "CARDRECHARGE");

		Map body = new HashMap();
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap());
		sooList.get(0).put("CARD_RECHARGE",req);
		sooList.get(0).put("PUBSEQ",pubSeq);
		body.put("SOO", sooList);
		/*
		 * 请求接口
		 */
		log.info("*************************充值卡充值接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean=(ResponseBean) this.appApiInvoke(body, "SC1003025", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************充值卡充值接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map resultResultMap = (Map)((Map)((List<Map>)bean.getBody().get(0).get("SOO")).get(0)).get("RESP");
		String status = (String) resultResultMap.get("Result");
		String result = (String) resultResultMap.get("Msg");
		
		if(HhConstants.SUCCESS.equals(status)){
		    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
		    resultMap.put(HhConstants.RESULT, status);
		    resultMap.put(HhConstants.MSG, result);
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, result);//失败描述
	    }
		log.info("*************************APP充值卡充值接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	/**
	 * 现金充值
	 */
	@Override
	public Map<String, Object> CashCharge(CashChargeVo cashChagerVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		UserInfoVo userInfoVo = new UserInfoVo();
		userInfoVo.setMobile(cashChagerVo.getMobile());
		UsermsgServiceServ userService = new UsermsgServiceImpl();
		Map userMap = userService.getUserInfo(userInfoVo);
		if(null==userMap.get("CUST_ID")){
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, "号码非在网用户");//失败描述
			return resultMap;
		}
		Map req = new HashMap();
		req.put("orderFrom",'1');
		req.put("orderFromView","商城");
		
		List orderpayments = new ArrayList();
		Map orderpaymentsMap = new HashMap();
		orderpaymentsMap.put("payMode", "4");
		orderpaymentsMap.put("payModeView", "支付宝支付");
		orderpayments.add(orderpaymentsMap);
		
		req.put("orderPayments",orderpayments);
		
		Map orderRechargeMap = new HashMap();
		if(null != cashChagerVo.getRcgAmt() && Float.parseFloat(cashChagerVo.getRcgAmt())<100){
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, "充值金额有误");//失败描述
			return resultMap;
		}
		orderRechargeMap.put("rcgAmt", cashChagerVo.getRcgAmt());//支付金额
		orderRechargeMap.put("rcgMobile", cashChagerVo.getMobile());//手机号
		req.put("orderRecharge",orderRechargeMap);
		
		req.put("orderRemark","充值订单"); 
		req.put("orderSource","2");
		req.put("orderSourceView","wx");
		req.put("payAmt",cashChagerVo.getRcgAmt());			
		req.put("promotionAmt","0");
		req.put("totalAmt",cashChagerVo.getRcgAmt());
		req.put("totalAmtView",cashChagerVo.getRcgAmt());
		req.put("type","1");
		req.put("typeView","充值订单");
		req.put("userAccount",userMap.get("CUST_ID"));//账号
		req.put("orderFlag","4");
		
		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean=(ResponseBean) this.appApiInvoke(req, "SC1002004", ResponseBean.class);
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************APP现金接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String,Object> resultOrderMap = (Map<String, Object>) ((Map)bean.getBody().get(0)).get("rspData");
		if(null != resultOrderMap.get("crmOrderNumber") && StringUtils.isNotEmpty(resultOrderMap.get("crmOrderNumber").toString())){
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
		    resultMap.put(HhConstants.RESULT, "0");
		    resultMap.put(HhConstants.MSG, "预充值成功");
		    resultMap.put("resultOrderMap", resultOrderMap);
		}else{
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, "预充值失败");//失败描述
		}
		log.info("*************************APP现金接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	/**
	 * 验证码
	 */
	@Override
	public Map<String, Object> getRandomCode(Judge170NumberVo judge170NumberVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		String phone = "randomCode" + judge170NumberVo.getAcc_nbr_170();
		
		// 生成验证码
		String validateCode = ValidateCodeUtil.create(4);
		ValidateCodeUtil.put(phone, validateCode);
		resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
		resultMap.put("randomcode", validateCode);//成功
		log.info("*************************APP充值卡充值验证码*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
}
