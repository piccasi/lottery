package com.tydic.uniform.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.tydic.uniform.hh.util.PropertiesUtil;


/** 
 * RSA 工具类。提供加密，解密，生成密钥对等方法。 
 * 需要到http://www.bouncycastle.org下载bcprov-jdk14-123.jar。 
 *  
 */  
public class RSAUtil {  
	private final static Logger LOGGER = Logger.getLogger(RSAUtil.class);
    private final static BouncyCastleProvider bouncyCastleProvider = new org.bouncycastle.jce.provider.BouncyCastleProvider();
    //RSA公钥的模数，放在js端
    //private final static js_modulus = "C43306809B5671F66917C828577323E1D2C5E57CB03A9DEEF62D78E8C12BC3DDF0E6728DD97C383FEB328A388E494964CF782318E9CC2FA75E769BC715F1592685BC20339DC81DE8FB9730EA9FCCE5AFB9DF647DBEFD9BB1AEE09EDDF634CDDFE7403DC1CAF3BDEBC2168EA67FDCDDAF8071155770BA3731A265967B32B8AB01";
    
    //密钥初始化
    private static volatile boolean inited = false;
    //private static final String PATH = PropertiesUtil.getPropertyValue("RSA_KEY_PATH");
    private static final String PATH = "D:/密钥/大唐/大唐";
    private static RSAPublicKey publicKey;
    private static RSAPrivateKey privateKey;
    
    static {
    	init(PATH);
    }
    
    
    private synchronized static void init(String path){
    	if(!inited){
    		if(StringUtils.isEmpty(path)){
    			path = RSAUtil.class.getClassLoader().getResource("").getPath();
    		}else{
    			path = PATH;
    		}
    		LOGGER.info("RSA KEY PATH: " + path);
			FileInputStream fis;
			try {
				fis = new FileInputStream( path + "/rsa_public_key.pem");
				publicKey = loadPublicKey(fis);
				fis = new FileInputStream( path + "/rsa_private_key.pem");
		        privateKey = loadPrivateKey(fis);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	       	        
	        inited = true;
	        System.out.println("RSA inited!");
    	}
        
    }
    
    /** 
     * * 加密 * 
     *  
     * @param key 
     *            加密的密钥 * 
     * @param data 
     *            待加密的明文数据 * 
     * @return 加密后的数据 * 
     * @throws Exception 
     */  
    public static String encrypt(byte[] data) throws Exception {  
    	//init(PATH);
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(data);
            return  Base64.encode(output);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  }  
  
    /** 
     * * 解密 * 
     *  
     * @param key 
     *            解密的密钥 * 
     * @param raw 
     *            已经加密的数据 * 
     * @return 解密后的明文 * 
     * @throws Exception 
     */  
    public static byte[] decryptForBase64(byte[] cipherData) throws Exception {
    		//init(PATH);
		    Cipher cipher = null;  
		    try {  
		        // 使用默认RSA  
		        cipher = Cipher.getInstance("RSA");  
		        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
		        byte[] output = cipher.doFinal(cipherData);  
		        return output;  
		    } catch (NoSuchAlgorithmException e) {  
		        throw new Exception("无此解密算法");  
		    } catch (NoSuchPaddingException e) {  
		        e.printStackTrace();  
		        return null;  
		    } catch (InvalidKeyException e) {  
		        throw new Exception("解密私钥非法,请检查");  
		    } catch (IllegalBlockSizeException e) {  
		        throw new Exception("密文长度非法");  
		    } catch (BadPaddingException e) {  
		        throw new Exception("密文数据已损坏");  
		    } 
    }
    
    public static byte[] decryptForHex(byte[] raw) throws Exception {  
    	//init(PATH);
        try {  
            Cipher cipher = Cipher.getInstance("RSA",  
            		bouncyCastleProvider);  
            cipher.init(cipher.DECRYPT_MODE, privateKey);  
            int blockSize = cipher.getBlockSize();  
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);  
            int j = 0;  
  
            while (raw.length - j * blockSize > 0) {  
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));  
                j++;  
            }  
            return bout.toByteArray();  
        } catch (Exception e) {  
            throw new Exception(e.getMessage());  
        }  
    }  
  
    /** 
     * * * 
     *  
     * @param args * 
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception { 
    	//init("D:/密钥/大唐/大唐");
        String test = "sauifhsajasdkjfhsfdghb";  
        test = java.net.URLEncoder.encode(test);
        String en_test = encrypt(test.getBytes());
/*        File publicFile = new File("D:/密钥/大唐/大唐/rsa_public_key.pem");
        InputStream publicIn  = new FileInputStream(publicFile);
        RSAPublicKey rSAPublicKey = loadPublicKey(publicIn);
        test = java.net.URLEncoder.encode(test);
        String en_test = encrypt(test.getBytes());
        System.out.println(en_test);
        
        File privateFile = new File("D:/密钥/大唐/大唐/rsa_private_key.pem");
        InputStream privateIn  = new FileInputStream(privateFile);
        RSAPrivateKey RSAPrivateKey = loadPrivateKey(privateIn);*/
        byte[] de_test = decryptForBase64(Base64.decode(en_test));
        System.out.println(java.net.URLDecoder.decode(new String(de_test),"UTF-8"));
        
        
        String cryptString = "ksa;hdfpoihsqdf:";
        String cryptString1 = "haskdfhskdf";
        String ssString = encrypt(cryptString.getBytes());
        String ssString1 = encrypt(cryptString1.getBytes());
        System.out.println("ssString: " + ssString);
        System.out.println("ssString1: " + ssString1);
        System.out.println(decryptArray(ssString+","+ssString1));
        
        String entest = "4d75a63a4229605b113956b1c43fbc3da357cac67177b705c1e00105e1fce9629a878f3e1109ea09c08d3623b0001e56d41e4bc39f39e387c16ff43d09127e2557a4e19fe1030cea7e18a9ff44366b2f78c548d033daf06db48683936f9a4d5a4eac34ced3c5b14ee447c514503559f52125f003ced72558937483e0d865e77a,HEXTYPEFORJAVASCRIPT";
        
        System.out.println("entest: " + entest.length());
        String de_arr = decryptArray(entest);
        System.out.println(de_arr);

    }  
    
    
    
    private static  byte[] hexStringToBytes(String hexString) {  
	    if (hexString == null || hexString.equals("")) {  
	        return null;  
	    }  
	    
	    hexString = hexString.toUpperCase();  
	    int length = hexString.length() / 2;  
	    char[] hexChars = hexString.toCharArray();  
	    byte[] d = new byte[length];  
	    for (int i = 0; i < length; i++) {  
	        int pos = i * 2;  
	        d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
	    }  
	    return d;  
	}  
	private static byte charToByte(char c) {  
	    return (byte) "0123456789ABCDEF".indexOf(c);  
	}
	
	
	
	public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception{  
            byte[] buffer= Base64.decode(privateKeyStr);  
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);  
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
    }
	
	public static RSAPrivateKey loadPrivateKey(InputStream in) throws Exception{  
        try {  
            BufferedReader br= new BufferedReader(new InputStreamReader(in));  
            String readLine= null;  
            StringBuilder sb= new StringBuilder();  
            while((readLine= br.readLine())!=null){  
                if(readLine.charAt(0)=='-'){  
                    continue;  
                }else{  
                    sb.append(readLine);  
                    sb.append('\r');  
                }  
            }  
            return loadPrivateKey(sb.toString());  
        } catch (IOException e) {  
            throw new Exception("私钥数据读取错误");  
        } catch (NullPointerException e) {  
            throw new Exception("私钥输入流为空");  
        }  
    }  
	
	
	 public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception{  
	        try {  
	        	byte[] buffer= Base64.decode(publicKeyStr);    
	            KeyFactory keyFactory= KeyFactory.getInstance("RSA");  
	            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);  
	            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
	        } catch (NoSuchAlgorithmException e) {  
	            throw new Exception("无此算法");  
	        } catch (InvalidKeySpecException e) {  
	            throw new Exception("公钥非法");  
	        }catch (NullPointerException e) {  
	            throw new Exception("公钥数据为空");  
	        }  
	    }
	 
	 public static RSAPublicKey loadPublicKey(InputStream in) throws Exception{  
	        try {  
	            BufferedReader br= new BufferedReader(new InputStreamReader(in));  
	            String readLine= null;  
	            StringBuilder sb= new StringBuilder();  
	            while((readLine= br.readLine())!=null){  
	                if(readLine.charAt(0)=='-'){  
	                    continue;  
	                }else{  
	                    sb.append(readLine);  
	                    sb.append('\r');  
	                }  
	            }  
	            return loadPublicKey(sb.toString());  
	        } catch (IOException e) {  
	            throw new Exception("公钥数据流读取错误");  
	        } catch (NullPointerException e) {  
	            throw new Exception("公钥输入流为空");
	        }  
	    }  
	//解密字符串数组
		public static String decryptArray(String reqRSA) throws Exception{
			//init(PATH);
			StringBuffer sb0 = new StringBuffer();
			LOGGER.info("密文内容："+reqRSA);
			String[] sourceStrArray = reqRSA.split(",");
			int length = sourceStrArray.length;
			//js是16进制编码密文
			if("HEXTYPEFORJAVASCRIPT".equals(sourceStrArray[sourceStrArray.length-1])){
				length =  length-1;//末尾标识元素
		        for (int i = 0; i <length; i++) {
		        	byte[] en_test = hexStringToBytes(sourceStrArray[i]);
		        	byte[] de_test;
		        	de_test = RSAUtil.decryptForHex(en_test);
		        	
		        	StringBuffer sb = new StringBuffer();  
		        	sb.append(new String(de_test));  
		        	String  ed = sb.reverse().toString();
		        	sb0.append(ed);
		        }
			}else{
		        for (int i = 0; i <length; i++) {
		        	byte[] en_test = Base64.decode(sourceStrArray[i]);
		        	byte[] de_test;
		        	de_test = RSAUtil.decryptForBase64(en_test);
		        	
		        	StringBuffer sb = new StringBuffer();  
		        	sb.append(new String(de_test));  
		        	String  ed = sb.toString();
		        	sb0.append(ed);
		        }
			}
			LOGGER.info("明文："+java.net.URLDecoder.decode(sb0.toString(),"UTF-8"));
			return java.net.URLDecoder.decode(sb0.toString(),"UTF-8");
		}
		
}  