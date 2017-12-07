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
import com.tydic.uniform.hh.service.interfaces.ContractPhoneServiceServ;
import com.tydic.uniform.hh.vo.rep.ContractPhoneVo;
import com.tydic.uniform.hh.vo.resp.ContractPhoneResp;
/**
 * <p>
 * </p>
 * 
 * @author ghp 2015年9月29日 下午6:16:12
 * @ClassName ContractPhoneServiceImpl 合约机号码查询接口实体
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
@Service("ContractPhoneServiceServ")
public class ContractPhoneServiceImpl extends AbstractService implements
		ContractPhoneServiceServ {
	public static Logger log = Logger
			.getLogger(ContractMobileServiceImpl.class);

	@Override
	public Map<String, Object> qryContractPhone(ContractPhoneVo contractPhoneVo) {
		log.info("*************************APP合约号码接口入参*************************");
		log.info("APP request str:"
				+ JSONObject.fromObject(contractPhoneVo).toString());
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		
		Map req = new HashMap();
		req.put("CHANNEL_CODE", HhConstants.CHANNEL_CODEVALUE);
		req.put("EXT_SYSTEM",  HhConstants.EXT_SYSTEMVALUE);
		req.put("STAFF_ID", contractPhoneVo.getStaff_id());
		req.put("CITY", contractPhoneVo.getCity());
		req.put("NBR_LEVEL", contractPhoneVo.getNbr_level());
		req.put("TELE_TYPE", contractPhoneVo.getTele_type());
		
		sooList.get(0).put("NUMBER", req);
		
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001013",
				ResponseBean.class);
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultSooMap = (Map)resultSooList.get(0);
		Map resp = (Map)resultSooMap.get(HhConstants.RESP);
		String status = (String)resp.get(HhConstants.RESULT);
		String msg = (String) resp.get(HhConstants.MSG);
		Map<String, Object> resultMap = new HashMap<String, Object>();

		ContractPhoneResp contractPhoneResp = new ContractPhoneResp();
		Map<String, Object> mapInfo = new HashMap<>();
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			
			ArrayList<Map<String, Object>> service_infolist = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> service_infoslist = (List<Map<String, Object>>)resultSooMap
					.get("SERVICE_INFO");
			for (int i = 0; i < service_infoslist.size(); i++) {
				Map<String, Object> pic = new HashMap<String, Object>();
				pic.put("area_code", service_infoslist.get(i).get("AREA_CODE"));
				pic.put("city_code", service_infoslist.get(i).get("CITY_CODE"));
				pic.put("city_name", service_infoslist.get(i).get("CITY_NAME"));
				pic.put("min_consume", service_infoslist.get(i).get("MIN_CONSUME"));
				pic.put("nbr_level", service_infoslist.get(i).get("NBR_LEVEL"));
				pic.put("normal_balance", service_infoslist.get(i).get("NORMAL_BALANCE"));
				pic.put("pre_deposit", service_infoslist.get(i).get("PRE_DEPOSIT"));
				pic.put("provice_name", service_infoslist.get(i).get("PROVICE_NAME"));
				pic.put("province_code", service_infoslist.get(i).get("PROVINCE_CODE"));
				pic.put("service_num", service_infoslist.get(i).get("SERVICE_NUM"));
				service_infolist.add(pic);
			}
			mapInfo.put("service_info", service_infolist);
			
			contractPhoneResp.setMapInfo(mapInfo);
		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, msg);// 失败描述
		}
		resultMap.put("contractphoneresp", contractPhoneResp);
		return resultMap;
	}

}
