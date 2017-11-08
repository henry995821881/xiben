package com.xiben.sso.site.client.util;

import org.apache.shiro.SecurityUtils;

import com.xiben.sso.client.model.User;

public class UserUtil {

	
	public static User getUser(){
		
		
		return (User) SecurityUtils.getSubject().getSession().getAttribute("currentUser");
	}
}
