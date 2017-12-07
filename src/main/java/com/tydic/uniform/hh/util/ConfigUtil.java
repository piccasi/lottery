package com.tydic.uniform.hh.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ConfigUtil {
	private final static Logger LOGGER = Logger.getLogger(ConfigUtil.class);

	private static Properties properties = new Properties();
	private static PropertiesMapping propertiesMapping = new PropertiesMapping();
	private static String propertyName = "autoRefresh.properties";
	public final static String FILE_SEP = System.getProperty("file.separator");//文件分隔符（在 UNIX 系统中是“/”）
	private final static int refTime = 60 * 1000;
	private static boolean initial = false;
	
    private ConfigUtil()
    {
    }
    
    public static void init(String path, long refTime) throws IOException {
            loadConfig();
            initial = true;
            Thread configWatchdog = new Thread(new RefreshThread(null), "ConfigWatchdog");
            configWatchdog.setDaemon(Boolean.TRUE.booleanValue());
            configWatchdog.start();
    }
    
	public static synchronized void loadConfig() {
		LOGGER.info("loadConfig start...");
		try{
				
			FileInputStream fis = new FileInputStream(getPropertyPath());   
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			  
			properties.load(isr);
			Iterator iterator =  properties.entrySet().iterator();
			while (iterator.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iterator.next();
			    Object key1 = entry.getKey(); 
			    Object val = entry.getValue();
			    propertiesMapping.put(String.valueOf(key1), String.valueOf(val));
			}
				
		}catch (FileNotFoundException e) {
			LOGGER.error("properties file not found:" + e.toString());
		}catch (IOException e) {
			LOGGER.error("Load properties file error:" + e.toString());
		}catch(Exception e){
			LOGGER.error("Load properties file error:" + e.toString());
		}
		
		LOGGER.info("loadConfig end");
		
	}

	public static String getPropertyPath(){
		String path = System.getProperty("user.dir") + FILE_SEP + propertyName;
		LOGGER.info("properties path: " + path);
		return path;
	}

	public static String getPropertyValue(String key){
		//System.out.println("key: " + key);
		String value = null;
		
		if(!initial){
			try {
				init(null, 0);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if(propertiesMapping.containsKey(key)){
			value = propertiesMapping.get(key);
		}else{
			value = properties.getProperty(key);
		}

		//System.out.println("value: " + value);
		return value;
	}


	private static class RefreshThread implements Runnable {

	    public void run()
	    {
	        do
	            try
	            {
	                ConfigUtil.loadConfig();
	                Thread.sleep(refTime);
	            }
	            catch(InterruptedException e)
	            {
	            	LOGGER.error("RefreshThread sleep error!", e);
	            }
	        while(true);
	    }
	
	    private RefreshThread()
	    {
	    }
	
	    RefreshThread(RefreshThread refreshthread)
	    {
	        this();
	    }
	}




}
