package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.UsermsgServiceServ;
import com.tydic.uniform.hh.util.DateUtil;
import com.tydic.uniform.hh.vo.rep.UserInfoVo;
import com.tydic.uniform.hh.vo.rep.UsermsgVo;
import com.tydic.uniform.hh.vo.resp.NumberListResp;

/**
 * <p></p>
 * @author ghp 2015年10月7日 下午9:06:46
 * @ClassName UsermsgServiceImpl  客户资料查询接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月7日
 * @modify by reason:{方法名}:{原因}
 */
@Service("UsermsgServiceServ")

public class UsermsgServiceImpl extends AbstractService implements UsermsgServiceServ{
	private static Logger log = Logger.getLogger(UsermsgServiceImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getUsermsgByCustid(UsermsgVo usermsgvo) {
		log.info("*************************APP客户资料查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(usermsgvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		
		Map req = new HashMap();
		req.put(HhConstants.CHANNEL_CODE, "10022");
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		if(usermsgvo.getCust_id()!=""&&usermsgvo.getCust_id()!=null){
			req .put(HhConstants.CUST_ID, usermsgvo.getCust_id());
		}else if(usermsgvo.getQry_type()!=""&&usermsgvo.getQry_type()!=null){
			req .put(HhConstants.QRY_TYPE, usermsgvo.getQry_type());
			req .put(HhConstants.QRY_NUMBER, usermsgvo.getQry_number());
		}
		
		sooList.get(0).put(HhConstants.CUST_QRY,req);
		
		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "CUST_QUERY");
		
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
		log.info("*************************BOSS客户资料查询接口入入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001004", ResponseBean.class);

		Map<String,Object> map = returnMapvalue(bean);

		return  map;
	
	}

//	@Override
//	public Map<String, Object> getUsermsgBynumber(UsermsgVo usermsgvo) {
//		log.info("*************************APP客户资料查询接口入参*************************");
//		log.info("APP request str:" + JSONObject.fromObject(usermsgvo).toString());
//		/*
//		 * 按照BOSS文档设置接口入参
//		 */
//		
//		List<Map> sooList = new ArrayList<Map>();
//		sooList.add(0, new HashMap());
//		
//		Map req = new HashMap();
//		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
//		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
//		req .put(HhConstants.QRY_TYPE, usermsgvo.getQry_type());
//		req .put(HhConstants.QRY_NUMBER, usermsgvo.getQry_number());
//		
//		sooList.get(0).put(HhConstants.CUST_QRY,req);
//		
//		Map pub_req = new HashMap();
//		pub_req.put(HhConstants.TYPE, "CUST_QUERY");
//		
//		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
//		
//		Map<String,Object> body = new HashMap<String,Object>();
//		body.put("SOO", sooList);
//		
//		
////		String ll = null;
////		try {
////			ll = JSON.toJSONString(sooList);
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		/*
//		 * 请求接口
//		 */
//		log.info("*************************BOSS客户资料查询接口入入参*************************");
//		log.info("BOSS response str:" + body.toString());
//		setIntefaceType(IntefaceType.CRM);
//		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001004", ResponseBean.class);
//		
//
//		Map<String,Object> map = returnMapvalue(bean);
//
//		return  map;
//	}
	
	public Map<String,Object> returnMapvalue(ResponseBean bean){
//		JSONObject resultJson = JSONObject.fromObject(string);
		log.info("*************************BOSS客户资料查询接口入出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSooList.get(0);
		Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		
		NumberListResp  numberListResp=new NumberListResp();
	    if(HhConstants.SUCCESS.equals(status)){
	    	List custlist=(List) resultsooMap.get("CUST");
	    	Map<String, Object> custmap = (Map) custlist.get(0);
	    	Map<String,Object> mapInfo = new HashMap<>();
	    	mapInfo.put(HhConstantsResp.AUTH_FLAG, custmap.get(HhConstants.AUTH_FLAG));
	    	mapInfo.put(HhConstantsResp.BIRTHDATE, custmap.get(HhConstants.BIRTHDATE));
	    	mapInfo.put(HhConstantsResp.BLACKLIST, custmap.get(HhConstants.BLACKLIST));
	    	mapInfo.put(HhConstantsResp.CERTI_ADDR, custmap.get(HhConstants.CERTI_ADDR));
	    	mapInfo.put(HhConstantsResp.CERTI_NBR, custmap.get(HhConstants.CERTI_NBR));
	    	mapInfo.put(HhConstantsResp.CERTI_TYPE, custmap.get(HhConstants.CERTI_TYPE));
	    	mapInfo.put(HhConstantsResp.CHANNEL_CODE, custmap.get(HhConstants.CHANNEL_CODE));
	    	mapInfo.put(HhConstantsResp.CHANNEL_NAME, custmap.get(HhConstants.CHANNEL_NAME));
	    	mapInfo.put(HhConstantsResp.CONTACT_ADDR, custmap.get(HhConstants.CONTACT_ADDR));
	    	mapInfo.put(HhConstantsResp.CUST_ID, custmap.get(HhConstants.CUST_ID));
	    	mapInfo.put(HhConstantsResp.CUST_FLAG, custmap.get(HhConstants.CUST_FLAG));
	    	mapInfo.put(HhConstantsResp.CUST_NAME, custmap.get(HhConstants.CUST_NAME));
	    	mapInfo.put(HhConstantsResp.CUST_NBR, custmap.get(HhConstants.CUST_NBR));
	    	mapInfo.put(HhConstantsResp.CUST_TYPE, custmap.get(HhConstants.CUST_TYPE));
	    	mapInfo.put(HhConstantsResp.CUST_VIP_LEVEL, custmap.get(HhConstants.CUST_VIP_LEVEL));
	    	mapInfo.put(HhConstantsResp.EMAIL, custmap.get(HhConstants.EMAIL));
	    	mapInfo.put(HhConstantsResp.JOIN_DATE, custmap.get(HhConstants.JOIN_DATE));
	    	mapInfo.put(HhConstantsResp.MOBILE, custmap.get(HhConstants.MOBILE));
	    	mapInfo.put(HhConstantsResp.MOBILE_170, custmap.get(HhConstants.MOBILE_170));
	    	mapInfo.put(HhConstantsResp.NICK_NAME, custmap.get(HhConstants.NICK_NAME));
	    	mapInfo.put(HhConstantsResp.PAY_PWD, custmap.get(HhConstants.PAY_PWD));
	    	mapInfo.put(HhConstantsResp.SEX, custmap.get(HhConstants.SEX));
	    	mapInfo.put(HhConstantsResp.CASH, custmap.get("CASH"));
	    	
	    	
			List<Object> prod_instlist=(List<Object>) custmap.get(HhConstants.PROD_INST);
			ArrayList<Object> prod_instlistresp = new ArrayList<Object>();
			if(prod_instlist.size()>0){
		    	for(int i=0; i<prod_instlist.size(); i++){
			    	Map<String, Object> prod_instmap=(Map<String, Object>) prod_instlist.get(i);
			    	Map<String, Object> prod_instmapresp = new HashMap<String, Object>();
			    	prod_instmapresp.put(HhConstantsResp.ACC_NBR, prod_instmap.get(HhConstants.ACC_NBR));
			    	prod_instmapresp.put(HhConstantsResp.MAIN_FLAG, prod_instmap.get(HhConstants.MAIN_FLAG));
			    	prod_instmapresp.put(HhConstantsResp.PRODUCT_ID, prod_instmap.get(HhConstants.PRODUCT_ID));
			    	prod_instmapresp.put(HhConstantsResp.PRODUCT_NAME, prod_instmap.get(HhConstants.PRODUCT_NAME));
			    	prod_instmapresp.put(HhConstantsResp.PROD_INST_ID, prod_instmap.get(HhConstants.PROD_INST_ID));
			    	prod_instmapresp.put(HhConstantsResp.STATUS_CD, prod_instmap.get(HhConstants.STATUS_CD));
			    	prod_instlistresp.add(prod_instmapresp);
		    	}
			}
			mapInfo.put(HhConstantsResp.PROD_INST, prod_instlistresp);
	    	
	    	
	    	List prod_offer_instlist=(List) custmap.get(HhConstants.PROD_OFFER_INST);
	    	ArrayList prod_offer_instlistresp = new ArrayList();
	    	if(prod_offer_instlist.size()>0){
		    	for(int i=0; i<prod_offer_instlist.size(); i++){
		    		Map<String, Object> prod_offer_instmap=(Map<String, Object>) prod_offer_instlist.get(i);
			    	Map<String, Object> prod_offer_instmapresp = new HashMap();
			    	prod_offer_instmapresp.put(HhConstantsResp.OFFER_CLASS, prod_offer_instmap.get(HhConstants.OFFER_CLASS));
			    	prod_offer_instmapresp.put(HhConstantsResp.OFFER_TYPE, prod_offer_instmap.get(HhConstants.OFFER_TYPE));
			    	prod_offer_instmapresp.put(HhConstantsResp.PROD_INST_ID, prod_offer_instmap.get(HhConstants.PROD_INST_ID));
			    	prod_offer_instmapresp.put(HhConstantsResp.PROD_OFFER_ID, prod_offer_instmap.get(HhConstants.PROD_OFFER_ID));
			    	prod_offer_instmapresp.put(HhConstantsResp.PROD_OFFER_INST_ID, prod_offer_instmap.get(HhConstants.PROD_OFFER_INST_ID));
			    	prod_offer_instmapresp.put(HhConstantsResp.PROD_OFFER_NAME, prod_offer_instmap.get(HhConstants.PROD_OFFER_NAME));
			    	prod_offer_instmapresp.put(HhConstantsResp.EXP_DATE, prod_offer_instmap.get(HhConstants.EXP_DATE));
			    	prod_offer_instmapresp.put(HhConstantsResp.OFR_DESC, prod_offer_instmap.get(HhConstants.OFR_DESC));
			    	prod_offer_instmapresp.put(HhConstantsResp.MARKET_PRICE, prod_offer_instmap.get(HhConstants.MARKET_PRICE));
			    	prod_offer_instmapresp.put(HhConstantsResp.EFF_DATE, prod_offer_instmap.get(HhConstants.EFF_DATE));
			    	
			    	
			    	//通信合伙人
			    	//先过滤基础包
					if(null!=prod_offer_instmap.get("OFFER_TYPE") && prod_offer_instmap.get("OFFER_TYPE").equals("11")
							&&null!=prod_offer_instmap.get("EFF_DATE")&&null!=prod_offer_instmap.get("EXP_DATE"))
					{
						Date effDate = DateUtil.parseDate(prod_offer_instmap.get("EFF_DATE").toString(),"yyyy-MM-dd");
						Date expDate = DateUtil.parseDate(prod_offer_instmap.get("EXP_DATE").toString(),"yyyy-MM-dd");
						Date date = new Date();
						if(IsTimeIn(date, effDate, expDate))
						{
							mapInfo.put("MARKET_PRICE", Double.parseDouble(prod_offer_instmap.get("MARKET_PRICE").toString()));
						}
					}
					//通信合伙人end
					
			    	prod_offer_instlistresp.add(prod_offer_instmapresp);
		    	}
		    
	    	}
	    	mapInfo.put(HhConstantsResp.PROD_OFFER_INST, prod_offer_instlistresp);

	    	numberListResp.setMapInfo(mapInfo);

	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			if(result!=null&&!result.equals(""))
				resultMap.put(HhConstants.MESSAGE,result);//失败描述
	    }
	    resultMap.put("numberListResp", numberListResp);
	    log.info("*************************APP客户资料查询接口入出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}

	@Override
	public Map<String, Object> getUserInfo(UserInfoVo userInfoVo) {
		setIntefaceType(IntefaceType.CRM);
		
		Map result = new HashMap();
		Map body = new HashMap();
		
		Map req = new HashMap();
		if(StringUtils.isNotEmpty(userInfoVo.getCustId())){
			req.put("QRY_NUMBER", userInfoVo.getCustId());
			req.put("QRY_TYPE", "4");
		}else if(StringUtils.isNotEmpty(userInfoVo.getLoginAcct())){
			req.put("QRY_NUMBER", userInfoVo.getLoginAcct());
			req.put("QRY_TYPE", "3");
		}else if(StringUtils.isNotEmpty(userInfoVo.getCardNo())){
			req.put("QRY_NUMBER", userInfoVo.getCardNo());
			req.put("QRY_TYPE", "2");
		}else if(StringUtils.isNotEmpty(userInfoVo.getMobile())){
			req.put("QRY_NUMBER", userInfoVo.getMobile());
			req.put("QRY_TYPE", "1");
		}
		req.put("CHANNEL_CODE", HhConstants.CHANNEL_CODEVALUE);
		req.put("EXT_SYSTEM", HhConstants.EXT_SYSTEMVALUE);
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		sooList.get(0).put("CUST_QRY",req);
		body.put("SOO", sooList);
		
		Map custResult = new HashMap();
		ResponseBean bean=(ResponseBean) this.appApiInvoke(body, "SC1001004", ResponseBean.class);
		if(null != (List)((Map)((List)((Map)bean.getBody().get(0)).get("SOO")).get(0)).get("CUST")){
			custResult = (Map)((List)((Map)((List)((Map)bean.getBody().get(0)).get("SOO")).get(0)).get("CUST")).get(0);
		}else{
			result.put("RESULT", "1");
			result.put("MSG", "未找到您的客户信息");
			return result;
		}
		return custResult;
	}
	
	
	private static boolean IsTimeIn(Date time,Date begin,Date end){
		return time.getTime()>=begin.getTime() && time.getTime()<=end.getTime();
	}
}
