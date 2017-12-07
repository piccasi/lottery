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
import com.tydic.uniform.hh.service.interfaces.RechargeRecordServiceServ;
import com.tydic.uniform.hh.vo.rep.RechargeRecordVo;
import com.tydic.uniform.hh.vo.resp.RechargeRecordResp;

import net.sf.json.JSONObject;

/**
 * <p></p>
 * @author ghp 2015年10月15日 下午6:27:09
 * @ClassName RechargeRecordServiceImpl
 * @Description TODO  1.10 会员充值记录查询实体
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月15日
 * @modify by reason:{方法名}:{原因}
 */
@Service("RechargeRecordServiceServ")
public class RechargeRecordServiceImpl extends AbstractService implements RechargeRecordServiceServ{
	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(RechargeRecordServiceImpl.class);

	@Override
	public Map<String, Object> rechargerecord(RechargeRecordVo rechargerecordvo) {
		
		
		log.info("*************************APP会员充值记录查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(rechargerecordvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		
		Map req = new HashMap();
		req.put(HhConstants.BILLTYPE, rechargerecordvo.getType());
		req.put(HhConstants.BILLVALUE, rechargerecordvo.getValue());//rechargerecordvo.getValue()
		req.put(HhConstants.BILLCYCLEID, rechargerecordvo.getBillcycleid());
		
		req.put(HhConstants.BILLPAGENUMBER, rechargerecordvo.getPageNo());//从1开始
		req.put(HhConstants.BILLPAGESIZE,rechargerecordvo.getPageSize());//
		req.put(HhConstants.SYSTEMID, "1");
		
		sooList.get(0).put(HhConstants.PAYMENTINFOQUERY,req);
		
		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "RechargeInfo");
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);

		

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS会员充值记录查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());

		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1003010", ResponseBean.class);
		

		log.info("*************************BOSS会员充值记录查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSooList.get(0);
		Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
		String code = (String) resultResultMap.get("Code");
		
		RechargeRecordResp rechargerecordresp = new RechargeRecordResp();
	    if(HhConstants.SUCCESS.equals(status)){
	    	List paymentInfolist=(List) resultResultMap.get("PaymentInfo");
			if(paymentInfolist.size()>0){
				Map<String,Object> mapInfo = new HashMap<>();
		    	ArrayList<Object> resplist = new ArrayList<Object>();
		    	 for(int i=0; i<paymentInfolist.size(); i++){
	    			 Map paymentInfomap = (Map) paymentInfolist.get(i);
	    			 HashMap respmap =new HashMap();
	    			 respmap.put(HhConstantsResp.LEFTBALANCE, paymentInfomap.get(HhConstants.LEFTBALANCE));
	    			 respmap.put(HhConstantsResp.PAYAMOUNT, paymentInfomap.get(HhConstants.PAYAMOUNT));
	    			 respmap.put(HhConstantsResp.PAYCHANNEL, paymentInfomap.get(HhConstants.PAYCHANNEL));
	    			 respmap.put(HhConstantsResp.PAYDATE, paymentInfomap.get(HhConstants.PAYDATE));
	    			 respmap.put(HhConstantsResp.PAYSERIALNBR, paymentInfomap.get(HhConstants.PAYSERIALNBR));
	    			 respmap.put(HhConstantsResp.PREBALANCE, paymentInfomap.get(HhConstants.PREBALANCE));
	    			 respmap.put(HhConstantsResp.SYSTEM_USER_CODE, paymentInfomap.get(HhConstants.SYSTEM_USER_CODE));
	    			 
	    			 resplist.add(respmap);
	    		 }
	    		 mapInfo.put(HhConstantsResp.PAYMENTINFO, resplist);
	    		 
	    		 rechargerecordresp.setMapInfo(mapInfo);
			}
			resultMap.put(HhConstants.ENDPAGE, HhConstants.ENDPAGEVALUE);//成功
	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
	    	
	    	
	    	
	    }else if(HhConstants.ERROR.equals(status)){
	    	//如果查不到记录，返回成功
	    	if(code!=null&&code.equals("300")){
	    		resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);
	    	}else{
	    		resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
	    	}
	    }
	    resultMap.put("rechargerecordresp", rechargerecordresp);
	    log.info("*************************APP会员充值记录查询接口出参*************************");
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//
//    	ArrayList<Object> resplist = new ArrayList<Object>();
//    	 for(int i=0; i<30; i++){
//
//			 HashMap respmap =new HashMap();
//			 respmap.put(HhConstantsResp.LEFTBALANCE, "7.77");
//			 respmap.put(HhConstantsResp.PAYAMOUNT, "77.77");
//			 respmap.put(HhConstantsResp.PAYCHANNEL, "test");
//			 respmap.put(HhConstantsResp.PAYDATE, "2015-03-02 03:17:59");
//			 respmap.put(HhConstantsResp.PAYSERIALNBR, "8888888888882");
//			 respmap.put(HhConstantsResp.PREBALANCE, "6.66");
//			
//			 resplist.add(respmap);
//		 }
//    	 resultMap.put(HhConstantsResp.PAYMENTINFO, resplist);
//    	 resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
// 		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}

}
