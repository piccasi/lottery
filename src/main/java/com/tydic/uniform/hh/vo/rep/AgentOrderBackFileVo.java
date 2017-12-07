package com.tydic.uniform.hh.vo.rep;

public class AgentOrderBackFileVo {
	private String certNbr;	//身份证号
	private String name;	//姓名
	private String selfieFile;	//人脸对比的照片（活体检测第一张）
	private String selfieAutoRotate;//默认0
	
	public String getCertNbr() {
		return certNbr;
	}
	public void setCertNbr(String certNbr) {
		this.certNbr = certNbr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSelfieFile() {
		return selfieFile;
	}
	public void setSelfieFile(String selfieFile) {
		this.selfieFile = selfieFile;
	}
	public String getSelfieAutoRotate() {
		return selfieAutoRotate;
	}
	public void setSelfieAutoRotate(String selfieAutoRotate) {
		this.selfieAutoRotate = selfieAutoRotate;
	}
	
	

}
