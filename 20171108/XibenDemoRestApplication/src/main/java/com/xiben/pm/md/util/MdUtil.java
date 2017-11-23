package com.xiben.pm.md.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.xiben.pm.exception.PMRuntimeException;

import net.sourceforge.pinyin4j.PinyinHelper;

public class MdUtil {

	
	
	 
	 public static void main(String[] args) {
		
		 //String pingYinString = MdUtil.getPingYinString("九点多了几分收到122332ewe3");
		 //System.out.println(pingYinString);
	/* Date o = MdUtil.getMonth("2017", "12");
		 System.out.println(o);*/
		 System.out.println(Integer.valueOf("02"));
	}
	 
	 
	 
	 public static Date getMonth(String year,String month){
		 
		 
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		 Date date =null;
		try {
			 date = format.parse(year+"-"+month);
		} catch (ParseException e) {
			throw new PMRuntimeException("传入的年月不符合,列如：year:2017 month:12");
		}
		
			
		return 	 date;  
	 }
	 
	 public static String getPingYinString(String hanzi){
		 
		 StringBuffer result = new StringBuffer();
		
		 for(int i = 0 ;i<hanzi.length();i++){
			 
			 
			 String[] pinying = PinyinHelper.toHanyuPinyinStringArray(hanzi.charAt(i));
			if(pinying != null){
				
				result.append(String.valueOf(pinying[0].charAt(0)).toUpperCase());
			}else{
				result.append(hanzi.charAt(i));
			} 
			 
		 }


		 return result.toString();
	 }
	 
	 public static String getDateString(Date date){
		 
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String format2 = format.format(date);
		 return format2;
		 
	 }
	 
 public static String getDateStringWithoutTime(Date date){
		 
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 String format2 = format.format(date);
		 return format2;
		 
	 }
 public static Date getStringDate(String date){
		 
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date parse =null;
		try {
			parse = format.parse(date);
		} catch (ParseException e) {
			throw new PMRuntimeException("传入时间不符合,列如：yyyy-MM-dd HH:mm:ss");
		}
		 return parse;
		 
	 }


/**
 * 是否当月
 * @param year
 * @param month
 * @return
 */
public static boolean isCurrentMonth(Integer year, Integer month) {
	
	
	
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		
	
		String dateString = format.format(new Date());
		
		String[] yearmonth = dateString.split("-");
		
		if(year.intValue() == Integer.valueOf(yearmonth[0]).intValue() && month.intValue() == Integer.valueOf(yearmonth[1]).intValue()){
			
			return true;
		}
		 
	
	 
	 
	return false;
}


	 
}
