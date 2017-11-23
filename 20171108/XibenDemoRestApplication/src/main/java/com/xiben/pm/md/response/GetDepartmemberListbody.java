package com.xiben.pm.md.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetDepartmemberListbody {

	/**
	 *  "userid": 1,
            "dispname": "",
            "phone": "",
            "logo": "",
            "role": 1

	 */
	
	
	private Integer userid;
	private String dispname;
	private String logo;
	private Integer role;
	
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getDispname() {
		return dispname;
	}
	public void setDispname(String dispname) {
		this.dispname = dispname;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	} 	
	
	
}
