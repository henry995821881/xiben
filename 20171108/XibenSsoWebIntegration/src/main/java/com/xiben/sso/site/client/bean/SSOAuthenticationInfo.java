package com.xiben.sso.site.client.bean;

import org.apache.shiro.authc.SimpleAuthenticationInfo;

import com.xiben.sso.client.model.User;

public class SSOAuthenticationInfo extends SimpleAuthenticationInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

       private User user;

       
       public SSOAuthenticationInfo(User user,String username ,String password,String realmName){
    	   
    	   super(username,password,realmName);
    	   this.setUser(user);
       }
    
       
       
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
       
       
       
}
