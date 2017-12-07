package com.tydic.uniform.hh.constant;

import java.util.HashMap;

public enum BusinessType {
	
	LOGIN {
		@Override
		public String toString(){
			return "代理商登录";
			
		}
		
		@Override
		public int code(){
			return 1;
			
		}
		
	}, IDENTITY {
		@Override
		public String toString(){
			return "实名补登";
			
		}
		
		@Override
		public int code(){
			return 2;
			
		}
	}, DISPLAY {
		@Override
		public String toString(){
			return "来电显示";
			
		}
		
		@Override
		public int code(){
			return 3;
			
		}
	}, PACKAGE {
		@Override
		public String toString(){
			return "加油包订购";
			
		}
		
		@Override
		public int code(){
			return 4;
			
		}
	}, OFFER {
		@Override
		public String toString(){
			return "套餐变更";
			
		}
		
		@Override
		public int code(){
			return 5;
			
		}
	};
	
	public abstract  int code();
	
	private static HashMap<Integer, BusinessType> map = new HashMap<Integer, BusinessType>();
	static {
		for (BusinessType busi : BusinessType.values())
			map.put(busi.code(), busi);
	}
	
	public static BusinessType getByCode(int code) {	
		return map.get(code);
	}

}
