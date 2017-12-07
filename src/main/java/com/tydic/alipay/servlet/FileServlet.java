package com.tydic.alipay.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tydic.uniform.hh.ftp.FileTransferService;
import com.tydic.uniform.hh.ftp.FtpService;
import com.tydic.uniform.hh.ftp.SftpService;
import com.tydic.uniform.hh.util.PropertiesUtil;


public class FileServlet extends HttpServlet {
	private final static Logger LOGGER = Logger.getLogger(FileServlet.class);
	private static String IMGPATH = "http://172.16.100.2:11000/qnResources/productImages/zhangting/";
	
	/**
	 * Constructor of the object.
	 */
	public FileServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");		
		String flag = "";
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		String standard = "";
		try {
			List items = upload.parseRequest(request);
			
			Iterator itr = items.iterator();
			Map<String, Object> result = new HashMap<String, Object>();
			String imageUrl = PropertiesUtil.getPropertyValue("IMAGE_URL");
			String machinePath = PropertiesUtil.getPropertyValue("FTP_MACHINE_PATH");
			String host = PropertiesUtil.getPropertyValue("FTP_IP");
			int port = Integer.parseInt(PropertiesUtil.getPropertyValue("FTP_PORT"));
			String username = PropertiesUtil.getPropertyValue("FTP_USER_NAME");
			String password = PropertiesUtil.getPropertyValue("FTP_PASSWORD");
			String portal = PropertiesUtil.getPropertyValue("PORTAL");
			
			String imgPath = imageUrl;
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				 if("imginput4".equals(item.getFieldName())) {
		                standard = item.getString("UTF-8").trim();
		              }
				
				if (item.isFormField() && !item.getFieldName().equals("flag")&& !item.getFieldName().equals("members") && !item.getFieldName().equals("agent")) {
					System.out.println("表单参数名:" + item.getFieldName()
							+ "，表单参数值:" + item.getString("UTF-8"));
					String saveFileName = System.currentTimeMillis()+".jpg";
					String sourcePath = System.getProperty("user.dir")+System.getProperty("file.separator")+saveFileName;
					File saveFile = new File(System.getProperty("user.dir"), saveFileName);
					
                    byte [] buf = new byte[1024] ;
                    
					BASE64Decoder decoder = new BASE64Decoder();
			            //Base64解码
		            byte[] b = decoder.decodeBuffer(item.getString("UTF-8"));
		            for(int i=0;i<b.length;++i)
		            {
		                if(b[i]<0)
		                {//调整异常数据
		                    b[i]+=256;
		                }
		            }
		            //生成jpeg图片
		            OutputStream out = new FileOutputStream(saveFileName);    
		            out.write(b);
		            out.flush();
		            out.close();
		            
		            
		            FileTransferService fts = null;
		            if(portal.equals("FTP")){
		            	fts = new FtpService();
		            }else{
		            	fts = new SftpService();
		            }
		            fts.getConnect(host, port, username, password);
					fts.upload(machinePath, sourcePath);
					
					LOGGER.info(saveFileName + "已经上传到ftp服务器");
					saveFile.delete();
					if (!imgPath.isEmpty()) {
						result.put("result", "0");
						result.put(item.getFieldName(), imgPath + "/"
								+ saveFileName);
					} else {
						result.put("result", "1");
						result.put("errorMsg", "调用文件移动方法失败");
					}
				} else {
					flag = item.getString("UTF-8");
					request.setAttribute("upload.message", "没有选择上传文件！");
				}
			}
			String img1 = result.get("img1").toString();
			String img2 = result.get("img2").toString();
			String img3 = result.get("img3").toString();
			String ftpimg1 = StringInterception(img1);
			String ftpimg2 = StringInterception(img2);
			String ftpimg3 = StringInterception(img3);
			Map image = new HashMap();
			image.put("img1", ftpimg1);
			image.put("img2", ftpimg2);
			image.put("img3", ftpimg3);
			String img = JSON.toJSONString(image);
			StringBuffer imgbuffer = new StringBuffer();
			imgbuffer.append(img1);
			imgbuffer.append(",");
			imgbuffer.append(img2);
			imgbuffer.append(",");
			imgbuffer.append(img3);
			
			LOGGER.info(img+"-"+flag);
			if(null != flag && flag.equals("contractPhoto")){
				LOGGER.info("跳转: "+PropertiesUtil.getPropertyValue("APP_CONTEXT")+ "/hh/html/contractdeliveryinfo.html#img="+ img);
				response.sendRedirect(PropertiesUtil
						.getPropertyValue("APP_CONTEXT")
						+ "/hh/html/contractdeliveryinfo.html#img="+ img);
			}else if(standard.equals("agent")){
				LOGGER.info("跳转: "+PropertiesUtil.getPropertyValue("APP_CONTEXT")+ "/hhagent/html/rmsgWriter.html?img="+ img);
				response.sendRedirect(PropertiesUtil
						.getPropertyValue("APP_CONTEXT")
						+ "/hhagent/html/rmsgWriter.html?img="+ img);
			}else{
				LOGGER.info("跳转: "+PropertiesUtil.getPropertyValue("APP_CONTEXT")+ "/hh/html/deliveryinfo.html#img="+ img);
				response.sendRedirect(PropertiesUtil
						.getPropertyValue("APP_CONTEXT")
						+ "/hh/html/deliveryinfo.html#img="+ img);
			}
		} catch (FileUploadException e) {
			//e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("upload.message", "上传文件失败！");
		}
	}


	public void init() throws ServletException {
		// Put your code here
	}
	
	public String StringInterception(String ftpPath){
		LOGGER.info("愿路径"+ftpPath);
		String[] ftpimg = ftpPath.split("/");
		
		//获取文件名字
		String ftpimgName = ftpimg[ftpimg.length-1];
		return ftpimgName;
		
//			String ftpimgName = ftpimg[ftpimg.length-1];
//			StringBuffer ftpimgpathBuffer = new StringBuffer();
//			ftpimgpathBuffer.append(PropertiesUtil.getPropertyValue("IMAGE_URL"));
//			ftpimgpathBuffer.append("/");
//			ftpimgpathBuffer.append(ftpimgName);
//			String ftpimgpath = ftpimgpathBuffer.toString();
//			return ftpimgpath;
//		}else{
//			LOGGER.info("ftp图片地址格式有误");
//			return "";
//		}
	}
	
}
