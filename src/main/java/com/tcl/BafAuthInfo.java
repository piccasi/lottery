package com.tcl;

import com.tcl.TclMsg;

public class BafAuthInfo {

	protected static String REGION_ID = "REGION_ID";

	protected static String COUNTY_ID = "COUNTY_ID";

	protected static String OFFICE_ID = "OFFICE_ID";

	protected static String OPERATOR_ID = "OPERATOR_ID";

	private String regionId;

	private String countyId;

	private String officeId;

	private String operatorId;

	public BafAuthInfo(String regionId, String countyId, String officeId,
			String operatorId) {
		super();

		this.regionId = regionId;
		this.countyId = countyId;
		this.officeId = officeId;
		this.operatorId = operatorId;
	}

	public String getCountyId() {
		return countyId;
	}

	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public TclMsg toTclMsg() {
		TclMsg tclMsg = new TclMsg();

		tclMsg.append(REGION_ID, this.getRegionId());
		tclMsg.append(COUNTY_ID, this.getCountyId());
		tclMsg.append(OFFICE_ID, this.getOfficeId());
		tclMsg.append(OPERATOR_ID, this.getOperatorId());

		return tclMsg;
	}

}
