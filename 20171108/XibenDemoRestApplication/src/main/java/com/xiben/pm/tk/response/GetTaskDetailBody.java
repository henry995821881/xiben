package com.xiben.pm.tk.response;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.tk.response.sub.Checkinfo;
import com.xiben.pm.tk.response.sub.Finishinfo;
import com.xiben.pm.tk.response.sub.Publishinfo;

public class GetTaskDetailBody {

	
	/**
	 * 
	 * 
	 *  "taskid": 1,
        "checkinfo": {
            "score": 4,
            "remark": "",
            "checkdt": ""
        },
        "publishinfo": {
            "publishdt": "",
            "title": "",
            "remark": "",
            "attachs": []
        },
        "finishinfo": {
            "remark": "",
            "finishdt": "",
            "attachs": []
        }

	 */
	
	private Integer taskid;
	private String taskno;
	private Checkinfo checkinfo;
	private Publishinfo publishinfo;
	private Finishinfo finishinfo;
	
	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	public Checkinfo getCheckinfo() {
		return checkinfo;
	}

	public void setCheckinfo(Checkinfo checkinfo) {
		this.checkinfo = checkinfo;
	}

	public Publishinfo getPublishinfo() {
		return publishinfo;
	}

	public void setPublishinfo(Publishinfo publishinfo) {
		this.publishinfo = publishinfo;
	}

	public Finishinfo getFinishinfo() {
		return finishinfo;
	}

	public void setFinishinfo(Finishinfo finishinfo) {
		this.finishinfo = finishinfo;
	}
	
	

	public String getTaskno() {
		return taskno;
	}

	public void setTaskno(String taskno) {
		this.taskno = taskno;
	}

	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}
	
}
