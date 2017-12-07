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
import com.tydic.uniform.hh.service.interfaces.AgentInfoServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentInfoVo;

import net.sf.json.JSONObject;

@Service("agentInfoServiceServ")
public class AgentInfoServiceImpl extends AbstractService implements AgentInfoServiceServ{
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
	public String getAgentInfo(AgentInfoVo agentInfoVo) {

		log.info("*************************APP代理商信息查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentInfoVo).toString());

		Map<String, Object> req = new HashMap<String, Object>();
		req.put("StaffId", agentInfoVo.getSystemuserid());
		req.put("Type", "2");
		
		Map<String, Object> AgentInfomation = new HashMap<String, Object>();
		AgentInfomation.put("PayPwd", "");
		AgentInfomation.put("CredentialsNo", "");
		AgentInfomation.put("PayAcct", "");
		AgentInfomation.put("EmailAddr", "");
		AgentInfomation.put("MobileNo", "");
		AgentInfomation.put("FavMode", "");
		AgentInfomation.put("Postcode", "");
		AgentInfomation.put("PayName", "");
		AgentInfomation.put("FavRate", "");
		AgentInfomation.put("Address", "");
		AgentInfomation.put("ContactAddr", "");
		AgentInfomation.put("OldPwd", "");
		AgentInfomation.put("CredentialsType", "");
		
		
		req.put("AgentInfomation", AgentInfomation);
		/*
		 * 请求接口
		 */
		log.info("*************************代理商信息查询接口入参*************************");
		log.info("BOSS response str:" + req.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(req, "SC1003043", ResponseBean.class);

		log.info("*************************代理商信息查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());

		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> resultsooMap = (Map<String, Object>) bean.getBody().get(0);
		String status = (String) resultsooMap.get(HhConstants.BILLRESULT);

		String resultString = "";
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap = (Map<String, Object>)resultsooMap.get("AgentInfomation");
			resultString = JsonResponse.toSuccessResult(resultMap);
		}else{
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, resultsooMap.get(HhConstants.BILLMSG).toString());
		}
		log.info("*************************APP代理商信息查询接口出参*************************");
		log.info("APP response str:" + DesEncryptUtil.decrypt(resultString));

		return resultString;
	}
	
}
