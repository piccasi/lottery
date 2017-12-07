package com.tydic.uniform.hh.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tydic.uniform.common.vo.resp.JsonResponse;
import com.tydic.uniform.hh.baseInvoke.AbstractService;
import com.tydic.uniform.hh.constant.CODE;
import com.tydic.uniform.hh.ftp.FileTransferService;
import com.tydic.uniform.hh.ftp.FtpService;
import com.tydic.uniform.hh.ftp.SftpService;
import com.tydic.uniform.hh.service.interfaces.FtpUploadServ;
import com.tydic.uniform.hh.util.PropertiesUtil;

import sun.misc.BASE64Decoder;

@Service("FtpUploadServ")
public class FtpUploadServiceImpl extends AbstractService implements FtpUploadServ {
	private static Logger log = Logger.getLogger(FtpUploadServiceImpl.class);
	
	@Override
	public String ftpUpload(Map map){
		log.info("*************************图片上传开始*************************");
		//log.info("APP upload str:" + JSONObject.fromObject(map).toString());
		StringBuffer buffer = new StringBuffer();
		FileTransferService fts = null;
		String resultString = "";
		try{
			String imageUrl = PropertiesUtil.getPropertyValue("IMAGE_URL");
			String machinePath = PropertiesUtil.getPropertyValue("FTP_MACHINE_PATH");
			String host = PropertiesUtil.getPropertyValue("FTP_IP");
			int port = Integer.parseInt(PropertiesUtil.getPropertyValue("FTP_PORT"));
			String username = PropertiesUtil.getPropertyValue("FTP_USER_NAME");
			String password = PropertiesUtil.getPropertyValue("FTP_PASSWORD");
			String portal = PropertiesUtil.getPropertyValue("PORTAL");
			
            if(portal.equals("FTP")){
            	fts = new FtpService();
            }else{
            	fts = new SftpService();
            }
            
            /*try{
            	  String filename = getFixLenthString(10);
            	  log.debug("--------文件名是-----------"+filename);
            	  File file = new File(machinePath + filename + ".txt");
            	  FileWriter fileWritter = new FileWriter(file.getName(),true);
            	  BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            	  bufferWritter.write((String)map.get("img4"));
            	  bufferWritter.close();
            }catch(Exception e){
            	e.printStackTrace();
            }*/
            
			for(int j=1;j <= 4; j++){
				/**
				 * 根据时间创建文件夹
				 */
				SimpleDateFormat sdf = new SimpleDateFormat("YYYYMM");
				String timeName = sdf.format(new Date());
				File file = new File(imageUrl+"/"+timeName);
				if (!file.exists()) {
					file.mkdir();
				}
				String imgPath = imageUrl;
				String saveFileName = System.currentTimeMillis()+".jpg";
				String sourcePath = System.getProperty("user.dir")+System.getProperty("file.separator")+saveFileName;
				File saveFile = new File(System.getProperty("user.dir")+System.getProperty("file.separator")+saveFileName);
				//生成jpeg图片
	            BASE64Decoder decoder = new BASE64Decoder();
	            String img = (String) map.get("img"+j);
	            int index = img.indexOf(",");
	            img = img.substring(index+1);
	            byte[] b = decoder.decodeBuffer(img);
	            for(int i=0;i<b.length;++i)
	            {
	                if(b[i]<0)
	                {//调整异常数据
	                    b[i]+=256;
	                }
	            }
	            OutputStream out = null;
	            try{
	            	out = new FileOutputStream(saveFile.getAbsolutePath()); 
		            out.write(b);
		            out.flush();
		            out.close();
	            }catch(Exception e){
	            	if(null != out){
	            		out.close();
	            	}
	            }
	            fts.getConnect(host, port, username, password);
				fts.upload(machinePath+"/"+timeName, sourcePath);
				buffer.append(saveFileName+",");
				log.info(saveFileName + "已经上传到ftp服务器");
				//saveFile.delete();
				if(saveFile.isFile() && saveFile.exists()){
					saveFile.getAbsoluteFile().delete();
				}
			}
			buffer.substring(0, buffer.length()-1);
			/*if (!imgPath.isEmpty()) {
				
				return JsonResponse.toSuccessResult(saveFileName);
			} else {
				throw new Exception("调用文件移动方法失败");
			}*/
			resultString =  JsonResponse.toSuccessResult(buffer.toString());
		}catch(Exception e){
			if(null != fts){
				try {
					fts.disConn();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			resultString =  JsonResponse.toErrorResult(CODE.INTERFACE_ERR, e.getMessage());
		}
		log.info("*************************图片上传接口出参*************************");
		log.info("APP upload str:" + resultString);
		return resultString;
	}
	
    /*
     * 返回长度为【strLength】的随机数，在前面补0
     */
    private static String getFixLenthString(int strLength) {
        
        Random rm = new Random();
        
        // 获得随机数
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);

        // 将获得的获得随机数转化为字符串
        String fixLenthString = String.valueOf(pross);

        // 返回固定的长度的随机数
        return fixLenthString.substring(1, strLength + 1);
    }
}
