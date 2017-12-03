package com.cn.hnust.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import sun.misc.BASE64Decoder;

@Controller
@RequestMapping("/image")
public class ImageController {
	/*@Resource
	private IUserService userService;*/
	
	@RequestMapping("/save")
	public void saveImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取存储路径  
	    //HttpServletRequest request = ServletActionContext.getRequest();  
	    String path = request.getContextPath()  +"upload";  
	    File file = new File(path);  
	    if(!file.exists()){  
	        file.mkdir();  
	    }  
	    String imgPath  = path + File.separator + "1"; //+ request.getParameter("imgName");  
	    String imgStr = request.getParameter("imageStr");
	    //String imgStr = imageStr;
	    //Map<String, String[]> params = request.getParameterMap();
	    boolean flag = string2Image(imgStr, imgPath);  
	    response.getOutputStream().print("flag:" + flag);
	    response.getOutputStream().flush();
	}
	
	/** 
	 * 通过BASE64Decoder解码，并生成图片 
	 * @param imgStr 解码后的string 
	 */  
	public boolean string2Image(String imgStr, String imgFilePath) {  
	    // 对字节数组字符串进行Base64解码并生成图片  
	    if (imgStr == null)  
	        return false;  
	    try {  
	        // Base64解码  
	        byte[] b = new BASE64Decoder().decodeBuffer(imgStr);  
	        for (int i = 0; i < b.length; ++i) {  
	            if (b[i] < 0) {  
	                // 调整异常数据  
	                b[i] += 256;  
	            }  
	        }  
	        // 生成Jpeg图片  
	        OutputStream out = new FileOutputStream(imgFilePath);  
	        out.write(b);  
	        out.flush();  
	        out.close();  
	        return true;  
	    } catch (Exception e) {  
	        return false;  
	    }  
	}
}
