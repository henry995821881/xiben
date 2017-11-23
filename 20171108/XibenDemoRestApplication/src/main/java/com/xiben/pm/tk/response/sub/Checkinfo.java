package com.xiben.pm.tk.response.sub;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Checkinfo {

	
	 private Integer score;
     private String remark;
     private String checkdt;
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCheckdt() {
		return checkdt;
	}
	public void setCheckdt(String checkdt) {
		this.checkdt = checkdt;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}
     
}
