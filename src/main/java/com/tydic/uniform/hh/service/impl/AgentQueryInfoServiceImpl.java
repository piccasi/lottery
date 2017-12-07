package com.tydic.uniform.hh.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.constant.NumberStatus;
import com.tydic.uniform.hh.service.interfaces.AgentOrderServiceServ;
import com.tydic.uniform.hh.service.interfaces.AgentQueryInfoServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentInfoQueryVo;
import com.tydic.uniform.hh.vo.rep.AgentOrderVo;
import com.tydic.uniform.hh.vo.rep.AgentSaleQuery;
import com.tydic.uniform.hh.vo.rep.NumberInfoVo;
import com.tydic.uniform.hh.vo.rep.UsermsgVo;

import net.sf.json.JSONObject;

@Service("AgentQueryInfoServiceServ")
public class AgentQueryInfoServiceImpl extends AbstractService implements AgentQueryInfoServiceServ {
	private static Logger log = Logger.getLogger(AgentQueryInfoServiceImpl.class);

	@Autowired
	private AgentOrderServiceServ agentOrderServiceServ;
	@Override
	public String getNumberInfo(UsermsgVo usermsgvo) {
		log.info("*************************APP 用户信息查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(usermsgvo).toString());
		
		if(StringUtils.isEmpty(usermsgvo.getQry_number())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "手机号码为空");
		}
		AgentOrderVo agentOrderVo = new AgentOrderVo();
		agentOrderVo.setMobile_170(usermsgvo.getQry_number());
		Map<String, Object> userInfo = agentOrderServiceServ.getUserInfo(agentOrderVo);
		
		NumberInfoVo numberInfoVo = new NumberInfoVo();
		{	
			//校验号码是否支持查询
			if(HhConstants.SUCCESSCODE.equals(userInfo.get(HhConstants.CODE))){
				Map<String, Object> cust = (Map<String, Object>)userInfo.get("CUST");
				Map<String, Object> prodInst = ((List<Map<String, Object>>)cust.get("PROD_INST")).get(0);
				String status = prodInst.get("STATUS_CD").toString();
				if(!(!"110000".equals(status) && !"110098".equals(status)
						&& !"404".equals(status)&&!"123001".equals(status)&&!"180000".equals(status))){
					return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "该用户状态受限制");
				}
				//手机号码
				numberInfoVo.setMobile(cust.get("MOBILE_170").toString());
				//custId
				numberInfoVo.setCustId(cust.get("CUST_ID").toString());
				
				//号码状态
				numberInfoVo.setStatus(NumberStatus.getDescByCode(Integer.parseInt(prodInst.get("STATUS_CD").toString())));
				
				//制式
				String flag4G = "4G";
				if("3g".equals(prodInst.get("FLAG_4G")))flag4G="3G";
				numberInfoVo.setFlag4G(flag4G);
				
				//归属地
				numberInfoVo.setAttribution(prodInst.get("LOCAL_NET_NAME").toString());
				
				//姓名
				String cretiName = cust.get("CUST_NAME").toString();
				String name = cretiName.substring(0, 1);
				for(int i=0;i<cretiName.length()-1;i++){
					name+="*";
				}
				numberInfoVo.setCretiName(name);
				
				//身份证号码
				String cretiNbr = cust.get("CERTI_NBR").toString();
				if(cretiNbr.length()==18){
					cretiNbr = cretiNbr.substring(0, 3) + "************" +cretiNbr.substring(cretiNbr.length()-3, cretiNbr.length());
				}else if(cretiNbr.length()==15){
					cretiNbr = cretiNbr.substring(0, 3) + "*********" +cretiNbr.substring(cretiNbr.length()-3, cretiNbr.length());
				}
				numberInfoVo.setCretiNbr(cretiNbr);
				
			}else{
				return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, userInfo.get(HhConstants.MESSAGE).toString());
			}
		}
		
		
		
		{	
			//查询基础业务
//			log.info("*************************APP基础业务查询接口入参*************************");
//			log.info("APP request str:" + JSONObject.fromObject(usermsgvo).toString());
			/*
			 * 按照BOSS文档设置接口入参
			 */
			
			List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
			
			Map<String,Object> req = new HashMap<String,Object>();
			req.put("MSISDN", usermsgvo.getQry_number());
			req.put("ContactChannle", HhConstants.CHANNEL_CODEVALUE);
			
			sooList.add(req);
			
			Map<String,Object> body = new HashMap<String,Object>();
			body.put("SOO", sooList);
			
			/*
			 * 请求接口
			 */
			log.info("*************************BOSS基础业务查询接口入参*************************");
			log.info("BOSS response str:" + body.toString());
			setIntefaceType(IntefaceType.CRM);
			ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001034", ResponseBean.class);
			
//			JSONObject resultJson = JSONObject.fromObject(rspJson);
			log.info("*************************BOSS基础业务查询接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
			/*
			 * 解析接口返参
			*/
			
			List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
			Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
			Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
			String status = (String) resultResultMap.get(HhConstants.RESULT);
			String mssg = (String) resultResultMap.get("Msg");
			numberInfoVo.setDisplayStatus("未开通");
			if(HhConstants.SUCCESS.equals(status)){
				List<Map<String,Object>> depententProdDtoList = (List<Map<String,Object>>)resultsooMap.get("DepententProdDtoList");
				for(Map<String,Object> depententProdDto : depententProdDtoList){
					String depententProdCode = depententProdDto.get("depententProdCode").toString();
					if("PBA004".equals(depententProdCode) || "TBA401".equals(depententProdCode)
							|| "PZS031M".equals(depententProdCode) || "PZS031B".equals(depententProdCode)){
						if("0".equals(depententProdDto.get("depententProdStatus").toString())){
							numberInfoVo.setDisplayStatus("未开通");
						}else if("1".equals(depententProdDto.get("depententProdStatus").toString())){
							numberInfoVo.setDisplayStatus("已开通");
						}else if("2".equals(depententProdDto.get("depententProdStatus").toString())){
							numberInfoVo.setDisplayStatus("开通中");
						}else if("3".equals(depententProdDto.get("depententProdStatus").toString())){
							numberInfoVo.setDisplayStatus("取消中");
						}
					}
				}
				//默认未开通
//				if(StringUtils.isEmpty(numberInfoVo.getDisplayStatus())){
//					return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "来电显示信息不存在");
//				}
			}else{
				return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mssg);
			}
		}
		
		{
			//查询会员余额

//			log.info("*************************APP会员余额查询接口入参*************************");
//			log.info("APP request str:" + JSONObject.fromObject(usermsgvo).toString());
			/*
			 * 按照BOSS文档设置接口入参
			 */
			
			List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
			sooList.add(0, new HashMap<String,Object>());
			
			Map<String,Object> req = new HashMap<String,Object>();

			req.put(HhConstants.SYSTEMID, HhConstants.EXT_SYSTEMVALUE);
			req .put(HhConstants.BALTYPE, "0");
			req .put(HhConstants.QRYTYPE, "2");
			req .put(HhConstants.MEMBERID, numberInfoVo.getCustId());
			
			sooList.get(0).put(HhConstants.BALANCEQRY_REQ,req);
			
			Map<String,Object> pub_req = new HashMap<String,Object>();
			pub_req.put(HhConstants.TYPE, "BalanceQry");
			
			sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
			
			
			Map<String,Object> body = new HashMap<String,Object>();
			body.put("SOO", sooList);


			/*
			 * 请求接口
			 */
			log.info("*************************BOSS会员余额查询接口入参*************************");
			log.info("BOSS response str:" + body.toString());

			
			setIntefaceType(IntefaceType.BILL);
			ResponseBean bean=(ResponseBean) this.appApiInvoke(body, "SC1003011", ResponseBean.class);


			log.info("*************************BOSS会员余额查询接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
			
			List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
			Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
			Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
			String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
			String mssg = (String) resultResultMap.get("Msg");

		    if(HhConstants.SUCCESS.equals(status)){
		    	String grantCash = resultResultMap.get("GrantCash").toString();
		    	String cash = resultResultMap.get("Cash").toString();
		    	
		    	BigDecimal cashBd = new BigDecimal(cash);
		    	BigDecimal grantCashBd = new BigDecimal(grantCash);
		    	BigDecimal gCash = cashBd.subtract(grantCashBd).setScale(2, BigDecimal.ROUND_HALF_UP);
		    	
		    	numberInfoVo.setNoCashBalance(grantCash);
		    	numberInfoVo.setCashBalance(gCash.toString());
		    }else{
		    	//查询余额失败
		    	return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mssg);
		    }
		    
		}
		
		{
			//查询实时账单
			
			List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
			sooList.add(0, new HashMap<String,Object>());
			
			Map<String,Object> req = new HashMap<String,Object>();
			
			req .put("DeviceNumber", usermsgvo.getQry_number());
			
			sooList.get(0).put("RealFeeQry",req);
			
			Map<String,Object> pub_req = new HashMap<String,Object>();
			pub_req.put(HhConstants.TYPE, "RealFeeQry");
			
			sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
			
			
			Map<String,Object> body = new HashMap<String,Object>();
			body.put("SOO", sooList);
			
			/*
			 * 请求接口
			 */
			log.info("*************************BOSS实时账单查询接口入参*************************");
			log.info("BOSS response str:" + body.toString());
			
			
			setIntefaceType(IntefaceType.BILL);
			ResponseBean bean=(ResponseBean) this.appApiInvoke(body, "SC1003033", ResponseBean.class);
			
			
			log.info("*************************BOSS实时账单查询接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
			
			List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
			Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
			Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
			String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
			String mssg = (String) resultResultMap.get(HhConstants.MSG);
			
			if(HhConstants.SUCCESS.equals(status)){
				numberInfoVo.setBalance(resultResultMap.get("RealBalance").toString());
		    	numberInfoVo.setBill(resultResultMap.get("Fee").toString());
			}else{
				return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mssg);
			}
		}
		
		{
			//查询通信资源
//			log.info("*************************APP通信资源查询接口入参*************************");
//			log.info("APP request str:" + JSONObject.fromObject(usermsgvo).toString());
			/*
			 * 按照BOSS文档设置接口入参
			 */
			
			List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
			sooList.add(0, new HashMap<String,Object>());
			
			Map<String,Object> req = new HashMap<String,Object>();

			req .put("MemberType", "1");
			req .put("Type", "2");
			req .put("Value", numberInfoVo.getCustId());
			
			sooList.get(0).put("OfrInst",req);
			
			Map<String,Object> pub_req = new HashMap<String,Object>();
			pub_req.put(HhConstants.TYPE, "OfrInst");
			
			sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
			
			
			Map<String,Object> body = new HashMap<String,Object>();
			body.put("SOO", sooList);


			/*
			 * 请求接口
			 */
			log.info("*************************BOSS通信资源查询接口入参*************************");
			log.info("BOSS response str:" + body.toString());

			
			setIntefaceType(IntefaceType.BILL);
			ResponseBean bean=(ResponseBean) this.appApiInvoke(body, "SC1003009", ResponseBean.class);


			log.info("*************************BOSS通信资源查询接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
			/*
//			 * 解析接口返参
//			*/
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
			Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
			Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
			String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
			String mssg = (String) resultResultMap.get(HhConstants.MSG);
		
		    if(HhConstants.SUCCESS.equals(status)){
		    	numberInfoVo.setFlowBalance(resultResultMap.get("DataLeftAmount").toString());
		    	numberInfoVo.setVoiceBalance(resultResultMap.get("CallLeftAmount").toString());
		    	numberInfoVo.setSmsBalance(resultResultMap.get("SmsLeftAmount").toString());
		    }else{
		    	return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mssg);
		    }
		}
		log.info("*************************APP 用户信息查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(DesEncryptUtil.decrypt(JsonResponse.toSuccessResult(numberInfoVo))).toString());
		return JsonResponse.toSuccessResult(numberInfoVo);
	}

	
	@Override
	public String getAgentRes(AgentInfoQueryVo agentinfoqueryvo) {
		//查询基础业务
		log.info("*************************APP代理商资源统计查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentinfoqueryvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> req = new HashMap<String,Object>();
		
		Map<String,Object> agentInfo = new HashMap<String,Object>();
		agentInfo.put("EXT_SYSTEM", HhConstants.EXT_SYSTEMVALUE);
		agentInfo.put("CHANNEL_CODE", agentinfoqueryvo.getOrg_id());
		
		Map<String,Object> pubReq = new HashMap<String,Object>();
		pubReq.put("TYPE", "AGENT_RES_QUERT");
		
		req.put("AGENT_INFO", agentInfo);
		req.put("PUB_REQ", pubReq);
		
		sooList.add(req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS代理商资源统计查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001064", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS代理商资源统计查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String mssg = (String) resultResultMap.get("Msg");
		if(HhConstants.SUCCESS.equals(status)){
			List<Map<String,Object>> resList = null;
			if(null != resultsooMap.get("RES_LIST")){
				resList = (List<Map<String,Object>>)resultsooMap.get("RES_LIST");
			}
			Map<String,Object> resultMap = new HashMap<String,Object>();
			resultMap.put("RES_LIST", resList);
			log.info("*************************APP 代理商资源统计查询接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(DesEncryptUtil.decrypt(JsonResponse.toSuccessResult(resultMap))).toString());
			return JsonResponse.toSuccessResult(resultMap);
		}else{
			log.info("*************************APP 代理商资源统计查询接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(DesEncryptUtil.decrypt(JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mssg))).toString());
			return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mssg);
		}
	}


	@Override
	public String getNumberOffer(UsermsgVo usermsgvo) {
		log.info("*************************APP 套餐询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(usermsgvo).toString());
		
		if(StringUtils.isEmpty(usermsgvo.getQry_number())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "手机号码为空");
		}
		AgentOrderVo agentOrderVo = new AgentOrderVo();
		agentOrderVo.setMobile_170(usermsgvo.getQry_number());
		Map<String, Object> userInfo = agentOrderServiceServ.getUserInfo(agentOrderVo);
		
		Map<String,Object> resultMap = new HashMap<String,Object>();
		{	
			//校验号码是否支持查询
			if(HhConstants.SUCCESSCODE.equals(userInfo.get(HhConstants.CODE))){
				Map<String, Object> cust = (Map<String, Object>)userInfo.get("CUST");
				Map<String, Object> prodInst = ((List<Map<String, Object>>)cust.get("PROD_INST")).get(0);
				String status = prodInst.get("STATUS_CD").toString();
				if(!(!"110000".equals(status) && !"110098".equals(status)
						&& !"404".equals(status)&&!"123001".equals(status)&&!"180000".equals(status))){
					return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "该用户状态受限制");
				}
				
				List<Map<String, Object>> prodOfferList = ((List<Map<String, Object>>)cust.get("PROD_OFFER_INST"));
				if(!(prodOfferList!=null && prodOfferList.size()>0)){
					return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "用户不存在套餐");
				}
				for(Map<String, Object> prodOffer : prodOfferList){
					if(prodOffer.get("OFFER_TYPE")!=null && "11".equals(prodOffer.get("OFFER_TYPE").toString())){
						try {
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = new Date();
							//开始时间
							String effDate = prodOffer.get("EFF_DATE").toString();
							if(date.getTime()>(df.parse(effDate)).getTime()){
								//套餐名称
								resultMap.put("offerName", prodOffer.get("PROD_OFFER_NAME").toString());
								//套餐说明
								resultMap.put("offerDesc", prodOffer.get("OFR_DESC").toString());
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
				//手机号码
				resultMap.put("mobile", cust.get("MOBILE_170").toString());
				//最低消费
				resultMap.put("minConsumption", cust.get("USER_MON_LIMIT").toString());
				String ON_NET_LIMIT = "0";
				if(cust.get("ON_NET_LIMIT")!=null)ON_NET_LIMIT = cust.get("ON_NET_LIMIT").toString();
				//合约期
				resultMap.put("contractNum", ON_NET_LIMIT);
				
				String createDate = "";
				if(prodInst.get("CREATE_DATE")!=null){
					createDate = prodInst.get("CREATE_DATE").toString();
				}
				//入网时间
				resultMap.put("createDate", createDate);
			}else{
				return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, userInfo.get(HhConstants.MESSAGE).toString());
			}
		}
		
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> req = new HashMap<String,Object>();
		
		Map<String,Object> number = new HashMap<String,Object>();
		number.put("EXT_SYSTEM", HhConstants.EXT_SYSTEMVALUE);
		number.put("CHANNEL_CODE", HhConstants.CHANNEL_CODEVALUE);
		number.put("RES_NBR", usermsgvo.getQry_number());
		
		Map<String,Object> pubReq = new HashMap<String,Object>();
		pubReq.put("TYPE", "NUM_QRY");
		
		req.put("NUMBER", number);
		req.put("PUB_REQ", pubReq);
		
		sooList.add(req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS 号码信息查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001037", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS 号码信息查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String mssg = (String) resultResultMap.get("Msg");
		if(HhConstants.SUCCESS.equals(status)){
			Map<String,Object> serviceInfo = ((List<Map<String,Object>>)resultsooMap.get("SERVICE_INFO")).get(0);
			if(serviceInfo.get("NBR_LEVEL")!=null){
				//号码类型
				if("0".equals(serviceInfo.get("NBR_LEVEL").toString())){
					resultMap.put("numberType", "普号");
				}else{
					resultMap.put("numberType", "靓号");
				}
			}else{
				return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, "号码等级不存在");
			}
		}else{
			return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mssg);
		}
		log.info("*************************APP 套餐查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(DesEncryptUtil.decrypt(JsonResponse.toSuccessResult(resultMap))));
		return JsonResponse.toSuccessResult(resultMap);
	}

	/**
	 * 销售报表查询
	 */
	@Override
	public String getSaleQuery(AgentSaleQuery query) {
		log.info("*************************APP 销售报表查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(query).toString());
		
		if(StringUtils.isEmpty(query.getStaff_id())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "代理商工号为空");
		}
		if(StringUtils.isEmpty(query.getExt_system())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "系统标识为空");
		}
		if(StringUtils.isEmpty(query.getChannel_code())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "渠道标识为空");
		}
		if(StringUtils.isEmpty(query.getStart_date())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "开始时间为空");
		}
		if(StringUtils.isEmpty(query.getEnd_date())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "结束时间为空");
		}
		
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> req = new HashMap<String,Object>();
		req.put("START_DATE", query.getStart_date());
		req.put("END_DATE", query.getEnd_date());
		Map<String,Object> pub_req = new HashMap<String,Object>();
		pub_req.put("TYPE", "AGENT_SALE_QUERY");
		req.put("PUB_REQ", pub_req);
		Map<String,Object> agent_info = new HashMap<String,Object>();
		agent_info.put("CHANNEL_CODE", query.getChannel_code());
		agent_info.put("EXT_SYSTEM", query.getExt_system());
		agent_info.put("STAFF_ID", query.getStaff_id());
		req.put("AGENT_INFO", agent_info);
		
		sooList.add(req);
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS销售报表查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001067", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS销售报表查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String mssg = (String) resultResultMap.get("MSG");
		if(HhConstants.SUCCESS.equals(status)){
			Map<String,Object> serviceInfo = (Map<String,Object>)resultResultMap.get("SALE_INFO");
			resultMap.put("act_user_num", serviceInfo.get("ACT_USER_NUM"));
			resultMap.put("new_user_num", serviceInfo.get("NEW_USER_NUM"));
			resultMap.put("recharge_user_num", serviceInfo.get("RECHARGE_USER_NUM"));
			resultMap.put("recharge_amount", serviceInfo.get("RECHARGE_AMOUNT"));
		}else{
			return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, mssg);
		}
		log.info("*************************APP 销售报表查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(DesEncryptUtil.decrypt(JsonResponse.toSuccessResult(resultMap))));
		return JsonResponse.toSuccessResult(resultMap);
		
	}
	
}
