package com.xiben.pm.tk.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.tk.pojo.TkTask;

public class TkTaskEx extends TkTask {
	
	private String dispname;
	private Integer optype;
	 

	public Integer getOptype() {
		return optype;
	}

	public void setOptype(Integer optype) {
		this.optype = optype;
	}

	public String getDispname() {
		return dispname;
	}

	public void setDispname(String dispname) {
		this.dispname = dispname;
	}
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}
}
