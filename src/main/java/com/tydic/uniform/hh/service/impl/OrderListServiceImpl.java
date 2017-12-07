package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.OrderListServiceServ;
import com.tydic.uniform.hh.vo.rep.OrderListVo;
import com.tydic.uniform.hh.vo.resp.OrderListResp;

import net.sf.json.JSONObject;
/**
 * 
 * <p></p>
 * @author gengtian 2015年10月13日 上午11:36:17
 * @ClassName OrderListServiceImpl
 * @Description TODO 订单查询接口实体
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月13日
 * @modify by reason:{方法名}:{原因}
 */
@Service("OrderListServiceServ")
public class OrderListServiceImpl extends AbstractService implements OrderListServiceServ{
	private static Logger log = Logger.getLogger(OrderListServiceImpl.class);

	@Override
	public Map<String, Object> getOrderList(OrderListVo orderListVo) {
		log.info("*************************APP订单查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(orderListVo).toString());
		
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, "10022");
		req.put(HhConstants.EXT_SYSTEM, "10002");
		req.put(HhConstants.CUST_ID, orderListVo.getCust_id());
		req.put(HhConstants.PAGE, orderListVo.getPage());
		req.put(HhConstants.PAGE_NO, orderListVo.getPage_no());
		req.put("BATCH_FLAG", "1");
		req.put(HhConstants.PROCESS_STS, orderListVo.getProcess_sts());
		
		sooList.get(0).put(HhConstants.ORDER,req);
		
		Map<String,Object> pub_req = new HashMap<String,Object>();
		pub_req.put(HhConstants.TYPE, "ORDER_QRY");
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS订单查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001007", ResponseBean.class);
		log.info("*************************BOSS订单查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		
		OrderListResp orderListResp = new OrderListResp();
		
		/*List<Map<String, Object>> pendingOrderList = new ArrayList<Map<String, Object>>();//待处理订单列表		
		List<Map<String, Object>> finishedOrderList = new ArrayList<Map<String, Object>>();//已完成订单列表
		List<Map<String, Object>> canceledOrderList = new ArrayList<Map<String, Object>>();//已取消订单列表
		 */		
		List<Map<String, Object>> orderListWithStatus = new ArrayList<Map<String, Object>>();
		if(HhConstants.SUCCESS.equals(status)){
			Map<String,Object> mapInfo = new HashMap<String,Object>();
			if(resultsooMap.containsKey(HhConstants.ORDER)){			
				List<Map<String, Object>> orderList = (List<Map<String, Object>>) resultsooMap.get(HhConstants.ORDER);
				for(int i=orderList.size()-1;i>=0;i--){
					Map<String,Object> orderMap = new HashMap<String,Object>();
					Map<String, Object> order = orderList.get(i);
					if(order.containsKey(HhConstants.ORDER_ID)){
						orderMap.put(HhConstantsResp.ORDER_ID,order.get(HhConstants.ORDER_ID));
					}
					if(order.containsKey(HhConstants.CREATE_TIME)){
						orderMap.put(HhConstantsResp.CREATE_TIME,order.get(HhConstants.CREATE_TIME));
					}
					if(order.containsKey(HhConstants.ORDER_PRICE)){
						orderMap.put(HhConstantsResp.ORDER_PRICE,order.get(HhConstants.ORDER_PRICE));
					}
					if(order.containsKey(HhConstants.STATUS_CD)){
						orderMap.put(HhConstantsResp.STATUS_CD,order.get(HhConstants.STATUS_CD));
					}
					if(order.containsKey(HhConstants.PROCESS_STS)){
						orderMap.put(HhConstantsResp.PROCESS_STS,order.get(HhConstants.PROCESS_STS));
					}
					if(order.containsKey(HhConstants.ORDER_TYPE)){
						orderMap.put(HhConstantsResp.ORDER_TYPE,order.get(HhConstants.ORDER_TYPE));
					}
					if(order.containsKey(HhConstants.PAY_STATUS)){
						orderMap.put(HhConstantsResp.PAY_STATUS,order.get(HhConstants.PAY_STATUS));
					}
					if(order.containsKey(HhConstants.STATUS_NAME)){
						orderMap.put(HhConstantsResp.STATUS_NAME,order.get(HhConstants.STATUS_NAME));
					}
					if(order.containsKey(HhConstants.DEVICE_NUMBER)){
						orderMap.put(HhConstantsResp.DEVICE_NUMBER,order.get(HhConstants.DEVICE_NUMBER));
					}
					if(order.containsKey(HhConstants.LOG_NBR)){
						orderMap.put(HhConstantsResp.LOG_NBR,order.get(HhConstants.LOG_NBR));
					}
					if(order.containsKey(HhConstants.LOG_CAMP)){
						orderMap.put(HhConstantsResp.LOG_CAMP,order.get(HhConstants.LOG_CAMP));
					}
					if(order.containsKey(HhConstants.ORDER_DESC)){
						orderMap.put(HhConstantsResp.ORDER_DESC,order.get(HhConstants.ORDER_DESC));
					}
					
					/*if(order.containsKey(HhConstants.PROCESS_STS) && order.get(HhConstants.PROCESS_STS).equals("00")){
						pendingOrderList.add(orderMap);
					}else if(order.containsKey(HhConstants.PROCESS_STS) && order.get(HhConstants.PROCESS_STS).equals("01")){
						finishedOrderList.add(orderMap);
					}else if(order.containsKey(HhConstants.PROCESS_STS) && order.get(HhConstants.PROCESS_STS).equals("02")){
						canceledOrderList.add(orderMap);
					}*/
					
					orderListWithStatus.add(orderMap);
				}
				/*mapInfo.put("pendingOrderList", pendingOrderList);
				mapInfo.put("pendingOrderNum", pendingOrderList.size());//待处理订单数
				mapInfo.put("finishedOrderList", finishedOrderList);
				mapInfo.put("finishedOrderNum", finishedOrderList.size());//已完成订单数
				mapInfo.put("canceledOrderList", canceledOrderList);
				mapInfo.put("canceledOrderNum", canceledOrderList.size());//已取消订单数*/		
				mapInfo.put("orderListWithStatus", orderListWithStatus);
			}
			orderListResp.setMapInfo(mapInfo);
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功	
		}else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
		resultMap.put("orderListResp", orderListResp);
		log.info("*************************APP订单查询接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}
	
}