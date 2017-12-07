package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;



/**
 * <p></p>
 * @author cws 2015年9月29日 下午5:31:23
 * @ClassName ChargeServiceServ  话费充值接口
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
public interface MemberChargeServiceServ {
	public Map<String, Object> chargeMoney(String memberId,String pay_amount);//话费充值
}