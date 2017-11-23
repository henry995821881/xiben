package com.xiben.sso.client.delegate.request.body;

public class GetUserInfoRequestBody {
	private String access_token;

	private String phone;
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
}
