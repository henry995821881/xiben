package com.xiben.pm.md.response.sub;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.request.sub.Duty;

public class DutyWithTask extends Duty {

private Integer	taskcnt;
private Integer	dutyid;
public Integer getTaskcnt() {
	return taskcnt;
}
public void setTaskcnt(Integer taskcnt) {
	this.taskcnt = taskcnt;
}
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
