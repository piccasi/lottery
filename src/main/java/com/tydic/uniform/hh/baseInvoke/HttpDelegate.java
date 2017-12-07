/*
 * Copyright  2013，the original authors or Tianyuan DIC Computer Co., Ltd.
 *
 * Please see the tydic success stories and it solutions 
 *
 *      http://www.tydic.com/Default.aspx
 *
 * Statement: This document's code after sufficiently has not permitted does not have 
 * any way dissemination and the change, once discovered violates the stipulation, will
 * receive the criminal sanction.
 * Address: 3/F,T3 Building, South 7th Road, South Area, Hi-tech Industrial park, Shenzhen, P.R.C.
 * Email: webmaster@tydic.com　
 * Tel: +86 755 26745688 
 */
package com.tydic.uniform.hh.baseInvoke;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.uniform.hh.util.PropertiesUtil;

public class HttpDelegate
{
	private int timeOut=30000;
	private CloseableHttpClient httpClient;
	private RequestConfig requestConfig;
	
	public HttpDelegate()
	{
		httpClient=this.getHttpClient(false);
		this.initRequestConfig();
	}
	public HttpDelegate(Boolean isHttps)
	{
		httpClient=this.getHttpClient(isHttps);
		this.initRequestConfig();
	}
	
	private String execute(HttpUriRequest method,CloseableHttpClient httpClient) throws RuntimeException, IOException
	{
		CloseableHttpResponse response = null;
		try
		{
			response = httpClient.execute(method);
			HttpEntity entity = response.getEntity();
			return EntityUtils.toString(entity, method.getFirstHeader(HttpHeaders.CONTENT_ENCODING).getValue());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			if (response != null)
			{
				response.close();
			}
		}
	}

	public String executePost(String url,String contentType,String param)
	{
		return this.executePost(url, contentType,"UTF-8",param);
	}
	public String executePost(String url,String contentType,String encoding,String param)
	{
		HttpPost httpPost = new HttpPost(url);
		try
		{
			httpPost.setConfig(requestConfig);
			this.initMethodParam(httpPost, contentType, encoding);
			if(param!=null)
			{
				httpPost.setEntity(new StringEntity(param, encoding));
			}
			return this.execute(httpPost,this.getHttpClient());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			httpPost.releaseConnection();
		}
	}
	public String executePost(String url,String param)
	{
		return this.executePost(url, null,"UTF-8",param);
	}

	public String executeGet(String url,String contentType)
	{
		return this.executeGet(url, "UTF-8", contentType);
	}
	public String executeGet(String url,String encoding,String contentType)
	{
		HttpGet httpGet = new HttpGet(url);
		try
		{
			httpGet.setConfig(requestConfig);
			this.initMethodParam(httpGet, contentType, encoding);
			return this.execute(httpGet,this.getHttpClient());
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		finally
		{
			httpGet.releaseConnection();
		}
	}
	public String executeGet(String url)
	{
		return this.executeGet(url, "UTF-8", null);
	}
	
	private void initMethodParam(HttpUriRequest method,String contentType,String encoding)
	{
		if (contentType != null)
		{
			method.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
		}
		method.setHeader(HttpHeaders.CONTENT_ENCODING, encoding);
		method.setHeader(HttpHeaders.TIMEOUT, PropertiesUtil.getProperty("HTTP_TIME_OUT"));
	}

	private CloseableHttpClient getHttpClient(Boolean isHttps)
	{
		CloseableHttpClient httpClient = null;
		try
		{
			if (isHttps)
			{

				TrustManager[] trustManagers = new TrustManager[1];
				trustManagers[0] = new X509TrustManager()
				{
					@Override
					public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
					{
						// TODO Auto-generated method stub
					}

					@Override
					public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException
					{
						// TODO Auto-generated method stub
					}

					@Override
					public X509Certificate[] getAcceptedIssuers()
					{
						// TODO Auto-generated method stub
						return null;
					}
					
				};
				SSLContext sslContext = SSLContext.getInstance("TLS");
				sslContext.init(new KeyManager[0], trustManagers, new SecureRandom());
				SSLContext.setDefault(sslContext);
				sslContext.init(null, trustManagers, null);
				SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			}
			else
			{
				httpClient=HttpClients.custom().build();
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		return httpClient;
	}

	public static void main(String[] args)
	{
		HttpDelegate delegate = new HttpDelegate();
		String response = delegate.executeGet("http://api.map.baidu.com/location/ip?ak=784b0730a0021dfd79b788ead7621ce0&ip=202.198.16.3");
		JSONObject json = JSON.parseObject(response);
		if (json.getIntValue("status") == 0)
		{
			JSONObject addJson = json.getJSONObject("content").getJSONObject("address_detail");
			System.out.println(addJson.getString("province"));
			System.out.println(addJson.getString("city"));
			System.out.println(addJson.getString("city_code"));
		}
	}

	public CloseableHttpClient getHttpClient()
	{
		return httpClient;
	}
	
	
	public void destroy() 
	{
		try
		{
			httpClient.close();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	public void setTimeOut(int timeOut)
	{
		this.timeOut = timeOut;
		this.initRequestConfig();
	}
	
	
	private void initRequestConfig()
	{
		requestConfig=RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
	}

}
