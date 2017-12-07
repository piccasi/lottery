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
import com.tydic.uniform.hh.service.interfaces.BalanceqryServiceServ;
import com.tydic.uniform.hh.vo.rep.BalanceqryIndexVo;
import com.tydic.uniform.hh.vo.rep.BalanceqryVo;
import com.tydic.uniform.hh.vo.resp.BalanceqryIndexResp;
import com.tydic.uniform.hh.vo.resp.BalanceqryResp;

import net.sf.json.JSONObject;

/**
 * <p></p>
 * @author ghp 2015年10月12日 下午5:03:25
 * @ClassName BalanceqryServiceImpl
 * @Description TODO           1.10 会员余额查询接口实体
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月12日
 * @modify by reason:{方法名}:{原因}
 */
@Service("BalanceqryServiceServ")
public class BalanceqryServiceImpl extends AbstractService implements BalanceqryServiceServ{

	private static Logger log = Logger.getLogger(BalanceqryServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> balanceqry(BalanceqryVo balanceqryvo) {
		
		log.info("*************************APP会员余额查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(balanceqryvo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		
		Map req = new HashMap();

		req.put(HhConstants.SYSTEMID, HhConstants.EXT_SYSTEMVALUE);
		req .put(HhConstants.BALTYPE, balanceqryvo.getBaltype());
		req .put(HhConstants.QRYTYPE, balanceqryvo.getQrytype());
		req .put(HhConstants.MEMBERID, balanceqryvo.getMemberid());
		
		sooList.get(0).put(HhConstants.BALANCEQRY_REQ,req);
		
		Map pub_req = new HashMap();
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
		/*
//		 * 解析接口返参
//		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
		String mssg = (String) resultResultMap.get("Msg");
		BalanceqryResp balanceqryresp = new BalanceqryResp();
			

	    if(HhConstants.SUCCESS.equals(status)){
	    
	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
	    	
	    	Map<String,Object> mapInfo = new HashMap<>();
	    	mapInfo.put(HhConstantsResp.CASH, resultResultMap.get(HhConstants.CASH));
	    	mapInfo.put(HhConstantsResp.FROZENBALANCE, resultResultMap.get(HhConstants.FROZENBALANCE));
	    	mapInfo.put(HhConstantsResp.WITHHOLDBALANCE, resultResultMap.get(HhConstants.WITHHOLDBALANCE));
	    	mapInfo.put(HhConstantsResp.OWEFEE, resultResultMap.get(HhConstants.OWEFEE));
	    	mapInfo.put(HhConstantsResp.XINGCOIN, resultResultMap.get(HhConstants.XINGCOIN));
	    	
			List<Object> resourcesList=(List<Object>) resultResultMap.get(HhConstants.RESOURCES);
			if(resourcesList.size()>0){
				ArrayList<Object> resourcesListresp = new ArrayList<Object>();
				for(int i=0; i<resourcesList.size();i++){
					Map<String, Object> resourcesMap=(Map<String, Object>) resourcesList.get(i);
			    
			    	Map<String, Object> prod_instmapresp = new HashMap<String, Object>();
			    	prod_instmapresp.put(HhConstantsResp.RESUNIT, resourcesMap.get(HhConstants.RESUNIT));
			    	prod_instmapresp.put(HhConstantsResp.EXPIRYDATE, resourcesMap.get(HhConstants.EXPIRYDATE));
			    	prod_instmapresp.put(HhConstantsResp.VALUE, resourcesMap.get(HhConstants.VALUE));
			    	prod_instmapresp.put(HhConstantsResp.COUNT, resourcesMap.get(HhConstants.COUNT));
			    	prod_instmapresp.put(HhConstantsResp.EFFECTIVEDATE, resourcesMap.get(HhConstants.EFFECTIVEDATE));
			    	prod_instmapresp.put(HhConstantsResp.RESOURCENAME, resourcesMap.get(HhConstants.RESOURCENAME));
			    	prod_instmapresp.put(HhConstantsResp.RESOURCESID, resourcesMap.get(HhConstants.RESOURCESID));
			    	resourcesListresp.add(prod_instmapresp);
				}
			
		    	mapInfo.put(HhConstantsResp.RESOURCES, resourcesListresp);
			}
	    	
			List<Object> resourcesuseList=(List<Object>) resultResultMap.get(HhConstants.RESOURCESUSE);
			if(resourcesuseList.size()>0){
		    	ArrayList<Object> resourcesuseListresp = new ArrayList<Object>();
				for(int i=0; i<resourcesuseList.size();i++){
					Map<String, Object> resourcesuseMap=(Map<String, Object>) resourcesuseList.get(i);

			    	Map<String, Object> prod_instmapresp = new HashMap<String, Object>();
			    	String resunit,realbalance;
			    	if(resourcesuseMap.get(HhConstants.RESUNIT)!=null&&resourcesuseMap.get(HhConstants.RESUNIT).equals("4")){
			    		resunit = "5";
			    		realbalance = String.valueOf((int)(Double.parseDouble((String)resourcesuseMap.get(HhConstants.REALBALANCE))/1024));
			    	}else if(resourcesuseMap.get(HhConstants.RESUNIT)!=null&&resourcesuseMap.get(HhConstants.RESUNIT).equals("3")){
			    		resunit = "2";
			    		realbalance = String.valueOf((int)(Double.parseDouble((String)resourcesuseMap.get(HhConstants.REALBALANCE))/60));
			    	}else{
			    		resunit = (String)resourcesuseMap.get(HhConstants.RESUNIT);
			    		realbalance = (String)resourcesuseMap.get(HhConstants.REALBALANCE);
			    	}
			    	prod_instmapresp.put(HhConstantsResp.RESUNIT, resunit);
			    	prod_instmapresp.put(HhConstantsResp.REALBALANCE, realbalance);
			    	prod_instmapresp.put(HhConstantsResp.USEBALANCE, resourcesuseMap.get(HhConstants.USEBALANCE));
			    	prod_instmapresp.put(HhConstantsResp.RESOURCESID, resourcesuseMap.get(HhConstants.RESOURCESID));
			    	prod_instmapresp.put(HhConstantsResp.TOTALBALANCE, resourcesuseMap.get(HhConstants.TOTALBALANCE));
			    	prod_instmapresp.put(HhConstantsResp.RESOURCESNAME, resourcesuseMap.get(HhConstants.RESOURCESNAME));
			    	resourcesuseListresp.add(prod_instmapresp);
				}

		    	mapInfo.put(HhConstantsResp.RESOURCESUSE, resourcesuseListresp);
			}
			
			List<Object> aldetailList=(List<Object>) resultResultMap.get(HhConstants.BALDETAIL);
			if(aldetailList.size()>0){
		    	ArrayList<Object> aldetailListresp = new ArrayList<Object>();
		    	
				for(int i=0; i<resourcesuseList.size();i++){
					Map<String, Object> aldetailMap=(Map<String, Object>) aldetailList.get(i);

			    	Map<String, Object> prod_instmapresp = new HashMap<String, Object>();
			    	prod_instmapresp.put(HhConstantsResp.ACCTBALANCEID, aldetailMap.get(HhConstants.ACCTBALANCEID));
			    	prod_instmapresp.put(HhConstantsResp.BALANCENAME, aldetailMap.get(HhConstants.BALANCENAME));
			    	prod_instmapresp.put(HhConstantsResp.BALANCETYPEID, aldetailMap.get(HhConstants.BALANCETYPEID));
			    	prod_instmapresp.put(HhConstantsResp.EFFECTIVEDATE, aldetailMap.get(HhConstants.EFFECTIVEDATE));
			    	prod_instmapresp.put(HhConstantsResp.EXPIRYDATE, aldetailMap.get(HhConstants.EXPIRYDATE));
			    	prod_instmapresp.put(HhConstantsResp.BALANCE, aldetailMap.get(HhConstants.BALANCE));
			    	prod_instmapresp.put(HhConstantsResp.BALANCECEIL, aldetailMap.get(HhConstants.BALANCECEIL));
			    	prod_instmapresp.put(HhConstantsResp.BALANCEFLOOR, aldetailMap.get(HhConstants.BALANCEFLOOR));
			    	prod_instmapresp.put(HhConstantsResp.USABLEBAL, aldetailMap.get(HhConstants.USABLEBAL));
			    	aldetailListresp.add(prod_instmapresp);
				}
			
		    	mapInfo.put(HhConstantsResp.BalDetail, aldetailListresp);
			}
	    	

			balanceqryresp.setMapInfo(mapInfo);
	    	
	    }else if(HhConstants.ERROR.equals(status)){
	    	if (mssg.equals("用户无套餐使用量记录!")) {
	    		resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//失败编码
	    		//resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			}else if (mssg.equals("查询不到")) {
	    		resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//失败编码
	    		//resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			}else{
				resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);
				resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);
				if(mssg!=null)
					resultMap.put(HhConstants.MESSAGE, mssg);
			}
	    }
	    log.info("*************************APP会员余额查询接口出参*************************");
	    resultMap.put("balanceqryresp", balanceqryresp);
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> balanceIndexqry(
			BalanceqryIndexVo balanceqryIndexVo) {
		log.info("*************************APP首页账户余额查询接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(balanceqryIndexVo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		
		List<Map> sooList = new ArrayList<Map>();
		sooList.add(0, new HashMap());
		
		Map req = new HashMap();

		req .put("MemberType", "1");
		req .put("Type", "2");
		req .put("Value", balanceqryIndexVo.getValue());
		
		sooList.get(0).put("OfrInst",req);
		
		Map pub_req = new HashMap();
		pub_req.put(HhConstants.TYPE, "OfrInst");
		
		sooList.get(0).put(HhConstants.PUB_REQ,pub_req);
		
		
		Map<String,Object> body = new HashMap<String,Object>();
		body.put("SOO", sooList);


		/*
		 * 请求接口
		 */
		log.info("*************************BOSS首页账户余额查询接口入参*************************");
		log.info("BOSS response str:" + body.toString());

		
		setIntefaceType(IntefaceType.BILL);
		ResponseBean bean=(ResponseBean) this.appApiInvoke(body, "SC1003009", ResponseBean.class);


		log.info("*************************BOSS首页账户余额查询接口出参*************************");
		log.info("BOSS response str:" + JSONObject.fromObject(bean).toString());
		/*
//		 * 解析接口返参
//		*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> resultSooList = (List<Map<String,Object>>) bean.getBody().get(0).get(HhConstants.SOO);
		Map<String,Object> resultsooMap = (Map<String,Object>) resultSooList.get(0);
		Map<String,Object> resultResultMap = (Map<String,Object>) resultsooMap.get(HhConstants.RESP);
		String status = (String) resultResultMap.get(HhConstants.BILLRESULT);
		String mssg = (String) resultResultMap.get(HhConstants.MSG);
		BalanceqryIndexResp balanceqryIndexResp = new BalanceqryIndexResp();
	
	    if(HhConstants.SUCCESS.equals(status)){
	    
	    	resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
	    	Map<String,Object> mapInfo = new HashMap<>();
	    	List<Map<String, Object>> OfrInstInfoList=(List<Map<String, Object>>) resultResultMap.get("OfrInstInfo");
			if(OfrInstInfoList.size()>0){
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();				
				for(int i=0; i<OfrInstInfoList.size();i++){
					@SuppressWarnings("unchecked")
			    	Map<String, Object> map = new HashMap<String, Object>();
			    	map.put("resourcetype", OfrInstInfoList.get(i).get("ResourceType"));
			    	map.put("begincycle", OfrInstInfoList.get(i).get("BeginCycle"));
			    	map.put("ofrname", OfrInstInfoList.get(i).get("OfrName"));
			    	map.put("leftamount", OfrInstInfoList.get(i).get("LeftAmount"));
			    	map.put("endcycle", OfrInstInfoList.get(i).get("EndCycle"));
			    	map.put("totalamount", OfrInstInfoList.get(i).get("TotalAmount"));
			    	map.put("usedamount", OfrInstInfoList.get(i).get("UsedAmount"));
			    	map.put("servicenbr", OfrInstInfoList.get(i).get("ServiceNbr"));
			    	mapList.add(map);
				}
		    	mapInfo.put("ofrinstinfo", mapList);
		    	mapInfo.put("callleftamount", resultResultMap.get("CallLeftAmount"));
		    	mapInfo.put("dataleftamount", resultResultMap.get("DataLeftAmount"));
		    	mapInfo.put("smsleftamount", resultResultMap.get("SmsLeftAmount"));
		    	mapInfo.put("pcallleftamount", resultResultMap.get("PCallLeftAmount"));
		    	mapInfo.put("pdataleftamount", resultResultMap.get("PDataLeftAmount"));
		    	mapInfo.put("psmsleftamount", resultResultMap.get("PSmsLeftAmount"));
		    	mapInfo.put("npcallleftamount", resultResultMap.get("NPCallLeftAmount"));
		    	mapInfo.put("npdataleftamount", resultResultMap.get("NPDataLeftAmount"));
		    	mapInfo.put("npsmsleftamount", resultResultMap.get("NPSmsLeftAmount"));
		    	mapInfo.put("ofrname", resultResultMap.get("OfrName"));
			}
			balanceqryIndexResp.setMapInfo(mapInfo);
	    	
	    }else if(HhConstants.ERROR.equals(status)){
	    	if (mssg.equals("用户无套餐使用量记录!")) {
	    		resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//失败编码
	    		resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);//失败描述
			}
	    }
  
	    resultMap.put("balanceqryindexresp", balanceqryIndexResp);
		log.info("APP response str:"+JSONObject.fromObject(resultMap).toString());
	    return resultMap;
	}

}
