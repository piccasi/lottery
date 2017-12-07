package com.tydic.uniform.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tydic.uniform.common.constant.Constants;
import com.tydic.uniform.common.service.interfaces.CommonServiceServ;


@Service("CommonServiceServ")
public class CommonServiceServImpl  implements CommonServiceServ {

	public Map<String,Object> getSystemTime(){
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
		Map<String,Object> rsMap = new HashMap<String,Object>();
		
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1; 
		int date = c.get(Calendar.DATE); 
		int hour = c.get(Calendar.HOUR_OF_DAY); 
		int minute = c.get(Calendar.MINUTE); 
		int second = c.get(Calendar.SECOND); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(c.getTime());
		
		rsMap.put("year", year);
		rsMap.put("month", month);
		rsMap.put("date", date);
		rsMap.put("hour", hour);
		rsMap.put("minute",minute);
		rsMap.put("second", second);
		rsMap.put("time", time);
		rsMap.put(Constants.CODE, Constants.SUCCESSCODE);//成功
		return rsMap;
	}
}
