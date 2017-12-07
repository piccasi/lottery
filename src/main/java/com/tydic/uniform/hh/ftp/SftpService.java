package com.tydic.uniform.hh.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;





public class SftpService implements FileTransferService{
    private static Session session = null;   
    private static Channel channel = null;   
    private static ChannelSftp sftp = null;// sftp操作类    
    
    /**
     * 
     * 参看父类中的注释 @see cn.mr.mohurd.service.sftp.FileTransferService#getConnect(java.lang.String, int, java.lang.String, java.lang.String)
     */
    public void getConnect(String host, int port, String username, String password) throws Exception {
        JSch jsch = new JSch();   
        session = jsch.getSession(username, host, port);   
        session.setPassword(password);   
        Properties config = new Properties();   
        config.put("StrictHostKeyChecking", "no"); // 不验证 HostKey    
        session.setConfig(config);   
        try {
            session.connect();   
        } catch (Exception e) {
            if (session.isConnected())   
                session.disconnect();   
            throw new Exception("连接服务器失败,请检查主机[" + host + "],端口[" + port   
                    + "],用户名[" + username + "],端口[" + port   
                    + "]是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝.");   
        }
        channel = session.openChannel("sftp");   
        try {
            channel.connect();   
        } catch (Exception e) {   
            if (channel.isConnected())   
                channel.disconnect();   
            throw new Exception("连接服务器失败,请检查主机[" + host + "],端口[" + port   
                    + "],用户名[" + username + "],密码[" + password   
                    + "]是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝.");   
        }
        sftp = (ChannelSftp) channel;   
    }
    
    /**
     * 断开连接
     * 参看父类中的注释 @see cn.mr.mohurd.service.sftp.FileTransferService#disConn()
     */
    public void disConn()throws Exception{
        if(null != sftp){
            sftp.disconnect();
            sftp.exit();
            sftp = null;
        }
        if(null != channel){
            channel.disconnect();
            channel = null;
        }
        if(null != session){
            session.disconnect();
            session = null;
        }
    }

    /**
     * 上传文件
     * 参看父类中的注释 @see cn.mr.mohurd.service.sftp.FileTransferService#upload(java.lang.String, java.lang.String)
     */
    public void upload(String directory,String uploadFile) throws Exception {
        try {
            if (!directory.equals("") && directory.trim() != "") {  
                try{
                    sftp.cd(directory);
                }catch(SftpException sException){
                    if(sftp.SSH_FX_NO_SUCH_FILE == sException.id){ //指定上传路径不存在
                        sftp.mkdir(directory);
                        sftp.cd(directory);  
                    }
                }
            }
            File file = new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            throw new Exception(e.getMessage(),e); 
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
            sftp.cd(directory); 
            File file = new File(saveFile);
            boolean bFile;
            bFile = false;
            bFile = file.exists();
            if (!bFile) {
                bFile = file.mkdirs();
            }
            sftp.get(downloadFile, new FileOutputStream(new File(saveFile,downloadFile)));
        } catch (Exception e) {
            throw new Exception(e.getMessage(),e); 
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
            sftp.cd(directory);  
            sftp.rm(deleteFile);
        } catch (Exception e) {
            throw new Exception(e.getMessage(),e); 
        } finally {
	    	disConn();
	    }
    }
    
    
    public static void main(String[] args) {
    	SftpService ss = new SftpService();
    	try {
			ss.getConnect("192.168.20.40", 22, "wss", "wss");
			ss.upload("/home/wss/resources/productImages/product/app/", "D:/1426597317959.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
