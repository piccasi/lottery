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
import com.tydic.uniform.hh.service.interfaces.BaseBusinessChangeServiceServ;
import com.tydic.uniform.hh.vo.rep.BaseBusinessChangeSerAttrDtoListVo;
import com.tydic.uniform.hh.vo.rep.BaseBusinessChangeSerDtoListVo;
import com.tydic.uniform.hh.vo.rep.BaseBusinessChangeVo;
import com.tydic.uniform.hh.vo.resp.BaseBusinessChangeResp;
import com.tydic.uniform.hh.vo.resp.MealChangeResp;

import net.sf.json.JSONObject;
@Service("BaseBusinessChangeServiceServ")
public class BaseBusinessChangeServiceImpl extends AbstractService implements BaseBusinessChangeServiceServ {
	private static Logger log = Logger.getLogger(BaseBusinessChangeServiceImpl.class);

	@Override
	public Map<String, Object> baseBusinessChangeServiceServ(BaseBusinessChangeVo basebusinesschangevo) {

		log.info("*************************APP号码查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(basebusinesschangevo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put("JDOrderID", basebusinesschangevo.getJdorderid());
		req.put("MSISDN", basebusinesschangevo.getMsisdn());
		req.put("ContactChannle", basebusinesschangevo.getContactchannle());
		List<BaseBusinessChangeSerDtoListVo> serviceDtoList = basebusinesschangevo.getServicedtolist();
		List<Map<String,Object>> serviceDtoListempty= new ArrayList<Map<String,Object>>();
		if (serviceDtoList!=null && !serviceDtoList.isEmpty()) {
			Map<String,Object> serviceDtoListMap= new HashMap<String,Object>();
			for (int i = 0; i < serviceDtoList.size(); i++) {
				BaseBusinessChangeSerDtoListVo mas = (BaseBusinessChangeSerDtoListVo)serviceDtoList.get(i);
				if (mas!=null) {
					serviceDtoListMap.put("Action", mas.getAction());
					if (mas.getEfftype()!=null) {
						serviceDtoListMap.put("EffType", mas.getEfftype());
					}
					if (mas.getExpduration()!=null) {
						serviceDtoListMap.put("ExpDuration", mas.getExpduration());
					}
					if (mas.getNumber()!=null) {
						serviceDtoListMap.put("Number", mas.getNumber());
					}
					List<BaseBusinessChangeSerAttrDtoListVo> baseBusinessChangeSerAttrDtoListVoList = mas.getServiceattrdtolist();
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					for (int j = 0; j < baseBusinessChangeSerAttrDtoListVoList.size(); j++) {
						BaseBusinessChangeSerAttrDtoListVo baseBusinessChangeSerAttrDtoListVo = baseBusinessChangeSerAttrDtoListVoList.get(j);
						if (baseBusinessChangeSerAttrDtoListVo!=null) {
							Map<String,Object> req_s2 = new HashMap<String,Object>();
							req_s2.put("ServiceAttrCode", baseBusinessChangeSerAttrDtoListVo.getServiceattrcode());
							req_s2.put("AttrValue", baseBusinessChangeSerAttrDtoListVo.getAttrvalue());
							list.add(req_s2);
						}
					}
					serviceDtoListMap.put("ServiceAttrDtoList", list);
					serviceDtoListMap.put("ServiceCode", mas.getServicecode());
					serviceDtoListMap.put("TimeUnit", mas.getTimeunit());
					serviceDtoListempty.add(serviceDtoListMap);
				}
			}
			
		}else {
			req.put("ServiceDtoList", "[]");
		}
		req.put("ServiceDtoList", serviceDtoListempty);
		req.put("UserEventCode", basebusinesschangevo.getUsereventcode());
		
		sooList.add(req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS号码查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001033", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS号码查询接口出参*************************");
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
		
		BaseBusinessChangeResp baseBusinessChangeResp = new BaseBusinessChangeResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    resultMap.put(HhConstants.RESULT, status);
			    resultMap.put(HhConstants.MSG, result);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
		    }
		    resultMap.put("baseBusinessChangeResp", baseBusinessChangeResp);
		    log.info("*************************APP号码查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
}
