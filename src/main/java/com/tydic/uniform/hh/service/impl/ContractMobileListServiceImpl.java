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
import com.tydic.uniform.hh.constant.HhConstantsResp;
import com.tydic.uniform.hh.service.interfaces.ContractMobileListServiceServ;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.vo.rep.ContractMobileListVo;
import com.tydic.uniform.hh.vo.resp.ContractMobileListResp;
import com.tydic.uniform.hh.vo.resp.ResourceConversionResp;
/**
 * <p>
 * </p>
 * 
 * @author ghp 2015年9月29日 下午6:16:12
 * @ClassName ContractMobileListServiceImpl 合约机列表查询接口实体
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
@Service("ContractMobileListServiceServ")
public class ContractMobileListServiceImpl extends AbstractService implements
		ContractMobileListServiceServ {
	public static Logger log = Logger.getLogger(ContractMobileListServiceImpl.class);

	@Override
	public Map<String, Object> queryContractMobileLis(
			ContractMobileListVo contractMobileListVo) {
		log.info("*************************APP合约机列表接口入参*************************");
		log.info("APP request str:" + JSONObject.fromObject(contractMobileListVo).toString());
		Map req = new HashMap();
		req.put("categoryCode", PropertiesUtil.getPropertyValue("CATEGORY_CODE"));
		Map body = new HashMap();
		body.put("pageSize",contractMobileListVo.getPagesize());
		body.put("pageIndex",contractMobileListVo.getPageindex());
		body.put("sellChannel",contractMobileListVo.getSellchannel());
		body.put("productGoods",req);
		
		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean=(ResponseBean) this.appApiInvoke(body, "SC1002101", ResponseBean.class);
		Map resultResultMap = (Map)bean.getBody().get(0);
		String status = (String) resultResultMap.get(HhConstants.ESHOPRESULT);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		ContractMobileListResp contractMobileListResp = new ContractMobileListResp();
		Map<String,Object> mapInfo = new HashMap<>();
		if(HhConstants.SUCCESS.equals(status)){
		    resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);//成功
		    resultMap.put(HhConstants.RESULT, status);
		    if(resultResultMap.containsKey(HhConstants.ESHOPREPDATA)){
	    		
	    		 ArrayList<Map<String,Object>> resplist = new ArrayList<Map<String,Object>>();
	    		 Map<String,Object> service_infoMap=(Map<String,Object>) resultResultMap.get(HhConstants.ESHOPREPDATA);
	    		 if (service_infoMap.containsKey("goodsIntros")) {
					List<Map<String,Object>> resTransTypelist = (List<Map<String, Object>>) service_infoMap.get("goodsIntros");
					
					for (int i = 0; i < resTransTypelist.size(); i++) {
						 Map<String,Object> resTransTypemap = (Map<String,Object>) resTransTypelist.get(i);
		    			 Map<String,Object> respmap =new HashMap<String,Object>();
		    			
		    			 respmap.put("categorycode", resTransTypemap.get("categoryCode"));
		    			 respmap.put("goodscode", resTransTypemap.get("goodsCode"));
		    			 respmap.put("goodsid", resTransTypemap.get("goodsId"));
		    			 respmap.put("goodslongname", resTransTypemap.get("goodsLongName"));
		    			 respmap.put("goodsmodel", resTransTypemap.get("goodsModel"));
		    			 respmap.put("goodsshortname", resTransTypemap.get("goodsShortName"));
		    			 respmap.put("price", resTransTypemap.get("price"));
		    			 respmap.put("productbrandid", resTransTypemap.get("productBrandId"));
		    			 respmap.put("saletime", resTransTypemap.get("saleTime"));
		    			 respmap.put("secondname", resTransTypemap.get("secondName"));
		    			 respmap.put("shopprice", resTransTypemap.get("shopPrice"));
		    			 if(((List<Map<String,Object>>)resTransTypemap.get("goodsPics")).size()>0){
		    				 Map<String,Object> goodsPic = (Map<String,Object>) ((List<Map<String,Object>>)resTransTypemap.get("goodsPics")).get(0);
		    				 if(goodsPic.get("imgOnePath")!=null&&!goodsPic.get("imgOnePath").toString().equals("")){
		    					 respmap.put("imgonepath", goodsPic.get("imgOnePath"));
		    				 }
		    			 }
		    			 
		    			 Map<String,Object> goodsLabel =new HashMap<String,Object>();
		    			 Map<String,Object> lbmap = (Map<String, Object>) resTransTypemap.get("goodsLabel");
		    			 goodsLabel.put("goodslabelname", lbmap.get("goodsLabelName"));
		    			 goodsLabel.put("labelflag", lbmap.get("labelFlag"));
		    			 goodsLabel.put("labelpicpath", lbmap.get("labelPicPath"));
		    			 respmap.put("goodslabel", goodsLabel);
		    			 
		    			 resplist.add(respmap);
					}
				}
	    		 mapInfo.put("goodsintros", resplist);
	    	}
		    contractMobileListResp.setMapInfo(mapInfo);
	    }else if(HhConstants.ERROR.equals(status)){
	    	resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);//失败编码
			resultMap.put(HhConstants.MESSAGE, "请求失败");//失败描述
	    }
		 resultMap.put("contractmobilelistresp", contractMobileListResp);
		return resultMap;
	}
}
