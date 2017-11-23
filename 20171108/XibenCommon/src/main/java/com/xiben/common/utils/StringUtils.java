package com.xiben.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	public static boolean isEmpty(String str)
	{
		if (null==str||"".equals(str.trim()))
			return true;
		return false;
	}
	
	public static String formatDate(Date date)
	{
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   String dateString = formatter.format(date);
	   return dateString;
	}
	
	public static String formatDate()
	{
		Date dt = new Date();
	
	   return formatDate(dt);
	}
}
