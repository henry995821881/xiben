package com.xiben.pm.rest.request.md;

import com.xiben.pm.rest.model.UploadAttach;

public class FinishUserinfoRequestBody {
	private String name;
	private int sex;
	private String superiorphone;
	private UploadAttach logo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getSuperiorphone() {
		return superiorphone;
	}
	public void setSuperiorphone(String superiorphone) {
		this.superiorphone = superiorphone;
	}
	public UploadAttach getLogo() {
		return logo;
	}
	public void setLogo(UploadAttach logo) {
		this.logo = logo;
	}
	
	
}
