package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.ResourceConversionServiceServ;
import com.tydic.uniform.hh.vo.rep.ResourceConversionVo;
import com.tydic.uniform.hh.vo.resp.ResourceConversionResp;

/**
 * 
 * <p></p>
 * @author Administrator 2015年10月14日 下午12:01:29
 * @ClassName ResourceConversionServiceImpl
 * @Description 资源转换
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
@Service("ResourceConversionServiceServ")
public class ResourceConversionServiceImpl  extends AbstractService implements ResourceConversionServiceServ{
	private static Logger log = Logger.getLogger(ResourceConversionServiceImpl.class);
	/**
	 * 资源转换
	 */
	@Override
	public Map<String, Object> resourceConversion(ResourceConversionVo resourceConversionVo) {

		log.info("*************************资源转换接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(resourceConversionVo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put("MemberId", resourceConversionVo.getMemberid());
		req.put("TransRuleId", resourceConversionVo.getTransruleid());
		req.put("OutBalance", resourceConversionVo.getOutbalance());
		req.put("OutBalanceTypeId", resourceConversionVo.getOutbalancetypeid());
	
		sooList.get(0).put("ResTrans",req);
		Map<String,Object> req_s2 = new HashMap<String,Object>();
		req_s2.put(HhConstants.TYPE, "ResTrans");
		sooList.get(0).put(HhConstants.PUB_REQ,req_s2);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003013", ResponseBean.class);
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
		String result = (String) resultResultMap.get(HhConstants.BILLMSG);
		
		ResourceConversionResp resourceConversionResp = new ResourceConversionResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    resultMap.put(HhConstants.MESSAGE, result);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
				if(result!=null)
					resultMap.put(HhConstants.MESSAGE, result);
		    }
		    resultMap.put("resourceDonationResp", resourceConversionResp);
		return resultMap;
	}
	/**
	 * 资源转换类型查询
	 */
	@Override
	public Map<String, Object> queryConversionType() {
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		
		Map<String,Object> req_s2 = new HashMap<String,Object>();
		req_s2.put(HhConstants.TYPE, "ResTypeQry");
		sooList.get(0).put(HhConstants.PUB_REQ,req_s2);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003012", ResponseBean.class);
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		ResourceConversionResp resourceConversionResp = new ResourceConversionResp();
		 Map<String,Object> mapInfo = new HashMap<>();
		 if(HhConstants.SUCCESS.equals(status)){
			    
		    	if(resultsooMap.containsKey(HhConstants.RESP)){
		    		
		    		 ArrayList<Map<String,Object>> resplist = new ArrayList<Map<String,Object>>();
		    		 Map<String,Object> service_infoMap=(Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		    		 if (service_infoMap.containsKey(HhConstants.RESTRANSTYPE)) {
						List<Map<String,Object>> resTransTypelist = (List<Map<String, Object>>) service_infoMap.get(HhConstants.RESTRANSTYPE);
						
						for (int i = 0; i < resTransTypelist.size(); i++) {
							 Map<String,Object> resTransTypemap = (Map<String,Object>) resTransTypelist.get(i);
			    			 Map<String,Object> respmap =new HashMap<String,Object>();
			    			 respmap.put(HhConstantsResp.RESTRANSRATIO, resTransTypemap.get(HhConstants.RESTRANSRATIO));
			    			 respmap.put(HhConstantsResp.RESTYPEID, resTransTypemap.get(HhConstants.RESTYPEID));
			    			 respmap.put(HhConstantsResp.RESTYPENAME, resTransTypemap.get(HhConstants.RESTYPENAME));
			    			 resplist.add(respmap);
						}
					}
		    		 mapInfo.put(HhConstantsResp.RESTRANSTYPE, resplist);
		    		 
		    	}
		    		 
		    	resourceConversionResp.setMapInfo(mapInfo);
		    	 
		    	resultMap.put(HhConstants.CODE,HhConstants.SUCCESSCODE);
		    	resultMap.put(HhConstants.MSG,"查询成功");

	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			if(result!=null)
				resultMap.put(HhConstants.MESSAGE, result);
	    }
		 resultMap.put("resourceConversionResp", resourceConversionResp);
		 log.info("*************************资源转换类型查询接口出参*************************");
		 log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

}
