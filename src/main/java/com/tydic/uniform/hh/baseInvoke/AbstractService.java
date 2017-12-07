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

import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.uniform.hh.util.ConfigUtil;
import com.tydic.uniform.hh.util.PropertiesUtil;
import com.tydic.uniform.hh.util.RedisUtil;
import com.tydic.uniform.hh.util.StringUtil;


/**
 *	//TODO
 *
 * @author Sun Liang {@link sunliang@tydic.com} 
 * @version  PNT Aug 28, 2014 9:43:05 AM 
 * @since 2.0
 **/
@SuppressWarnings("unchecked")
public abstract class AbstractService
{
	private static ThreadLocal<IntefaceType> intefaceTypeLocal=new ThreadLocal<IntefaceType>();
	
	protected HttpDelegate httpClient=new HttpDelegate(false);
	private static final String CACHE_INTERFACE = "CACHE_INTERFACE";
	private static final int DEFAULT_EXP = 60 * 60 * 12;//默认12小时过期
	
	private final static Logger LOGGER = Logger.getLogger(AbstractService.class);
	
	public Object appApiInvoke(Object body,String sId,Class resClass) throws ServiceException
	{
		try 
		{
			RequestBean reqBean=new RequestBean();
			reqBean.init(sId, "json");
			reqBean.setBody(body);
			String bodyJson=JSON.toJSONString(reqBean.getBody());
			reqBean.setLength(bodyJson.length()+"");
			reqBean.initSign(bodyJson);
			reqBean.initToken();
			String reqStr = JSON.toJSONString(reqBean);
			LOGGER.info("requtest str:"+reqStr);
			LOGGER.info("INTERFACE_"+getIntefaceType().toString()+"_URL");
			LOGGER.info(PropertiesUtil.getPropertyValue("INTERFACE_"+getIntefaceType().toString()+"_URL"));
			
			boolean needCache = false;
			String responseStr = null;
			String cacheInterface = ConfigUtil.getPropertyValue(CACHE_INTERFACE);

			if(cacheInterface.indexOf(sId) > -1){
			//if("SC1002401".equals(sId) || "SC1002402".equals(sId) || "SC1002029".equals(sId)){
				//String cacheInterface = ConfigUtil.getPropertyValue(CACHE_INTERFACE);
				if("SC1002029".equals(sId)){//版本查询接口按请求的hash来查询缓存，也就是说不同的参数会缓存不同的结果
					//String os = (String) ((Map)((Map)body).get("param")).get("appOS");
					//responseStr = RedisUtil.get(sId + "_" + os);
					int hashCode = reqStr.hashCode();
					LOGGER.info("requtest hashCode:" + hashCode);
					responseStr = RedisUtil.get(String.valueOf(hashCode));
					
				}else{
					responseStr = RedisUtil.get(sId);
				}
				
				if(StringUtil.isNullOrBlank(responseStr)){
					needCache = true;
					responseStr = httpClient.executePost(PropertiesUtil.getPropertyValue("INTERFACE_"+getIntefaceType().toString()+"_URL"), "application/json", reqStr);
				} else {
					LOGGER.info(sId + " 命中缓存************");
				}
								
			}else{
				responseStr = httpClient.executePost(PropertiesUtil.getPropertyValue("INTERFACE_"+getIntefaceType().toString()+"_URL"), "application/json", reqStr);
			}
			
			LOGGER.info("response str:"+responseStr);
			Object responseObj=JSON.parseObject(responseStr, resClass);
			if(((Header)responseObj).getRc().equals(Header.RC_SUCCESS))
			{
				if(needCache){
					int exp = DEFAULT_EXP;
					String cacheExp = ConfigUtil.getPropertyValue(sId + "_EXP");
					try{
						 exp = Integer.valueOf(cacheExp);
					}catch (Exception e){
						exp = DEFAULT_EXP;
					}
					
					if("SC1002029".equals(sId)){//版本查询接口按请求的hash来缓存接口，也就是说不同的参数会缓存不同的结果
						//String os = (String) ((Map)((Map)body).get("param")).get("appOS");
						//RedisUtil.set(sId + "_" + os, responseStr, exp);
						int hashCode = reqStr.hashCode();
						RedisUtil.set(String.valueOf(hashCode) , responseStr, exp);
					}else{
						RedisUtil.set(sId, responseStr, exp);
					}
				}
				return responseObj;
			}
			else 
			{
				throw new RuntimeException(((Header)responseObj).getDs());
			}
		} catch (ServiceException e) 
		{
			LOGGER.error(e.getMessage(),e);
			throw e;
		}catch (RuntimeException e) 
		{
			LOGGER.error(e.getMessage(),e);
			throw new ServiceException("接口服务异常",e);
		}catch (Exception e) 
		{
			LOGGER.error("调用服务异常",e);
			throw new ServiceException("调用服务异常",e);
		}
		
	}

	
	protected Map getPageMap(Map map)
	{
		Map pageMap=new HashMap();
		pageMap.put("index", ObjectUtil.isEmpty(map.get(ConstantUtils.PAGE_NO))?0:Integer.valueOf(map.get(ConstantUtils.PAGE_NO).toString())-1);
		pageMap.put("pageSize", ObjectUtil.isEmpty(map.get(ConstantUtils.PAGE_SIZE))?10:Integer.valueOf(map.get(ConstantUtils.PAGE_SIZE).toString()));
		if(ObjectUtil.isNotEmpty(map.get(ConstantUtils.PAGE_NO)))
		{
			map.remove(ConstantUtils.PAGE_NO);
		}
		if(ObjectUtil.isNotEmpty(map.get(ConstantUtils.PAGE_SIZE)))
		{
			map.remove(ConstantUtils.PAGE_SIZE);
		}
		return pageMap;
	}
	protected void initTotal(Map req,Map pagination)
	{
		Map pageMap=new HashMap();
		if(ObjectUtil.isEmpty(pagination.get("total")))
		{
			pagination.put("total", 0);
		}
		if(ObjectUtil.isEmpty(pagination.get("totalPage")))
		{
			pagination.put("totalPage", 0);
		}
		pageMap.put(ConstantUtils.TOTAL,pagination.get("total") );
		pageMap.put(ConstantUtils.TOTAL_PAGE,pagination.get("totalPage") );
		pageMap.put(ConstantUtils.PAGE_NO,(Integer)pagination.get("index")+1);
		req.put("pagination", pageMap);
	}
	protected void initTotal(Map req,Integer total)
	{
		Map pageMap=new HashMap();
		Integer pageNo=ObjectUtil.isEmpty(req.get(ConstantUtils.PAGE_NO))?1:Integer.valueOf(req.get(ConstantUtils.PAGE_NO).toString());
		Integer pageSize=ObjectUtil.isEmpty(req.get(ConstantUtils.PAGE_SIZE))?10:Integer.valueOf(req.get(ConstantUtils.PAGE_SIZE).toString());
		if(ObjectUtil.isEmpty(total))
		{
			total=0;
		}
		Integer totalPage=total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		pageMap.put(ConstantUtils.TOTAL,total);
		pageMap.put(ConstantUtils.TOTAL_PAGE,totalPage);
		pageMap.put(ConstantUtils.PAGE_NO,pageNo);
		req.put("pagination", pageMap);
	}
	
	
	protected List<String> getListValue(Map map,String key)
	{
		List<String> list=new ArrayList<String>();
		if(!map.containsKey(key) || map.get(key)==null)
		{
			return null;
		}
		if(map.get(key) instanceof List)
		{
			for (String tmp : ((List<String>)map.get(key)))
			{
				list.add(tmp);
			}
		}
		else
		{
			list.add(map.get(key).toString());
		}
		return list;
	}
	
	public static IntefaceType getIntefaceType()
	{
		if(intefaceTypeLocal.get()==null)
		{
			intefaceTypeLocal.set(IntefaceType.ESHOP);
		}
		return intefaceTypeLocal.get();
	}
	/**
	 * 服务类型
	 * @param type  void
	 */
	public static void setIntefaceType(IntefaceType type)
	{
		intefaceTypeLocal.set(type);
	}
	
}
