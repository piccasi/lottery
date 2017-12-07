package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年9月28日 下午7:19:34
 * @ClassName NumberListVo  号码查寻接口VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
public class NumberListVo {
	private String city;
	private String nbr_level;
	private String last_nbr;
	private String tele_type;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getNbr_level() {
		return nbr_level;
	}
	public void setNbr_level(String nbr_level) {
		this.nbr_level = nbr_level;
	}
	public String getLast_nbr() {
		return last_nbr;
	}
	public void setLast_nbr(String last_nbr) {
		this.last_nbr = last_nbr;
	}
	public String getTele_type() {
		return tele_type;
	}
	public void setTele_type(String tele_type) {
		this.tele_type = tele_type;
	}
	
}
