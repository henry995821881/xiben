package com.xiben.pm.tk.response;


import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetMonthScoreListBody {
/**
 *  "year": 1,
            "month": 1,
            "taskscore": "",
            "isok": 1

 */
	
	
	private Integer year;
	private Integer month;
	private String taskScore;
	private Integer isok;
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public String getTaskScore() {
		return taskScore;
	}
	public void setTaskScore(String taskScore) {
		this.taskScore = taskScore;
	}
	public Integer getIsok() {
		return isok;
	}
	public void setIsok(Integer isok) {
		this.isok = isok;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}
	
}
