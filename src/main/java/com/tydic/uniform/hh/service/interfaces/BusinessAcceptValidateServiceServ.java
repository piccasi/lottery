package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.BusinessAcceptValidateVo;

/**
 * 
 * <p></p>
 * @author Administrator 2015年10月14日 下午4:44:55
 * @ClassName BusinessAcceptValidateServiceServ
 * @Description 业务受理验证
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public interface BusinessAcceptValidateServiceServ {

	Map<String, Object> businessAcceptValidateService(BusinessAcceptValidateVo businessacceptvalidatevo);

}
