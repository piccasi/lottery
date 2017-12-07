package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年9月28日 下午3:30:58
 * @ClassName NumberManaVo  号码套餐查询接口Vo
 * @Description TODO  号码套餐查询接口入参映射
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
public class NumberManaVo {
	private String channel_code;
	private String ext_system;
	private String qry_number;
	private String ofr_mode;
	public String getChannel_code() {
		return channel_code;
	}
	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}
	public String getExt_system() {
		return ext_system;
	}
	public void setExt_system(String ext_system) {
		this.ext_system = ext_system;
	}
	public String getQry_number() {
		return qry_number;
	}
	public void setQry_number(String qry_number) {
		this.qry_number = qry_number;
	}
	public String getOfr_mode() {
		return ofr_mode;
	}
	public void setOfr_mode(String ofr_mode) {
		this.ofr_mode = ofr_mode;
	}
	
	
}
