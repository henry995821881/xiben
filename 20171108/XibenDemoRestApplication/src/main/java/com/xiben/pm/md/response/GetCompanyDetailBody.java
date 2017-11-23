package com.xiben.pm.md.response;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.response.sub.Dept;
import com.xiben.pm.md.response.sub.Mgrdept;

public class GetCompanyDetailBody implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Integer  companyid;
    private String fullname;
    private String  shortname;
    private String  logo;
    private Integer userright;
    private String  defaultduty;//":"遵守公司制度及其他任务",
    private String  defaultdutyratio;//": 20,

    
    
	
    public String getDefaultduty() {
		return defaultduty;
	}
	public void setDefaultduty(String defaultduty) {
		this.defaultduty = defaultduty;
	}
	public String getDefaultdutyratio() {
		return defaultdutyratio;
	}
	public void setDefaultdutyratio(String defaultdutyratio) {
		this.defaultdutyratio = defaultdutyratio;
	}
	private List<Mgrdept> mgrdeptlist;
    private List<Dept> deptlist;
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Integer getUserright() {
		return userright;
	}
	public void setUserright(Integer userright) {
		this.userright = userright;
	}
	public List<Mgrdept> getMgrdeptlist() {
		return mgrdeptlist;
	}
	public void setMgrdeptlist(List<Mgrdept> mgrdeptlist) {
		this.mgrdeptlist = mgrdeptlist;
	}
	public List<Dept> getDeptlist() {
		return deptlist;
	}
	public void setDeptlist(List<Dept> deptlist) {
		this.deptlist = deptlist;
	}
    
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}     
    
}

