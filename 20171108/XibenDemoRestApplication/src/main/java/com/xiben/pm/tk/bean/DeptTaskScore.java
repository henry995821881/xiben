package com.xiben.pm.tk.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class DeptTaskScore {
/**
 * sum(a.score) as scores ,count(1) as tasknum,a.deptid ,
 */
	public Integer deptid;
	public Integer scores ;
	public Integer tasknum;
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
	}
	public Integer getScores() {
		return scores;
	}
	public void setScores(Integer scores) {
		this.scores = scores;
	}
	public Integer getTasknum() {
		return tasknum;
	}
	public void setTasknum(Integer tasknum) {
		this.tasknum = tasknum;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
	

	
}
