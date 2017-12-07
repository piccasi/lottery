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
import com.tydic.uniform.hh.service.interfaces.ResourceDonationServiceServ;
import com.tydic.uniform.hh.util.ValidateCodeUtil;
import com.tydic.uniform.hh.vo.rep.MessageCheckVo;
import com.tydic.uniform.hh.vo.rep.ResourceDonationInputVo;
import com.tydic.uniform.hh.vo.rep.ResourceDonationVo;
import com.tydic.uniform.hh.vo.resp.ResourceDonationResp;

import net.sf.json.JSONObject;

/**
 * 
 * <p></p>
 * @author Administrator 2015年10月14日 下午12:01:29
 * @ClassName ResourceDonationServiceImpl
 * @Description 资源转增
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
@Service("ResourceDonationServiceServ")
public class ResourceDonationServiceImpl extends AbstractService implements ResourceDonationServiceServ{
	private static Logger log = Logger.getLogger(ResourceDonationServiceImpl.class);
	@Override
	public Map<String, Object> resourceDonation(ResourceDonationVo resourcedonationvo) {
		//判断手机验证码是否正确
		boolean flag = ValidateCodeUtil.isSuccess(resourcedonationvo.getNumber(),resourcedonationvo.getValidatecode());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(flag){
			log.info("*************************APP号码查询接口入参*************************");
			log.info("APP request str:" + JSONObject.fromObject(resourcedonationvo).toString());
			/*
			 * 按照BOSS文档设置接口入参
			 */
			
			List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
			sooList.add(0, new HashMap<String,Object>());
			
			List<Map<String,Object>> presentinfolit = new ArrayList<Map<String,Object>>();
			Map<String,Object> req = new HashMap<String,Object>();
			List<ResourceDonationInputVo> resourcedonationInputvolist = resourcedonationvo.getPresentinfolist();
			for (int i = 0; i < resourcedonationInputvolist.size(); i++) {
				ResourceDonationInputVo resourceDonationInputVo = (ResourceDonationInputVo)resourcedonationInputvolist.get(i);
				if (resourceDonationInputVo!=null) {
					Map<String,Object> req_s1 = new HashMap<String,Object>();
					
					req_s1.put("OutBalanceTypeId", resourceDonationInputVo.getOutbalancetypeid());
					req_s1.put("OutBalance", resourceDonationInputVo.getOutbalance());
					req_s1.put("OutMemberId", resourcedonationvo.getOutid());
					req_s1.put("InMemberId", resourceDonationInputVo.getInmemberid());
					presentinfolit.add(req_s1);
				}
			}
			req.put("PresentInfo", presentinfolit);
			sooList.get(0).put("ResPresent",req);
			Map<String,Object> req_s2 = new HashMap<String,Object>();
			req_s2.put(HhConstants.TYPE, "ResPresent");
			sooList.get(0).put(HhConstants.PUB_REQ,req_s2);
			
			Map<String,Object> body = new HashMap<String,Object>();
			body.put("SOO", sooList);
			
			/*
			 * 请求接口
			 */
			log.info("*************************BOSS号码查询接口入参*************************");
			log.info("BOSS response str:" + body.toString());
			setIntefaceType(IntefaceType.BILL);
			ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003015", ResponseBean.class);
			
//			JSONObject resultJson = JSONObject.fromObject(rspJson);
			log.info("*************************BOSS号码查询接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
			/*
			 * 解析接口返参
			*/
		
			
			List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
			Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
			Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
			String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
			String result = (String) resultResultMap.get(HhConstants.MSG);
			
			ResourceDonationResp resourceDonationResp = new ResourceDonationResp();
			
			   if(HhConstants.SUCCESS.equals(status)){
				    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
				    resultMap.put(HhConstants.RESULT, status);
				    resultMap.put(HhConstants.MSG, result);
			    }else if(HhConstants.ERROR.equals(status)){
			    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
					resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			    }
			    resultMap.put("resourceDonationResp", resourceDonationResp);
			    log.info("*************************APP号码查询接口出参*************************");
				log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		}else{
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, "验证码有误");//失败描述
		}
		return resultMap;
	}
}
