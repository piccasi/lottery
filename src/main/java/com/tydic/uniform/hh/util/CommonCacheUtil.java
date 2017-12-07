package com.tydic.uniform.hh.util;

import com.tydic.uniform.hh.baseInvoke.ResponseBean;

public class CommonCacheUtil {
	private static final String PREFIX = "HNA_AGENT_APP_";
	private static final String MANUAL = "MANUAL";
	private static final String MENU = "MENU";
	private static final int EXPTIME = 24 * 60 * 60;
	
	
	private  static void cacheThis(String key, Object o) {
		RedisUtil.setByByte(key.getBytes(), SerializeUtil.serialize(o), EXPTIME);
	}

	public  static void cacheManual(Object o){
		cacheThis(PREFIX + MANUAL , o);
	}
	
	public  static void cacheMenu(Object o){
		cacheThis(PREFIX + MENU, o);
	}
	
	public  static ResponseBean getCacheManual(){
		return getCache(PREFIX + MANUAL);
	}
	
	public  static ResponseBean getCacheMenu(){
		return getCache(PREFIX + MENU);
	}
	
	private  static ResponseBean getCache(String key) {
		byte[] bb = RedisUtil.getByByte(key.getBytes());
		ResponseBean bean = (ResponseBean) SerializeUtil.unserialize(bb);
				
		return bean;
	}
}
