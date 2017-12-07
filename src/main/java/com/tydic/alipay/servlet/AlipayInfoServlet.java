package com.tydic.alipay.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.tydic.uniform.hh.util.PropertiesUtil;

public class AlipayInfoServlet extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map result = new HashMap();
		try{
			request.setCharacterEncoding("UTF-8");
			Map infoMap = new HashMap();
			String alipay_gateway_new = PropertiesUtil.getPropertyValue("ALIPAY_GATEWAY_NEW");
			String notify_url = PropertiesUtil.getPropertyValue("notify_url");
			String call_back_url = PropertiesUtil.getPropertyValue("call_back_url");
			String merchant_url = PropertiesUtil.getPropertyValue("merchant_url");
			String partner = PropertiesUtil.getPropertyValue("partner");
			String seller_email = PropertiesUtil.getPropertyValue("seller_email");
//			String key = PropertiesUtil.getPropertyValue("key");
			String private_key = PropertiesUtil.getPropertyValue("private_key");
//			String ali_public_key = PropertiesUtil.getPropertyValue("ali_public_key");
			
			infoMap.put("alipay_gateway_new", alipay_gateway_new);
			infoMap.put("notify_url", notify_url);
			infoMap.put("call_back_url", call_back_url);
			infoMap.put("merchant_url", merchant_url);
			infoMap.put("partner", partner);
			infoMap.put("seller_email", seller_email);
//			infoMap.put("key", key);
			infoMap.put("private_key", private_key);
//			infoMap.put("ali_public_key", ali_public_key);
			result.put("result", "0");
			result.put("msg", "获取签约商户信息正常");
			result.put("data", infoMap);
			System.out.println(JSONObject.toJSONString(result));
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONObject.toJSONString(result));
			response.getWriter().close();
		}catch(Exception e){
			result.put("result", "1");
			result.put("msg", "获取签约商户信息失败");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(JSONObject.toJSONString(result));
			response.getWriter().close();
		}
		
		
	}

}
