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

import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.QueryAgentUserNumServiceServ;
import com.tydic.uniform.hh.vo.rep.QueryAgentUserNumVo;

import net.sf.json.JSONObject;

@Service("QueryAgentUserNumServiceServ")
public class QueryAgentUserNumServiceImpl extends AbstractService implements QueryAgentUserNumServiceServ {

	private static Logger log = Logger.getLogger(QueryAgentUserNumServiceImpl.class);
	public final String dayNames[] = { "周日", "周一", "周二", "周三", "周四", "周五","周六" };
	/**
	 * @author ghp 2016年8月19日14:48:39
	 * @Method: queryAgentUserNumMethod 
	 * @Description: 代理商代理商app用户数/销售明细查询接口入参
	 * @param queryagentusernumvo
	 * @return 
	 */
	@Override
	public Map<String, Object> queryAgentUserNumMethod(QueryAgentUserNumVo queryagentusernumvo) {
		
		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP代理商app用户数/销售明细查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(queryagentusernumvo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(queryagentusernumvo).toString());
		/*,.
		 * APP入参解析
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		sooList.add(0, new HashMap<String,Object>());
		sooList.get(0).put("CHANNEL_CODE",queryagentusernumvo.getChannel_code());
		sooList.get(0).put("EXT_SYSTEM", "102");
		sooList.get(0).put("COUNT_TYPE", queryagentusernumvo.getCount_type());
		sooList.get(0).put("START_DATE", queryagentusernumvo.getStart_date());
		sooList.get(0).put("END_DATE", queryagentusernumvo.getEnd_date());
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		log.info("*************************BOSS代理商app用户数/销售明细查询接口入参*************************");
		log.info("BOSS request str:" + JSONObject.fromObject(body).toString());
		/*
		 * 请求接口
		 */
		setIntefaceType(IntefaceType.CRM);
		/*
		 * 调用能力平台
		 */
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001058", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String msg = (String) resultResultMap.get(HhConstants.MSG);

		 if(HhConstants.SUCCESS.equals(status)){
			 resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			 resultMap.put(HhConstants.RESULT, status);
			 resultMap.put(HhConstants.MESSAGE, msg);
					 if(resultsooMap.containsKey("USER_NUMBER")){
						 resultMap.put("USER_NUMBER", resultsooMap.get("USER_NUMBER"));
						 List<Map<String,Object>> user_numList = (List<Map<String, Object>>) resultsooMap.get("USER_NUMBER");
						 for (Map<String, Object> user_number_map : user_numList) {
							 String date_time = user_number_map.get("CREATE_DATE").toString();
							 String date_string = date_time.substring(0,4)+"-"+ date_time.substring(4,6)+"-"+ date_time.substring(6,8);
							 
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
//								System.out.println(dayNames[dayOfWeek]);
								user_number_map.put("YEAR", String.valueOf(year));
								user_number_map.put("MONTH", String.valueOf(month));
								user_number_map.put("DAY", String.valueOf(day));
								user_number_map.put("WEEK", dayNames[dayOfWeek]);
							} catch (ParseException e) {
								e.printStackTrace();
							} 
							 
						}
					 }
					 if (resultsooMap.containsKey("SALE_INFO")) {
						 resultMap.put("SALE_INFO", resultsooMap.get("SALE_INFO")); 
						 List<Map<String,Object>> user_numList = (List<Map<String, Object>>) resultsooMap.get("SALE_INFO");
						 for (Map<String, Object> user_number_map : user_numList) {
							 String date_time = user_number_map.get("SALE_DATE").toString();
							 String date_string = date_time.substring(0,4)+"-"+ date_time.substring(4,6)+"-"+ date_time.substring(6,8);
							 
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
//								System.out.println(dayNames[dayOfWeek]);
								user_number_map.put("YEAR", String.valueOf(year));
								user_number_map.put("MONTH", String.valueOf(month));
								user_number_map.put("DAY", String.valueOf(day));
								user_number_map.put("WEEK", dayNames[dayOfWeek]);
							} catch (ParseException e) {
								e.printStackTrace();
							} 
							 
						}
					}
				
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.RESULT, status);
		    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
		    }
		    log.info("*************************APP代理商app用户数/销售明细查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			
		return resultMap;
	}

}
