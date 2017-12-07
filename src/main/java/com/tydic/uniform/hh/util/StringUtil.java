package com.tydic.uniform.hh.util;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class StringUtil {
	public static Map transStringToMap(String mapString) {
		Map map = new HashMap();
		java.util.StringTokenizer items;
		for (StringTokenizer entrys = new StringTokenizer(mapString, "^"); entrys
				.hasMoreTokens(); map.put(items.nextToken(),
				items.hasMoreTokens() ? ((Object) (items.nextToken())) : null))
			items = new StringTokenizer(entrys.nextToken(), "'");
		return map;
	}

	public static boolean isNullOrBlank(String json) {
		if(null == json || "".equals(json)){
			return true;
		}
		
		return false;
	}
}
