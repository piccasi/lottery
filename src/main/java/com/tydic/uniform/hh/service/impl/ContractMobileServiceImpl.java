package com.tydic.uniform.hh.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import com.tydic.uniform.hh.service.interfaces.ContractMobileServiceServ;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.vo.rep.ContractMealVo;
import com.tydic.uniform.hh.vo.rep.ContractMobileListVo;
import com.tydic.uniform.hh.vo.rep.ContractMobileVo;
import com.tydic.uniform.hh.vo.resp.ContractMobileListResp;
import com.tydic.uniform.hh.vo.resp.ContractMobileResp;
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
@Service("ContractMobileServiceServ")
public class ContractMobileServiceImpl extends AbstractService implements
		ContractMobileServiceServ {
	public static Logger log = Logger
			.getLogger(ContractMobileServiceImpl.class);
    /**
     * 合约机详情
     */
	@Override
	public Map<String, Object> queryContractMobile(
			ContractMobileVo contractMobileVo) {
		log.info("*************************APP合约机详情接口入参*************************");
		log.info("APP request str:"
				+ JSONObject.fromObject(contractMobileVo).toString());
		Map body = new HashMap();
		body.put("goodsCode", contractMobileVo.getGoodscode());
		body.put("showChannel", contractMobileVo.getShowchannel());
		body.put("sellChannel", contractMobileVo.getSellchannel());

		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1002102",
				ResponseBean.class);
		Map resultResultMap = (Map) bean.getBody().get(0);
		String status = (String) resultResultMap.get(HhConstants.ESHOPRESULT);
		Map<String, Object> resultMap = new HashMap<String, Object>();

		ContractMobileResp contractMobileResp = new ContractMobileResp();
		Map<String, Object> mapInfo = new HashMap<>();
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			if (resultResultMap.containsKey(HhConstants.ESHOPREPDATA)) {
				Map<String, Object> service_infoMap = (Map<String, Object>) resultResultMap
						.get(HhConstants.ESHOPREPDATA);
				Map<String, Object> respmap = new HashMap<String, Object>();
				respmap.put("brandname", service_infoMap.get("brandName"));
				respmap.put("buynote", service_infoMap.get("buyNote"));
				respmap.put("categorycode", service_infoMap.get("categoryCode"));
				respmap.put("categoryname", service_infoMap.get("categoryName"));
				respmap.put("categorytype", service_infoMap.get("categoryType"));
				respmap.put("goodscode", service_infoMap.get("goodsCode"));
				respmap.put("goodscomment", service_infoMap.get("goodsComment"));
				respmap.put("goodsid", service_infoMap.get("goodsId"));
				respmap.put("goodslongname",
						service_infoMap.get("goodsLongName"));
				respmap.put("goodsmedianame",
						service_infoMap.get("goodsMediaName"));
				respmap.put("goodsModel", service_infoMap.get("goodsModel"));
				respmap.put("goodsPackageList",
						service_infoMap.get("goodsPackageList"));
				
				respmap.put("goodssellpoint",
						service_infoMap.get("goodsSellPoint"));
				respmap.put("goodsshortname",
						service_infoMap.get("goodsShortName"));
				respmap.put("goodstype", service_infoMap.get("goodsType"));
				respmap.put("isrequired", service_infoMap.get("isRequired"));
				respmap.put("maxprice", service_infoMap.get("maxPrice"));
				respmap.put("minprice", service_infoMap.get("minPrice"));
				respmap.put("modelid", service_infoMap.get("modelId"));
				respmap.put("price", service_infoMap.get("price"));
				respmap.put("productbrandid",
						service_infoMap.get("productBrandId"));
				respmap.put("secondname", service_infoMap.get("secondName"));
				respmap.put("shopprice", service_infoMap.get("shopPrice"));
				
				ArrayList<Map<String, Object>> goodsPiclist = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> goodsPicslist = (List<Map<String, Object>>) service_infoMap
						.get("goodsPics");
				for (int i = 0; i < goodsPicslist.size(); i++) {
					Map<String, Object> pic = new HashMap<String, Object>();
					pic.put("attrcode", goodsPicslist.get(i).get("attrCode"));
					pic.put("attrvalue", goodsPicslist.get(i).get("attrValue"));
					pic.put("goodscode", goodsPicslist.get(i).get("goodsCode"));
					pic.put("imgfivepath", goodsPicslist.get(i).get("imgFivePath"));
					pic.put("imgfourpath", goodsPicslist.get(i).get("imgFourPath"));
					pic.put("imgonepath", goodsPicslist.get(i).get("imgOnePath"));
					pic.put("imgsevenpath", goodsPicslist.get(i)
							.get("imgSevenPath"));
					pic.put("imgsixpath", goodsPicslist.get(i).get("imgSixPath"));
					pic.put("imgthreepath", goodsPicslist.get(i)
							.get("imgThreePath"));
					pic.put("imgtwopath", goodsPicslist.get(i).get("imgTwoPath"));
					goodsPiclist.add(pic);
				}
				respmap.put("goodspics",goodsPiclist);
				
				ArrayList<Map<String, Object>> goodsSalePlanlist = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> goodsSalePlanslist = (List<Map<String, Object>>) service_infoMap
						.get("goodsSalePlan");
				for (int i = 0; i < goodsSalePlanslist.size(); i++) {
					Map<String, Object> pic = new HashMap<String, Object>();
					pic.put("description", goodsSalePlanslist.get(i).get("description"));
					pic.put("plancode", goodsSalePlanslist.get(i).get("planCode"));
					pic.put("planname", goodsSalePlanslist.get(i).get("planName"));
					pic.put("selltype", goodsSalePlanslist.get(i).get("sellType"));
					pic.put("selltypename", goodsSalePlanslist.get(i).get("sellTypeName"));
					pic.put("usertype", goodsSalePlanslist.get(i).get("userType"));
					goodsSalePlanlist.add(pic);
				}
				respmap.put("goodssaleplan",goodsSalePlanlist);
				
				ArrayList<Map<String, Object>> goodsSellAttrlist = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> goodsSellAttrslist = (List<Map<String, Object>>) service_infoMap
						.get("goodsSellAttrs");
				for (int i = 0; i < goodsSellAttrslist.size(); i++) {
					Map<String, Object> pic = new HashMap<String, Object>();
					pic.put("attrid", goodsSellAttrslist.get(i).get("attrId"));
					pic.put("attrname", goodsSellAttrslist.get(i).get("attrName"));
					pic.put("attrshowvalue", goodsSellAttrslist.get(i).get("attrShowValue"));
					pic.put("attrvalue", goodsSellAttrslist.get(i).get("attrValue"));
					goodsSellAttrlist.add(pic);
				}
				respmap.put("goodssellattrs",goodsSellAttrlist);
				
				ArrayList<Map<String, Object>> yanseSkuAttrlist = new ArrayList<Map<String, Object>>();
				ArrayList<Map<String, Object>> romSkuAttrlist = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> goodsSkuAttrslist = (List<Map<String, Object>>) service_infoMap
						.get("goodsSkuAttr");
				for (int i = 0; i < goodsSkuAttrslist.size(); i++) {
					if((int)goodsSkuAttrslist.get(i).get("skuAttrId")==4878){
						Map<String, Object> pic = new HashMap<String, Object>();
						pic.put("skuattrcode", goodsSkuAttrslist.get(i).get("skuAttrCode"));
						pic.put("skuattrid", goodsSkuAttrslist.get(i).get("skuAttrId"));
						pic.put("skuattrname", goodsSkuAttrslist.get(i).get("skuAttrName"));
						pic.put("skuattrshowvalue", goodsSkuAttrslist.get(i).get("skuAttrShowValue"));
						pic.put("skuattrvalue", goodsSkuAttrslist.get(i).get("skuAttrValue"));
						yanseSkuAttrlist.add(pic);
					}else if((int)goodsSkuAttrslist.get(i).get("skuAttrId")==4884){
						Map<String, Object> pic = new HashMap<String, Object>();
						pic.put("skuattrcode", goodsSkuAttrslist.get(i).get("skuAttrCode"));
						pic.put("skuattrid", goodsSkuAttrslist.get(i).get("skuAttrId"));
						pic.put("skuattrname", goodsSkuAttrslist.get(i).get("skuAttrName"));
						pic.put("skuattrshowvalue", goodsSkuAttrslist.get(i).get("skuAttrShowValue"));
						pic.put("skuattrvalue", goodsSkuAttrslist.get(i).get("skuAttrValue"));
						romSkuAttrlist.add(pic);
					}
				}
//				List yansesSkuAttrlist = new ArrayList<>(new HashSet<>(yanseSkuAttrlist));
//				List romsSkuAttrlist = new ArrayList<>(new HashSet<>(romSkuAttrlist));
				respmap.put("yanseskuattr",yanseSkuAttrlist);
				respmap.put("romskuattr",romSkuAttrlist);
				
				mapInfo.put("detail", respmap);
			}
			contractMobileResp.setMapInfo(mapInfo);
		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "请求失败");// 失败描述
		}
		resultMap.put("contractmobileresp", contractMobileResp);
		return resultMap;
	}

	 /**
     * 合约机套餐
     */
	@Override
	public Map<String, Object> queryContractMeal(ContractMealVo contractMealVo) {
		log.info("*************************APP合约机详情接口入参*************************");
		log.info("APP request str:"
				+ JSONObject.fromObject(contractMealVo).toString());
		Map body = new HashMap();
		body.put("planCode", contractMealVo.getPlancode());
		body.put("productCode", contractMealVo.getProductcode());
		body.put("sellChannel", contractMealVo.getSellchannel());

		setIntefaceType(IntefaceType.ESHOP);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1002103",
				ResponseBean.class);
		Map resultResultMap = (Map) bean.getBody().get(0);
		String status = (String) resultResultMap.get(HhConstants.ESHOPRESULT);
		Map<String, Object> resultMap = new HashMap<String, Object>();

		ContractMobileResp contractMobileResp = new ContractMobileResp();
		Map<String, Object> mapInfo = new HashMap<>();
		if (HhConstants.SUCCESS.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.RESULT, status);
			if (resultResultMap.containsKey(HhConstants.ESHOPREPDATA)) {
				Map<String, Object> service_infoMap = (Map<String, Object>) resultResultMap
						.get(HhConstants.ESHOPREPDATA);
				Map<String, Object> respmap = new HashMap<String, Object>();

				respmap.put("buylimit", service_infoMap.get("buyLimit"));
				respmap.put("contractid", service_infoMap.get("contractId"));
				respmap.put("dealcombonum", service_infoMap.get("dealComboNum"));
				respmap.put("plancode", service_infoMap.get("planCode"));
				respmap.put("planid", service_infoMap.get("planId"));
				respmap.put("planname", service_infoMap.get("planName"));
				respmap.put("salescontent", service_infoMap.get("salesContent"));
				respmap.put("selltype", service_infoMap.get("sellType"));
				respmap.put("usertype",
						service_infoMap.get("userType"));
				
				ArrayList<Map<String, Object>> giftlist = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> giftslist = (List<Map<String, Object>>) service_infoMap
						.get("gift");
				for (int i = 0; i < giftslist.size(); i++) {
					Map<String, Object> pic = new HashMap<String, Object>();
//					pic.put("attrcode", goodsPicslist.get(i).get("attrCode"));
//					pic.put("attrvalue", goodsPicslist.get(i).get("attrValue"));
					giftlist.add(pic);
				}
				respmap.put("goodspics",giftlist);
				
				ArrayList<Map<String, Object>> planGoodsPricelist = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> planGoodsPriceslist = (List<Map<String, Object>>) service_infoMap
						.get("planGoodsPrice");
				for (int i = 0; i < planGoodsPriceslist.size(); i++) {
					Map<String, Object> pic = new HashMap<String, Object>();
					pic.put("goodsprice", planGoodsPriceslist.get(i).get("goodsPrice"));
					pic.put("marketrescode", planGoodsPriceslist.get(i).get("marketResCode"));
					pic.put("skucode", planGoodsPriceslist.get(i).get("skuCode"));
					
					ArrayList<Map<String, Object>> productSkuOptionlist = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> productSkuOptionslist = (List<Map<String, Object>>) planGoodsPriceslist.get(i)
							.get("productSkuOption");
					for (int j = 0; j < productSkuOptionslist.size(); j++) {
						Map<String, Object> pic1 = new HashMap<String, Object>();
						pic1.put("attrname", productSkuOptionslist.get(j).get("attrName"));
						pic1.put("attrshowvalue", productSkuOptionslist.get(j).get("attrShowValue"));
						pic1.put("attrtype", productSkuOptionslist.get(j).get("attrType"));
						pic1.put("attrvalue", productSkuOptionslist.get(j).get("attrValue"));
						pic1.put("extendattrid", productSkuOptionslist.get(j).get("extendattrId"));
						productSkuOptionlist.add(pic1);
					}
					pic.put("productskuoption",productSkuOptionlist);
					
					planGoodsPricelist.add(pic);
				}
				respmap.put("plangoodsprice",planGoodsPricelist);
				
				ArrayList<Map<String, Object>> recommendGoodslist = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> recommendGoodsslist = (List<Map<String, Object>>) service_infoMap
						.get("recommendGoods");
				for (int i = 0; i < recommendGoodsslist.size(); i++) {
					Map<String, Object> pic = new HashMap<String, Object>();
//					pic.put("attrid", recommendGoodsslist.get(i).get("attrId"));
//					pic.put("attrname", recommendGoodsslist.get(i).get("attrName"));
//					pic.put("attrshowvalue", recommendGoodsslist.get(i).get("attrShowValue"));
//					pic.put("attrvalue", recommendGoodsslist.get(i).get("attrValue"));
					recommendGoodslist.add(pic);
				}
				respmap.put("recommendgoods",recommendGoodslist);
				
				ArrayList<Map<String, Object>> relateGoodslist = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> relateGoodsslist = (List<Map<String, Object>>) service_infoMap
						.get("relateGoods");
				for (int i = 0; i < relateGoodsslist.size(); i++) {
					Map<String, Object> pic = new HashMap<String, Object>();
			
					pic.put("brandid", ((Map<String, Object>)relateGoodsslist.get(i).get("goodsDetail")).get("brandId"));
					pic.put("brandname", ((Map<String, Object>)relateGoodsslist.get(i).get("goodsDetail")).get("brandName"));
					pic.put("categorycode", ((Map<String, Object>)relateGoodsslist.get(i).get("goodsDetail")).get("categoryCode"));
					pic.put("categorytype", ((Map<String, Object>)relateGoodsslist.get(i).get("goodsDetail")).get("categoryType"));
					pic.put("goodscode", ((Map<String, Object>)relateGoodsslist.get(i).get("goodsDetail")).get("goodsCode"));
					pic.put("goodsname", ((Map<String, Object>)relateGoodsslist.get(i).get("goodsDetail")).get("goodsName"));
					pic.put("goodsprice", ((Map<String, Object>)relateGoodsslist.get(i).get("goodsDetail")).get("goodsPrice"));
					pic.put("offerid", ((Map<String, Object>)relateGoodsslist.get(i).get("goodsDetail")).get("offerId"));
					
					ArrayList<Map<String, Object>> relPlanGoodsItemlist = new ArrayList<Map<String, Object>>();
					List<Map<String, Object>> relPlanGoodsItemslist = (List<Map<String, Object>>)((Map<String, Object>)relateGoodsslist.get(i).get("goodsDetail")).get("relPlanGoodsItem");
					for (int j = 0; j < relPlanGoodsItemslist.size(); j++) {
						Map<String, Object> pic1 = new HashMap<String, Object>();
						pic1.put("brandid", relPlanGoodsItemslist.get(j).get("brandId"));
						pic1.put("brandname", relPlanGoodsItemslist.get(j).get("brandName"));
						pic1.put("categorycode", relPlanGoodsItemslist.get(j).get("categoryCode"));
						pic1.put("categorytype", relPlanGoodsItemslist.get(j).get("categoryType"));
						pic1.put("categorytypename", relPlanGoodsItemslist.get(j).get("categoryTypeName"));
						pic1.put("goodscode", relPlanGoodsItemslist.get(j).get("goodsCode"));
						pic1.put("goodsname", relPlanGoodsItemslist.get(j).get("goodsName"));
						pic1.put("goodsprice", relPlanGoodsItemslist.get(j).get("goodsPrice"));
						pic1.put("goodstype", relPlanGoodsItemslist.get(j).get("goodsType"));
						pic1.put("numberlevel", relPlanGoodsItemslist.get(j).get("numberLevel"));
						relPlanGoodsItemlist.add(pic1);
					}
					pic.put("relplangoodsitem",relPlanGoodsItemlist);
					
					
					relateGoodslist.add(pic);
				}
				respmap.put("relategoods",relateGoodslist);
				
				mapInfo.put("detail", respmap);
			}
			contractMobileResp.setMapInfo(mapInfo);
		} else if (HhConstants.ERROR.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, "请求失败");// 失败描述
		}
		resultMap.put("contractmobileresp", contractMobileResp);
		return resultMap;
	}
}
