package com.tydic.uniform.hh.util;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码工具类
 * @author chenliangyu
 *
 */
public class ValidateCodeUtil {
	private static final Map<String,Object[]> codeContext = new HashMap<String, Object[]>();//验证码上下文
	private static final int DEFAULT_LENGTH = 6;//验证码默认长度;
	private static final int DEFAULT_TIMES = 5 * 1000 * 60;//验证码默认有效期（毫秒）

	/**
	 * 将验证码放入验证码上下文
	 * @param mobTel
	 * @param validateCode
	 */
	public static void put(String mobTel, String validateCode){
		if(null != validateCode){
			codeContext.put(mobTel, new Object[]{validateCode, System.currentTimeMillis()});
		}
	}
	/**
	 * 判断传入的验证码是否与上下文中的相同，如果相同表示通过，否则为不通过
	 * @param mobTel
	 * @param validateCode
	 * @return
	 */
	public static boolean isSuccess(String mobTel, String validateCode){
		Object[] obj = codeContext.get(mobTel);
		if(null == obj){
			return false;
		}
		else{
			long newMills = System.currentTimeMillis();
			long oldMills = (Long)obj[1];
			if((newMills - oldMills) <= DEFAULT_TIMES){
				String code = (String)obj[0];
				boolean isPass = code.equals(validateCode);
				if(isPass){//验证码成功匹配1次后，需要做废
					codeContext.remove(mobTel);
				}
				return isPass;
			}
			return false;
		}
	}
	/**
	 * 生成一个随机验证码
	 * @return
	 */
	public static String create(int length) {
		String seed = "0123456789";//种子
		StringBuffer radomChars = new StringBuffer();
		Random random = new Random();
		int len = DEFAULT_LENGTH;
		if(length <= 0 || length > DEFAULT_LENGTH){
			len = length;
		}
		for (int i=0; i<len; i++) {
			radomChars.append(seed.charAt(random.nextInt(seed.length())));
		}
		return radomChars.toString();
	}
}
