package com.tydic.uniform.hh.vo.rep;

import java.io.Serializable;
import java.util.List;

public class ExampleBean implements Serializable{	private static final long serialVersionUID = 642019674L;	private List<SOO> sOO;
	public List<SOO> getSOO() {		return this.sOO;	}
	public void setSOO(List<SOO> sOO) {		this.sOO = sOO;	}
	public ExampleBean() {}
	public ExampleBean(List<SOO> sOO){
		super();		this.sOO = sOO;
	}
	public String toString() {
		return "ExampleBean [sOO = " + sOO + "]";	}
}
class SALE_RES_INST implements Serializable{	private static final long serialVersionUID = 642019674L;	private String rES_ID;	private String rES_NAME;	private String rES_INST_NBR;	private String sALE_ORDER_ID;	private String rES_INST_ID;
	public String getRES_ID() {		return this.rES_ID;	}
	public void setRES_ID(String rES_ID) {		this.rES_ID = rES_ID;	}
	public String getRES_NAME() {		return this.rES_NAME;	}
	public void setRES_NAME(String rES_NAME) {		this.rES_NAME = rES_NAME;	}
	public String getRES_INST_NBR() {		return this.rES_INST_NBR;	}
	public void setRES_INST_NBR(String rES_INST_NBR) {		this.rES_INST_NBR = rES_INST_NBR;	}
	public String getSALE_ORDER_ID() {		return this.sALE_ORDER_ID;	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {		this.sALE_ORDER_ID = sALE_ORDER_ID;	}
	public String getRES_INST_ID() {		return this.rES_INST_ID;	}
	public void setRES_INST_ID(String rES_INST_ID) {		this.rES_INST_ID = rES_INST_ID;	}
	public SALE_RES_INST() {}
	public SALE_RES_INST(String rES_ID, String rES_NAME, String rES_INST_NBR, String sALE_ORDER_ID, String rES_INST_ID){
		super();		this.rES_ID = rES_ID;		this.rES_NAME = rES_NAME;		this.rES_INST_NBR = rES_INST_NBR;		this.sALE_ORDER_ID = sALE_ORDER_ID;		this.rES_INST_ID = rES_INST_ID;
	}
	public String toString() {
		return "SALE_RES_INST [rES_ID = " + rES_ID + ", rES_NAME = " + rES_NAME + ", rES_INST_NBR = " + rES_INST_NBR + ", sALE_ORDER_ID = " + sALE_ORDER_ID + ", rES_INST_ID = " + rES_INST_ID + "]";	}
}
class NUMBER_INFO implements Serializable{	private static final long serialVersionUID = 1044461351L;	private String pROVICE_NAME;	private String nUMBER_LEVEL;	private String cITY_NAME;	private String pROD_INST_ID;	private String cITY_CODE;	private String pROVINCE_CODE;
	public String getPROVICE_NAME() {		return this.pROVICE_NAME;	}
	public void setPROVICE_NAME(String pROVICE_NAME) {		this.pROVICE_NAME = pROVICE_NAME;	}
	public String getNUMBER_LEVEL() {		return this.nUMBER_LEVEL;	}
	public void setNUMBER_LEVEL(String nUMBER_LEVEL) {		this.nUMBER_LEVEL = nUMBER_LEVEL;	}
	public String getCITY_NAME() {		return this.cITY_NAME;	}
	public void setCITY_NAME(String cITY_NAME) {		this.cITY_NAME = cITY_NAME;	}
	public String getPROD_INST_ID() {		return this.pROD_INST_ID;	}
	public void setPROD_INST_ID(String pROD_INST_ID) {		this.pROD_INST_ID = pROD_INST_ID;	}
	public String getCITY_CODE() {		return this.cITY_CODE;	}
	public void setCITY_CODE(String cITY_CODE) {		this.cITY_CODE = cITY_CODE;	}
	public String getPROVINCE_CODE() {		return this.pROVINCE_CODE;	}
	public void setPROVINCE_CODE(String pROVINCE_CODE) {		this.pROVINCE_CODE = pROVINCE_CODE;	}
	public NUMBER_INFO() {}
	public NUMBER_INFO(String pROVICE_NAME, String nUMBER_LEVEL, String cITY_NAME, String pROD_INST_ID, String cITY_CODE, String pROVINCE_CODE){
		super();		this.pROVICE_NAME = pROVICE_NAME;		this.nUMBER_LEVEL = nUMBER_LEVEL;		this.cITY_NAME = cITY_NAME;		this.pROD_INST_ID = pROD_INST_ID;		this.cITY_CODE = cITY_CODE;		this.pROVINCE_CODE = pROVINCE_CODE;
	}
	public String toString() {
		return "NUMBER_INFO [pROVICE_NAME = " + pROVICE_NAME + ", nUMBER_LEVEL = " + nUMBER_LEVEL + ", cITY_NAME = " + cITY_NAME + ", pROD_INST_ID = " + pROD_INST_ID + ", cITY_CODE = " + cITY_CODE + ", pROVINCE_CODE = " + pROVINCE_CODE + "]";	}
}
class PAYMENT implements Serializable{	private static final long serialVersionUID = 1428044185L;	private String pAYMENT_CODE;	private String pAYMENT_SERIAL;	private String sALE_ORDER_ID;	private String aMOUNT;	private String bANK_SERIAL_NBR;	private String sERIAL_NBR;
	public String getPAYMENT_CODE() {		return this.pAYMENT_CODE;	}
	public void setPAYMENT_CODE(String pAYMENT_CODE) {		this.pAYMENT_CODE = pAYMENT_CODE;	}
	public String getPAYMENT_SERIAL() {		return this.pAYMENT_SERIAL;	}
	public void setPAYMENT_SERIAL(String pAYMENT_SERIAL) {		this.pAYMENT_SERIAL = pAYMENT_SERIAL;	}
	public String getSALE_ORDER_ID() {		return this.sALE_ORDER_ID;	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {		this.sALE_ORDER_ID = sALE_ORDER_ID;	}
	public String getAMOUNT() {		return this.aMOUNT;	}
	public void setAMOUNT(String aMOUNT) {		this.aMOUNT = aMOUNT;	}
	public String getBANK_SERIAL_NBR() {		return this.bANK_SERIAL_NBR;	}
	public void setBANK_SERIAL_NBR(String bANK_SERIAL_NBR) {		this.bANK_SERIAL_NBR = bANK_SERIAL_NBR;	}
	public String getSERIAL_NBR() {		return this.sERIAL_NBR;	}
	public void setSERIAL_NBR(String sERIAL_NBR) {		this.sERIAL_NBR = sERIAL_NBR;	}
	public PAYMENT() {}
	public PAYMENT(String pAYMENT_CODE, String pAYMENT_SERIAL, String sALE_ORDER_ID, String aMOUNT, String bANK_SERIAL_NBR, String sERIAL_NBR){
		super();		this.pAYMENT_CODE = pAYMENT_CODE;		this.pAYMENT_SERIAL = pAYMENT_SERIAL;		this.sALE_ORDER_ID = sALE_ORDER_ID;		this.aMOUNT = aMOUNT;		this.bANK_SERIAL_NBR = bANK_SERIAL_NBR;		this.sERIAL_NBR = sERIAL_NBR;
	}
	public String toString() {
		return "PAYMENT [pAYMENT_CODE = " + pAYMENT_CODE + ", pAYMENT_SERIAL = " + pAYMENT_SERIAL + ", sALE_ORDER_ID = " + sALE_ORDER_ID + ", aMOUNT = " + aMOUNT + ", bANK_SERIAL_NBR = " + bANK_SERIAL_NBR + ", sERIAL_NBR = " + sERIAL_NBR + "]";	}
}
class FEE_ITEM implements Serializable{	private static final long serialVersionUID = 1428044185L;	private String oBJ_INST_TYPE;	private String aCCT_ITEM_TYPE;	private String sALE_ORDER_ID;	private String oBJ_INST_ID;	private String aMOUNT;
	public String getOBJ_INST_TYPE() {		return this.oBJ_INST_TYPE;	}
	public void setOBJ_INST_TYPE(String oBJ_INST_TYPE) {		this.oBJ_INST_TYPE = oBJ_INST_TYPE;	}
	public String getACCT_ITEM_TYPE() {		return this.aCCT_ITEM_TYPE;	}
	public void setACCT_ITEM_TYPE(String aCCT_ITEM_TYPE) {		this.aCCT_ITEM_TYPE = aCCT_ITEM_TYPE;	}
	public String getSALE_ORDER_ID() {		return this.sALE_ORDER_ID;	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {		this.sALE_ORDER_ID = sALE_ORDER_ID;	}
	public String getOBJ_INST_ID() {		return this.oBJ_INST_ID;	}
	public void setOBJ_INST_ID(String oBJ_INST_ID) {		this.oBJ_INST_ID = oBJ_INST_ID;	}
	public String getAMOUNT() {		return this.aMOUNT;	}
	public void setAMOUNT(String aMOUNT) {		this.aMOUNT = aMOUNT;	}
	public FEE_ITEM() {}
	public FEE_ITEM(String oBJ_INST_TYPE, String aCCT_ITEM_TYPE, String sALE_ORDER_ID, String oBJ_INST_ID, String aMOUNT){
		super();		this.oBJ_INST_TYPE = oBJ_INST_TYPE;		this.aCCT_ITEM_TYPE = aCCT_ITEM_TYPE;		this.sALE_ORDER_ID = sALE_ORDER_ID;		this.oBJ_INST_ID = oBJ_INST_ID;		this.aMOUNT = aMOUNT;
	}
	public String toString() {
		return "FEE_ITEM [oBJ_INST_TYPE = " + oBJ_INST_TYPE + ", aCCT_ITEM_TYPE = " + aCCT_ITEM_TYPE + ", sALE_ORDER_ID = " + sALE_ORDER_ID + ", oBJ_INST_ID = " + oBJ_INST_ID + ", aMOUNT = " + aMOUNT + "]";	}
}
class RECEIVE_INFO implements Serializable{	private static final long serialVersionUID = 801352273L;	private String sALE_ORDER_ID;	private String aDDRESS;	private String rECEIVE_NAME;	private String pOST_CODE;	private String mOBILE;
	public String getSALE_ORDER_ID() {		return this.sALE_ORDER_ID;	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {		this.sALE_ORDER_ID = sALE_ORDER_ID;	}
	public String getADDRESS() {		return this.aDDRESS;	}
	public void setADDRESS(String aDDRESS) {		this.aDDRESS = aDDRESS;	}
	public String getRECEIVE_NAME() {		return this.rECEIVE_NAME;	}
	public void setRECEIVE_NAME(String rECEIVE_NAME) {		this.rECEIVE_NAME = rECEIVE_NAME;	}
	public String getPOST_CODE() {		return this.pOST_CODE;	}
	public void setPOST_CODE(String pOST_CODE) {		this.pOST_CODE = pOST_CODE;	}
	public String getMOBILE() {		return this.mOBILE;	}
	public void setMOBILE(String mOBILE) {		this.mOBILE = mOBILE;	}
	public RECEIVE_INFO() {}
	public RECEIVE_INFO(String sALE_ORDER_ID, String aDDRESS, String rECEIVE_NAME, String pOST_CODE, String mOBILE){
		super();		this.sALE_ORDER_ID = sALE_ORDER_ID;		this.aDDRESS = aDDRESS;		this.rECEIVE_NAME = rECEIVE_NAME;		this.pOST_CODE = pOST_CODE;		this.mOBILE = mOBILE;
	}
	public String toString() {
		return "RECEIVE_INFO [sALE_ORDER_ID = " + sALE_ORDER_ID + ", aDDRESS = " + aDDRESS + ", rECEIVE_NAME = " + rECEIVE_NAME + ", pOST_CODE = " + pOST_CODE + ", mOBILE = " + mOBILE + "]";	}
}
class SALE_CUST implements Serializable{	private static final long serialVersionUID = 801352273L;	private String sALE_ORDER_ID;	private String cUST_ID;	private String eMAIL;	private String cUST_NAME;	private String cERT_TYPE;	private String bIRTHDATE;	private String aUTH_FLAG;	private String nEW_FLAG;	private String cERT_NBR;
	public String getSALE_ORDER_ID() {		return this.sALE_ORDER_ID;	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {		this.sALE_ORDER_ID = sALE_ORDER_ID;	}
	public String getCUST_ID() {		return this.cUST_ID;	}
	public void setCUST_ID(String cUST_ID) {		this.cUST_ID = cUST_ID;	}
	public String getEMAIL() {		return this.eMAIL;	}
	public void setEMAIL(String eMAIL) {		this.eMAIL = eMAIL;	}
	public String getCUST_NAME() {		return this.cUST_NAME;	}
	public void setCUST_NAME(String cUST_NAME) {		this.cUST_NAME = cUST_NAME;	}
	public String getCERT_TYPE() {		return this.cERT_TYPE;	}
	public void setCERT_TYPE(String cERT_TYPE) {		this.cERT_TYPE = cERT_TYPE;	}
	public String getBIRTHDATE() {		return this.bIRTHDATE;	}
	public void setBIRTHDATE(String bIRTHDATE) {		this.bIRTHDATE = bIRTHDATE;	}
	public String getAUTH_FLAG() {		return this.aUTH_FLAG;	}
	public void setAUTH_FLAG(String aUTH_FLAG) {		this.aUTH_FLAG = aUTH_FLAG;	}
	public String getNEW_FLAG() {		return this.nEW_FLAG;	}
	public void setNEW_FLAG(String nEW_FLAG) {		this.nEW_FLAG = nEW_FLAG;	}
	public String getCERT_NBR() {		return this.cERT_NBR;	}
	public void setCERT_NBR(String cERT_NBR) {		this.cERT_NBR = cERT_NBR;	}
	public SALE_CUST() {}
	public SALE_CUST(String sALE_ORDER_ID, String cUST_ID, String eMAIL, String cUST_NAME, String cERT_TYPE, String bIRTHDATE, String aUTH_FLAG, String nEW_FLAG, String cERT_NBR){
		super();		this.sALE_ORDER_ID = sALE_ORDER_ID;		this.cUST_ID = cUST_ID;		this.eMAIL = eMAIL;		this.cUST_NAME = cUST_NAME;		this.cERT_TYPE = cERT_TYPE;		this.bIRTHDATE = bIRTHDATE;		this.aUTH_FLAG = aUTH_FLAG;		this.nEW_FLAG = nEW_FLAG;		this.cERT_NBR = cERT_NBR;
	}
	public String toString() {
		return "SALE_CUST [sALE_ORDER_ID = " + sALE_ORDER_ID + ", cUST_ID = " + cUST_ID + ", eMAIL = " + eMAIL + ", cUST_NAME = " + cUST_NAME + ", cERT_TYPE = " + cERT_TYPE + ", bIRTHDATE = " + bIRTHDATE + ", aUTH_FLAG = " + aUTH_FLAG + ", nEW_FLAG = " + nEW_FLAG + ", cERT_NBR = " + cERT_NBR + "]";	}
}
class SALE_PROD_INST_ATTR implements Serializable{	private static final long serialVersionUID = 2079034930L;	private String aTTR_ID;	private String aTTR_NAME;	private String pROD_INST_ID;	private String aTTR_VALUE;
	public String getATTR_ID() {		return this.aTTR_ID;	}
	public void setATTR_ID(String aTTR_ID) {		this.aTTR_ID = aTTR_ID;	}
	public String getATTR_NAME() {		return this.aTTR_NAME;	}
	public void setATTR_NAME(String aTTR_NAME) {		this.aTTR_NAME = aTTR_NAME;	}
	public String getPROD_INST_ID() {		return this.pROD_INST_ID;	}
	public void setPROD_INST_ID(String pROD_INST_ID) {		this.pROD_INST_ID = pROD_INST_ID;	}
	public String getATTR_VALUE() {		return this.aTTR_VALUE;	}
	public void setATTR_VALUE(String aTTR_VALUE) {		this.aTTR_VALUE = aTTR_VALUE;	}
	public SALE_PROD_INST_ATTR() {}
	public SALE_PROD_INST_ATTR(String aTTR_ID, String aTTR_NAME, String pROD_INST_ID, String aTTR_VALUE){
		super();		this.aTTR_ID = aTTR_ID;		this.aTTR_NAME = aTTR_NAME;		this.pROD_INST_ID = pROD_INST_ID;		this.aTTR_VALUE = aTTR_VALUE;
	}
	public String toString() {
		return "SALE_PROD_INST_ATTR [aTTR_ID = " + aTTR_ID + ", aTTR_NAME = " + aTTR_NAME + ", pROD_INST_ID = " + pROD_INST_ID + ", aTTR_VALUE = " + aTTR_VALUE + "]";	}
}
class SALE_PROD_INST implements Serializable{	private static final long serialVersionUID = 1788161L;	private String sALE_ORDER_ID;	private String aCC_NBR;	private String nEW_FLAG;	private String pROD_INST_ID;	private String pRODUCT_ID;
	public String getSALE_ORDER_ID() {		return this.sALE_ORDER_ID;	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {		this.sALE_ORDER_ID = sALE_ORDER_ID;	}
	public String getACC_NBR() {		return this.aCC_NBR;	}
	public void setACC_NBR(String aCC_NBR) {		this.aCC_NBR = aCC_NBR;	}
	public String getNEW_FLAG() {		return this.nEW_FLAG;	}
	public void setNEW_FLAG(String nEW_FLAG) {		this.nEW_FLAG = nEW_FLAG;	}
	public String getPROD_INST_ID() {		return this.pROD_INST_ID;	}
	public void setPROD_INST_ID(String pROD_INST_ID) {		this.pROD_INST_ID = pROD_INST_ID;	}
	public String getPRODUCT_ID() {		return this.pRODUCT_ID;	}
	public void setPRODUCT_ID(String pRODUCT_ID) {		this.pRODUCT_ID = pRODUCT_ID;	}
	public SALE_PROD_INST() {}
	public SALE_PROD_INST(String sALE_ORDER_ID, String aCC_NBR, String nEW_FLAG, String pROD_INST_ID, String pRODUCT_ID){
		super();		this.sALE_ORDER_ID = sALE_ORDER_ID;		this.aCC_NBR = aCC_NBR;		this.nEW_FLAG = nEW_FLAG;		this.pROD_INST_ID = pROD_INST_ID;		this.pRODUCT_ID = pRODUCT_ID;
	}
	public String toString() {
		return "SALE_PROD_INST [sALE_ORDER_ID = " + sALE_ORDER_ID + ", aCC_NBR = " + aCC_NBR + ", nEW_FLAG = " + nEW_FLAG + ", pROD_INST_ID = " + pROD_INST_ID + ", pRODUCT_ID = " + pRODUCT_ID + "]";	}
}
class SALE_OFFER_INST implements Serializable{	private static final long serialVersionUID = 1788161L;	private String oFFER_TYPE_ID;	private String oFFER_ID;	private String nEW_FLAG;	private String oFFER_INST_ID;	private String sALE_ORDER_ID;
	public String getOFFER_TYPE_ID() {		return this.oFFER_TYPE_ID;	}
	public void setOFFER_TYPE_ID(String oFFER_TYPE_ID) {		this.oFFER_TYPE_ID = oFFER_TYPE_ID;	}
	public String getOFFER_ID() {		return this.oFFER_ID;	}
	public void setOFFER_ID(String oFFER_ID) {		this.oFFER_ID = oFFER_ID;	}
	public String getNEW_FLAG() {		return this.nEW_FLAG;	}
	public void setNEW_FLAG(String nEW_FLAG) {		this.nEW_FLAG = nEW_FLAG;	}
	public String getOFFER_INST_ID() {		return this.oFFER_INST_ID;	}
	public void setOFFER_INST_ID(String oFFER_INST_ID) {		this.oFFER_INST_ID = oFFER_INST_ID;	}
	public String getSALE_ORDER_ID() {		return this.sALE_ORDER_ID;	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {		this.sALE_ORDER_ID = sALE_ORDER_ID;	}
	public SALE_OFFER_INST() {}
	public SALE_OFFER_INST(String oFFER_TYPE_ID, String oFFER_ID, String nEW_FLAG, String oFFER_INST_ID, String sALE_ORDER_ID){
		super();		this.oFFER_TYPE_ID = oFFER_TYPE_ID;		this.oFFER_ID = oFFER_ID;		this.nEW_FLAG = nEW_FLAG;		this.oFFER_INST_ID = oFFER_INST_ID;		this.sALE_ORDER_ID = sALE_ORDER_ID;
	}
	public String toString() {
		return "SALE_OFFER_INST [oFFER_TYPE_ID = " + oFFER_TYPE_ID + ", oFFER_ID = " + oFFER_ID + ", nEW_FLAG = " + nEW_FLAG + ", oFFER_INST_ID = " + oFFER_INST_ID + ", sALE_ORDER_ID = " + sALE_ORDER_ID + "]";	}
}
class COMM_ATTR implements Serializable{	private static final long serialVersionUID = 1279470818L;	private String aTTR_ID;	private String cOMM_INST_ID;	private String aTTR_NAME;	private String aTTR_VALUE;
	public String getATTR_ID() {		return this.aTTR_ID;	}
	public void setATTR_ID(String aTTR_ID) {		this.aTTR_ID = aTTR_ID;	}
	public String getCOMM_INST_ID() {		return this.cOMM_INST_ID;	}
	public void setCOMM_INST_ID(String cOMM_INST_ID) {		this.cOMM_INST_ID = cOMM_INST_ID;	}
	public String getATTR_NAME() {		return this.aTTR_NAME;	}
	public void setATTR_NAME(String aTTR_NAME) {		this.aTTR_NAME = aTTR_NAME;	}
	public String getATTR_VALUE() {		return this.aTTR_VALUE;	}
	public void setATTR_VALUE(String aTTR_VALUE) {		this.aTTR_VALUE = aTTR_VALUE;	}
	public COMM_ATTR() {}
	public COMM_ATTR(String aTTR_ID, String cOMM_INST_ID, String aTTR_NAME, String aTTR_VALUE){
		super();		this.aTTR_ID = aTTR_ID;		this.cOMM_INST_ID = cOMM_INST_ID;		this.aTTR_NAME = aTTR_NAME;		this.aTTR_VALUE = aTTR_VALUE;
	}
	public String toString() {
		return "COMM_ATTR [aTTR_ID = " + aTTR_ID + ", cOMM_INST_ID = " + cOMM_INST_ID + ", aTTR_NAME = " + aTTR_NAME + ", aTTR_VALUE = " + aTTR_VALUE + "]";	}
}
class COMMODITY implements Serializable{	private static final long serialVersionUID = 1349707696L;	private String uNIT_PRICE;	private String cOMM_NAME;	private String cOMM_INST_ID;	private String cOMM_CNT;	private String cOMM_ID;	private String nEW_FLAG;	private String dISCOUNT;
	public String getUNIT_PRICE() {		return this.uNIT_PRICE;	}
	public void setUNIT_PRICE(String uNIT_PRICE) {		this.uNIT_PRICE = uNIT_PRICE;	}
	public String getCOMM_NAME() {		return this.cOMM_NAME;	}
	public void setCOMM_NAME(String cOMM_NAME) {		this.cOMM_NAME = cOMM_NAME;	}
	public String getCOMM_INST_ID() {		return this.cOMM_INST_ID;	}
	public void setCOMM_INST_ID(String cOMM_INST_ID) {		this.cOMM_INST_ID = cOMM_INST_ID;	}
	public String getCOMM_CNT() {		return this.cOMM_CNT;	}
	public void setCOMM_CNT(String cOMM_CNT) {		this.cOMM_CNT = cOMM_CNT;	}
	public String getCOMM_ID() {		return this.cOMM_ID;	}
	public void setCOMM_ID(String cOMM_ID) {		this.cOMM_ID = cOMM_ID;	}
	public String getNEW_FLAG() {		return this.nEW_FLAG;	}
	public void setNEW_FLAG(String nEW_FLAG) {		this.nEW_FLAG = nEW_FLAG;	}
	public String getDISCOUNT() {		return this.dISCOUNT;	}
	public void setDISCOUNT(String dISCOUNT) {		this.dISCOUNT = dISCOUNT;	}
	public COMMODITY() {}
	public COMMODITY(String uNIT_PRICE, String cOMM_NAME, String cOMM_INST_ID, String cOMM_CNT, String cOMM_ID, String nEW_FLAG, String dISCOUNT){
		super();		this.uNIT_PRICE = uNIT_PRICE;		this.cOMM_NAME = cOMM_NAME;		this.cOMM_INST_ID = cOMM_INST_ID;		this.cOMM_CNT = cOMM_CNT;		this.cOMM_ID = cOMM_ID;		this.nEW_FLAG = nEW_FLAG;		this.dISCOUNT = dISCOUNT;
	}
	public String toString() {
		return "COMMODITY [uNIT_PRICE = " + uNIT_PRICE + ", cOMM_NAME = " + cOMM_NAME + ", cOMM_INST_ID = " + cOMM_INST_ID + ", cOMM_CNT = " + cOMM_CNT + ", cOMM_ID = " + cOMM_ID + ", nEW_FLAG = " + nEW_FLAG + ", dISCOUNT = " + dISCOUNT + "]";	}
}
class SOO implements Serializable{	private static final long serialVersionUID = 642019674L;	private PUB_REQ pUB_REQ;	private List<SALE_RES_INST> sALE_RES_INST;	private List<NUMBER_INFO> nUMBER_INFO;	private List<PAYMENT> pAYMENT;	private List<FEE_ITEM> fEE_ITEM;	private List<RECEIVE_INFO> rECEIVE_INFO;	private List<SALE_CUST> sALE_CUST;	private List<SALE_PROD_INST_ATTR> sALE_PROD_INST_ATTR;	private List<SALE_PROD_INST> sALE_PROD_INST;	private List<SALE_OFFER_INST> sALE_OFFER_INST;	private List<COMM_ATTR> cOMM_ATTR;	private List<COMMODITY> cOMMODITY;	private List<SALE_ORDER_INST> sALE_ORDER_INST;
	public PUB_REQ getPUB_REQ() {		return this.pUB_REQ;	}
	public void setPUB_REQ(PUB_REQ pUB_REQ) {		this.pUB_REQ = pUB_REQ;	}
	public List<SALE_RES_INST> getSALE_RES_INST() {		return this.sALE_RES_INST;	}
	public void setSALE_RES_INST(List<SALE_RES_INST> sALE_RES_INST) {		this.sALE_RES_INST = sALE_RES_INST;	}
	public List<NUMBER_INFO> getNUMBER_INFO() {		return this.nUMBER_INFO;	}
	public void setNUMBER_INFO(List<NUMBER_INFO> nUMBER_INFO) {		this.nUMBER_INFO = nUMBER_INFO;	}
	public List<PAYMENT> getPAYMENT() {		return this.pAYMENT;	}
	public void setPAYMENT(List<PAYMENT> pAYMENT) {		this.pAYMENT = pAYMENT;	}
	public List<FEE_ITEM> getFEE_ITEM() {		return this.fEE_ITEM;	}
	public void setFEE_ITEM(List<FEE_ITEM> fEE_ITEM) {		this.fEE_ITEM = fEE_ITEM;	}
	public List<RECEIVE_INFO> getRECEIVE_INFO() {		return this.rECEIVE_INFO;	}
	public void setRECEIVE_INFO(List<RECEIVE_INFO> rECEIVE_INFO) {		this.rECEIVE_INFO = rECEIVE_INFO;	}
	public List<SALE_CUST> getSALE_CUST() {		return this.sALE_CUST;	}
	public void setSALE_CUST(List<SALE_CUST> sALE_CUST) {		this.sALE_CUST = sALE_CUST;	}
	public List<SALE_PROD_INST_ATTR> getSALE_PROD_INST_ATTR() {		return this.sALE_PROD_INST_ATTR;	}
	public void setSALE_PROD_INST_ATTR(List<SALE_PROD_INST_ATTR> sALE_PROD_INST_ATTR) {		this.sALE_PROD_INST_ATTR = sALE_PROD_INST_ATTR;	}
	public List<SALE_PROD_INST> getSALE_PROD_INST() {		return this.sALE_PROD_INST;	}
	public void setSALE_PROD_INST(List<SALE_PROD_INST> sALE_PROD_INST) {		this.sALE_PROD_INST = sALE_PROD_INST;	}
	public List<SALE_OFFER_INST> getSALE_OFFER_INST() {		return this.sALE_OFFER_INST;	}
	public void setSALE_OFFER_INST(List<SALE_OFFER_INST> sALE_OFFER_INST) {		this.sALE_OFFER_INST = sALE_OFFER_INST;	}
	public List<COMM_ATTR> getCOMM_ATTR() {		return this.cOMM_ATTR;	}
	public void setCOMM_ATTR(List<COMM_ATTR> cOMM_ATTR) {		this.cOMM_ATTR = cOMM_ATTR;	}
	public List<COMMODITY> getCOMMODITY() {		return this.cOMMODITY;	}
	public void setCOMMODITY(List<COMMODITY> cOMMODITY) {		this.cOMMODITY = cOMMODITY;	}
	public List<SALE_ORDER_INST> getSALE_ORDER_INST() {		return this.sALE_ORDER_INST;	}
	public void setSALE_ORDER_INST(List<SALE_ORDER_INST> sALE_ORDER_INST) {		this.sALE_ORDER_INST = sALE_ORDER_INST;	}
	public SOO() {}
	public SOO(PUB_REQ pUB_REQ, List<SALE_RES_INST> sALE_RES_INST, List<NUMBER_INFO> nUMBER_INFO, List<PAYMENT> pAYMENT, List<FEE_ITEM> fEE_ITEM, List<RECEIVE_INFO> rECEIVE_INFO, List<SALE_CUST> sALE_CUST, List<SALE_PROD_INST_ATTR> sALE_PROD_INST_ATTR, List<SALE_PROD_INST> sALE_PROD_INST, List<SALE_OFFER_INST> sALE_OFFER_INST, List<COMM_ATTR> cOMM_ATTR, List<COMMODITY> cOMMODITY, List<SALE_ORDER_INST> sALE_ORDER_INST){
		super();		this.pUB_REQ = pUB_REQ;		this.sALE_RES_INST = sALE_RES_INST;		this.nUMBER_INFO = nUMBER_INFO;		this.pAYMENT = pAYMENT;		this.fEE_ITEM = fEE_ITEM;		this.rECEIVE_INFO = rECEIVE_INFO;		this.sALE_CUST = sALE_CUST;		this.sALE_PROD_INST_ATTR = sALE_PROD_INST_ATTR;		this.sALE_PROD_INST = sALE_PROD_INST;		this.sALE_OFFER_INST = sALE_OFFER_INST;		this.cOMM_ATTR = cOMM_ATTR;		this.cOMMODITY = cOMMODITY;		this.sALE_ORDER_INST = sALE_ORDER_INST;
	}
	public String toString() {
		return "SOO [pUB_REQ = " + pUB_REQ + ", sALE_RES_INST = " + sALE_RES_INST + ", nUMBER_INFO = " + nUMBER_INFO + ", pAYMENT = " + pAYMENT + ", fEE_ITEM = " + fEE_ITEM + ", rECEIVE_INFO = " + rECEIVE_INFO + ", sALE_CUST = " + sALE_CUST + ", sALE_PROD_INST_ATTR = " + sALE_PROD_INST_ATTR + ", sALE_PROD_INST = " + sALE_PROD_INST + ", sALE_OFFER_INST = " + sALE_OFFER_INST + ", cOMM_ATTR = " + cOMM_ATTR + ", cOMMODITY = " + cOMMODITY + ", sALE_ORDER_INST = " + sALE_ORDER_INST + "]";	}
}
class PUB_REQ implements Serializable{	private static final long serialVersionUID = 1044461351L;	private String tYPE;
	public String getTYPE() {		return this.tYPE;	}
	public void setTYPE(String tYPE) {		this.tYPE = tYPE;	}
	public PUB_REQ() {}
	public PUB_REQ(String tYPE){
		super();		this.tYPE = tYPE;
	}
	public String toString() {
		return "PUB_REQ [tYPE = " + tYPE + "]";	}
}
class SALE_ORDER_INST implements Serializable{	private static final long serialVersionUID = 1349707696L;	private String sEL_IN_ORG_ID;	private String eXT_SYSTEM;	private String cUST_ID;	private String pAY_STATUS;	private String sALE_ORDER_ID;	private String oRDER_TYPE;	private String eXT_ORDER_ID;	private String bUSINESS_TYPE;
	public String getSEL_IN_ORG_ID() {		return this.sEL_IN_ORG_ID;	}
	public void setSEL_IN_ORG_ID(String sEL_IN_ORG_ID) {		this.sEL_IN_ORG_ID = sEL_IN_ORG_ID;	}
	public String getEXT_SYSTEM() {		return this.eXT_SYSTEM;	}
	public void setEXT_SYSTEM(String eXT_SYSTEM) {		this.eXT_SYSTEM = eXT_SYSTEM;	}
	public String getCUST_ID() {		return this.cUST_ID;	}
	public void setCUST_ID(String cUST_ID) {		this.cUST_ID = cUST_ID;	}
	public String getPAY_STATUS() {		return this.pAY_STATUS;	}
	public void setPAY_STATUS(String pAY_STATUS) {		this.pAY_STATUS = pAY_STATUS;	}
	public String getSALE_ORDER_ID() {		return this.sALE_ORDER_ID;	}
	public void setSALE_ORDER_ID(String sALE_ORDER_ID) {		this.sALE_ORDER_ID = sALE_ORDER_ID;	}
	public String getORDER_TYPE() {		return this.oRDER_TYPE;	}
	public void setORDER_TYPE(String oRDER_TYPE) {		this.oRDER_TYPE = oRDER_TYPE;	}
	public String getEXT_ORDER_ID() {		return this.eXT_ORDER_ID;	}
	public void setEXT_ORDER_ID(String eXT_ORDER_ID) {		this.eXT_ORDER_ID = eXT_ORDER_ID;	}
	public String getBUSINESS_TYPE() {		return this.bUSINESS_TYPE;	}
	public void setBUSINESS_TYPE(String bUSINESS_TYPE) {		this.bUSINESS_TYPE = bUSINESS_TYPE;	}
	public SALE_ORDER_INST() {}
	public SALE_ORDER_INST(String sEL_IN_ORG_ID, String eXT_SYSTEM, String cUST_ID, String pAY_STATUS, String sALE_ORDER_ID, String oRDER_TYPE, String eXT_ORDER_ID, String bUSINESS_TYPE){
		super();		this.sEL_IN_ORG_ID = sEL_IN_ORG_ID;		this.eXT_SYSTEM = eXT_SYSTEM;		this.cUST_ID = cUST_ID;		this.pAY_STATUS = pAY_STATUS;		this.sALE_ORDER_ID = sALE_ORDER_ID;		this.oRDER_TYPE = oRDER_TYPE;		this.eXT_ORDER_ID = eXT_ORDER_ID;		this.bUSINESS_TYPE = bUSINESS_TYPE;
	}
	public String toString() {
		return "SALE_ORDER_INST [sEL_IN_ORG_ID = " + sEL_IN_ORG_ID + ", eXT_SYSTEM = " + eXT_SYSTEM + ", cUST_ID = " + cUST_ID + ", pAY_STATUS = " + pAY_STATUS + ", sALE_ORDER_ID = " + sALE_ORDER_ID + ", oRDER_TYPE = " + oRDER_TYPE + ", eXT_ORDER_ID = " + eXT_ORDER_ID + ", bUSINESS_TYPE = " + bUSINESS_TYPE + "]";	}
}
