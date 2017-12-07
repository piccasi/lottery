package com.tydic.alipay.service;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;

/**
 * <p></p>
 * @author ghp 2015年10月8日 下午1:14:12
 * @ClassName SaveFileService   生成文件保存预订单入参报文
 * @Description TODO
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2015年10月8日
 * @modify by reason:{方法名}:{原因}
 */
public class SaveFileService  {
	private static Logger logger = Logger.getLogger(SaveFileService.class);
    private static final String STORE_DIR = "session";
    String rootPath=this.getClass().getClassLoader().getResource("").getPath().toString(); 
    private  String tmpdir=rootPath.substring(1, rootPath.length())+"tmp";
    private  String filePath = tmpdir+"/"+STORE_DIR;

    public void init() throws IOException {
    	logger.info(rootPath);
    	logger.info(tmpdir);
    	File dir = new File(filePath);  
    	if(!dir.exists()){
    		dir.mkdirs();
    	}
    }
    public  Object file2Object(String fileName) {
    	String file = filePath +"/"+ fileName;
        try {
            ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(file)));
            Object object = ois.readObject();
            ois.close();
            return object;
        } catch (Exception e) {
        	e.printStackTrace(); 
        }
        return null; 
    }
    public  void object2File(Object obj, String outputFile){
    	logger.info(filePath);
    
    	String setFile = filePath+"/"+outputFile;
    	logger.info(setFile);
        ObjectOutputStream obs;
        try {
            obs = new ObjectOutputStream(Files.newOutputStream(Paths.get(setFile)));
            obs.writeObject(obj);
            obs.close();
        } catch (IOException e) {
        	e.printStackTrace(); 
        }
    }
 
}
