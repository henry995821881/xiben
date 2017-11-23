package com.xiben.pm.tk.response.sub;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GetTaskListData {

	/**
	 *   "taskid": 1,
                "taskstatus": 1,
                "optype": 1,
                "title": "",
                "createdt": "",
                "enddt": "",
                "createusername": ""
	 */
	
	private Integer taskid;
	private Integer taskstatus;
	private Integer optype;
	private String createdt;
	private String enddt;
	private String taskno;
	private String createusername;
	public Integer getTaskid() {
		return taskid;
	}
	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}
	public Integer getTaskstatus() {
		return taskstatus;
	}
	public void setTaskstatus(Integer taskstatus) {
		this.taskstatus = taskstatus;
	}
	public Integer getOptype() {
		return optype;
	}
	public void setOptype(Integer optype) {
		this.optype = optype;
	}
	public String getCreatedt() {
		return createdt;
	}
	public void setCreatedt(String createdt) {
		this.createdt = createdt;
	}
	public String getEnddt() {
		return enddt;
	}
	public void setEnddt(String enddt) {
		this.enddt = enddt;
	}
	public String getCreateusername() {
		return createusername;
	}
	public void setCreateusername(String createusername) {
		this.createusername = createusername;
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
