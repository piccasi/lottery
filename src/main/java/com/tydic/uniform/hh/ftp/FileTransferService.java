package com.tydic.uniform.hh.ftp;

/**
 * sftp和ftp文件传输的接口
 * @author leixiao
 *
 */
public interface FileTransferService{
	
	/**
	 * 连接ftp/sftp服务器
	 * 2014年1月6日 下午2:05:09 leixiao添加此方法
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 */
	public abstract void getConnect(String host, int port, String username, String password)throws Exception;
	
	/**
	 * 断开连接
	 * 2014年1月6日 下午2:06:28 leixiao添加此方法
	 */
	public abstract void disConn()throws Exception;
	
	/**
	 * 上传文件
	 * 2014年1月6日 下午2:06:41 leixiao添加此方法
	 * @param directory
	 * @param uploadFile
	 */
	public abstract void upload(String directory,String uploadFile)throws Exception;
	
	/**
	 * 下载文件
	 * 2014年1月6日 下午2:06:59 leixiao添加此方法
	 * @param directory
	 * @param downloadFile
	 * @param saveFile
	 */
	public abstract void download(String directory, String downloadFile,String saveFile)throws Exception;
	
	/**
	 * 删除文件
	 * 2014年1月6日 下午2:07:12 leixiao添加此方法
	 * @param directory
	 * @param deleteFile
	 */
	public abstract void delete(String directory, String deleteFile)throws Exception;
	
}