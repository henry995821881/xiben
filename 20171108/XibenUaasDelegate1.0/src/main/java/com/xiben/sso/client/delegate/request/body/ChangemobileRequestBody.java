package com.xiben.sso.client.delegate.request.body;

public class ChangemobileRequestBody {
	private String access_token;
	private String secureunid;
	private String newmobile;
	private String newsmscode;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getSecureunid() {
		return secureunid;
	}
	public void setSecureunid(String secureunid) {
		this.secureunid = secureunid;
	}
	public String getNewmobile() {
		return newmobile;
	}
	public void setNewmobile(String newmobile) {
		this.newmobile = newmobile;
	}
	public String getNewsmscode() {
		return newsmscode;
	}
	public void setNewsmscode(String newsmscode) {
		this.newsmscode = newsmscode;
	}
	
	
}
