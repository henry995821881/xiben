package com.xiben.pm.md.request;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.request.sub.DutyModify;

public class ModifyDepartmentParam {
/**
 * 
 *  "reqdata": {
        "deptid": 1,
        "deptname": "",
        "mgruserid": 1,
        "dutylist": [
            {
                "dutyid": 1,
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

 * 
 */
	
	
	private Integer deptid;
	private String deptname;
	private Integer mgruserid;
	private List<DutyModify> dutylist;
	public Integer getDeptid() {
		return deptid;
	}
	public void setDeptid(Integer deptid) {
		this.deptid = deptid;
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
	public List<DutyModify> getDutylist() {
		return dutylist;
	}
	public void setDutylist(List<DutyModify> dutylist) {
		this.dutylist = dutylist;
	}
	
	
	@Override
	public String toString() {
		 return ReflectionToStringBuilder.toString(this); 
	}	
		
}
