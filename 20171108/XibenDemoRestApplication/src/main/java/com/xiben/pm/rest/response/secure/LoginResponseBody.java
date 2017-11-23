package com.xiben.pm.rest.response.secure;

import com.xiben.pm.rest.model.RestUser;
import com.xiben.sso.client.model.AccessToken;

public class LoginResponseBody {
	private AccessToken token;
	private RestUser user;
	public AccessToken getToken() {
		return token;
	}
	public void setToken(AccessToken token) {
		this.token = token;
	}
	public RestUser getUser() {
		return user;
	}
	public void setUser(RestUser user) {
		this.user = user;
	}
}
