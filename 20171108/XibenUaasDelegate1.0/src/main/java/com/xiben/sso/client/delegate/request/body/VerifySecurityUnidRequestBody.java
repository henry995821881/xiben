package com.xiben.sso.client.delegate.request.body;

public class VerifySecurityUnidRequestBody {
	private String access_token;
	private String secureunid;
	private int biztype;
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
	public int getBiztype() {
		return biztype;
	}
	public void setBiztype(int biztype) {
		this.biztype = biztype;
	}
}
