package com.tydic.uniform.hh.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.BusinessType;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.constant.NumberStatus;
import com.tydic.uniform.hh.service.interfaces.AgentOfferChangeServiceServ;
import com.tydic.uniform.hh.service.interfaces.AgentOrderServiceServ;
import com.tydic.uniform.hh.service.interfaces.IdentifyCodeService;
import com.tydic.uniform.hh.vo.rep.AgentOrderVo;
import com.tydic.uniform.hh.vo.rep.IdentifyCodeVo;
import com.tydic.uniform.hh.vo.rep.MealChangeOfferVo;
import com.tydic.uniform.hh.vo.rep.MealChangeVo;
import com.tydic.uniform.hh.vo.rep.UsermsgVo;

import net.sf.json.JSONObject;

@Service("AgentOfferChangeServiceServ")
public class AgentOfferChangeServiceImpl extends AbstractService implements AgentOfferChangeServiceServ {
	private static Logger log = Logger.getLogger(AgentOfferChangeServiceImpl.class);

	@Autowired
	private AgentOrderServiceServ agentOrderServiceServ;
	
	@Autowired
	private IdentifyCodeService identifyCodeService;

	@Override
	public String getNumberOfferChange(UsermsgVo usermsgvo) {
		log.info("*************************APP 套餐变更查询信息接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(usermsgvo).toString());
		
		if(StringUtils.isEmpty(usermsgvo.getQry_number())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "手机号码为空");
		}
		AgentOrderVo agentOrderVo = new AgentOrderVo();
		agentOrderVo.setMobile_170(usermsgvo.getQry_number());
		Map<String, Object> userInfo = agentOrderServiceServ.getUserInfo(agentOrderVo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		{	
			//校验号码是否支持查询
			if(HhConstants.SUCCESSCODE.equals(userInfo.get(HhConstants.CODE))){
				Map<String, Object> cust = (Map<String, Object>)userInfo.get("CUST");
				Map<String, Object> prodInst = ((List<Map<String, Object>>)cust.get("PROD_INST")).get(0);
				String status = prodInst.get("STATUS_CD").toString();
				if(!"100000".equals(status)){
					return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "该号码状态非在用，不可办理此业务");
				}
				
				List<Map<String, Object>> prodOfferList = ((List<Map<String, Object>>)cust.get("PROD_OFFER_INST"));
				if(!(prodOfferList!=null && prodOfferList.size()>0)){
					return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "用户不存在套餐");
				}
				for(Map<String, Object> prodOffer : prodOfferList){
					if(prodOffer.get("OFFER_TYPE")!=null && "11".equals(prodOffer.get("OFFER_TYPE").toString())){
						
						try {
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = new Date();
							//开始时间
							String effDate = prodOffer.get("EFF_DATE").toString();
							if(date.getTime()>(df.parse(effDate)).getTime()){
								//套餐名称
								resultMap.put("offerName", prodOffer.get("PROD_OFFER_NAME").toString());
								//套餐说明
								resultMap.put("offerDesc", prodOffer.get("OFR_DESC").toString());
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				//会员标识
				resultMap.put("custId", cust.get("CUST_ID").toString());
				//手机号码
				resultMap.put("mobile", cust.get("MOBILE_170").toString());
				//号码状态
				resultMap.put("status", NumberStatus.getDescByCode(Integer.parseInt(prodInst.get("STATUS_CD").toString())));
				//号码归属地
				resultMap.put("attribution", prodInst.get("LOCAL_NET_NAME").toString());
			}else{
				return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, userInfo.get(HhConstants.MESSAGE).toString());
			}
		}
		
		log.info("*************************APP 套餐变更查询信息接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(DesEncryptUtil.decrypt(JsonResponse.toSuccessResult(resultMap))));
		return JsonResponse.toSuccessResult(resultMap);
	}

	
	@Override
	public String getNumberOfferChangeQuery(UsermsgVo usermsgvo) {
		log.info("*************************APP 可订购套餐查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(usermsgvo).toString());
		
		IdentifyCodeVo codeVo = new IdentifyCodeVo();
		codeVo.setMobile_170(usermsgvo.getQry_number());
		codeVo.setType(BusinessType.OFFER);
		codeVo.setCode(usermsgvo.getCode());
		
		boolean checkResult = identifyCodeService.checkCodeSync(codeVo);
		if(!checkResult){
			log.info("验证码错误！");
			return JsonResponse.toErrorResult(CODE.VALIDATE_ERROR, "验证码错误");
		}
		
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String,Object> resultsooMap = queryOffer(usermsgvo);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		
		Map<String,Object> mapInfo = new HashMap<>();
		if(HhConstants.SUCCESS.equals(status)){
			
			if(resultsooMap.containsKey(HhConstants.RESP)){
				
				ArrayList<Map<String,Object>> resplist = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> service_infolist=(List<Map<String,Object>>) resultsooMap.get(HhConstants.OFR_LIST);
				for(int i=0; i<service_infolist.size(); i++){
					Map<String,Object> service_infomap = (Map<String,Object>) service_infolist.get(i);
					Map<String,Object> respmap =new HashMap<String,Object>();
					respmap.put(HhConstantsResp.OFR_DESC, service_infomap.get(HhConstants.OFR_DESC));
					respmap.put(HhConstantsResp.OFR_ID, service_infomap.get(HhConstants.OFR_ID));
					respmap.put(HhConstantsResp.OFR_MODE, service_infomap.get(HhConstants.OFR_MODE));
					respmap.put(HhConstantsResp.OFR_NAME, service_infomap.get(HhConstants.OFR_NAME));
					respmap.put(HhConstantsResp.OFR_PRICE, service_infomap.get(HhConstants.OFR_PRICE));
					resplist.add(respmap);
				}
				mapInfo.put(HhConstantsResp.OFR_LIST, resplist);
				
			}
			
			
		}else if(HhConstants.ERROR.equals(status)){
			return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, result);
		}
		resultMap.put("orderMealQryResp", mapInfo);

		
		log.info("*************************APP 可订购套餐查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(DesEncryptUtil.decrypt(JsonResponse.toSuccessResult(resultMap))));
		return JsonResponse.toSuccessResult(resultMap);
	}
	
	@Override
	public String getNumberOfferChangeHandle(MealChangeVo mealchangevo) {
		
		log.info("*************************APP套餐变更接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(mealchangevo).toString());
		
		UsermsgVo usermsgvo = new UsermsgVo();
		usermsgvo.setChannel_code(mealchangevo.getOrg_id());
		usermsgvo.setQry_number(mealchangevo.getAcc_nbr());
		Map<String,Object> resultOfferSooMap = queryOffer(usermsgvo);
		
		Map<String,Object> offerResultResultMap = (Map<String,Object>) resultOfferSooMap.get(HhConstants.RESP);
		String offerStatus = (String) offerResultResultMap.get(HhConstants.RESULT);
		String offerResult = (String) offerResultResultMap.get(HhConstants.MSG);

		//套餐是否具有办理权限
		Boolean offerSatus = false;
		if(HhConstants.SUCCESS.equals(offerStatus)){
			List<Map<String,Object>> service_infolist=(List<Map<String,Object>>) resultOfferSooMap.get(HhConstants.OFR_LIST);
			for(int i=0; i<service_infolist.size(); i++){
				Map<String,Object> service_infomap = (Map<String,Object>) service_infolist.get(i);
				
				List<MealChangeOfferVo> offer_instList = mealchangevo.getSale_offer_instlist();
				MealChangeOfferVo mealChangeOfferVo = (MealChangeOfferVo) offer_instList.get(0);
				
				if(service_infomap.get(HhConstants.OFR_ID).equals(mealChangeOfferVo.getOffer_id())){
					offerSatus = true;
					break;
				}
			}
		}else{
			return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, offerResult);
		}
		
		if(!offerSatus){
			log.info("该套餐不具备变更权限");
			return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "当前套餐不具备办理权限！");
		}
		
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());
		sooList.add(1, new HashMap<String, Object>());
		sooList.add(2, new HashMap<String, Object>());
		Map<String, Object> req = new HashMap<String, Object>();
		Map<String, Object> req_s1 = new HashMap<String, Object>();
		
		req.put(HhConstants.SALE_ORDER_ID, "$581007$");
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		req.put(HhConstants.CUST_ID, mealchangevo.getCust_id());
		req.put(HhConstants.CHANNEL_CODE, mealchangevo.getOrg_id());
		req.put(HhConstants.EXT_ORDER_ID, "-1");
		sooList.get(0).put(HhConstants.SALE_ORDER_INST, req);
		
		Map<String, Object> pub_req = new HashMap<String, Object>();
		pub_req.put(HhConstants.TYPE, "SAVE_SALE_ORDER_INST");
		sooList.get(0).put(HhConstants.PUB_REQ, pub_req);
		
		req_s1.put(HhConstants.PROD_INST_ID, "107107156");
		req_s1.put(HhConstants.SALE_ORDER_ID, "$581007$");
		req_s1.put(HhConstants.ACC_NBR, mealchangevo.getAcc_nbr());
		req_s1.put(HhConstants.MAIN_FLAG, "1");
		req_s1.put(HhConstants.PRODUCT_ID, "1360");
		req_s1.put(HhConstants.NEW_FLAG, "1");
		req_s1.put(HhConstants.SERVICE_OFFER_ID, "888");
		sooList.get(1).put(HhConstants.SALE_PROD_INST, req_s1);
		
		Map<String, Object> pub_req_s1 = new HashMap<String, Object>();
		pub_req_s1.put(HhConstants.TYPE, "SAVE_SALE_PROD_INST");
		sooList.get(1).put(HhConstants.PUB_REQ, pub_req_s1);
		
		List<Map<String, Object>> list_pub = new ArrayList<Map<String, Object>>();
		List<MealChangeOfferVo> offer_instList = mealchangevo.getSale_offer_instlist();
		for (int i = 0; i < offer_instList.size(); i++) {
			MealChangeOfferVo mealChangeOfferVo = (MealChangeOfferVo) offer_instList.get(i);
			if (mealChangeOfferVo != null) {
				Map<String, Object> req_s2 = new HashMap<String, Object>();
				
				req_s2.put(HhConstants.OFFER_INST_ID, "123");
				req_s2.put(HhConstants.OFFER_ID, mealChangeOfferVo.getOffer_id());
				req_s2.put(HhConstants.NEW_FLAG, "1");
				req_s2.put(HhConstants.SALE_ORDER_ID, "$581007$");
				req_s2.put(HhConstants.SERVICE_OFFER_ID, "250");
				list_pub.add(req_s2);
			}
		}
		
		sooList.get(2).put(HhConstants.SALE_OFFER_INST, list_pub);
		
		Map<String, Object> pub_req_s2 = new HashMap<String, Object>();
		pub_req_s2.put(HhConstants.TYPE, "SAVE_SALE_OFFER_INST");
		sooList.get(2).put(HhConstants.PUB_REQ, pub_req_s2);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS套餐变更接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001025", ResponseBean.class);
		
		// JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS套餐变更接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		 */
		
		List<Map<String, Object>> resultSooList = (List<Map<String, Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		String resultString = "";
		if (HhConstants.SUCCESS.equals(status)) {
			resultString = JsonResponse.toSuccessResult(null);
		} else if (HhConstants.ERROR.equals(status)) {
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, HhConstants.ERRORMESSAGE);
			if (result != null && !result.equals("")){
				resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, result);
			}
			if(result.equals("ZSMART-CC-00000:当前用户为待开通状态，不允许办理:改套餐业务")){
				resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "当前用户为待开通状态，不允许办理:改套餐业务");
			}
		}
		log.info("*************************APP套餐变更询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(DesEncryptUtil.decrypt(resultString)).toString());

		return resultString;
	}

	public Map<String,Object> queryOffer(UsermsgVo usermsgvo){

		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, usermsgvo.getChannel_code());
		req.put(HhConstants.DEVICE_NUMBER, usermsgvo.getQry_number());
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		req.put(HhConstants.SERVICE_CODE, "888");
		req.put(HhConstants.OFR_MODE, "01");

		
		sooList.get(0).put(HhConstants.DNR_CHECK, req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS 可订购套餐查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001029", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS 可订购套餐查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		return  resultsooMap;
	}
}
