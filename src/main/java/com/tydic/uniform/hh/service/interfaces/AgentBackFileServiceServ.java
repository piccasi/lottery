/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author ghp
 * @date: 2015年11月28日 下午6:39:06
 * @Title: AgentBackFileServiceServ.java
 * @Package com.tydic.uniform.hh.service.interfaces
 * @Description: TODO
 */
package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.AgentBackFileVo;

/**
 * <p></p>
 * @author ghp 2015年11月28日 下午6:39:06
 * @ClassName AgentBackFileServiceServ
 * @Description TODO 代理商返档接口
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年11月28日
 * @modify by reason:{方法名}:{原因}
 */
public interface AgentBackFileServiceServ {
	public Map<String,Object> agentBackfile (AgentBackFileVo agentbackfilevo);
}
