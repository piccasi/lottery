package com.tydic.uniform.hh.constant;

/**
 * 订单类型
 * @author clw
 *
 */
public enum OrderType {
	YHFD(200,"成卡返档"),
	TCBG(11,"套餐变更"),
	DJYB(210,"订加油包"),
	SMBD(206,"实名补登");
	OrderType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    
    /**
	 * 根据编码获取枚举对象
	 */
	public static OrderType getEnumByCode(int code) {
		for (OrderType enm : OrderType.values()) {
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
		OrderType enm = getEnumByCode(code);
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
