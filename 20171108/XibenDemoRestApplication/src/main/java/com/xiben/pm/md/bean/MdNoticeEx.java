package com.xiben.pm.md.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.pojo.MdNotice;

public class MdNoticeEx extends MdNotice {

	
	/**
	 * logourl,e.dispname
	 * 
	 */
	
	private String logourl;
	private String dispname;
	public String getLogourl() {
		return logourl;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}
	public String getDispname() {
		return dispname;
	}
	public void setDispname(String dispname) {
		this.dispname = dispname;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
}
