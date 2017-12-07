package com.tydic.uniform.hh.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.constant.BusinessType;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhControllerMappings;
import com.tydic.uniform.hh.constant.HhUrlsMappings;
import com.tydic.uniform.hh.service.interfaces.LogonInServiceServ;
import com.tydic.uniform.hh.util.IdentifyCodeUtil;
import com.tydic.uniform.hh.util.LoginUtil;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.util.StringUtil;
import com.tydic.uniform.hh.vo.rep.IdentifyCodeVo;
import com.tydic.uniform.hh.vo.rep.LogonInVo;

import net.sf.json.JSONObject;


/**
 * <p></p>
 * @author yiyaohong 2015年10月8日 下午3:50:26
 * @ClassName LogonInController  用户登录控制
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
@Controller
@RequestMapping(value = HhControllerMappings.LOGONIN)
public class LogonInController {
	
	private static Logger logger = Logger.getLogger(LogonInController.class);
	
	@Autowired
	private LogonInServiceServ logonInServiceServ;
	
/*	@RequestMapping(value = HhUrlsMappings.LOGONINLIST, method = RequestMethod.POST)
	@ResponseBody
	public String logoninlist(@RequestBody LogonInVo logoninvo, HttpRequest request) {
		
		Map<String, Object> result = logonInServiceServ.Logonin_bak(logoninvo);
        if(!StringUtils.isEmpty((String)result.get(HhConstants.CUST_ID))){//如果custId不为，放入session中
            request.getSession().setAttribute(Constants.USER_SESSION_KEY , result);
        }
		return DesEncryptUtil.encrypt(result);
	}*/
	
	/**
	 * 代理商原生APP登录
	 * @param logoninvo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = HhUrlsMappings.LOGIN, method = RequestMethod.POST)
	@ResponseBody
	public String logoninlist(@RequestBody LogonInVo logoninvo) {
		if(StringUtil.isNullOrBlank(logoninvo.getLogin_nbr()) || StringUtil.isNullOrBlank(logoninvo.getCode())){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, null);
		}
		
		String check = PropertiesUtil.getPropertyValue("smsIdentifyCode.check");
		
		//if(!logoninvo.getCode().equals("888888")){
		if(!"0".equals(check)){//1校验，0不校验
			IdentifyCodeVo ico = new IdentifyCodeVo();
			ico.setLogin_nbr(logoninvo.getLogin_nbr());
			ico.setCode(logoninvo.getCode());
			ico.setType(BusinessType.LOGIN);
			if(!IdentifyCodeUtil.isValidate(ico)){
				return JsonResponse.toErrorResult(CODE.VALIDATE_ERROR, null);
			}
		}
		
		return logonInServiceServ.Logonin(logoninvo);
	}
	
	/**
	 * 代理商原生APP登录
	 * @param logoninvo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = HhUrlsMappings.LOGIN_VALID, method = RequestMethod.POST)
	@ResponseBody
	public String tokenVerify(@RequestBody String tokenJson) {
		if(StringUtil.isNullOrBlank(tokenJson)){
			//Object tokenMap = JSON.parse(tokenJson);
			//String token =  (String) new JSONObject().fromObject(tokenJson).get("token");
			
			return JsonResponse.toErrorResult(CODE.NOT_LOGGED, null);
		}
		
		String token =  (String) new JSONObject().fromObject(tokenJson).get("token");
		if(!LoginUtil.isLogin(token)){
			return JsonResponse.toErrorResult(CODE.NOT_LOGGED, null);
		}
		
		return JsonResponse.toSuccessResult(null);
	}
	
	/**
	 * 代理商原生APP退出登录
	 * @param logoninvo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = HhUrlsMappings.LOGOUT, method = RequestMethod.POST)
	@ResponseBody
	public String logout(@RequestHeader("token") String token, @RequestBody LogonInVo logoninvo) {
		if(StringUtil.isNullOrBlank(logoninvo.getLogin_nbr()) || StringUtil.isNullOrBlank(token) ){
			return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, null);
		}	
		
		return logonInServiceServ.Logout(token, logoninvo);
	}
	
	/**
	 * 代理商原生APP登录
	 * @param logoninvo
	 * @param request
	 * @return
	 */
/*	@RequestMapping(value = HhUrlsMappings.LOGIN, method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestBody LogonInVo logoninvo , HttpServletRequest request) {
		return logonInServiceServ.login(logoninvo,request);
	}*/
	
	/**
	 * 代理商原生APP密码找回
	 * @param logoninvo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = HhUrlsMappings.LOGIN_PWD_BACK, method = RequestMethod.POST)
	@ResponseBody
	public String loginPwdBack(@RequestBody LogonInVo logoninvo , HttpServletRequest request) {
		String result = logonInServiceServ.Logonin(logoninvo);
		return DesEncryptUtil.encrypt(result);
	}
}
