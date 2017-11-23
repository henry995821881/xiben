package com.xiben.pm.rest.request.secure;

public class GetSecureUnidRequestBody {
	private String mobile;
	private int biztype;
	private String code;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
}
