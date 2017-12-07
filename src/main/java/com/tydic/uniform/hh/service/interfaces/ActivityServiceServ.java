package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.ActivityVo;
/**
 * <p></p>
 * @author ghp 2015年9月29日 下午5:31:23
 * @ClassName ActivityServiceServ  首页活动列表接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public interface ActivityServiceServ {
	public Map<String,Object> qryactivity(ActivityVo activityVo);
	public Map<String,Object> qryiosactivity();
	
	//话费宝新春红包
	public Map<String,Object> qryRedUrl(ActivityVo activityVo);
}
