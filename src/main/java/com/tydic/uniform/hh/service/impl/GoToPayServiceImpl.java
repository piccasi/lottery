package com.tydic.uniform.hh.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.GoToPayServiceServ;
import com.tydic.uniform.hh.vo.rep.GoToPayVo;
import com.tydic.uniform.hh.vo.resp.GoToPayResp;
import net.sf.json.JSONObject;
/**
 * GoToPay
 * <p></p>
 * @author Administrator 2015年10月19日 上午11:13:40
 * @ClassName GoToPayServiceImpl
 * @Description 去支付
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月19日
 * @modify by reason:{方法名}:{原因}
 */
@Service("GoToPayServiceServ")
public class GoToPayServiceImpl extends AbstractService implements GoToPayServiceServ {
	private static Logger log = Logger.getLogger(Judge170NumberServiceImpl.class);
	@Override
	public Map<String, Object> gotopayserviceserv(GoToPayVo gotopayvo) {
	    log.info("*************************APP号码查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(gotopayvo).toString());
		/*
		 * 按照去支付接口入参
		 */
		Map<String,Object> req = new HashMap<String,Object>();
		req.put(HhConstants.ORDERNUMBER, gotopayvo.getOrdernumber());
		req.put(HhConstants.PAYMODE, gotopayvo.getPaymode());
		req.put(HhConstants.RETURNURL, gotopayvo.getReturnurl());

//		Map<String,Object> body = new HashMap<String,Object>();
//		body.put("BODY", req);
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS去支付接口入参*************************");
		log.info("BOSS response str:" + req.toString());
		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(req,"SC1002005", ResponseBean.class);
		
//		JSONObject resultJson = JSONObject.fromObject(rspJson);
		log.info("*************************BOSS去支付接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
		 * 解析接口返参
		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Map<String,Object> resulsooMap = (Map<String,Object>) bean.getBody().get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resulsooMap.get("rspData");
		String status = (String) resulsooMap.get(HhConstants.ESHOPRESULT);
		GoToPayResp goToPayResp = new GoToPayResp();
		
		   if(HhConstants.SUCCESS.equals(status)){
			   Map<String,Object> querMap=new HashMap<String,Object>();
			   Map<String,Object> retMap=new HashMap<String,Object>();
			  if (resultResultMap.containsKey(HhConstants.ESHOPREPDATA)) {
				 //Map<String,Object> service_infoMap=(Map<String,Object>) resultResultMap.get(HhConstants.ESHOPREPDATA);
				 String service_iMap=(String) resultResultMap.get(HhConstants.ESHOPTOPAYLINK);
				 retMap.put("topaylink", service_iMap);
			}
			  querMap.put("rspdata", retMap);
			  querMap.put("rspcode", HhConstants.SUCCESS);
			  goToPayResp.setMapInfo(querMap);
		    }else if(HhConstants.ERROR.equals(status)){
		    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
		    }
		    resultMap.put("goToPayResp", goToPayResp);
		    log.info("*************************APP去支付接口出参*************************");
			log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
		return resultMap;
	}
	
}
