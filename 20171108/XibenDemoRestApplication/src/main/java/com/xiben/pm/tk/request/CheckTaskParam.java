package com.xiben.pm.tk.request;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class CheckTaskParam {

	/**
	 *  "taskid": 1,
        "remark": "",
        "score": 1

	 * 
	 */
	
	
	private Integer taskid;
	private String remark;
	private Integer score;
	public Integer getTaskid() {
		return taskid;
	}
	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
	
}
