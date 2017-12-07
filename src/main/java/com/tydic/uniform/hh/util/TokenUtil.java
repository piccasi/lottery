package com.tydic.uniform.hh.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {
	public static String TOKEN = "token";
	
	public static String getToken(){
		String token = UUID.randomUUID().toString();
		token = token.replaceAll("-", "");
		return token;
	}
	
	public static String getTokenFromRequest(HttpServletRequest request){		
		return request.getHeader(TOKEN);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String token = getToken();
		System.out.println(token + "," + token.length());
	}

}
