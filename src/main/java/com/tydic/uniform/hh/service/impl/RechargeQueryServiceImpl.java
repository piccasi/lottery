package com.tydic.uniform.hh.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.RechargeQueryServiceServ;
import com.tydic.uniform.hh.vo.rep.RechargeQueryVo;

import net.sf.json.JSONObject;

@Service("RechargeQueryServiceServ")

public class RechargeQueryServiceImpl extends AbstractService implements RechargeQueryServiceServ {
	private static Logger log = Logger.getLogger(RechargeQueryServiceImpl.class);
	public final String dayNames[] = { "周日", "周一", "周二", "周三", "周四", "周五","周六" };
	@SuppressWarnings("unchecked")
	@Override
	public String rechargeQuery(RechargeQueryVo rechargeQueryVo) {
		log.info("*************************空中充值记录查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(rechargeQueryVo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());

//		int pageSize = rechargeQueryVo.getPageSize();
//		int pageIndex = rechargeQueryVo.getPageNo();

		Map<String, Object> pub_req = new HashMap<String, Object>();
		Map<String, Object> rechargeQry_req = new HashMap<String, Object>();
		rechargeQry_req.put("RequestSource", rechargeQueryVo.getRequestSource());
		rechargeQry_req.put("RequestUser", rechargeQueryVo.getRequestUser());
		rechargeQry_req.put("RequestId", rechargeQueryVo.getRequestId());
		rechargeQry_req.put("Type", rechargeQueryVo.getType());
		rechargeQry_req.put("StaffId", rechargeQueryVo.getStaffId());
		rechargeQry_req.put("PayAcct", rechargeQueryVo.getPayAcct());
		rechargeQry_req.put("Value", rechargeQueryVo.getValue());
		rechargeQry_req.put("BeginTime", rechargeQueryVo.getBeginTime());
		rechargeQry_req.put("EndTime", rechargeQueryVo.getEndTime());

		pub_req.put(HhConstants.TYPE, "RechargeQry");
		sooList.get(0).put(HhConstants.PUB_REQ, pub_req);
		sooList.get(0).put("RECHARGEQRY_REQ", rechargeQry_req);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put("SOO", sooList);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS空中充值记录查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1003036", ResponseBean.class);
		log.info("*************************BOSS空中充值记录查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultSooList = (List<Map<String, Object>>) bean.getBody().get(0)
				.get(HhConstants.SOO);
		Map<String, Object> resultsooMap = (Map<String, Object>) resultSooList.get(0);
		Map<String, Object> resultResultMap = (Map<String, Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
		if (HhConstants.SUCCESS.equals(status)) {
			List<Map<String, Object>> queryList = (List<Map<String, Object>>) (JSONObject.fromObject(resultResultMap)
					.get("Query-Item"));
			
			List<Map<String,Object>> totalList = new ArrayList<>();
			
			if(null != queryList){
				for (Map<String, Object> queryListMap : queryList) {
					String rechargeTime = queryListMap.get("rechargeTime").toString();
					String date_string = rechargeTime.substring(0,4)+"-"+ rechargeTime.substring(4,6)+"-"+ rechargeTime.substring(6,8);
					 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
					 Calendar calendar = Calendar.getInstance();
					 try {
						 Date date = sdf.parse(date_string);
						 calendar.setTime(date);
						int year = calendar.get(Calendar.YEAR);
					    int month = calendar.get(Calendar.MONTH) + 1; // 0-based!
					    int day = calendar.get(Calendar.DAY_OF_MONTH);

						int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
						if(dayOfWeek<0)dayOfWeek=0;
//						System.out.println(dayNames[dayOfWeek]);
						queryListMap.put("YEAR", String.valueOf(year));
						queryListMap.put("MONTH", String.valueOf(month));
						queryListMap.put("DAY", String.valueOf(day));
						queryListMap.put("WEEK", dayNames[dayOfWeek]);
						
						boolean l = true;
						for(Map<String,Object> map:totalList){
							if(map.get("reMonth").equals(String.valueOf(month))){
								l = false;
								List<Map<String, Object>> neList = (List<Map<String, Object>>) map.get("reList");
								neList.add(queryListMap);
							}
						}
						if(l){
							Map<String,Object> neMap = new HashMap<String,Object>();
							neMap.put("reMonth", String.valueOf(month));
							List<Map<String, Object>> neList = new ArrayList<>();
							neList.add(queryListMap);
							neMap.put("reList", neList);
							totalList.add(neMap);
						}
						
					} catch (ParseException e) {
						e.printStackTrace();
					} 
				}
			}
			resultMap.put("data", totalList);// 成功
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.MSG, "成功");
		} else if ("100".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "输入参数缺失");// 失败描述
		} else if ("101".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "输入参数格式错误");// 失败描述
		} else if ("300".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			resultMap.put(HhConstants.MSG, "成功");
		} else if ("700".equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "系统异常");// 失败描述
		}else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//
		} 
		log.info("*************************APP空中充值记录查询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		
		if(HhConstants.SUCCESSCODE.equals(resultMap.get(HhConstants.CODE))){
			return JsonResponse.toSuccessResult(resultMap);
		}else{
			return JsonResponse.toErrorResult(CODE.INTERFACE_ERR, (String)resultMap.get(HhConstants.MESSAGE));
		}
		
		
	}

}
