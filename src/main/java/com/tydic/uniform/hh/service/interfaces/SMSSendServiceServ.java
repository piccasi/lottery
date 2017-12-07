package com.tydic.uniform.hh.service.interfaces;

import java.util.Map;

import com.tydic.uniform.hh.vo.rep.SMSInfoVo;

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
public interface SMSSendServiceServ {
	public Map<String, Object> sendSMS(SMSInfoVo smsInfoVo);
}
