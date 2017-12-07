package com.tydic.uniform.hh.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.OcrServiceServ;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.vo.rep.OcrInfoVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("OcrServiceServ")
public class OcrServiceImpl extends AbstractService implements OcrServiceServ {
	
	private static Logger log = Logger.getLogger(OcrServiceImpl.class);
	
	@Override
	public String getOcrFace(OcrInfoVo ocrInfoVo) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time = sdf.format(new Date());
		
		log.debug("*************************APP人脸识别接口入参*************************");
		log.debug("APP request str:" + JSONObject.fromObject(ocrInfoVo).toString());
		
		if("".equals(ocrInfoVo.getCertNbr())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "身份证号码不能为空");
		}else if("".equals(ocrInfoVo.getName())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "姓名不能为空");
		}else if("".equals(ocrInfoVo.getSelfieFile())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "活体图片不能为空");
		}
		
		String rootPath = PropertiesUtil.getPropertyValue("ocr.identy.address");
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
        String dateString = formatter.format(currentTime);
        rootPath+=dateString+"/";
        
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("EXT_SYSTEM", "102");
		map.put("CHANNEL_CODE", "10002");
		Map<String,String> map2 = new HashMap<String,String>();
		List<Map> identity = new ArrayList<Map>();
		map2.put("SYN_ID", time);
		map2.put("CERTI_NBR", ocrInfoVo.getCertNbr());
		map2.put("NAME", ocrInfoVo.getName());
		map2.put("SELFIE_FILE", rootPath + ocrInfoVo.getSelfieFile());
		map2.put("SELFIE_AUTO_ROTATE", ocrInfoVo.getSelfieAutoRotate());
		identity.add(map2);
		map.put("LINK_FACE", identity);
		Map<String,String> map3 = new HashMap<String,String>();
		map3.put("TYPE", "LINK_FACE_SELFIELD");
		map.put("PUB_REQ", map3);
		List<Map> mapList = new ArrayList<Map>();
		mapList.add(map);
		
		
		JSONArray sooList =  new JSONArray();

		JSONObject object = new JSONObject();
		object.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		object.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEM_VALUE);
		
		JSONArray LINK_FACE = new JSONArray();
		JSONObject linkface = new JSONObject();
		linkface.put("SYN_ID", time);
		linkface.put("CERTI_NBR", ocrInfoVo.getCertNbr());
		linkface.put("NAME", ocrInfoVo.getName());
		linkface.put("SELFIE_FILE", rootPath + ocrInfoVo.getSelfieFile());
		linkface.put("SELFIE_AUTO_ROTATE", ocrInfoVo.getSelfieAutoRotate());
		LINK_FACE.add(linkface);
		object.put("LINK_FACE", LINK_FACE);
		JSONObject PUB_REQ = new JSONObject();
		PUB_REQ.put("TYPE", "LINK_FACE_SELFIELD");
		object.put("PUB_REQ", PUB_REQ);

		sooList.add(object);
		
		JSONObject body = new JSONObject();
		body.put("SOO", sooList);
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS人脸识别接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001052", ResponseBean.class);
		
		log.info("*************************BOSS人脸识别接口出参*************************");
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
		if(HhConstants.SUCCESS.equals(status)){
			JSONObject res = new JSONObject();
			res.put("CONFIDENCE", resultResultMap.get("CONFIDENCE"));
		    return JsonResponse.toSuccessResult(res);
	    }else{
	    	return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, result);
	    }
		
	}

}
