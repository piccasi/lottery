package com.tydic.uniform.hh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;



public class PropertiesUtil{
	private static Properties properties = new Properties();
	private static PropertiesMapping propertiesMapping = new PropertiesMapping();
	private static String propertyName = "appWebPlatformConfig_test.properties";
	public final static String FILE_SEP = System.getProperty("file.separator");//文件分隔符（在 UNIX 系统中是“/”）
	private final static Logger LOGGER = Logger.getLogger(PropertiesUtil.class);
	public synchronized static Properties getProperties(){
		return System.getProperties();
	}
	/**
	 * 得到一个配置参数
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		return getProperties().getProperty(key);
	}
	/**
	 * 得到一个配置参数
	 * @param key
	 * @return
	 */
	public static int getIntProperty(String key){
		return Integer.valueOf(getProperties().getProperty(key));
	}
	/**
	 * 得到一个配置参数
	 * @param key
	 * @param defaultValue 
	 * @return
	 */
	public static String getProperty(String key,String defaultValue){
		return getProperties().getProperty(key,defaultValue);
	}
	
	/**
	 * 添加或更新一个配置参数
	 * @param key
	 * @param value
	 */
	public static void putProperty(String key , String value){
		getProperties().put(key, value);
	}
	
	public static String getServerPath(){//获取服务的路径
		String path = null;
		File file = new File(".");
		try {
			path = file.getCanonicalPath()+FILE_SEP;
		} catch (IOException e) {
			LOGGER.error("Get Server Path error:" + e.toString());
		}
		//LOGGER.info("Get Server Path *********** " + path);
		return path;
	}
	
	public static String getPropertyPath(String fileName){
		return getServerPath()+fileName;
	}
	public static String getPropertyPath(){
		//String path = System.getProperty("user.dir")+FILE_SEP+"config"+FILE_SEP+propertyName;
		//return propertyName;
		String path = System.getProperty("user.dir") + FILE_SEP + propertyName;
		LOGGER.info("properties path: " + path);
		return path;
	}
	
	public static String getPropertyValue(String key){
		String value = null;
		//PropertiesMapping propertiesInstance = PropertiesMapping.getInstance();
		try{
			//if(propertiesInstance.isEmpty()){
			if(propertiesMapping.isEmpty()){
				//InputStream in =  PropertiesUtil.class.getClassLoader().getResourceAsStream(getPropertyPath());//包内配置文件
//				String path = System.getProperty("user.dir")+FILE_SEP+propertyName;//包外配置文件，在bin目录和主目录，貌似两个位置都需要更新
				//InputStreamReader in = new BufferedInputStream(new FileInputStream(getPropertyPath()));
				InputStreamReader in = new InputStreamReader(new FileInputStream(getPropertyPath()), "UTF-8");//支持中文配置
				properties.load(in);
				Iterator iterator =  properties.entrySet().iterator();
				while (iterator.hasNext()) { 
				    Map.Entry entry = (Map.Entry) iterator.next();
				    Object key1 = entry.getKey(); 
				    Object val = entry.getValue();
				    propertiesMapping.put(String.valueOf(key1), String.valueOf(val));
				}
				if(propertiesMapping.containsKey(key)){
					value = propertiesMapping.get(key);
				}else{
					value = properties.getProperty(key);
				}
			}else{
				value = propertiesMapping.get(key);
			}
		}catch (FileNotFoundException e) {
			LOGGER.error("properties file not found:" + e.toString());
		}catch (IOException e) {
			LOGGER.error("Load properties file error:" + e.toString());
		}catch(Exception e){
			LOGGER.error("Load properties file error:" + e.toString());
		}
		return value;
	}
	
}
