package com.xiben.sso.client.delegate.request.body;

public class VerifySmscodeRequestBody {
	private String access_token;
	private String mobile;
	private int biztype;
	private String smscode;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getBiztype() {
		return biztype;
	}
	public void setBiztype(int biztype) {
		this.biztype = biztype;
	}
	public String getSmscode() {
		return smscode;
	}
	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}
	
	
}
