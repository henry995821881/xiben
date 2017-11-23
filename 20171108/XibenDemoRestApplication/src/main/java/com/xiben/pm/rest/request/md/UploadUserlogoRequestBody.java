package com.xiben.pm.rest.request.md;

import com.xiben.pm.rest.model.UploadAttach;

public class UploadUserlogoRequestBody {
	private int userid;
	private UploadAttach logo;
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public UploadAttach getLogo() {
		return logo;
	}
	public void setLogo(UploadAttach logo) {
		this.logo = logo;
	}
	
	
}
