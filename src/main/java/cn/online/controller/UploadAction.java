package cn.online.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.online.pojo.BaseSearch;
import cn.online.service.impl.AbstractSearch;
import cn.online.util.BaiduOcrClient;

/** 
 * @author  作者 E-mail: 
 * @date 创建时间：2017-6-18 下午12:20:49 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Controller
@RequestMapping(value = "health")
public class UploadAction extends AbstractSearch{  
  
    @RequestMapping(value = "/analysis") 
    //public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {  
      public void upload(MultipartHttpServletRequest multipartRequest,
    		   HttpServletResponse response) { 
    	response.setContentType("text/html;charset=UTF-8");  
    	  String result = "";
    	  try {
    	   for (Iterator it = multipartRequest.getFileNames(); it.hasNext();) {
    	    String key = (String) it.next();
    	    MultipartFile orderFile = multipartRequest.getFile(key);
    	    if (orderFile.getOriginalFilename().length() > 0) {
    	     String realPath = multipartRequest.getSession()
    	       .getServletContext()
    	       .getRealPath("/WEB-INF/uploadOrder");
    	     
    	     JSONObject genRes = BaiduOcrClient.getInstance().basicGeneral(orderFile.getBytes(), new HashMap<String, String>());
    	     result = genRes.toString(2);
    	     System.out.println(result);
    	        org.json.JSONArray array = genRes.getJSONArray("words_result");
    	        StringBuilder report_word = new StringBuilder();
    	        //Iterator item = array.iterator();
    	        array.length();
    	        for(int i = 0; i < array.length(); ++i){
    	        	String tmp = array.getJSONObject(i).get("words").toString();
    	        	report_word.append(tmp);
    	        	if(i < array.length() - 1 ){
    	        		report_word.append("###");
    	        	}
    	        }
    	     
 			BaseSearch baseSearch = new BaseSearch();
 			baseSearch.setActionName("Pis_HYD_Check");
 			baseSearch.setKeyWords(report_word.toString());
 			
 			result = callBackend(baseSearch);
 			result = "{result:'" + result + "'}";
 			System.out.println(result);
    	    /* FileUtils
    	       .copyInputStreamToFile(
    	         orderFile.getInputStream(),
    	         new File(realPath, orderFile
    	           .getOriginalFilename()));*/
    	     
/*    	     System.out.println("开始");  
    	        //String path = multipartRequest.getSession().getServletContext().getRealPath("upload");  
    	        String fileName = orderFile.getOriginalFilename(); 
    	        System.out.println(realPath);  
    	        File targetFile = new File(realPath, fileName);  
    	        if(!targetFile.exists()){  
    	            targetFile.mkdirs();  
    	        }  
    	  
    	        try {  
    	        	orderFile.transferTo(targetFile);  
    	        } catch (Exception e) {  
    	            e.printStackTrace();  
    	        }*/
    	    }
    	   }
    	     //result="{'result':'上传成功'}";
    	   } catch (Exception ex) {
    	        result="{result:'上传失败'}";
    	        ex.printStackTrace();
    	  }
    	  try {
			response.getWriter().print(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

/*        System.out.println("开始");  
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();  
        System.out.println(path);  
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }*/
        //model.addAttribute("fileUrl", request.getContextPath()+"/upload/"+fileName);  
  
        //return "analysis";  
    }  
  
}
