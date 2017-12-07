package com.tydic.uniform.hh.service.impl;

import java.util.HashMap;
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
import com.tydic.uniform.hh.service.interfaces.AgentVersionServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentVersionVo;

import net.sf.json.JSONObject;

@Service("agentVersionServiceServ")
public class AgentVersionServiceImpl extends AbstractService implements AgentVersionServiceServ{
	private static Logger log = Logger.getLogger(AgentBackFileByOCRServiceImpl.class);

	@Override
	public String getAgentVersion(AgentVersionVo agentVersionVo) {

		log.info("*************************APP版本查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentVersionVo).toString());
		
		Map<String, Object> req = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appOS", agentVersionVo.getOs());
		param.put("version", agentVersionVo.getVersion());
		param.put("osType", "2");
		req.put("param", param);
		/*
		 * 请求接口
		 */
		log.info("*************************ESHOP 版本查询接口入参*************************");
		log.info("BOSS response str:" + req.toString());
		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(req, "SC1002029", ResponseBean.class);
		
		log.info("*************************ESHOP 版本查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String, Object> resultsooMap = (Map<String, Object>) bean.getBody().get(0);
		String status = (String) resultsooMap.get(HhConstants.ESHOPRESULT);
		
		String resultString = "";
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap = (Map<String, Object>)resultsooMap.get(HhConstants.ESHOPREPDATA);
			resultString = JsonResponse.toSuccessResult(resultMap);
		}else{
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "查询版本接口失败");
		}
		log.info("*************************APP版本查询接口入参*************************");
		log.info("APP response str:" + DesEncryptUtil.decrypt(resultString));
		
		return resultString;
		
	}

	
	
}
