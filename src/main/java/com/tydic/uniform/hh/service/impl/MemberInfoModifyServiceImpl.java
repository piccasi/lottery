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
import com.tydic.uniform.hh.service.interfaces.MemberInfoModifyServiceServ;
import com.tydic.uniform.hh.vo.rep.MemberInfoModifyVo;
import com.tydic.uniform.hh.vo.resp.MemberInfoModifyResp;
import net.sf.json.JSONObject;
/**
 * 
 * <p></p>
 * @author Administrator 2015年10月15日 下午7:31:22
 * @ClassName MemberInfoModifyServiceImpl
 * @Description 会员资料修改
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月15日
 * @modify by reason:{方法名}:{原因}
 */
@Service("MemberInfoModifyServiceServ")
public class MemberInfoModifyServiceImpl extends AbstractService implements MemberInfoModifyServiceServ {
	private static Logger log = Logger.getLogger(MemberInfoModifyServiceImpl.class);
	@Override
	public Map<String, Object> memberinfomodifyserviceserv(MemberInfoModifyVo memberinfomodifyvo) {
		log.info("*************************个人资料修改接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(memberinfomodifyvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> req = new HashMap<String,Object>();
		if (!(memberinfomodifyvo.getBirthdate().equals(null)||memberinfomodifyvo.getBirthdate().equals(""))) {
			String birthdate = memberinfomodifyvo.getBirthdate();
			if (birthdate.indexOf("/")!=-1) {
				String formatBirthdate = birthdate.replaceAll("/", "-");
				req.put(HhConstants.BIRTHDATE, formatBirthdate);
			}else {
				req.put(HhConstants.BIRTHDATE, memberinfomodifyvo.getBirthdate());
			}
		}
		if (!(memberinfomodifyvo.getCerti_addr().equals(null)||memberinfomodifyvo.getCerti_addr().equals(""))) {
			req.put(HhConstants.CERTI_ADDR, memberinfomodifyvo.getCerti_addr());
		}
		//判断是否为170用户，若为170用户，则不用传身份证号 ADD BY PANXINXING 2016/01/04
		if(!is170Num(memberinfomodifyvo.getCust_id())&&isAuditing(memberinfomodifyvo.getCust_id())){
			if (!(memberinfomodifyvo.getCerti_nbr().equals(null)||memberinfomodifyvo.getCerti_nbr().equals(""))) {
				req.put(HhConstants.CERTI_NBR, memberinfomodifyvo.getCerti_nbr());
			}
		}
		if (!(memberinfomodifyvo.getContact_addr().equals(null)||memberinfomodifyvo.getContact_addr().equals(""))) {
			req.put(HhConstants.CONTACT_ADDR, memberinfomodifyvo.getContact_addr());
		}
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.CUST_ID, memberinfomodifyvo.getCust_id());
		if (!(memberinfomodifyvo.getCust_name().equals(null)||memberinfomodifyvo.getCust_name().equals(""))) {
			req.put(HhConstants.CUST_NAME, memberinfomodifyvo.getCust_name());
		}
		if (!(memberinfomodifyvo.getEmail().equals(null)||memberinfomodifyvo.getEmail().equals(""))) {
			req.put(HhConstants.EMAIL, memberinfomodifyvo.getEmail());
		}
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		if (!(memberinfomodifyvo.getMobile().equals(null)||memberinfomodifyvo.getMobile().equals(""))) {
			req.put(HhConstants.MOBILE, memberinfomodifyvo.getMobile());
		}
		if (!(memberinfomodifyvo.getNick_name().equals(null)||memberinfomodifyvo.getNick_name().equals(""))) {
			req.put(HhConstants.NICK_NAME, memberinfomodifyvo.getNick_name());
		}
		if (!(memberinfomodifyvo.getSex().equals(null)||memberinfomodifyvo.getSex().equals(""))) {
			req.put(HhConstants.SEX, memberinfomodifyvo.getSex());
		}
		if (!(memberinfomodifyvo.getWx_acct().equals(null)||memberinfomodifyvo.getWx_acct().equals(""))) {
			req.put(HhConstants.WX_ACCT, memberinfomodifyvo.getWx_acct());
		}
		sooList.get(0).put("CUST", req);
		Map<String,Object> typemap= new HashMap<String,Object>();
		typemap.put(HhConstants.TYPE, "CUST_UPDATE");
		sooList.get(0).put(HhConstants.PUB_REQ, typemap);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS个人资料修改接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001011", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS个人资料修改接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		//如果返回结果里没有result字段  换用code字段
		if(status==null){
			status = (String) resultResultMap.get(HhConstants.CODE);
		}
		String result = (String) resultResultMap.get(HhConstants.MSG);
		
		MemberInfoModifyResp memberInfoModifyResp = new MemberInfoModifyResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			    resultMap.put(HhConstants.RESULT, status);
			    resultMap.put(HhConstants.MESSAGE, result);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
				if(result!=null)
					resultMap.put(HhConstants.MESSAGE, result);
		    }
		    resultMap.put("memberInfoModifyResp", memberInfoModifyResp);
		    log.info("*************************APP号码查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
	/**
	 *  判断是否为170用户
	 * @param custId
	 * @return true/false
	 */
	private boolean is170Num (String custId){
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());
		Map<String, Object> req = new HashMap<String, Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		req.put(HhConstants.CUST_ID, custId);
		sooList.get(0).put(HhConstants.CUST_QRY,req);
		Map<String, Object> pub_req = new HashMap<String, Object>();
		pub_req.put(HhConstants.TYPE, "CUST_QUERY");
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS客户资料查询接口（领取首次安装大礼包并发送验证短信）入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001004", ResponseBean.class);
		log.info("*************************BOSS客户资料查询接口（领取首次安装大礼包并发送验证短信）出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		List<Map<String,Object>> resultResultCust =  (List<Map<String,Object>>) resultsooMap.get(HhConstants.CUST);
		System.out.println(resultResultCust);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		if(HhConstants.SUCCESS.equals(status)){
			String MobileNumber ="";
			for (Map<String, Object> map : resultResultCust) {
				Object value = map.get(HhConstants.MOBILE_170);
			     MobileNumber = value.toString();
			}
			if (MobileNumber.equals("")||MobileNumber==null) {
//				if(isAuditing(custId));
				return false;
			}else {
				return true;
			}
			
		}else{
			return false;
		}
	}
	//判断是否有订单的状态
	private boolean isAuditing (String custId){
		boolean Flag = true;
		log.info("*************************判断是否有订单在审核*************************");
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.CUST_ID, custId);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		req.put(HhConstants.PAGE, "10");
		req.put(HhConstants.PAGE_NO, "1");
		
//		Map<String,Object> orderMap = new HashMap<String,Object>();
//	    orderMap.put(HhConstants.ORDER, req);
	    
	    Map<String,Object> typeMap = new HashMap<String,Object>();
	    typeMap.put(HhConstants.TYPE, HhConstants.ORDER_QRY);
	    
//	    Map<String,Object> pub_reqMap = new HashMap<String,Object>();
//	    pub_reqMap.put(HhConstants.PUB_REQ, typeMap);
	    
		sooList.get(0).put(HhConstants.ORDER, req);
		sooList.get(0).put(HhConstants.PUB_REQ, typeMap);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		/*
		 * 请求接口
		 */
		log.info("*************************可订购套餐查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001007", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************可订购套餐查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP); 
		List<Map<String,Object>> orderListMap = (List<Map<String,Object>>) resultsooMap.get(HhConstants.ORDER);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		if (HhConstants.SUCCESS.equals(status)) {
			for (Map<String, Object> ordermap : orderListMap) {
				Object statuscd = ordermap.get("STATUS_CD");
				String statusd = statuscd.toString();
				if (statusd.equals("100006")||statusd.equals("100100")) {
					Flag = false;
				}else {
					Flag = true;
				}
			}
			
		}
		return Flag;
	}
	
}
