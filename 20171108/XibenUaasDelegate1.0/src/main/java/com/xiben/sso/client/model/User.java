package com.xiben.sso.client.model;

import java.util.List;

public class User  {
    private int userid;
    private String username;
    private String nickname;
    private String dispname;
    private String truename;
    private int isshownickname;
    private int sex;
    private String logourl;
    private String phone;
    private String email;
    private int superioruserid;
    private int securitylevel;
    private int hadsecuritypass;
    private int authstatus;
    private int version;
    private String logon_clientid;
    
	private List<String> privileges;
	private List<String> privilegeuris;
	
	public List<String> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}
	public List<String> getPrivilegeuris() {
		return privilegeuris;
	}
	public void setPrivilegeuris(List<String> privilegeuris) {
		this.privilegeuris = privilegeuris;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public int getSecuritylevel() {
		return securitylevel;
	}
	public void setSecuritylevel(int securitylevel) {
		this.securitylevel = securitylevel;
	}
	public int getHadsecuritypass() {
		return hadsecuritypass;
	}
	public void setHadsecuritypass(int hadsecuritypass) {
		this.hadsecuritypass = hadsecuritypass;
	}
	public int getAuthstatus() {
		return authstatus;
	}
	public void setAuthstatus(int authstatus) {
		this.authstatus = authstatus;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getLogon_clientid() {
		return logon_clientid;
	}
	public void setLogon_clientid(String logon_clientid) {
		this.logon_clientid = logon_clientid;
	}

    
  
}  