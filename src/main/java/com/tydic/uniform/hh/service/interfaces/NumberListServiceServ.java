package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.NumberListVo;


/**
 * <p></p>
 * @author ghp 2015年9月28日 下午7:24:11
 * @ClassName NumberListServiceServ  号码套餐查询接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月28日
 * @modify by reason:{方法名}:{原因}
 */
public interface NumberListServiceServ {
	public Map<String,Object> numberlistService (NumberListVo numberlistvo);
}
