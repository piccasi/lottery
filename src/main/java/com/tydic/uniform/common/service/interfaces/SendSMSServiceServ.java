package com.tydic.uniform.common.service.interfaces;

import java.util.Map;

import com.tydic.uniform.common.vo.rep.SMSInfoVo;

/**
 * 
 * <p></p>
 * @author Administrator 2015年10月14日 上午11:47:04
 * @ClassName SMSSendServiceServ
 * @Description 短信发送
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月14日
 * @modify by reason:{方法名}:{原因}
 */
public interface SendSMSServiceServ {
	public Map<String, Object> sendSMS(SMSInfoVo smsInfoVo);
}
