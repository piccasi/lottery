package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.PlaceOrderServiceServ;
import com.tydic.uniform.hh.vo.rep.PlaceOrderNetWorkVo;
import com.tydic.uniform.hh.vo.rep.PlaceOrderNumbersVo;
import com.tydic.uniform.hh.vo.rep.PlaceOrderPaymentsVo;
import com.tydic.uniform.hh.vo.rep.PlaceOrderVo;
import com.tydic.uniform.hh.vo.resp.PlaceOrderResp;
import com.tydic.uniform.hh.vo.resp.RechargeOrderResp;

import net.sf.json.JSONObject;
/**
 * PlaceOrder
 * <p></p>
 * @author Administrator 2015年10月16日 下午6:00:33
 * @ClassName PlaceOrderServiceImpl
 * @Description eshop 下单
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月16日
 * @modify by reason:{方法名}:{原因}
 */
@Service("PlaceOrderServiceServ")
public class PlaceOrderServiceImpl extends AbstractService implements PlaceOrderServiceServ {
	public static Logger log=Logger.getLogger(PlaceOrderServiceImpl.class);
	@Override
	public Map<String, Object> placeorderserviceserv(PlaceOrderVo placeordervo) {
		log.info("*************************APP下单接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(placeordervo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		//List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		//sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> sooListMap=new HashMap<String,Object>();
		Map<String,Object> distrmap=new HashMap<String,Object>();
		distrmap.put("address", placeordervo.getAddress());
		distrmap.put("consignee", placeordervo.getConsignee());
		distrmap.put("detailAddress", placeordervo.getDetailaddress());
		distrmap.put("mobileNumber", placeordervo.getMobilenumber());
		distrmap.put("regionId", placeordervo.getRegionid());
		distrmap.put("telNumber", placeordervo.getTelnumber());
		sooListMap.put("orderDistribution", distrmap);
		
		sooListMap.put("orderFlag", placeordervo.getOrderflag());
		sooListMap.put("orderFrom", placeordervo.getOrderfrom());
		sooListMap.put("orderFromView", placeordervo.getOrderfromview());
		
		List<Map<String,Object>> networklist = new ArrayList<Map<String,Object>>();
		List<PlaceOrderNetWorkVo> placeOrderNetWorkVo =placeordervo.getOrdernetworkslist();
		if (placeOrderNetWorkVo!=null) {
			Map<String,Object> networkmap=new HashMap<String,Object>();
			for (int i = 0; i < placeOrderNetWorkVo.size(); i++) {
				PlaceOrderNetWorkVo ponetworkvo = (PlaceOrderNetWorkVo) placeOrderNetWorkVo.get(i);
				networkmap.put("fullName", ponetworkvo.getFullname());
				networkmap.put("idCardAddress", ponetworkvo.getIdcardaddress());
				networkmap.put("idCardNum", ponetworkvo.getIdcardnum());
				networkmap.put("idCardPhoto1", ponetworkvo.getIdcardphoto1());
				networkmap.put("idCardPhoto2", ponetworkvo.getIdcardphoto2());
				networkmap.put("serviceNum", ponetworkvo.getServicenumnetwork());
			}
			networklist.add(networkmap);
		}
		sooListMap.put("orderNetworks",networklist);
		
		List<Map<String,Object>> numlist = new ArrayList<Map<String,Object>>();
		List<PlaceOrderNumbersVo> placeOrderNumbersVo =placeordervo.getOrdernumberslist();
		if (placeOrderNumbersVo!=null) {
			Map<String,Object> nummap=new HashMap<String,Object>();
			for (int i = 0; i < placeOrderNumbersVo.size(); i++) {
				PlaceOrderNumbersVo ponumvo = (PlaceOrderNumbersVo) placeOrderNumbersVo.get(i);
				nummap.put("city", ponumvo.getCity());
				nummap.put("minConsume", ponumvo.getMinconsume());
				nummap.put("nbrLevel", ponumvo.getNbrlevel());
				nummap.put("offerId", ponumvo.getOfferidnum());
				nummap.put("preDeposit", ponumvo.getPredeposit());
				nummap.put("serviceNum", ponumvo.getServicenumnum());
			}
			numlist.add(nummap);
		}
		sooListMap.put("orderNumbers",numlist);
		
		Map<String,Object> orderoffermap = new HashMap<String,Object>();
		orderoffermap.put("offerId", placeordervo.getOfferid());
		sooListMap.put("orderOffer",orderoffermap);
		
		List<Map<String,Object>> paymentslist = new ArrayList<Map<String,Object>>();
		List<PlaceOrderPaymentsVo> placeOrderPaymentsVo =placeordervo.getOrderpaymentslist();
		if (placeOrderPaymentsVo!=null) {
			Map<String,Object> paymentsmap=new HashMap<String,Object>();
			for (int i = 0; i < placeOrderPaymentsVo.size(); i++) {
				PlaceOrderPaymentsVo popaymentsvo = (PlaceOrderPaymentsVo) placeOrderPaymentsVo.get(i);
				paymentsmap.put("payAmount", popaymentsvo.getPayamount());
				paymentsmap.put("payAmountView", popaymentsvo.getPayamountview());
				paymentsmap.put("payMode", popaymentsvo.getPaymode());
				paymentsmap.put("payModeView", popaymentsvo.getPaymodeview());
			}
			paymentslist.add(paymentsmap);
		}
		sooListMap.put("orderPayments",paymentslist);
		
		sooListMap.put("orderRemark",placeordervo.getOrderremark());
		sooListMap.put("orderSource",placeordervo.getOrdersource());
		sooListMap.put("orderSourceView",placeordervo.getOrdersourceview());
		sooListMap.put("payAmt",placeordervo.getPayamt());
		sooListMap.put("promotionAmt",placeordervo.getPromotionamt());
		sooListMap.put("totalAmt",placeordervo.getTotalamt());
		sooListMap.put("totalAmtView",placeordervo.getTotalamtview());
		sooListMap.put("type",placeordervo.getType());
		sooListMap.put("typeView",placeordervo.getTypeview());
		sooListMap.put("userAccount",placeordervo.getUseraccount());
		sooListMap.put("channelCode", "10027");//渠道ID
		sooListMap.put("thirdChannelCode", "");//第三方渠道ID
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS下单接口入参*************************");
		log.info("BOSS response str:" + sooListMap.toString());
		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(sooListMap,"SC1002201", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS下单接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String,Object> resulsooMap = (Map<String,Object>) bean.getBody().get(0);
		if (resulsooMap.containsKey("rspData")) {
			Map<String,Object> resultResultMap = (Map<String,Object>) resulsooMap.get("rspData");
		}
		String status = (String) resulsooMap.get(HhConstants.ESHOPRESULT);
		String repdata=null;
		if (resulsooMap.containsKey("rspData")) {
			repdata = resulsooMap.get(HhConstants.ESHOPREPDATA).toString();
		}
		PlaceOrderResp placeOrderResp = new PlaceOrderResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			   resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			   resultMap.put(HhConstants.ESHOPREPDATA, repdata);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
		    }
		    resultMap.put("placeOrderResp", placeOrderResp);
		    log.info("*************************APP下单单接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	@Override
	public Map submitOrder(Map req) {
		String serial_number = System.currentTimeMillis()+"";
		
		List pay = JSON.parseArray(req.get("pay").toString());
		//req.put("CHANNEL_CODE", HhConstants.CHANNEL_CODEVALUE);// 渠道编码
		//req.put("EXT_SYSTEM", HhConstants.EXT_SYSTEMVALUE);// 接入系统标识（见附录编码）
		List numberlistStr = JSON.parseArray(req.get("numberlist").toString());
		Map numberlist = (Map)numberlistStr.get(0);
		
		Map deliveryinfo = (Map)JSON.parseObject(req.get("deliveryinfo").toString());
		List mealsList = JSON.parseArray(req.get("meals").toString());
		Map meals = (Map)mealsList.get(0);

		Map custlist = JSON.parseObject(req.get("cust").toString());
		
		Map imglist = JSON.parseObject(req.get("img").toString());
		String teleType = req.get("teleType").toString();
		String isOrderNum = StringUtils.isEmpty(req.get("isOrderNum").toString())?"N":req.get("isOrderNum").toString();
		
		if(deliveryinfo.equals(null) || numberlist.equals(null) || meals.equals(null)){
			return null;
		}

		Map result = new HashMap();
		Map body = new HashMap();
		String address = "";
		if(deliveryinfo.get("province") != null && deliveryinfo.get("city") != null && deliveryinfo.get("add") != null){
			address = deliveryinfo.get("province").toString()+deliveryinfo.get("city").toString()+deliveryinfo.get("add").toString();
		}else{
			address = custlist.get("contact_addr").toString();
		}
		Map orderDistribution = new HashMap();
		orderDistribution.put("address", address);
		orderDistribution.put("detailAddress", address);
		orderDistribution.put("regionId", numberlist.get("city_code"));
		orderDistribution.put("consignee", deliveryinfo.get("username")==null?custlist.get("cust_name"):deliveryinfo.get("username"));
		orderDistribution.put("mobileNumber", deliveryinfo.get("tel")==null?((Map)((List)custlist.get("PROD_INST")).get(0)).get("ACC_NBR"):deliveryinfo.get("tel"));
		
		List<Map> orderNetworks = new ArrayList<Map>();
		orderNetworks.add(0,new HashMap());
		orderNetworks.get(0).put("fullName", deliveryinfo.get("username")==null?custlist.get("cust_name"):deliveryinfo.get("username"));
		orderNetworks.get(0).put("idCardAddress", deliveryinfo.get("idcardadd")==null?custlist.get("certi_addr"):deliveryinfo.get("idcardadd"));
		orderNetworks.get(0).put("idCardNum", deliveryinfo.get("idcard")==null?custlist.get("certi_nbr"):deliveryinfo.get("idcard"));
		orderNetworks.get(0).put("idCardPhoto1", imglist.get("img1"));
		orderNetworks.get(0).put("idCardPhoto2", imglist.get("img2"));
		orderNetworks.get(0).put("idCardPhoto3", imglist.get("img3"));
		orderNetworks.get(0).put("serviceNum", deliveryinfo.get("tel")==null?((Map)((List)custlist.get("prod_inst")).get(0)).get("acc_nbr"):deliveryinfo.get("tel"));
		
		String proviceName="";
		String cityName="";
		if(numberlist.get("province_name") != null){ 
			proviceName = numberlist.get("province_name").toString();
		}
		if(numberlist.get("city_name") != null){ 
			cityName = numberlist.get("city_name").toString();
		}
		//Float deposit = Float.parseFloat(((Map)numberlist.get(0)).get("PRE_DEPOSITO").toString())*100;
		Float payamt = Float.parseFloat(numberlist.get("PRE_DEPOSIT").toString())*100;
		List<Map> orderNumbers = new ArrayList<Map>();
		orderNumbers.add(0,new HashMap());
		orderNumbers.get(0).put("city", numberlist.get("city_code"));
		orderNumbers.get(0).put("cityAddr", proviceName+cityName);
		orderNumbers.get(0).put("minConsume", numberlist.get("min_consume"));
		orderNumbers.get(0).put("nbrLevel", numberlist.get("nbr_level"));
		orderNumbers.get(0).put("offerId", meals.get("OFFER_ID"));
		orderNumbers.get(0).put("preDeposit", payamt.intValue());
		orderNumbers.get(0).put("serviceNum",numberlist.get("service_num"));
		
		List<Map> orderPayments = new ArrayList<Map>();
		int j=0;
		for(int i=0;i<pay.size();++i){
			Float payAmount = Float.parseFloat(((Map)pay.get(i)).get("payAmount").toString());
			if(payAmount>0){
			orderPayments.add(j, new HashMap());//为接口SC1002201，准备入参。
			((Map)orderPayments.get(j)).put("payAmount", payAmount);//原有的选卡单价，修改后的运费，总价对应的Key是多少。
			((Map)orderPayments.get(j)).put("payAmountView", ((Map)pay.get(i)).get("payAmountView"));
			((Map)orderPayments.get(j)).put("payMode", ((Map)pay.get(i)).get("payMode"));
			((Map)orderPayments.get(j)).put("payModeView", ((Map)pay.get(i)).get("payModeView"));
			((Map)orderPayments.get(j)).put("freightFlag", ((Map)pay.get(i)).get("freightFlag"));
			((Map)orderPayments.get(j)).put("payFreight", ((Map)pay.get(i)).get("payFreight"));
			++j;
			}
		}
		
		
		Map orderOffer = new HashMap();
		orderOffer.put("offerId", meals.get("OFFER_ID"));
		orderOffer.put("offerDeposit", "0");
		body.put("orderDistribution", orderDistribution);
		body.put("orderNetworks", orderNetworks);
		body.put("orderNumbers", orderNumbers);
		if(orderPayments.size() !=0){
			body.put("orderPayments", orderPayments);
		} 
		body.put("orderOffer", orderOffer);
		
		body.put("orderFlag", req.get("orderFlag"));//区分选号下单、换靓号下单
		body.put("orderFrom", "2");
		body.put("orderFromView", "WAP");
		body.put("orderRemark", "");
		body.put("recommendCode", "");
		body.put("orderSource", "2");
		body.put("subject", meals.get("OFFER_NAME"));
		body.put("orderSourceView", "MOBILE");
		body.put("payAmt", payamt.intValue());
		body.put("promotionAmt", payamt.intValue());
		body.put("totalAmt", payamt.intValue());
		body.put("totalAmtView", payamt.intValue());
		body.put("type", "1");
		body.put("teleType", teleType);//网络类型
		body.put("networkType", numberlist.get("networkType"));//制式类型
		body.put("isOrderNum", isOrderNum);
		body.put("typeView", "普通订单");
		body.put("userAccount", custlist.get("cust_id"));
		body.put("channelCode", "10027");//渠道ID
		body.put("thirdChannelCode", "");//第三方渠道ID暂时未空
		
		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1002201", ResponseBean.class);
		result = (Map)bean.getBody().get(0);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(null != ((Map)result.get("rspData")) && null != ((Map)result.get("rspData")).get("crmOrderNumber") && StringUtils.isNotEmpty(((Map)result.get("rspData")).get("crmOrderNumber").toString())){
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
		    resultMap.put(HhConstants.RESULT, "0");
		    resultMap.put(HhConstants.MSG, "成功");
		    resultMap.put("resultOrderMap", result);
		}else{
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, result.get("rspMsg"));//失败描述
		}
		return resultMap;
	}
	
}
