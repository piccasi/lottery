package com.tydic.uniform.hh.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
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

import org.apache.log4j.Logger;

import com.tydic.uniform.common.constant.Constants;
import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.constant.HhConstants;
import com.tydic.uniform.hh.util.LoginUtil;
import com.tydic.uniform.hh.util.MD5Utils;
import com.tydic.uniform.hh.util.StringUtil;
import com.tydic.uniform.hh.util.TokenUtil;


public class SecurityFilter implements Filter {

	private static Logger log = Logger.getLogger(SecurityFilter.class);
	
    private static final List<String> filterUrls = new ArrayList<String>();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        initfilterUrls(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) (arg0));
        HttpServletResponse response = ((HttpServletResponse) arg1);
        //boolean isAjax = isAjaxRequest(request); // ajax请求标识

        request.setCharacterEncoding("utf-8");     
        response.setContentType("text/html;charset=utf-8");
        //response.setHeader("token", "piccasi");
        //String contextPath = request.getContextPath();
        //log.debug("请求路径：" + contextPath);
        
        /** 权限拦截 */

        
        int flag = isValidateRequest(request);
        //int flag = 0;
        
        if (flag == -1) {//用户未登录
        	//if(isAjax){
	            String ret = JsonResponse.toErrorResult(CODE.NOT_LOGGED, null);	            
	            PrintWriter writer = response.getWriter();
	            writer.print(ret);
	            writer.close();
	            log.info("用户未登录:" + request.getRequestURI() + ", 返回报文: " + DesEncryptUtil.decrypt(ret));
        	/*}else{
        		String repURL = contextPath + "/hhagent/html/login.html";
                response.sendRedirect(repURL);
        	}*/
            return;
        } else if (flag == -2) {//请求超时
            String ret = JsonResponse.toErrorResult(CODE.TIMEOUT, null);
            response.getWriter().print(ret);
            log.info("请求超时:" + request.getRequestURI() + ", 返回报文: " + DesEncryptUtil.decrypt(ret));
            return;
        } else if (flag == -3) {//请求非法
        	String ret = JsonResponse.toErrorResult(CODE.SIGN_ERROR, null);
            response.getWriter().print(ret);
            log.info("请求非法:" + request.getRequestURI() + ", 返回报文: " + DesEncryptUtil.decrypt(ret));
            return;
        }
        
        arg2.doFilter(arg0, arg1);
    }
    
    private int isValidateRequest(HttpServletRequest request) {
    	
        String uri = request.getRequestURI();
        //String contextPath = request.getContextPath();
        
        if(uri.endsWith(".html") || uri.endsWith(".jsp") || uri.contains(".css") || uri.endsWith(".js") || uri.endsWith(".png") 
        		|| uri.endsWith(".img") || uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".ttf")
        		|| uri.endsWith(".ico") || uri.endsWith(".pdf") || uri.endsWith(".properties") || uri.endsWith(".woff") ){
        	return 0;
        }
        
        boolean needFilter = true;
        // 不拦截并不需要登录的URL
        for (String p : filterUrls) {
            //p = contextPath + p;
            if (Pattern.matches(p, uri)) {
                log.debug("不需要拦截的url: " + uri);
                //return 0;
                needFilter = false;
            }
        }
        
        if(needFilter){
        	String token = TokenUtil.getTokenFromRequest(request);
        	log.info(request.getRequestURI() + ", " + token + ", " + request.getHeader("timestamp") + ", " + request.getHeader("sign"));
	        //校验请求是否超时
	        String timestamp = null != request.getHeader("timestamp") ? request.getHeader("timestamp") : "";
	        long now = System.currentTimeMillis();
	        try {
	        	long  reqTime = Long.parseLong(timestamp);//Timestamp.valueOf(timestamp).getTime();
	        	long diffTime = Math.abs(now - reqTime);
	        	if(diffTime > 1000 * 60 * 3){//请求+-3分钟内有效，也就是6分钟内有效
	        		return -2;
	        	}
	        } catch (NumberFormatException e){
	        	return -2;
	        }
	        
	        //校验请求签名是否合法
	        String reqSign = request.getHeader("sign");
	        String localSign = MD5Utils.getSign(uri + token + timestamp);
	        if(!localSign.equals(reqSign)){
	        	return -3;
	        }
	        
	        //查看用户是否登录，为什么把这个校验放在最后，因为这个接口需要访问缓存
	        if(StringUtil.isNullOrBlank(token) || !LoginUtil.isLogin(token)){
	        	return -1;
	        }
        }
        
        return 0;
		
	}

	public void doFilter_bak(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) (arg0));
        HttpServletResponse response = ((HttpServletResponse) arg1);
        boolean isAjax = isAjaxRequest(request); // ajax请求标识

        request.setCharacterEncoding("utf-8");     
        response.setContentType("text/html;charset=utf-8");
        //response.setHeader("token", "piccasi");
        String contextPath = request.getContextPath();
        log.debug("请求路径：" + contextPath);
        /** 权限拦截 */
        //int flag = interceptor(request, response);
        int flag = -1;
        if (flag == -1) {
            log.debug("不需鉴权! IP:" + request.getRemoteAddr());
            arg2.doFilter(arg0, arg1);
            return;
        } else if (flag == 0) {

            log.debug("session超时! IP:" + request.getRemoteAddr());

            if (isAjax) {//会话超时
                Map<String ,Object> resultMap = new HashMap<String ,Object>();
                resultMap.put(HhConstants.CODE, HhConstants.LOGON_ERROR_CODE);// 失败编码
                resultMap.put(HhConstants.MESSAGE, HhConstants.LOGON_ERROR_MESSAGE);// 失败编码
                response.getWriter().print( DesEncryptUtil.encrypt(resultMap));
                return;
                //
            } else {
            	String requestURL = request.getServletPath();
            	if (requestURL.indexOf("hhagent")!=-1) { //如果包含 hhagent
            		 String repURL = contextPath + "/hhagent/html/login.html";
                     response.sendRedirect(repURL);
				}else {   //如果不包含
					 String repURL = contextPath + "/hh/html/login.html";
		             response.sendRedirect(repURL);
				}
            }
            return;
        } else if (flag == 1) {
            log.debug("通过正常拦截! IP:" + request.getRemoteAddr());
            arg2.doFilter(arg0, arg1);
            return;
        } else if (flag == 2) {
            log.debug("通过方法拦截! IP:" + request.getRemoteAddr());
            arg2.doFilter(arg0, arg1);
            return;
        }
    }


    /**
     * 判断是否是ajax请求
     * @param request
     * @return
     */
    private boolean isAjaxRequest(HttpServletRequest request){
    	Enumeration ers = request.getHeaderNames();
        while ( ers.hasMoreElements()) {
        	   String s = (String) ers.nextElement();
        	   String value = request.getHeader(s);
        	   log.info(s + ":" + value);
              }
    	
        String requestType = request.getHeader("X-Requested-With");
        if(Constants.XMLHTTP_REQUEST.equals(requestType)){
            return true;
        }else{
            return false;
        }
    }

    public static int interceptor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
        }else{
            return 1;
        }
    }


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
}
