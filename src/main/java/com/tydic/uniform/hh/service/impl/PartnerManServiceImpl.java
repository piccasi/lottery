package com.tydic.uniform.hh.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.DButils;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.PartnerManServiceServ;
import com.tydic.uniform.hh.util.DESUtil;
import com.tydic.uniform.hh.util.DateUtil;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.vo.rep.PartnerVo;
import com.tydic.uniform.hh.vo.resp.PartnerResp;
/**
 * <p></p>
 * @author Administrator 2015年10月16日 下午4:05:02
 * @ClassName PartnerManServiceImpl
 * @Description 通信合伙人
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月16日
 * @modify by reason:{方法名}:{原因}
 */
@Service("PartnerManServiceServ")
public class PartnerManServiceImpl  extends AbstractService implements PartnerManServiceServ {
	private static Logger log = Logger.getLogger(PartnerManServiceImpl.class);

	/**
	 *  全民营销-获取我赚的话费信息
	 */
	@Override
	public Map<String, Object> getTelPhoneCharege(PartnerVo partnerVo) {
		/*
		 * 按照BOSS文档设置接口入参
		 */
		Map paramMap = new HashMap();
		paramMap.put("SYSTEM_ID", HhConstants.EXT_SYSTEMVALUE);
		paramMap.put("STAFF_ID", HhConstants.STAFF_IDMAVLUE);
		//获取当前170用户号码
		if(null==partnerVo||partnerVo.getMobile170()==null || partnerVo.getMobile170().toString().equals("")){
			paramMap.put("DEVICE_NUMBER","");
		}else{
			paramMap.put("DEVICE_NUMBER",partnerVo.getMobile170());
		}
		
		//获取当月已赚话费
		if(null!=partnerVo&&null!=partnerVo.getDateflag()&&partnerVo.getDateflag().equals("1"))
		{
			//1、获取当前月第一天
	    	Map dateMap = new HashMap();
	    	Calendar calendarStart = Calendar.getInstance();
	    	calendarStart.add(Calendar.MONTH, 0);
	    	calendarStart.set(Calendar.DAY_OF_MONTH, 1);
	    	Date startDateTo = calendarStart.getTime();     	
	    	
			paramMap.put("EFF_DATE", DateUtil.formatDate((startDateTo),"yyyy-MM-dd"));
			
			Date date = new Date();
			paramMap.put("EXP_DATE", DateUtil.formatDate(date,"yyyy-MM-dd"));
		}
		else if(null!=partnerVo&&null!=partnerVo.getDateflag()&&partnerVo.getDateflag().equals("2"))
		{
			//1、获取当前月第一天
			paramMap.put("EFF_DATE", DateUtil.formatDate(DateUtil.addDate(Calendar.YEAR, -2),"yyyy-MM-dd"));	
			
			Date date = new Date();
			paramMap.put("EXP_DATE", DateUtil.formatDate(date,"yyyy-MM-dd"));
		}
		Map tmpMap = new HashMap();
		tmpMap.put("PRESENT_INFO", paramMap);
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, tmpMap);
		
		Map body = new HashMap();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************获取我赚的话费信息查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003030", ResponseBean.class);
		log.info("*************************获取我赚的话费信息接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		
		PartnerResp partnerResp = new PartnerResp();
		 Map<String,Object> mapInfo = new HashMap<>();
		 if(HhConstants.SUCCESS.equals(status)){
	    	if(resultsooMap.containsKey(HhConstants.RESP)){
	    		mapInfo.put("balance", resultResultMap.get("Balance")!=null?resultResultMap.get("Balance"):0);
	    		mapInfo.put("presentbalance", resultResultMap.get("PresentBalance")!=null?resultResultMap.get("PresentBalance"):0);
	    		mapInfo.put("presentnumber", resultResultMap.get("PresentNumber")!=null?resultResultMap.get("PresentNumber"):0);
	    	}
	    	 partnerResp.setMapInfo(mapInfo);
    		 resultMap.put(HhConstants.CODE,HhConstants.SUCCESSCODE);
    		 resultMap.put(HhConstants.RESULT, HhConstants.SUCCESS);
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    }
	    resultMap.put("partnerresp", partnerResp);
		return resultMap;
	}

	/**
	 *  全民营销-查询数据库内排行榜信息
	 * @throws SQLException 
	 */
	@Override
	public Map<String, Object> getQmyxRankList(PartnerVo partnerVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Map reqMap = new HashMap();
			reqMap.put("rankType", partnerVo.getRanktype());
			reqMap.put("sendPrizeState", "1");
			reqMap.put("batchRankType", Integer.parseInt(partnerVo.getBatchranktype().toString()));
			
			String sql="select * from (select to_number(a.ranking)ranking,a.user_name,a.user_mobile,a.user_sex, a.total_revenue,to_char(wm_concat(prize_name)) prize_name,a.state,to_char(wm_concat(send_prize_id)) sendprize_id "
					+ "from(select a.*,c.prize_name,b.state,b.send_prize_id from TB_QMYX_RANKLIST a,TB_QMYX_SENDPRIZE  b,TB_QMYX_PRIZE_INFO c "
					+ "where a.rank_id = b.rank_id and b.prize_id = c.prize_id and a.rank_type = "+reqMap.get("rankType")+" and b.state = "+reqMap.get("sendPrizeState")+") a where a.batch = (select max(b.batch) from TB_QMYX_BATCH b where b.rank_type = "+reqMap.get("batchRankType")+") "
					+ "group by a.ranking,a.user_name,a.user_mobile,a.user_sex,a.total_revenue,a.state order by ranking) where ROWNUM <= 5";
			List<Map> list = DButils.getInstance().executeSql(sql);
			System.out.println(list.size()+"");
			if(list!=null&&list.size()>0){
				ArrayList<Object> paihang = new ArrayList<Object>();
				for(int i=0;i<list.size();i++){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ranking", list.get(i).get("RANKING"));
					map.put("state", list.get(i).get("STATE"));
					map.put("prize_name", list.get(i).get("PRIZE_NAME"));
					map.put("sendprize_id", list.get(i).get("SENDPRIZE_ID"));
					map.put("user_name", list.get(i).get("USER_NAME"));
					map.put("user_mobile", list.get(i).get("USER_MOBILE"));
					map.put("total_revenue", list.get(i).get("TOTAL_REVENUE"));
					map.put("user_sex", list.get(i).get("USER_SEX"));
					paihang.add(map);
				}
				Map<String,Object> mapInfo = new HashMap<>();
				mapInfo.put("list", paihang);
				PartnerResp partnerResp = new PartnerResp();
		    	 partnerResp.setMapInfo(mapInfo);
	    		 resultMap.put(HhConstants.CODE,HhConstants.SUCCESSCODE);
	    		 resultMap.put(HhConstants.RESULT, HhConstants.SUCCESS);
			    resultMap.put("partnerresp", partnerResp);
			    return resultMap;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Map<String, Object> getShareUrl(PartnerVo partnerVo) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String qrcodeUrl = PropertiesUtil.getPropertyValue("qrcodeUrl");
		String wxshareUrl = PropertiesUtil.getPropertyValue("wxshareUrl");
//		wxshareUrl= "http://test.hna170.com/wap/resources/pages/activity/marketing/chat.jsp?aid=4ccf7d0873e134a5";
//		qrcodeUrl = "http://test.hna170.com/web/share.html";
		
		try {
			Map reqMap = new HashMap();
			String info = partnerVo.getInfo();
			com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(info);
			String url = qrcodeUrl+"?a="+DESUtil.encryptStr(jsonObject.getString("mobile"));
			
			String wxurl = wxshareUrl+"&userInfo="+DESUtil.encryptWxStr(info);
			
			Map<String,Object> mapInfo = new HashMap<>();
			mapInfo.put("qrcodeurl", url);
			mapInfo.put("wxurl", wxurl);
			PartnerResp partnerResp = new PartnerResp();
	    	 partnerResp.setMapInfo(mapInfo);
			 resultMap.put(HhConstants.CODE,HhConstants.SUCCESSCODE);
			 resultMap.put(HhConstants.RESULT, HhConstants.SUCCESS);
		    resultMap.put("partnerresp", partnerResp);
		    return resultMap;
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}