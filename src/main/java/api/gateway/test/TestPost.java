package api.gateway.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.entity.StringEntity;

import api.gateway.util.MD5;

import com.alibaba.fastjson.JSONObject;

public class TestPost {
	public static void main(String[] args) {

		PostMethod post=null;
		try{
		HttpClient client = new HttpClient();
		 post = new PostMethod("http://localhost:8083/api/gateway");

		Date date = new Date();
	SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
	String timestamp = format1.format(date);
		HttpMethodParams p = new HttpMethodParams();
	NameValuePair pair1=new NameValuePair("SUBJECT", "Pis_Get_Medical_Info"); 
	NameValuePair pair2=new NameValuePair("KEY_WORDS", "蒲地蓝");
	NameValuePair pair3=new NameValuePair("TYPE", "2");

	/*String signValue="[subject=Pis_User_New][version=1.0]" +
	         "[appid=10014]" +
             "[phone=17080340147]7b9d31aa17b849b238ab79cef0733041";
    System.out.println("加密前="+signValue);
    signValue=MD5.md5(signValue);
    System.out.println("加密后="+signValue);
	NameValuePair pair4=new NameValuePair("signValue", signValue);*/
    NameValuePair[] pairs={pair1,pair2,pair3};
    post.setRequestBody(pairs); 
    post.setParams(p);
    post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
	int statusCode = client.executeMethod(post);
	
	if(statusCode != HttpStatus.SC_OK){
		

	}
	String response =post.getResponseBodyAsString();

	System.out.println("param="+response);
		}
	catch(Exception e){
		e.printStackTrace();
		
	}finally{
		post.releaseConnection();
	}
	
}
}
