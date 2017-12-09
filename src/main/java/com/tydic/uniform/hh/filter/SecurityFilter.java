package com.tydic.uniform.hh.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.tydic.uniform.common.constant.Constants;
import com.tydic.uniform.common.filter.HttpServletRequestWrapper;
import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.util.StringUtil;

public class SecurityFilter implements Filter {

	private static Logger log = Logger.getLogger(SecurityFilter.class);

	private static final List<String> filterUrls = new ArrayList<String>();
	//private static final List<String> noAuthUrls = new ArrayList<String>();
	//private List<String> uris;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		initfilterUrls(filterConfig);
		//initNoAuthUrls(filterConfig);

		/*uris = new ArrayList<String>();

		uris.add("/portal/login");
		uris.add("/health/search");
		uris.add("/health/analysis");*/
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = ((HttpServletRequest) (arg0));
		HttpServletResponse response = ((HttpServletResponse) arg1);
		// boolean isAjax = isAjaxRequest(request); // ajax请求标识

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// response.setHeader("token", "piccasi");
		// String contextPath = request.getContextPath();
		// log.debug("请求路径：" + contextPath);

		/** 权限拦截 */

		boolean isFilter = isFilterRequest(request);
		HttpServletRequestWrapper requestW = new HttpServletRequestWrapper((HttpServletRequest) request);//解密

		if (isFilter) {
			String header = requestW.getHeader();
			JSONObject obj = JSONObject.fromObject(header);

			CODE code = auth(request, obj);//签名及登录校验

			String ret = JsonResponse.toErrorResult(code, null);
			PrintWriter writer = response.getWriter();
			writer.print(ret);
			writer.close();
			log.info( request.getRequestURI() + " " + code.getDisplayMsg() + ", 返回报文: " + DesEncryptUtil.decrypt(ret));
			
			if(!CODE.SUCCESS.equals(code)){
				return;
			}
/*			switch (code) {
			case PARAMETER_ERROR:
			case NOT_LOGGED:
			case TIMEOUT:
			case REQERR:
				return;

			default:
				break;
			}*/
			
/*			if (CODE.NOT_LOGGED.equals(code)) {// 用户未登录
				// if(isAjax){
				
				writer.print(ret);
				writer.close();
				log.info("用户未登录:" + request.getRequestURI() + ", 返回报文: " + DesEncryptUtil.decrypt(ret));
				
				 * }else{ String repURL = contextPath +
				 * "/hhagent/html/login.html"; response.sendRedirect(repURL); }
				 
				return;
			} else if (CODE.TIMEOUT.equals(code)) {// 请求超时
				writer.print(ret);
				writer.close();
				log.info("请求超时:" + request.getRequestURI() + ", 返回报文: " + DesEncryptUtil.decrypt(ret));
				return;
			} else if (CODE.REQERR.equals(code)) {// 请求非法
				writer.print(ret);
				writer.close();
				log.info("请求非法:" + request.getRequestURI() + ", 返回报文: " + DesEncryptUtil.decrypt(ret));
				return;
			}*/
		}

		arg2.doFilter(requestW, arg1);
	}

	private boolean isFilterRequest(HttpServletRequest request) {

		String uri = request.getRequestURI();
		// String contextPath = request.getContextPath();

		if ("/".equals(uri) || uri.endsWith(".html") || uri.endsWith(".jsp") || uri.contains(".css") || uri.endsWith(".js") || uri.endsWith(".png")
				|| uri.endsWith(".img") || uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".ttf") || uri.endsWith(".ico") || uri.endsWith(".pdf")
				|| uri.endsWith(".properties") || uri.endsWith(".woff")) {
			return false;
		}

		// 不拦截的URL
		for (String p : filterUrls) {
			// p = contextPath + p;
			if (Pattern.matches(p, uri)) {
				log.debug("不需要拦截的url: " + uri);
				return false;
				// needFilter = false;
			}
		}
		
		return true;

	}

	private CODE auth(HttpServletRequest request, JSONObject header) {
		// boolean needFilter = true;

		// if(needFilter){
		String service = header.getString("SERVICE");
		String token = header.getString("TOKEN");
		String reqId = header.getString("REQID");
		String version = header.getString("VERSION");
		String reqTime = header.getString("REQTIME");
		String sign = header.getString("SIGN");
		String uri = request.getRequestURI();
		log.info(uri + ", " + token + ", " + reqTime + ", " + reqId + ", " + version + ", " + sign);
		
		if(StringUtil.isNullOrBlank(service) || !uri.equals(service) || StringUtil.isNullOrBlank(reqId) || StringUtil.isNullOrBlank(version)
				|| StringUtil.isNullOrBlank(reqTime) || StringUtil.isNullOrBlank(sign)){
			return CODE.PARAMETER_ERROR;
		}
		
		// 校验请求是否超时
		String timestamp = null != reqTime ? reqTime : "";
		long now = System.currentTimeMillis();
		try {
			long reqTime1 = Long.parseLong(timestamp);// Timestamp.valueOf(timestamp).getTime();
			long diffTime = Math.abs(now - reqTime1);
			if (diffTime > 1000 * 60 * 6) {// 请求+-6分钟内有效，也就是6分钟内有效
				return CODE.TIMEOUT;
			}
		} catch (NumberFormatException e) {
			log.error("错误", e);
			return CODE.UNKNOW;
		}

		// 校验请求签名是否合法
		// String reqSign = request.getHeader("sign");
		String localSign = MD5Utils.getSign(uri + token + reqTime + reqId + version);
		if (!localSign.equals(sign)) {
			return CODE.SIGN_ERROR;
		}
		// 查看用户是否登录，为什么把这个校验放在最后，因为这个接口需要访问缓存
		if (null == request.getSession().getAttribute("TOKEN")) {
			return CODE.NOT_LOGGED;
		}
		// }
		return CODE.SUCCESS;
	}

	/**
	 * 判断是否是ajax请求
	 * 
	 * @param request
	 * @return
	 */
	private boolean isAjaxRequest(HttpServletRequest request) {
		Enumeration ers = request.getHeaderNames();
		while (ers.hasMoreElements()) {
			String s = (String) ers.nextElement();
			String value = request.getHeader(s);
			log.info(s + ":" + value);
		}

		String requestType = request.getHeader("X-Requested-With");
		if (Constants.XMLHTTP_REQUEST.equals(requestType)) {
			return true;
		} else {
			return false;
		}
	}

	public static int interceptor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int flag = 0;
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		// 不拦截并不需要会话的URL
		for (String p : filterUrls) {
			p = contextPath + p;
			if (Pattern.matches(p, url)) {
				log.debug("不需要拦截的url: " + url);
				return flag = 1;
			}
		}

		HttpSession session = request.getSession(false);
		if (session == null) {
			log.info("会话超时!");
			return flag = 0;
		}

		Map<String, Object> user = (Map<String, Object>) session.getAttribute(Constants.USER_SESSION_KEY);
		if (user == null) {
			log.debug("会话中用户对象为空!");
			return flag = 0;
		} else {
			return 1;
		}
	}

/*	private boolean notEncrypt(HttpServletRequest request) {

		String uri = request.getRequestURI();

		boolean postFix = false;
		if ("/".equals(uri) || uri.endsWith(".html") || uri.endsWith(".jsp") || uri.contains(".css") || uri.endsWith(".js") || uri.endsWith(".png")
				|| uri.endsWith(".img") || uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".ttf") || uri.endsWith(".ico") || uri.endsWith(".pdf")
				|| uri.endsWith(".properties") || uri.endsWith(".woff")) {
			postFix = true;
		}

		boolean noEncrypt = uris.contains(uri);

		// System.out.println(noEncrypt);

		return postFix || noEncrypt;
	}*/

	public void destroy() {
	}

	/** 初始化不过滤的URL */
	private void initfilterUrls(FilterConfig arg0) {
		String temp = arg0.getInitParameter("filterUrl");
		if (temp != null) {
			String[] nfu = temp.split(",");
			for (int i = 0; nfu != null && i < nfu.length; i++) {
				String url = nfu[i].trim();
				filterUrls.add(url);
			}
		}
	}
	
	/** 初始化不登录的URL */
/*	private void initNoAuthUrls(FilterConfig arg0) {
		String temp = arg0.getInitParameter("noAuthUrl");
		if (temp != null) {
			String[] nfu = temp.split(",");
			for (int i = 0; nfu != null && i < nfu.length; i++) {
				String url = nfu[i].trim();
				noAuthUrls.add(url);
			}
		}
	}*/
}
