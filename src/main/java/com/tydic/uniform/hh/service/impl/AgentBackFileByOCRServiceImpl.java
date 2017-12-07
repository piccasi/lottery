package com.tydic.uniform.hh.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.AgentBackFileByOCRServiceServ;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.vo.rep.AgentBackFileByOCRVo;

import net.sf.json.JSONObject;

@Service("AgentBackFileByOCRServiceServ")
public class AgentBackFileByOCRServiceImpl extends AbstractService implements AgentBackFileByOCRServiceServ{

	private static Logger log = Logger.getLogger(AgentBackFileByOCRServiceImpl.class);
	/**
	 * @author ghp 2015年11月28日 下午7:04:33
	 * @Method: agentBackfile 
	 * @Description: TODO
	 * @param agentbackfilebyocrvo
	 * @return 
	 * @see com.tydic.uniform.hh.service.interfaces.AgentBackFileByOCRServiceImpl#agentBackfile(com.tydic.uniform.hh.vo.rep.AgentBackFileVo) 
	 */

	@Override
	public Map<String, Object> agentBackfileByOCR(AgentBackFileByOCRVo agentbackfilebyocrvo) {
		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP代理商OCR接口返档接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentbackfilebyocrvo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(agentbackfilebyocrvo).toString());
		
		/*
		 * 获取jquery入参
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
		String picture_Path = sdf.format(new Date());
		String certiid_addr = PropertiesUtil.getPropertyValue("CERTIID_ADDR")+picture_Path+"/";
		String image1 = agentbackfilebyocrvo.getImage1();
		if (null==image1||""==image1) {
			resultMap.put(HhConstants.MESSAGE, "身份证正面照未上传");//失败描述
		}else {
			image1 = certiid_addr + image1;
		}
		String image2 = agentbackfilebyocrvo.getImage2();
		if (null==image2||""==image2) {
			resultMap.put(HhConstants.MESSAGE, "身份证侧面照未上传");//失败描述
		}else {
			image2 = certiid_addr + image2;
		}
		String date_string = agentbackfilebyocrvo.getDate_string();
		if (null==date_string||""==date_string) {
			resultMap.put(HhConstants.MESSAGE, "时间戳为空");//失败描述
		}
		/*,.
		 * APP入参解析
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		sooList.get(0).put("EXT_SYSTEM", "102");
		sooList.get(0).put("CHANNEL_CODE", "10022");
		List<Map> linkfacelist = new ArrayList<Map>();

		Map<String,String> linkfaceMap = new HashMap<String,String>();
		linkfacelist.add(0,new HashMap<>());
		linkfacelist.get(0).put("SYN_ID", date_string);
		linkfacelist.get(0).put("SELFIE_FILE", image1);
		linkfacelist.get(0).put("SELFIE_AUTO_ROTATE", "0");
		linkfacelist.get(0).put("SIDE", "front");
		linkfacelist.add(1,new HashMap<>());
		linkfacelist.get(1).put("SYN_ID", date_string);
		linkfacelist.get(1).put("SELFIE_FILE", image2); 
		linkfacelist.get(1).put("SELFIE_AUTO_ROTATE", "0");
		linkfacelist.get(1).put("SIDE", "back");
		sooList.get(0).put("LINK_FACE", linkfacelist);
		
		Map body = new HashMap();
		body.put("SOO", sooList);
		log.info("*************************BOSS号码查询接口出参*************************");
		log.info("BOSS request str:" + JSONObject.fromObject(body).toString());
		/*
		 * 请求接口
		 */
		setIntefaceType(IntefaceType.CRM);
		/*
		 * 调用能力平台
		 */
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001051", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.MSG);
		
		 if(HhConstants.SUCCESS.equals(status)){
			 resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			 resultMap.put(HhConstants.RESULT, status);
			 resultMap.put(HhConstants.CERTI_NBR, resultResultMap.get("CERTI_NBR"));
			 resultMap.put(HhConstants.NAME, resultResultMap.get("NAME"));
			 resultMap.put(HhConstants.ADDRESS, resultResultMap.get("ADDRESS"));
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.RESULT, status);
		    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
		    }
		    log.info("*************************APP代理商返档接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			
		return resultMap;
	}
}
