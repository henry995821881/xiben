package com.xiben.pm.md.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetDepartmentDetailParam {

/**
 *  "deptid": 1,
        "year": 2017,
        "month": 11

 */
	
	
	private Integer deptid;
	private String year;
	private String month;
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
}
