package com.xiben.pm.md.response.sub;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Dept {

	
	
private Integer	 deptid;
  private String   deptname;
  private Integer  taskcnt;
  private Integer  deptscore;
  private Integer  deptmgrid;
  private String  deptmgrname;
  private String  deptmgrphone;
  private String  deptmgrlogo;
  private Integer issys;
  
  
public Integer getIssys() {
	return issys;
}
public void setIssys(Integer issys) {
	this.issys = issys;
}
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
public Integer getTaskcnt() {
	return taskcnt;
}
public void setTaskcnt(Integer taskcnt) {
	this.taskcnt = taskcnt;
}
public Integer getDeptscore() {
	return deptscore;
}
public void setDeptscore(Integer deptscore) {
	this.deptscore = deptscore;
}
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
  
@Override
public String toString() {
	 return ReflectionToStringBuilder.toString(this); 
}  
  

}
