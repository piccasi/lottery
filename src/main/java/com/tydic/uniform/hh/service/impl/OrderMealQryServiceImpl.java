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
import com.tydic.uniform.hh.service.interfaces.OrderMealQryServiceServ;
import com.tydic.uniform.hh.vo.rep.OrderMealQryVo;
import com.tydic.uniform.hh.vo.resp.OrderMealQryResp;

import net.sf.json.JSONObject;
/**
 * OrderMealQry
 * <p></p>
 * @author Administrator 2015年10月16日 下午4:05:02
 * @ClassName OrderMealQryServiceImpl
 * @Description 可订购套餐查询
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月16日
 * @modify by reason:{方法名}:{原因}
 */
@Service("OrderMealQryServiceServ")
public class OrderMealQryServiceImpl extends AbstractService implements OrderMealQryServiceServ {
	private static Logger log = Logger.getLogger(OrderMealQryServiceImpl.class);

	@Override
	public Map<String, Object> ordermealqryserviceserv(OrderMealQryVo ordermealqryvo) {
//		log.info("*************************可订购套餐查询接口入参*************************");
//		log.info("APP request str:" + JSONObject.fromObject(ordermealqryvo).toString());
//		/*
//		 * 按照BOSS文档设置接口入参
//		 */
//		
//		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
//		sooList.add(0, new HashMap<String,Object>());
//		Map<String,Object> req = new HashMap<String,Object>();
//		req.put(HhConstants.CHANNEL_CODE, ordermealqryvo.getChannel_code());
//		req.put(HhConstants.DEVICE_NUMBER, ordermealqryvo.getDevice_number());
//		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
//		req.put(HhConstants.SERVICE_CODE, ordermealqryvo.getService_code());
//		if(ordermealqryvo.getCust_flag()!=null
//				&&(ordermealqryvo.getCust_flag().equals("0")||ordermealqryvo.getCust_flag().equals("1"))){
//			req.put("OFR_MODE", "05");//员工套餐
//		}
//		
//		sooList.get(0).put(HhConstants.DNR_CHECK, req);
//		
//		Map<String,Object> body = new HashMap<String,Object>();
//		body.put("SOO", sooList);
//		/*
//		 * 请求接口
//		 */
//		log.info("*************************可订购套餐查询接口入参*************************");
//		log.info("BOSS response str:" + body.toString());
//		setIntefaceType(IntefaceType.CRM);
//		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001029", ResponseBean.class);
//		
////		JSONObject resultJson = JSONObject.fromObject(rspJson);
//		log.info("*************************可订购套餐查询接口出参*************************");
//		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
//		/*
//		 * 解析接口返参
//		*/
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//		
//		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
//		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
//		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
//		String status = (String) resultResultMap.get(HhConstants.RESULT);
//		String result = (String) resultResultMap.get(HhConstants.MSG);
//		
//		OrderMealQryResp orderMealQryResp = new OrderMealQryResp();
//		 Map<String,Object> mapInfo = new HashMap<>();
//		 if(HhConstants.SUCCESS.equals(status)){
//			    
//		    	if(resultsooMap.containsKey(HhConstants.RESP)){
//		    		
//		    		 ArrayList<Map<String,Object>> resplist = new ArrayList<Map<String,Object>>();
//		    		 List service_infolist=(List) resultsooMap.get(HhConstants.OFR_LIST);
//		    		 for(int i=0; i<service_infolist.size(); i++){
//		    			 Map<String,Object> service_infomap = (Map<String,Object>) service_infolist.get(i);
//		    			 Map<String,Object> respmap =new HashMap<String,Object>();
//		    			 respmap.put(HhConstantsResp.OFR_DESC, service_infomap.get(HhConstants.OFR_DESC));
//		    			 respmap.put(HhConstantsResp.OFR_ID, service_infomap.get(HhConstants.OFR_ID));
//		    			 respmap.put(HhConstantsResp.OFR_MODE, service_infomap.get(HhConstants.OFR_MODE));
//		    			 respmap.put(HhConstantsResp.OFR_NAME, service_infomap.get(HhConstants.OFR_NAME));
//		    			 respmap.put(HhConstantsResp.OFR_PRICE, service_infomap.get(HhConstants.OFR_PRICE));
//		    			 resplist.add(respmap);
//		    		 }
//		    		 mapInfo.put(HhConstantsResp.OFR_LIST, resplist);
//		    		 
//		    	}
//		    		 
//		    		 orderMealQryResp.setMapInfo(mapInfo);
//		    		 resultMap.put(HhConstants.CODE,HhConstants.SUCCESSCODE);
//		    		 resultMap.put(HhConstants.MSG,"查询成功");
//		    		 resultMap.put(HhConstants.ORDERID,"");
//		    		 resultMap.put(HhConstants.RESULT, HhConstants.SUCCESS);
//		    }else if(HhConstants.ERROR.equals(status)){
//		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
//				resultMap.put(HhConstants.MESSAGE, result);//失败描述
//		    }
//		    resultMap.put("orderMealQryResp", orderMealQryResp);
//		    log.info("*************************APP可订购套餐查询接口出参*************************");
//			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
//		return resultMap;
		/**
		 * 关闭上面接口
		 */
		Map<String,Object> resultMap = new HashMap<String,Object>();
    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		resultMap.put(HhConstants.MESSAGE, "禁止代理商用户进行可订购套餐查询");//失败描述
		
		return resultMap;
	}
}
