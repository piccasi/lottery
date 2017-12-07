package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.ContractMealVo;
import com.tydic.uniform.hh.vo.rep.ContractMobileVo;
/**
 * <p></p>
 * @author ghp 2015年9月29日 下午5:31:23
 * @ClassName ContractMobileServiceServ  合约机详情接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public interface ContractMobileServiceServ {
	public Map<String,Object> queryContractMobile (ContractMobileVo contractMobileVo);
	
	public Map<String,Object> queryContractMeal (ContractMealVo contractMealVo);
}
