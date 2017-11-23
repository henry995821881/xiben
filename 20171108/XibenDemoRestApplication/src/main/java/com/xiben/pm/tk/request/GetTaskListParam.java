package com.xiben.pm.tk.request;

public class GetTaskListParam {

	private Integer   dutyid;
    private Integer   curpageno;
    private Integer   pagesize;
	public Integer getDutyid() {
		return dutyid;
	}
	public void setDutyid(Integer dutyid) {
		this.dutyid = dutyid;
	}
	public Integer getCurpageno() {
		return curpageno;
	}
	public void setCurpageno(Integer curpageno) {
		this.curpageno = curpageno;
	}
	public Integer getPagesize() {
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

    
}
