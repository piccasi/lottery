package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年10月12日 下午4:49:25
 * @ClassName BalanceqryVo
 * @Description TODO      1.10.	会员余额查询Vo
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月12日
 * @modify by reason:{方法名}:{原因}
 */
public class BalanceqryVo {
	private String memberid;
	private String qrytype;
	private String baltype;
	
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getQrytype() {
		return qrytype;
	}
	public void setQrytype(String qrytype) {
		this.qrytype = qrytype;
	}
	public String getBaltype() {
		return baltype;
	}
	public void setBaltype(String baltype) {
		this.baltype = baltype;
	}
	
	
}
