package com.tydic.uniform.hh.vo.rep;

import com.alibaba.fastjson.annotation.JSONField;

public class CanOrderOffer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5314737810684317704L;

	@JSONField(name = "OFR_ID")
	private String offerId; // 套餐ID
	
	@JSONField(name = "OFR_NAME")
	private String offerName; // 套餐名称
	
	@JSONField(name = "OFR_MODE")
	private String offerMode; // 套餐模式-01：主套餐02：加油包

	
	@JSONField(name = "OFR_PRICE")
	private String offerPrice; // 套餐价格
	
	@JSONField(name = "OFR_DESC")
	private String offerDesc; // 套餐说明
	
	@JSONField(name = "OFR_DETAIL_TYPE")
	private String offerDetailType; // 套餐小类说明
	
	@JSONField(name = "OFR_MODE_TYPE")
	private String ofrModeType; //模组套餐识别字段

	public String getOfrModeType() {
		return ofrModeType;
	}

	public void setOfrModeType(String ofrModeType) {
		this.ofrModeType = ofrModeType;
	}
	public String getOfferDetailType() {
		return offerDetailType;
	}

	public void setOfferDetailType(String offerDetailType) {
		this.offerDetailType = offerDetailType;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferMode() {
		return offerMode;
	}

	public void setOfferMode(String offerMode) {
		this.offerMode = offerMode;
	}

	public String getOfferPrice() {
		return offerPrice;
	}

	public void setOfferPrice(String offerPrice) {
		this.offerPrice = offerPrice;
	}

	public String getOfferDesc() {
		return offerDesc;
	}

	public void setOfferDesc(String offerDesc) {
		this.offerDesc = offerDesc;
	}
}
