package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.AgentInfoQueryVo;

/**
 * <p></p>
 * @author yiyaohong 2015年12月8日 下午2:11:06
 * @ClassName AgentInfoQueryServ  代理商用户基本信息查询接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年12月8日
 * @modify by reason:{方法名}:{原因}
 */

public interface AgentInfoQueryServ {
	public Map<String,Object> agentdetail(AgentInfoQueryVo agentinfoqueryvo);
}
