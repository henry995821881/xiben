package com.xiben.pm.md.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetDownloadUrlResult {
	private String downloadurl;

	public String getDownloadurl() {
		return downloadurl;
	}

	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	} 
}
