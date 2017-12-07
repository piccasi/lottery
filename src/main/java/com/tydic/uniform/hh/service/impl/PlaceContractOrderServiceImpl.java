package com.tydic.uniform.hh.service.impl;

import com.alibaba.fastjson.JSON;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.PlaceContractOrderServiceServ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * PlaceOrder
 * <p></p>
 * @author Administrator 2015年10月16日 下午6:00:33
 * @ClassName PlaceContractOrderServiceImpl
 * @Description eshop 合约机下单
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月16日
 * @modify by reason:{方法名}:{原因}
 */
@Service("PlaceContractOrderServiceServ")
public class PlaceContractOrderServiceImpl extends AbstractService implements PlaceContractOrderServiceServ {

	@Override
	public Map submitContractOrder(Map req) {
		String serial_number = System.currentTimeMillis()+"";
		System.out.println("req:"+req.toString());
		
		List pay = JSON.parseArray(req.get("pay").toString());
		//req.put("CHANNEL_CODE", HhConstants.CHANNEL_CODEVALUE);// 渠道编码
		//req.put("EXT_SYSTEM", HhConstants.EXT_SYSTEMVALUE);// 接入系统标识（见附录编码）
		Map numberlist = JSON.parseObject(req.get("numberlist").toString());
//		Map numberlist = (Map)numberlistStr.get(0);
		
		Map deliveryinfo = (Map)JSON.parseObject(req.get("deliveryinfo").toString());
		Map meals = JSON.parseObject(req.get("meals").toString());
//		Map meals = (Map)mealsList.get(0);

		Map custlist = JSON.parseObject(req.get("cust").toString());
		
		Map imglist = JSON.parseObject(req.get("img").toString());
		String teleType = req.get("teleType").toString();
		String isOrderNum = StringUtils.isEmpty(req.get("isOrderNum").toString())?"N":req.get("isOrderNum").toString();
		
		if(deliveryinfo.equals(null) || numberlist.equals(null) || meals.equals(null)){
			return null;
		}

		Map result = new HashMap();
		Map body = new HashMap();
		body.put("contractId", meals.get("offerid"));
		body.put("invitationCode", "");
		body.put("networkType",req.get("tele_g"));
	
		String address = "";
        String province = (String) deliveryinfo.get("province");
        String city = (String) deliveryinfo.get("city");
        String add = (String)deliveryinfo.get("add");
        if(StringUtils.isNotEmpty(province)&& StringUtils.isNotEmpty(city)&& StringUtils.isNotEmpty(add)){
			address = province + city + add;
		}else{
//			address = custlist.get("contact_addr").toString(); add by chenglin
            Map resultMap = new HashMap();
            resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
            resultMap.put(HhConstants.MESSAGE, "收货地址为空");//失败描述
            return resultMap;
		}
		Map orderDistribution = new HashMap();
		orderDistribution.put("address", address);
		orderDistribution.put("detailAddress", address);
//		orderDistribution.put("regionId", numberlist.get("city_code"));
		orderDistribution.put("consignee", deliveryinfo.get("username")==null?custlist.get("cust_name"):deliveryinfo.get("username"));
		orderDistribution.put("mobileNumber", deliveryinfo.get("tel")==null?((Map)((List)custlist.get("PROD_INST")).get(0)).get("ACC_NBR"):deliveryinfo.get("tel"));
		body.put("orderDistribution", orderDistribution);
		
		body.put("orderFlag", req.get("orderFlag"));
		body.put("orderFrom", 1);
		body.put("orderFromView", "商城");
		
		List<Map> orderNetworks = new ArrayList<Map>();
		orderNetworks.add(0,new HashMap());
		orderNetworks.get(0).put("fullName", deliveryinfo.get("username")==null?custlist.get("cust_name"):deliveryinfo.get("username"));
		orderNetworks.get(0).put("idCardAddress", deliveryinfo.get("idcardadd")==null?custlist.get("certi_addr"):deliveryinfo.get("idcardadd"));
		orderNetworks.get(0).put("idCardNum", deliveryinfo.get("idcard")==null?custlist.get("certi_nbr"):deliveryinfo.get("idcard"));
		orderNetworks.get(0).put("idCardPhoto1", imglist.get("img1"));
		orderNetworks.get(0).put("idCardPhoto2", imglist.get("img2"));
		orderNetworks.get(0).put("idCardPhoto3", imglist.get("img3"));
		orderNetworks.get(0).put("serviceNum", deliveryinfo.get("tel")==null?((Map)((List)custlist.get("prod_inst")).get(0)).get("acc_nbr"):deliveryinfo.get("tel"));
		body.put("orderNetworks", orderNetworks);
		
		String proviceName="";
		String cityName="";
		if(numberlist.get("province_name") != null){ 
			proviceName = numberlist.get("province_name").toString();
		}
		if(numberlist.get("city_name") != null){ 
			cityName = numberlist.get("city_name").toString();
		}
		Float pre_deposit = Float.parseFloat(numberlist.get("pre_deposit").toString());
		pre_deposit = pre_deposit*100;
		List<Map> orderNumbers = new ArrayList<Map>();
		orderNumbers.add(0,new HashMap());
		orderNumbers.get(0).put("city", numberlist.get("city_code"));
		orderNumbers.get(0).put("cityAddr", proviceName+cityName);
		orderNumbers.get(0).put("minConsume", numberlist.get("min_consume"));
		orderNumbers.get(0).put("nbrLevel", numberlist.get("nbr_level"));
//		orderNumbers.get(0).put("offerId", meals.get("offerid"));
		orderNumbers.get(0).put("preDeposit",String.valueOf(pre_deposit.intValue()));
		orderNumbers.get(0).put("serviceNum",numberlist.get("service_num"));
		body.put("orderNumbers", orderNumbers);
		
		Map orderOffer = new HashMap();
		orderOffer.put("offerId", meals.get("offerid"));
		orderOffer.put("offerDeposit", "0");
		body.put("orderOffer", orderOffer);
		
		List<Map> orderPayments = new ArrayList<Map>();
		int j=0;
		for(int i=0;i<pay.size();++i){
			Float payAmount = Float.parseFloat(((Map)pay.get(i)).get("payAmount").toString());
			if(payAmount>0){
			orderPayments.add(j, new HashMap());
			((Map)orderPayments.get(j)).put("payAmount", payAmount);
			((Map)orderPayments.get(j)).put("payAmountView", ((Map)pay.get(i)).get("payAmountView"));
			((Map)orderPayments.get(j)).put("payMode", ((Map)pay.get(i)).get("payMode"));
			((Map)orderPayments.get(j)).put("payModeView", ((Map)pay.get(i)).get("payModeView"));
			++j;
			}
		}
		if(orderPayments.size() !=0){
			body.put("orderPayments", orderPayments);
		} 
		body.put("orderSource", "2");
		body.put("orderSourceView", "MOBILE");
		
		int payamt = Integer.parseInt(req.get("payamt").toString());
		body.put("payAmt", payamt);
		body.put("recommendCode", "");
		
		Map goodssaleplan = JSON.parseObject(req.get("goodssaleplan").toString());
		Map yanse = JSON.parseObject(req.get("yanse").toString());
		Map rom = JSON.parseObject(req.get("rom").toString());
		Map detail = JSON.parseObject(req.get("detail").toString());
		body.put("subject", goodssaleplan.get("planname")+"_"+detail.get("goodslongname"));
		body.put("teleType", teleType);//网络类型
		
		//terminal
		Map terminal = new HashMap();
		terminal.put("goodsCode", detail.get("goodscode"));
		terminal.put("goodsName", detail.get("goodslongname"));
		terminal.put("modelId", detail.get("modelid"));
		terminal.put("skuCode", yanse.get("skuattrcode"));
		terminal.put("skuGoodsDesc", detail.get("goodslongname")
				+" "+yanse.get("skuattrshowvalue")
				+" "+rom.get("skuattrshowvalue"));
		body.put("terminal", terminal);
		
		body.put("totalAmt", payamt);
		body.put("totalAmtView", payamt*0.01);
		body.put("type", "1");
		body.put("typeView", "合约机订单");
		body.put("userAccount", custlist.get("cust_id"));
		body.put("userFlag", "1");
		body.put("channelCode", "10027");//渠道ID
		body.put("thirdChannelCode", "");//第三方渠道ID暂时为空
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
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, result.get("rspMsg"));//失败描述
		}
		return resultMap;
	}

}


