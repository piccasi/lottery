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
import com.tydic.uniform.hh.service.interfaces.AgentFaceBackFileServiceServ;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.vo.rep.AgentBackFileByOCRVo;

import net.sf.json.JSONObject;

@Service("AgentFaceBackFileServiceServ")
public class AgentFaceBackFileServiceImpl extends AbstractService implements AgentFaceBackFileServiceServ{
	private static Logger log = Logger.getLogger(AgentBackFileByOCRServiceImpl.class);
	/**
	 * @author ghp 2016年7月14日10:15:28
	 * @Method: agentFaceBackfile 
	 * @Description: TODO
	 * @param agentbackfilebyocrvo
	 * @return 
	 */
	@Override
	public Map<String, Object> agentFaceBackfile(AgentBackFileByOCRVo agentbackfilebyocrvo) {

		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP代理商OCR人脸对比接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentbackfilebyocrvo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(agentbackfilebyocrvo).toString());
		
		/*
		 * 获取jquery入参
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
		String picture_Path = sdf.format(new Date());
		String certiid_addr = PropertiesUtil.getPropertyValue("CERTIID_ADDR")+picture_Path+"/";
		String name = agentbackfilebyocrvo.getCust_name(); //姓名
		if (null==name||""==name) {
			resultMap.put(HhConstants.MESSAGE, "身份证正面照未上传");//失败描述
		}
		String certi_nbr = agentbackfilebyocrvo.getCerti_nbr(); //身份张编号
		if (null==certi_nbr||""==certi_nbr) {
			resultMap.put(HhConstants.MESSAGE, "身份证正面照未上传");//失败描述
		}
		String certi_addr = agentbackfilebyocrvo.getCerti_addr(); //身份证地址
		if (null==certi_addr||""==certi_addr) {
			resultMap.put(HhConstants.MESSAGE, "身份证正面照未上传");//失败描述
		}
		String image3 = agentbackfilebyocrvo.getImage3();
		if (null==image3||""==image3) {
			resultMap.put(HhConstants.MESSAGE, "身份证正面照未上传");//失败描述
		}else {
			image3 = certiid_addr + image3;
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
		sooList.get(0).put("CHANNEL_CODE", "10015");
		List<Map> linkfacelist = new ArrayList<Map>();

		Map<String,String> linkfaceMap = new HashMap<String,String>();
		linkfacelist.add(0,new HashMap<>());
		linkfacelist.get(0).put("SYN_ID", date_string);
		linkfacelist.get(0).put("CERTI_NBR", certi_nbr);
		linkfacelist.get(0).put("NAME", name);
		linkfacelist.get(0).put("SELFIE_FILE", image3);
		linkfacelist.get(0).put("SELFIE_AUTO_ROTATE", "0");
		sooList.get(0).put("LINK_FACE", linkfacelist);
		
		Map<String,String> typeMap = new HashMap<String,String>();
		typeMap.put("TYPE", "LINK_FACE_SELFIELD");
		
		sooList.get(0).put("PUB_REQ", typeMap);
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
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001052", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.MSG);
		
		 if(HhConstants.SUCCESS.equals(status)){
			 resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			 resultMap.put(HhConstants.RESULT, status);
			 resultMap.put(HhConstants.MESSAGE, msg);
			 resultMap.put(HhConstants.CONFIDENCE, resultResultMap.get("CONFIDENCE"));
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.RESULT, status);
		    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
		    }
		    log.info("*************************APP代理商OCR人脸对比接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			
		return resultMap;
	}

}
