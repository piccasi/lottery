package com.tydic.uniform.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class EncryptFilter implements Filter{
	private static Logger logger = Logger.getLogger(EncryptFilter.class);
	
	private List<String> uris;
	public void init(FilterConfig filterConfig) throws ServletException {
		uris = new ArrayList<String>();

		/*uris.add(".ico");
		uris.add(".css");
		uris.add(".css.");
		uris.add(".css.map");
		uris.add(".js");
		uris.add(".png");
		uris.add(".gif");
		uris.add(".jpg");
		uris.add(".jpeg");
		uris.add(".ttf");
		uris.add(".jsp");
		uris.add(".html");*/
		uris.add("/portal/login");
		uris.add("/health/search");
		uris.add("/health/analysis");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
			if(!notEncrypt((HttpServletRequest)request)){
				HttpServletRequestWrapper requestW = new HttpServletRequestWrapper((HttpServletRequest)request);
				chain.doFilter(requestW, response);
			}else{
				chain.doFilter(request, response);
			}
		
		
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean notEncrypt(HttpServletRequest request){
		//String uri = request.getRequestURI();
		/*String method = request.getMethod();
        Enumeration ers = request.getHeaderNames();
        while ( ers.hasMoreElements()) {
        	   String s = (String) ers.nextElement();
        	   String value = request.getHeader(s);
        	   log.info(s + ":" + value);
              }*/
		
		String uri = request.getRequestURI();

		
		boolean postFix = false;
        if("/".equals(uri) || uri.endsWith(".html") || uri.endsWith(".jsp") || uri.contains(".css") || uri.endsWith(".js") || uri.endsWith(".png") 
        		|| uri.endsWith(".img") || uri.endsWith(".jpg") || uri.endsWith(".gif") || uri.endsWith(".ttf")
        		|| uri.endsWith(".ico") || uri.endsWith(".pdf") || uri.endsWith(".properties") || uri.endsWith(".woff") ){
        	postFix = true;
        }
        
        boolean noEncrypt = uri.contains(uri);
		
		//System.out.println(noEncrypt);
		
		return postFix || noEncrypt;		
	}

}
