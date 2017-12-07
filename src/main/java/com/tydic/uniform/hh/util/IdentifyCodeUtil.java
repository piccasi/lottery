package com.tydic.uniform.hh.util;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.tydic.uniform.hh.vo.rep.IdentifyCodeVo;

public class IdentifyCodeUtil {
	private static Logger logger = Logger.getLogger(IdentifyCodeUtil.class);
	
	
	//private static Random RANDOM = new Random(6); //随机数对象
	private static String SEED = "0123456789";//种子	
	private static int DEFAULT_LENGTH = 6;//默认验证码长度
	private static int DEFAULT_EXPTIME = 60 * 5;//默认2分钟有效
	
	
	/**
	 * 生成一个随机验证码
	 * @param length
	 * @return
	 */
	public static String create(int length) {
		
		StringBuffer radomChars = new StringBuffer();
		Random random = new Random();
		int len = DEFAULT_LENGTH;
		if(length <= 0 || length > DEFAULT_LENGTH){
			len = length;
		}
		for (int i=0; i<len; i++) {
			radomChars.append(SEED.charAt(random.nextInt(SEED.length())));
		}
		
		return radomChars.toString();
	}
	
	/**
	 * 验证码存储
	 * @param ico
	 * @return
	 */
	public static boolean store(IdentifyCodeVo ico){
		
		String key = (StringUtil.isNullOrBlank(ico.getLogin_nbr()) ? ico.getMobile_170() : ico.getLogin_nbr()) + "_" + ico.getType().name(); 
		String code = ico.getCode();
		
		String ret = RedisUtil.set(key, code, DEFAULT_EXPTIME);
		
		
		logger.info("store:" + ico + ",ret:" + ret + ",key:" + key + ",code=" + code);
		
		return "OK".equals(ret);
	}
	
	/**
	 * 验证码校验
	 * @param ico
	 * @return
	 */
	public static boolean isValidate(IdentifyCodeVo ico){
		
		String key = (StringUtil.isNullOrBlank(ico.getLogin_nbr()) ? ico.getMobile_170() : ico.getLogin_nbr()) + "_" + ico.getType().name(); 
		//String key =  ico.getLogin_nbr() + "_" + (null != ico.getType() ? ico.getType().name() : "0"); 
		String code = RedisUtil.get(key);
		
		logger.info("isValidate:" + ico + ",key:" + key + ",code=" + code);
		
		return (null != code) && code.equals(ico.getCode());
	}

}
