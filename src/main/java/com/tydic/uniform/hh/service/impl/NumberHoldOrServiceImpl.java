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
import com.tydic.uniform.hh.service.interfaces.NumberHoldOrServiceServ;
import com.tydic.uniform.hh.util.SoapUtil;
import com.tydic.uniform.hh.vo.rep.NumberHoldOrVo;
import com.tydic.uniform.hh.vo.resp.NumberListResp;

import net.sf.json.JSONObject;

/**
 * <p></p>
 * @author ghp  2015年9月28日 下午8:21:44
 * @ClassName NumberHoldOrServiceImpl  号码预占/释放实体
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
@Service("NumberHoldOrServiceServ")
public class NumberHoldOrServiceImpl extends AbstractService implements NumberHoldOrServiceServ{
	private static Logger log = Logger.getLogger(NumberHoldOrServiceImpl.class);
	@Override
	public Map<String, Object> numberholdor(NumberHoldOrVo numberholdorov) {
		
		
		log.info("*************************APP号码预占/释放接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(numberholdorov).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		
		Map req = new HashMap();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		req.put(HhConstants.RES_NBR, numberholdorov.getRes_nbr());
		req.put(HhConstants.CUST_ID, numberholdorov.getCust_id());
		req.put(HhConstants.OPT_TYPE, numberholdorov.getOpt_type());
		req.put(HhConstants.RES_CODE, numberholdorov.getRes_code());
		
		sooList.get(0).put(HhConstants.NUMBER,req);
		
		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "NUM_CHANGE");
		
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
		log.info("*************************BOSS号码预占/释放接口入参*************************");
		log.info("BOSS response str:" + body.toString());
//		SoapUtil soapUilt = new SoapUtil();
//		String rspJson = soapUilt.SoapWebService("/ServiceBus/source/num/number002", "/esb/number002", "108", ll);
		
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001012", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(bean);
		log.info("*************************BOSS号码预占/释放接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSooList.get(0);
		Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		
	
	    if(HhConstants.SUCCESS.equals(status)){
	    
	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
	  
	    log.info("*************************APP号码预占/释放接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}
	
}
