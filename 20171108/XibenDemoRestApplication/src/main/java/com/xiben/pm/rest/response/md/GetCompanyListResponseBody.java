package com.xiben.pm.rest.response.md;

import java.io.Serializable;


public class GetCompanyListResponseBody implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer companyid;
	private String fullname	;
	private String shortname;
	private String logo	;
	private Integer userright;
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getUserright() {
		return userright;
	}
	public void setUserright(Integer userright) {
		this.userright = userright;
	}	
	

	
	
}
