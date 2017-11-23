package com.xiben.sso.client.delegate.request.body;

public class GetAccessTokenOrgCodeRequestBody {
	private String organization_code;
	private String sign;
	public String getOrganization_code() {
		return organization_code;
	}
	public void setOrganization_code(String organization_code) {
		this.organization_code = organization_code;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
