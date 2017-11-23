package com.xiben.pm.rest.request.md;

public class GetUserinfoRequestBody {
	private int userid;
	private String phone;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
