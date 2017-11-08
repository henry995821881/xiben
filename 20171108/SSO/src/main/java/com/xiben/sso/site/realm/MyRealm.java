package com.xiben.sso.site.realm;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


import com.xiben.sso.site.bean.User_;
import com.xiben.sso.site.service.LoginService;



public class MyRealm extends AuthorizingRealm {

	@Autowired
	private LoginService userService;
	

	
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
		User_ user = userService.queryByUsername(username);
		if (null != user) {
			AuthenticationInfo info = new SimpleAuthenticationInfo(
					user.getUsername(), user.getPassword(), getName());
			return info;
		}
		return null;
	}


	public LoginService getUserService() {
		return userService;
	}


	public void setUserService(LoginService userService) {
		this.userService = userService;
	}

	
	
	

}