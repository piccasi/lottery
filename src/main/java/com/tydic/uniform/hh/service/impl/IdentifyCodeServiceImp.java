package com.tydic.uniform.hh.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.AgentMobileService;
import com.tydic.uniform.hh.service.interfaces.IdentifyCodeService;
import com.tydic.uniform.hh.util.IdentifyCodeUtil;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.util.RedisUtil;
import com.tydic.uniform.hh.util.SmsTemplateUtil;
import com.tydic.uniform.hh.util.StringUtil;
import com.tydic.uniform.hh.util.TokenUtil;
import com.tydic.uniform.hh.vo.rep.IdentifyCodeVo;
import com.tydic.uniform.hh.vo.rep.SMSInfoVo;
import com.tydic.uniform.hh.service.interfaces.SMSSendServiceServ;

@Service("identifyCodeService")
public class IdentifyCodeServiceImp implements IdentifyCodeService {
	private static Logger logger = Logger.getLogger(IdentifyCodeServiceImp.class);
	
	@Autowired
	private AgentMobileService agentMobileService;
	
	@Autowired
	private SMSSendServiceServ SMSSendServiceServ;
	
	//private static final int expTime = 60 * 2;

	@Override
	public String sendCode(IdentifyCodeVo ico) {
		logger.info("sendCode:" + ico);
		
		String mobile;
		switch(ico.getType()){
			case LOGIN:
				mobile = agentMobileService.getMobile(ico.getLogin_nbr());
				break;
			default:
				mobile = ico.getMobile_170();
		}
		
		if(!StringUtil.isNullOrBlank(mobile) && mobile.contains("非代理商")){
			return JsonResponse.toErrorResult(CODE.NOT_AGENT_ACCOUNT, null);
		}
		
		if(StringUtil.isNullOrBlank(mobile) || !StringUtils.isNumeric(mobile)){
			return JsonResponse.toErrorResult(CODE.MOBILE_ERR, null);
		}
		
		logger.info("发送短信的号码为：" + mobile);
		
		String code = IdentifyCodeUtil.create(6);
		
		ico.setCode(code);
		
		Map<String, String> context = new HashMap<String, String>();
		context.put("code", code);
		//context.put("date", "2016-09-06 12:44:13");
		//生成短信内容
		String content =  SmsTemplateUtil.getSmsContent(ico.getType(), context);
		SMSInfoVo smi = new SMSInfoVo();
		smi.setContent(content);
		smi.setMobile(mobile);
		
		//发送短信
		//SMSSendServiceImpl sms = new SMSSendServiceImpl();
		Map<String, Object> ret =  SMSSendServiceServ.sendSMS(smi);
		String isSuccess = (String) ret.get(HhConstants.CODE);
		if(StringUtil.isNullOrBlank(isSuccess) || !HhConstants.SUCCESSCODE.equals(isSuccess) ){
			return JsonResponse.toErrorResult(CODE.VALIDATE_ERROR, null);
		}
		
		//存缓存
		boolean isStore = IdentifyCodeUtil.store(ico);
		if(!isStore){
			return JsonResponse.toErrorResult(CODE.VALIDATE_ERROR, null);
		}
		
		return JsonResponse.toSuccessResult(null);
	}

	
	public static void main(String[] args){
		String token = TokenUtil.getToken();
		System.out.println(token);
		new IdentifyCodeServiceImp().sendCode(null);
		String val = RedisUtil.get(token);
		System.out.println(val);
	}


	/**
	 * 验证码校验
	 */
	@Override
	public String checkCode(IdentifyCodeVo ico) {
		boolean valid = IdentifyCodeUtil.isValidate(ico);
		if(valid){
			return JsonResponse.toSuccessResult(null);
		}
		
		return JsonResponse.toErrorResult(CODE.VALIDATE_ERROR, null);
	}
	
	/**
	 * 非异步校验短信码
	 */
	@Override
	public boolean checkCodeSync(IdentifyCodeVo ico) {
		String check = PropertiesUtil.getPropertyValue("smsIdentifyCode.check");
		
		if(!"0".equals(check)){//1校验，0不校验
			boolean valid = IdentifyCodeUtil.isValidate(ico);
			if(valid){
				return true;
			}else{
				return false;
			}
		}
		
		return true;
	}

}
