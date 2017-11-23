package com.xiben.pm.md.service;

import java.util.List;
import java.util.Map;

import com.xiben.pm.md.bean.MdDeptEx;
import com.xiben.pm.md.bean.MdEmployeeEx;
import com.xiben.pm.md.pojo.MdDepartment;
import com.xiben.pm.md.pojo.MdDepartmentMember;
import com.xiben.pm.md.pojo.MdDeptduty;
import com.xiben.pm.md.pojo.MdEmployeeEntity;
import com.xiben.pm.md.request.AddDepartmentParam;
import com.xiben.pm.md.request.GetDepartmentListParam;
import com.xiben.pm.md.request.ModifyDepartmentParam;
import com.xiben.pm.md.response.GetDepartmemberListbody;
import com.xiben.pm.md.response.GetDepartmentDetailBody;
import com.xiben.pm.md.response.GetDepartmentListResult;

public interface MdDeptService {

	public List<MdDeptEx> getMdDeptExForCompany(Integer companyid);
	public List<MdDepartment> getDetpByUser(Integer userid);
	public MdDepartmentMember getMemberByUseridAndDeptid(Integer userid,Integer deptid);
	public List<MdEmployeeEntity>   getDeptManagerByDeptid(Integer deptid);
	public void addDepartment(Integer userid,AddDepartmentParam param);
	public MdDepartment getDeptById(Integer deptid);
	public void modifyDepartment(Integer userid,ModifyDepartmentParam param);
	public void removeDepartment(Integer userid,Integer deptid);
	
	public GetDepartmentDetailBody getDepartmentDetail(Integer userid,Integer deptid,String year, String month);
	
	public List<GetDepartmemberListbody> getDepartmemberListbody(Integer userid,Integer deptid);

	public MdDeptduty getDutyBydeptidwithtype1(Integer deptid);
	public MdDeptduty getbyDutyid(Integer dutyid);
	public List<MdEmployeeEx> getMemeberList(Integer deptid);
	public void addDepartmentMember(Integer userid,Integer deptid,String phone);
	public void removeDepartmentMember(Integer userid,Integer deptid,Integer removeuserid);
	public void initDept_office_finance(Integer companyid,Integer userid, Map<String,Integer> return_map);
	
	public List<GetDepartmentListResult> getDepartmentList(GetDepartmentListParam param,int userid);
}
