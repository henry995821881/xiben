package com.xiben.pm.md.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class getDownloadUrlParam {
	private int attachid;

	public int getAttachid() {
		return attachid;
	}

	public void setAttachid(int attachid) {
		this.attachid = attachid;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}
}
