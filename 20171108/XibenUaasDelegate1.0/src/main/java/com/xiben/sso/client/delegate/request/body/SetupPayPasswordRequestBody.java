package com.xiben.sso.client.delegate.request.body;

public class SetupPayPasswordRequestBody {
	private String access_token;
	private String secureunid;
	private int optype;
	private int opscope;
	private String oldpaypass;
	private String paypassword;
	private int securitylevel;
	
	public int getOpscope() {
		return opscope;
	}
	public void setOpscope(int opscope) {
		this.opscope = opscope;
	}
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
	public int getOptype() {
		return optype;
	}
	public void setOptype(int optype) {
		this.optype = optype;
	}
	public String getOldpaypass() {
		return oldpaypass;
	}
	public void setOldpaypass(String oldpaypass) {
		this.oldpaypass = oldpaypass;
	}
	public String getPaypassword() {
		return paypassword;
	}
	public void setPaypassword(String paypassword) {
		this.paypassword = paypassword;
	}
	public int getSecuritylevel() {
		return securitylevel;
	}
	public void setSecuritylevel(int securitylevel) {
		this.securitylevel = securitylevel;
	}
}
