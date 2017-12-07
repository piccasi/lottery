package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.NumberManaServiceServ;
import com.tydic.uniform.hh.util.SoapUtil;
import com.tydic.uniform.hh.vo.rep.NumberManaVo;
import com.tydic.uniform.hh.vo.resp.NumberManaResp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



/**
 * <p></p>
 * @author ghp 2015年9月28日 下午7:00:25
 * @ClassName NumberManaServiceImpl  号码套餐查询接口实体
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
@Service("NumberManaServiceServ")
public class NumberManaServiceImpl extends AbstractService implements NumberManaServiceServ{
	
	private static Logger log = Logger.getLogger(NumberManaServiceImpl.class);

	
	public Map<String, Object> numbermanaService(NumberManaVo numbermanaVo) {
		log.info("*************************APP号码套餐查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(numbermanaVo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		
		Map req = new HashMap();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		req.put(HhConstants.QRY_NUMBER, numbermanaVo.getQry_number());
		req.put(HhConstants.OFR_MODE,numbermanaVo.getOfr_mode());
		
		sooList.get(0).put(HhConstants.CUST_QRY,req);
		
		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "NUMBER_QRY");
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
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
		log.info("*************************BOSS号码套餐查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001026", ResponseBean.class);
		
//		log.info("**************************************************"+rspJson);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS号码套餐查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSooList.get(0);
		Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		
		NumberManaResp  numberManaResp=new NumberManaResp();
	    if(HhConstants.SUCCESS.equals(status)){
	    
	    	if(resultsooMap.containsKey(HhConstants.OFFER_LIST)){
	    		 Map<String,Object> mapInfo = new HashMap<>();
	    		 ArrayList resplist = new ArrayList();
	    		 List offer_list=(List) resultsooMap.get(HhConstants.OFFER_LIST);
	    		 for(int i=0; i<offer_list.size(); i++){
	    			 Map offer_listmap = (Map) offer_list.get(i);
	    			 HashMap respmap =new HashMap();
	    			 respmap.put(HhConstantsResp.OFFER_DESC, offer_listmap.get(HhConstants.OFFER_DESC));
	    			 respmap.put(HhConstantsResp.OFFER_ID, offer_listmap.get(HhConstants.OFFER_ID));
	    			 respmap.put(HhConstantsResp.OFFER_NAME, offer_listmap.get(HhConstants.OFFER_NAME));
	    			 respmap.put(HhConstantsResp.OFR_PRICE, offer_listmap.get(HhConstants.OFR_PRICE));
	    			
	    			 resplist.add(respmap);
	    		 }
	    		 mapInfo.put(HhConstantsResp.OFFER_LIST, resplist);
	    		 numberManaResp.setMapInfo(mapInfo);
	    	}
	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
	    resultMap.put("numberManaResp", numberManaResp);
	    log.info("*************************APP号码套餐查询接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}

}
