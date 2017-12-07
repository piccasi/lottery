/*
 * Copyright  2014，the original authors or Tianyuan DIC Computer Co., Ltd.
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

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.tydic.uniform.hh.util.DateUtil;


/**
 *	//TODO
 *
 * @author Sun Liang {@link sunliang@tydic.com} 
 * @version  PNT Aug 28, 2014 11:12:29 AM 
 * @since 2.0
 **/
public class Body extends Header
{
	/**
	 * 
	 * @param sId -服务编码
	 * @param format -协议格式
	 * @return  void
	 */
	public void init(String sId,String format)
	{
		this.setSid(sId);
		this.setFormat(format);
		this.setReqTime(DateUtil.formatDate(new Date(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
		this.setMid(UUID.randomUUID().toString());
		this.setSysId("1003");
	}
	
	public void initToken() throws ServiceException
	{
		try 
		{
			String src=System.getProperty("INTERFACE_SYSID")
			+"@"
			+System.getProperty("INTERFACE_PWD");

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			this.setToken(toHexString(md.digest()).toUpperCase());
		} catch (Exception e) {
			throw new ServiceException("生成SIGN异常",e);
		}
		
	}
	
	public void initSign(String bodyJson) throws ServiceException
	{
		try 
		{
			StringBuilder src=new StringBuilder();
			src.append(bodyJson);
			src.append("@");
			src.append(System.getProperty("INTERFACE_PWD"));

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src.toString().getBytes());
			this.setSign(toHexString(md.digest()).toUpperCase());

		} catch (Exception e) {
			throw new ServiceException("生成TOKEN异常",e);
		}
		
	}
	
	
	private static String toHexString(byte[] buffer) {
		StringBuffer sb = new StringBuffer();
		String s = null;

		for (int i = 0; i < buffer.length; ++i) {
			s = Integer.toHexString(buffer[i] & 0xFF);
			if (s.length() < 2) {
				sb.append('0');
			}
			sb.append(s);
		}

		return sb.toString();
	}
}
