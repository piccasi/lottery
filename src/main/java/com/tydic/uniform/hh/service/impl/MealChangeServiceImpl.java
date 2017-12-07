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
import com.tydic.uniform.hh.service.interfaces.MealChangeServiceServ;
import com.tydic.uniform.hh.util.ValidateCodeUtil;
import com.tydic.uniform.hh.vo.rep.MealChangeOfferVo;
import com.tydic.uniform.hh.vo.rep.MealChangeVo;
import com.tydic.uniform.hh.vo.resp.MealChangeResp;

import net.sf.json.JSONObject;

/**
 * <p>
 * </p>
 * 
 * @author Administrator 2015年10月13日 下午3:43:52
 * @ClassName MealChangeServiceImpl
 * @Description 套餐变更
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月13日
 * @modify by reason:{方法名}:{原因}
 */
@SuppressWarnings("unchecked")
@Service("MealChangeServiceServ")
public class MealChangeServiceImpl extends AbstractService implements MealChangeServiceServ {
	private static Logger log = Logger.getLogger(MealChangeServiceImpl.class);

	@Override
	public Map<String, Object> mealChangeService(MealChangeVo mealchangevo) {
		// 判断手机验证码是否正确
//		boolean flag = ValidateCodeUtil.isSuccess(mealchangevo.getNumber(), mealchangevo.getValidatecode());
		Map<String, Object> resultMap = new HashMap<String, Object>();
//		if (flag) {
			log.info("*************************APP套餐变更接口入参*************************");
			log.info("APP request str:" + JSONObject.fromObject(mealchangevo).toString());

			List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
			sooList.add(0, new HashMap<String, Object>());
			sooList.add(1, new HashMap<String, Object>());
			sooList.add(2, new HashMap<String, Object>());
			Map<String, Object> req = new HashMap<String, Object>();
			Map<String, Object> req_s1 = new HashMap<String, Object>();

			req.put(HhConstants.SALE_ORDER_ID, mealchangevo.getSale_order_id());
			req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
			req.put(HhConstants.CUST_ID, mealchangevo.getCust_id());
			req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
			req.put(HhConstants.EXT_ORDER_ID, mealchangevo.getExt_order_id());
			sooList.get(0).put(HhConstants.SALE_ORDER_INST, req);

			Map<String, Object> pub_req = new HashMap<String, Object>();
			pub_req.put(HhConstants.TYPE, "SAVE_SALE_ORDER_INST");
			sooList.get(0).put(HhConstants.PUB_REQ, pub_req);

			req_s1.put(HhConstants.PROD_INST_ID, mealchangevo.getProd_inst_id());
			req_s1.put(HhConstants.SALE_ORDER_ID, mealchangevo.getSale_order_id());
			req_s1.put(HhConstants.ACC_NBR, mealchangevo.getAcc_nbr());
			req_s1.put(HhConstants.MAIN_FLAG, mealchangevo.getMain_flag());
			req_s1.put(HhConstants.PRODUCT_ID, mealchangevo.getProduct_id());
			req_s1.put(HhConstants.NEW_FLAG, mealchangevo.getNew_flag());
			req_s1.put(HhConstants.SERVICE_OFFER_ID, mealchangevo.getService_offer_id());
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

					req_s2.put(HhConstants.OFFER_INST_ID, mealChangeOfferVo.getOffer_inst_id());
					req_s2.put(HhConstants.OFFER_ID, mealChangeOfferVo.getOffer_id());
					req_s2.put(HhConstants.NEW_FLAG, mealChangeOfferVo.getNew_flag());
					req_s2.put(HhConstants.SALE_ORDER_ID, mealChangeOfferVo.getSale_order_id());
					req_s2.put(HhConstants.SERVICE_OFFER_ID, mealChangeOfferVo.getService_offer_id());
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

			List<Map<String, Object>> resultSooList = (List<Map<String, Object>>) bean.getBody().get(0)
					.get(HhConstants.SOO);
			Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
			Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
			String status = (String) resultResultMap.get(HhConstants.RESULT);
			String result = (String) resultResultMap.get(HhConstants.MSG);

			MealChangeResp mealChangeResp = new MealChangeResp();

			if (HhConstants.SUCCESS.equals(status)) {
				resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
				resultMap.put(HhConstants.RESULT, status);
				resultMap.put(HhConstants.MESSAGE, result);
			} else if (HhConstants.ERROR.equals(status)) {
				resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码

				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
				if (result != null && !result.equals(""))
					resultMap.put(HhConstants.MESSAGE, result);
				if(result.equals("ZSMART-CC-00000:当前用户为待开通状态，不允许办理:改套餐业务"))
					resultMap.put(HhConstants.MESSAGE, "当前用户为待开通状态，不允许办理:改套餐业务");
					
			}
			resultMap.put("mealChangeResp", mealChangeResp);
			log.info("*************************APP套餐变更询接口出参*************************");
			log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
//		} else {
//			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
//			resultMap.put(HhConstants.MESSAGE, "验证码有误");// 失败描述
//		}
		return resultMap;
	}

}
