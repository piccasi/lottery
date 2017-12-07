package com.tydic.uniform.common.util;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DesEncryptUtil {
		private static Key key;  
		private static String keyStr = "6Ta4OaTYdic=";
		static{
			 setKey(keyStr);  
		}
	  
	    public DesEncryptUtil() {  
	        setKey(keyStr);  
	    }  
	  
	    /**  
	     * 根据参数生成KEY  
	     */  
	   private static void setKey(String strKey) {  
	        try {  
	            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
	            key  = keyFactory.generateSecret(new DESKeySpec(strKey.getBytes("UTF8")));  
	        } catch (Exception e) {  
	            throw new RuntimeException(  
	                    "Error initializing SqlMap class. Cause: " + e);  
	        }  
	    }  
	   /**  
	     * 加密String明文输入,String密文输出  
	     */  
	    public static String encrypt(Map<String,Object> map) {  
	      JSONObject jsonObject = JSONObject.fromObject(map);
	      return encrypt(jsonObject.toString());
	    }  
	      
	    /**  
	     * 加密String明文输入,String密文输出  
	     */  
	    public static String encrypt(String strMing) {  
	        byte[] byteMi = null;  
	        byte[] byteMing = null;  
	        String strMi = "";  
	        BASE64Encoder base64en = new BASE64Encoder();  
	        try {  
	            byteMing = strMing.getBytes("UTF8");  
	            byteMi = getEncCode(byteMing);  
	            strMi = base64en.encode(byteMi);  
	            strMi = StringUtil.replaceBlank(strMi);
	        } catch (Exception e) {  
	            throw new RuntimeException(  
	                    "Error initializing SqlMap class. Cause: " + e);  
	        } finally {  
	            base64en = null;  
	            byteMing = null;  
	            byteMi = null;  
	        }  
	        return strMi;  
	    }  
	  
	    /**  
	     * 解密 以String密文输入,String明文输出  
	     *   
	     * @param strMi  
	     * @return  
	     */  
	    public static String decrypt(String strMi) {  
	        BASE64Decoder base64De = new BASE64Decoder();  
	        byte[] byteMing = null;  
	        byte[] byteMi = null;  
	        String strMing = "";  
	        try {  
	            byteMi = base64De.decodeBuffer(strMi);  
	            byteMing = getDesCode(byteMi);  
	            strMing = new String(byteMing, "UTF8");  
	        } catch (Exception e) {  
	            throw new RuntimeException(  
	                    "Error initializing SqlMap class. Cause: " + e);  
	        } finally {  
	            base64De = null;  
	            byteMing = null;  
	            byteMi = null;  
	        }  
	        return strMing;  
	    }  
	  
	    /**  
	     * 加密以byte[]明文输入,byte[]密文输出  
	     *   
	     * @param byteS  
	     * @return  
	     */  
	    private static byte[] getEncCode(byte[] byteS) {  
	        byte[] byteFina = null;  
	        Cipher cipher;  
	        try {  
	            cipher = Cipher.getInstance("DES");  
	            cipher.init(Cipher.ENCRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));  
	            byteFina = cipher.doFinal(byteS);
	        } catch (Exception e) {  
	            throw new RuntimeException(  
	                    "Error initializing SqlMap class. Cause: " + e);  
	        } finally {  
	            cipher = null;  
	        }  
	        return byteFina;  
	    }  
	  
	    /**  
	     * 解密以byte[]密文输入,以byte[]明文输出  
	     *   
	     * @param byteD  
	     * @return  
	     */  
	    private static byte[] getDesCode(byte[] byteD) {  
	        Cipher cipher;  
	        byte[] byteFina = null;  
	        try {  
	            cipher = Cipher.getInstance("DES");  
	            cipher.init(Cipher.DECRYPT_MODE, key,SecureRandom.getInstance("SHA1PRNG"));  
	            byteFina = cipher.doFinal(byteD);
	        } catch (Exception e) {  
	            throw new RuntimeException(  
	                    "Error initializing SqlMap class. Cause: " + e);  
	        } finally {  
	            cipher = null;  
	        }  
	        return byteFina;  
	    }  
	  
}
