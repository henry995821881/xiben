package com.xiben.pm.md.request;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetCompanyMgrListParam implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer companyid ;
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
}
