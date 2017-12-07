package com.tydic.uniform.hh.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
{
    private static final int[] dayArray = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public static synchronized Calendar getCalendar()
    {
        return GregorianCalendar.getInstance();
    }


    /**
     @param strDate
     @param pattern
     @return java.util.Date
     */
    public static Date parseDate(String strDate, String pattern)
    {
    	 SimpleDateFormat formater = new SimpleDateFormat();
        Date date = null;
        formater.applyPattern(pattern);
        try
        {
            date = parseDate(strDate,formater);
        }
        catch (Exception e)
        {
        	throw new IllegalArgumentException(e.getMessage(),e);
        }
        return date;
    }
    
    
    public static Date parseDateFormat(String strDate, String pattern)
    {
   	 SimpleDateFormat formater = new SimpleDateFormat();
       Date date = null;
       formater.applyPattern(pattern);
       try
       {
           date = parseDate(strDate,formater);
       }
       catch (Exception e)
       {
       	throw new IllegalArgumentException(e.getMessage(),e);
       }
       return date;
   }
    
    public static Date parseDate(String strDate,DateFormat theDateFormat)
    {
        Date date = null;
        try
        {
            date = theDateFormat.parse(strDate);
        }
        catch (Exception e)
        {
        	throw new IllegalArgumentException(e.getMessage(),e);
        }
        return date;
    }
    
    /**
    @param strDate
    @param pattern
    @return java.util.Date
    */
   public static String formatDate(Date strDate, String pattern)
   {
	   SimpleDateFormat formater = new SimpleDateFormat();
       String date = null;
       try
       {
    	   formater.applyPattern(pattern);
           date = formatDate(strDate,formater);
       }
       catch (Exception e)
       {
    	   throw new IllegalArgumentException(e.getMessage(),e);
       }
       return date;
   }
   
   /**
   @param strDate
   @param pattern
   @return java.util.Date
   */
  public static String getDateFormat(Date strDate, String pattern)
  {
	   SimpleDateFormat formater = new SimpleDateFormat();
      String date = null;
      try
      {
   	   formater.applyPattern(pattern);
          date = formatDate(strDate,formater);
      }
      catch (Exception e)
      {
   	   throw new IllegalArgumentException(e.getMessage(),e);
      }
      return date;
  }

   public static String formatDate(Date theDate, DateFormat theDateFormat)
   {
       if (theDate == null) 
	   	{
	   		return "";
	   	}
       return theDateFormat.format(theDate);
   }
   
   public static Date addDate(int field,int amount)
   {
	   Calendar ca = Calendar.getInstance(); 
	   ca.setTime(new Date());
	   ca.add(field, amount); 
	   return ca.getTime();
   }
   public static Date addDate(Date today,int field,int amount)
   {
	   Calendar ca = Calendar.getInstance(); 
	   ca.setTime(today);
	   ca.add(field, amount); 
	   return ca.getTime();
   }
   
   public static void main(String args[])
   {
	   System.out.println(DateUtil.formatDate(DateUtil.addDate(Calendar.DATE, 7),"yyyy-MM-dd"));
	   
   }

   /**
    * 
    * @param date
    * @return  java.util.Date
    */
   public static synchronized java.util.Date getLastDayOfMonth(java.util.Date date)
   {
	   Calendar ca = Calendar.getInstance(); 
       ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
       return ca.getTime();
   }

   public static synchronized java.util.Calendar getLastDayOfMonth(java.util.Calendar gc)
   {
       gc.set(Calendar.DAY_OF_MONTH, dayArray[gc.get(Calendar.MONTH)]);
       
       if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY) && (isLeapYear(gc.get(Calendar.YEAR))))
       {
           gc.set(Calendar.DAY_OF_MONTH, 29);
       }
       return gc;
   }

    public static synchronized int getLastDayOfMonth(int month)
    {
        if (month < 1 || month > 12)
        {
            return -1;
        }
        int retn = 0;
        if (month == 2)
        {
            if (isLeapYear())
            {
                retn = 29;
            }
            else
            {
                retn = dayArray[month - 1];
            }
        }
        else
        {
            retn = dayArray[month - 1];
        }
        return retn;
    }

    public static synchronized int getLastDayOfMonth(int year, int month)
    {
        if (month < 1 || month > 12)
        {
            return -1;
        }
        int retn = 0;
        if (month == 2)
        {
            if (isLeapYear(year))
            {
                retn = 29;
            }
            else
            {
                retn = dayArray[month - 1];
            }
        }
        else
        {
            retn = dayArray[month - 1];
        }
        return retn;
    }

    public static synchronized boolean isLeapYear()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    public static synchronized boolean isLeapYear(int year)
    {
        if (year%400==0) {
        	return true;
        }
        else {
        	return false;
        }
    }

  /**
   * 
   * @param date
   * @return  boolean
   */
    public static synchronized boolean isLeapYear(java.util.Date date)
    {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        int year = gc.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    
    public static synchronized boolean isLeapYear(java.util.Calendar gc)
    {
        int year = gc.get(Calendar.YEAR);
        return isLeapYear(year);
    }


    public static synchronized java.util.Date getFirstDayOfMonth(java.util.Date date)
    {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc.getTime();
    }

    
    public static synchronized java.util.Calendar getFirstDayOfMonth(java.util.Calendar gc)
    {
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc;
    }
    
    
	@SuppressWarnings("unchecked")
	public static java.util.List getNMonth(int monthNum) {
		if (monthNum > 12)
			monthNum = 12;
		java.util.List monthList = new java.util.ArrayList();
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int year = cal.get(java.util.Calendar.YEAR);
		int month = cal.get(java.util.Calendar.MONTH) + 1;
		StringBuffer bf = new StringBuffer("");
		int i = 0;
		while (month >= 1 && i < monthNum) {
			bf.append(year);
			bf.append(month >= 10 ? month : "0" + month);
			monthList.add(bf.toString());
			i++;
			month--;
			bf.delete(0, bf.length());
		}
		for (int j = 0; j < monthNum - i; j++) {
			bf.append(year - 1);
			bf.append(12 - j >= 10 ? 12 - j : "0" + (12 - j));
			monthList.add(bf.toString());
			bf.delete(0, bf.length());
		}

		return monthList;
	}
	
	
	@SuppressWarnings("unchecked")
	public static java.util.List getNMonth1(int monthNum) {
		if (monthNum > 12)
			monthNum = 12;
		java.util.List monthList = new java.util.ArrayList();
		java.util.Calendar cal = java.util.Calendar.getInstance();
		int year = cal.get(java.util.Calendar.YEAR);
		int month = cal.get(java.util.Calendar.MONTH);
		StringBuffer bf = new StringBuffer("");
		int i = 0;
		while (month >= 1 && i < monthNum) {
			bf.append(year);
			bf.append(month >= 10 ? month : "0" + month);
			monthList.add(bf.toString());
			i++;
			month--;
			bf.delete(0, bf.length());
		}
		for (int j = 0; j < monthNum - i; j++) {
			bf.append(year - 1);
			bf.append(12 - j >= 10 ? 12 - j : "0" + (12 - j));
			monthList.add(bf.toString());
			bf.delete(0, bf.length());
		}

		return monthList;
	}
	

	/**
	 * 取日期当月最后一天
	 * 
	 * @author FANG_HONG_BIN
	 * @param date
	 * @return
	 */
	public static Date getCurrMonthEndDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		// 设置日期
		c.setTime(date);
		// 取日期当月第一天
		c.set(Calendar.DAY_OF_MONTH, 1);
		// 取日期当月最后一天
		c.roll(Calendar.DAY_OF_MONTH, -1);
		// 结果
		return c.getTime();
	}

	/**
	 * 获取n年后的年底
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateAddYearLast(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		// 设置日期
		c.setTime(date);
		// 日期加1年
		// c.add(Calendar.DATE , 1);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DATE, 31);
		// 结果
		return c.getTime();
	}

	
}

