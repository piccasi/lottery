/*
 * 文 件 名:  HttpClientUtil.java
 * 版    权:  Tydic Technologies Co., Ltd. Copyright 1993-2012,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  panmin
 * 修改时间:  2013-7-25
 * 跟踪单号:  <需求跟踪单号>
 * 修改单号:  <需求修改单号>
 * 修改内容:  <修改内容>
 */
package com.lottery.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * HttpClient工具类
 * 
 * @author panmin
 * @version [版本号, 2013-7-25]
 * @since [产品/模块版本]
 */
public class HttpClientUtil {

	private static Logger logger = Logger.getLogger(HttpClientUtil.class);
	   private static String encoding = "UTF-8";
	    static DefaultHttpClient client = null;
	    static {
	        ThreadSafeClientConnManager tc = new ThreadSafeClientConnManager();
	        tc.setMaxTotal(100);
	        tc.setDefaultMaxPerRoute(tc.getMaxTotal());
	        client = new DefaultHttpClient(tc);
	        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,20000);
	        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
	    }

	public static String getRequest(String uri) throws Exception {
		HttpMethod method = null;
		try {
			HttpClient client = HttpClientFactory.getHttpClient();
			method = new GetMethod(uri); // 使用GET方法
			client.executeMethod(method); // 请求资源
			int status = method.getStatusCode();
			logger.info("响应状态码" + status);
			if (200 == status) {
				String ret = method.getResponseBodyAsString().trim();
				/*logger.info("响应报文：" + ret);*/
				return ret;
			} else {
				throw new Exception("请求失败，响应状态码" + status);
			}
		} catch (Exception e) {
			logger.error("error", e);
			return null;
		} finally {
			if (method != null)
				method.releaseConnection(); // 释放连接
		}
	}
	
	public static String getPostRequest(String uri, String bodyContent) throws Exception {
		PostMethod post = null;
		HttpClient client;
		try {			
			client = HttpClientFactory.getHttpClient();
			post = new PostMethod(uri); // 使用GET方法
			post.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			post.setRequestBody(bodyContent);
			logger.info("请求报文：" + bodyContent);
			client.executeMethod(post); // 请求资源
			int status = post.getStatusCode();
			logger.info("响应状态码" + status);
			if (200 == status||302==status) {
				String ret = post.getResponseBodyAsString().trim();
				/*logger.info("响应报文：" + ret);*/
				return ret;
			}
			else {
				throw new Exception("请求失败，响应状态码" + status);
			}
		}  finally {
			if (post != null){
				post.releaseConnection(); // 释放连接
				client = null;
			}	
		}
	}
	
	public static String postParams(String url, Map<String, Object> data) throws Exception {
        HttpPost post = new HttpPost(url);
        List <NameValuePair> params = new ArrayList<NameValuePair>();  
        if(data != null){
            Set<Map.Entry<String, Object>> set = data.entrySet();
            for(Map.Entry<String,Object> entry:set){
                if(entry.getValue() instanceof String){
                	params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }
        }
        String res = "";
        try {
        	post.setEntity(new UrlEncodedFormEntity(params,encoding));  
            HttpResponse response = client.execute(post);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                res = EntityUtils.toString(httpEntity, encoding);
                //打印响应内容
                logger.info("response:" + res);
                EntityUtils.consume(httpEntity);
            }
		} finally{
			 post.abort();
		}
        
        return res;
    }
	
	
	/**
	 * HttpClient发送请求
	 * 
	 * @param url
	 *            请求url
	 * @param bodyContent
	 *            消息体
	 * @return 服务器返回内容
	 */
	@SuppressWarnings("deprecation")
	public static String httpSendMsg(String url, String bodyContent)
			throws Exception {
		PostMethod post = null;
		HttpClient client = null;
		try {
			client = HttpClientFactory.getHttpClient();
			client.setConnectionTimeout(20 * 1000);
			post = new PostMethod(url);
			logger.info("httpSendMsg url==" + url);
			post.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			post.setRequestBody(bodyContent);
			long start = System.currentTimeMillis();
			
/*			boolean ssoFlag = url.contains("http://yht.hna170.com/api/gateway/v1");//调用一号通接口打印报文标识
			boolean isLogin = false;
			JSONObject object = JSONObject.fromObject(bodyContent);
			if(object.get("SID")!=null){
				String SID = object.get("SID").toString();
				if(!"SC1002101".equals(SID)&&!"SC1002002".equals(SID)&&!"SC1002102".equals(SID)){
					isLogin = true;
				}
			}
			
			if(ssoFlag){
				logger.info("一号通加密后请求报文：" + bodyContent);	
			}
			
			if(isLogin){
				logger.info("请求报文：" + bodyContent);				
			}*/
			client.executeMethod(post);
			InputStream inputStream = post.getResponseBodyAsStream(); 
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream)); 
			StringBuffer stringBuffer = new StringBuffer();  
			String str = "";  
			while((str = br.readLine()) != null){  
				stringBuffer.append(str );  
			} 
			
			//byte ssBytes = Byte.parseByte(str);
			//new String(post.getResponseBodyAsStream(), "UTF-8");
			String result = new String(stringBuffer.toString().getBytes(), "UTF-8");

/*			if(isLogin){			
				if (logger.isInfoEnabled()) {
					logger.info("响应的编码是：" + post.getResponseCharSet());
					long end = System.currentTimeMillis();
					logger.info("能力平台接口响应时间:" + (end - start) / 1000 + "秒");
					logger.info("响应结果:" + result);
				}
			}
			
			if(ssoFlag){
				if (logger.isInfoEnabled()) {
					logger.info("一号通报文响应：");
					logger.info("响应的编码是：" + post.getResponseCharSet());
					long end = System.currentTimeMillis();
					logger.info("能力平台接口响应时间:" + (end - start) / 1000 + "秒");
					logger.info("响应结果:" + result);
				}
			}*/
			
			return result;
		} catch(Exception e){
			throw e;
		} finally {
			if (post != null) {
				post.releaseConnection();
				client = null;
			}
		}
	}
	
	public static void main(String[] args) throws Exception{
		String url = "http://www.robot-web.com/wkapi.php";
		String content = "words=我最后一次月经是在6月8号，8我最后一次月经是在6月8号，8月4号做的b超显示有孕囊，有胚胎，心管博动可见！8月28号再做b超却说没有胚胎，具体看b超单，这是为什么？连胚胎都看不到?&format=0";
		httpSendMsg(url, content);
	}
}
