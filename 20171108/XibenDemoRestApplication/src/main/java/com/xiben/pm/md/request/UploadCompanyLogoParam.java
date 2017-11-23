package com.xiben.pm.md.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.rest.model.UploadAttach;

public class UploadCompanyLogoParam {

	
	private Integer companyid;
    private  UploadAttach logo;
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public UploadAttach getLogo() {
		return logo;
	}
	public void setLogo(UploadAttach logo) {
		this.logo = logo;
	}
    
    
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	} 


}
