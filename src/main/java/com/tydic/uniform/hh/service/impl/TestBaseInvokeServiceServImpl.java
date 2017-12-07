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
import com.tydic.uniform.hh.service.interfaces.TestBaseInvokeServiceServ;
import com.tydic.uniform.hh.vo.rep.UsermsgVo;
import com.tydic.uniform.hh.vo.resp.NumberListResp;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("TestBaseInvokeServiceServ")
public class TestBaseInvokeServiceServImpl extends AbstractService implements TestBaseInvokeServiceServ{

	private static Logger log = Logger.getLogger(TestBaseInvokeServiceServImpl.class);
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getUsermsgBynumber(UsermsgVo usermsgvo) {
		log.info("*************************APP客户资料查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(usermsgvo).toString());
		/*
		 * APP入参解析
		 */
		String channei_code = usermsgvo.getChannel_code();
		String ext_system = usermsgvo.getExt_system();
		String qry_type = usermsgvo.getQry_type();
		String qry_number = usermsgvo.getQry_number();
		/*
		 * 组装能力平台入参
		 */
		JSONObject json = new JSONObject();
		Map<String,Object> cust_qry = new HashMap<String,Object>();
		cust_qry.put("CHANNEL_CODE", channei_code);
		cust_qry.put("EXT_SYSTEM", ext_system);
		cust_qry.put("QRY_TYPE", qry_type);
		cust_qry.put("QRY_NUMBER", qry_number);
		json.put("CUST_QRY", cust_qry);
		List<Object> SOO = new ArrayList<Object>();
		SOO.add(0,json);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", SOO);
		setIntefaceType(IntefaceType.CRM);
		/*
		 * 调用能力平台
		 */
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001004", ResponseBean.class);
		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		NumberListResp  numberListResp=new NumberListResp();
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get("SOO");
		Map<String,Object> resultSooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResp = (Map<String,Object>)resultSooMap.get("RESP");
		String status = (String)resultResp.get("RESULT");
		/*
		 * 请求成功，解析出参
		 */
		if(HhConstants.SUCCESS.equals(status)){
			JSONArray custlist=(JSONArray) resultSooMap.get("CUST");
	    	Map<String, Object> custmap = (Map<String, Object>) custlist.get(0);
	    	Map<String,Object> mapInfo = new HashMap<>();
	    	
	    	mapInfo.put(HhConstantsResp.CUST_NAME, custmap.get(HhConstants.CUST_NAME));
	    	
	    	numberListResp.setMapInfo(mapInfo);
			resultMap.put("11111", HhConstants.SUCCESSCODE);//成功
			resultMap.put("numberListResp", numberListResp);
		}else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
		log.info("*************************APP客户资料查询接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

}
