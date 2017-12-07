package com.tydic.uniform.common.service.interfaces;

import java.util.Map;

import com.tydic.uniform.common.vo.rep.MsgCheckVo;

/**
 * <p></p>
 * @author ghp 2015年10月20日 下午8:30:11
 * @ClassName MessageCheckServiceServ
 * @Description TODO  短信验证接口
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月20日
 * @modify by reason:{方法名}:{原因}
 */
public interface MsgCheckServiceServ {
	public Map<String,Object> accountorder (MsgCheckVo messagecheckvo);
}
