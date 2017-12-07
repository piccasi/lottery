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

import com.alibaba.fastjson.annotation.JSONField;

/**
 *	//TODO
 *
 * @author Sun Liang {@link sunliang@tydic.com} 
 * @version  PNT Aug 28, 2014 11:28:47 AM 
 * @since 2.0
 **/
public class RequestBean extends Body
{
	@JSONField(name="BODY")
	private Object body;

	public Object getBody()
	{
		return body;
	}

	public void setBody(Object body)
	{
		this.body = body;
	}
	
	
}
