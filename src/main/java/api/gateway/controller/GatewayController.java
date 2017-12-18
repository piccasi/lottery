package api.gateway.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import api.gateway.common.CommonResponse;
import api.gateway.enums.BafSubjectEnums;
import api.gateway.exception.HandlerException;
import api.gateway.exception.ParametersValidException;
import api.gateway.handlerinterface.GatewayHandler;
import api.gateway.util.SpringBeanUtil;

import com.tcl.CommonResult;


/**
 * API接口入口
 *
 * @author zhaozm
 * @date 2017年12月05日上午11:16:16
 * @since 1.0.0
 *
 */
@Controller
@RequestMapping("/api/gateway")
public class GatewayController {

	private static final Logger logger = Logger.getLogger(GatewayController.class);

	/**
	 * 处理请求方法
	 *
	 * @param request
	 * @return
	 */
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String handler(HttpServletRequest request) {
		final CommonResponse response = new CommonResponse();
		try {
			final String subject = request.getParameter("SUBJECT");
			if (StringUtils.isEmpty(subject)) {
				response.setResponseMsg("主题[SUBJECT]为空");
				return response.toString();
			}

			final BafSubjectEnums subjectEnum = BafSubjectEnums.typeOf(subject);
			if (subjectEnum == null) {
				response.setResponseMsg("主题[SUBJECT]不存在");
				return response.toString();
			}
			final GatewayHandler handler = (GatewayHandler) SpringBeanUtil.getBean(subjectEnum.getClassz());
			final Map<String, String> params = this.getParamMap(request);

			handler.validParams(subjectEnum.getRequireds(), params);

			/*handler.checkSign(subjectEnum.getSigns(), params);*/ //不需要验证签名

			return handler.handler(subjectEnum.getType(),this.getParamMap(request),subjectEnum.getResStrs(),subjectEnum.getReqStrs());

		} catch (final Exception e) {
			String errorMessage = "API端内部异常，请联系管理员";
			if (e instanceof ParametersValidException) {
				errorMessage = ((ParametersValidException) e).getErrorMessage();
			}else if (e instanceof HandlerException) {
				errorMessage = ((HandlerException) e).getErrorMessage();
			}
			response.setResponseMsg(errorMessage);
			response.setSuccess(false);
			response.setResponseCode("1");
			GatewayController.logger.error(errorMessage, e);
		}
		return response.toString();

	}

	/**
	 * 加载request中的参数转为map
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private Map<String, String> getParamMap(HttpServletRequest request) throws Exception {
		final Map<String, String> map = new HashMap<String, String>();
		final Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			final String key = keys.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}

}
