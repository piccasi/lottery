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

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.AgentBalanceDeQryServiceServ;
import com.tydic.uniform.hh.vo.rep.AgentBalanceDeQryVo;

import net.sf.json.JSONObject;
/**
 * 
* @ClassName: AgentBalanceDeQryServiceImpl 
* @Description: 代理商账户费用查询
* @author 天源迪科
* @date 2016年9月1日 下午4:32:54 
*
 */
@Service("AgentBalanceDeQryServiceServ")
public class AgentBalanceDeQryServiceImpl extends AbstractService implements AgentBalanceDeQryServiceServ {
	public final String dayNames[] = { "周日", "周一", "周二", "周三", "周四", "周五","周六" };
	private static Logger log = Logger.getLogger(AgentBalanceDeQryServiceImpl.class);
	/**
	 * @author 2016年9月1日16:43:03
	 * @Method: agentBalanceDeQryMethod 
	 * @Description: 代理商账户费用查询
	 * @param agentbalancedeqryvo
	 * @return 
	 * @see 
	 */
	
	@Override
	public Map<String, Object> agentBalanceDeQryMethod(AgentBalanceDeQryVo agentbalancedeqryvo) {

		/*
		 * 解析能力平台出参,形成自己的报文
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("*************************APP代理商账户费用查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(agentbalancedeqryvo).toString());
		System.out.println("APP request str:" + JSONObject.fromObject(agentbalancedeqryvo).toString());
		/*,.
		 * APP入参解析
		 */
		List<Map<String,Object>> sooList = new ArrayList<Map<String,Object>>();
		Map<String,Object> sooListMap = new HashMap<String,Object>();
		Map<String,Object> agentBalanceDeQryMap = new HashMap<String,Object>();
		agentBalanceDeQryMap.put("StaffId", agentbalancedeqryvo.getStaffId());
		agentBalanceDeQryMap.put("PayAcct", agentbalancedeqryvo.getPayAcct());
		agentBalanceDeQryMap.put("BeginDate", agentbalancedeqryvo.getBeginDate());
		agentBalanceDeQryMap.put("EndDate", agentbalancedeqryvo.getEndDate());
		agentBalanceDeQryMap.put("PageNumber", "1");
		agentBalanceDeQryMap.put("PageSize", "10000");
		
		Map<String,Object> typeMap = new HashMap<String,Object>();
		typeMap.put("TYPE", "AgentBalanceDetailQry");
		
		sooListMap.put("AgentBalanceDetailQry", agentBalanceDeQryMap);
		sooListMap.put("PUB_REQ", typeMap);
		sooList.add(0, sooListMap);
		Map body = new HashMap();
		body.put("SOO", sooList);
		log.info("*************************BOSS代理商账户费用查询接口出参*************************");
		log.info("BOSS request str:" + JSONObject.fromObject(body).toString());
		/*
		 * 请求接口
		 */
		setIntefaceType(IntefaceType.BILL);
		/*
		 * 调用能力平台
		 */
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003040", ResponseBean.class);
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get("Result");
		String msg = (String) resultResultMap.get("Msg");
		Integer totalAmount = (Integer) resultResultMap.get("TotalAmount");
		
		
		 if(HhConstants.SUCCESS.equals(status)){
			 resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
			 resultMap.put(HhConstants.RESULT, status);
			 resultMap.put(HhConstants.MESSAGE, msg);
			 resultMap.put("TotalAmount", totalAmount);
			 
			 if (resultResultMap.containsKey("BalanceQryInfo")) {
				List<Map<String,Object>> balanceQryInfoMap= (List<Map<String, Object>>) resultResultMap.get("BalanceQryInfo");
				 for (Map<String, Object> map : balanceQryInfoMap) {

						String rechargeTime = map.get("DateSys").toString();
//						String date_string = rechargeTime.substring(0,4)+"-"+ rechargeTime.substring(4,6)+"-"+ rechargeTime.substring(6,8);
						 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
						 Calendar calendar = Calendar.getInstance();
						 try {
							 Date date = sdf.parse(rechargeTime);
							 calendar.setTime(date);
							int year = calendar.get(Calendar.YEAR);
						    int month = calendar.get(Calendar.MONTH) + 1; // 0-based!
						    int day = calendar.get(Calendar.DAY_OF_MONTH);

							int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
							if(dayOfWeek<0)dayOfWeek=0;
//							System.out.println(dayNames[dayOfWeek]);
							map.put("YEAR", String.valueOf(year));
							map.put("MONTH", String.valueOf(month));
							map.put("DAY", String.valueOf(day));
							map.put("WEEK", dayNames[dayOfWeek]);
						} catch (ParseException e) {
							e.printStackTrace();
						} 
				}
				 resultMap.put("BalanceQryInfo", balanceQryInfoMap);//成功
			}
			 
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
		    	resultMap.put(HhConstants.RESULT, status);
		    	resultMap.put(HhConstants.MESSAGE, msg);//失败描述
		    }
		    log.info("*************************APP代理商账户费用查询接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			
		return resultMap;
	
	}

}
