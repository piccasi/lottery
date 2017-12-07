package com.tydic.uniform.hh.util;

import java.util.HashMap;
import java.util.Map;

public class PropertiesMapping {

	//private static PropertiesMapping instance;
	
	private Map<String, String> propertyMap;
	
	public PropertiesMapping() {
		propertyMap = new HashMap<String, String>();
	}
	
	/*public static PropertiesMapping getInstance(){
		if(null == instance){
			instance = new PropertiesMapping();
		}
		return instance;
	}*/
	
	public void put(String key, String value){
		propertyMap.put(key, value);
	}
	
	public String get(String key){
		return propertyMap.get(key);
	}
	
	public boolean isEmpty(){
		return propertyMap.isEmpty();
	}
	
	public boolean containsKey(String key){
		return propertyMap.containsKey(key);
	}
	
}
