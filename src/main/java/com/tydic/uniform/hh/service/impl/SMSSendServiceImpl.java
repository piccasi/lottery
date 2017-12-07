package com.tydic.uniform.hh.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.baseInvoke.IntefaceType;
import com.tydic.uniform.hh.baseInvoke.ResponseBean;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.SMSSendServiceServ;
import com.tydic.uniform.hh.vo.rep.SMSInfoVo;

/**
 * <p>
 * </p>
 * 
 * @author ghp 2015年9月29日 下午6:16:12
 * @ClassName SMSSendServiceImpl 短信发送接口实体
 * @Description TODO
 * @version V1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年9月29日
 * @modify by reason:{方法名}:{原因}
 */
@Service("SMSSendServiceServ")
public class SMSSendServiceImpl extends AbstractService implements
		SMSSendServiceServ {
	private static Logger log = Logger.getLogger(SMSSendServiceImpl.class);

	@Override
	public Map<String, Object> sendSMS(SMSInfoVo smsInfoVo) {
		log.info("*************************短信 接口入参*************************");
		log.info("APP request str:"
				+ JSONObject.fromObject(smsInfoVo).toString());
		/*
		 * 按照BOSS文档设置接口入参
		 */
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("mobile", smsInfoVo.getMobile());
		body.put("content", smsInfoVo.getContent());

		setIntefaceType(IntefaceType.THIRDPART);
		ResponseBean bean = (ResponseBean) this.appApiInvoke(body, "SC1004003", ResponseBean.class);
		/*
		 * 解析接口返参
		 */
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, Object> resultResultMap = (Map<String, Object>) bean.getBody().get(0);
		String status = (String) resultResultMap.get("code");
		String result = (String) resultResultMap.get("desc");

		if (HhConstants.SUCCESS.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.MESSAGE, result);
		} else {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, HhConstants.ERRORMESSAGE);// 失败描述
			if (result != null)
				resultMap.put(HhConstants.MESSAGE, result);
		}
		return resultMap;
	}

}