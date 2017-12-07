package com.tydic.uniform.hh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.AgentManualServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentManualVo;

import net.sf.json.JSONObject;

@Service("agentManualServiceServ")
public class AgentManualServiceImpl extends AbstractService implements AgentManualServiceServ{
	private static Logger log = Logger.getLogger(AgentBackFileByOCRServiceImpl.class);
	/**
	 * 170号码充值
	 * @author ghp 2016年7月14日10:15:28
	 * @Method: agentFaceBackfile 
	 * @Description: TODO
	 * @param agentbackfilebyocrvo
	 * @return 
	 */
	@Override
	public String getAgentManual(AgentManualVo agentManualVo) {
		
		//log.info("*************************先查手册缓存*************************");
		
		
		log.info("*************************APP使用手册查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentManualVo).toString());
		
		Map<String, Object> req = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("systemUserId", agentManualVo.getSystemuserid());
		param.put("orgId", agentManualVo.getOrg_id());
		req.put("param", param);				
		
		/*
		 * 请求接口
		 */
		log.info("*************************ESHOP 使用手册查询接口入参*************************");
		log.info("BOSS response str:" + req.toString());
		setIntefaceType(IntefaceType.ESHOP);
		
/*		ResponseBean bean = CommonCacheUtil.getCacheManual();
		boolean needCache = false;
		
		if(null != bean && !bean.getBody().isEmpty()){
			log.info("*************************使用手册接口数据直接从缓存读取*************************");
			bean = CommonCacheUtil.getCacheManual(); 
		} else {
			needCache = true;
			bean = (ResponseBean) this.appApiInvoke(req, "SC1002402", ResponseBean.class);
		}*/
		
		
		ResponseBean bean = (ResponseBean) this.appApiInvoke(req, "SC1002402", ResponseBean.class);
		
		log.info("*************************ESHOP 使用手册查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		 */
		List<Map<String, Object>> resultMap = null;
		
		Map<String, Object> resultsooMap = (Map<String, Object>) bean.getBody().get(0);
		String status = (String) resultsooMap.get(HhConstants.ESHOPRESULT);
		
		String resultString = "";
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap = (List<Map<String, Object>>)resultsooMap.get(HhConstants.ESHOPREPDATA);
			
			/*if(needCache){
				CommonCacheUtil.cacheManual(bean);
			}*/
			
			resultString = JsonResponse.toSuccessResult(resultMap);
		}else{
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "查询使用手册接口失败");
		}
		log.info("*************************APP使用手册查询接口出参*************************");
		log.info("APP response str:" + DesEncryptUtil.decrypt(resultString));
		
		return resultString;
		}
	
}
