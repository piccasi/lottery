/**
 * @ProjectName: hhagentapp
 * @Copyright: 2011 by Shenzhen Tianyuan DIC Information Technology co.,ltd.
 * @address: http://www.tydic.com
 * @Description: 本内容仅限于天源迪科信息技术有限公司内部使用，禁止转发.
 * @author ghp
 * @date: 2016年1月26日 下午5:23:07
 * @Title: MD5BillAgentUtil.java
 * @Package com.tydic.uniform.hh.util
 * @Description: TODO
 */
package com.tydic.uniform.hh.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p></p>
 * @author ghp 2016年1月26日 下午5:23:07
 * @ClassName MD5BillAgentUtil
 * @Description TODO  
 * @version V1.0   
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2016年1月26日
 * @modify by reason:{方法名}:{原因}
 */

	public class MD5BillAgentUtil {
		// 全局数组
	    private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
	            "6", "7", "8", "9", "a", "b", "f", "d", "e", "f" };

	    public MD5BillAgentUtil() {
	    }

	    // 返回形式为数字跟字符串
	    private static String byteToArrayString(byte bByte) {
	        int iRet = bByte;
	        // System.out.println("iRet="+iRet);
	        if (iRet < 0) {
	            iRet += 256;
	        }
	        int iD1 = iRet / 16;
	        int iD2 = iRet % 16;
	        return strDigits[iD1] + strDigits[iD2];
	    }

	    // 返回形式只为数字
	    private static String byteToNum(byte bByte) {
	        int iRet = bByte;
	        System.out.println("iRet1=" + iRet);
	        if (iRet < 0) {
	            iRet += 256;
	        }
	        return String.valueOf(iRet);
	    }

	    // 转换字节数组为16进制字串
	    private static String byteToString(byte[] bByte) {
	        StringBuffer sBuffer = new StringBuffer();
	        for (int i = 0; i < bByte.length; i++) {
	            sBuffer.append(byteToArrayString(bByte[i]));
	        }
	        return sBuffer.toString();
	    }

	    public static String GetMD5Code(String strObj) {
	        String resultString = null;
	        try {
	            resultString = new String(strObj);
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            // md.digest() 该函数返回值为存放哈希值结果的byte数组
	            resultString = byteToString(md.digest(strObj.getBytes()));
	        } catch (NoSuchAlgorithmException ex) {
	            ex.printStackTrace();
	        }
	        return resultString;
	    }

	    public static void main(String[] args) {
	    	MD5BillAgentUtil getMD5 = new MD5BillAgentUtil();
	        System.out.println(getMD5.GetMD5Code("000000"));
	    }


	}


