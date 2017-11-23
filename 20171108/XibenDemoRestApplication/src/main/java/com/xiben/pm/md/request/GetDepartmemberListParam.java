package com.xiben.pm.md.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetDepartmemberListParam {

	
	private Integer deptid;

	public Integer getDeptid() {
		return deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
}
