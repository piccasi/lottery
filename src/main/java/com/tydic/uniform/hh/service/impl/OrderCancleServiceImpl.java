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
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.OrderCancleServiceServ;
import com.tydic.uniform.hh.service.interfaces.OrderdetailListServiceServ;
import com.tydic.uniform.hh.vo.rep.OrderCancleVo;
import com.tydic.uniform.hh.vo.rep.OrderdetailListVo;
import com.tydic.uniform.hh.vo.resp.OrderCancleResp;
import com.tydic.uniform.hh.vo.resp.OrderdetailListResp;
/**
 * 
 * <p></p>
 * @author gengtian 2015年10月14日 下午3:11:21
 * @ClassName OrderCancleServiceImpl
 * @Description TODO 订单取消接口实体
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
@Service("OrderCancleServiceServ")
public class OrderCancleServiceImpl  extends AbstractService implements OrderCancleServiceServ{
	private static Logger log = Logger.getLogger(OrderdetailListServiceImpl.class);

	@Override
	public Map<String, Object> cancleOrder(OrderCancleVo orderCancleVo) {
		log.info("*************************APP订单取消接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(orderCancleVo).toString());
		
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		sooList.add(1, new HashMap<String,Object>());
		
		
//		"CUST_ID":"31000000028377",
//		"EXT_ORDER_ID":"-1",
//		"ORDER_TYPE":"2000",
//		"SALE_ORDER_ID":"100071451887025198",
//		"SEL_IN_ORG_ID":"10002"
		List<Map<String,Object>> reqList = new ArrayList<Map<String,Object>>();
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, "10002");
		req.put(HhConstants.EXT_SYSTEM, "102");
		req.put("CUST_ID", orderCancleVo.getCust_id());
		req.put("EXT_ORDER_ID", orderCancleVo.getExt_order_id());
		req.put("ORDER_TYPE", orderCancleVo.getOrder_type());
		req.put("SALE_ORDER_ID", orderCancleVo.getSale_order_id());
		req.put("SEL_IN_ORG_ID", orderCancleVo.getSel_in_org_id());
		reqList.add(req);
		sooList.get(0).put("SALE_ORDER_INST",reqList);
		
		Map<String,Object> pub_req = new HashMap<String,Object>();
		pub_req.put(HhConstants.TYPE, "SAVE_SALE_ORDER_INST");
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		
//		"AUTH_FLAG":"1",
//		"CUST_ID":"31000000028377",
//		"NEW_FLAG":"1",
//		"SALE_ORDER_ID":"$581007$"
		List<Map<String,Object>> req1List = new ArrayList<Map<String,Object>>();
		Map<String,Object> req1 = new HashMap<String,Object>();
		req1.put("CUST_ID", orderCancleVo.getCust_id());
		req1.put("AUTH_FLAG", orderCancleVo.getAuth_flag());
		req1.put("NEW_FLAG", orderCancleVo.getNew_flag());
		req1.put("SALE_ORDER_ID", orderCancleVo.getSale_order_id_id());
		req1List.add(req1);
		sooList.get(1).put("SALE_CUST",req1List);
		
		Map<String,Object> pub_req1 = new HashMap<String,Object>();
		pub_req1.put(HhConstants.TYPE, "SAVE_SALE_CUST");
		sooList.get(1).put(HhConstants.PUB_REQ,pub_req1);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS订单取消接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001010", ResponseBean.class);
		log.info("*************************BOSS订单取消接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		
		OrderCancleResp orderCancleResp = new OrderCancleResp();
		Map<String,Object> mapInfo = new HashMap<String,Object>();
		if(HhConstants.SUCCESS.equals(status)){
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功	
			mapInfo.put("sale_order_id", resultsooMap.get("SALE_ORDER_ID"));
		}else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
		orderCancleResp.setMapInfo(mapInfo);
		resultMap.put("ordercancleresp", orderCancleResp);
		log.info("*************************APP订单取消接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}
	
}