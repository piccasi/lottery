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
import com.tydic.uniform.hh.service.interfaces.RechargeOrderServiceServ;
import com.tydic.uniform.hh.vo.rep.RechargeOrderPayVo;
import com.tydic.uniform.hh.vo.rep.RechargeOrderVo;
import com.tydic.uniform.hh.vo.resp.RechargeOrderResp;

import net.sf.json.JSONObject;
/**
 * RechargeOrder
 * <p></p>
 * @author Administrator 2015年10月19日 下午2:41:24
 * @ClassName RechargeOrderServiceImpl
 * @Description 充值订单
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月19日
 * @modify by reason:{方法名}:{原因}
 */
@Service("RechargeOrderServiceServ")
public class RechargeOrderServiceImpl extends AbstractService implements RechargeOrderServiceServ {
	private static Logger log = Logger.getLogger(RechargeOrderServiceImpl.class);
	@Override
	public Map<String, Object> rechargeorderserviceserv(RechargeOrderVo rechargeordervo) {
		 	log.info("*************************APP充值订单接口入参*************************");
			log.info("APP request str:" + JSONObject.fromObject(rechargeordervo).toString());
			/*
			 * 按照去支付接口入参
			 */
			Map<String,Object> req = new HashMap<String,Object>();
			req.put(HhConstants.ORDERFROM, rechargeordervo.getOrderfrom());
			req.put(HhConstants.ORDERFROMVIEW, rechargeordervo.getOrderfromview());
			List<RechargeOrderPayVo> rechargeorderpayvolist = rechargeordervo.getOrderpaymentslist();
			Map<String,Object> reqMap= new HashMap<String,Object>();
			List<Map<String,Object>> reqList = new ArrayList<Map<String,Object>>();
			if (rechargeorderpayvolist!=null) {
				for (int i = 0; i < rechargeorderpayvolist.size(); i++) {
					RechargeOrderPayVo rechargeOrderPayVo = rechargeorderpayvolist.get(i);
					reqMap.put(HhConstants.PAYMODE, rechargeOrderPayVo.getPaymode());
					reqMap.put(HhConstants.PAYMODEVIEW, rechargeOrderPayVo.getPaymodeview());
					reqList.add(reqMap);
				}
			}
			req.put(HhConstants.ORDERPAYMENTS, reqList); //数组
			
			Map<String,Object> orderrechargeMap=new HashMap<String,Object>();
			orderrechargeMap.put(HhConstants.RCGAMT, rechargeordervo.getRcgamt());
			orderrechargeMap.put(HhConstants.RCGMOBILE, rechargeordervo.getRcgmobile());
			
			req.put(HhConstants.ORDERRECHARGE, orderrechargeMap);//对象
			
			req.put(HhConstants.ORDERREMARK, rechargeordervo.getOrderremark());
			req.put(HhConstants.ORDERSOURCE, rechargeordervo.getOrdersource());
			req.put(HhConstants.ORDERSOURCEVIEW, rechargeordervo.getOrdersourceview());
			req.put(HhConstants.PAYAMT, rechargeordervo.getPayamt());
			req.put(HhConstants.PROMOTIONAMT, rechargeordervo.getPromotionamt());
			req.put(HhConstants.TOTALAMT, rechargeordervo.getTotalamt());
			req.put(HhConstants.TOTALAMTVIEW, rechargeordervo.getTotalamtview());
			req.put(HhConstants.RECHARGETYPE, rechargeordervo.getType());
			req.put(HhConstants.TYPEVIEW, rechargeordervo.getTypeview());
			req.put(HhConstants.USERACCOUNT, rechargeordervo.getUseraccount());
			req.put(HhConstants.ORDERFLAG, rechargeordervo.getOrderflag());

//			Map<String,Object> body = new HashMap<String,Object>();
//			body.put("BODY", req);
			/*
			 * 请求接口
			 */
			log.info("*************************BOSS充值订单接口入参*************************");
			log.info("BOSS response str:" + req.toString());
			setIntefaceType(IntefaceType.ESHOP);
			ResponseBean bean = (ResponseBean) this.appApiInvoke(req,"SC1002004", ResponseBean.class);
			
//			JSONObject resultJson = JSONObject.fromObject(rspJson);
			log.info("*************************BOSS充值订单接口出参*************************");
			log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
			/*
			 * 解析接口返参
			*/
			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			Map<String,Object> resulsooMap = (Map<String,Object>) bean.getBody().get(0);
			Map<String,Object> resultResultMap = (Map<String,Object>) resulsooMap.get("rspData");
			String status = (String) resulsooMap.get(HhConstants.ESHOPRESULT);
			
			RechargeOrderResp rechargeOrderResp = new RechargeOrderResp();
			
			   if(HhConstants.SUCCESS.equals(status)){
				   Map<String,Object> querMap=new HashMap<String,Object>();
				   Map<String,Object> retMap=new HashMap<String,Object>();
				  if (resulsooMap.containsKey(HhConstants.ESHOPREPDATA)) {
					 String service_infoMap=(String) resultResultMap.get(HhConstants.ESHOPCRMORDERNUMBER);
					 String service_iMap=(String) resultResultMap.get(HhConstants.ESHOPCRMORDERNUMBER);
					 retMap.put("crmordernumber", service_iMap);
				}
				  querMap.put("rspdata", retMap);
				  querMap.put("rspcode", HhConstants.SUCCESS);
				  rechargeOrderResp.setMapInfo(querMap);
			    }else if(HhConstants.ERROR.equals(status)){
			    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
					resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			    }
			    resultMap.put("rechargeOrderResp", rechargeOrderResp);
			    log.info("*************************APP充值订单接口出参*************************");
				log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
			return resultMap;
	}

}
