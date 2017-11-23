package com.xiben.pm.rest.request.secure;

public class GetSmscodeRequestBody {
	private String mobile;
	private int biztype;





	public int getBiztype() {
		return biztype;
	}

	public void setBiztype(int biztype) {
		this.biztype = biztype;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
