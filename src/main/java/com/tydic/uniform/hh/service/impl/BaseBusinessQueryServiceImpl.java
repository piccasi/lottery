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
import com.tydic.uniform.hh.service.interfaces.BaseBusinessQueryServiceServ;
import com.tydic.uniform.hh.vo.rep.BaseBusinessQueryVo;

import net.sf.json.JSONObject;
@Service("BaseBusinessQueryServiceServ")
public class BaseBusinessQueryServiceImpl extends AbstractService implements BaseBusinessQueryServiceServ {

	private static Logger log = Logger.getLogger(BaseBusinessQueryServiceImpl.class);
	
	@Override
	public Map<String, Object> baseBusinessQueryMethod(BaseBusinessQueryVo basebusinessqueryvo) {
		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP代理商基础业务查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(basebusinessqueryvo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(basebusinessqueryvo).toString());
		
		String  number = basebusinessqueryvo.getMsisdn();
		if (""==number||null==number) {
			resultMap.put(HhConstants.MESSAGE, "可查询号码不能为空");//失败描述			
		}

		/*,.
		 * APP入参解析
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		sooList.get(0).put("MSISDN", basebusinessqueryvo.getMsisdn());
		sooList.get(0).put("contactChannle", "10022");
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		log.info("*************************BOSS代理商基础业务查询接口入参*************************");
		log.info("BOSS request str:" + JSONObject.fromObject(body).toString());
		/*
		 * 请求接口
		 */
		setIntefaceType(IntefaceType.CRM);
		/*
		 * 调用能力平台
		 */
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001034", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		List<Map<String,Object>> depententProdDtoList = (List<Map<String, Object>>) resultsooMap.get("DepententProdDtoList");
		
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.MSG);
		
		 if(HhConstants.SUCCESS.equals(status)){
			 resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			 resultMap.put(HhConstants.RESULT, status);
			 resultMap.put(HhConstants.MESSAGE, msg);
			 for (Map<String, Object> depententProdDtoListmap : depententProdDtoList) {
				String depententProdCode =  depententProdDtoListmap.get("depententProdCode").toString();
				String depententProdStatus =  depententProdDtoListmap.get("depententProdStatus").toString();
				String choice_status =  depententProdDtoListmap.get("CHOICE_STATUS").toString();
				Map<String,Object> CTC_MSG_resultMap = new HashMap<String,Object>();
				Map<String,Object> GSM_MSG_resultMap = new HashMap<String,Object>();
				Map<String,Object> CTC_LX_resultMap = new HashMap<String,Object>();
				Map<String,Object> GSM_LX_resultMap = new HashMap<String,Object>();
				Map<String,Object> PZSM_LX_resultMap = new HashMap<String,Object>();
				Map<String,Object> PZSB_LX_resultMap = new HashMap<String,Object>();
				if (depententProdCode.equals("TBA401")) { //中国电信来电显示
					CTC_LX_resultMap.put("depententProdCode", depententProdCode);
					CTC_LX_resultMap.put("depententProdStatus", depententProdStatus);
					CTC_LX_resultMap.put("choice_status", choice_status);
					resultMap.put("LX", CTC_LX_resultMap);
				}
				if (depententProdCode.equals("PBA004")) {//中国联通来电显示
					GSM_LX_resultMap.put("depententProdCode", depententProdCode);
					GSM_LX_resultMap.put("depententProdStatus", depententProdStatus);
					GSM_LX_resultMap.put("choice_status", choice_status);
					resultMap.put("LX", GSM_LX_resultMap);
				}
				if (depententProdCode.equals("PZS031M")) {//
					PZSM_LX_resultMap.put("depententProdCode", depententProdCode);
					PZSM_LX_resultMap.put("depententProdStatus", depententProdStatus);
					PZSM_LX_resultMap.put("choice_status", choice_status);
					resultMap.put("LX", PZSM_LX_resultMap);
				}
				if (depententProdCode.equals("PZS031B")) {//
					PZSB_LX_resultMap.put("depententProdCode", depententProdCode);
					PZSB_LX_resultMap.put("depententProdStatus", depententProdStatus);
					PZSB_LX_resultMap.put("choice_status", choice_status);
					resultMap.put("LX", PZSB_LX_resultMap);
				}
				
				if (depententProdCode.equals("TBA301")) {//中国电信点对点短信
					CTC_MSG_resultMap.put("depententProdCode", depententProdCode);
					CTC_MSG_resultMap.put("depententProdStatus", depententProdStatus);
					CTC_MSG_resultMap.put("choice_status", choice_status);		
					resultMap.put("DX", CTC_MSG_resultMap);
				}
				if (depententProdCode.equals("PBA003")) {//中国联通点对点短信
					GSM_MSG_resultMap.put("depententProdCode", depententProdCode);
					GSM_MSG_resultMap.put("depententProdStatus", depententProdStatus);
					GSM_MSG_resultMap.put("choice_status", choice_status);
					resultMap.put("DX", GSM_MSG_resultMap);
				}
			}
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.RESULT, status);
		    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
		    }
		    log.info("*************************APP代理商基础业务查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			
		return resultMap;
	}

}
