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
import com.tydic.uniform.hh.service.interfaces.MemberRechargeServiceServ;
import com.tydic.uniform.hh.vo.rep.MemberRechargeVo;
import com.tydic.uniform.hh.vo.resp.MemberRechargeResp;

import net.sf.json.JSONObject;
/**
 * MemberRecharge
 * <p></p>
 * @author Administrator 2015年10月18日 下午2:07:16
 * @ClassName MemberRechargeServiceImpl
 * @Description 现金充值
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月18日
 * @modify by reason:{方法名}:{原因}
 */
@Service("MemberRechargeServiceServ")
@SuppressWarnings("unchecked")
public class MemberRechargeServiceImpl extends AbstractService implements MemberRechargeServiceServ {
	private static Logger log = Logger.getLogger(MemberRechargeServiceImpl.class);
	@Override
	public Map<String, Object> memberrechargeserviceserv(MemberRechargeVo memberrechargevo) {
		log.info("*************************APP现金充值接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(memberrechargevo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> req = new HashMap<String,Object>();
		if (memberrechargevo.getBank_code()!=null) {
			req.put(HhConstants.BANK_CODE, memberrechargevo.getBank_code());
		}
		if (memberrechargevo.getBank_serial_nbr()!=null) {
			req.put(HhConstants.BANK_SERIAL_NBR, memberrechargevo.getBank_serial_nbr());
		}
		if (memberrechargevo!=null) {
			req.put(HhConstants.PAY_TYPE, memberrechargevo.getPay_type());
		}
		req.put(HhConstants.MEMBER_ID, memberrechargevo.getMember_id());
		req.put(HhConstants.PAY_AMOUNT, memberrechargevo.getPay_amount());
		req.put(HhConstants.PAY_METHOD, memberrechargevo.getPay_method());
		req.put(HhConstants.REQUEST_ID, memberrechargevo.getRequest_id());
		req.put(HhConstants.STAFF_ID, HhConstants.STAFF_IDMAVLUE);
		req.put(HhConstants.SYSTEM_ID, HhConstants.EXT_SYSTEM_VALUE);
		sooList.get(0).put(HhConstants.RECHARGE_INFO, req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		/*
		 * 请求接口
		 */
		log.info("*************************现金充值接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003002", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************现金充值接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		
		MemberRechargeResp memberRechargeResp = new MemberRechargeResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			   Map<String,Object> cashmap = new HashMap<String,Object>();
			   if (resultsooMap.containsKey(HhConstants.CASH_INFO)) {
				Map<String,Object> cd=(Map<String, Object>) resultsooMap.get(HhConstants.CASH_INFO);
				cashmap.put("cash", cd.get("CASH"));
			   }
			   memberRechargeResp.setMapInfo(cashmap);
		    	 
		    	resultMap.put(HhConstants.CODE,HhConstants.SUCCESSCODE);
		    	resultMap.put(HhConstants.MSG,"查询成功");
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
		    }
		    resultMap.put("memberRechargeResp", memberRechargeResp);
		    log.info("*************************APP现金充值接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
}
