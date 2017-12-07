package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.OrderCancleVo;
/**
 * 
 * <p></p>
 * @author gengtian 2015年10月14日 下午3:08:50
 * @ClassName OrderCancleServiceServ
 * @Description TODO 订单取消接口
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public interface OrderCancleServiceServ {
	Map<String, Object> cancleOrder(OrderCancleVo orderCancleVo);
}
