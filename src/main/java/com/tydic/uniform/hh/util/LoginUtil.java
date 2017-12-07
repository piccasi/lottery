package com.tydic.uniform.hh.util;

import java.util.Map;

import net.sf.json.JSONObject;

public class LoginUtil {
	//public static final String LOGIN_TOKEN_MAP = "active_session_map";
	//public static final String LOGIN_USER_MAP = "active_user_map";
	//public static final String AGENT_INFO_PREFIX = "agent_info_";
	private static final String TOKEN_PREFIX = "online_";//token前缀方便查当前在线用户数
	private static final String LOGINFO_PREFIX = "loginfo_";//用户登录信息key前缀
	private static final int EXPTIME = 24 * 60 * 60;
	
/*	public static boolean isLogin(String token){		
		return RedisUtil.hexists(LOGIN_TOKEN_MAP, token);
	}*/
	
	public static boolean isLogin(String token){		
		return RedisUtil.exsits(TOKEN_PREFIX + token);
	}
	
/*	
 * 
 * 最初的一个账号只能在一处登录
 * 
 * public static String logon(String login_nbr, Map loginInfo){
		String login = JSONObject.fromObject(loginInfo).toString();
		
		//先从已登录的缓存中查，查到则需要删除用户的相关信息
		String token = RedisUtil.hget(LOGIN_USER_MAP, login_nbr);
		if(!StringUtil.isNullOrBlank(token)){
			RedisUtil.hdel(LOGIN_TOKEN_MAP, token);
			RedisUtil.hdel(LOGIN_USER_MAP, token);
			//RedisUtil.del(AGENT_INFO_PREFIX + login_nbr);
		}
		
		token = TokenUtil.getToken();
		RedisUtil.hset(LOGIN_TOKEN_MAP, token, login);
		RedisUtil.hset(LOGIN_USER_MAP, login_nbr, token);
		
		return token;
	}*/
	
	/**
	 * 登录，支持一个账号在多个手机登录
	 * @param login_nbr
	 * @param loginInfo
	 * @return
	 */
	public static String logon(String login_nbr, Map loginInfo){
		String login = JSONObject.fromObject(loginInfo).toString();
		
		String token = TokenUtil.getToken();
		
		RedisUtil.set(TOKEN_PREFIX + token, login_nbr, EXPTIME);//token与登录账号的映射
		RedisUtil.set(login_nbr + "_" + token, "", EXPTIME);//登录账号+token为key，只为查询当前账号有几个人同时在线
		RedisUtil.set(LOGINFO_PREFIX + login_nbr, login, EXPTIME);//登录账号与登录信息
		//RedisUtil.incr("online_count");
		
		return token;
	}
	
	/**
	 * 退出，支持一个账号多个手机登录的退出操作
	 * @param login_nbr
	 * @return
	 */
	 public static boolean logout(String login_nbr, String token){
		 RedisUtil.del(TOKEN_PREFIX + token);
		 RedisUtil.del(login_nbr + "_" + token);
		 RedisUtil.del(LOGINFO_PREFIX + login_nbr);
			
		return true;
	 }
	
/*	
 * 
 * 原来一个账号只能在
 * 
 * public static boolean logout(String login_nbr){
		
		String token = RedisUtil.hget(LOGIN_USER_MAP, login_nbr);
		if(!StringUtil.isNullOrBlank(token)){
			RedisUtil.hdel(LOGIN_TOKEN_MAP, token);
			RedisUtil.hdel(LOGIN_USER_MAP, token);
			//RedisUtil.del(AGENT_INFO_PREFIX + login_nbr);
		}
				
		return true;
	}*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
