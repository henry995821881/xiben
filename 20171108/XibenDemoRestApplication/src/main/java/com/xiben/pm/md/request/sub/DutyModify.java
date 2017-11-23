package com.xiben.pm.md.request.sub;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DutyModify extends Duty {

	
	private Integer dutyid;

	public Integer getDutyid() {
		return dutyid;
	}

	public void setDutyid(Integer dutyid) {
		this.dutyid = dutyid;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
	 
}
