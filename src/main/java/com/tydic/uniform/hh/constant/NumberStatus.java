package com.tydic.uniform.hh.constant;

/**
 * 号码状态
 * @author clw
 *
 */
public enum NumberStatus {
	GS(122001,"挂失状态"),
	ZY(100000,"在用状态"),
	CJ(110000,"拆机状态"),
	SC(110098,"删除状态"),
	WJH(130000,"未激活状态"),
	ST(120000,"申停状态"),
	LSHM(140000,"临时号码"),
	QFDT(160000,"欠费单停状态"),
	QFST(170000,"欠费双停状态"),
	YYCJ(180000,"预约拆机状态");
	NumberStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    /**
	 * 根据编码获取枚举对象
	 */
	public static NumberStatus getEnumByCode(int code) {
		for (NumberStatus enm : NumberStatus.values()) {
			if (code == enm.getCode()) {
				return enm;
			}
		}
		return null;
	}
	
	/**
	 * 根据编码获取枚举描述
	 */
	public static String getDescByCode(int code) {
		NumberStatus enm = getEnumByCode(code);
		return enm == null ? null : enm.getDesc();
	}
	
    private int code; //枚举编码
    
    private String desc; //枚举描述
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }

	public String getDesc() {
        return desc;
    }
    
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
