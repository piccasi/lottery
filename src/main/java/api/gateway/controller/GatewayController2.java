package api.gateway.controller;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import api.gateway.enums.BafSubjectEnums;
import api.gateway.exception.HandlerException;
import api.gateway.exception.ParametersValidException;
import api.gateway.handlerinterface.GatewayHandler;
import api.gateway.util.SpringBeanUtil;

import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.constant.CODE;


/**
 * API接口入口
 *
 * @author zhaozm
 * @date 2017年12月05日上午11:16:16
 * @since 1.0.0
 *
 */
@Controller
@RequestMapping("/api/gateway2")
public class GatewayController2 {

	private static final Logger logger = Logger.getLogger(GatewayController2.class);

	/**
	 * 处理请求方法
	 *
	 * @param request
	 * @return
	 */
	
	//@ResponseBody
	@RequestMapping(method = RequestMethod.POST,produces = 
			"application/json; charset=utf-8")
	public void handler(@RequestBody String json, HttpServletResponse response) {
		//final CommonResponse response = new CommonResponse();
		logger.info("@RequestBody json: " + json);
		logger.info("response is null: " + response==null);
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter writer = null;
		String ret = "";

		try {
			 writer = response.getWriter();
			JSONObject obj = JSONObject.fromObject(json);
			final String subject = obj.getJSONObject("HEADER").getString("SERVICE");//obj.getString("SERVICE");
			if (StringUtils.isEmpty(subject)) {
				//response.setResponseMsg("服务[SERVICE]为空");
				ret = JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "服务[SERVICE]为空");
				writer.print(ret);

				//return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "服务[SERVICE]为空");
			}
			//Map mapObj = (Map) JSONObject.toBean(obj, Map.class);//JSONObject.parseObject(json,Map.class);
			//Map<String, Object> mapJson = JSONObject.fromObject(obj);
			final BafSubjectEnums subjectEnum = BafSubjectEnums.typeOf(subject);
			if (subjectEnum == null) {
				//response.setResponseMsg("服务[SERVICE]不存在");
				ret = JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "服务不存在");
				writer.print(ret);
				//return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "服务不存在");
			}
			final GatewayHandler handler = (GatewayHandler) SpringBeanUtil.getBean(subjectEnum.getClassz());
			final Map<String, String> params = this.getParamMap(obj.getJSONObject("BODY"));

			handler.validParams(subjectEnum.getRequireds(), params);

			/*handler.checkSign(subjectEnum.getSigns(), params);*/ //不需要验证签名

			ret = handler.handler(subjectEnum.getType(), params,subjectEnum.getResStrs(),subjectEnum.getReqStrs());

			logger.info("handler.handler ret: " + ret);
			//response.getWriter().write(ret);
			//PrintWriter writer = response.getWriter();
			writer.print(ret);
			//writer.close();
			
			//return ret;
			//return handler.handler(subjectEnum.getType(), params,subjectEnum.getResStrs(),subjectEnum.getReqStrs());

		} catch (final Exception e) {
			String errorMessage = "API端内部异常，请联系管理员";
			if (e instanceof ParametersValidException) {
				errorMessage = ((ParametersValidException) e).getErrorMessage();
			}else if (e instanceof HandlerException) {
				errorMessage = ((HandlerException) e).getErrorMessage();
			}else {
				errorMessage = "未知异常";
			}
			/*response.setResponseMsg(errorMessage);
			response.setSuccess(false);
			response.setResponseCode("1");*/
			GatewayController2.logger.error(errorMessage, e);
			
			ret = JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, errorMessage);
			writer.print(ret);

			//return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, errorMessage);
		} finally{
			writer.close();
		}
		//return JsonResponse.toErrorResult(CODE.PARAMETER_ERROR, "API端内部异常，请联系管理员");

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
	
	private Map<String, String> getParamMap(JSONObject body) throws Exception {
		final Map<String, String> map = new HashMap<String, String>();
		Iterator<String> it = body.keys();
		while(it.hasNext()){
			final String key = it.next();
			map.put(key, body.getString(key));
		}
		/*final Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			final String key = keys.nextElement();
			map.put(key, request.getParameter(key));
		}*/
		return map;
	}

}
