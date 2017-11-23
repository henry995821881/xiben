package com.xiben.pm.tk.response.sub;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Page {

	
	/**
	 *  "curpageno": 1,
     "pagenum": 100,
     "pagesize": 15,
     "totalsize": 1500

	 */
	
	private int curpageno;
	private int pagenum;
	private int pagesize;
	private int totalsize;
	public int getCurpageno() {
		return curpageno;
	}
	public void setCurpageno(int curpageno) {
		this.curpageno = curpageno;
	}
	public int getPagenum() {
		return pagenum;
	}
	public void setPagenum(int pagenum) {
		this.pagenum = pagenum;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getTotalsize() {
		return totalsize;
	}
	public void setTotalsize(int totalsize) {
		this.totalsize = totalsize;
	}
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
}

