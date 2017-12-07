package com.tydic.uniform.hh.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;



/**
 * @author huangwx
 *
 */
public class SoapUtil {
	private static Logger logger = Logger.getLogger(SoapUtil.class);
	/**
	 * @param serviceName
	 * @param transactionID
	 * @param data
	 * @return
	 */
	public String SoapWebService(String serviceName,String esbName,String sysId,String data) {
		data = "{\"SOO\":"  + data + "}";
		logger.info("SoapWebService服务调用入参：serviceName="+serviceName);
		logger.info("SoapWebService服务调用入参：esbName="+esbName);
		logger.info("SoapWebService服务调用入参：sysId="+sysId);
		logger.info("SoapWebService服务调用入参：data="+data);
		
		try {
			//创建连接
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory
					.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();

			//创建实际的消息
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage message = messageFactory.createMessage();

			//创建消息的部分对象            
			SOAPPart soapPart = message.getSOAPPart();
			
			//获取发送报文
			String xml = req(serviceName,sysId,data);
			logger.info("SoapWebService发送报文：xml="+xml);
			InputStream fileInputStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));   
			StreamSource preppedMsgSrc = new StreamSource(fileInputStream,"UTF-8");
			//填充消息
			soapPart.setContent(preppedMsgSrc);
			//保存消息
			message.saveChanges();
			
			//青牛测试环境地址
			String destination = "http://172.16.100.1:8020"+esbName;
			//发送消息
			SOAPMessage reply = connection.call(message, destination);
			
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = (Transformer) transformerFactory.newTransformer();
			ByteArrayOutputStream myOutStr = new ByteArrayOutputStream();
			if(reply!=null){

			//提取的回复内容
			Source sourceContent = reply.getSOAPPart().getContent();
			//为转换设置输出
			StreamResult result = new StreamResult(System.out);
		
			result.setOutputStream(myOutStr);

			transformer.transform(sourceContent,result);
			
			}
			String msg = myOutStr.toString("UTF-8").trim();    
			logger.info("SoapWebService出参完整报文：msg="+msg);
			connection.close();
			return rsp(msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "";
		}
		
		
	}
		
		
	/** 
	 * 发送报文
	 * @param serviceName 服务名称
	 * @param transactionID 流水号
	 * @param data 拼接报文
	 * @return xml报文
	 */
	public static String req(String ServiceCode,String sysId,String data){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		String ReqTime = formatter.format(new Date());
		String encryptValue = signatureInfo(data);
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("TransactionID", UUID.randomUUID().toString());
		headMap.put("ReqTime", ReqTime);
		headMap.put("SignatureInfo", encryptValue);
		headMap.put("ServiceCode", ServiceCode);
		headMap.put("SYS_ID", sysId);
		String val = null;
		try {
			val = JSON.toJSONString(headMap);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		String xml = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns1=\"http://www.tydic.com/\">" +
					 "<SOAP-ENV:Header/><SOAP-ENV:Body><Business>{" + 
					 "\"TcpCont\":" + val + ",\"SvcCont\":" + data +
					 "}</Business></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		return xml;
	}
	
	/** 
	 * 返回报文
	 * @param serviceName 服务名称
	 * @param transactionID 流水号
	 * @param data 拼接报文
	 * @return xml报文
	 */
	public static String rsp(String data){
		RSAEncrypt rsaEncrypt = new RSAEncrypt();
		String repValue = data.substring(data.indexOf("<Business>")+10, data.indexOf("</Business>"));
		repValue = repValue.replaceAll("'", "\"");
		logger.info(repValue);
		Map<String, Map<String, Object>> maps = null;
		try {
			maps = JSON.parseObject(repValue,Map.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		Map<String, Object> TcpContMap = (Map<String, Object>) maps.get("TcpCont");
		Map<String, Object> SvcCont = (Map<String, Object>) maps.get("SvcCont");
		String content =  SvcCont.toString() ;
		logger.info(content);
		String SignatureInfo = (String) TcpContMap.get("SignatureInfo");
		String decValue = rsaEncrypt.dec(SignatureInfo);
		logger.info(decValue);
		String encValue = rsaEncrypt.MD5(content);
		logger.info(encValue);
		if(decValue.equals(encValue)){
			return content;	
		}else{
			return "认证失败";
		}		
	}
	
	/** 
	 * 消息体认证
	 * @param json 前台拼接的报文
	 * @return 认证密文
	 */
	public static String signatureInfo(String json) {
		RSAEncrypt rsaEncrypt = new RSAEncrypt();
		String encryptValue= null;
		try {
            String code = rsaEncrypt.MD5(json);
    		//把MD5加密内容通过私钥认证
    		encryptValue = rsaEncrypt.enc(code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encryptValue; 	
	}
	
	public static void main(String[] args) {
		List list = new ArrayList();
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("CHANNEL_CODE", "01");
		data.put("EXT_SYSTEM", "10002");
		data.put("CUST_NAME", "黄先生");
		data.put("SERVICE_NBR", "13001211111");
		data.put("PWD", "e10adc3949ba59abbe56e057f20f883e");
		
		Map<String, Object> type=new HashMap<String, Object>();
		type.put("TYPE", "SAVE_CUST_REQ");
		
		List<Map<String,Object>> soo=new ArrayList<Map<String, Object>>();
		soo.add(new HashMap());
		soo.get(0).put("CUST", data);
		soo.get(0).put("PUB_REQ", type);
		
		Map<String, Object> data1=new HashMap<String, Object>();
		data1.put("CHANNEL_CODE", "01");
		data1.put("EXT_SYSTEM", "10002");
		data1.put("CUST_NAME", "黄先生");
		data1.put("SERVICE_NBR", "13001211111");
		data1.put("PWD", "e10adc3949ba59abbe56e057f20f883e");
		
		Map<String, Object> type1=new HashMap<String, Object>();
		type1.put("TYPE", "SAVE_CUST_REQ");
		soo.add(new HashMap());
		soo.get(1).put("CUST", data);
		soo.get(1).put("PUB_REQ", type);
		
		String reqJson;
		String ll = null;
		try {
			ll = JSON.toJSONString(soo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SoapUtil soapUilt = new SoapUtil();
		String rspJson = soapUilt.SoapWebService("/ServiceBus/custView/cust/custReg002", "/esb/Register", "108", ll);
		logger.info("SoapWebService服务调用报文体出参：rspJson="+rspJson);
	}
}
