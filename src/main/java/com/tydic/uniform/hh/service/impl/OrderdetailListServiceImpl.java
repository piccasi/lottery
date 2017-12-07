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
import com.tydic.uniform.hh.service.interfaces.OrderdetailListServiceServ;
import com.tydic.uniform.hh.vo.rep.OrderdetailListVo;
import com.tydic.uniform.hh.vo.resp.OrderdetailListResp;

import net.sf.json.JSONObject;
/**
 * 
 * <p></p>
 * @author gengtian 2015年10月14日 下午3:11:21
 * @ClassName OrderdetailListServiceImpl
 * @Description TODO 订单详情查询接口实体
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
@Service("OrderdetailListServiceServ")
public class OrderdetailListServiceImpl extends AbstractService implements OrderdetailListServiceServ{
	private static Logger log = Logger.getLogger(OrderdetailListServiceImpl.class);

	@Override
	public Map<String, Object> getOrderDetailList(OrderdetailListVo orderdetailListVo) {
		log.info("*************************APP订单查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(orderdetailListVo).toString());
		
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, "10002");
		req.put(HhConstants.EXT_SYSTEM, "102");
		req.put(HhConstants.ORDER_ID, orderdetailListVo.getOrder_id());
		req.put(HhConstants.CUST_ID, orderdetailListVo.getCustid());
		
		sooList.get(0).put(HhConstants.ORDER,req);
		
		Map<String,Object> pub_req = new HashMap<String,Object>();
		pub_req.put(HhConstants.TYPE, "ORDER_DETAIL_QRY");
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS订单查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001008", ResponseBean.class);
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
		
		OrderdetailListResp orderdetailListResp = new OrderdetailListResp();
		if(HhConstants.SUCCESS.equals(status)){
			Map<String,Object> mapInfo = new HashMap<String,Object>();
			if(resultsooMap.containsKey(HhConstants.ORDER)){
				Map<String,Object> order = (Map<String, Object>)resultsooMap.get(HhConstants.ORDER);
				Map<String,Object> orderMap = new HashMap<String,Object>();
				if(order.containsKey(HhConstants.CREATE_TIME)){
					orderMap.put(HhConstantsResp.CREATE_TIME, order.get(HhConstants.CREATE_TIME));
				}
				if(order.containsKey(HhConstants.SALE_ORDER_ID)){
					orderMap.put(HhConstantsResp.SALE_ORDER_ID, order.get(HhConstants.SALE_ORDER_ID));
				}
				if(order.containsKey(HhConstants.STATUS_NAME)){
					orderMap.put(HhConstantsResp.STATUS_NAME, order.get(HhConstants.STATUS_NAME));
				}
				mapInfo.put("orderMap", orderMap);
			}
			if(resultsooMap.containsKey(HhConstants.OFFER_INST)){
				List<Map<String,Object>> offerInstList = (List<Map<String, Object>>)resultsooMap.get(HhConstants.OFFER_INST);
				List<Map<String,Object>> offer_instList = new ArrayList<Map<String,Object>>();
				for(Map<String,Object> offerinst : offerInstList){
					Map<String,Object> offer_inst = new HashMap<String,Object>();
					if(offerinst.containsKey(HhConstants.OFFER_DESC)){
						offer_inst.put(HhConstantsResp.OFFER_DESC, offerinst.get(HhConstants.OFFER_DESC));
					}
					if(offerinst.containsKey(HhConstants.OFFER_NAME)){
						offer_inst.put(HhConstantsResp.OFFER_NAME, offerinst.get(HhConstants.OFFER_NAME));
					}
					if(offerinst.containsKey(HhConstants.OFFER_TYPE_NAME)){
						offer_inst.put(HhConstantsResp.OFFER_TYPE_NAME, offerinst.get(HhConstants.OFFER_TYPE_NAME));
					}
					offer_instList.add(offer_inst);
				}
				mapInfo.put("offer_instList", offer_instList);
			}
			if(resultsooMap.containsKey(HhConstants.SALE_PROD_INST_ATTR)){
				List<Map<String,Object>> saleProdInstAttrList = (List<Map<String, Object>>)resultsooMap.get(HhConstants.SALE_PROD_INST_ATTR);
				List<Map<String,Object>> sale_prod_inst_attrList = new ArrayList<Map<String,Object>>();
				for(Map<String,Object> prod_inst : saleProdInstAttrList){
					Map<String,Object> sale_prod_inst = new HashMap<String,Object>();
					if(prod_inst.containsKey(HhConstants.ATTR_ID)){
						sale_prod_inst.put(HhConstantsResp.ATTR_ID, prod_inst.get(HhConstants.ATTR_ID));
					}
					if(prod_inst.containsKey(HhConstants.ATTR_NAME)){
						sale_prod_inst.put(HhConstantsResp.ATTR_NAME, prod_inst.get(HhConstants.ATTR_NAME));
					}
					if(prod_inst.containsKey(HhConstants.ATTR_VALUE)){
						sale_prod_inst.put(HhConstantsResp.ATTR_VALUE, prod_inst.get(HhConstants.ATTR_VALUE));
					}
					sale_prod_inst_attrList.add(sale_prod_inst);
				}
				mapInfo.put("sale_prod_inst_attrList", sale_prod_inst_attrList);
			}
			if(resultsooMap.containsKey(HhConstants.PROD_INST)){
				List<Map<String,Object>> prodInstList = (List<Map<String, Object>>)resultsooMap.get(HhConstants.PROD_INST);
				if(prodInstList.size()>0){
					Map<String,Object> prodinst = prodInstList.get(0);
					Map<String,Object> prod_inst = new HashMap<String,Object>();
					if(prodinst.containsKey(HhConstants.ACC_NBR)){
						prod_inst.put(HhConstantsResp.ACC_NBR, prodinst.get(HhConstants.ACC_NBR));
					}
					mapInfo.put("prod_inst", prod_inst);
				}
			}
			orderdetailListResp.setMapInfo(mapInfo);
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功	
		}else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
		resultMap.put("orderdetailListResp", orderdetailListResp);
		log.info("*************************APP订单查询接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}
	
}