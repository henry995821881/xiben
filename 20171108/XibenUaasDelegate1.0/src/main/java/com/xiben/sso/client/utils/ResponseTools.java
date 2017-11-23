package com.xiben.sso.client.utils;

import com.xiben.sso.client.delegate.response.ServiceResponse;

public class ResponseTools {
	public static boolean isError(ServiceResponse response)
	{
		if (null==response)
			return true;
		
		if (Constants._SESSION_TIMEOUT==response.getCode())
			return false;
		
		if (Constants._SUCCESS==response.getCode())
			return false;
		
		return true;
	}
	
	public static boolean isSessionTimeout(ServiceResponse response)
	{
		if (null==response)
			return false;
		
		if (Constants._SESSION_TIMEOUT==response.getCode())
			return true;
		
		return false;
	}
}
