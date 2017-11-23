package com.xiben.pm.md.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class RemoveCompanyManagerParam {

private Integer	 companyid;

private Integer  mgruserid;

public Integer getCompanyid() {
	return companyid;
}

public void setCompanyid(Integer companyid) {
	this.companyid = companyid;
}

public Integer getMgruserid() {
	return mgruserid;
}

public void setMgruserid(Integer mgruserid) {
	this.mgruserid = mgruserid;
}
@Override
public String toString() {
	 return ReflectionToStringBuilder.toString(this); 
}

}
