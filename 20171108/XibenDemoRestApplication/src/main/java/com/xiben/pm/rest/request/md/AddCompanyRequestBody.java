package com.xiben.pm.rest.request.md;

import java.io.Serializable;

import com.xiben.pm.rest.model.UploadAttach;

public class AddCompanyRequestBody implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private UploadAttach logo;
	private String fullname;
	private String shortname;
	
	public UploadAttach getLogo() {
		return logo;
	}
	public void setLogo(UploadAttach logo) {
		this.logo = logo;
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
	
	
	

}
