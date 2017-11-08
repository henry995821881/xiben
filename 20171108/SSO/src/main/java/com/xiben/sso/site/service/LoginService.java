package com.xiben.sso.site.service;

import com.xiben.sso.site.bean.User_;

public interface LoginService {

	public User_  queryByUsername(String username);
	
	public String getRedirectUrl(String salt, String client_id, String sign, String redirect_uri) throws Exception ;
	
	public boolean shiroLogin(String username, String password) ;
		
	
}
