package com.xiben.sso.client.dto;


public class ChangeUserInfoDto {
	private String access_token;
	private String nickname;
	private String dispname;
	private String truename;
	private int isshownickname;
	private int sex;
	private String logourl;
	private String email;
	private int superioruserid;
	private int authstatus;
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDispname() {
		return dispname;
	}
	public void setDispname(String dispname) {
		this.dispname = dispname;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public int getIsshownickname() {
		return isshownickname;
	}
	public void setIsshownickname(int isshownickname) {
		this.isshownickname = isshownickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getSuperioruserid() {
		return superioruserid;
	}
	public void setSuperioruserid(int superioruserid) {
		this.superioruserid = superioruserid;
	}
	public int getAuthstatus() {
		return authstatus;
	}
	public void setAuthstatus(int authstatus) {
		this.authstatus = authstatus;
	} 
}
