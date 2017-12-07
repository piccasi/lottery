package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.NumberHoldOrVo;


/**
 * <p></p>
 * @author ghp 2015年9月28日 下午8:20:01
 * @ClassName NumberHoldOrServiceServ  号码预占/释放接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
public interface NumberHoldOrServiceServ {
	public Map<String,Object> numberholdor (NumberHoldOrVo numberholdorov);
}
