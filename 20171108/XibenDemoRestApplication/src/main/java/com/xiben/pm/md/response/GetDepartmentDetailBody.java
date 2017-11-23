package com.xiben.pm.md.response;

import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.xiben.pm.md.response.sub.DutyWithTask;

public class GetDepartmentDetailBody {

	
	
	/**
	 * "resdata": {
        "deptid": 1,
        "compid": 1,
        "deptname": "",
        "userright": 1,
        "mgruserid": 1,
        "mgrdispname": "",
        "mgrphone": "",
        "mgrlogo": "",
        "deptscore": "",
        "dutylist": [
            {
                "dutyid": 1,
                "dutyname": "",
                "remark": "",
                "ratio": 1,
                "dutytype": 1,
                "taskcnt": 1,
                "attachs": [
                    {}
                ]
            }
        ]
    }

	 */
	
	
  private Integer deptid;
  private Integer compid;
  private String deptname;
  private Integer userright;
  
  private Integer mgruserid;
  private String mgrdispname;
  private String mgrphone;
  private String mgrlogo;
  private Integer deptscore;
  
  private List<DutyWithTask>  dutylist;

public Integer getDeptid() {
	return deptid;
}

public void setDeptid(Integer deptid) {
	this.deptid = deptid;
}

public Integer getCompid() {
	return compid;
}

public void setCompid(Integer compid) {
	this.compid = compid;
}

public String getDeptname() {
	return deptname;
}

public void setDeptname(String deptname) {
	this.deptname = deptname;
}

public Integer getUserright() {
	return userright;
}

public void setUserright(Integer userright) {
	this.userright = userright;
}

public Integer getMgruserid() {
	return mgruserid;
}

public void setMgruserid(Integer mgruserid) {
	this.mgruserid = mgruserid;
}

public String getMgrdispname() {
	return mgrdispname;
}

public void setMgrdispname(String mgrdispname) {
	this.mgrdispname = mgrdispname;
}

public String getMgrphone() {
	return mgrphone;
}

public void setMgrphone(String mgrphone) {
	this.mgrphone = mgrphone;
}

public String getMgrlogo() {
	return mgrlogo;
}

public void setMgrlogo(String mgrlogo) {
	this.mgrlogo = mgrlogo;
}

public Integer getDeptscore() {
	return deptscore;
}

public void setDeptscore(Integer deptscore) {
	this.deptscore = deptscore;
}

public List<DutyWithTask> getDutylist() {
	return dutylist;
}

public void setDutylist(List<DutyWithTask> dutylist) {
	this.dutylist = dutylist;
}
  
@Override
public String toString() {
	 return ReflectionToStringBuilder.toString(this); 
}  
  
  
}
