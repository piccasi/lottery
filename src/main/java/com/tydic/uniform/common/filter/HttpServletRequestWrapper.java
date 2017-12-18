package com.tydic.uniform.common.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.tydic.uniform.common.util.DesEncryptUtil;
import com.tydic.uniform.common.util.RSAUtil;
/**
 * 重写HttpServletRequest类，对所有加密过的请求数据进行解密
 * @author chenyu
 * 
 */
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper{
	  private static Logger logger = Logger.getLogger(HttpServletRequestWrapper.class);
	  private Map<String, String[]> params;  
	  private static final int BUFFER_START_POSITION = 0;
	  private static final int CHAR_BUFFER_LENGTH = 1024;
	  
	  private  byte[] body;  
	  private String HEADER;
	  public HttpServletRequestWrapper(HttpServletRequest request) throws IOException {  
	      super(request);   
	      params = request.getParameterMap();//new HashMap<String, String[]>();
	      //this.

		  this.initByteBody(request);

	      //this.params.putAll(dealParameterMap(request.getParameterMap()));  //url后面的参数和post提交的表单参数暂时不加密，只为H5页面能正常访问pdf
	     
	  } 
	  
	  public String RepeatedlyReadRequestWrapper(HttpServletRequest request) {
	        //super(request);

	        StringBuilder stringBuilder = new StringBuilder();

	        InputStream inputStream = null;
	        try {
	            inputStream = request.getInputStream();
	        } catch (IOException e) {
	        	logger.error("Error reading the request body…", e);
	        }
	        if (inputStream != null) {
	            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
	                char[] charBuffer = new char[CHAR_BUFFER_LENGTH];
	                int bytesRead;
	                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                    stringBuilder.append(charBuffer, BUFFER_START_POSITION, bytesRead);
	                }
	            } catch (IOException e) {
	            	logger.error("Fail to read input stream",e);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	        String body = stringBuilder.toString();
	        return body;
	    }
	  
	  private void initByteBody(HttpServletRequest request) throws IOException{
		  /*BufferedReader br = request.getReader();
		  String json = "",line="", header="";
		  while ((line = br.readLine()) != null) {
		         json += line;
		  }*/
		  
		  String json = "",line="", header="";
		  json = RepeatedlyReadRequestWrapper(request);
		  //json = DesEncryptUtil.decrypt(json);
		  
		  if(!"".equals(json)){
			  //String uri = request.getRequestURI();
			  logger.info("json: " + json);
			  /*try {
				  json = RSAUtil.decryptArray(json);
			  } catch (Exception e) {
				  e.printStackTrace();
			  }*/
				  
				  
				  JSONObject obj = JSONObject.fromObject(json);
				  header = obj.getString("HEADER");
				  //json = obj.getString("BODY");
				  //json = DesEncryptUtil.decrypt(json);
				  
			
				  logger.info("json: " + json);
		  }
		  
		  //System.out.println("json: " + json);
		  this.body = json.getBytes("UTF-8");
		  this.HEADER = header;
	  }
	  /**
	   * 前台的value必须密文传递，不允许明文传递
	   * @param params
	   * @return
	   */	  
	  private Map<String, String[]> dealParameterMap(Map<String, String[]> params){	
		  Map<String, String[]> tParams = new HashMap<String, String[]>();
		 for (String key : params.keySet()) { 
			 String[] tempVals = params.get(key);
			 for(int i=0; tempVals != null&& i < tempVals.length; i++){
				 System.out.println("解密前:"+tempVals[i]);
				 tempVals[i] = DesEncryptUtil.decrypt(tempVals[i]);
				 System.out.println("解密后:"+tempVals[i]);
			 }
			 System.out.println("解密前的:" + key);
			 String tempKey = DesEncryptUtil.decrypt(key);
			 System.out.println("解密后的:" + tempKey);
			 tParams.put(tempKey, tempVals);
		 }  
		 return tParams;
	  }
	  
/*	  private Map<String, String> getParameterMap(HttpServletRequest request){	
		  Map<String, String> tParams = new HashMap<String, String>();
		 for (String key : request.getParameterMap()) { 
			 String[] tempVals = params.get(key);
			 for(int i=0; tempVals != null&& i < tempVals.length; i++){
				 System.out.println("解密前:"+tempVals[i]);
				 tempVals[i] = DesEncryptUtil.decrypt(tempVals[i]);
				 System.out.println("解密后:"+tempVals[i]);
			 }
			 System.out.println("解密前的:" + key);
			 String tempKey = DesEncryptUtil.decrypt(key);
			 System.out.println("解密后的:" + tempKey);
			 tParams.put(tempKey, tempVals);
		 }  
		 return tParams;
	  }*/
	  
	  @Override  
	  public String getParameter(String name) {  
	        String result = "";  
	          
	        Object v = params.get(name);  
	        if (v == null) {  
	            result = null;  
	        } else if (v instanceof String[]) {  
	            String[] strArr = (String[]) v;  
	            if (strArr.length > 0) {  
	                result =  strArr[0];  
	            } else {  
	                result = null;  
	            }  
	        } else if (v instanceof String) {  
	            result = (String) v;  
	        } else {  
	            result =  v.toString();  
	        }  
	          
	        return result;  
	    }  
	  
	    @Override  
	    public Map<String, String[]> getParameterMap() {  
	        return params;  
	    }  
	  
	    @Override  
	    public Enumeration<String> getParameterNames() {  
	        return new Vector<String>(params.keySet()).elements();  
	    }  
	  
	    @Override  
	    public String[] getParameterValues(String name) {  
	        String[] result = null;  
	          
	        Object v = params.get(name);  
	        if (v == null) {  
	            result =  null;  
	        } else if (v instanceof String[]) {  
	            result =  (String[]) v;  
	        } else if (v instanceof String) {  
	            result =  new String[] { (String) v };  
	        } else {  
	            result =  new String[] { v.toString() };  
	        }  
	          
	        return result;  
	    }  
	    
	    @Override  
	    public BufferedReader getReader() throws IOException {  
	        return new BufferedReader(new InputStreamReader(getInputStream()));  
	    }  
	  
	    @Override  
	    public ServletInputStream getInputStream() throws IOException {  
	        final ByteArrayInputStream bais = new ByteArrayInputStream(body);  
	        return new ServletInputStream() {  
	        	
	        	private boolean isFinished = false;
	        	
	        	private boolean isReady = true;
	        	
	        	private ReadListener readListener;
	        	//数据是否读到末尾

				public boolean isFinished() {
					// TODO Auto-generated method stub
					return this.isFinished;
				}
	            //数据是否准备好读取
				public boolean isReady() {
					// TODO Auto-generated method stub
					return this.isReady;
				}

				public void setReadListener(ReadListener readListener) {
					// TODO Auto-generated method stub
					this.readListener = readListener;
					
				}
				
	            public int read() throws IOException { 
					try {
						int a = bais.read();
						if(a == -1){
							if(this.readListener != null){
								this.readListener.onAllDataRead();
							}
							this.isFinished = true;
						}
						if(this.isReady){
							if(this.readListener != null){
								this.readListener.onDataAvailable();
							}
							this.isReady = false;
						}
						
						return  a;
					} catch (IOException e) {
						if(this.readListener != null){
							this.readListener.onError(e);
						}
						throw e;
					} 
	            }  
	        };  
	    }
		public String getHeader() {
			return HEADER;
		}
		public void setHeader(String header) {
			this.HEADER = header;
		}  
	    
}	
