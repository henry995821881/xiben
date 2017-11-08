package com.xiben.sso.site.client.realm;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Value;

import com.xiben.common.security.DsaCoder;
import com.xiben.sso.client.delegate.UaasRestServiceDelegator;
import com.xiben.sso.client.delegate.UaasRestServiceDelegatorImpl;
import com.xiben.sso.client.exception.UaasBusinessException;
import com.xiben.sso.client.model.User;
import com.xiben.sso.site.client.bean.SSOAuthenticationInfo;





public class SSORealm extends AuthorizingRealm {

	
	
	  @Value("${web.sso_login_privateKey}")
		private String Login_privateKey;
	
	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
	/*	authorizationInfo.setRoles(new HashSet<>(userExtendDao
				.getRoles(username)));
		authorizationInfo.setStringPermissions(new HashSet<>(userExtendDao
				.getPermissions(username)));*/
		
		return authorizationInfo;
	}

	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		UaasRestServiceDelegator uaasRestServiceDelegator = UaasRestServiceDelegatorImpl.instance();
		
		
		DsaCoder coder = new DsaCoder();
		User user = null;
		 try {
			String sign = coder.sign(username, Login_privateKey);
			 user = uaasRestServiceDelegator.getAccessTokenByAuthorizationCode(username, sign);
		} catch (Exception e) {
			return null;
		}
		 
		    SecurityUtils.getSubject().getSession().setAttribute("currentUser", user);
			AuthenticationInfo info = new  SSOAuthenticationInfo(user,user.getUsername(),"",getName());//new SimpleAuthenticationInfo(user.getUsername(),"", getName());
			
			return info;
		
		
	}



	
	
	

}