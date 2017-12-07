package com.tydic.uniform.hh.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;


public class FtpService implements FileTransferService{
	private static FTPClient ftp = null; 
	private static String encoding = "";

	/**
	 * 
	 * 参看父类中的注释 @see cn.mr.mohurd.service.sftp.FileTransferService#getConnect(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	public void getConnect(String host, int port, String username, String password) throws Exception {
		try {
			ftp = new FTPClient(); 
	    	ftp.connect(host, port);//连接FTP服务器    
	    	ftp.login(username, password);//登录    
	    	int reply = ftp.getReplyCode();   
	        if (!FTPReply.isPositiveCompletion(reply)) { 
	        	disConn(); 
	        	throw new IOException();
	        } 
	        /*encoding = System.getProperty("file.encoding");
			ftp.setControlEncoding(encoding);
	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);*/ 
		} catch (Exception e){
			System.out.print(e.getMessage()+e);
			throw new Exception("连接ftp服务器失败,请检查主机[" + host + "],端口[" + port   
	                + "],用户名[" + username + "],密码[" + password   
	                + "]是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝."); 
		}
	}

	/**
	 * 断开连接
	 * 参看父类中的注释 @see cn.mr.mohurd.service.sftp.FileTransferService#disConn()
	 */
	public void disConn() throws Exception{
		try {
	    	if (ftp.isConnected()) {
	        	ftp.logout();
	            ftp.disconnect();   
	        }
		} catch (Exception e) {   
			throw new Exception("断开ftp连接失败"); 
	    }
	}

	/**
	 * 上传文件
	 * 参看父类中的注释 @see cn.mr.mohurd.service.sftp.FileTransferService#upload(java.lang.String, java.lang.String)
	 */
	public void upload(String directory,String uploadFile) throws Exception {
		/*directory = new String(directory.getBytes(encoding),"iso-8859-1"); 
		uploadFile = new String(uploadFile.getBytes(encoding),"iso-8859-1"); */
		try {
			//如果目录不存在，则创建目录
		    if (directory != null && !"".equals(directory.trim())) {
		        String[] pathes = directory.split("/");
		        for (String onepath : pathes) {
		            if (onepath == null || "".equals(onepath.trim())) {
		                continue;
		            }
		            onepath=new String(onepath);                    
		            if (!ftp.changeWorkingDirectory(onepath)) {
		                ftp.makeDirectory(onepath);
		                ftp.changeWorkingDirectory(onepath);
		            }
		        }
		    }
		    File file = new File(uploadFile);
		    FileInputStream input = new FileInputStream(file);
		    ftp.storeFile(file.getName(), input); 
	        input.close();   
	    } catch (Exception e) {
	        throw new Exception("文件上传失败！"+e.getMessage()); 
	    } finally {
	    	disConn();
	    }
	}

	/**
	 * 下载文件
	 * 参看父类中的注释 @see cn.mr.mohurd.service.sftp.FileTransferService#download(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void download(String directory, String downloadFile,String saveFile) throws Exception {
		try {
			/*directory = new String(directory.getBytes(encoding),"iso-8859-1"); 
			downloadFile = new String(downloadFile.getBytes(encoding),"iso-8859-1"); 
			saveFile = new String(saveFile.getBytes(encoding),"iso-8859-1"); */
	    	ftp.changeWorkingDirectory(directory);//转移到FTP服务器目录  
	        FTPFile[] fs = ftp.listFiles();  
	        boolean hasFile = false;
	        for(FTPFile ff:fs){  
	            if(ff.getName().equals(downloadFile)){ 
	                hasFile = true;
	                File localFile = new File(saveFile);
	                if (!localFile.exists()) {
	                	localFile.mkdirs();
	                }
	                OutputStream is = new FileOutputStream(localFile+"/"+downloadFile);   
	                ftp.retrieveFile(ff.getName(), is);  
	                is.close();  
	            }  
	        }
	        if (!hasFile){
	            throw new Exception("没有在指定目录"+directory+"找到需要下载的文件"+downloadFile); 
	        }
		} catch (Exception e){
		    throw new Exception(e); 
		} finally {
	    	disConn();
	    }
	}

	/**
	 * 删除文件
	 * 参看父类中的注释 @see cn.mr.mohurd.service.sftp.FileTransferService#delete(java.lang.String, java.lang.String)
	 */
	public void delete(String directory, String deleteFile) throws Exception {
		try {
			/*directory = new String(directory.getBytes(encoding),"iso-8859-1"); 
			deleteFile = new String(deleteFile.getBytes(encoding),"iso-8859-1"); */
			//ftp.doCommand("DELE", directory+"/"+deleteFile);
			if (!ftp.deleteFile(directory+"/"+deleteFile)){
				throw new Exception("删除文件失败，原因为"+ftp.getReplyString()); 
			}		
	    } catch (Exception e) {
	    	throw new Exception(e.getMessage(),e); 
	    } finally {
	    	disConn();
	    }
	}
}
