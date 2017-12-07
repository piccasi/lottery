/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author yiyaohong
 * @date: 2015年10月14日 上午10:42:04
 * @Title: BillInfoServiceImpl.java
 * @Package com.tydic.uniform.hh.service.impl
 * @Description: TODO
 */
package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.BillInfoServiceServ;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.util.ValidateCodeUtil;
import com.tydic.uniform.hh.vo.rep.BillInfoVo;
import com.tydic.uniform.hh.vo.rep.HistoryBillInfoVo;
import com.tydic.uniform.hh.vo.rep.HistoryBillPwdVo;
import com.tydic.uniform.hh.vo.rep.SMSInfoVo;
import com.tydic.uniform.hh.vo.resp.BillInfoResp;

/**
 * <p>
 * </p>
 * 
 * @author yiyaohong 2015年10月14日 上午10:42:04
 * @ClassName BillInfoServiceImpl 会员账单查询服务
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
@Service("BillInfoServiceServ")
public class BillInfoServiceImpl extends AbstractService implements BillInfoServiceServ {

	private static Logger log = Logger.getLogger(BillInfoServiceImpl.class);

	@Override
	public Map<String, Object> billInfoService(BillInfoVo billinfovo) {
		log.info("*************************APP会员账单查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(billinfovo).toString());

		List<Map<String, Object>> soolist = new ArrayList<Map<String, Object>>();
		soolist.add(0, new HashMap());

		Map<String, Object> pub_req = new HashMap<String, Object>();
		pub_req.put(HhConstants.TYPE, "BillInfo");

		Map<String, Object> billinfo_req = new HashMap<String, Object>();
		billinfo_req.put(HhConstants.Type, billinfovo.getType());
		billinfo_req.put(HhConstants.Value, billinfovo.getValue());
		billinfo_req.put(HhConstants.BillCycleId, billinfovo.getBillcycleid());
		billinfo_req.put(HhConstants.SystemId, "102");

		soolist.get(0).put(HhConstants.PUB_REQ, pub_req);
		soolist.get(0).put(HhConstants.BILLINFO_REQ, billinfo_req);

		Map<String, Object> body = new HashMap<String, Object>();
		body.put(HhConstants.SOO, soolist);

		/*
		 * 请求接口
		 */
		log.info("*************************BOSS会员账单查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1003006", ResponseBean.class);

		log.info("*************************BOSS会员账单查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());

		/**
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Map<String, Object>> resultsoolist = (List<Map<String, Object>>) bean.getBody().get(0)
				.get(HhConstants.SOO);

		Map<String, Object> soolistmap = resultsoolist.get(0);

		Map<String, Object> resultpub = (Map<String, Object>) soolistmap.get(HhConstants.PUB_REQ);
		String type = (String) resultpub.get(HhConstants.TYPE);

		Map<String, Object> resultresp = (Map<String, Object>) soolistmap.get(HhConstants.RESP);
		String result = (String) resultresp.get(HhConstants.Result);
		String msg = (String) resultresp.get(HhConstants.Msg);
		String code = (String) resultresp.get(HhConstants.Code);

		BillInfoResp billinforesp = new BillInfoResp();

		if (HhConstants.SUCCESS.equals(result)) {
			if ((resultresp.get(HhConstants.BillInfo)!=null)) {
				Map billinfo = (Map) resultresp.get(HhConstants.BillInfo);
				Map acctinfo = (Map) billinfo.get(HhConstants.AcctInfo);
				int xingcoin = (int) acctinfo.get(HhConstants.XingCoin);
				String cash = (String) acctinfo.get(HhConstants.Cash);
				List resources = (List) acctinfo.get(HhConstants.Resources);
				String billname = (String) billinfo.get(HhConstants.BillName);
				String tips = (String) billinfo.get(HhConstants.Tips);
				String servicenbr = (String) billinfo.get(HhConstants.ServiceNbr);
				String custname = (String) billinfo.get(HhConstants.CustName);
			
				ArrayList list = new ArrayList<>();
				for (int i = 0; i < resources.size(); i++) {
					Map resMap = (Map) resources.get(i);
					HashMap map = new HashMap();
					map.put(HhConstants.ExpiryDate, resMap.get(HhConstants.ExpiryDate));
					map.put(HhConstants.Value, resMap.get(HhConstants.Value));
					map.put(HhConstants.ResourceId, resMap.get(HhConstants.ResourceId));
					map.put(HhConstants.EffectiveDate, resMap.get(HhConstants.EffectiveDate));
					map.put(HhConstants.ResourceName, resMap.get(HhConstants.ResourceName));
					list.add(map);
				}
			
				
				List feeinfo = (List) billinfo.get(HhConstants.FeeInfo);
				ArrayList list1 = new ArrayList<>();
				for (int j = 0; j < feeinfo.size(); j++) {
					Map feemap = (Map) feeinfo.get(j);
					HashMap map1 = new HashMap();
					map1.put(HhConstants.BillItemName, feemap.get(HhConstants.BillItemName));
					map1.put(HhConstants.BillItemValue, feemap.get(HhConstants.BillItemValue));
					list1.add(map1);
				}
				

				Map billMap = new HashMap();
				Map acctMap = new HashMap();
				acctMap.put(HhConstants.XingCoin, xingcoin);
				acctMap.put(HhConstants.Cash, cash);
				acctMap.put(HhConstants.Resources, list);
				
				billMap.put(HhConstants.BillName, billname);
				billMap.put(HhConstants.Tips, tips);
				billMap.put(HhConstants.ServiceNbr, servicenbr);
				billMap.put(HhConstants.CustName, custname);
				billMap.put(HhConstants.FeeInfo, list1);
				billMap.put(HhConstants.AcctInfo, acctMap);
				
				
			/*	
				billMap.put(HhConstants.AcctInfo, acctMap); // 添加 acctInfo
				billMap.put(HhConstants.Resources, resource);
				billMap.put(HhConstants.FeeInfo, bill); // 添加feeInfo
				acctMap.put(HhConstants.XingCoin, xingcoin);
				acctMap.put(HhConstants.Cash, cash);*/

				Map respMap = new HashMap();
				respMap.put(HhConstants.Result, result);
				respMap.put(HhConstants.Msg, msg);
				respMap.put(HhConstants.Code, code);
				respMap.put(HhConstants.BillInfo, billMap);

				Map pub_reqMap = new HashMap();
				pub_reqMap.put(HhConstants.TYPE, type);

				billinforesp.setMapInfo(respMap);
			}
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
		} else if (HhConstants.ERROR.equals(result)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
		}
		resultMap.put("billinforesp", billinforesp);
		log.info("*************************APP会员账单查询接口出参*************************");
		log.info("APP response str:" + JSONObject.fromObject(resultMap).toString());
		
		return resultMap;
	}
	
	/**
	 * 查询我的账单详情 add by panxinxing 2016/01/07
	 */
	public Map<String,Object> historyBillInfoService(HistoryBillInfoVo historyBillInfoVo){
		log.info("*************************APP会员账单查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(historyBillInfoVo).toString());
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//若successFlag = true,此前验证了服务密码，则临时存储了成功信息，若successFlag = false,则服务密码验证过了5分钟或黑客跳过验证页面
		String key = "servicePwd" + historyBillInfoVo.getCust_id();
		boolean successFlag = ValidateCodeUtil.isSuccess(key, historyBillInfoVo.getCust_id());
		if(!successFlag){
			log.info("*************************服务密码超时或者未验证服务密码*************************");
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);
			//resultCode=1证明服务密码验证不通过
			resultMap.put("resultCode", "1");
			return resultMap;
		}
		//验证完毕后将验证信息继续存储
		ValidateCodeUtil.put(key, historyBillInfoVo.getCust_id());
		
		
		//查询详情
		List<Map<String, Object>> soolist1 = new ArrayList<Map<String, Object>>();
		soolist1.add(0, new HashMap<String, Object>());
		Map<String, Object> billinfo_req = new HashMap<String, Object>();
		billinfo_req.put("BeginDate", historyBillInfoVo.getBeginDate());
		billinfo_req.put("EndDate", historyBillInfoVo.getEndDate());
		billinfo_req.put("Index", historyBillInfoVo.getIndex());
		billinfo_req.put("MemberId", historyBillInfoVo.getCust_id());
		billinfo_req.put("QryType", historyBillInfoVo.getQryType());
		billinfo_req.put("Records", historyBillInfoVo.getRecords());
		soolist1.get(0).put("DETAILQRY", billinfo_req);
		Map<String, Object> body1 = new HashMap<String, Object>();
		body1.put(HhConstants.SOO, soolist1);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS会员账单查询接口入参*************************");
		log.info("BOSS response str:" + body1.toString());
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean1 = (ResponseBean) this.appApiInvoke(body1, "SC1003017", ResponseBean.class);

		log.info("*************************BOSS会员账单查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean1).toString());
		
		/**
		 * 解析接口返参
		 */
		List<Map<String, Object>> resultsoolist1 = (List<Map<String, Object>>) bean1.getBody().get(0).get(HhConstants.SOO);
		Map<String, Object> soolistmap1 = resultsoolist1.get(0);
		Map<String, Object> resultresp1 = (Map<String, Object>) soolistmap1.get(HhConstants.RESP);
		String status = (String) resultresp1.get(HhConstants.RESULT);
		String msg = (String) resultresp1.get(HhConstants.MSG);
		
		if(HhConstants.SUCCESS.equals(status)){
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//失败编码
			resultMap.put("resultCode", "0");
			resultMap.put("totalRecord", resultresp1.get("TotalRecord"));
			resultMap.put("PageCount", resultresp1.get("PageCount"));
			resultMap.put("callsInfo", resultresp1.get("callsInfo"));
			resultMap.put("smsInfo", resultresp1.get("smsInfo"));
			resultMap.put("dataInfo", resultresp1.get("dataInfo"));
			resultMap.put(HhConstants.MESSAGE, msg);
		}else{
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, msg);//失败描述
		}
		
		return resultMap;
	}
	
	public Map<String,Object> provPwd(HistoryBillPwdVo historyBillPwdVo){
		log.info("*************************APP会员账单查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(historyBillPwdVo).toString());
		
		//1.验证服务密码
		List<Map<String, Object>> sooList = new ArrayList<Map<String, Object>>();
		sooList.add(0, new HashMap<String, Object>());
		Map<String, Object> req = new HashMap<String, Object>();
		req.put(HhConstants.CHANNEL_CODE, HhConstants.CHANNEL_CODEVALUE);
		req.put(HhConstants.EXT_SYSTEM, HhConstants.EXT_SYSTEMVALUE);
		if((historyBillPwdVo.getCust_id() != "") && (historyBillPwdVo.getCust_id() != null)){
			req .put(HhConstants.CUST_ID, historyBillPwdVo.getCust_id());
		}
		req.put(HhConstants.PWD, MD5Utils.MD5(historyBillPwdVo.getPwd()));
		sooList.get(0).put(HhConstants.CUST_PAY, req);
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);
		
		/*
		 * 请求接口
		 */
		log.info("*************************BOSS用户登录接口入参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(body).toString());
		
		setIntefaceType(IntefaceType.CRM);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body,"SC1001016", ResponseBean.class);
		
		log.info("*************************BOSS用户登录接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map resultsooMap = (Map) resultSooList.get(0);
		Map resultResultMap = (Map) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.RESULT);
		String result = (String) resultResultMap.get(HhConstants.MSG);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if(HhConstants.SUCCESS.equals(status)){
			//通过后将custid放入临时存储空间，用于查询验证，有效期5分钟
			String key = "servicePwd" + historyBillPwdVo.getCust_id();
			ValidateCodeUtil.put(key, historyBillPwdVo.getCust_id());
			
			
			SMSInfoVo smsinfovo = new SMSInfoVo();
    		String content = "尊敬的客户，您好！您通过海航通信APP查询详单信息，如需帮助，请致电10044客户服务热线。";
    		smsinfovo.setContent(content);
    		smsinfovo.setMobile(historyBillPwdVo.getPhoneNum());
    		// 发送短信
    		//正式环境启用如下代码   BEGIN
    		SMSSendServiceImpl smssendserviceimpl = new SMSSendServiceImpl();
    		Map<String, Object> map = smssendserviceimpl.sendSMS(smsinfovo);
    		//正式环境启用以上代码  END
    		
    		
    		//以下代码为屏蔽发送短信接口验证使用，正式环境请删除
    		//TestCode BEGIN
    		/*Map<String, Object> map = new HashMap<String, Object>();
    		map.put(HhConstants.CODE, "0000");
    		map.put(HhConstants.SUCCESSCODE, HhConstants.MESSAGE);*/
    		//TestCode END
    		
    		String smsStatus = (String) map.get(HhConstants.CODE);
    		if (HhConstants.SUCCESSCODE.equals(smsStatus)) {
    			log.info("===========resp 短信发送 成功===========");
    		} else if (HhConstants.ERRORCODE.equals(smsStatus)) {
    			log.info("===========resp 短信发送 失败===========");
    		}
    		
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.SUCCESSMESSAGE);//成功描述
		}else{
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, result);//失败描述
		}
		
		return resultMap;
	}
}
