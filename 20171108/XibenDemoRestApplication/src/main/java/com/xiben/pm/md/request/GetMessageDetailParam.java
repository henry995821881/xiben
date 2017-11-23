package com.xiben.pm.md.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetMessageDetailParam {

	private Integer noticeid;

	public Integer getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(Integer noticeid) {
		this.noticeid = noticeid;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
}
