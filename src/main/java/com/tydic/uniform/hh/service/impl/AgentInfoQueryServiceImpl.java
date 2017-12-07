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
import com.tydic.uniform.hh.service.interfaces.AgentInfoQueryServ;
import com.tydic.uniform.hh.vo.rep.AgentInfoQueryVo;

import net.sf.json.JSONObject;

/**
 * <p></p>
 * @author yiyaohong 2015年12月9日 下午12:27:37
 * @ClassName AgentInfoQueryServiceImpl
 * @Description TODO	代理商基本信息接口查询
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年12月9日
 * @modify by reason:{方法名}:{原因}
 */
@Service("AgentInfoQueryServ")
public class AgentInfoQueryServiceImpl extends AbstractService implements AgentInfoQueryServ {
	private static Logger log = Logger.getLogger(AgentInfoQueryServiceImpl.class);
	
	
	public static void main(String[] args){
		AgentInfoQueryServiceImpl agent = new AgentInfoQueryServiceImpl();
		/*AgentInfoQueryVo avo = new AgentInfoQueryVo();
		avo.setAccountNo("B_WANG");
		agent.agentdetail(avo);*/
		agent.agentInfo();
		
	}
	
	public void agentInfo(){
		/** TODO Auto-generated method stub */
		log.info("*************************APP代理商基本信息接口入参*************************");
		//log.info("APP request str:" + JSONObject.fromObject(agentinfoqueryvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map> sooList = new ArrayList<Map>();

		sooList.add(0, new HashMap());

		Map pub_rep = new HashMap();
		pub_rep.put(HhConstants.TYPE, "QUERY_AGENT_INFO_EXT");

		Map req = new HashMap();
		req.put("LOGIN_NBR", "B_WANG"); // 必选
		req.put("AGENT_CODE", "A29289264131"); // 必选
		//req.put("LOGIN_NBR", ""); // 必选
		//req.put("AGENT_CODE", "A4168809475");

		sooList.get(0).put("AGENT_QUERY", req);
		sooList.get(0).put(HhConstants.PUB_REQ, pub_rep);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS代理商基本信息接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001046", ResponseBean.class);
		log.info("*************************BOSS代理商基本信息接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		//{"SOO":[{"AGENT_QUERY":{"LOGIN_NBR":"","AGENT_CODE":"A4168809475"},"PUB_REQ":{"TYPE":"QUERY_AGENT_INFO_EXT"}}]}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<String, Object> agentdetail(AgentInfoQueryVo agentinfoqueryvo) {
		/** TODO Auto-generated method stub */
		log.info("*************************APP代理商基本信息接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentinfoqueryvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map> sooList = new ArrayList<Map>();

		sooList.add(0, new HashMap());

		Map pub_rep = new HashMap();
		pub_rep.put(HhConstants.TYPE, "AGENT_QRY");

		Map req = new HashMap();
		req.put(HhConstants.ACCOUNTNO, agentinfoqueryvo.getAccountNo()); // 必选

		sooList.get(0).put(HhConstants.AGENT_QRY, req);
		sooList.get(0).put(HhConstants.PUB_REQ, pub_rep);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS代理商基本信息接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1001038", ResponseBean.class);
		log.info("*************************BOSS代理商基本信息接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> result_resp = (Map<String, Object>) bean.getBody().get(0).get(HhConstants.RESP);
		String status = (String) result_resp.get(HhConstants.RESULT);
		String result = (String) result_resp.get(HhConstants.MSG);

		Map result_agent = new HashMap();
		if (HhConstants.SUCCESS.equals(status)) {
			if (((Map<String, Object>) bean.getBody().get(0)).containsKey(HhConstants.AGENT)) {
				Map<String, Object> resultSooMap = (Map<String, Object>) bean.getBody().get(0).get(HhConstants.AGENT);

				String acct_id = (String) resultSooMap.get(HhConstants.ACCT_ID);
				String agent_code = (String) resultSooMap.get(HhConstants.AGENT_CODE);
				String agent_name = (String) resultSooMap.get(HhConstants.AGENT_NAME);
				String statu = (String) resultSooMap.get(HhConstants.STATUS);
				String jion_date = (String) resultSooMap.get(HhConstants.JION_DATE);
				String crt_date = (String) resultSooMap.get(HhConstants.CRT_DATE);
				String eff_date = (String) resultSooMap.get(HhConstants.EFF_DATE);
				String exp_date = (String) resultSooMap.get(HhConstants.EXP_DATE);
				String agent_id = (String) resultSooMap.get(HhConstants.AGENT_ID);
				String bank_name = (String) resultSooMap.get(HhConstants.BANK_NAME);
				String basic_info_id = (String) resultSooMap.get(HhConstants.BASIC_INFO_ID);
				String agent_addr = (String) resultSooMap.get(HhConstants.AGENT_ADDR);
				String agent_type = resultSooMap.containsKey(HhConstants.AGENT_TYPE)
						? (String) resultSooMap.get(HhConstants.AGENT_TYPE) : "";
				String agent_level = resultSooMap.containsKey(HhConstants.AGENT_LEVEL)
						? (String) resultSooMap.get(HhConstants.AGENT_LEVEL) : "";
				String acct_name = resultSooMap.containsKey(HhConstants.ACCT_NAME)
						? (String) resultSooMap.get(HhConstants.ACCT_NAME) : "";
				String payment_type_id = resultSooMap.containsKey(HhConstants.PAYMENT_TYPE_ID)
						? (String) resultSooMap.get(HhConstants.PAYMENT_TYPE_ID) : "";
				String acct_type = resultSooMap.containsKey(HhConstants.ACCT_TYPE)
						? (String) resultSooMap.get(HhConstants.ACCT_TYPE) : "";
				String pay_method = resultSooMap.containsKey(HhConstants.PAY_METHOD)
						? (String) resultSooMap.get(HhConstants.PAY_METHOD) : "";
				String bank_acct = resultSooMap.containsKey(HhConstants.BANK_ACCT)
						? (String) resultSooMap.get(HhConstants.BANK_ACCT) : "";
				String org_certif_type_id = resultSooMap.containsKey(HhConstants.ORG_CERTIF_TYPE_ID)
						? (String) resultSooMap.get(HhConstants.ORG_CERTIF_TYPE_ID) : "";
				String org_certif_nbr = resultSooMap.containsKey(HhConstants.ORG_CERTIF_NBR)
						? (String) resultSooMap.get(HhConstants.ORG_CERTIF_NBR) : "";
				String contact_tel_nbr = resultSooMap.containsKey(HhConstants.CONTACT_TEL_NBR)
						? (String) resultSooMap.get(HhConstants.CONTACT_TEL_NBR) : "";
				String contact_addr = resultSooMap.containsKey(HhConstants.CONTACT_ADDR)
						? (String) resultSooMap.get(HhConstants.CONTACT_ADDR) : "";
				String contactor = resultSooMap.containsKey(HhConstants.CONTACTOR)
						? (String) resultSooMap.get(HhConstants.CONTACTOR) : "";
				String org_name = resultSooMap.containsKey(HhConstants.ORG_NAME)
						? (String) resultSooMap.get(HhConstants.ORG_NAME) : "";
				String tax_charter_nbr = resultSooMap.containsKey(HhConstants.TAX_CHARTER_NBR)
						? (String) resultSooMap.get(HhConstants.TAX_CHARTER_NBR) : "";
				String tax_register_nbr = resultSooMap.containsKey(HhConstants.TAX_REGISTER_NBR)
						? (String) resultSooMap.get(HhConstants.TAX_REGISTER_NBR) : "";
				String tax_register_date = resultSooMap.containsKey(HhConstants.TAX_REGISTER_DATE)
						? (String) resultSooMap.get(HhConstants.TAX_REGISTER_DATE) : "";

				result_agent.put(HhConstants.ACCT_ID, acct_id);
				result_agent.put(HhConstants.ACCT_NAME, acct_name);
				result_agent.put(HhConstants.ACCT_TYPE, acct_type);
				result_agent.put(HhConstants.AGENT_ADDR, agent_addr);
				result_agent.put(HhConstants.AGENT_CODE, agent_code);
				result_agent.put(HhConstants.AGENT_ID, agent_id);
				result_agent.put(HhConstants.AGENT_LEVEL, agent_level);
				result_agent.put(HhConstants.AGENT_NAME, agent_name);
				result_agent.put(HhConstants.AGENT_TYPE, agent_type);
				result_agent.put(HhConstants.BANK_ACCT, bank_acct);
				result_agent.put(HhConstants.BANK_NAME, bank_name);
				result_agent.put(HhConstants.BASIC_INFO_ID, basic_info_id);
				result_agent.put(HhConstants.CONTACTOR, contactor);
				result_agent.put(HhConstants.CONTACT_ADDR, contact_addr);
				result_agent.put(HhConstants.CONTACT_TEL_NBR, contact_tel_nbr);
				result_agent.put(HhConstants.CRT_DATE, crt_date);
				result_agent.put(HhConstants.EFF_DATE, eff_date);
				result_agent.put(HhConstants.EXP_DATE, exp_date);
				result_agent.put(HhConstants.JION_DATE, jion_date);
				result_agent.put(HhConstants.ORG_CERTIF_NBR, org_certif_nbr);
				result_agent.put(HhConstants.ORG_CERTIF_TYPE_ID, org_certif_type_id);
				result_agent.put(HhConstants.ORG_NAME, org_name);
				result_agent.put(HhConstants.PAYMENT_TYPE_ID, payment_type_id);
				result_agent.put(HhConstants.PAY_METHOD, pay_method);
				result_agent.put(HhConstants.STATUS, statu);
				result_agent.put(HhConstants.TAX_CHARTER_NBR, tax_charter_nbr);
				result_agent.put(HhConstants.TAX_REGISTER_DATE, tax_register_date);
				result_agent.put(HhConstants.TAX_REGISTER_NBR, tax_register_nbr);

			}
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.MSG, result);

		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
		}
		resultMap.put(HhConstants.AGENT, result_agent);
		log.info("*************************APP代理商基本信息接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}

}
