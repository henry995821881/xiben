package com.xiben.pm.tk.response.sub;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.request.UploadAttachParam;

public class Publishinfo {

	
	private String publishdt;
    private String title;
    private String remark;
    private List<UploadAttachParam> attachs;
	public String getPublishdt() {
		return publishdt;
	}
	public void setPublishdt(String publishdt) {
		this.publishdt = publishdt;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<UploadAttachParam> getAttachs() {
		return attachs;
	}
	public void setAttachs(List<UploadAttachParam> attachs) {
		this.attachs = attachs;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	} 
    
}
