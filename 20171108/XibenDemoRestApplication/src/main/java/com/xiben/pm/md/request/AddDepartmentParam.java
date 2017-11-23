package com.xiben.pm.md.request;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.request.sub.Duty;

public class AddDepartmentParam {
/**
 * "reqdata": {
        "companyid": 1,
        "deptname": "",
        "mgruserid": 1,
        "dutylist": [
            {
                "dutyname": "",
                "remark": "",
                "ratio": 45,
                "dutytype": 1,
                "attachs": [
                    {}
                ]
            }
        ]
    }

 */
	
	private Integer companyid ;
	private String deptname;
	private Integer mgruserid;
	private List<Duty> dutylist;
	public Integer getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}
	public String getDeptname() {
		return deptname;
	}
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	public Integer getMgruserid() {
		return mgruserid;
	}
	public void setMgruserid(Integer mgruserid) {
		this.mgruserid = mgruserid;
	}
	public List<Duty> getDutylist() {
		return dutylist;
	}
	public void setDutylist(List<Duty> dutylist) {
		this.dutylist = dutylist;
	}
	
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
}
