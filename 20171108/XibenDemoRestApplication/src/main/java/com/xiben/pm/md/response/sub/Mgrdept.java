package com.xiben.pm.md.response.sub;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Mgrdept {

	
   private Integer  deptid;
   private String deptname;
public Integer getDeptid() {
	return deptid;
}
public void setDeptid(Integer deptid) {
	this.deptid = deptid;
}
public String getDeptname() {
	return deptname;
}
public void setDeptname(String deptname) {
	this.deptname = deptname;
}

@Override
public String toString() {
	 return ReflectionToStringBuilder.toString(this); 
} 
}
