package com.xiben.pm.utils;

import com.xiben.common.utils.StringUtils;
import com.xiben.sso.client.model.User;

public class ThreadLocalHelper {
	private static final ThreadLocal<String> _ACCESS_TL = new ThreadLocal<String>();
	private static final ThreadLocal<User> _USER_TL =   new ThreadLocal<User>();
	
	public static void setAccessToken(String accessToken)
	{
		_ACCESS_TL.set(accessToken);
	}
	public static String getAccessToken()
	{
		String  accessToken = _ACCESS_TL.get();
		if (StringUtils.isEmpty(accessToken))
			return "";
		return accessToken;
	}
	
	public static void setUser(User user)
	{
		_USER_TL.set(user);
	}
	public static User getUser()
	{
		return _USER_TL.get();
	}
}
