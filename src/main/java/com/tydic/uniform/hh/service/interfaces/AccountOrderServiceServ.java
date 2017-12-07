package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.AccountOrderVo;


/**
 * <p></p>
 * @author ghp 2015年9月29日 下午5:31:23
 * @ClassName AccountOrderServiceServ  开户新增订单接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public interface AccountOrderServiceServ {
	public Map<String,Object> accountorder (AccountOrderVo accountordervo);
}
