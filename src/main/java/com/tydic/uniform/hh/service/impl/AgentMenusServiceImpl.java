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
import com.tydic.uniform.hh.service.interfaces.AgentMenusServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentMenusVo;

import net.sf.json.JSONObject;

@Service("agentMenusServiceServ")
public class AgentMenusServiceImpl extends AbstractService implements AgentMenusServiceServ{
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
	public String getAgentMenus(AgentMenusVo agentMenusVo) {
		//log.info("*************************先查菜单缓存*************************");		
		
		log.info("*************************APP菜单布局查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentMenusVo).toString());
		
		Map<String, Object> req = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageName", agentMenusVo.getPageName());
		req.put("param", param);
		/*
		 * 请求接口
		 */
		log.info("*************************ESHOP 菜单布局查询接口入参*************************");
		log.info("BOSS request str:" + req.toString());
		setIntefaceType(IntefaceType.ESHOP);
		
/*		ResponseBean bean = CommonCacheUtil.getCacheMenu();
		boolean needCache = false;
		
		if(null != bean && !bean.getBody().isEmpty()){
			log.info("*************************菜单布局接口数据直接从缓存读取*************************");
			bean = CommonCacheUtil.getCacheMenu(); 
		} else {
			needCache = true;
			bean = (ResponseBean) this.appApiInvoke(req, "SC1002401", ResponseBean.class);
		}*/
		
		ResponseBean bean = (ResponseBean) this.appApiInvoke(req, "SC1002401", ResponseBean.class);
		
		log.info("*************************ESHOP 菜单布局查询接口出参*************************");
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

			/*if(needCache){
				CommonCacheUtil.cacheMenu(bean);
			}*/
			
			resultString = JsonResponse.toSuccessResult(resultMap);
		}else{
			resultString = JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "查询菜单接口失败");
		}
		log.info("*************************APP菜单布局查询接口出参*************************");
		log.info("APP response str:" + DesEncryptUtil.decrypt(resultString));
		
		return resultString;
		}
	
}
