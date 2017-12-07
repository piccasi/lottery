package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.NumberManaVo;





/**
 * <p></p>
 * @author ghp 2015年9月28日 下午3:33:38
 * @ClassName NumberManaServiceServ  号码套餐查询接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
public interface NumberManaServiceServ {
	public Map<String,Object> numbermanaService (NumberManaVo numbermanaVo);
}
