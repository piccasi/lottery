package com.tydic.uniform.hh.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tydic.alipay.service.SaveFileService;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.AccountOrderServiceServ;
import com.tydic.uniform.hh.util.SoapUtil;
import com.tydic.uniform.hh.vo.rep.AccountFee_itemBaseVo;
import com.tydic.uniform.hh.vo.rep.AccountOfferBaseVo;
import com.tydic.uniform.hh.vo.rep.AccountOrderVo;
import com.tydic.uniform.hh.vo.resp.AccountOrderResp;
import com.tydic.uniform.hh.vo.resp.NumberListResp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * <p></p>
 * @author ghp 2015年9月29日 下午6:16:12
 * @ClassName AccountOrderServiceImpl  开户订单接口实体
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
@Service("AccountOrderServiceServ")
public class AccountOrderServiceImpl extends AbstractService implements AccountOrderServiceServ{
	private static Logger log = Logger.getLogger(AccountOrderServiceImpl.class);
	@Override
	public Map<String, Object> accountorder(AccountOrderVo accountordervo) {
		
		
		log.info("*************************APP开户订单接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(accountordervo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		Map body = new HashMap();
		List<Map> sooList = new ArrayList<Map>();
		//1订单信息
		sooList.add(0, new HashMap());
		
		Map req = new HashMap();
		req.put(HhConstants.SEL_IN_ORG_ID, "01");
		req.put(HhConstants.EXT_SYSTEM, "102");
		req.put(HhConstants.BUSINESS_TYPE, accountordervo.getBusiness_type());
		req.put(HhConstants.CUST_ID, accountordervo.getCust_id());
		req.put(HhConstants.EXT_ORDER_ID, accountordervo.getExt_order_id());
		req.put(HhConstants.ORDER_TYPE, accountordervo.getOrder_type());
		req.put(HhConstants.PAY_STATUS, accountordervo.getPay_status());
		req.put(HhConstants.SALE_ORDER_ID, accountordervo.getSale_order_id());
		req.put(HhConstants.APPENDIX, accountordervo.getAppendix());
		
		List sale_order_inst_list = new ArrayList();
		sale_order_inst_list.add(req);
		
		sooList.get(0).put(HhConstants.SALE_ORDER_INST,sale_order_inst_list);
		
		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "SAVE_SALE_ORDER_INST");
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		//2       套餐信息 "SALE_OFFER_INST"
		
		sooList.add(1, new HashMap());
		List<Map> sALE_OFFERlist = new ArrayList();
		List offer_instList= accountordervo.getSale_offer_instlist();
		for(int i=0; i<offer_instList.size(); i++){
			AccountOfferBaseVo accountofferbasevo = (AccountOfferBaseVo) offer_instList.get(i);
			if(accountofferbasevo!=null){
				Map offer_inst_req = new HashMap();
				offer_inst_req.put(HhConstants.NEW_FLAG, accountofferbasevo.getNew_flag());
				offer_inst_req.put(HhConstants.OFFER_ID, accountofferbasevo.getOffer_id());
				offer_inst_req.put(HhConstants.OFFER_INST_ID, accountofferbasevo.getOffer_inst_id());
				offer_inst_req.put(HhConstants.OFFER_TYPE_ID, accountofferbasevo.getOffer_type_id());
				offer_inst_req.put(HhConstants.SALE_ORDER_ID, accountofferbasevo.getSale_order_id());
				sALE_OFFERlist.add(offer_inst_req);
			}
		}
		
		sooList.get(1).put(HhConstants.SALE_OFFER_INST,sALE_OFFERlist);
		
		Map offer_inst_pub_req = new HashMap();
		offer_inst_pub_req.put(HhConstants.TYPE, "SAVE_SALE_OFFER_INST");
		
		sooList.get(1).put(HhConstants.PUB_REQ,offer_inst_pub_req);
		
		//3费用信息 "FEE_ITEM"
		sooList.add(2, new HashMap());
		List<Map> fEE_ITEMlist = new ArrayList();
		List fee_itemlist= accountordervo.getServicedtolist();
		for(int i=0; i<fee_itemlist.size(); i++){
			AccountFee_itemBaseVo accountfee_itembasevo = (AccountFee_itemBaseVo) fee_itemlist.get(i);
			if(accountfee_itembasevo!=null){
				Map offer_inst_req = new HashMap();
				offer_inst_req.put(HhConstants.ACCT_ITEM_TYPE, accountfee_itembasevo.getAcct_item_type());
				offer_inst_req.put(HhConstants.AMOUNT, accountfee_itembasevo.getAmount());
				offer_inst_req.put(HhConstants.OBJ_INST_ID, accountfee_itembasevo.getObj_inst_id());
				offer_inst_req.put(HhConstants.OBJ_INST_TYPE, accountfee_itembasevo.getObj_inst_type());
				offer_inst_req.put(HhConstants.SALE_ORDER_ID, accountfee_itembasevo.getSale_order_id());
				fEE_ITEMlist.add(offer_inst_req);
			}
		}
		
		sooList.get(2).put(HhConstants.FEE_ITEM,fEE_ITEMlist);
		
		Map fee_item_pub_req = new HashMap();
		fee_item_pub_req.put(HhConstants.TYPE, "SAVE_FEE_ITEM");
		
		sooList.get(2).put(HhConstants.PUB_REQ,fee_item_pub_req);
		
		
		//4客户信息 "SALE_CUST"
		sooList.add(3, new HashMap());
		
		Map sale_cust_req = new HashMap();
		sale_cust_req.put(HhConstants.AUTH_FLAG, accountordervo.getAuth_flag());
		sale_cust_req.put(HhConstants.CERT_NBR, accountordervo.getCert_nbr());
		sale_cust_req.put(HhConstants.CERT_TYPE, accountordervo.getCert_type());
		sale_cust_req.put(HhConstants.CUST_ID, accountordervo.getCust_id());
		sale_cust_req.put(HhConstants.CUST_NAME, accountordervo.getCustname());
		sale_cust_req.put(HhConstants.BIRTHDATE, accountordervo.getBirthdate());
		sale_cust_req.put(HhConstants.NEW_FLAG, accountordervo.getNew_flag());
		sale_cust_req.put(HhConstants.SALE_ORDER_ID, accountordervo.getSale_order_id());
		sale_cust_req.put(HhConstants.EMAIL, accountordervo.getEmail());
		List sale_cust_list = new ArrayList();
		sale_cust_list.add(sale_cust_req);
		sooList.get(3).put(HhConstants.SALE_CUST,sale_cust_list);
		
		Map sale_cust_pub_req = new HashMap();
		sale_cust_pub_req.put(HhConstants.TYPE, "SAVE_SALE_CUST");
		
		sooList.get(3).put(HhConstants.PUB_REQ,sale_cust_pub_req);
		
		//6产品信息 "SALE_PROD_INST"
		
		sooList.add(4, new HashMap());
		
		Map reqSALE_PROD  = new HashMap();
		reqSALE_PROD .put(HhConstants.ACC_NBR, accountordervo.getAcc_nbr());
		reqSALE_PROD .put(HhConstants.NEW_FLAG, accountordervo.getNew_flag());
		reqSALE_PROD .put(HhConstants.PRODUCT_ID, accountordervo.getProduct_id());
		reqSALE_PROD .put(HhConstants.PROD_INST_ID, accountordervo.getProd_inst_id());
		reqSALE_PROD .put(HhConstants.SALE_ORDER_ID, accountordervo.getSale_order_id());
		
		List sale_prod_instlist  = new ArrayList();
		sale_prod_instlist.add(reqSALE_PROD);
		sooList.get(4).put(HhConstants.SALE_PROD_INST,sale_prod_instlist);
		
		Map req_PUB_SALE_PROD  = new HashMap();
		req_PUB_SALE_PROD .put(HhConstants.TYPE, "SAVE_SALE_PROD_INST");
		
		sooList.get(4).put(HhConstants.PUB_REQ,req_PUB_SALE_PROD);
		
		//7产品实例属性 "SALE_PROD_INST_ATTR"
		
		sooList.add(5, new HashMap());
		
		Map reqSALE_PROD_INST_ATTR   = new HashMap();
		reqSALE_PROD_INST_ATTR  .put(HhConstants.ATTR_ID, accountordervo.getAttr_id());
		reqSALE_PROD_INST_ATTR  .put(HhConstants.ATTR_NAME, accountordervo.getAttr_name());
		reqSALE_PROD_INST_ATTR  .put(HhConstants.ATTR_VALUE, accountordervo.getAttr_value());
		reqSALE_PROD_INST_ATTR  .put(HhConstants.PROD_INST_ID, accountordervo.getProd_inst_id());
		
		
		List sale_prod_inst_attrlist   = new ArrayList();
		sale_prod_inst_attrlist .add(reqSALE_PROD_INST_ATTR );
		sooList.get(5).put(HhConstants.SALE_PROD_INST_ATTR,sale_prod_inst_attrlist );
		
		Map req_PUB_INST_ATTR    = new HashMap();
		req_PUB_INST_ATTR   .put(HhConstants.TYPE, "SAVE_SALE_PROD_INST_ATTR");
		
		sooList.get(5).put(HhConstants.PUB_REQ,req_PUB_INST_ATTR );
		
		//8 收货信息 "RECEIVE_INFO"
		
		sooList.add(6, new HashMap());
		
		Map reqRECEIVE_INFO = new HashMap();
		reqRECEIVE_INFO.put(HhConstants.ADDRESS, accountordervo.getAddress());
		reqRECEIVE_INFO.put(HhConstants.MOBILE, accountordervo.getMobile());
		reqRECEIVE_INFO.put(HhConstants.RECEIVE_NAME, accountordervo.getReceive_name());
		reqRECEIVE_INFO.put(HhConstants.SALE_ORDER_ID, accountordervo.getSale_order_id());
		
		
		List receive_infolist = new ArrayList();
		receive_infolist .add(reqRECEIVE_INFO );
		sooList.get(6).put(HhConstants.RECEIVE_INFO,receive_infolist );
		
		Map req_PUB_RECEIVE = new HashMap();
		req_PUB_RECEIVE.put(HhConstants.TYPE, "SAVE_RECEIVE_INFO");
		
		sooList.get(6).put(HhConstants.PUB_REQ,req_PUB_RECEIVE  );
		
		//9NUMBER_INFO号码信息
		
		sooList.add(7, new HashMap());
		
		Map reqNUMBER_INFO = new HashMap();
		reqNUMBER_INFO.put(HhConstants.CITY_CODE, accountordervo.getCity_code());
		reqNUMBER_INFO.put(HhConstants.NUMBER_LEVEL, accountordervo.getNumber_level());
		reqNUMBER_INFO.put(HhConstants.PROD_INST_ID, accountordervo.getProd_inst_id());
		reqNUMBER_INFO.put(HhConstants.PROVICE_NAME, accountordervo.getProvice_name());
		reqNUMBER_INFO.put(HhConstants.SALE_ORDER_ID, accountordervo.getSale_order_id());
		
		
		List number_infolist  = new ArrayList();
		number_infolist .add(reqNUMBER_INFO );
		sooList.get(7).put(HhConstants.NUMBER_INFO,number_infolist );
		
		Map req_PUB_NUMBER = new HashMap();
		req_PUB_NUMBER.put(HhConstants.TYPE, "SAVE_NUMBER_INFO");
		
		sooList.get(7).put(HhConstants.PUB_REQ,req_PUB_NUMBER  );
		body.put(HhConstants.SOO, sooList);
		

		
//		String ll = null;
//		try {
//			ll = JSON.toJSONString(sooList);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS开户订单接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001010", ResponseBean.class);
//		SoapUtil soapUilt = new SoapUtil();
//		String rspJson = soapUilt.SoapWebService("/ServiceBus/saleView/order/saveOrder002", "/esb/saveOrder002", "108", ll);

//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS开户订单接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		AccountOrderResp accountorderresp = new AccountOrderResp();
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSooList.get(0);
		Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		
	    if(HhConstants.SUCCESS.equals(status)){
	        Map mapInfo = new HashMap();
	        String sale_order_id = (String) resultsooMap.get(HhConstants.SALE_ORDER_ID);
	        mapInfo.put(HhConstantsResp.SALE_ORDER_ID, sale_order_id);
	    	
	    	accountorderresp.setMapInfo(mapInfo);
	    	
	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
	    	resultMap.put("accountorderresp", accountorderresp);
	    	
			SaveFileService saveFileService=new SaveFileService();
			try {
				saveFileService.init();
			} catch (IOException e) {
				/** TODO Auto-generated catch block */
				e.printStackTrace();
			}
			saveFileService.object2File(body, sale_order_id);
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
	  
	    log.info("*************************APP开户订单接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}
	
	
	
	public Map submitData(Map map) throws Exception {
		String outTradeNo = map.get("out_trade_no").toString();
		String tradeStatus = map.get("trade_status").toString();
		
		log.info("outTradeNo订单号值为"+outTradeNo);
		log.info("tradeStatus支付状态为"+tradeStatus);
		
		 Map backDataSubmit = new HashMap();
		 if (outTradeNo == null && "".equals(outTradeNo.trim())) {
			 backDataSubmit.put("RESULT", "1");
			 backDataSubmit.put("MSG", "订单号为空");
			 log.info("订单号为空");
			 return backDataSubmit; 
		 }
		if("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)){
		}else{
			 backDataSubmit.put("RESULT", "1");
			 backDataSubmit.put("MSG", "支付宝支付失败");
			 log.info("支付宝支付失败");
			 return backDataSubmit;
		}
		try {
			SaveFileService saveFileService=new SaveFileService();
			Object object = (Object) saveFileService.file2Object(outTradeNo);
			JSONObject jsons = (JSONObject) JSONObject.fromObject(object);
			JSONArray jsonsContextOrder = jsons.getJSONArray("SOO");
			if (jsonsContextOrder.size() > 0) {
				for (int i = 0; i < jsonsContextOrder.size(); i++) {
					JSONObject jsonsContextOrderREQ = jsonsContextOrder
							.getJSONObject(i).getJSONObject("PUB_REQ");
					if ("SAVE_SALE_ORDER_INST".equals(jsonsContextOrderREQ.get(
							"TYPE").toString())) {
						JSONArray jsonsContextOrderInst = jsonsContextOrder
								.getJSONObject(i).getJSONArray(
										"SALE_ORDER_INST");
						// 1订单信息
						Map req = new HashMap();
						req.put("SEL_IN_ORG_ID", jsonsContextOrderInst
								.getJSONObject(0).get("SEL_IN_ORG_ID")
								.toString());
						req.put("EXT_SYSTEM",
								jsonsContextOrderInst.getJSONObject(0)
										.get("EXT_SYSTEM").toString());
						req.put("BUSINESS_TYPE", jsonsContextOrderInst
								.getJSONObject(0).get("BUSINESS_TYPE")
								.toString());
						req.put("CUST_ID", jsonsContextOrderInst.getJSONObject(0)
								.get("CUST_ID").toString());
						req.put("EXT_ORDER_ID", jsonsContextOrderInst
								.getJSONObject(0).get("EXT_ORDER_ID")
								.toString());
						// req .put("EXT_SYSTEM", "100005");
						// req .put("SEL_IN_ORG_ID", "888888");

						req.put("ORDER_TYPE", "1000");
						req.put("PAY_STATUS", "1");
						req.put("SALE_ORDER_ID", outTradeNo);
						req.put("APPENDIX", jsonsContextOrderInst.getJSONObject(0)
								.get("APPENDIX").toString());

						List<Map> SALE_ORDER_INSTList = new ArrayList<Map>();
						SALE_ORDER_INSTList.add(req);
						Map req_ORDER_REQ = new HashMap();
						req_ORDER_REQ.put("TYPE", "SAVE_SALE_ORDER_INST");

						Map ORDER_INST = new HashMap();
						ORDER_INST.put("SALE_ORDER_INST", SALE_ORDER_INSTList);
						ORDER_INST.put("PUB_REQ", req_ORDER_REQ);

						jsonsContextOrder.remove(i);
						jsonsContextOrder.add(i, ORDER_INST);

					}

				}
			}
			Map bodySubmit = new HashMap();
			bodySubmit.put("SOO", jsonsContextOrder);

			setIntefaceType(IntefaceType.CRM);
			ResponseBean bean = (ResponseBean) this.appApiInvoke(bodySubmit,"SC1001010", ResponseBean.class);
			
			
			List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
			Map returnMapSubmit = (Map) resultSooList.get(0);
			log.info("真订单出参"+resultSooList.toString());
			
			 Map RESPmap = (Map) returnMapSubmit.get("RESP");
			 String result= RESPmap.get("RESULT").toString();
		     if(result.equals("0")){
			   backDataSubmit.put("RESULT", result);
		     }else{
		    	 backDataSubmit.put("RESULT", result);
		    	 String MSG= RESPmap.containsKey("MSG")? RESPmap.get("MSG").toString() : "";
		    	 backDataSubmit.put("MSG", MSG);
		     }
			 
			return backDataSubmit;
		} catch (Exception e) {
			log.error("调用服务异常", e);
			throw new Exception("调用服务异常", e);
		}
	}
}
