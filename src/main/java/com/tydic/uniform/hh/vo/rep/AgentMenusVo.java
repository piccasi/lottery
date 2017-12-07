package com.tydic.uniform.hh.vo.rep;

/**
 * <p></p>
 * @author ghp 2015年9月29日 下午3:30:03
 * @ClassName AccountOrderVo   开户新增订单接口VO
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public class AgentMenusVo {

	private String pageName;
	private String appVersion;
	private String appOs;
	
	
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAppOs() {
		return appOs;
	}
	public void setAppOs(String appOs) {
		this.appOs = appOs;
	}
}
