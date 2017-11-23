package com.xiben.pm.md.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.pojo.MdDepartment;

public class MdDeptEx extends MdDepartment {

	
	private Integer userid;
	private String dispname;
	private String phone;
	private String logourl;
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
}
