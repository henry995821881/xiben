package com.xiben.pm.md.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetDepartmentListResult {
	private int deptid;
	private String deptname;
	
	private Integer deptmgrid;
	private String deptmgrname;
	private String deptmgrphone;
	private String deptmgrlogo;
	
	
	
	public Integer getDeptmgrid() {
		return deptmgrid;
	}
	public void setDeptmgrid(Integer deptmgrid) {
		this.deptmgrid = deptmgrid;
	}
	public String getDeptmgrname() {
		return deptmgrname;
	}
	public void setDeptmgrname(String deptmgrname) {
		this.deptmgrname = deptmgrname;
	}
	public String getDeptmgrphone() {
		return deptmgrphone;
	}
	public void setDeptmgrphone(String deptmgrphone) {
		this.deptmgrphone = deptmgrphone;
	}
	public String getDeptmgrlogo() {
		return deptmgrlogo;
	}
	public void setDeptmgrlogo(String deptmgrlogo) {
		this.deptmgrlogo = deptmgrlogo;
	}
	public int getDeptid() {
		return deptid;
	}
	public void setDeptid(int deptid) {
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
