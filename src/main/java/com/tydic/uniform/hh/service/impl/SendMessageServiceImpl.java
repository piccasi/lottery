package com.tydic.uniform.hh.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.service.interfaces.SendMessageServiceServ;
import com.tydic.uniform.hh.util.StringUtil;
import com.tydic.uniform.hh.util.ValidateCodeUtil;
import com.tydic.uniform.hh.vo.rep.SMSInfoVo;
import com.tydic.uniform.hh.vo.rep.SendMessageVo;

/**
 * <p></p>
 * @author ghp 2015年10月20日 下午8:01:57
 * @ClassName SendMessageServiceImpl
 * @Description TODO 短信发送实体
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月20日
 * @modify by reason:{方法名}:{原因}
 */
@Service("SendMessageServiceServ")
public class SendMessageServiceImpl  extends AbstractService implements SendMessageServiceServ{
	private static Logger log = Logger.getLogger(SendMessageServiceImpl.class);

	@Override
	public Map<String, Object> resourceDonation(SendMessageVo sendmessagevo) {

		
		// 生成验证码
		String validateCode = ValidateCodeUtil.create(6);
		// 获取手机号
		String mobile = sendmessagevo.getNumber();
		//标志位
		String flag = sendmessagevo.getFlage();
		
		ValidateCodeUtil.put(mobile, validateCode);
		
		String msg="";
		//#详单查询短信模板
		if("1".equals(flag)){
			msg ="尊敬的客户，您好！您于#DATE#通过海航通信手机app查询清单信息，如需帮助，请致电10044客户服务热线。海航通信推出“存100送100”话费福利，期待您的参与，验证码："+validateCode;
		}else if("2".equals(flag)){
		//#预约成功发送短信模板
			msg = "尊敬的客户，您已成功预约海航通信#RESNBR#号码，请您登录海航通信手机app，谢谢！海航通信推出“存100送100”话费福利，期待您的参与，验证码："+validateCode;
		}else if("3".equals(flag)){
		//#预约注册成功发送短信模板
			//msg = "尊敬的客户，您已注册海航通信账户，登陆账号：联系号码（号码激活后为海航通信170号码），密码：身份证后6位。海航通信推出“存100送100”话费福利，期待您的参与，验证码："+validateCode;
			msg = "尊敬的客户：您正在注册海航通信账户，验证码："+validateCode;
		}else if("4".equals(flag)){
		//#注册发送短信验证码模板
			msg = "尊敬的客户：您正在注册海航通信账户，验证码："+validateCode;
		}else if("5".equals(flag)){
		//#修改密码发送短信验证码模板
			msg = "尊敬的客户：您正在通过海航通信手机app修改密码，验证码："+validateCode;
		}else if("6".equals(flag)){
		//#找回密码发送短信验证码模板
			msg = "尊敬的客户：您正在通过海航通信手机app找回密码，验证码："+validateCode;
		}else if("7".equals(flag)){
		//#找回密码发送短信验证码模板
			msg = "尊敬的客户：您正在通过海航通信手机app进行资源转赠，验证码："+validateCode;
		}else if("8".equals(flag)){
		//#找回密码发送短信验证码模板
			msg = "尊敬的客户：您正在通过海航通信手机app进行套餐变更，验证码："+validateCode;
		}
		
		Map <String,Object> resultMap = new HashMap <String,Object>();
		
		SMSSendServiceImpl smssendserviceimpl = new SMSSendServiceImpl();
		
		SMSInfoVo smsinfovo = new SMSInfoVo();
		smsinfovo.setContent(msg);
		smsinfovo.setMobile(mobile);
		Map map = smssendserviceimpl.sendSMS(smsinfovo);
		String status = (String) map.get(HhConstants.CODE);
		log.info("===========resp 验证码 ："+validateCode);
		if (HhConstants.SUCCESSCODE.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.SUCCESSCODE);// 成功
			resultMap.put(HhConstants.MESSAGE, map.get(HhConstants.MESSAGE));
			log.info("===========resp 验证码 ：成功");
		} else if (HhConstants.ERRORCODE.equals(status)) {
			resultMap.put(HhConstants.CODE, HhConstants.ERRORCODE);// 失败编码
			resultMap.put(HhConstants.MESSAGE, map.get(HhConstants.MESSAGE));
			log.info("===========resp 验证码 ：失败");
		}
		return resultMap;
	
	}
	
}
