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
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.NumberListServiceServ;

import com.tydic.uniform.hh.vo.rep.NumberListVo;

import com.tydic.uniform.hh.vo.resp.NumberListResp;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
	
/**
 * <p></p>
 * @author ghp 2015年9月28日 下午7:30:43
 * @ClassName NumberListServiceImpl  号码查询接口实体
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
@Service("NumberListServiceServ")
public class NumberListServiceImpl extends AbstractService implements NumberListServiceServ{
	private static Logger log = Logger.getLogger(NumberListServiceImpl.class);
	@Override
	public Map<String, Object> numberlistService(NumberListVo numberlistvo) {
	
		
			log.info("*************************APP号码查询接口入参*************************");
			log.info("APP request str:" + JSONObject.fromObject(numberlistvo).toString());
			/*
			 * 按照BOSS文档设置接口入参
			 */
			
			List<Map> sooList = new ArrayList<Map>();
			sooList.add(0, new HashMap());
			
			Map req = new HashMap();
			req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
			req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
			if(numberlistvo.getCity()!=null){
				req.put(HhConstants.CITY, numberlistvo.getCity());
			}
			if(numberlistvo.getLast_nbr()!=null){
				req.put(HhConstants.LAST_NBR,numberlistvo.getLast_nbr());
			}
			if(numberlistvo.getNbr_level()!=null){
				req.put(HhConstants.NBR_LEVEL,numberlistvo.getNbr_level());
			}
			req.put(HhConstants.TELE_TYPE,numberlistvo.getTele_type());
			sooList.get(0).put(HhConstants.NUMBER,req);
			
			Map pub_req = new HashMap();
			pub_req.put(HhConstants.TYPE, "NUM_QRY");
			
			sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
			
			
			Map<String,Object> body = new HashMap<String,Object>();
			body.put("SOO", sooList);
//			String ll = null;
//			try {
//				ll = JSON.toJSONString(sooList);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			/*
			 * 请求接口
			 */
			log.info("*************************BOSS号码查询接口入参*************************");
			log.info("BOSS response str:" + body.toString());
			setIntefaceType(IntefaceType.CRM);
			ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001013", ResponseBean.class);

//			JSONObject resultJson = JSONObject.fromObject(rspJson);
			log.info("*************************BOSS号码查询接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
			/*
			 * 解析接口返参
			*/
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
			Map resultsooMap = (Map) resultSooList.get(0);
			Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
			String status = (String) resultResultMap.get(HhConstants.RESULT);
			
			NumberListResp  numberListResp=new NumberListResp();
		    if(HhConstants.SUCCESS.equals(status)){
		    
		    	if(resultsooMap.containsKey(HhConstants.SERVICE_INFO)){
		    		 Map<String,Object> mapInfo = new HashMap<>();
		    		 ArrayList resplist = new ArrayList();
		    		 List service_infolist=(List) resultsooMap.get(HhConstants.SERVICE_INFO);
		    		 for(int i=0; i<service_infolist.size(); i++){
		    			 Map service_infomap = (Map) service_infolist.get(i);
		    			 HashMap respmap =new HashMap();
		    			 respmap.put(HhConstantsResp.CITY_NAME, service_infomap.get(HhConstants.CITY_NAME));
		    			 respmap.put(HhConstantsResp.MIN_CONSUME, service_infomap.get(HhConstants.MIN_CONSUME));
		    			 respmap.put(HhConstantsResp.NBR_LEVEL, service_infomap.get(HhConstants.NBR_LEVEL));
		    			 respmap.put(HhConstantsResp.PRE_DEPOSIT, service_infomap.get(HhConstants.PRE_DEPOSIT));
		    			 respmap.put(HhConstantsResp.PROVICE_NAME, service_infomap.get(HhConstants.PROVICE_NAME));
		    			 respmap.put(HhConstantsResp.SERVICE_NUM, service_infomap.get(HhConstants.SERVICE_NUM));
		    			 respmap.put(HhConstantsResp.AREA_CODE, service_infomap.get(HhConstants.AREA_CODE));
		    			 respmap.put(HhConstantsResp.CITY_CODE, service_infomap.get(HhConstants.CITY_CODE));
		    			 respmap.put(HhConstantsResp.NORMAL_BALANCE, service_infomap.get(HhConstants.NORMAL_BALANCE));
		    			 respmap.put(HhConstantsResp.PROVINCE_CODE, service_infomap.get(HhConstants.PROVINCE_CODE));
		    			 resplist.add(respmap);
		    		 }
		    		 mapInfo.put(HhConstantsResp.SERVICE_INFO, resplist);
		    		 
		    		 numberListResp.setMapInfo(mapInfo);
		    	}
		    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
		    }
		    resultMap.put("numberManaResp", numberListResp);
		    log.info("*************************APP号码查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		    return resultMap;
		}

}
	

