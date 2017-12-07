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
import com.tydic.uniform.hh.service.interfaces.BaseBusinessQryServiceServ;
import com.tydic.uniform.hh.vo.rep.BaseBusinessQryVo;
import com.tydic.uniform.hh.vo.resp.BaseBusinessQryResp;
import com.tydic.uniform.hh.vo.resp.NumberListResp;

import net.sf.json.JSONObject;
/**
 * 
 * <p></p>
 * @author Administrator 2015年10月15日 下午2:53:47
 * @ClassName BaseBusinessQryServiceImpl
 * @Description 基础业务查询
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月15日
 * @modify by reason:{方法名}:{原因}
 */
@Service("BaseBusinessQryServiceServ")
public class BaseBusinessQryServiceImpl extends AbstractService implements BaseBusinessQryServiceServ {
	private static Logger log = Logger.getLogger(BaseBusinessQryServiceImpl.class);
	@Override
	public Map<String, Object> baseBusinessQryServiceServ(BaseBusinessQryVo basebusinessqryvo) {
		log.info("*************************APP号码查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(basebusinessqryvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put("MSISDN", basebusinessqryvo.getMsisdn());
		req.put("ContactChannle", basebusinessqryvo.getContactchannle());
		
		sooList.add(req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS号码查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001034", ResponseBean.class);
		
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
		
		BaseBusinessQryResp  baseBusinessQryListResp=new BaseBusinessQryResp();
		List<Map<String,Object>> depententproddtolist = new ArrayList<Map<String,Object>>();
		if (HhConstants.SUCCESS.equals(status)) {
			if (resultsooMap.containsKey(HhConstants.DEPENTENTPRODDTOLIST)) {
				Map<String,Object> mapInfo = new HashMap<String,Object>();
				 ArrayList<Map<String,Object>> resplist = new ArrayList<Map<String,Object>>();
				 List<Map<String,Object>> service_infolist=(List<Map<String, Object>>) resultsooMap.get(HhConstants.DEPENTENTPRODDTOLIST);
				 for (int i = 0; i < service_infolist.size(); i++) {
					Map<String,Object> service_infomap = (Map<String,Object>) service_infolist.get(i);
					Map<String,Object> respMap= new HashMap<String,Object>();
					respMap.put("depententProdCode", service_infomap.get("depententProdCode"));
					respMap.put("depententProdName", service_infomap.get("depententProdName"));
					respMap.put("depententProdStatus", service_infomap.get("depententProdStatus"));
					respMap.put("depententProdType", service_infomap.get("depententProdType"));
					respMap.put("effDate", service_infomap.get("effDate"));
					respMap.put("effType", service_infomap.get("effType"));
					respMap.put("expDate", service_infomap.get("expDate"));
					respMap.put("fee", service_infomap.get("fee"));
					respMap.put("ordid", service_infomap.get("ordid"));
					//判断是含有数组depententAttrDtoList
					if (service_infomap.containsKey(HhConstants.DEPENTENTATTRDTOLIST)) {
						Map<String,Object> mapInfo_s1 = new HashMap<String,Object>();
			    		 List<Map<String,Object>> resplist_s1 = new ArrayList<Map<String,Object>>();
						 List<Map<String,Object>>   resplist_s2 = (List)service_infomap.get(HhConstants.DEPENTENTATTRDTOLIST);
						for (int j = 0; j < resplist_s2.size(); j++) {
							Map<String,Object> service_infomap_s1 = (Map<String,Object>) service_infolist.get(j);
							Map<String,Object> respmap_s1 =new HashMap<String,Object>();
							respmap_s1.put("", service_infomap_s1.get(""));
							resplist_s1.add(respmap_s1);
						}
						mapInfo_s1.put("", resplist_s1);
						respMap.put("depententAttrDtoList", mapInfo_s1);
					}
					//判断是含有数组mutexProductIds
					if (service_infomap.containsKey(HhConstants.MUTEXPRODUCTIDS)) {
			    		 List<Map<String,Object>> resplist_s1 = new ArrayList<Map<String,Object>>();
						 List   resplist_s2 = (List)service_infomap.get(HhConstants.MUTEXPRODUCTIDS);
						for (int k = 0; k < resplist_s2.size(); k++) {
							Map<String,Object> service_infomap_s2 = (Map<String,Object>) service_infolist.get(k);
							resplist_s1.add(service_infomap_s2);
						}
						respMap.put("mutexProductIds", resplist_s1);
					}
					//判断是含有数组serviceAttrs
					if (service_infomap.containsKey(HhConstants.SERVICEATTRS)) {
			    		 List<Map<String,Object>> resplist_s2 = new ArrayList<Map<String,Object>>();
						 List<Map<String,Object>>   resplist_s3 = (List)service_infomap.get(HhConstants.SERVICEATTRS);
						for (int t = 0; t < resplist_s2.size(); t++) {
							Map<String,Object> service_infomap_s3 = (Map<String,Object>) service_infolist.get(t);
							Map<String,Object> respmap_s2 =new HashMap<String,Object>();
							respmap_s2.put("attr_val", service_infomap_s3.get("attr_val"));
							respmap_s2.put("is_mandetory", service_infomap_s3.get("is_mandetory"));
							respmap_s2.put("product_id", service_infomap_s3.get("product_id"));
							respmap_s2.put("service_attr_id", service_infomap_s3.get("service_attr_id"));
							respmap_s2.put("service_attr_name", service_infomap_s3.get("service_attr_name"));
							respmap_s2.put("service_id", service_infomap_s3.get("service_id"));
							resplist_s2.add(respmap_s2);
						}
						respMap.put("depententAttrDtoList", resplist_s2);
					}
					resplist.add(respMap);
				}
				 mapInfo.put(HhConstants.DEPENTENTPRODDTOLIST, resplist);
				 Map<String,Object> pub_resmap = new HashMap<String,Object>();
				 pub_resmap.put(HhConstants.TYPE, "QueryDependProd001");
				 mapInfo.put(HhConstants.PUB_RES, pub_resmap);
				 baseBusinessQryListResp.setMapInfo(mapInfo);
			}
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
		}else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
		 resultMap.put("baseBusinessQryListResp", baseBusinessQryListResp);
		 log.info("*************************APP号码查询接口出参*************************");
		 log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
}
