package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.MealChangeServiceServ;
import com.tydic.uniform.hh.service.interfaces.MealListServiceServ;
import com.tydic.uniform.hh.vo.rep.MealChangeOfferVo;
import com.tydic.uniform.hh.vo.rep.MealChangeVo;
import com.tydic.uniform.hh.vo.rep.MealListVo;
import com.tydic.uniform.hh.vo.resp.MealChangeResp;

import net.sf.json.JSONObject;
/**
 * 
 * @author DIC_YINSHUANGHUA
 *
 */
@SuppressWarnings("unchecked")
@Service("MealListServiceServ")
public class MealListServiceImpl extends AbstractService implements MealListServiceServ{
	private static Logger log = Logger.getLogger(MealListServiceImpl.class);
	@Override
	public Map<String, Object> mealListService(MealListVo mealListvo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP号码套餐查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(mealListvo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(mealListvo).toString());
		
		List<Map> result = new ArrayList<Map>();
		Map req = new HashMap();
		req.put("TYPE", "NUMBER_QRY");
		Map req1 = new HashMap();
		req1.put("QRY_NUMBER", mealListvo.getQry_number());
		if (mealListvo.getOfr_mode()==null||mealListvo.getOfr_mode()=="") {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, "代理商编码不能为空");//失败描述
		}
		req1.put(HhConstants.CHANNEL_CODE, mealListvo.getOrg_id());
		req1.put("OFR_MODE",mealListvo.getOfr_mode());
		Map body = new HashMap();
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		sooList.get(0).put("PUB_REQ",req);
		sooList.get(0).put("CUST_QRY",req1);
		body.put("SOO", sooList);
		setIntefaceType(IntefaceType.CRM);
		log.info("*************************APP号码套餐查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		ResponseBean bean=(ResponseBean) this.appApiInvoke(body, "SC1001026", ResponseBean.class);
		result = (List<Map>)((Map)((List<Map>) bean.getBody().get(0).get("SOO")).get(0)).get("OFFER_LIST");
		if(StringUtils.isNotEmpty(mealListvo.getCust_flag()) && (mealListvo.getCust_flag().equals("0")||mealListvo.getCust_flag().equals("1"))){
			req1.put("OFR_MODE","05");
			sooList.get(0).put("CUST_QRY",req1);
			body.put("SOO", sooList);
			ResponseBean bean2=(ResponseBean) this.appApiInvoke(body, "SC1001026", ResponseBean.class);
			List<Map> template2 = (List<Map>)((Map)((List<Map>) bean2.getBody().get(0).get("SOO")).get(0)).get("OFFER_LIST");
			result.addAll(template2);
		}
		
		MealChangeResp mealChangeResp = new MealChangeResp();
		   if(result.size()>0){
			    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    resultMap.put(HhConstants.RESULT, "0");
			    resultMap.put(HhConstants.MSG, "查询成功");
		    }else{
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
		    }
		    resultMap.put("mealListResp", result);
		    log.info("*************************APP号码套餐查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
}
