package com.xiben.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static Date parseDate(String date,String pattern)
	{
		try
		{
			SimpleDateFormat sdf =   new SimpleDateFormat(pattern);
			return sdf.parse(date);
		}catch(Exception e)
		{
			
		}
		return null;
	}
	
	public static String formatDate(Date dt,String pattern)
	{
		try
		{
			SimpleDateFormat sdf =   new SimpleDateFormat(pattern);
			return sdf.format(dt);
		}catch(Exception e)
		{
			
		}
		return "";
	}
}
