package com.tydic.uniform.hh.vo.rep;


/**
 * <p></p>
 * @author ghp 2015年9月28日 下午8:12:11
 * @ClassName NumberHoldOrOV  号码预占/释放 VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
public class NumberHoldOrVo {
	private String res_nbr;
	private String res_code;
	private String opt_type;
	private String cust_id;
	
	public String getRes_nbr() {
		return res_nbr;
	}
	public void setRes_nbr(String res_nbr) {
		this.res_nbr = res_nbr;
	}
	public String getRes_code() {
		return res_code;
	}
	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}
	public String getOpt_type() {
		return opt_type;
	}
	public void setOpt_type(String opt_type) {
		this.opt_type = opt_type;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	
}
