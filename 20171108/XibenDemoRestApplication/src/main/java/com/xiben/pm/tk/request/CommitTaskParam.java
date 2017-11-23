package com.xiben.pm.tk.request;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.request.UploadAttachParam;

public class CommitTaskParam {
/**
 *  "taskid": 1,
        "remark": "",
        "attachs": []

 * 
 */
	
	
	private Integer taskid;
	private String remark;
	private List<UploadAttachParam> attachs;
	
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
	public List<UploadAttachParam> getAttachs() {
		return attachs;
	}
	public void setAttachs(List<UploadAttachParam> attachs) {
		this.attachs = attachs;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
}
