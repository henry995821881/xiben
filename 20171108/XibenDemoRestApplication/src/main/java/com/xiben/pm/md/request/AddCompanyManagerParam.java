package com.xiben.pm.md.request;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class AddCompanyManagerParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private Integer	companyid;
private String 	mgrphone;

public Integer getCompanyid() {
	return companyid;
}
public void setCompanyid(Integer companyid) {
	this.companyid = companyid;
}
public String getMgrphone() {
	return mgrphone;
}
public void setMgrphone(String mgrphone) {
	this.mgrphone = mgrphone;
}

@Override
public String toString() {
	 return ReflectionToStringBuilder.toString(this); 
}	

}
