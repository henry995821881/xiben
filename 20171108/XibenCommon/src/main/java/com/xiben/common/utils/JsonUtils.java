package com.xiben.common.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
	public static Object toBean(String json,Class clazz) {
		return JSON.parseObject(json, clazz);
	}
	
	public static String toJson(Object object)
	{
		return JSON.toJSONString(object); 
	}
}