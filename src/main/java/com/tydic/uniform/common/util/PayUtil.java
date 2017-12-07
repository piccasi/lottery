package com.tydic.uniform.common.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.tydic.uniform.hh.util.PropertiesUtil;

public class PayUtil {
	private static Logger logger = Logger.getLogger(PayUtil.class);
	
	public static Map<String,Object> getPay(Map<String,Object> resultResultMap){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("order_id", resultResultMap.get("RequestId"));
		
		String name = PropertiesUtil.getPropertyValue("order.name");
		String orderName = "代理商账户充值";
		if(StringUtils.isNotEmpty(name)){
			orderName = name+orderName;
		}
		resultMap.put("order_name", orderName);
		resultMap.put("pay_type", "agent_order");
		resultMap.put("notifyUrl_dic", PropertiesUtil.getPropertyValue("notify.url"));
		resultMap.put("call_back_succUrl", PropertiesUtil.getPropertyValue("call.back.succUrl"));
		resultMap.put("call_back_failUrl", PropertiesUtil.getPropertyValue("call.back.failUrl"));
		resultMap.put("pay_fee", resultResultMap.get("Balance"));
		resultMap.put("buss_code", "0");
		
		Map<String,String> notifyUrl_dic = new HashMap<String,String>();
		notifyUrl_dic.put("alibabaPay", PropertiesUtil.getPropertyValue("notify.url"));
		resultMap.put("notifyUrl_dic", notifyUrl_dic);
		
		resultMap.put("payment", PropertiesUtil.getPropertyValue("payment.type"));
		
		Map<String,Object> pay_seller = new HashMap<String,Object>();
		pay_seller.put("alibabaPay", "hhxx");
		resultMap.put("pay_seller", pay_seller);
		
		return resultMap;
	}
	
	public static Map<String,Object> aliToPay(Map<String,Object> resultResultMap){
		Map<String,Object> resultMap = new HashMap<String,Object>();
		//实例化客户端
		String APP_ID = PropertiesUtil.getPropertyValue("APP_ID");
		String ALIPAY_PRIVATE_KEY = PropertiesUtil.getPropertyValue("ALIPAY_PRIVATE_KEY");
		String ALIPAY_PUBLIC_KEY = PropertiesUtil.getPropertyValue("ALIPAY_PUBLIC_KEY");
		String alipayReqUrl = PropertiesUtil.getPropertyValue("ALIPAYREQURL");
		String notify_url  = PropertiesUtil.getPropertyValue("notify.url");
		String outtradeno = resultResultMap.get("RequestId").toString();;
		
		BigDecimal balance = new BigDecimal(resultResultMap.get("Balance").toString()); 
		double payAmount = balance.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue(); 
		
		String name = PropertiesUtil.getPropertyValue("order.name");
		String orderName = "代理商账户充值";
		if(StringUtils.isNotEmpty(name))orderName = name+orderName;

		logger.info("appid:"+APP_ID);
		logger.info("APP_PRIVATE_KEY:"+ALIPAY_PRIVATE_KEY);
		logger.info("ALIPAY_PUBLIC_KEY:"+ALIPAY_PUBLIC_KEY);
		logger.info("alipayReqUrl:"+alipayReqUrl);
		logger.info("notify_url:"+notify_url);
		logger.info("outtradeno:"+outtradeno);
		logger.info("payAmount:"+payAmount);
		
		AlipayClient alipayClient = new DefaultAlipayClient(alipayReqUrl, APP_ID, ALIPAY_PRIVATE_KEY, "json", "UTF-8", ALIPAY_PUBLIC_KEY, "RSA2");
		
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(orderName);
		model.setSubject(orderName);
		model.setOutTradeNo(outtradeno);
		model.setTimeoutExpress("30m");
		model.setTotalAmount(String.valueOf(payAmount));
		model.setProductCode("AGENT_RECHARGE");
		request.setBizModel(model);
		request.setNotifyUrl(notify_url);
		try {
			//这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			resultMap.put("aliPayOrderStr", response.getBody());
			resultMap.put("order_name", orderName);
			resultMap.put("pay_fee", String.valueOf(payAmount));
			resultMap.put("order_id", outtradeno);
			return resultMap;
		} catch (AlipayApiException e) {
			logger.info("调用支付宝生成预付单失败："+e.getErrMsg()+":"+e.getMessage(),e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		Map<String,Object> resultResultMap = new HashMap<String,Object>();
		resultResultMap.put("Balance", "10.09");
		resultResultMap.put("RequestId", "78912498124211242141");
		
		Map<String,Object> body = aliToPay(resultResultMap);
		System.out.println(body);
	}
}
