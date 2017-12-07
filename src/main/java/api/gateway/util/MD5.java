package api.gateway.util;

import java.security.MessageDigest;

import api.gateway.base.GatewayAbstractHandler;

public class MD5 {

	public static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	public static String  sign(String waitSign, String appKey,String  INPUT_CHARSET){
		String md5res=md5(waitSign+appKey);
		return md5res;
	}
	public static void main(String[] args) {
		System.out.println(md5("11"));
		System.out.println(md5("22"));
	}
}
