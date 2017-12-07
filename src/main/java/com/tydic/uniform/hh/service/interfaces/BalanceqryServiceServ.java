package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.BalanceqryIndexVo;
import com.tydic.uniform.hh.vo.rep.BalanceqryVo;

/**
 * <p></p>
 * @author ghp 2015年10月12日 下午4:57:59
 * @ClassName BalanceqryServiceServ
 * @Description TODO  1.10.	会员余额查询接口
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月12日
 * @modify by reason:{方法名}:{原因}
 */
public interface BalanceqryServiceServ {
	public Map<String,Object> balanceqry (BalanceqryVo balanceqryvo);
	
	public Map<String,Object> balanceIndexqry (BalanceqryIndexVo balanceqryIndexVo);
}
