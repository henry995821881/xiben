package com.xiben.pm.md.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class UploadAttachParam {
	private String fk;
	private String fn;
	private String fs;
	private String ft;
	public String getFk() {
		return fk;
	}
	public void setFk(String fk) {
		this.fk = fk;
	}
	public String getFn() {
		return fn;
	}
	public void setFn(String fn) {
		this.fn = fn;
	}
	public String getFs() {
		return fs;
	}
	public void setFs(String fs) {
		this.fs = fs;
	}
	public String getFt() {
		return ft;
	}
	public void setFt(String ft) {
		this.ft = ft;
	}	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}
}
