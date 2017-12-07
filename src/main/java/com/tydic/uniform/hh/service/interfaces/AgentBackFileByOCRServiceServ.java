package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.AgentBackFileByOCRVo;

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
public interface AgentBackFileByOCRServiceServ {

	Map<String, Object> agentBackfileByOCR(AgentBackFileByOCRVo agentbackfilebyocrvo);

}
