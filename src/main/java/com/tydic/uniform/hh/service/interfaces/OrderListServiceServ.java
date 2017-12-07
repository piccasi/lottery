package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.OrderListVo;

/**
 * 
 * <p></p>
 * @author gengtian 2015年10月13日 上午11:35:58
 * @ClassName OrderListServiceServ
 * @Description TODO 订单查询接口
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月13日
 * @modify by reason:{方法名}:{原因}
 */
public interface OrderListServiceServ {

	Map<String, Object> getOrderList(OrderListVo orderListVo);

}
