package com.xiben.sso.site.util;

import java.util.UUID;


public class SSOUtil {


	public static String getUUID(){ 
		String uuid = UUID.randomUUID().toString(); 
		//去掉“-”符号 
		return uuid.replaceAll("-", "");
		}

	
}
