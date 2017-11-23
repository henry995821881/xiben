package com.xiben.pm.tk.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.tk.pojo.TkTaskMonthScore;

public class TkTaskMonthScoreEx extends TkTaskMonthScore {

	private Integer isok;

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
