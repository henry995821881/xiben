package com.xiben.pm.tk.bean;

import java.util.Date;

public class IndexTaskBean {

	

	private Integer biztype;
	private Integer bizid;
	private Integer compid;
	private String complogo;
	private String  title;
	private String remark;
	private String  statusname;
	private Integer  optype;
	private String deptname;
	private String  taskenddt;
	
	private Date ordertime;

	public Integer getBiztype() {
		return biztype;
	}

	public void setBiztype(Integer biztype) {
		this.biztype = biztype;
	}

	public Integer getBizid() {
		return bizid;
	}

	public void setBizid(Integer bizid) {
		this.bizid = bizid;
	}

	public Integer getCompid() {
		return compid;
	}

	public void setCompid(Integer compid) {
		this.compid = compid;
	}

	public String getComplogo() {
		return complogo;
	}

	public void setComplogo(String complogo) {
		this.complogo = complogo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatusname() {
		return statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	public Integer getOptype() {
		return optype;
	}

	public void setOptype(Integer optype) {
		this.optype = optype;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}

	public String getTaskenddt() {
		return taskenddt;
	}

	public void setTaskenddt(String taskenddt) {
		this.taskenddt = taskenddt;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	
	
}
