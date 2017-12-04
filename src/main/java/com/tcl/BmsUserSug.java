package com.tcl;

import com.tcl.BaModel;
import com.tcl.BafVariants;
import com.tcl.MapBafVariantsImpl;
import com.tcl.StandBafVariants;

public class BmsUserSug implements BaModel {
	 /**业务类别 GSM,CDMA...*/
    protected String serviceType;

    /**用户订购ID*/
    protected String subscriptionId;

    /** 地区编码 **/
    private String regionId;

    /** 详细叙述 **/
    private String remark;

    /** 名字 **/
    private String name;

    public String getActionName() {
        return "User_Busi_Pre_Check";
    	//return "Bms_Action_Batch";
    }

    public BafVariants getBafVariants() {
        StandBafVariants bafVariants = new MapBafVariantsImpl();

        bafVariants.addParameter("SERVICE_TYPE", this.serviceType);
        bafVariants.addParameter("SUBSCRIPTION_ID", this.subscriptionId);
        bafVariants.addParameter("REGION_ID", this.regionId);
        bafVariants.addParameter("SERVICE_NUM", "13036072558");
        bafVariants.addParameter("BUSI_ID", "10010");
        return bafVariants;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}